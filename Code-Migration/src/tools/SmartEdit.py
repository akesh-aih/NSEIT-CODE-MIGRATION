import sys
import asyncio
import os
import re
from pathlib import Path
from pydantic import BaseModel, Field
from autogen_core import CancellationToken
from autogen_core.code_executor import CodeBlock
from autogen_ext.code_executors.local import LocalCommandLineCodeExecutor
from autogen_core.tools import FunctionTool

# ✅ Fix for Windows asyncio subprocess issues
if sys.platform == "win32":
    asyncio.set_event_loop_policy(asyncio.WindowsProactorEventLoopPolicy())


class EditToolParams(BaseModel):
    """
    Parameters for the Edit tool.
    """
    file_path: str = Field(..., description="The absolute path to the file to modify.")
    old_string: str = Field(..., description="The text to replace.")
    new_string: str = Field(..., description="The text to replace it with.")
    instruction: str = Field(..., description="The instruction for what needs to be done.")


def apply_replacement(current_content: str | None, old_string: str, new_string: str, is_new_file: bool) -> str:
    """
    Applies the replacement logic.
    """
    if is_new_file:
        return new_string
    if current_content is None:
        # Should not happen if not a new file
        return ""
    if old_string == "":
        return current_content
    return current_content.replace(old_string, new_string)


def restore_trailing_newline(original_content: str, modified_content: str) -> str:
    """
    Restores the trailing newline if it existed in the original content.
    """
    had_trailing_newline = original_content.endswith('\n')
    if had_trailing_newline and not modified_content.endswith('\n'):
        return modified_content + '\n'
    if not had_trailing_newline and modified_content.endswith('\n'):
        return modified_content.rstrip('\n')
    return modified_content


def calculate_exact_replacement(current_content: str, params: EditToolParams) -> tuple[str, int, str, str] | None:
    """
    Calculates an exact replacement.
    Returns (new_content, occurrences, final_old_string, final_new_string) or None.
    """
    normalized_code = current_content
    normalized_search = params.old_string.replace('\r\n', '\n')
    normalized_replace = params.new_string.replace('\r\n', '\n')

    exact_occurrences = normalized_code.count(normalized_search)
    if exact_occurrences > 0:
        modified_code = normalized_code.replace(normalized_search, normalized_replace)
        modified_code = restore_trailing_newline(current_content, modified_code)
        return (modified_code, exact_occurrences, normalized_search, normalized_replace)

    return None


def calculate_flexible_replacement(current_content: str, params: EditToolParams) -> tuple[str, int, str, str] | None:
    """
    Calculates a flexible, indentation-agnostic replacement.
    Returns (new_content, occurrences, final_old_string, final_new_string) or None.
    """
    normalized_code = current_content
    normalized_search = params.old_string.replace('\r\n', '\n')
    normalized_replace = params.new_string.replace('\r\n', '\n')

    source_lines = normalized_code.splitlines(keepends=True)
    search_lines_stripped = [line.strip() for line in normalized_search.split('\n')]
    replace_lines = normalized_replace.split('\n')

    flexible_occurrences = 0
    i = 0
    while i <= len(source_lines) - len(search_lines_stripped):
        window = [line.strip() for line in source_lines[i:i + len(search_lines_stripped)]]
        is_match = all(window[j] == search_lines_stripped[j] for j in range(len(search_lines_stripped)))

        if is_match:
            flexible_occurrences += 1
            first_line_in_match = source_lines[i]
            indentation_match = re.match(r'^(\s*)', first_line_in_match)
            indentation = indentation_match.group(1) if indentation_match else ''
            new_block_with_indent = [f"{indentation}{line}" for line in replace_lines]
            
            source_lines[i:i + len(search_lines_stripped)] = new_block_with_indent
            i += len(replace_lines)
        else:
            i += 1

    if flexible_occurrences > 0:
        modified_code = "".join(source_lines)
        modified_code = restore_trailing_newline(current_content, modified_code)
        return (modified_code, flexible_occurrences, normalized_search, normalized_replace)

    return None


def get_error_replace_result(
    params: EditToolParams,
    occurrences: int,
    expected_replacements: int,
    final_old_string: str,
    final_new_string: str,
) -> dict | None:
    """
    Returns an error dictionary if there is an issue with the replacement count.
    """
    if occurrences == 0:
        return {
            "display": "Failed to edit, could not find the string to replace.",
            "raw": f"Failed to edit, 0 occurrences found for old_string ({final_old_string}). Original old_string was ({params.old_string}) in {params.file_path}. No edits made.",
        }
    if occurrences != expected_replacements:
        occurrence_term = "occurrence" if expected_replacements == 1 else "occurrences"
        return {
            "display": f"Failed to edit, expected {expected_replacements} {occurrence_term} but found {occurrences}.",
            "raw": f"Failed to edit, Expected {expected_replacements} {occurrence_term} but found {occurrences} for old_string in file: {params.file_path}",
        }
    if final_old_string == final_new_string:
        return {
            "display": "No changes to apply. The old_string and new_string are identical.",
            "raw": f"No changes to apply. The old_string and new_string are identical in file: {params.file_path}",
        }
    return None

def mock_fix_llm_edit(instruction: str, old_string: str, new_string: str, error_message: str, current_content: str):
    """
    This is a mock function to simulate the LLM's self-correction.
    In a real implementation, this would be an LLM call.
    It attempts to find a likely correct old_string based on the error.
    """
    # Simple mock logic: If the error says "0 occurrences found" and the old string has newlines,
    # try trimming them to see if it fixes it.
    
    # Check for flexible replacement match if exact failed.
    if "0 occurrences found" in error_message:
        normalized_search = old_string.replace('\r\n', '\n')
        normalized_code = current_content.replace('\r\n', '\n')
        
        search_lines_stripped = [line.strip() for line in normalized_search.split('\n')]
        source_lines = normalized_code.splitlines(keepends=True)

        i = 0
        while i <= len(source_lines) - len(search_lines_stripped):
            window = [line.strip() for line in source_lines[i:i + len(search_lines_stripped)]]
            if all(window[j] == search_lines_stripped[j] for j in range(len(search_lines_stripped))):
                # Found a flexible match, return a corrected `old_string`
                # We'll use the original content's lines to get the correct whitespace
                corrected_old_string_lines = source_lines[i:i + len(search_lines_stripped)]
                return {
                    "noChangesRequired": False,
                    "explanation": "Flexible match found by ignoring whitespace and indentation.",
                    "search": "".join(corrected_old_string_lines),
                    "replace": new_string.replace('\r\n', '\n')
                }
            i += 1

    return {
        "noChangesRequired": True,
        "explanation": "No fix could be determined by the self-correction logic.",
        "search": old_string,
        "replace": new_string
    }


async def attempt_self_correction(
    params: EditToolParams,
    current_content: str,
    initial_error: dict
):
    """
    Attempts to self-correct a failed edit using a mock LLM fix.
    """
    fixed_edit = mock_fix_llm_edit(
        params.instruction,
        params.old_string,
        params.new_string,
        initial_error['raw'],
        current_content,
    )

    if fixed_edit['noChangesRequired']:
        return {
            "new_content": current_content,
            "occurrences": 0,
            "error": {
                "display": "No changes required. The file already meets the specified conditions.",
                "raw": f"A secondary check determined that no changes were necessary to fulfill the instruction. Explanation: {fixed_edit['explanation']}. Original error with the parameters given: {initial_error['raw']}",
            },
        }

    second_attempt_result = calculate_exact_replacement(
        current_content,
        EditToolParams(
            file_path=params.file_path,
            old_string=fixed_edit['search'],
            new_string=fixed_edit['replace'],
            instruction=params.instruction
        )
    )

    if second_attempt_result:
        new_content, occurrences, _, _ = second_attempt_result
        return {
            "new_content": new_content,
            "occurrences": occurrences,
            "error": None
        }

    # The fix failed, return the original error
    return {
        "new_content": current_content,
        "occurrences": 0,
        "error": initial_error,
    }


async def run_edit_tool(input: EditToolParams):
    """
    Executes a file edit operation.
    """
    file_path_obj = Path(input.file_path)
    expected_replacements = 1

    current_content = None
    file_exists = False
    is_new_file = input.old_string == ""

    if not is_new_file:
        try:
            with open(file_path_obj, 'r', encoding='utf-8') as f:
                current_content = f.read()
            file_exists = True
        except FileNotFoundError:
            file_exists = False
        except Exception as e:
            return {
                "llmContent": f"Error preparing edit: {str(e)}",
                "success": False,
                "error": {"message": f"Error preparing edit: {str(e)}"}
            }

    if is_new_file:
        if file_exists:
            return {
                "llmContent": "Failed to edit. Attempted to create a file that already exists.",
                "success": False,
                "error": {"message": f"File already exists, cannot create: {input.file_path}"}
            }
        
        # New file creation
        try:
            file_path_obj.parent.mkdir(parents=True, exist_ok=True)
            with open(file_path_obj, 'w', encoding='utf-8') as f:
                f.write(input.new_string)
            return {
                "llmContent": f"Created new file: {input.file_path} with provided content.",
                "success": True,
                "work_dir": str(file_path_obj.parent)
            }
        except Exception as e:
            return {
                "llmContent": f"Error writing file: {str(e)}",
                "success": False,
                "error": {"message": f"Error writing file: {str(e)}"}
            }

    # Edits on an existing file
    if not file_exists:
        return {
            "llmContent": "File not found. Cannot apply edit. Use an empty old_string to create a new file.",
            "success": False,
            "error": {"message": f"File not found: {input.file_path}"}
        }
    
    # Attempt replacement
    replacement_result = calculate_exact_replacement(current_content, input)
    if not replacement_result:
        replacement_result = calculate_flexible_replacement(current_content, input)

    initial_error = None
    if replacement_result:
        _, occurrences, final_old_string, final_new_string = replacement_result
        initial_error = get_error_replace_result(input, occurrences, expected_replacements, final_old_string, final_new_string)

    if initial_error:
        calculated_edit = await attempt_self_correction(input, current_content, initial_error)
        if calculated_edit['error']:
            return {
                "llmContent": calculated_edit['error']['raw'],
                "success": False,
                "error": {"message": calculated_edit['error']['display']}
            }
        new_content = calculated_edit['new_content']
        occurrences = calculated_edit['occurrences']
    else:
        new_content, occurrences, _, _ = replacement_result

    # Write the new content to the file
    try:
        with open(file_path_obj, 'w', encoding='utf-8') as f:
            f.write(new_content)
        return {
            "llmContent": f"Successfully modified file: {input.file_path} ({occurrences} replacements).",
            "success": True,
            "work_dir": str(file_path_obj.parent)
        }
    except Exception as e:
        return {
            "llmContent": f"Error writing file: {str(e)}",
            "success": False,
            "error": {"message": f"Error writing file: {str(e)}"}
        }

# ✅ Wrap in FunctionTool
edit_tool = FunctionTool(
    func=run_edit_tool,
    description="Replaces text within a file, handling new file creation and flexible replacements.",
    # input_model=EditToolParams,
)


# ✅ Example standalone usage
async def main():
    # Example 1: Creating a new file
    result_create = await run_edit_tool(
        EditToolParams(
            file_path=str(Path("./test_dir/new_file.txt").resolve()),
            old_string="",
            new_string="This is a new file.",
            instruction="Create a new file with some text."
        )
    )
    print("\n🔧 Create File Output:", result_create)

    # Example 2: Editing an existing file
    result_edit = await run_edit_tool(
        EditToolParams(
            file_path=str(Path("./test_dir/new_file.txt").resolve()),
            old_string="new file.",
            new_string="edited file!",
            instruction="Change the content of the file."
        )
    )
    print("\n🔧 Edit File Output:", result_edit)


if __name__ == "__main__":
    # Ensure test directory exists for the examples
    test_dir = Path("./test_dir")
    test_dir.mkdir(exist_ok=True)
    asyncio.run(main())

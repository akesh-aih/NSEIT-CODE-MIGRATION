import asyncio
import os
import re
from typing import Dict, List, Literal, Optional, TypedDict

import aiofiles
from autogen_core.tools import FunctionTool




async def edit_file(
    file_path: str,
    old_string: str,
    new_string: str,
    expected_replacements: Optional[int] = 1,
) -> str:
    """
    Replaces specific text within a file.

    This tool is designed for precise, literal text replacement. It's crucial
    that `old_string` and `new_string` are exact, unescaped text snippets,
    including all whitespace and newlines.

    - If `old_string` is an empty string and the `file_path` does not exist,
      a new file will be created with `new_string` as its content.
    - If `old_string` is not empty, the tool will search for and replace
      all occurrences that exactly match `old_string`.
    - If the number of replacements found does not match `expected_replacements`,
      the operation will fail to prevent unintended changes.

    Args:
        file_path (str): The absolute path to the file to modify.
        old_string (str): The exact text to find and replace.
        new_string (str): The exact text to replace it with.
        expected_replacements (Optional[int]): The expected number of times
                                                `old_string` should be found.

    Returns:
        str: A success message detailing the number of replacements made.

    Raises:
        ValueError: If file_path is not an absolute path or is a directory.
        FileNotFoundError: If the file_path does not exist and old_string is not empty.
        PermissionError: If the file cannot be accessed or written to.
        Exception: For other unexpected errors, including occurrence mismatches.
    """
    if not os.path.isabs(file_path):
        raise ValueError(f"❌ File path must be absolute: '{file_path}'")

    if os.path.isdir(file_path):
        raise IsADirectoryError(f"📁 '{file_path}' is a directory, not a file.")

    try:
        # Check if file exists to determine if it's a new file creation
        file_exists = os.path.exists(file_path)
        is_new_file = not file_exists and not old_string

        if not file_exists and not is_new_file:
            raise FileNotFoundError(f"🔍 File not found: '{file_path}'. Cannot apply edit.")

        original_content = ""
        if file_exists:
            async with aiofiles.open(file_path, mode="r") as file:
                original_content = await file.read()
            
            # Normalize line endings
            original_content = original_content.replace('\r\n', '\n')

        # Perform the replacement and count occurrences
        if is_new_file:
            new_content = new_string
            occurrences = 1  # A new file is considered a single "replacement"
        else:
            # Check for occurrences to prevent unintended modifications
            matches = list(re.finditer(re.escape(old_string), original_content))
            occurrences = len(matches)

            if occurrences == 0 and old_string != "":
                raise ValueError(
                    f"❌ Failed to edit: 'old_string' not found in file. "
                    f"No replacements made. Please check the content of the file."
                )

            if expected_replacements is not None and occurrences != expected_replacements:
                raise ValueError(
                    f"⚠️ Mismatch! Expected {expected_replacements} replacements "
                    f"but found {occurrences}. No edits were performed."
                )
            
            new_content = original_content.replace(old_string, new_string)

        # Write the new content to the file
        parent_dir = os.path.dirname(file_path)
        if parent_dir:
            os.makedirs(parent_dir, exist_ok=True)
            
        async with aiofiles.open(file_path, mode="w") as file:
            await file.write(new_content)

        if is_new_file:
            return f"✅ Successfully created new file: '{file_path}'"
        else:
            return f"✅ Successfully modified file: '{file_path}' ({occurrences} replacements)."

    except (FileNotFoundError, ValueError, PermissionError, IsADirectoryError) as e:
        # Re-raise specific, user-friendly exceptions
        raise e
    except Exception as e:
        # Catch-all for other unexpected errors
        raise Exception(f"🚨 An unexpected error occurred during file editing: {e}") from e


# Create the FunctionTool instance
edit_file_tool = FunctionTool(
    name="edit_file",
    description=edit_file.__doc__,
)

print(edit_file_tool.schema)
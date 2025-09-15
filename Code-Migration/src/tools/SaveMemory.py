import asyncio
import os
import re
from typing import Dict, List, Literal, Optional, TypedDict
import aiofiles
from autogen_core.tools import FunctionTool


# --- Constants and Configuration (Mirroring TypeScript) ---

# In a real system, these would be managed by a global config,
# but for this standalone tool, we define them here.
GLOBAL_GEMINI_DIR = os.path.join(os.getcwd(), ".state")
DEFAULT_CONTEXT_FILENAME = "AI-HORIZON.md"
MEMORY_SECTION_HEADER = "## Agent Added Memories"


def _get_global_memory_file_path() -> str:
    """Returns the absolute path to the global memory file."""
    return os.path.join(GLOBAL_GEMINI_DIR, DEFAULT_CONTEXT_FILENAME)


def _ensure_newline_separation(current_content: str) -> str:
    """Ensures proper newline separation before appending content."""
    if not current_content:
        return ""
    if current_content.endswith("\n\n") or current_content.endswith("\r\n\r\n"):
        return ""
    if current_content.endswith("\n") or current_content.endswith("\r\n"):
        return "\n"
    return "\n\n"


def _compute_new_content(current_content: str, fact: str) -> str:
    """Computes the new content to write to the memory file."""
    processed_fact = fact.strip()
    processed_fact = re.sub(r"^(-+\s*)+", "", processed_fact).strip()
    new_memory_item = f"- {processed_fact}"

    header_index = current_content.find(MEMORY_SECTION_HEADER)

    if header_index == -1:
        # Header not found, append header and then the entry
        separator = _ensure_newline_separation(current_content)
        return (
            current_content + f"{separator}{MEMORY_SECTION_HEADER}\n{new_memory_item}\n"
        )
    else:
        # Header found, find where to insert the new memory entry
        start_of_section_content = header_index + len(MEMORY_SECTION_HEADER)
        end_of_section_index = current_content.find("\n## ", start_of_section_content)
        if end_of_section_index == -1:
            end_of_section_index = len(current_content)  # End of file

        before_section = current_content[:start_of_section_content].rstrip()
        section_content = current_content[
            start_of_section_content:end_of_section_index
        ].rstrip()
        after_section = current_content[end_of_section_index:].strip()

        section_content += f"\n{new_memory_item}"

        # Re-assemble the file content
        new_content_parts = [before_section.strip(), section_content.strip()]
        if after_section:
            new_content_parts.append(after_section)

        return "\n".join(new_content_parts).strip() + "\n"


async def save_memory(fact: str) -> str:
    """
    🧠 Saves a specific piece of information or fact to your long-term memory.

    Use this when the user explicitly asks you to remember something, or when they
    state a clear, concise fact that seems important to retain for future interactions.

    Args:
        fact (str): The specific fact or piece of information to remember. This
                    should be a clear, self-contained statement.

    Returns:
        str: A success or error message.
    """
    if not fact.strip():
        raise ValueError("Parameter 'fact' must be a non-empty string.")

    memory_file_path = _get_global_memory_file_path()

    try:
        # Read current content of the memory file
        current_content = ""
        try:
            # Use asyncio.to_thread for blocking os.path.exists
            if await asyncio.to_thread(os.path.exists, memory_file_path):
                async with aiofiles.open(memory_file_path, "r", encoding="utf-8") as f:
                    current_content = await f.read()
        except FileNotFoundError:
            pass  # File does not exist, start with empty content

        # Compute the new content
        new_content = _compute_new_content(current_content, fact)

        # Ensure the directory exists
        parent_dir = os.path.dirname(memory_file_path)
        await asyncio.to_thread(os.makedirs, parent_dir, exist_ok=True)

        # Write the new content to the file
        async with aiofiles.open(memory_file_path, "w", encoding="utf-8") as f:
            await f.write(new_content)

        return f"✅ Okay, I've remembered that: '{fact}'"

    except Exception as e:
        return f"🚨 Error: Failed to save memory. Detail: {e}"


# Create the FunctionTool instance
save_memory_tool = FunctionTool(
    name="save_memory",
    description=save_memory.__doc__,
    func=save_memory,
    # parameters=MemoryToolParams,
)


if __name__ == "__main__":

    async def main():
        # Example usage:
        test_fact = "My favorite color is blue."
        try:
            result = await save_memory(fact=test_fact)
            # print(result)

            # Test another fact to see if it appends correctly
            result_2 = await save_memory(fact="My dog's name is Whiskers.")
            # print(result_2)

            await save_memory(fact="")
        except ValueError as e:
            print(f"Caught expected error: {e}")

    asyncio.run(main())

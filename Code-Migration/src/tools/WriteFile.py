import asyncio
import os
from typing import Dict, List, Literal, Optional, TypedDict

import aiofiles
from autogen_core.tools import FunctionTool


async def write_file(file_path: str, content: str) -> str:
    """
    ✍️ Asynchronously writes content to a file.

    This tool overwrites the file if it exists. It will also create the file
    and any necessary parent directories if they don't exist.

    Args:
        file_path (str): The absolute path to the file to write to.
        content (str): The content to write to the file.

    Returns:
        str: A success message indicating the file was written, or an error message.

    Raises:
        ValueError: If the file_path is not an absolute path.
        PermissionError: If the file cannot be written due to permission issues.
        IsADirectoryError: If the given path is a directory, not a file.
        OSError: For other I/O related errors.
    """
    if not os.path.isabs(file_path):
        raise ValueError(f"❌ File path must be absolute: '{file_path}'")

    try:
        # Create parent directories if they don't exist
        parent_dir = os.path.dirname(file_path)
        if parent_dir and not os.path.exists(parent_dir):
            os.makedirs(parent_dir, exist_ok=True)

        async with aiofiles.open(file_path, mode="w") as file:
            await file.write(content)
        return f"✅ Successfully wrote to file: '{file_path}'"

    except PermissionError as e:
        raise PermissionError(f"🚫 Permission denied for file: '{file_path}'") from e
    except IsADirectoryError as e:
        raise IsADirectoryError(f"📁 '{file_path}' is a directory, not a file.") from e
    except OSError as e:
        raise OSError(
            f"⚠️ Unexpected I/O error while writing to '{file_path}': {e}"
        ) from e
    except Exception as e:
        raise Exception(f"🚨 An unexpected error occurred: {e}") from e


# Create the FunctionTool instance
file_writer_tool = FunctionTool(
    description=write_file.__doc__,
)

# print(file_writer_tool.schema)
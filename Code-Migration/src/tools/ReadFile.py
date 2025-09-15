import asyncio
from typing import List, Dict
import aiofiles
from autogen_core.tools import FunctionTool

import aiofiles
import os

async def read_file(file_path):
    """
    🗂️ Asynchronously reads the contents of a file and returns it as a string.

    Args:
        file_path (str): The path to the file you want to read.

    Returns:
        str: The contents of the file, if successful.

    Raises:
        FileNotFoundError: If the file does not exist.
        PermissionError: If the file cannot be accessed due to permission issues.
        IsADirectoryError: If the given path is a directory, not a file.
        OSError: For other I/O related errors.


    Notes:
        🧠 Pro Tip: Make sure the file exists before trying to read it,
        unless you enjoy catching exceptions as a hobby!
    """
    try:
        if not os.path.isfile(file_path):
            raise FileNotFoundError(f"🔍 File not found: {file_path}")

        async with aiofiles.open(file_path, mode='r') as file:
            content = await file.read()
            return content

    except FileNotFoundError as e:
        raise FileNotFoundError(f"❌ Oops! The file at '{file_path}' doesn't exist.") from e

    except PermissionError as e:
        raise PermissionError(f"🚫 Permission denied for file: '{file_path}'") from e

    except IsADirectoryError as e:
        raise IsADirectoryError(f"📁 '{file_path}' is a directory, not a file.") from e

    except OSError as e:
        raise OSError(f"⚠️ Unexpected I/O error while reading '{file_path}': {e}") from e

    

async def read_multiple_files(file_paths: List[str]) -> Dict[str, str]:
    """
    📂 Reads multiple files asynchronously and returns their contents.

    Args:
        file_paths (List[str]): A list of file paths to read.

    Returns:
        Dict[str, str]: A dictionary where each key is the file path and the value is the file's content.

    Notes:
        Files that fail to read will return the error message as their value.
    """
    async def safe_read(file_path: str) -> tuple[str, str]:
        try:
            content = await read_file(file_path)
            return (file_path, content)
        except Exception as e:
            return (file_path, f"❌ Error reading file: {str(e)}")

    results = await asyncio.gather(*(safe_read(path) for path in file_paths))
    return dict(results)

file_reader_tool = FunctionTool(read_multiple_files, description=read_multiple_files.__doc__)

print(file_reader_tool.schema)
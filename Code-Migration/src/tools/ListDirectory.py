import asyncio
import os
import re
from typing import Dict, List, Literal, Optional, TypedDict
from functools import partial
import aiofiles
from autogen_core.tools import FunctionTool




async def list_directory(
    path: str,
    ignore: Optional[List[str]] = None,
    # file_filtering_options: Optional[Dict[str, bool]] = None,
) -> str:
    """
    📂 Lists the names of files and subdirectories within a specified directory.

    This tool is a directory listing utility that returns a formatted string
    of the contents of a given directory.

    Args:
        path (str): The absolute path to the directory.
        ignore (Optional[List[str]]): An optional list of glob patterns to exclude from the results.
        file_filtering_options (Optional[Dict[str, bool]]): An optional dictionary to control
            whether to respect .gitignore and .geminiignore files.

    Returns:
        str: A string containing a formatted list of directory contents, or an error message.
    
    Raises:
        ValueError: If the path is not absolute or does not exist.
        NotADirectoryError: If the path exists but is not a directory.
    """
    if not os.path.isabs(path):
        raise ValueError(f"❌ Path must be absolute: '{path}'")

    if not await asyncio.to_thread(os.path.exists, path):
        raise FileNotFoundError(f"❌ Directory not found: '{path}'")

    if not await asyncio.to_thread(os.path.isdir, path):
        raise NotADirectoryError(f"❌ Path is not a directory: '{path}'")
    
    def _should_ignore(filename: str, patterns: Optional[List[str]]) -> bool:
        """Helper to check if a filename matches an ignore pattern."""
        if not patterns:
            return False
        for pattern in patterns:
            try:
                # Convert glob to regex for matching
                regex_pattern = re.escape(pattern).replace(r'\*', '.*').replace(r'\?', '.')
                if re.fullmatch(regex_pattern, filename):
                    return True
            except re.error:
                # Fallback to simple string check if regex fails
                if filename == pattern:
                    return True
        return False

    try:
        # Readdir is a blocking call, so use to_thread
        all_entries = await asyncio.to_thread(os.listdir, path)
        
        # This part of the logic (gitignore/geminiignore filtering) is complex to implement
        # without external libraries. The `file_filtering_options` are acknowledged in the
        # function signature but a full implementation is beyond a simple script.
        
        filtered_entries = []
        for entry_name in all_entries:
            if _should_ignore(entry_name, ignore):
                continue
            
            full_path = os.path.join(path, entry_name)
            try:
                # Stat is a blocking call
                stats = await asyncio.to_thread(os.stat, full_path)
                
                is_dir = stats.st_mode & 0o40000 == 0o40000 # Check if directory
                
                filtered_entries.append({
                    "name": entry_name,
                    "path": full_path,
                    "isDirectory": is_dir,
                    "size": stats.st_size if not is_dir else 0,
                    "modifiedTime": stats.st_mtime
                })
            except OSError:
                continue

        # Sort: directories first, then by name
        filtered_entries.sort(key=lambda x: (not x['isDirectory'], x['name']))

        if not filtered_entries:
            return f"Directory '{path}' is empty."
            
        output_lines = [f"Directory listing for {path}:"]
        for entry in filtered_entries:
            prefix = "[DIR] " if entry["isDirectory"] else ""
            output_lines.append(f"{prefix}{entry['name']}")
        
        return "\n".join(output_lines)

    except Exception as e:
        raise OSError(f"🚨 An unexpected error occurred while listing directory: {e}") from e


# Create the FunctionTool instance
list_directory_tool = FunctionTool(
    name="list_directory",
    description=list_directory.__doc__,
    func=list_directory,
    # parameters=LSToolParams,
)

if __name__ == "__main__":
    async def main():
        # Example usage:
        # List files in the current directory, ignoring .pyc files
        result = await list_directory(path=os.getcwd(), ignore=["*.md","*gnore"])
        print(result)

    asyncio.run(main())
import asyncio
import os
import re
import shutil
from typing import Dict, List, Optional, TypedDict
from functools import partial
import subprocess
from autogen_core.tools import FunctionTool
from rich import print
import json
# --- Constants and Configuration ---

DEFAULT_TOTAL_MAX_MATCHES = 20000



async def search_file_content(
    pattern: str,
    path: Optional[str] = None,
    include: Optional[str] = None,
) -> str:
    """
    🔎 Searches for a regular expression pattern in file contents using ripgrep.
    
    This is an optimized tool for searching large codebases. It uses ripgrep for
    fast, reliable, and efficient searches, returning all matching lines with
    their file paths and line numbers.
    
    Args:
        pattern (str): The regular expression (regex) to search for.
        path (Optional[str]): The absolute path to the directory to search within.
        include (Optional[str]): A glob pattern to filter which files are searched.
    
    Returns:
        str: A formatted string showing all found matches, or a message if none are found.
    """
    search_path = os.path.abspath(path) if path else os.getcwd()

    if not await asyncio.to_thread(os.path.isdir, search_path):
        raise ValueError(f"❌ Search path is not a valid directory: '{search_path}'")

    if not await asyncio.to_thread(shutil.which, 'rg'):
        return "⚠️ Ripgrep ('rg') is not installed or not in PATH. Please install it to use this tool."
    
    rg_args = [
        '--json',  # <-- Use JSON output for robust parsing
        '--regexp',
        pattern,
        '--no-ignore'
    ]

    if include:
        rg_args.extend(['--glob', include])

    rg_args.append(search_path)
    
    matches = []
    
    try:
        proc = await asyncio.create_subprocess_exec(
            'rg',
            *rg_args,
            stdout=asyncio.subprocess.PIPE,
            stderr=asyncio.subprocess.PIPE
        )
        
        stdout, stderr = await proc.communicate()
        stdout_str = stdout.decode('utf-8')
        stderr_str = stderr.decode('utf-8')
        # print(stdout_str)
        if proc.returncode not in [0, 1]:  # 0: found, 1: not found
            raise RuntimeError(f"ripgrep failed with code {proc.returncode}: {stderr_str}")

        if stdout_str:
            for line in stdout_str.splitlines():
                if line.startswith('{'):
                    data = json.loads(line)
                    if data['type'] == 'match':
                        match_data = data['data']
                        matches.append({
                            "file_path": match_data['path']['text'],
                            "line_number": match_data['line_number'],
                            "line": match_data['lines']['text'].strip()
                        })
    
    except FileNotFoundError:
        return "⚠️ Ripgrep ('rg') executable not found. Please ensure it's installed and in your system's PATH."
    except Exception as e:
        return f"🚨 An unexpected error occurred during search: {e}"

    if not matches:
        return f"No matches found for pattern '{pattern}' in '{search_path}'."

    was_truncated = len(matches) > DEFAULT_TOTAL_MAX_MATCHES
    if was_truncated:
        matches = matches[:DEFAULT_TOTAL_MAX_MATCHES]

    output_lines = []
    matches_by_file = {}
    for match in matches:
        file_path = match['file_path']
        if file_path not in matches_by_file:
            matches_by_file[file_path] = []
        matches_by_file[file_path].append(match)

    match_count = len(matches)
    output_lines.append(f"Found {match_count} match(es) for pattern '{pattern}' in '{search_path}'")
    if was_truncated:
        output_lines[0] += f" (results limited to {DEFAULT_TOTAL_MAX_MATCHES} matches)."
    output_lines.append("\n")

    for file_path, file_matches in matches_by_file.items():
        output_lines.append(f"File: {file_path}")
        for match in file_matches:
            output_lines.append(f"\tLine {match['line_number']}: {match['line']}")
        output_lines.append("\n")
        
        
    return "\n".join(output_lines)

# Create the FunctionTool instance
search_file_content_tool = FunctionTool(
    name="search_file_content",
    description=search_file_content.__doc__,
    func=search_file_content,
    # parameters=RipGrepToolParams,
)

if __name__ == "__main__":
    async def main():
        # Example: Search for 'def ' in the current directory's Python files
        # (Note: This will only work if 'rg' is installed and in your PATH)
        try:
            result = await search_file_content(pattern="^def ", include="*.py")
            print(result)
        except Exception as e:
            print(f"Test failed: {e}")

    asyncio.run(main())
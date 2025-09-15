import asyncio
import os
import re
from typing import Dict, List, Optional, TypedDict
import aiofiles
from autogen_core.tools import FunctionTool
from functools import partial


import glob
# --- Interfaces ---






async def search_file_content(
    pattern: str,
    path: Optional[str] = None,
    include: Optional[str] = None,
) -> str:
    """
    🔍 Searches for a regular expression pattern within the content of files.

    This tool searches files in a specified directory (or the current working directory)
    for a given regular expression pattern. It returns the lines that match the
    pattern, along with their file paths and line numbers.

    Args:
        pattern (str): The regular expression to search for.
        path (Optional[str]): The absolute path to the directory to search within.
        include (Optional[str]): A glob pattern to filter files. E.g., '*.js' or '**/*.py'.

    Returns:
        str: A formatted string showing all found matches, or a message if no
             matches are found.
    """
    search_path = os.path.abspath(path) if path else os.getcwd()

    if not await asyncio.to_thread(os.path.isdir, search_path):
        raise ValueError(f"❌ Search path is not a valid directory: '{search_path}'")
    
    try:
        re.compile(pattern)
    except re.error:
        raise ValueError(f"❌ Invalid regular expression pattern: '{pattern}'")

    async def _run_git_grep():
        """Attempts to run `git grep`."""
        proc = await asyncio.create_subprocess_shell(
            f'git grep --untracked -n -E -I "{pattern}" -- {include if include else ""}',
            cwd=search_path,
            stdout=asyncio.subprocess.PIPE,
            stderr=asyncio.subprocess.PIPE
        )
        stdout, stderr = await proc.communicate()
        if proc.returncode == 0:
            return stdout.decode()
        elif proc.returncode == 1:
            return "" # No matches found
        else:
            raise RuntimeError(f"git grep failed: {stderr.decode()}")
    
    async def _run_system_grep():
        """Attempts to run system `grep`."""
        cmd = f'grep -r -n -H -E -I --exclude-dir=__pycache__ "{pattern}" {include if include else ""} .'
        proc = await asyncio.create_subprocess_shell(
            cmd,
            cwd=search_path,
            stdout=asyncio.subprocess.PIPE,
            stderr=asyncio.subprocess.PIPE
        )
        stdout, stderr = await proc.communicate()
        if proc.returncode == 0:
            return stdout.decode()
        elif proc.returncode == 1:
            return "" # No matches
        else:
            # Suppress common "Permission denied" errors
            if "Permission denied" not in stderr.decode():
                raise RuntimeError(f"System grep failed: {stderr.decode()}")
            return ""

    async def _fallback_search():
        """Pure Python fallback using glob and aiofiles."""
        glob_pattern = os.path.join(search_path, include if include else "**/*")
        all_files = await asyncio.to_thread(
            glob.glob, glob_pattern, recursive=True
        )
        
        compiled_regex = re.compile(pattern)
        all_matches = []

        async def _read_and_search(file_path):
            try:
                async with aiofiles.open(file_path, 'r') as f:
                    content = await f.read()
                
                matches = []
                for i, line in enumerate(content.splitlines()):
                    if compiled_regex.search(line):
                        matches.append({
                            "file_path": os.path.relpath(file_path, search_path),
                            "line_number": i + 1,
                            "line": line.strip()
                        })
                return matches
            except OSError:
                return []
        
        tasks = [_read_and_search(f) for f in all_files]
        results = await asyncio.gather(*tasks)
        
        for result in results:
            all_matches.extend(result)
        
        return all_matches

    # --- Search Strategy ---
    raw_output = ""
    matches = []

    try:
        raw_output = await _run_git_grep()
        if raw_output:
            # Simple parsing for git grep output
            matches = [
                {"file_path": m.group(1), "line_number": int(m.group(2)), "line": m.group(3).strip()}
                for m in re.finditer(r"^([^:]+):(\d+):(.*)$", raw_output, re.MULTILINE)
            ]
    except Exception:
        try:
            raw_output = await _run_system_grep()
            if raw_output:
                # Simple parsing for system grep output
                matches = [
                    {"file_path": m.group(1), "line_number": int(m.group(2)), "line": m.group(3).strip()}
                    for m in re.finditer(r"^([^:]+):(\d+):(.*)$", raw_output, re.MULTILINE)
                ]
        except Exception:
            # Fallback to pure Python search
            matches = await _fallback_search()

    if not matches:
        return f"No matches found for pattern '{pattern}' in '{search_path}'."

    # Format the final output
    output_lines = []
    matches_by_file = {}
    for match in matches:
        file_path = match['file_path']
        if file_path not in matches_by_file:
            matches_by_file[file_path] = []
        matches_by_file[file_path].append(match)

    for file_path, file_matches in matches_by_file.items():
        output_lines.append(f"File: {file_path}")
        for match in file_matches:
            output_lines.append(f"\tLine {match['line_number']}: {match['line']}")
        output_lines.append("---")
        
    return "\n".join(output_lines)

# Create the FunctionTool instance
search_file_content_tool = FunctionTool(
    name="search_file_content",
    description=search_file_content.__doc__,
    func=search_file_content,
    # parameters=GrepToolParams,
)

if __name__ == "__main__":
    async def main():
        # Example usage:
        # Find all mentions of "async" in Python files
        result = await search_file_content(pattern="async", include="*.py")
        print(result)

    asyncio.run(main())
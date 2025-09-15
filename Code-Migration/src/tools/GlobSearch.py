import asyncio
import os
import glob
import glob as fnmatch_glob
from typing import Dict, List, Optional, TypedDict
from functools import partial
import aiofiles
from autogen_core.tools import FunctionTool



def _sort_file_entries_sync(entries: List[Dict], now_timestamp: float, recency_threshold_ms: int) -> List[Dict]:
    """
    Synchronous helper to sort file entries. This is designed to be run in a thread.
    """
    sorted_entries = sorted(entries, key=lambda x: x['path'])
    
    def sort_key(entry):
        mtime_ms = entry.get('mtime_ms', 0)
        is_recent = now_timestamp - mtime_ms < recency_threshold_ms
        return (not is_recent, -mtime_ms)
        
    sorted_entries.sort(key=sort_key)
    return sorted_entries


async def glob_search(
    pattern: str,
    path: Optional[str] = None,
    # case_sensitive is removed from the signature as it's not a supported glob parameter
    respect_git_ignore: Optional[bool] = True,
    respect_gemini_ignore: Optional[bool] = True,
) -> str:
    """
    🔍 Asynchronously finds files that match a given glob pattern.

    This tool efficiently searches for files using a glob pattern, returning absolute
    paths sorted by modification time (newest first). It's ideal for quickly locating
    files without blocking the main event loop.

    Args:
        pattern (str): The glob pattern to match files against.
        path (Optional[str]): The absolute path to the directory to start the search.
        respect_git_ignore (Optional[bool]): Whether to ignore files specified in .gitignore.
        respect_gemini_ignore (Optional[bool]): Whether to ignore files specified in .geminiignore.

    Returns:
        str: A string containing a list of found files, or a message indicating no files were found.

    Raises:
        ValueError: If the search path is not a valid absolute directory.
    """
    if not pattern:
        raise ValueError("The 'pattern' parameter cannot be empty.")

    search_path = os.path.abspath(path) if path else os.getcwd()

    if not await asyncio.to_thread(os.path.isabs, search_path) or not await asyncio.to_thread(os.path.isdir, search_path):
        raise ValueError(f"❌ Search path must be a valid absolute directory: '{search_path}'")

    # The case_sensitive argument has been removed, resolving the TypeError.
    all_files = await asyncio.to_thread(
        glob.glob,
        os.path.join(search_path, pattern),
        recursive=True
    )

    async def get_ignored_files():
        ignored_patterns = []
        if respect_git_ignore:
            git_ignore_path = os.path.join(search_path, '.gitignore')
            if await asyncio.to_thread(os.path.exists, git_ignore_path):
                async with aiofiles.open(git_ignore_path, 'r') as f:
                    ignored_patterns.extend((await f.read()).splitlines())
        
        if respect_gemini_ignore:
            gemini_ignore_path = os.path.join(search_path, '.aihignore')
            if await asyncio.to_thread(os.path.exists, gemini_ignore_path):
                async with aiofiles.open(gemini_ignore_path, 'r') as f:
                    ignored_patterns.extend((await f.read()).splitlines())
        return ignored_patterns

    ignored_patterns = await get_ignored_files()
    
    # Use fnmatch from glob for pattern matching.
    filtered_files = await asyncio.to_thread(
        lambda: [
            file_path for file_path in all_files
            if not any(fnmatch_glob.fnmatch.fnmatch(file_path, os.path.join(search_path, ignored)) for ignored in ignored_patterns)
        ]
    )

    if not filtered_files:
        return f"No files found matching pattern '{pattern}' in '{search_path}'."

    async def get_file_stats(file_path):
        try:
            mtime_ms = (await asyncio.to_thread(os.path.getmtime, file_path)) * 1000
            return {'path': file_path, 'mtime_ms': mtime_ms}
        except OSError:
            return None
    
    file_entries = await asyncio.gather(*[get_file_stats(f) for f in filtered_files])
    file_entries = [entry for entry in file_entries if entry is not None]

    now_timestamp = asyncio.get_event_loop().time() * 1000
    sorted_files = await asyncio.to_thread(
        _sort_file_entries_sync, file_entries, now_timestamp, 24 * 60 * 60 * 1000
    )
    
    file_list = "\n".join([entry['path'] for entry in sorted_files])
    
    return f"Found {len(sorted_files)} file(s) matching '{pattern}' in '{search_path}':\n{file_list}"


glob_search_tool = FunctionTool(
    name="glob_search",
    description=glob_search.__doc__,
    func=glob_search
)

if __name__ == "__main__":
    print(asyncio.run(glob_search("**/**s")))


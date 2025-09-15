import difflib
from typing import TypedDict, Dict
from autogen_core.tools import FunctionTool

# This is a constant from the original TypeScript code,
# though not used directly in the Python implementation below.
DEFAULT_DIFF_OPTIONS = {
    "context": 3,
    "ignoreWhitespace": True,
}



class DiffStat(TypedDict):
    """
    Represents the statistical differences between two versions of a file.
    """

    model_added_lines: int
    model_removed_lines: int
    model_added_chars: int
    model_removed_chars: int
    user_added_lines: int
    user_removed_lines: int
    user_added_chars: int
    user_removed_chars: int



def get_diff_stat(old_str: str, ai_str: str, user_str: str) -> Dict[str, int]:
    """
    📊 Calculates the line and character-level differences between two or three
    versions of a file's content.

    This tool is useful for understanding the magnitude of changes made
    by an AI and then subsequently by a human user.

    Args:
        old_str (str): The original content of the file.
        ai_str (str): The content proposed by the AI.
        user_str (str): The content after user modification.

    Returns:
        Dict[str, int]: A dictionary containing the statistical diffs,
                        including counts for lines and characters added and removed
                        by both the model and the user.
    """

    def _get_stats(old: str, new: str) -> tuple[int, int, int, int]:
        """Helper function to calculate stats for a single diff."""
        added_lines, removed_lines, added_chars, removed_chars = 0, 0, 0, 0

        diff = difflib.unified_diff(
            old.splitlines(keepends=True),
            new.splitlines(keepends=True),
            n=DEFAULT_DIFF_OPTIONS["context"],
        )

        for line in diff:
            if line.startswith("+"):
                added_lines += 1
                added_chars += len(line) - 1
            elif line.startswith("-"):
                removed_lines += 1
                removed_chars += len(line) - 1

        return added_lines, removed_lines, added_chars, removed_chars

    # Calculate model's changes from the original content
    (model_added_lines, model_removed_lines, model_added_chars, model_removed_chars) = (
        _get_stats(old_str, ai_str)
    )

    # Calculate user's changes from the AI-proposed content
    (user_added_lines, user_removed_lines, user_added_chars, user_removed_chars) = (
        _get_stats(ai_str, user_str)
    )

    return DiffStat(
        model_added_lines=model_added_lines,
        model_removed_lines=model_removed_lines,
        model_added_chars=model_added_chars,
        model_removed_chars=model_removed_chars,
        user_added_lines=user_added_lines,
        user_removed_lines=user_removed_lines,
        user_added_chars=user_added_chars,
        user_removed_chars=user_removed_chars,
    )


# Create the FunctionTool instance
get_diff_stat_tool = FunctionTool(
    # name="get_diff_stat",
    func=get_diff_stat,
    description=get_diff_stat.__doc__,
    # parameters=GetDiffStatParams,
)

if __name__ == "__main__":
    print(get_diff_stat_tool.schema)
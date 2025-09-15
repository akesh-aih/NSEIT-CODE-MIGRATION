import sys
import asyncio
import venv
import os
from pathlib import Path
from pydantic import BaseModel, Field
from autogen_core import CancellationToken
from autogen_core.code_executor import CodeBlock
from autogen_ext.code_executors.local import LocalCommandLineCodeExecutor
from autogen_core.tools import FunctionTool

# ✅ Fix for Windows asyncio subprocess issues
if sys.platform == "win32":
    asyncio.set_event_loop_policy(asyncio.WindowsProactorEventLoopPolicy())


# ✅ Input model with work_dir support
class ShellCommandInput(BaseModel):
    command: str = Field(..., description="The shell command to execute")
    work_dir: str = Field(
        default="dev_workdir",
        description="Directory where the command should run (will be created if missing).",
    )
    language: str = Field(
        default="bash",
        description="Command language: bash, sh, pwsh, powershell, ps1, or python",
    )
    use_virtualenv: bool = Field(
        default=False,
        description="Whether to create & use a venv in the working directory.",
    )


async def run_shell_tool(input: ShellCommandInput):
    """Executes a shell command dynamically with a user-specified working directory."""
    work_dir_path = Path(input.work_dir)
    work_dir_path.mkdir(exist_ok=True)

    # ✅ Construct the command to first change the directory, then run the command
    resolved_work_dir = work_dir_path.resolve()
    print('working dir', [str(resolved_work_dir)])

    # Handle different shell syntaxes for changing directories
    if input.language.lower() in ['powershell', 'pwsh', 'ps1']:
        full_command = f"Set-Location -Path '{str(resolved_work_dir)}'; {input.command}"
    else: # Default to bash syntax
        full_command = f"cd '{str(resolved_work_dir)}' && {input.command}"

    venv_context = None
    if input.use_virtualenv:
        venv_dir = resolved_work_dir / ".venv"
        venv_builder = venv.EnvBuilder(with_pip=True)
        venv_builder.create(venv_dir)
        venv_context = venv_builder.ensure_directories(venv_dir)

    # The executor can be initialized without work_dir since we already changed it in the command string
    executor = LocalCommandLineCodeExecutor(virtual_env_context=venv_context)
    # await executor.start()

    result = await executor.execute_code_blocks(
        code_blocks=[CodeBlock(language=input.language, code=full_command)],
        cancellation_token=CancellationToken(),
    )

    # await executor.stop()

    return {
        "work_dir": str(resolved_work_dir),
        "stdout": result.output,
        "exit_code": result.exit_code,
        "success": result.exit_code == 0,
    }

# ✅ Wrap in FunctionTool
shell_command_tool = FunctionTool(
    func=run_shell_tool,
    description="Executes dynamic shell commands in a specified working directory (supports venv).",
    # input_model=ShellCommandInput,
)


# ✅ Example standalone usage
async def main():
    result = await run_shell_tool(
    ShellCommandInput(
        command="mkdir akesh",
        work_dir="./dev_workdir",
        language="powershell"
    )
)

    # print(result)

    print("\n🔧 Command Output:", result)


if __name__ == "__main__":
    asyncio.run(main())

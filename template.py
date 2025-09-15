import os
import sys

def create_project_structure(project_name):
    """
    Creates the directory structure for the new Java application,
    inspired by the reference application.
    """
    if os.path.exists(project_name):
        print(f"Error: Directory '{project_name}' already exists.")
        return

    print(f"Creating project structure for '{project_name}'...")

    # --- Root Directories ---
    base_dirs = [
        "src/com/nseit/generic/action",
        "src/com/nseit/generic/dao/impl",
        "src/com/nseit/generic/models",
        "src/com/nseit/generic/service/impl",
        "src/com/nseit/generic/util/interceptor",
        "src/com/nseit/generic/util/validation",
        "WebContent/WEB-INF/admin",
        "WebContent/WEB-INF/agency",
        "WebContent/WEB-INF/candidate",
        "WebContent/WEB-INF/Jasper",
        "WebContent/WEB-INF/lib",
        "WebContent/WEB-INF/otbs",
        "WebContent/WEB-INF/security",
        "WebContent/WEB-INF/uploads",
        "WebContent/WEB-INF/util",
        "WebContent/CSS",
        "WebContent/js",
        "WebContent/Images",
        "WebContent/fonts",
        "resource",
        "test"
    ]

    for dir_path in base_dirs:
        os.makedirs(os.path.join(project_name, dir_path))

    print("Project directory structure created successfully.")

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python template.py <project_name>")
        sys.exit(1)
    
    project_name = sys.argv[1]
    create_project_structure(project_name)
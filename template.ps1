# PowerShell Script: create_maven_structure.ps1
# Creates a Maven-style directory structure for iiiexams project

# Change this to your desired project root
$root = "C:\Users\akliv\OneDrive - NEXASPRINT TECHNOLOGIES PRIVATE LIMITED\Desktop\POCS\NSEIT-CODE-MIGRATION-Development\IIIRegistrationPortal"

# Modules
$modules = @(
    "generic", "users", "candidates", "payments", "reports",
    "batches", "branches", "scheduler", "masterdata", "notifications"
)

# Standard subfolders for each module
$moduleSubDirs = @(
    "action",
    "dao\impl",
    "model",
    "models",
    "queries",
    "service\impl",
    "util"
)

# Extra subfolders for generic/util
$genericUtilSubDirs = @(
    "aware",
    "emailsms",
    "interceptor",
    "resource",
    "validation"
)

# Test subfolders (under test/java/com/nseit)
$testSubDirs = $moduleSubDirs  # mirror module structure in tests

# WebContent / webapp folders
$webappFolders = @(
    "WEB-INF\admin",
    "WEB-INF\agency",
    "WEB-INF\candidate",
    "WEB-INF\Jasper",
    "WEB-INF\lib",
    "WEB-INF\otbs",
    "WEB-INF\security",
    "WEB-INF\uploads",
    "WEB-INF\util",
    "css\fonts",
    "css\ui-lightness\images",
    "fonts",
    "images",
    "js\bower_components\pdfmake\build",
    "js\candidateJS",
    "js\contrib",
    "js\languages",
    "webfonts"
)

# Function to create folder
function Create-Folder($path) {
    if (-not (Test-Path $path)) {
        New-Item -ItemType Directory -Force -Path $path | Out-Null
    }
}

Write-Host " Creating Maven-style iiiexams project structure..."

# Create main folders
Create-Folder "$root\src\main\java\com\nseit"
Create-Folder "$root\src\main/resources"
Create-Folder "$root\src\main/webapp"
Create-Folder "$root\test\java\com\nseit"

# Create module folders under src/main/java
foreach ($module in $modules) {
    foreach ($sub in $moduleSubDirs) {
        if ($module -eq "generic" -and $sub -eq "util") {
            foreach ($utilSub in $genericUtilSubDirs) {
                Create-Folder "$root\src\main\java\com\nseit\$module\$sub\$utilSub"
            }
        } else {
            Create-Folder "$root\src\main\java\com\nseit\$module\$sub"
        }
    }
}

# Create module folders under test/java
foreach ($module in $modules) {
    foreach ($sub in $moduleSubDirs) {
        Create-Folder "$root\test\java\com\nseit\$module\$sub"
    }
}

# Create webapp folders
foreach ($folder in $webappFolders) {
    Create-Folder "$root\src\main\webapp\$folder"
}

# Create resources/queries folder
Create-Folder "$root\src\main/resources/queries"

# Create pom.xml file placeholder
$pomPath = "$root\pom.xml"
if (-not (Test-Path $pomPath)) {
    Set-Content -Path $pomPath -Value "<project><!-- Add your Maven dependencies here --></project>"
}

Write-Host " Maven-style directory structure created successfully at $root"

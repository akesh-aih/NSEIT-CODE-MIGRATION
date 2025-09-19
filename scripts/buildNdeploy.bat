@echo off
setlocal

REM === CONFIGURE THESE PATHS ===
set MAVEN_PATH=mvn
set PROJECT_DIR=C:\Users\akliv\OneDrive - NEXASPRINT TECHNOLOGIES PRIVATE LIMITED\Desktop\POCS\NSEIT-CODE-MIGRATION-Development\IIIRegistrationPortal
set TOMCAT_WEBAPPS=C:\Program Files\Apache Software Foundation\Tomcat 10.1\webapps
set WAR_NAME=iiiexams.war

echo ==========================
echo Cleaning and Installing...
echo ==========================
cd /d "%PROJECT_DIR%"

REM Run Maven inside CMD explicitly
cmd /c "%MAVEN_PATH% clean install -U"

IF %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Maven build failed!
    pause
    exit /b %ERRORLEVEL%
)

echo ==========================
echo Cleaning old deployment...
echo ==========================
if exist "%TOMCAT_WEBAPPS%\iiiexams" (
    echo Deleting exploded folder: %TOMCAT_WEBAPPS%\iiiexams
    rmdir /S /Q "%TOMCAT_WEBAPPS%\iiiexams"
)
if exist "%TOMCAT_WEBAPPS%\%WAR_NAME%" (
    echo Deleting old WAR: %TOMCAT_WEBAPPS%\%WAR_NAME%
    del /Q "%TOMCAT_WEBAPPS%\%WAR_NAME%"
)

echo ==========================
echo Copying WAR to Tomcat...
echo ==========================
echo From: "%PROJECT_DIR%\target\%WAR_NAME%"
echo To:   "%TOMCAT_WEBAPPS%\%WAR_NAME%"
copy /Y "%PROJECT_DIR%\target\%WAR_NAME%" "%TOMCAT_WEBAPPS%\%WAR_NAME%"

IF %ERRORLEVEL% EQU 0 (
    echo [SUCCESS] Deployment completed!
) ELSE (
    echo [ERROR] Failed to copy WAR file.
)

pause
endlocal

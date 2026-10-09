@if "%DEBUG%" == "" @echo off
@rem Gradle Wrapper Script untuk Windows

setlocal enabledelayedexpansion

set GRADLE_VERSION=8.5
set GRADLE_SHA256=e92b2badbe96ca0e21d5c5e93076c1227f72ac6cd7f4f06a8f2b1eaa1dd59daa

if not exist "gradle/wrapper/gradle-%GRADLE_VERSION%-bin.zip" (
    echo Downloading Gradle %GRADLE_VERSION%...
    powershell -Command "Invoke-WebRequest -Uri 'https://services.gradle.org/distributions/gradle-%GRADLE_VERSION%-bin.zip' -OutFile 'gradle/wrapper/gradle-%GRADLE_VERSION%-bin.zip'"
)

@rem Execute gradle
@rem Use '@' to silently run the command
cd /d "%~dp0"
if "%JAVA_HOME%"==\"\" (
    echo Error: JAVA_HOME is not set and no 'java' command could be found in your PATH.
    exit /b 1
)

for /f \"tokens=*\" %%i in ('powershell -Command "[System.IO.Path]::Combine('.', 'gradle', 'wrapper', 'gradle.bat')"') do set GRADLE_WRAPPER=%%i

if exist "%GRADLE_WRAPPER%" (
    "%GRADLE_WRAPPER%" %*
) else (
    echo Gradle wrapper not found. Installing...
    mkdir gradle\wrapper 2>nul
    powershell -Command "Invoke-WebRequest -Uri 'https://services.gradle.org/distributions/gradle-8.5-bin.zip' -OutFile 'gradle/wrapper/gradle-8.5-bin.zip'"
    powershell -Command "Expand-Archive -Path 'gradle/wrapper/gradle-8.5-bin.zip' -DestinationPath 'gradle/wrapper'"
    gradle/wrapper/gradle-8.5/bin/gradle.bat %*
)

endlocal

@echo off
setlocal enabledelayedexpansion

set "MVN_VERSION=3.9.9"
set "ROOT_DIR=%~dp0"
set "MAVEN_DIR=%ROOT_DIR%.mvn\apache-maven-%MVN_VERSION%"
set "MAVEN_CMD=%MAVEN_DIR%\bin\mvn.cmd"
set "DIST_DIR=%ROOT_DIR%.mvn\dist"
set "ZIP_FILE=%DIST_DIR%\apache-maven-%MVN_VERSION%-bin.zip"
set "DOWNLOAD_URL=https://archive.apache.org/dist/maven/maven-3/%MVN_VERSION%/binaries/apache-maven-%MVN_VERSION%-bin.zip"

set "JAVA_HOME_VALID="
if defined JAVA_HOME (
  if exist "%JAVA_HOME%\bin\java.exe" set "JAVA_HOME_VALID=1"
)

if not defined JAVA_HOME_VALID (
  if exist "C:\Program Files\Java\jdk-21\bin\java.exe" (
    set "JAVA_HOME=C:\Program Files\Java\jdk-21"
    set "JAVA_HOME_VALID=1"
  )
)

if not defined JAVA_HOME_VALID (
  if exist "C:\Program Files\Java\jdk-17\bin\java.exe" (
    set "JAVA_HOME=C:\Program Files\Java\jdk-17"
    set "JAVA_HOME_VALID=1"
  )
)

if not defined JAVA_HOME_VALID (
  echo JAVA_HOME is not defined and no local JDK fallback was found 1>&2
  exit /b 1
)

if exist "%MAVEN_CMD%" goto run

if not exist "%DIST_DIR%" mkdir "%DIST_DIR%"

powershell -NoLogo -NoProfile -ExecutionPolicy Bypass -Command ^
  "$ErrorActionPreference='Stop';" ^
  "$version='%MVN_VERSION%';" ^
  "$root='%ROOT_DIR%';" ^
  "$dist=Join-Path $root '.mvn\\dist';" ^
  "$zip=Join-Path $dist ('apache-maven-' + $version + '-bin.zip');" ^
  "$downloadUrl='https://archive.apache.org/dist/maven/maven-3/' + $version + '/binaries/apache-maven-' + $version + '-bin.zip';" ^
  "$extractRoot=Join-Path $root '.mvn';" ^
  "New-Item -ItemType Directory -Force -Path $dist | Out-Null;" ^
  "Invoke-WebRequest -Uri $downloadUrl -OutFile $zip;" ^
  "Expand-Archive -Path $zip -DestinationPath $extractRoot -Force;" ^
  "Remove-Item $zip -Force;"

if not exist "%MAVEN_CMD%" (
  echo No se pudo preparar Maven 1>&2
  exit /b 1
)

:run
"%MAVEN_CMD%" %*
exit /b %ERRORLEVEL%
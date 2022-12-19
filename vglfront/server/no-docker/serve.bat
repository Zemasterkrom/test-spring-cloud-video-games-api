@ECHO OFF
SETLOCAL ENABLEDELAYEDEXPANSION
SET FRONT_SERVER_PORT=4200

type NUL >> .env
if %errorlevel% neq 0 exit /b %errorlevel%

FOR /F "tokens=* eol=#" %%i IN ('type .env') DO (
    SET %%i
    if !errorlevel! neq 0 exit /b !errorlevel!
)

javac server/FileEnvironmentConfigurator.java
if %errorlevel% neq 0 exit /b %errorlevel%

java -cp .. vglfront.server.FileEnvironmentConfigurator src/assets/environment.js @JSKEY@ @JSVALUE@ "window['environment']['@JSKEY@'] = '@JSVALUE@';" url %API_URL% http://localhost:8080
if %errorlevel% neq 0 exit /b %errorlevel%

ng serve --port %FRONT_SERVER_PORT%
ENDLOCAL
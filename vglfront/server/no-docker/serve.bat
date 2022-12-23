@ECHO OFF
SETLOCAL ENABLEDELAYEDEXPANSION
SET FRONT_SERVER_PORT=4200
SET API_URL=http://localhost:8080
TYPE NUL >> .env
IF %errorlevel% NEQ 0 EXIT /b %errorlevel%

FOR /F "tokens=* eol=#" %%i IN ('type .env') DO (
    SET %%i
    IF !errorlevel! NEQ 0 EXIT /b !errorlevel!
)

javac server/FileEnvironmentConfigurator.java
IF %errorlevel% NEQ 0 EXIT /b %errorlevel%

java -cp .. vglfront.server.FileEnvironmentConfigurator src/assets/environment.js @JSKEY@ @JSVALUE@ "window['environment']['@JSKEY@'] = '@JSVALUE@';" url %API_URL% http://localhost:8080
IF %errorlevel% NEQ 0 EXIT /b %errorlevel%

echo Waiting for %API_URL% to be up ...

SET API_URL=%API_URL:http://=http-get://%
SET API_URL=%API_URL:https://=https-get://%
Echo.%API_URL%| findstr /R /C:"^http-get://[a-Z0-9]*$" /C:"^https-get://[a-Z0-9]*$">NUL & IF %errorlevel% NEQ 0 (
    SET API_URL=http-get://%API_URL%
)
wait-on %API_URL%/video-games/all -t 400000 -i 10000 && ng serve --port %FRONT_SERVER_PORT%
ENDLOCAL
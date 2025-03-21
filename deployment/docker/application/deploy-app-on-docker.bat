@echo off

copy ..\..\..\build\libs\hello-boot-1.0.war .
if %errorlevel% neq 0 goto error

docker build -t hello-boot:1.0 .
if %errorlevel% neq 0 goto error

docker run -d --name hello-boot hello-boot:1.0
if %errorlevel% neq 0 goto error

echo "Done"
exit

error:
del .\*.war
exit /b %errorlevel%


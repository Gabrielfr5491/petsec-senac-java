@echo off
setlocal

cd /d "%~dp0"
java -cp "bin;lib\mysql-connector-j-9.5.0.jar" jdbc.ConnectionFactory

endlocal

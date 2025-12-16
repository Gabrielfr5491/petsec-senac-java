@echo off
setlocal
set BASE=%~dp0
set JAR=%BASE%lib\mysql-connector-j-9.5.0.jar
set JAVAFX=%USERPROFILE%\Downloads\openjfx-21.0.9_windows-x64_bin-sdk\javafx-sdk-21.0.9\lib










endlocal"%JAVA_HOME%\bin\java.exe" --module-path "%JAVAFX%;%BASE%bin" --add-modules javafx.controls,javafx.fxml -cp "%BASE%bin;%JAR%" -m petsec_senac/petsec_senac.PetshopFX)  exit /b 1  pause  echo Copy the driver JAR into lib\ and try again.  echo MySQL connector not found: %JAR%if not exist "%JAR%" (n
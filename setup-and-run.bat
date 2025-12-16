@echo off
setlocal enabledelayedexpansion

echo Verificando JavaFX SDK...
set JAVAFX_PATH=%USERPROFILE%\Downloads\javafx-sdk-21.0.9

if not exist "%JAVAFX_PATH%" (
    echo.
    echo JavaFX SDK nao encontrado em: %JAVAFX_PATH%
    echo.
    echo Para resolver:
    echo 1. Visite: https://gluonhq.com/products/javafx/
    echo 2. Baixe "JavaFX SDK 21.0.9 Windows"
    echo 3. Extraia em: %USERPROFILE%\Downloads\
    echo 4. Renomeie a pasta para "javafx-sdk-21.0.9"
    echo 5. Tente novamente
    echo.
    pause
    exit /b 1
)

if not exist "%JAVAFX_PATH%\lib\glass.dll" (
    echo ERRO: JavaFX SDK esta incompleto (falta glass.dll)
    echo Baixe novamente uma versao completa de: https://gluonhq.com/products/javafx/
    pause
    exit /b 1
)

echo JavaFX SDK encontrado em: %JAVAFX_PATH%
echo Iniciando PetshopFX...
echo.

cd /d "%~dp0"
java -Dprism.order=sw --module-path "%JAVAFX_PATH%\lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.base -cp "bin;lib\mysql-connector-j-9.5.0.jar" petsec_senac.PetshopFX

endlocal

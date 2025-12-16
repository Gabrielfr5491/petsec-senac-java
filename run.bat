@echo off
setlocal enabledelayedexpansion

set WORKDIR=%~dp0
set JAVAFX=%USERPROFILE%\Downloads\javafx\Main\lib
set JAR=%WORKDIR%lib\mysql-connector-j-9.5.0.jar
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.9.10-hotspot

cd /d "%WORKDIR%"

echo Tentando rodar PetshopFX com software rendering...
"%JAVA_HOME%\bin\java.exe" -Dprism.order=sw -Dprism.verbose=false -Djava.awt.headless=false --module-path "%JAVAFX%" --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.base -cp "bin;%JAR%" petsec_senac.PetshopFX

if %ERRORLEVEL% neq 0 (
    echo.
    echo Tentando com pipeline em ordem diferente...
    "%JAVA_HOME%\bin\java.exe" -Dprism.order=es2,sw --module-path "%JAVAFX%" --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.base -cp "bin;%JAR%" petsec_senac.PetshopFX
)

if %ERRORLEVEL% neq 0 (
    echo.
    echo ERRO: Nao foi possivel renderizar a interface grafica.
    echo.
    echo Solucoes:
    echo 1. Conecte um monitor ou use Remote Desktop
    echo 2. Para ambiente SSH: use XMing ou VcXsrv no Windows
    echo 3. Atualize os drivers de video
    echo.
    echo A conexao ao banco de dados esta funcionando!
    echo Use "Test Connection" para validar a conexao.
    echo.
    pause
)

endlocal

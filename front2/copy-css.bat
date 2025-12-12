@echo off
REM Copia o arquivo CSS para a pasta bin
copy src\petshop.css bin\petshop.css
if exist bin\petshop.css (
    echo CSS copiado com sucesso para bin/
) else (
    echo Erro ao copiar CSS
)

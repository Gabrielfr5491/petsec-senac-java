#!/bin/bash
# Script para copiar CSS para a pasta bin (compatível com Windows)

SRC="src/petshop.css"
BIN="bin/petshop.css"

if [ -f "$SRC" ]; then
    cp "$SRC" "$BIN"
    echo "✓ CSS copiado para bin/"
else
    echo "✗ Arquivo CSS não encontrado em $SRC"
fi

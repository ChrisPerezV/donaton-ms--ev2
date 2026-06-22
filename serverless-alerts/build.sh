#!/bin/bash

echo "Iniciando empaquetado de la función Serverless"

rm -f lambda-alertas.zip

zip -r lambda-alertas.zip index.js

echo "Empaquetado exitoso"
echo "Archivo generado: lambda-alertas.zip"
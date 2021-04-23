#!/bin/bash

echo "resultフォルダの読み込むファイル名を拡張子抜きで入力してください"
read inputFileName
java dataAnalyze/DataAnalyze result/$inputFileName.txt > analyze/analyze$inputFileName.txt

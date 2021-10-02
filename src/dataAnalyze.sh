#!/bin/bash

echo "resultフォルダの読み込むファイル名を拡張子抜きで入力してください"
read inputFileName
java dataAnalyze/DataAnalyze data/result/single/$inputFileName.txt > data/result/analyze/analyze$inputFileName.txt

#!/bin/bash

echo "ファイル名を入力"
read fileName
java dataAnalyze/DataAnalyzeForUbertooth data/others/$fileName 9 15 8 0 >data/others/ana$fileName


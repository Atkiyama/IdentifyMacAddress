#bin/bash

#このスクリプトはUbuntuで動かすことを想定しています
echo "ファイル名を入力"
read fileName
timeout 10s python3 scan.py >data/capture/$fileName.txt

#bin/bash

#このスクリプトはUbuntuで動かすことを想定しています
#sudoで実行してください
echo "ファイル名を入力"
read fileName
date > $fileName.txt
echo "パケットキャプチャを開始します"
while true
do
  python3 scan2.py >>data/capture/$fileName.txt
done

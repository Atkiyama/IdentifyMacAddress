#bin/bash

#このスクリプトはUbuntuで動かすことを想定しています
#sudoで実行してください
echo "ファイル名を入力"
read fileName
echo "キャプチャを開始します"
python3 scan.py > data/capture/$fileName.txt
echo "パケットキャプチャが終了しました"

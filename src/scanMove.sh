#bin/bash

#このスクリプトはUbuntuで動かすことを想定しています
#sudoで実行してください
echo "ファイル名を入力"
read fileName
date
echo "キャプチャを開始します"
timeout 1h python3 scan.py > data/capture/single/move/txt/$fileName.txt
echo "パケットキャプチャが終了しました"
date

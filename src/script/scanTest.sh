#bin/bash

#このスクリプトはUbuntuで動かすことを想定しています
#sudoで実行してください
echo "ファイル名を入力"
read fileName
date
echo "キャプチャを開始します"
python3 scan.py > data/capture/ver3/original/$fileName.txt
echo "パケットキャプチャが終了しました"
grep -a 0000fd6f data/capture/ver3/original/$fileName.txt > $fileName.txt
date

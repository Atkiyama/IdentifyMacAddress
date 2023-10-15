#bin/bash


echo "ファイル名を入力"
read fileName
date
echo "キャプチャを開始します"
ubertooth-btle -n> data/others/$fileName.txt
echo "パケットキャプチャが終了しました"
date

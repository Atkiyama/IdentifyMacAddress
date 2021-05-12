#bin/bash

#このスクリプトはUbuntuで動かすことを想定しています
#sudoで実行してください
date
echo "BLEパケットのキャプチャを開始します"
timeout 1h ./scan3.sh
echo "パケットキャプチャが終了しました"

#bin/bash

#このスクリプトはUbuntuで動かすことを想定しています
#sudoで実行してください
date
timeout 1h ./while1h.sh
echo "パケットキャプチャが終了しました"

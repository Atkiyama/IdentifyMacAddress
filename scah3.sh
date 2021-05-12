#bin/bash

#このスクリプトはUbuntuで動かすことを想定しています
#sudoで実行してください
while true
do
  python3 scan2.py >data/capture/$fileName.txt
done

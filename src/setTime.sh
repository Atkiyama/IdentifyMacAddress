#bin/bash

sudo timedatectl set-ntp true
sudo systemctl daemon-reload
sudo systemctl restart systemd-timesyncd.service

#bin/bash

for n in {1..100}
do
  java convert.Convert data/capture/convert/$n,convertData.csv
  if ["$n"%5 = 0]
  then
    echo "$n まで終了"
    sleep 3m
  fi
done

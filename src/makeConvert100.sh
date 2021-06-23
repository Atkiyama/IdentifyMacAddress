#bin/bash

#引数2は手動で書き換え
for n in {1..100}
do
  java convertMacAddress/ConvertMacAddress data/capture/convert/$n,convertData.csv 5
done

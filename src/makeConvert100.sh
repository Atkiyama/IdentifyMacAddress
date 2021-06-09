#bin/bash

for n in {1..100}
do
  java convertMacAddress/ConvertMacAddress data/capture/convert/$n,convertData.csv
done

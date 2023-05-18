#bin/bash

echo "stay or move?"
read isStay
echo "number of Using data ?"
read data
#引数2は手動で書き換え
for n in {1..100}
do
  java convertMacAddress/ConvertMacAddress data/capture/$isStay/convert/$n,convertData.csv $data
done

#bin/bash

#コマンドライン引数

isMove = $1
numOfData = $2
for n in {1..100}
do
  java convertMacAddress/ConvertMacAddress data/capture/convert/$isMove/$numOfData/$n,convertData.csv $numOfData  data/capture/single/$isMove/txt/
done

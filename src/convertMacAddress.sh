#bin/bash

#コマンドライン引数

isMove=$1
for numOfData in {1..20}
do
  for n in {1..100}
  do
    java convertMacAddress/ConvertMacAddress data/capture/convert/$isMove/$numOfData/$n,convertData.csv $numOfData data/capture/single/$isMove/txt/
  done
done

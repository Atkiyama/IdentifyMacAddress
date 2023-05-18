#bin/bash

method=old
for numOfData in 1 2 3 5 10 15 20
do
  for n in {1..100}
  do
    java convertMacAddress/ConvertMacAddress data/capture/convert/move/convert$n.csv $numOfData data/capture/single/move/txt/
  done
  for R in {1..20}
  do
    for T in 6
    do
      for n in {1..100}
      do
        java identifyMacAddress/identify/IdentifyStay data/capture/convert/move/convert$n.csv $R $T > data/result/multi/move/$method/$n.txt
      done
      if [ $R -eq "1" ]; then
        java evaluation/evaluation/Evaluation100Old $R $T $numOfData > data/result/evaluation/move/$method,$numOfData.txt
      else
        java evaluation/evaluation/Evaluation100Old $R $T $numOfData >> data/result/evaluation/move/$method,$numOfData.txt
      fi
    done
  done
done

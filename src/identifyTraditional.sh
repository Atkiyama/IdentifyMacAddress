#bin/bash

method=old
for numOfData in 5 10 15 20
do
  for n in {1..100}
  do
    java convertMacAddress/ConvertMacAddress data/capture/convert/move/convert$n.csv 20 data/capture/single/move/txt/
  done
  for R in {1..20}
  do
    for T in 6
    do
      for n in {1..100}
      do
        java identifyMacAddress/identify/IdentifyStay data/capture/convert/move/convert$n.csv $R $T > data/result/multi/move/$method/$n.txt
      done
      java evaluation/evaluation/Evaluation100Old $R $T $numOfData >> data/result/evaluation/move/$method.txt
    done
  done
done

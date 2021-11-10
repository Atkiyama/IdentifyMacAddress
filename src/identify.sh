#bin/bash

#コマンドライン引数
method=$1
numOfData=$2
for R in {1..20}
do
  for T in 6
  do
    for I in {1..20}
    do
      for n in {1..10000}
      do
        ./identify $R $T $I $numOfData $n $method > data/result/multi/move/10000/$method/$numOfData/$n.txt
      done
      if [ "$R" -eq "1" -a "$I" -eq "1" ]
      then
        java evaluation/evaluation/Evaluation $R $T $I $method $numOfData > data/result/evaluation/move/10000/$method,$numOfData.txt
      else
        java evaluation/evaluation/Evaluation $R $T $I $method $numOfData >> data/result/evaluation/move/10000/$method,$numOfData.txt
      fi
    done
  done
done

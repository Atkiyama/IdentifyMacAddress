#bin/bash


numOfData=$1
method=$2
for R in {1..20}
do
  for T in 6
  do
    for I in {1..20}
    do
      for n in {1..100}
      do
        ./identify $R $T $I $numOfData $n $method >data/result/multi/move/$method/$n.txt
      done
      if [ "$R" -eq "1" -a "$I" -eq "1" ]; then
        echo "R,T,I,score" > data/result/evaluation/move/$numOfTime/$method,$numOfData.csv
      fi
      java evaluation/evaluation/Evaluation $R $T $I $method >> data/result/evaluation/move/$numOfTime/$method,$numOfData.csv
    done
  done
done

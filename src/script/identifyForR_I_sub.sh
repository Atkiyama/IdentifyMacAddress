#bin/bash


numOfData=$1
method=$2
python $method.py
./identify 1 $method
for R in {1..20}
do
  for T in 6
  do
    for I in {1..20}
    do
      if [ "$R" -eq "1" -a "$I" -eq "1" ]; then
        echo "R,T,I,score" > data/result/evaluation/move/$numOfTime/$method,$numOfData.csv
      fi
      java evaluation/evaluation/Evaluation $R $T $I $method >> data/result/evaluation/move/$numOfTime/$method,$numOfData.csv
    done
  done
done

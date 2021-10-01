#bin/bash

for R in {1..20}
do
  for T in {1..20}
  do
    for P in {1..20}
    do
      if [ "$R" -eq "1" -a "$T" -eq "1" -a "$P" -eq "1" ]
      then
        java evaluation/Evaluation100_2 $R $T $numOfData stay >data/result/evaluation/stay/evaluation$numOfData _2.txt
      else
        java evaluation/Evaluation100_2 $R $T $numOfData stay >>data/result/evaluation/stay/evaluation$numOfData _2.txt
      fi
    done
  done
done

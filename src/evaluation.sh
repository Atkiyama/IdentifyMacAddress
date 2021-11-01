#bin/bash


for numOfData in 5 10 15 20
do
  for method in linerRegression svr bagging
  do
    for R in {1..20}
    do
      for T in 5 6
      do
        for I in {1..20}
        do
          if [ "$R" -eq "1" -a "$T" -eq "6" -a "$I" -eq "1" -a "$numOfData" -eq "5" -a]
          then
            java evaluation/evaluation/Evaluation $R $T $I $numOfData $method >data/result/evaluation/move/$method evaluation$numOfData.txt &
          else
            java evaluation/evaluation/Evaluation $R $T $I $numOfData $method >>data/result/evaluation/move/$method evaluation$numOfData.txt &
          fi
        done
      done
    done

  done
done

#bin/bash


for method in linerRegression bagging svr old
do
  for numOfData in {1..20}
  do
    for R in {1..20}
    do
      for T in 6
      do
        for I in {1..20}
        do
          for n in {1..100}
          do
            if [ $method = "old" ]; then
              java identifyMacAddress/identify/IdentifyMove>data/result/multi/move/$method/$n.txt
            else
              ./identify $R $T $I $numOfData $n $method >data/result/multi/move/$method/$n.txt
            fi
          done
          if [ "$R" -eq "1" -a "$I" -eq "1" ]; then
            echo "R,T,I,score" > data/result/evaluation/move/$numOfTime/$method,$numOfData.csv
          fi
          if [ $method = "old" ]; then
            java evaluation/evaluation/Evaluation100Old $R $T $numOfData >> data/result/evaluation/move/$numOfTime/$method,$numOfData.csv
          else
              java evaluation/evaluation/Evaluation $R $T $I $method >> data/result/evaluation/move/$numOfTime/$method,$numOfData.csv
          fi
        done
      done
    done
  done
done

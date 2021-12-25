#bin/bash
#バックグラウンド側で実行しないこと

for method in $1
do
  for R in {1..20}
  do
    for T in 6
    do
      for I in {1..20}
      do
        ./identify $R $T $I 0 $n $method >data/result/multi/move/$method/$n.txt
        if [ "$R" -eq "1" -a "$I" -eq "1" ]; then
          echo "R,T,I,score" > data/result/evaluation/move/$numOfTime/$method,LineUp.csv
        fi
        java evaluation/evaluation/EvaluationForLineUP $R $T $I $method >> data/result/evaluation/move/$numOfTime/$method,LineUP.csv
      done
    done
  done
done

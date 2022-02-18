#bin/bash
#バックグラウンド側で実行しないこと
numOfTime=0
n=0
for method in $1
do
  ./identify 3 $method
  for R in {1..20}
  do
    for T in 6
    do
      for I in {1..20}
      do
        if [ "$R" -eq "1" -a "$I" -eq "1" ]; then
          echo "R,T,I,score" > data/result/evaluation/move/$method,LineUp.csv
        fi
        java evaluation/evaluation/EvaluationForLineUP $R $T $I $method >> data/result/evaluation/move/$method,LineUP.csv
      done
    done
  done
done

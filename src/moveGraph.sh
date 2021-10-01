#コマンドライン引数
numOfData = $1

for parameta1 in R T P
do
  for parameta2 in R T P
  do
    for parameta3 in R T P
    do
      for number2 in {1..20}
      do
        for number3 in {1..20}
        do
          java graph/move/Graph data/result/evaluation/move/evaluation$numOfData.txt $parameta1 $parameta2 $number2 $parameta3 $number3 > $numOdData,$parameta1,$parameta2,$number2,$parameta3,$number3.txt
        done
      done
    done
  done
done

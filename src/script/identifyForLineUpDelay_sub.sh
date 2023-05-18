numOfData=$1
method=$2
delay=$3
python $method.py
./identify 1 $method
for R in {1..20}
do
  for T in 6
  do
    for I in {1..20}
    do
      if [ "$R" -eq "1" -a "$I" -eq "1" ]; then
        echo "R,T,I,score" > data/result/evaluation/move/$numOfTime/$method,lineUp,$numOfData,$delay.csv
      fi
      java evaluation/evaluation/EvaluationForLineUp2 $R $T $I $method >> data/result/evaluation/move/$numOfTime/$method,LineUp,$numOfData,$delay.csv
    done
  done
done

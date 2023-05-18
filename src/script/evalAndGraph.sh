#bin/bash
for numOfData in 5 10 15 20
do
for R in {1..20}
 do
   for T in {1..20}
   do
     if [ "$R" -eq "1" -a "$T" -eq "1" ]
     then
       java evaluation/Evaluation100 $R $T $numOfData >data/result/evaluation$numOfData.txt
     else
       java evaluation/Evaluation100 $R $T $numOfData >>data/result/evaluation$numOfData.txt
     fi
   done
 done


 java evaluation/table/EvaluationTable data/result/evaluation$numOfData.txt data/result/table/evaluationTable$numOfData.csv

done


./graph.sh

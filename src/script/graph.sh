#bin/bash
for M in 5 10 15 20
do
  for parameta in 5 10 15 20
  do
    java graph/stay/Graph data/result/table/evaluationTable$M.csv Rssi $parameta >data/result/graph/data/T$parameta.txt
  done
  gnuplot -e "file='data/result/graph/graph/R$M.pdf'" r.plt
  for parameta in 1 5 10 15 20
  do
    java graph/stay/Graph data/result/table/evaluationTable$M.csv Time $parameta >data/result/graph/data/R$parameta.txt
  done
  gnuplot -e "file='data/result/graph/graph/T$M.pdf'" t.plt
done

java graph/stay/Graph null DataSet max >data/result/graph/data/max.txt
java graph/stay/Graph null DataSet ave >data/result/graph/data/ave.txt

gnuplot -e "file='data/result/graph/graph/dataSet.pdf'" dataSet.plt

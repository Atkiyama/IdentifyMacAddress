#bin/bash
for M in 4
do
  for parameta in 5 10 15 20
  do
    java graph/Graph data/result/table/move/evaluationTable4_2.csv Rssi $parameta >data/result/graph/data/T$parameta.txt
  done
  gnuplot -e "file='data/result/graph/graph/move/R$M.pdf'" r.plt
  for parameta in 1 5 10 15 20
  do
    java graph/Graph data/result/table/Move/evaluationTable4_2.csv Time $parameta >data/result/graph/data/R$parameta.txt
  done
  gnuplot -e "file='data/result/graph/graph/move/T$M.pdf'" t.plt
done

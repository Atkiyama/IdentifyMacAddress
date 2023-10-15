#コマンドライン引数


LS=$(ls data/result/table/move/)

for inputFileName in ${LS}; do
  for threshold in Rssi Time
  do
    for parameta in {1..20}
    do
      java graph/Graph data/result/table/move/$inputFileName $threshold $parameta > data/result/graph/data/$threshold,$parameta.txt
    done
  done
  gnuplot -e "file='data/result/graph/graph/move/R,${inputFileName//.csv/.pdf}'" rFull.plt
  gnuplot -e "file='data/result/graph/graph/move/I,${inputFileName//.csv/.pdf}'" iFull.plt
done

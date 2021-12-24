set term pdfcairo enhanced color font ",16"
#set size 0.9,1

set xlabel font ",24" "Time"
set ylabel font ",24" "RSSI"
set yrange [-60:-100]
set xtics font ",20"
set ytics font ",20"
set xrange [890:920]
set datafile separator ","

set key right bottom font ",24"

set output file

plot \
"data/result/graph/data/65:72:c4:8d:8f:64.csv" using 1:2 lw 6 lc rgb "red" title "変化前のMACアドレス"\
 with lines,\
"data/result/graph/data/79:7e:38:89:ab:4f.csv" using 1:2 lw 1  lc rgb "green" title "変化後のMACアドレス"\
with lines

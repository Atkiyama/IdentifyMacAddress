set term pdfcairo enhanced color font ",16"
#set size 0.9,1

set xlabel "Time"
set ylabel "RSSI"
set yrange [-60:-100]
set xrange [0:800]
set datafile separator ","

set key right bottom

set output file

plot \
"data/result/graph/data/moveL45.txt" using 1:2 lw 1  lc rgb "green" title "moveL45"\
with lines

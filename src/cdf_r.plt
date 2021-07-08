set term pdfcairo enhanced color font ",16"
#set size 0.9,1

set xlabel "Rssi"
set ylabel "CDF"
set yrange [0:1]
set xrange [0:20]
set datafile separator ","

set nokey

set output "data/result/cdf/cdf_r.pdf"
plot \
 "data/result/graph/data/packetRssi.txt" using 2:1 lw 5 lc rgb "green" \
 with lines

set term pdfcairo enhanced color font ",16"
#set size 0.9,1
set datafile separator ","

set colorsequence default
set xlabel "Time"
set ylabel "CDF"
set yrange [0:1]
set xrange [0:25]

set key right bottom

set output "data/result/graph/graph/packetFNum.pdf"
plot \
"data/result/graph/data/lPackets.csv" using 1:2 lw 5 title "end"\
 with lines
#"data/result/graph/data/fPackets.csv"using 1:2 lw 6   title "start"\
#with lines

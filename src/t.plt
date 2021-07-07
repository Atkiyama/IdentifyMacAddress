set term pdfcairo enhanced color font ",16"
#set size 0.9,1

set xlabel "T"
set ylabel "accuracy"
set yrange [0:100]
set xrange [0:20]
set datafile separator ","

set key right bottom

set output file
plot \
"data/result/graph/data/R1.txt" using 1:2 lw 6 lc rgb "red" title "R=1"\
 with lines,\
"data/result/graph/data/R5.txt" using 1:2 lw 6 lc rgb "brown" title "R=5"\
 with lines,\
"data/result/graph/data/R10.txt" using 1:2 lw 6 lc rgb "blue" title "R=10"\
with lines,\
"data/result/graph/data/R15.txt" using 1:2 lw 6 lc rgb "yellow" title "R=15"\
with lines,\
"data/result/graph/data/R20.txt" using 1:2 lw 6 lc rgb "green" title "R=20"\
with lines

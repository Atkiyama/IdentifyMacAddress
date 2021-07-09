set term pdfcairo enhanced color font ",16"
#set size 0.9,1

set xlabel "R"
set ylabel "accuracy"
set yrange [0:100]
set xrange [0:20]
set datafile separator ","

set key right bottom

set output file

plot \
"data/result/graph/data/T5.txt" using 1:2 lw 6 lc rgb "red" title "T=5"\
 with lines,\
"data/result/graph/data/T10.txt" using 1:2 lw 6 dt (1,4) lc rgb "blue" title "T=10"\
with lines,\
"data/result/graph/data/T15.txt" using 1:2 lw 6 dt (5,5) lc rgb "dark-green" title "T=15"\
with lines,\
"data/result/graph/data/T20.txt" using 1:2 lw 6 dt (10,10) lc rgb "green" title "T=20"\
with lines

set term pdfcairo enhanced color font ",16"
#set size 0.9,1

set xlabel "M"
set ylabel "accuracy"
set yrange [0:100]
set xrange[5:20]
set xtics 5
set datafile separator ","

set key right bottom outside

set output file

plot \
"data/result/graph/data/max.txt" using 1:2 lw 6 dt 2 lc rgb "red" title "Max"\
 with linespoint ,\
"data/result/graph/data/ave.txt" using 1:2 lw 6 lc rgb "green" title "Average"\
with linespoint

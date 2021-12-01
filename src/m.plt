set term pdfcairo enhanced color font ",16"
#set size 0.9,1

set colorsequence default
set xlabel "M"
set ylabel "accuracy"
set yrange [0:100]
set xrange [0:20]
set datafile separator ","

set key right bottom

set output data/result/graph/graph/M.pdf

plot \
"data/result/evaluation/move/linerRegression.txt" using 1:2 lw 6   title "linerRegression"\
with lines,\
"data/result/evaluation/move/svr.txt" using 1:2 lw 6   title "svr"\
with lines,\
"data/result/evaluation/move/bagging.txt" using 1:2 lw 6   title "bagging"\
with lines,\
"data/result/evaluation/move/old.txt" using 1:2 lw 6   title "old"\
with lines

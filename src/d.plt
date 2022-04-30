set term pdfcairo enhanced color font ",16"
#set size 0.9,1

set colorsequence default
set xlabel font ",24" "D"
set ylabel font ",24" "accuracy"
set yrange [0:100]
set xrange [0:30]
set datafile separator ","
set xtics font ",20"
set ytics font ",20"

set key right bottom

set output "data/result/graph/graph/move/d.pdf"

plot \
"data/result/evaluation/move/D,linerRegression.txt" using 1:2 lw 6   title "LinerRegression"\
with lines,\
"data/result/evaluation/move/D,svr.txt" using 1:2 lw 6   title "SVR"\
with linespoints,\
"data/result/evaluation/move/D,bagging.txt" using 1:2 lw 6   title "Bagging"\
with points,\
"data/result/evaluation/move/D,randomForest.txt" using 1:2 lw 6   title "RandomForest"\
with linespoints,\
"data/result/evaluation/move/D,old.txt" using 1:2 lw 6   title "Traditional"\
with lines

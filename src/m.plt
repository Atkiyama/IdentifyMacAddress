set term pdfcairo enhanced color font ",16"
#set size 0.9,1

set colorsequence default
set xlabel font ",24" "M"
set ylabel font ",24" "accuracy"
set yrange [0:100]
set xrange [0:20]
set datafile separator ","
set xtics font ",20"
set ytics font ",20"

set key right bottom

set output "data/result/graph/graph/move/m.pdf"

plot \
"data/result/evaluation/move/M,distance.txt" using 1:2 lw 6   title "Compared Method"\
with linespoints,\
"data/result/evaluation/move/M,old.txt" using 1:2 lw 6   title "Average"\
with linespoints,\
"data/result/evaluation/move/M,linerRegression.txt" using 1:2 lw 6   title "Liner Regression"\
with linespoints,\
"data/result/evaluation/move/M,svr.txt" using 1:2 lw 6   title "Support Vector Regression"\
with linespoints,\
"data/result/evaluation/move/M,randomForest.txt" using 1:2 lw 6   title "Random Forest"\
with linespoints
#"data/result/evaluation/move/M,bagging.txt" using 1:2 lw 6   title "Bagging"\
#with points,\

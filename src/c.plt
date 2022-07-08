set term pdfcairo enhanced color font ",16"
#set size 0.9,1

set colorsequence default
set xlabel font ",24" "C"
set ylabel font ",24" "accuracy"
set yrange [0:100]
set xrange [0:79]
set datafile separator ","
set xtics font ",20"
set ytics font ",20"

set key right

set output "data/result/graph/graph/move/c.pdf"

plot \
"data/result/evaluation/move/C,old.txt" using 1:2 lw 6   title "Average"\
with lines,\
"data/result/evaluation/move/C,timeDifference.txt" using 1:2 lw 6   title "Time Difference"\
with lines,\
"data/result/evaluation/move/C,linerRegression.txt" using 1:2 lw 6   title "Liner Regression"\
with lines,\
"data/result/evaluation/move/C,svr.txt" using 1:2 lw 6   title "Support Vector Regression"\
with lines,\
"data/result/evaluation/move/C,randomForest.txt" using 1:2 lw 6   title "Traditional Random Forest"\
with lines
#"data/result/evaluation/move/M,bagging.txt" using 1:2 lw 6   title "Bagging"\
#with points,\

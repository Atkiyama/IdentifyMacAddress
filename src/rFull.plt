set term pdfcairo enhanced color font ",16"
#set size 0.9,1

set colorsequence default
set xlabel "R"
set ylabel "accuracy"
set yrange [0:100]
set xrange [0:20]
set datafile separator ","

set key below

set output file

plot \
"data/result/graph/data/Time,1.txt" using 1:2 lw 6   title "I=1"\
with lines,\
"data/result/graph/data/Time,2.txt" using 1:2 lw 6   title "I=2"\
with lines,\
"data/result/graph/data/Time,3.txt" using 1:2 lw 6   title "I=3"\
with lines,\
"data/result/graph/data/Time,4.txt" using 1:2 lw 6   title "I=4"\
with lines,\
"data/result/graph/data/Time,5.txt" using 1:2 lw 6   title "I=5"\
with lines,\
"data/result/graph/data/Time,6.txt" using 1:2 lw 6   title "I=6"\
with lines,\
"data/result/graph/data/Time,7.txt" using 1:2 lw 6   title "I=7"\
with lines,\
"data/result/graph/data/Time,8.txt" using 1:2 lw 6   title "I=8"\
with lines,\
"data/result/graph/data/Time,9.txt" using 1:2 lw 6   title "I=9"\
with lines,\
"data/result/graph/data/Time,10.txt" using 1:2 lw 6   title "I=10"\
with lines,\
"data/result/graph/data/Time,11.txt" using 1:2 lw 6   title "I=11"\
with lines,\
"data/result/graph/data/Time,12.txt" using 1:2 lw 6   title "I=12"\
with lines,\
"data/result/graph/data/Time,13.txt" using 1:2 lw 6   title "I=13"\
with lines,\
"data/result/graph/data/Time,14.txt" using 1:2 lw 6   title "I=14"\
with lines,\
"data/result/graph/data/Time,15.txt" using 1:2 lw 6   title "I=15"\
with lines,\
"data/result/graph/data/Time,16.txt" using 1:2 lw 6   title "I=16"\
with lines,\
"data/result/graph/data/Time,17.txt" using 1:2 lw 6   title "I=17"\
with lines,\
"data/result/graph/data/Time,18.txt" using 1:2 lw 6   title "I=18"\
with lines,\
"data/result/graph/data/Time,19.txt" using 1:2 lw 6   title "I=19"\
with lines,\
"data/result/graph/data/Time,20.txt" using 1:2 lw 6   title "I=20"\
with lines

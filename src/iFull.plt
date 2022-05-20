set term pdfcairo enhanced color font ",16"
#set size 0.9,1

set colorsequence default
set xlabel "I"
set ylabel "accuracy"
set yrange [0:100]
set xrange [0:20]
set datafile separator ","

set key below

set output file

plot \
"data/result/graph/data/Rssi,1.txt" using 1:2 lw 6   title "R=1"\
with lines,\
"data/result/graph/data/Rssi,2.txt" using 1:2 lw 6   title "R=2"\
with lines,\
"data/result/graph/data/Rssi,3.txt" using 1:2 lw 6   title "R=3"\
with lines,\
"data/result/graph/data/Rssi,4.txt" using 1:2 lw 6   title "R=4"\
with lines,\
"data/result/graph/data/Rssi,5.txt" using 1:2 lw 6   title "R=5"\
with lines,\
"data/result/graph/data/Rssi,6.txt" using 1:2 lw 6   title "R=6"\
with lines,\
"data/result/graph/data/Rssi,7.txt" using 1:2 lw 6   title "R=7"\
with lines,\
"data/result/graph/data/Rssi,8.txt" using 1:2 lw 6   title "R=8"\
with lines,\
"data/result/graph/data/Rssi,9.txt" using 1:2 lw 6   title "R=9"\
with lines,\
"data/result/graph/data/Rssi,10.txt" using 1:2 lw 6   title "R=10"\
with lines,\
"data/result/graph/data/Rssi,11.txt" using 1:2 lw 6   title "R=11"\
with lines,\
"data/result/graph/data/Rssi,12.txt" using 1:2 lw 6   title "R=12"\
with lines,\
"data/result/graph/data/Rssi,13.txt" using 1:2 lw 6   title "R=13"\
with lines,\
"data/result/graph/data/Rssi,14.txt" using 1:2 lw 6   title "R=14"\
with lines,\
"data/result/graph/data/Rssi,15.txt" using 1:2 lw 6   title "R=15"\
with lines,\
"data/result/graph/data/Rssi,16.txt" using 1:2 lw 6   title "R=16"\
with lines,\
"data/result/graph/data/Rssi,17.txt" using 1:2 lw 6   title "R=17"\
with lines,\
"data/result/graph/data/Rssi,18.txt" using 1:2 lw 6   title "R=18"\
with lines,\
"data/result/graph/data/Rssi,19.txt" using 1:2 lw 6   title "R=19"\
with lines,\
"data/result/graph/data/Rssi,20.txt" using 1:2 lw 6 title "R=20"\
with lines

set term pdfcairo enhanced color font ",16"
#set size 0.9,1

set xlabel "T"
set ylabel "accuracy"
set yrange [0:1]
set xrange [0:100]

set key right bottom

set output file.
plot \
"R1.txt" using 1:2 lw 6 lc rgb "red" title "R=1"\
 with lines,\
"R5.txt" using 1:2 lw 6 lc rgb "red" title "R=5"\
 with lines,\
"R10.txt" using 1:2 lw 6 lc rgb "blue" title "R=10"\
with lines,\
"R15.txt" using 1:2 lw 6 lc rgb "yellow" title "R=15"\
with lines,\
"R20.txt" using 1:2 lw 6 lc rgb "green" title "R=20"\
with lines

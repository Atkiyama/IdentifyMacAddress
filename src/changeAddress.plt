set term pdfcairo enhanced color font ",16"
#set size 0.9,1

set xlabel font ",20" "Time"
set ylabel font ",20" "RSSI"
set yrange [-60:-100]
set xtics font ",20"
set ytics font ",20"
set xrange [3250:3270]
set datafile separator ","

set key right bottom font ",20"

set output "data/result/graph/graph/changeAddress5.pdf"

plot \
"data/address/processed/lAddress/7d:00:82:53:61:25_0.csv" using 1:2 lw 6 lc rgb "red" title "変化前のMACアドレス"\
with linespoints,\
"data/address/processed/regression/approximate/7d:00:82:53:61:25_5_0.csv" using 1:2 lw 6 lc rgb "blue" title "変化前のMACアドレスの回帰結果"\
with linespoints,\
"data/address/processed/fAddress/70:08:e6:e0:5f:50_0.csv" using 1:2 lw 6 lc rgb "black" title "実際の変化後のMACアドレス"\
with linespoints,\
"data/address/processed/fAddress/7e:d0:46:8c:24:3d_0.csv" using 1:2 lw 1  lc rgb "green" title "誤判定した変化後のMACアドレス"\
with linespoints

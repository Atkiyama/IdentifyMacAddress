set term pdfcairo enhanced color font ",16"
#set size 0.9,1

set xlabel font ",18" "Time"
set ylabel font ",20" "RSSI"
set yrange [-70:-95]
set xtics font ",20"
set ytics font ",20"
set xrange [0:15]
set datafile separator ","

set key right below font ",18"

set output "data/result/graph/graph/changeAddress.pdf"

plot \
"data/address/processed/lAddress/7f:a9:2a:14:12:f4_1.csv" using 1:2 lw 6 lc rgb "red" title "MACアドレスA"\
with linespoints,\
"data/address/processed/lAddress/72:f3:00:18:24:2d_1.csv" using 1:2 lw 6 lc rgb "green" title "MACアドレスB"\
with linespoints,\
"data/address/processed/fAddress/78:f4:32:4c:9c:80_2_1.csv" using 1:2 lw 1  lc rgb "red" title "MACアドレスC"\
with linespoints,\
"data/address/processed/fAddress/74:bd:6d:a4:14:1c_2_1.csv" using 1:2 lw 1  lc rgb "green" title "MACアドレスD"\
with linespoints

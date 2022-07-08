set term pdfcairo enhanced color font ",16"
#set size 0.9,1

set xlabel font ",20" "Time"
set ylabel font ",20" "RSSI"
set yrange [-30:-150]
set xtics font ",20"
set ytics font ",20"
set xrange [-10:10]
set datafile separator ","

set key right bottom font ",20"

set output "data/result/graph/graph/changeAddressAllocation.pdf"

plot \
"data/address/processed/lAddress/7d:ae:67:19:51:03_5.csv" using 1:2 lw 6 lc rgb "red" title "変化前のMACアドレス"\
with linespoints,\
"data/address/processed/regression/svr/7d:ae:67:19:51:03_5_5.csv" using 1:2 lw 6 lc rgb "blue" title "変化前のMACアドレスの回帰結果"\
with linespoints,\
"data/address/processed/fAddress/4d:69:aa:69:53:1f_2_4.csv" using 1:2 lw 1  lc rgb "green" title "変化後のMACアドレス"\
with linespoints
#"data/address/processed/fAddress/70:08:e6:e0:5f:50_0.csv" using 1:2 lw 6 lc rgb "black" title "実際の変化後のMACアドレス"\
#with linespoints,\


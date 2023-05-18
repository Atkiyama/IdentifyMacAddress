set term pdfcairo enhanced color font ",16"
#set size 0.9,1

set xlabel font ",18" "Time"
set ylabel font ",20" "RSSI"
set yrange [-70:-95]
set xtics font ",20"
set ytics font ",20"
set xrange [2138:2148]
set datafile separator ","

set key right below font ",18"

set output "data/result/graph/graph/exampleRegression.pdf"

plot \
"data/address/processed/lAddress/58:fd:d2:1d:88:9b_1.csv" using 1:2 lw 6 lc rgb "red" title "変化前のMACアドレス"\
with lines,\
"data/address/processed/regression/svr/58:fd:d2:1d:88:9b_15_1.csv" using 1:2 lw 6 lc rgb "blue" title "SVR(poly)の回帰結果"\
with lines,\
"data/address/processed/regression/svr/rbf_58:fd:d2:1d:88:9b_15_1.csv" using 1:2 lw 6 lc rgb "green" title "SVR(rbf)の回帰結果"\
with lines,\
"data/address/processed/regression/randomForest/58:fd:d2:1d:88:9b_15_1.csv" using 1:2 lw 6 lc rgb "dark-pink" title "RandomForestの回帰結果"\
with lines,\
"data/address/processed/fAddress/6c:d5:22:c7:f7:70_1.csv" using 1:2 lw 6 lc rgb "brown" title "変化後のMACアドレス"\
with lines



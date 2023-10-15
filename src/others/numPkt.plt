set terminal pngcairo enhanced font "arial,10" fontscale 1.0 size 600, 400
set output 'bar_graph.png'

# データのラベルと値を読み込む
data = "data.txt"
labels = system("awk '{print $1}' " . data)
values = system("awk '{print $2}' " . data)

# データの個数を取得する
stats data using 2 nooutput

# グラフのスタイルを設定する
set style fill solid
set boxwidth 0.5

# X軸の設定
set xtics rotate out
set xtics border in scale 0,0
set xtics nomirror
set xtics format ""

# Y軸の設定
set yrange [0:*]
set ytics 5
set ylabel "Value"

# グラフのプロット
plot data using (column(0)+0.5):2:xtic(1) with boxes lc rgb "blue" title "Data"


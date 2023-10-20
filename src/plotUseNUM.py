import matplotlib.pyplot as plt
import csv

# CSVファイルの読み込みとデータの取得
def read_csv(file_path):
    x_values = []
    y_values = []
    with open(file_path, 'r') as csvfile:
        csv_reader = csv.reader(csvfile)
        for row in csv_reader:
            x_values.append(int(row[0]))
            y_values.append(float(row[1]))
    return x_values, y_values

# 3つのCSVファイルからデータを読み込む
x_values1, y_values1 = read_csv('data/result/evaluation/ver3/actual/timeDiff.csv')
x_values2, y_values2 = read_csv('data/result/evaluation/ver3/actual/liner.csv')
x_values3, y_values3 = read_csv('data/result/evaluation/ver3/actual/combine_liner_dist.csv')

# プロット
plt.figure(figsize=(8, 6))

# データ1のプロット
plt.plot(x_values1, y_values1, marker='o', color='b', linestyle='-', linewidth=2, markersize=8, label='timediff')

# データ2のプロット
plt.plot(x_values2, y_values2, marker='s', color='g', linestyle='-', linewidth=2, markersize=8, label='liner')

# データ3のプロット
plt.plot(x_values3, y_values3, marker='^', color='r', linestyle='-', linewidth=2, markersize=8, label='timediff+liner*0.75')

plt.title('3つのCSVデータのプロット')
plt.xlabel('NUMofData')
plt.ylabel('accuracy')
plt.legend()  # 凡例を表示
plt.grid(True)
plt.show()

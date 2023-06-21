import matplotlib.pyplot as plt
import japanize_matplotlib
import csv
import matplotlib

print(matplotlib.get_configdir())

# CSVファイルからデータを読み込む
data = []
with open('data/result/graph/data/numPkt.csv', 'r') as file:
    reader = csv.reader(file)
    for row in reader:
        data.append([int(row[0]), int(row[1])])

# データをリストに分割
labels = [row[0] for row in data]
values = [row[1] for row in data]

# グラフの作成
plt.figure(figsize=(10, 8))  # グラフのサイズを調整
plt.xlabel("パケット数", fontsize=30)  # x軸のラベルのフォントサイズを大きく
plt.ylabel("データ数", fontsize=30)  # y軸のラベルのフォントサイズを大きく
plt.xticks(ha='right', fontsize=25)  # x軸のラベルを45度回転して表示
plt.yticks(fontsize=25)  # y軸の目盛りのフォントサイズを変更
plt.bar(labels, values)
plt.xlim(0, 12)  # X軸の範囲を指定
plt.ylim(0, 35)  # Y軸の範囲を指定
plt.title("")  # タイトルを削除

# グラフをPDFファイルに保存
plt.savefig('data/result/graph/graph/numPkt.pdf')

# グラフの表示
# plt.show()

import sys
import pandas as pd
import numpy as np

#コマンドライン引数 1 読み込むファイル 2前アドレス 3類似度を出したいアドレス 4閾値R 5使用する手法
args = sys.argv
capture = pd.read_csv(args[1], sep=",")
baseAddress = args[2]
testAddress = args[3]
R = int(args[4])

#学習用データ作成
x = []
x_train = []
y = []
y_train = []
for line in range(len(capture)):
    if capture.address[line] == baseAddress:
        x.append(capture.time[line])
        y.append(capture.rssi[line])


for num in range(len(x)):
    if x[len(x)-1] - x[num] <= R:
        x_train.append(x[num])
        y_train.append(y[num])

#モデル生成
from sklearn.linear_model import LinearRegression
clf = LinearRegression()
clf.fit(pd.DataFrame(x_train),y_train)

#テストデータ作成
x = []
y = []
x_test = []
y_test = []
for line in range(len(capture)):
    if capture.address[line] == testAddress:
        x.append(capture.time[line])
        y.append(capture.rssi[line])

for num in range(len(x)):
    if x[num] - x[0] <= R:
        x_test.append(x[num])
        y_test.append(y[num])

f = open('data/result/identifyRssi.txt', 'w')
f.write(str(np.average(clf.predict(pd.DataFrame(x_test)))))
f.close()

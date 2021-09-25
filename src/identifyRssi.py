import sys
import pandas as pd
import numpy as np

#コマンドライン引数 1 読み込むファイル 2前アドレス 3類似度を出したいアドレス 4閾値R 5使用する手法
args = sys.argv
capture = pd.read_csv(args[1], sep=",")
baseAddress = args[2]
testAddress = args[3]
R = int(args[4])

#テストデータ作成

x_train = []
y = []
y_train = []
for line in range(len(capture)):
    if capture.address[line] == baseAddress:
        x_train.append(capture.time[line])
        y.append(capture.rssi[line])


for num in range(len(y)):
    if len(y) - num <= R:
        y_train.append(y[num])


#モデル生成
from sklearn.linear_model import LinearRegression
clf = LinearRegression()
clf.fit(pd.DataFrame(x_train).tail(R),y_train)

#テストデータ作成
x_test = []
y_test = []
for line in range(len(capture)):
    if capture.address[line] == testAddress and len(x_test) < R:
        x_test.append(capture.time[line])
        y_test.append(capture.rssi[line])

#実際に回帰する
import time
time.sleep(5)
for result in clf.predict(pd.DataFrame(x_test)):
    print(result)

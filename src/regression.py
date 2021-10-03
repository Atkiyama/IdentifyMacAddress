import sys
import pandas as pd
import numpy as np

#コマンドライン引数 1閾値P 2使用する手法
args = sys.argv
capture = pd.read_csv("data/result/forRegression.csv", sep=",")
P = int(args[1])

#モデル生成
from sklearn.linear_model import LinearRegression
clf = LinearRegression(fit_intercept = True, normalize = False, copy_X = True, n_jobs = -1)
clf.fit(pd.DataFrame(capture.trainTime),capture.trainRssi)
#ファイル出力
f = open('data/result/regression.txt', 'w')
for data in clf.predict(pd.DataFrame(capture.testTime))
    f.write(data)
f.close()

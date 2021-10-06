import sys
import pandas as pd
import numpy as np

#コマンドライン引数 1読み込みファイル名 2閾値P
args = sys.argv
capture = pd.read_csv(args[1], sep=",")
P = int(args[2])
#モデル生成
from sklearn.linear_model import LinearRegression
clf = LinearRegression(fit_intercept = True, normalize = False, copy_X = True, n_jobs = -1)
clf.fit(pd.DataFrame(capture.trainTime),capture.trainRssi)
#ファイル出力
output = 'data/regression/regression.txt'
f = open(output, 'w')
for data in clf.predict(pd.DataFrame(capture.testTime)):
    print(data)
f.close()

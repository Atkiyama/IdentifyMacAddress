import sys
import pandas as pd
import numpy as np

#コマンドライン引数 1閾値P
args = sys.argv
capture = pd.read_csv('data/regression/regression.csv', sep=",")
P = int(args[5])
#モデル生成
from sklearn.linear_model import LinearRegression
clf = LinearRegression(fit_intercept = True, normalize = False, copy_X = True, n_jobs = -1)
clf.fit(pd.DataFrame(capture.trainTime),capture.trainRssi)
#ファイル出力
output = 'data/regression/regression.txt'
f = open(output, 'w')
for data in clf.predict(pd.DataFrame(capture.testTime)):
    f.write(str(data)+'\n')

f.close()

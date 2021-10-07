import sys
import pandas as pd
import numpy as np

#コマンドライン引数 1学習ファイル名 2テストファイル 3閾値P 4書き込みファイル名
args = sys.argv
capture = pd.read_csv(args[1], sep=",")
P = int(args[3])
#モデル生成
from sklearn.linear_model import LinearRegression
clf = LinearRegression(fit_intercept = True, normalize = False, copy_X = True, n_jobs = -1)
clf.fit(pd.DataFrame(capture.trainTime),capture.trainRssi)
#ファイル出力
output = args[4]
f = open(output, 'w')
capture = pd.read_csv(args[2], sep=",")
for data in clf.predict(pd.DataFrame(capture.testTime)):
    f.write(str(data)+'\n')
f.close()

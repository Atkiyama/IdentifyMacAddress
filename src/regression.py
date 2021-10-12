import sys
import pandas as pd
import numpy as np

#コマンドライン引数 1学習ファイル名 2テストファイル 3閾値P 4書き込みファイル名
args = sys.argv

def main(arg1,arg2,arg3,arg4):
    capture = pd.read_csv(arg1, sep=",")
    P = int(arg3)
    #モデル生成
    from sklearn.linear_model import LinearRegression
    clf = LinearRegression(fit_intercept = True, normalize = False, copy_X = True, n_jobs = -1)
    clf.fit(pd.DataFrame(capture.trainTime),capture.trainRssi)
    #ファイル出力
    #平均を出力？
    f = open(arg4, 'w')
    regression = []
    capture = pd.read_csv(arg2, sep=",")
    for first in capture.testTime:
        for data in range(P*10):
            regression.append(float(first)+float(data)/10)
        f.write(str(np.average(clf.predict(pd.DataFrame(regression))+'\n')))
        regression=[]
    f.close()
    return 0

main(args[1],args[2],args[3],args[4])

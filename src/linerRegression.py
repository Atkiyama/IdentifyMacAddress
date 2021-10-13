import sys
import pandas as pd
import numpy as np

#コマンドライン引数 1学習ファイル名 2テストファイル 3閾値P 4書き込みファイル名 5テストファイルのアドレス(列)数
args = sys.argv

def main(arg1,arg2,arg3,arg4,arg5):
    df = pd.read_csv(arg1, sep=",")
    P = int(arg3)
    #モデル生成
    from sklearn.linear_model import LinearRegression
    clf = LinearRegression(fit_intercept = True, normalize = False, copy_X = True, n_jobs = -1)
    clf.fit(pd.DataFrame(df.trainTime),df.trainRssi)
    #ファイル出力
    #平均を出力？
    f = open(arg4, 'w')
    for column in range(int(arg5)):
        df = pd.read_csv(arg2,header = None,usecols=[int(column)])
        f.write(str(np.average(clf.predict(pd.DataFrame(df))))+'\n')
    f.close()


main(args[1],args[2],args[3],args[4],args[5])

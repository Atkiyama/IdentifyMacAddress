#諸々のインポート
import pandas as pd
import numpy as np
from sklearn import ensemble, tree



#バギング ランダムフォレスト使うやつ　アンサンブル学習
def bagging(addressList):
    clf = ensemble.BaggingRegressor(tree.DecisionTreeRegressor(),n_jobs = -1)
    #clf = ensemble.BaggingRegressor(tree.DecisionTreeRegressor(), n_estimators=100, max_samples=0.3)
    for line in range(len(addressList)):
        for I in range(1,21):
            regression(addressList,addressList.address[line],addressList.lTime[line],I,clf)

def regression(addressList,address,lTime,I,clf):
    x_train = []
    y_train = []
    lAddress = pd.read_csv("data/address/delay/lAddress/.csv", sep=",")
    for line in range(len(lAddress)):
        time = lAddress.time[line]
        if(lTime-time <= I):
            x_train.append(time)
            y_train.append(lAddress.rssi[line])
    clf.fit(pd.DataFrame(x_train),y_train)
    x_test = []
    for  tmp_address in range(len(addressList)):
        #print(addressList.address[tmp_address])
        sub = addressList.fTime[tmp_address] - lTime
        if(0<=sub and sub<=6):
            fAddress = pd.read_csv("data/address/delay/fAddress/"+addressList.address[tmp_address]+".csv", sep=",")
            fTime = addressList.fTime[tmp_address]
            for time in fAddress.time:
                if(time-fTime>=0):
                    x_test.append(time)
    if(len(x_test)>0):
        x_test.sort()
    else:
        x_test.append(9999)
    predict = clf.predict(pd.DataFrame(x_test))
    write(address,x_test,predict,I)

def write(address,data,predict,I):
    f = open("data/address/delay/regression/bagging/"+address+"_"+str(I)+".csv", 'w')
    for line in range(len(predict)):
        if(line != 0):
            f.write("\r\n")
        f.write(str(data[line])+","+str(predict[line]))
    f.close()


addressList = pd.read_csv("data/address/delay/addressList.csv", sep=",",usecols=[1,2,3])
bagging(addressList)

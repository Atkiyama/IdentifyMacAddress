#諸々のインポート
import pandas as pd
import numpy as np
from sklearn import svm

def main():
    for i in range (1,101):
        addressList = pd.read_csv("data/address/processed/addressList/addressList"+str(i)+".csv", sep=",",usecols=[1,2,3])
        svr(addressList,i)

#線形回帰

#SVR(SVMを回帰に利用したもの)
def svr(addressList,i):
    clf = svm.SVR(kernel='rbf')
    for I in range(1,21):
        for line in range(len(addressList)):
            regression(addressList,addressList.address[line],addressList.lTime[line],I,clf,"svr/",i)
#バギング ランダムフォレスト使うやつ　アンサンブル学習

def regression(addressList,address,lTime,I,clf,regression,i):
    x_train = []
    y_train = []
    lAddress = pd.read_csv("data/address/processed/lAddress/"+address+"_"+str(i)+".csv", sep=",")
    for line in range(len(lAddress)):
        time = lAddress.time[line]
        if(lTime-float(time) <= I):
            x_train.append(time)
            y_train.append(lAddress.rssi[line])
    clf.fit(pd.DataFrame(x_train),y_train)
    x_test = []
    for  tmp_address in range(len(addressList)):
        #print(addressList.address[tmp_address])
        sub = addressList.fTime[tmp_address] - lTime
        if(0<=sub and sub<=6):
            fAddress = pd.read_csv("data/address/processed/fAddress/"+addressList.address[tmp_address]+"_"+str(i)+".csv", sep=",")
            fTime = addressList.fTime[tmp_address]
            for time in fAddress.time:
                if(time-fTime>=0):
                    x_test.append(time)
    if(len(x_test)>0):
        x_test.sort()
    else:
        x_test.append(9999)
    predict = clf.predict(pd.DataFrame(x_test))
    write(address,I,x_test,predict,regression,i)
def write(address,I,data,predict,regression,i):
    f = open("data/address/processed/regression/"+regression+address+"_"+str(I)+"_"+str(i)+".csv", 'w')
    for line in range(len(predict)):
        if(line != 0):
            f.write("\r\n")
        f.write(str(data[line])+","+str(predict[line]))
    f.close()
args = sys.argv
I=int(args[1])
if(sys.argv==3):
    i=0
    addressList = pd.read_csv("data/address/processed/addressList/addressList"+str(i)+".csv", sep=",",usecols=[1,2,3])
    randomForest(addressList,i,I)
else:
    main(I)
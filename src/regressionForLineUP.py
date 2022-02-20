#諸々のインポート
import pandas as pd
import numpy as np
from sklearn.linear_model import LinearRegression
from sklearn import svm
from sklearn import ensemble, tree
from sklearn.ensemble import RandomForestRegressor
import sys
def main(I):
    i=0
    addressList = pd.read_csv("data/address/processed/addressList/addressList"+str(i)+".csv", sep=",",usecols=[1,2,3])
    linerRegression(addressList,i,I)
    svr(addressList,i,I)
    bagging(addressList,i,I)
    randomForest(addressList,i,I)
#線形回帰
def linerRegression(addressList,i,I):
    clf = LinearRegression(fit_intercept = True, copy_X = True, n_jobs = -1)
    for line in range(len(addressList)):
        regression(addressList,addressList.address[line],addressList.lTime[line],I,clf,"linerRegression/",i)
#SVR(SVMを回帰に利用したもの)
def svr(addressList,i,I):
    clf = svm.SVR(kernel='rbf')
    for line in range(len(addressList)):
        regression(addressList,addressList.address[line],addressList.lTime[line],I,clf,"svr/",i)
#バギング ランダムフォレスト使うやつ　アンサンブル学習
def bagging(addressList,i,I):
    clf = ensemble.BaggingRegressor(tree.DecisionTreeRegressor(),n_jobs = -1)
    #clf = ensemble.BaggingRegressor(tree.DecisionTreeRegressor(), n_estimators=100, max_samples=0.3)
    for line in range(len(addressList)):
        regression(addressList,addressList.address[line],addressList.lTime[line],I,clf,"bagging/",i)
#ランダムフォレスト
def randomForest(addressList,i,I):
    clf =  RandomForestRegressor(n_jobs = -1)
    for line in range(len(addressList)):
        regression(addressList,addressList.address[line],addressList.lTime[line],I,clf,"randomForest/",i)
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

for I in range(1,21):
    main(I)

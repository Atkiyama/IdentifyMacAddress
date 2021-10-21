#諸々のインポート
import pandas as pd
import numpy as np
from sklearn.linear_model import LinearRegression

def main():
    df = pd.read_csv("data/address/addressList.csv", sep=",",usecols=[1,3])
    clf = LinearRegression(fit_intercept = True, normalize = False, copy_X = True, n_jobs = -1)
    for I in range(1,21):
        for line in range(len(df)):
            regression(df.address[line],df.lTime[line],I,clf)

def regression(address,lTime,I,clf):
    x_train = []
    y_train = []
    df = pd.read_csv("data/address/lAddress/"+address+".csv", sep=",")
    for line in range(len(df)):
        time = df.time[line]
        if(lTime-time <= I):
            x_train.append(time)
            y_train.append(df.rssi[line])

    clf.fit(pd.DataFrame(x_train),y_train)
    x_test = []
    data = np.arange(lTime, lTime+41, 0.1)
    data = np.round(data, 1)
    for fTime in data:
        x_test.append(fTime)
    predict = clf.predict(pd.DataFrame(x_test))
    write(address,I,data,predict)

def write(address,I,data,predict):
    f = open("data/address/regression/"+address+"_"+str(I)+".csv", 'w')
    for line in range(len(predict)):
        f.write(str(data[line])+","+str(predict[line]))
        f.write("\r\n")
    f.close()

main()

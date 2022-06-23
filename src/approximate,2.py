#諸々のインポート
import pandas as pd
import numpy as np
import sys
from scipy import interpolate

def main(I):
    for i in range (1,101):
        addressList = pd.read_csv("data/address/processed/addressList/addressList"+str(i)+".csv", sep=",",usecols=[1,2,3])
        approximates(addressList,i,I)


#SVR(SVMを回帰に利用したもの)
def approximates(addressList,i,I):
    for line in range(len(addressList)):
        approximate(addressList,addressList.address[line],addressList.lTime[line],I,"approximate/",i)

def approximate(addressList,address,lTime,I,regression,i):
    x_train = []
    y_train = []
    lAddress = pd.read_csv("data/address/processed/lAddress/"+address+"_"+str(i)+".csv", sep=",")
    for line in range(len(lAddress)):
        time = lAddress.time[line]
        if(0<= lTime -float(time) and lTime-float(time) <= I):
            x_train.append(time)
            y_train.append(lAddress.rssi[line])
    #補完
    fitted = interpolate.Akima1DInterpolator(x_train,y_train)
    last = x_train[len(x_train)-1]
    first = x_train[0]
    j = 1
    while first+i<last:
         x_train.append(first+i)
         j+=1
    x_train.sort()
    y_train = fitted(x_train)
    x_test = []
    for  tmp_address in range(len(addressList)):
        #print(addressList.address[tmp_address])
        sub = addressList.fTime[tmp_address] - lTime
        if(0<=sub and sub<=6):
            fAddress = pd.read_csv("data/address/processed/fAddress/"+addressList.address[tmp_address]+"_"+str(i)+".csv", sep=",")
            fTime = addressList.fTime[tmp_address]
            for time in fAddress.time:
                if(I >=time-fTime and time-fTime>=0):
                    x_test.append(time)
    if(len(x_test)>0):
        x_test.sort()
    else:
        x_test.append(9999)
    
    predict = np.poly1d(np.polyfit(x_train, y_train, 5))(x_test)
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
if(len(sys.argv)>=3):
    i=0
    addressList = pd.read_csv("data/address/processed/addressList/addressList"+str(i)+".csv", sep=",",usecols=[1,2,3])
    approximates(addressList,i,I)
else:
    main(I)
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
        x_train.append(lAddress.time[line]-lTime)
        y_train.append(lAddress.rssi[line])
    
    x_train.sort()
    #print(len(x_train))
    x_output = []
    for  tmp_address in range(len(addressList)):
        sub = addressList.fTime[tmp_address] - lTime
        if(0<=sub and sub<=6):
            fAddress = pd.read_csv("data/address/processed/fAddress/"+addressList.address[tmp_address]+"_"+str(i)+".csv", sep=",")
            fTime = addressList.fTime[tmp_address]
            for time in fAddress.time:
                if(I >=time-fTime and time-fTime>=0):
                    x_output.append(time)
    if(len(x_output)>0):
        x_output.sort()
    else:
        x_output.append(9999)
    x_test=[]
    for j in x_output:
        x_test.append(j-lTime)
    appro = np.polyfit(x_train, y_train, 5)
    predict = np.poly1d(appro)(x_test)
    write(address,I,x_output,predict,regression,i)
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
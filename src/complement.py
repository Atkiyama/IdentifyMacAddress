#諸々のインポート
import pandas as pd
import numpy as np
import sys
from scipy import interpolate

def main(I,n): 
    addressList = pd.read_csv("data/address/processed/addressList/addressList"+str(n)+".csv", sep=",",usecols=[1,2,3])
    for line in range(len(addressList)):
        complement(addressList,line,I,n)

def complement(addressList,line,I,n):
    address = addressList.address[line]
    x_train = []
    y_train = []
    lAddress = pd.read_csv("data/address/processed/lAddress/"+address+"_"+str(i)+".csv", sep=",")
    for line in range(len(lAddress)):
        x_train.append(lAddress.time[line])
        y_train.append(lAddress.rssi[line])
    #補完
    fitted = interpolate.Akima1DInterpolator(x_train,y_train)
    last = x_train[len(x_train)-1]
    first = x_train[0]
    j = 1
    while first+j<last:
         x_train.append(first+i)
         j+=1
    x_train.sort()
    y_train = fitted(x_train)
    
    
    write(address,I,x_train,y_train,i)
def write(address,I,data,predict,regression,i):
    f = open("data/address/processed/regression/"+regression+address+"_"+str(I)+"_"+str(i)+".csv", 'w')
    for line in range(len(predict)):
        if(line != 0):
            f.write("\r\n")
        f.write(str(data[line])+","+str(predict[line]))
    f.close()
    
    
args = sys.argv
I=int(args[1])
n=int(args[2])
main(I,n)
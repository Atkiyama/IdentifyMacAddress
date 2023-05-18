import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
addressList = pd.read_csv("data/address/processed/addressList/addressList1.csv", sep=",",usecols=[1])
color = []
j=0
x=[]
y=[]
for adr in addressList:
    if(adr!="address"):
        lAddress = pd.read_csv("data/address/processed/lAddress/"+adr+"_1.csv", sep=",")
        print(lAddress)
        for line in range(len(lAddress)):
            x.append(lAddress.time[line])
            y.append(lAddress.rssi[line])
            color.append(j)
        fAddress = pd.read_csv("data/address/processed/fAddress/"+adr+"_1.csv", sep=",")
        for line in range(len(lAddress)):
            x.append(fAddress.time[line])
            y.append(fAddress.rssi[line])
            color.append(j)
    j+=1

print(x)

plt.scatter(x,y,s=2000, c=color,cmap="gnuplot")
plt.xlabel("time")
plt.ylabel("rssi")
plt.xlim(-3000,3000)
plt.ylim(-100,-60)
plt.show()
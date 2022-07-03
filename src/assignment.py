from scipy.optimize import linear_sum_assignment
import numpy as np
import pandas as pd
import sys

args = sys.argv
numOfData=int(args[1])
R=int(args[2])
I=int(args[3])
T=int(args[4])

costTable = pd.read_csv("data/address/processed/costTable/svr/"+numOfData+"_"+R+","+I+","+T+".csv", sep=",",header=None)
df= pd.read_csv("data/address/processed/addressList/addressList"+numOfData+".csv", sep=",",usecols=[1])
row=df.address.to_list()
column=[]
#アドレス数/2+1だけnoneを増やす
for adr in range(int(len(costTable)/2)+1):
    column.append("none")
for adr in df.address:
    column.append(adr)

#線形割り当てのライブラリ
row_ind, col_ind = linear_sum_assignment(costTable)


#アドレスクラスの定義
class Address:
    def __init__(self, address, nextAddress):
        self.address = address
        self.nextAddress = nextAddress
        
    def getAddress(self):
        return self.address
    
    def getNextAddress(self):
        return self.nextAddress

ansList=[]
i=0
#解答の生成
for assign in col_ind:
    ansList.append(Address(row[i],column[assign]))
    print(i)
    i+=1

#結果書き込み
f = open("data/result/multi/move/svr/"+numOfData+"/"+R+","+I+","+T+".txt", 'w')
for ans in ansList:
        f.write(ans.getAddress())
        f.write("\r\n")
        f.write(ans.getNextAddress())
        f.write("\r\n")
        f.write("\r\n")
f.close()
from scipy.optimize import linear_sum_assignment
import numpy as np
import pandas as pd
import sys

args = sys.argv
method=args[1]
n=args[2]
R=args[3]
T=args[4]
I=args[5]

costTable = pd.read_csv("data/address/processed/costTable/"+method+"/"+n+"_"+R+","+T+","+I+".csv", sep=",",header=None)
df= pd.read_csv("data/address/processed/addressList/addressList"+n+".csv", sep=",",usecols=[1])
column=df.address.to_list()
row = column

#線形割り当てのライブラリ
row_ind, col_ind = linear_sum_assignment(costTable)


#アドレスのクラスの定義
class Address:
    def __init__(self, row, column):
        self.row = row
        self.column = column
        
    def getRow(self):
        return self.row
    
    def getColumn(self):
        return self.column

ansList=[]
i=0
for assign in col_ind:
    ansList.append(Address(i,assign))
    i+=1

#結果書き込み
f = open("data/result/multi/move/"+method+"/"+n+"/"+R+","+T+","+I+".txt", 'w')
for ans in ansList:
        f.write(row[ans.getRow()])
        f.write("\r\n")
        if costTable[ans.getColumn()][ans.getRow()]!=999:
            f.write(column[ans.getColumn()])
        f.write("\r\n")
        f.write("\r\n")
f.close()
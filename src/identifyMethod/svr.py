import pandas as pd
import os
import sys
import random
import numpy as np
from sklearn import svm
from scipy.optimize import linear_sum_assignment
from sklearn.base import clone
from collections import defaultdict

def regression(model,changed):
    models={}
    assignment_table=[]
    predicts={}
    
    #学習データを学習させる
    for data in changed:
        #print(data[0].address)
        x_train=[]
        y_train=[]
        for line in data:
            x_train.append(float(line[1]))
            y_train.append(float(line[2]))
    #ここでクローンしておかないと同じモデルになる
        clf =clone(model)
        clf.fit(pd.DataFrame(x_train),y_train)

        models[data[0][0]]=clf
    

    #テストデータとの差分をどんどんコスト関数として割り当てテーブルに記録していく
    for data in changed:
        regression_data=[]
        assignment_line=[]
        for data2 in changed:
            diff =float(data2[0][1])-float(data[len(data)-1][1])
            if data[0][0] != data2[0][0] and 0 <= diff and diff<= 6:
                x_test=[]
                for line in data2:
                    x_test.append(line[1])
                    predict=models[data[0][0]].predict(pd.DataFrame(x_test))

                sum=0
                for i in range(len(data2)):
                    sum+=abs(float(data2[i][2])-predict[i])
                assignment_line.append(abs(sum/len(data2)))
   
                for i in range(len(predict)):
                    regression_data.append((x_test[i],predict[i]))
            else:
                assignment_line.append(sys.float_info.max)

        regression_data.sort()
        predicts[data[0][0]]=regression_data
        assignment_table.append(assignment_line)

    
    return assignment_table,predicts

def assignment(assignment_table,changed):

    row_ind, col_ind = linear_sum_assignment(np.array(assignment_table))

    isTrue = 0
    for i in row_ind:
        if i % 2 == 0 and i + 1 == col_ind[i]:
            isTrue += 1
    accuracy = isTrue / float(len(changed) / 2)
    return row_ind,col_ind,accuracy

def main():
    changed=[]
    addressDict=defaultdict(list)
    i=0
    for line in sys.stdin:
        packet=list(line.replace("\n", "").split(","))
        addressDict[packet[0]].append(packet)
    for k,v in addressDict.items():
        changed.append(v)
    model=svm.SVR(kernel='poly')
    assignment_table,predicts=regression(model,changed)
    row_ind,col_ind,accuracy = assignment(assignment_table,changed)
    print(accuracy)



if __name__=='__main__' :
    main()
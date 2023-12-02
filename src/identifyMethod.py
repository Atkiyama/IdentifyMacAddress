import pandas as pd
import sys
import numpy as np
from sklearn.linear_model import LinearRegression
from sklearn import svm
from scipy.optimize import linear_sum_assignment
from sklearn.base import clone
from collections import defaultdict
INF=10**5
'''
changed[i番目のアドレス][i番目のアドレスのj番目のパケット][0,address 1,time 2,rssi]
'''
def timeDiff(changed):
    assignment_table=[[INF for _ in range(len(changed))] for _ in range(len(changed))]
    for i in range(len(changed)):
        for j in range(len(changed)):
            iAddress = changed[i][0][0]
            jAddress = changed[j][0][0]
            _2="_2"
            judge1=_2 in iAddress and (_2 not in jAddress)
            judge2= _2 not in iAddress and (_2  in jAddress)
            if i!=j and (judge1 or judge2):
                diff =float(changed[j][0][1])-float(changed[i][-1][1])
                if   0 <= diff <= 6:
                    assignment_table[i][j]=diff
    return assignment_table

def regression(model,changed):
    models={}
    assignment_table=[]
    
    #このループが原因
    #address7が読み込めない
    #学習データを学習させる
    for data in changed:
        #print(data[0][0],flush=True)
        x_train = [float(line[1]) for line in data]
        y_train = [float(line[2]) for line in data]
        
    #ここでクローンしておかないと同じモデルになる
        clf =clone(model)
        # print(x_train,flush=True)
        # print(y_train,flush=True)
        clf.fit(pd.DataFrame(x_train),y_train)
        #ここで処理が止まる

        models[data[0][0]]=clf
        #print(models,flush=True)

    #テストデータとの差分をどんどんコスト関数として割り当てテーブルに記録していく
    assignment_table=[[INF for _ in range(len(changed))] for _ in range(len(changed))]
    for i in range(len(changed)):
        for j in range(len(changed)):
            #ここの条件分岐によって実験環境が変化する
            #変化後アドレス同士や変化前アドレス同士ならコスト関数にINFを割り当てる
            iAddress = changed[i][0][0]
            jAddress = changed[j][0][0]
            _2="_2"
            judge1=_2 in iAddress and (_2 not in jAddress)
            judge2= _2 not in iAddress and (_2  in jAddress)
            if i!=j and (judge1 or judge2):
                diff =float(changed[j][0][1])-float(changed[i][-1][1])
                if 0 <= diff <= 6:
                    x_test=[line[1] for line in changed[j]]
                    predict=models[changed[i][0][0]].predict(pd.DataFrame(x_test))
                    sum=0
                    for k in range(len(changed[j])):
                        sum+=abs(float(changed[j][k][2])-predict[k])
                    assignment_table[i][j]=sum/len(changed[j])
                    
    return assignment_table

        
def combine_sum(model,changed,bias1,bias2):
    assignment_table1=regression(model,changed)
    assignment_table2= timeDiff(changed)
        
    for i in range(len(assignment_table1)):
        for j in range(len(assignment_table1[i])):
            assignment_table1[i][j] *= bias1
            
    for i in range(len(assignment_table2)):
        for j in range(len(assignment_table2[i])):
            assignment_table2[i][j] *= bias2         
    assignment_table = np.add(assignment_table1, assignment_table2)

    return assignment_table
    
    

def combine_dist(model,changed,bias1,bias2):
    assignment_table1=regression(model,changed)
    assignment_table2= timeDiff(changed)
        
    for i in range(len(assignment_table1)):
        for j in range(len(assignment_table1[i])):
            assignment_table1[i][j] *=bias1
            assignment_table1[i][j] *=assignment_table1[i][j]
            
    for i in range(len(assignment_table2)):
        for j in range(len(assignment_table2[i])):
            assignment_table2[i][j] *= bias2
            assignment_table2[i][j] *= assignment_table2[i][j]
                     
    assignment_table = np.add(assignment_table1, assignment_table2)
    
    for i in range(len(assignment_table)):
        for j in range(len(assignment_table[i])):
            assignment_table[i][j] = np.sqrt(assignment_table[i][j])
    return assignment_table

def combine_mul(model,changed):
    assignment_table1=regression(model,changed)
    assignment_table2= timeDiff(changed)
               
    assignment_table = np.multiply(assignment_table1, assignment_table2)
    return assignment_table
    

def assignment(assignment_table,changed):

    row_ind, col_ind = linear_sum_assignment(np.array(assignment_table))

    isTrue = 0
    for i in row_ind:
        if i % 2 == 0 and i + 1 == col_ind[i]:
            isTrue += 1
    accuracy = isTrue / float(len(changed) / 2)
    return accuracy

'''
デバック用にファイルを読み込み関数
'''
def read_data(filename):
    addressDict = defaultdict(list)
    with open(filename, 'r') as file:
        for line in file:
            packet = list(line.replace("\n", "").split(","))
            addressDict[packet[0]].append(packet)
    return list(addressDict.values())

def main():
    changed=[]
    addressDict=defaultdict(list)

    for line in sys.stdin:
        packet=list(line.replace("\n", "").split(","))
        addressDict[packet[0]].append(packet)
    for k,v in addressDict.items():
        changed.append(v)

    #debug用
    #changed = read_data("data/capture/ver3/simulate/USE_NUM/data_"+sys.argv[4]+"_"+sys.argv[5]+".csv")

    #回帰にかけるバイアス
    bias1=float(sys.argv[2])
    #時間差にかけるバイアス
    bias2=float(sys.argv[3])
    if sys.argv[1]=="timeDiff":
        assignment_table=timeDiff(changed)
    elif sys.argv[1]=="liner":
        model=LinearRegression(fit_intercept = True, copy_X = True, n_jobs = 2)
        assignment_table=regression(model,changed)
    elif sys.argv[1]=="svr":
        model=svm.SVR(kernel='poly',degree=2)
        assignment_table=regression(model,changed)
    elif sys.argv[1]=="combine_liner_sum":
        model=LinearRegression(fit_intercept = True, copy_X = True, n_jobs = 1)
        assignment_table=combine_sum(model,changed,bias1,bias2)
    elif sys.argv[1]=="combine_liner_dist":
        model=LinearRegression(fit_intercept = True, copy_X = True, n_jobs = 3)
        assignment_table=combine_dist(model,changed,bias1,bias2)
    elif sys.argv[1]=="combine_liner_mul":
        model=LinearRegression(fit_intercept = True, copy_X = True, n_jobs = 1)
        assignment_table=combine_mul(model,changed)
    elif sys.argv[1]=="combine_svr":
        model=svm.SVR(kernel='poly')
        assignment_table=combine_sum(model,changed,bias1,bias2)
    else:
        print("Invalid argument. Please provide a valid argument: 'timeDiff', 'linear', 'svr', 'combine_linear', or 'combine_svr'")
        sys.exit(1)
    #print("regreesion OK")
    accuracy = assignment(assignment_table,changed)
    print(accuracy)



if __name__=='__main__' :
    main()
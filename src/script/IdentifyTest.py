#!/usr/bin/env python
# coding: utf-8

# In[1]:


import pandas as pd
from sklearn.linear_model import LinearRegression
from scipy.optimize import linear_sum_assignment
import numpy as np
import sys
from sklearn import svm
import matplotlib.ticker as ticker
import random
import datetime
from collections import deque
import os
from IPython.display import clear_output
import multiprocessing
from sklearn.linear_model import Ridge
from sklearn.model_selection import GridSearchCV
from sklearn.base import clone


USE_NUM =20
useData = pd.read_csv('data/capture/ver3/useDataFull.csv')
useData.drop(useData.index[USE_NUM:], inplace=True)

print(useData.fileName)


# In[2]:


#元データを読み取る
original=[]
number=0
for fileName in useData.fileName:
    df =pd.read_csv('data/capture/ver3/masterPiece/'+fileName)
    df.replace(df.address[0],"address"+str(number),inplace=True)
    original.append(df)
    number +=1
#print(original)


# In[3]:


# 元データからMACアドレスの変化データを作成

def changeAddress():
    changed = []
    addressList=[]
    
    #変更点pointを抽出する
    #pointはmacアドレス変化前の最後のアドレス
    for data in original:
        before = []
        after = []
        afterFirst = 999
        changeP=[]
        point=data.iloc[0]
        for line in data.itertuples():
            #print(line)
            if 5<=float(line.time)<=float(data.iloc[-1].time)-5 :
                changeP.append(line)
            elif 5<=float(line.time) and  point.time==data.iloc[0].time:
                point=line

        if len(changeP)!=0:
            point = changeP[random.randint(0,len(changeP)-1)]
            #point = changeP[0]
        for line in data.itertuples():

            if float(point.time)-5<= float(line.time) <= float(point.time):
                packet=[]
                packet.append(line.address)
                packet.append(line.time)
                packet.append(line.rssi)
                before.append(packet)
            elif float(point.time) < float(line.time) and afterFirst == 999:
                afterFirst = float(line.time)
            elif float(line.time) - afterFirst <= 5 and afterFirst !=999:
                modified_address = line.address + "_2"
                line = line._replace(address=modified_address)
                packet=[]
                packet.append(line.address)
                packet.append(line.time)
                packet.append(line.rssi)
                after.append(packet)
            else:
                break
        changed.append(before)
        addressList.append(before[0][0])
        changed.append(after)
        addressList.append(after[0][0])
        
    return changed,addressList
    # print(addressList)
    # for data in changed:
    #     for line in data:
    #         print(line)
    #     print()


# In[4]:


#パケットの受信時刻をフォーマット
#変化タイミングを0にそろえる
def studyC(changed):
    for data in changed:
        if not "_2" in data[0][0]:
            beforeLast=float(data[len(data)-1][1])
            #print(beforeLast)
        for i, line in enumerate(data):
            data[i][1] =float(line[1] - beforeLast)
#         print(data[i])
# print(changed)


# In[5]:


def studyD(changed,D):
    studyC(changed)
    d=0
    for data in changed:
        if not "_2" in data[0][0]:
            d=random.randint(-1*D,D)
        for i, line in enumerate(data):
            data[i][1] =float(line[1]) + d


# In[6]:


#モデルを用意
#返り値に回帰結果を返すようにしたい
#なぜか回帰結果が全て同じ？
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
# #モデルを比較したが係数と切片はちがうらしい
#         coefficients = clf.coef_
#         intercept = clf.intercept_
#         print("x_train",x_train)
#         print("y_train",y_train)
#         print("係数:", coefficients)
#         print("切片:", intercept)
#         print(data[0][0])
        #アドレスをキーに辞書に入れていく
        models[data[0][0]]=clf
    
#     print()
#     print("辞書のデータ")
#     for address,model in models.items():
#         print(address,model.coef_,model.intercept_)
    
    #テストデータとの差分をどんどんコスト関数として割り当てテーブルに記録していく
    #ここで比較すると何故かmodelsの切片と係数が同じになっている
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
#                 coefficients = models[data[0][0]].coef_
#                 intercept = models[data[0][0]].intercept_
#                 print("係数:", coefficients)
#                 print("切片:", intercept)
#                 print(data[0][0])
                sum=0
                for i in range(len(data2)):
                    sum+=abs(data2[i][2]-predict[i])
                assignment_line.append(abs(sum/len(data2)))
                #print(data[0][0],data2[0][0],predict)
                for i in range(len(predict)):
                    regression_data.append((x_test[i],predict[i]))
            else:
                assignment_line.append(sys.float_info.max)

        regression_data.sort()
        predicts[data[0][0]]=regression_data
        assignment_table.append(assignment_line)

    
    return assignment_table,predicts
#print(models)


# In[7]:


def timeDiff(changed):
    assignment_table=[]
    for data in changed:
        assignment_line=[]
        for data2 in changed:
            diff =float(data2[0][1])-float(data[len(data)-1][1])
            if data[0][0] != data2[0][0] and 0 <= diff and diff<= 6:
                assignment_line.append(diff)
            else:
                assignment_line.append(sys.float_info.max)
        assignment_table.append(assignment_line)
    return assignment_table


# In[8]:


def addressInfo(address,change,f):
    for data in change:
        if data[0][0]==address:
            f.write(address+" "+"ftime= "+ str(data[0][1])+",ltime="+str(data[len(data)-1][1])+"\n")
            break


# In[9]:


def combine(model,changed,bias1,bias2):
    assignment_table1,predicts= regression(model,changed)
    assignment_table2= timeDiff(changed)
    
#     bias=1
#     for i in range(len(assignment_table1)):
#         for j in range(len(assignment_table1[i])):
#             assignment_table1[i][j] *= bias
    for i in range(len(assignment_table1)):
        for j in range(len(assignment_table1[i])):
            if assignment_table1[i][j]==sys.float_info.max or assignment_table1[i][j]<0:
                assignment_table1[i][j]=sys.float_info.max/2
                
    for i in range(len(assignment_table2)):
        for j in range(len(assignment_table2[i])):
            if assignment_table2[i][j]==sys.float_info.max or assignment_table2[i][j]<0:
                assignment_table2[i][j]=sys.float_info.max/2
        
    for i in range(len(assignment_table1)):
        for j in range(len(assignment_table1[i])):
            assignment_table1[i][j] *= bias1
            
    for i in range(len(assignment_table2)):
        for j in range(len(assignment_table2[i])):
            assignment_table2[i][j] *= bias2         
    assignment_table = np.add(assignment_table1, assignment_table2)
#     for i in range(len(assignment_table)):
#         for j in range(len(assignment_table[i])):
#             if assignment_table[i][j]==float('inf') or assignment_table[i][j]<0:
#                 assignment_table[i][j]=sys.float_info.max
        
    #value errorの例外処理を入れる
    return assignment_table,predicts


# In[10]:


# 線形割り当て

def assignment(assignment_table,addressList,changed,fileName,number):
    # path=fileName+"/"+str(number)+".txt"
    # f = open(path, 'w')
    row_ind, col_ind = linear_sum_assignment(np.array(assignment_table))
    # print(row_ind)
    # print(col_ind)
    isTrue = 0
    for i in row_ind:
        # if assignment_table[i][col_ind[i]] != sys.float_info.max:
        #     addressInfo(addressList[i], changed,f)
        #     f.write(addressList[i] + " is matched " + addressList[col_ind[i]]+"\n")
        #     addressInfo(addressList[col_ind[i]], changed,f)
        #     f.write("cost is " + str(assignment_table[i][col_ind[i]])+"\n")
        # else:
        #     f.write(addressList[i] + " is not matched"+"\n")
        if i % 2 == 0 and i + 1 == col_ind[i]:
            # f.write(str(True)+"\n")
            isTrue += 1
        # elif i % 2 == 1:
        #     f.write("\n")
        # else:
        #     f.write(str(False)+"\n")

    accuracy = isTrue / float(len(addressList) / 2)
    # f.write("accuracy is " + str(accuracy)+"\n")
    # f.close()
    return row_ind,col_ind,accuracy


# In[11]:


# プロット用の関数
def graph(before, correct, after,fileName,j):
    import matplotlib.pyplot as plt
    bTime = []
    bRssi = []
    for packet in before:
        bTime.append(float(packet[1]))
        bRssi.append(packet[2])
    aTime = []
    aRssi = []
    for packet in after:
        aTime.append(float(packet[1]))
        aRssi.append(packet[2])
    cTime = []
    cRssi = []
    for packet in correct:
        cTime.append(float(packet[1]))
        cRssi.append(packet[2])
#     # X軸の数字をオフセットを使わずに表現する
#     plt.gca().get_xaxis().get_major_formatter().set_useOffset(False)
#     plt.gca().get_xaxis().set_major_locator(ticker.MaxNLocator(integer=True))
    #グラフの描画設定
    plt.rcParams['pdf.fonttype'] = 42
    plt.rcParams['font.size'] = 16
    plt.rcParams['axes.labelsize'] = 19
    plt.rcParams['axes.titlesize'] = 24
    plt.rcParams['xtick.labelsize'] = 20
    plt.rcParams['ytick.labelsize'] = 20

    plt.rcParams['legend.fontsize'] = 20
    plt.rcParams['legend.loc'] = 'lower right'

    plt.rcParams['figure.figsize'] = (16, 12)
    plt.rcParams['figure.dpi'] = 100

    plt.xlabel("Time")
    plt.ylabel("RSSI")
    plt.ylim(-65, -100)

    minx = float(min(min(bTime),min(cTime),min(aTime))) - 1
    maxx = float(max(max(bTime),max(cTime),max(aTime))) + 1
    plt.xlim(minx, maxx)

    # X軸のメモリを1刻みに設定
#     x_ticks = np.arange(int(minx), int(maxx) + 1,1)
#     print(x_ticks)
#     plt.xticks(x_ticks)
    

    plt.plot(bTime, bRssi, linewidth=6, label="before")
    plt.plot(aTime, aRssi, linewidth=6, label="after",)
    plt.plot(cTime, cRssi, linewidth=6, label="correct")
    plt.legend(bbox_to_anchor=(0, -0.1), loc='upper left', borderaxespad=0, fontsize=18,ncol=3)
   

    plt.title(before[0][0])
  
    
    plt.savefig(fileName+"/"+j+"_"+before[0][0]+".pdf")
    plt.show()


# In[12]:


# プロット用の関数
def graph2(before, correct, after,fileName,j,predicts):
    import matplotlib.pyplot as plt
    bTime = []
    bRssi = []
    rTime=[]
    for packet in before:
        bTime.append(float(packet[1]))
        bRssi.append(packet[2])
    aTime = []
    aRssi = []
    for packet in after:
        #rTime.append(float(packet[1]))
        aTime.append(float(packet[1]))
        aRssi.append(packet[2])
    cTime = []
    cRssi = []
    for packet in correct:
        #rTime.append(float(packet[1]))
        cTime.append(float(packet[1]))
        cRssi.append(packet[2])
#     # X軸の数字をオフセットを使わずに表現する
#     plt.gca().get_xaxis().get_major_formatter().set_useOffset(False)
#     plt.gca().get_xaxis().set_major_locator(ticker.MaxNLocator(integer=True))
    #グラフの描画設定
#     plt.rcParams['pdf.fonttype'] = 42
#     plt.rcParams['font.size'] = 16
#     plt.rcParams['axes.labelsize'] = 19
#     plt.rcParams['axes.titlesize'] = 24
#     plt.rcParams['xtick.labelsize'] = 20
#     plt.rcParams['ytick.labelsize'] = 20

#     plt.rcParams['legend.fontsize'] = 20
#     plt.rcParams['legend.loc'] = 'lower right'

#     plt.rcParams['figure.figsize'] = (16, 12)
#     plt.rcParams['figure.dpi'] = 100

    plt.xlabel("Time")
    plt.ylabel("RSSI")
    plt.ylim(-65,-100)

    minx = float(min(min(bTime),min(cTime),min(aTime))) - 1
    maxx = float(max(max(bTime),max(cTime),max(aTime))) + 1
    
    plt.xlim(minx, maxx)

    # X軸のメモリを1刻みに設定
#     x_ticks = np.arange(int(minx), int(maxx) + 1,1)
#     print(x_ticks)
#     plt.xticks(x_ticks)


    rTime=[]
    rRssi=[]
#     print(before[0][0])
#     print(predicts[before[0][0]])
    for time,rssi in predicts[before[0][0]]:
        rTime.append(time)
        rRssi.append(rssi)
        
    plt.plot(bTime, bRssi, linewidth=6, label="before")
    plt.plot(aTime, aRssi, linewidth=6, label="after",)
    plt.plot(cTime, cRssi, linewidth=6, label="correct")
    plt.plot(rTime, rRssi, linewidth=6, label="regression")
    plt.legend(bbox_to_anchor=(0, -0.1), loc='upper left', borderaxespad=0, fontsize=18,ncol=3)



    plt.title(before[0][0])
  
    
    plt.savefig(fileName+"/"+j+"_"+before[0][0]+".pdf")
    plt.show()


# In[13]:


def writeChanged(changed,i):
    os.makedirs("data/test/packet/",exist_ok=True)
    f= open("data/test/packet/"+datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")+"_"+str(i)+".csv", 'w')
    f.write("address,time,rssi\n")
    for address in changed:
        for packet in address:
            f.write(str(packet[0])+","+str(packet[1])+","+str(packet[2])+"\n")
    f.close()


# In[14]:


def run1(studyInfo,changed,addressList,i):
    accuracyName="data/test/"+studyInfo
    txtName="data/test/matching/"+studyInfo
    imgName="data/test/img/"+studyInfo
    # os.makedirs(txtName, exist_ok=True)
    # os.makedirs(imgName, exist_ok=True)
    assignment_table=timeDiff(changed)
    #     for j in range(len(assignment_table)):
    #         print(assignment_table[j])
    # os.makedirs("data/test/table/"+studyInfo, exist_ok=True)
    # f= open("data/test/table/"+studyInfo+"/"+str(i)+".csv", 'w')
    # for line in assignment_table:
    #     for j in line:
    #         f.write(str(j)+",")
    #     f.write("\n")
    row_ind,col_ind,accuracy = assignment(assignment_table,addressList,changed,txtName,i)
    # for j in row_ind:
    #     if j%2==0:
    #         graph(changed[j],changed[j+1],changed[col_ind[j]],imgName,str(i))
    return accuracy

    


# In[15]:


def run2(studyInfo,model,changed,addressList,i):
    accuracyName="data/test/"+studyInfo
    txtName="data/test/matching/"+studyInfo
    imgName="data/test/img/"+studyInfo
    # os.makedirs(txtName, exist_ok=True)
    # os.makedirs(imgName, exist_ok=True)
    assignment_table,predicts=regression(model,changed)
#     for j in range(len(assignment_table)):
#             print(assignment_table[j])
    # os.makedirs("data/test/table/"+studyInfo, exist_ok=True)
    # f= open("data/test/table/"+studyInfo+"/"+str(i)+".csv", 'w')
    # for line in assignment_table:
    #     for j in line:
    #         f.write(str(j)+",")
    #     f.write("\n")
    row_ind,col_ind,accuracy = assignment(assignment_table,addressList,changed,txtName,i)
    # for j in row_ind:
    #     if j%2==0:
    #         graph2(changed[j],changed[j+1],changed[col_ind[j]],imgName,str(i),predicts)
    return accuracy
    
    


# In[16]:


def run3(studyInfo,model,bias1,bias2,changed,addressList,i):
    accuracyName="data/test/"+studyInfo
    txtName="data/test/matching/"+studyInfo
    imgName="data/test/img/"+studyInfo
    # os.makedirs(txtName, exist_ok=True)
    # os.makedirs(imgName, exist_ok=True)
    assignment_table,predicts=combine(model,changed,bias1,bias2)
    #     for j in range(len(assignment_table)):
    #         print(assignment_table[j])
    # os.makedirs("data/test/table/"+studyInfo, exist_ok=True)
    # f= open("data/test/table/"+studyInfo+"/"+str(i)+".csv", 'w')
    # for line in assignment_table:
    #     for j in line:
    #         f.write(str(j)+",")
    #     f.write("\n")
    row_ind,col_ind,accuracy = assignment(assignment_table,addressList,changed,txtName,i)
    # for j in row_ind:
    #     if j%2==0:
    #         graph2(changed[j],changed[j+1],changed[col_ind[j]],imgName,str(i),predicts)
    return accuracy


# In[17]:


def main():
    studyInfo1="timeDiff"+datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")

    studyInfo2="Liner"+datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")

    studyInfo3="svr"+datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    studyInfo4="ridge"+datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")

    #studyInfo4="combine_liner_1"+datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")

    #studyInfo5="combine_svr_1"+datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    
    studyInfo6="combine_liner_075"+datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")

    studyInfo7="combine_svr_075"+datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    studyInfo8="combine_ridge_075"+datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    
    # studyInfo8="combine_liner_05"+datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")

    # studyInfo9="combine_svr_05"+datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    
    # studyInfo10="combine_liner_025"+datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")

    # studyInfo11="combine_svr_025"+datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    
    # studyInfo12="combine_liner_01"+datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")

    # studyInfo13="combine_svr_01"+datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    
    os.makedirs("data/test/evaluation/",exist_ok=True)
    f= open("data/test/evaluation/test"+datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")+".csv", 'w')


    liner=LinearRegression(fit_intercept = True, copy_X = True, n_jobs = -1)
    svr=svm.SVR(kernel='poly')
    ridge = Ridge()
    
    # ハイパーパラメータの候補を設定
    param_grid_ridge={
        'alpha':[n*0.1 for n in range(1, 1001)]
    }
    param_grid_svr = {
        'C': [0.1, 1, 10, 100],
        'epsilon': [0.01, 0.1, 0.5, 1],
        'gamma': [0.001, 0.01, 0.1, 1]
    }

    attempt=1000
    f.write("timeDiff,")
    f.write("liner,")
    f.write("svr,")
    #f.write("ridge,")
    f.write("combine_liner_075,")
    f.write("combine_svr_075\n")
    #f.write("combine_ridge_075\n")
    # f.write("combine_liner_075,")
    # f.write("combine_svr_075,")
    # f.write("combine_liner_05,")
    # f.write("combine_svr_05,")
    # f.write("combine_liner_025,")
    # f.write("combine_svr_025,")
    # f.write("combine_liner_01,")
    # f.write("combine_svr_01\n")
    sum=[0]*5
    plist=[]
    for i in range(attempt):
            changed,addressList=changeAddress()
            #simple_smoothing(changed,1)

            #writeChanged(changed,i)
            studyD(changed,150)

            ac=[]
            ac.append(run1(studyInfo1,changed,addressList,i))
            #ac.append(run4(studyInfo2,ridge,changed,addressList,i,param_grid_ridge))
            ac.append(run2(studyInfo2,liner,changed,addressList,i))
            ac.append(run2(studyInfo3,svr,changed,addressList,i))
            # ac.append(run2(studyInfo4,ridge,changed,addressList,i))
            ac.append(run3(studyInfo6,liner,0.75,1,changed,addressList,i))
            ac.append(run3(studyInfo7,svr,0.75,1,changed,addressList,i))
            # ac.append(run3(studyInfo8,svr,0.75,1,changed,addressList,i))
            
            # ac.append(run3(studyInfo6,liner,0.75,1,changed,addressList,i))
            # ac.append(run3(studyInfo7,svr,0.75,1,changed,addressList,i))
            # ac.append(run3(studyInfo8,liner,0.5,1,changed,addressList,i))
            # ac.append(run3(studyInfo9,svr,0.5,1,changed,addressList,i))
            # ac.append(run3(studyInfo10,liner,0.25,1,changed,addressList,i))
            # ac.append(run3(studyInfo11,svr,0.25,1,changed,addressList,i))
            # ac.append(run3(studyInfo12,liner,0.1,1,changed,addressList,i))
            # ac.append(run3(studyInfo13,svr,0.1,1,changed,addressList,i))

            for j in range(len(ac)):
                        sum[j]+=ac[j]
                        f.write(str(ac[j]))
                        if j!=len(ac)-1:
                            f.write(",")
            f.write("\n")
            print(i)
            clear_output()


    
    for i in range(len(sum)):
        if i!=len(sum)-1:
                f.write(str(sum[i]/attempt)+",")
        else:
                f.write(str(sum[i]/attempt))

    f.close()


# In[18]:


if __name__=='__main__' :
    main()


# In[19]:


def simple_smoothing(changed,scope):
    for data in changed:
        for i in range(scope,len(data)-scope):
            sum=0
            for j in range(-1*scope,scope+1):
                sum+=data[i+j][2]
            data[i][2]=sum//(scope*2+1)
            


# In[20]:


#モデルを用意
def regression_grid(model,changed,param_grid):
    models={}
    assignment_table=[]
    predicts=[]
    for data in changed:
        #print(data[0].address)
        x_train=[]
        y_train=[]
        for line in data:
            x_train.append(float(line[1]))
            y_train.append(float(line[2]))
        clf = GridSearchCV(estimator=model, param_grid=param_grid, cv=3)
        clf.fit(pd.DataFrame(x_train),y_train)
        models[data[0][0]]=clf
    for data in changed:
        assignment_line=[]
        for data2 in changed:
            diff =float(data2[0][1])-float(data[len(data)-1][1])
            if data[0][0] != data2[0][0] and 0 <= diff and diff<= 6:
                x_test=[]
                for line in data2:
                    x_test.append(line[1])
                predict=models[data[0][0]].predict(pd.DataFrame(x_test))
                predicts.append(predict)
                sum=0
                for i in range(len(data2)):
                    sum+=abs(data2[i][2]-predict[i])
                assignment_line.append(abs(sum/len(data2)))
            else:
                assignment_line.append(sys.float_info.max)
        assignment_table.append(assignment_line)
    return assignment_table,models
#print(models)


# In[21]:


def run4(studyInfo,model,changed,addressList,i,param_grid):
    accuracyName="data/test/"+studyInfo
    txtName="data/test/matching/"+studyInfo
    imgName="data/test/img/"+studyInfo
    os.makedirs(txtName, exist_ok=True)
    os.makedirs(imgName, exist_ok=True)
    assignment_table,models=regression_grid(model,changed,param_grid)
    #     for j in range(len(assignment_table)):
    #         print(assignment_table[j])
    os.makedirs("data/test/table/"+studyInfo, exist_ok=True)
    f= open("data/test/table/"+studyInfo+"/"+str(i)+".csv", 'w')
    for line in assignment_table:
        for j in line:
            f.write(str(j)+",")
        f.write("\n")
    row_ind,col_ind,accuracy = assignment(assignment_table,addressList,changed,txtName,i)
    for j in row_ind:
        if j%2==0:
            graph2(changed[j],changed[j+1],changed[col_ind[j]],imgName,str(i),models)
    return accuracy
    


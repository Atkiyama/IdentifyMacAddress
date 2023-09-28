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


USE_NUM = 10
useData = pd.read_csv('data/capture/ver3/useData.csv')
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
        for line in data.itertuples():

            if float(line.time) <= float(point.time):
                before.append(line)
            elif float(point.time) < float(line.time) and afterFirst == 999:
                afterFirst = float(line.time)
            elif float(line.time) - afterFirst <= 5:
                modified_address = line.address + "_2"
                line = line._replace(address=modified_address)
                after.append(line)
            else:
                break
        changed.append(before)
        addressList.append(before[0].address)
        changed.append(after)
        addressList.append(after[0].address)
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
        if not "_2" in data[0].address:
            beforeLast=float(data[len(data)-1].time)
            #print(beforeLast)
        for i, line in enumerate(data):
            data[i] = line._replace(time=str(float(line.time) - beforeLast))
#         print(data[i])
# print(changed)


# In[5]:


def studyD(changed,D):
    studyC(changed)
    d=0
    for data in changed:
        if not "_2" in data[0].address:
            d=random.randint(-1*D,D)
        for i, line in enumerate(data):
            data[i] = line._replace(time=str(float(line.time) + d))


# In[6]:


#モデルを用意
def regression(model,changed):
    models={}
    assignment_table=[]
    for data in changed:
        #print(data[0].address)
        x_train=[]
        y_train=[]
        for line in data:
            x_train.append(float(line.time))
            y_train.append(float(line.rssi))
        clf = model
        clf.fit(pd.DataFrame(x_train),y_train)
        models[data[0].address]=clf
    for data in changed:
        assignment_line=[]
        for data2 in changed:
            diff =float(data2[0].time)-float(data[len(data)-1].time)
            if data[0].address != data2[0].address and 0 <= diff and diff<= 6:
                x_test=[]
                for line in data2:
                    x_test.append(line.time)
                predict=models[data[0].address].predict(pd.DataFrame(x_test))
                sum=0
                for i in range(len(data2)):
                    sum+=abs(data2[i].rssi-predict[i])
                assignment_line.append(abs(sum/len(data2)))
            else:
                assignment_line.append(sys.float_info.max)
        assignment_table.append(assignment_line)
    return assignment_table

#print(models)


# In[7]:


def timeDiff(changed):
    assignment_table=[]
    for data in changed:
        assignment_line=[]
        for data2 in changed:
            diff =float(data2[0].time)-float(data[len(data)-1].time)
            if data[0].address != data2[0].address and 0 <= diff and diff<= 6:
                assignment_line.append(diff)
            else:
                assignment_line.append(sys.float_info.max)
        assignment_table.append(assignment_line)
    return assignment_table


# In[8]:


def combine(model,changed,bias1,bias2):
    assignment_table1= regression(model,changed)
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
    return assignment_table


# In[9]:


def addressInfo(address,change,f):
    for data in change:
        if data[0].address==address:
            f.write(address+" "+"ftime= "+ str(data[0].time)+",ltime="+str(data[len(data)-1].time)+"\n")
            break


# In[10]:


# 線形割り当て

def assignment(assignment_table,addressList,changed,fileName,number):
    path=fileName+"/"+str(number)+".txt"
    f = open(path, 'w')
    row_ind, col_ind = linear_sum_assignment(np.array(assignment_table))
    # print(row_ind)
    # print(col_ind)
    isTrue = 0
    for i in row_ind:
        if assignment_table[i][col_ind[i]] != sys.float_info.max:
            addressInfo(addressList[i], changed,f)
            f.write(addressList[i] + " is matched " + addressList[col_ind[i]]+"\n")
            addressInfo(addressList[col_ind[i]], changed,f)
            f.write("cost is " + str(assignment_table[i][col_ind[i]])+"\n")
        else:
            f.write(addressList[i] + " is not matched"+"\n")
        if i % 2 == 0 and i + 1 == col_ind[i]:
            f.write(str(True)+"\n")
            isTrue += 1
        elif i % 2 == 1:
            f.write("\n")
        else:
            f.write(str(False)+"\n")

    accuracy = isTrue / float(len(addressList) / 2)
    f.write("accuracy is " + str(accuracy)+"\n")
    f.close()
    return row_ind,col_ind,accuracy


# In[11]:


# プロット用の関数
def graph(before, correct, after,fileName,j):
    import matplotlib.pyplot as plt
    bTime = []
    bRssi = []
    for packet in before:
        bTime.append(float(packet.time))
        bRssi.append(packet.rssi)
    aTime = []
    aRssi = []
    for packet in after:
        aTime.append(float(packet.time))
        aRssi.append(packet.rssi)
    cTime = []
    cRssi = []
    for packet in correct:
        cTime.append(float(packet.time))
        cRssi.append(packet.rssi)
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



    plt.title(before[0].address)
  
    
    plt.savefig(fileName+"/"+j+"_"+before[0].address+".pdf")
    plt.show()


# In[12]:


# #関数呼び出し
# #ここでデータをずらしたり割り当てを行う
# #regressionのコスト関数がおかしいかもしれない

# liner=LinearRegression(fit_intercept = True, copy_X = True, n_jobs = -1)
# svr=svm.SVR(kernel='poly')

# #studyInfo= datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
# studyInfo="timeDiff"+datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
# accuracyName="data/test/"+studyInfo
# txtName="data/test/txt/"+studyInfo
# imgName="data/test/img/"+studyInfo
# f = open(accuracyName+".txt", 'w')
# sum=0.0
# attempt=1000
# for i in range(attempt):
#     changed,addressList=changeAddress()
#     studyD(changed,150)

#     assignment_table=timeDiff(changed)
# #     for j in range(len(assignment_table)):
# #         print(assignment_table[j])
#     row_ind,col_ind,accuracy = assignment(assignment_table,addressList,changed,txtName,i)
#     sum+=accuracy
#     #print(str(accuracy)+"\n")
#     f.write(str(accuracy)+"\n")
#     for j in row_ind:
#         if j%2==0:
#             graph(changed[j],changed[j+1],changed[col_ind[j]],imgName,str(i))
# print("average accuracy ="+str(sum/attempt)+"\n")
# f.write("average accuracy ="+str(sum/attempt)+"\n")
# f.close()


# In[13]:


def run1(studyInfo,changed,addressList,i,k,ac):
    accuracyName="data/test/"+studyInfo
    txtName="data/test/matching/"+studyInfo
    imgName="data/test/img/"+studyInfo
    os.makedirs(txtName, exist_ok=True)
    os.makedirs(imgName, exist_ok=True)
    assignment_table=timeDiff(changed)
    #     for j in range(len(assignment_table)):
    #         print(assignment_table[j])
    os.makedirs("data/test/table/"+studyInfo, exist_ok=True)
    f= open("data/test/table/"+studyInfo+"/"+str(i)+".txt", 'w')
    for line in assignment_table:
        for j in line:
            f.write(str(j)+",")
        f.write("\n")
    row_ind,col_ind,accuracy = assignment(assignment_table,addressList,changed,txtName,i)
    for j in row_ind:
        if j%2==0:
            graph(changed[j],changed[j+1],changed[col_ind[j]],imgName,str(i))
    ac[i][k]=accuracy

    


# In[14]:


def run2(studyInfo,model,changed,addressList,i,k,ac):
    accuracyName="data/test/"+studyInfo
    txtName="data/test/matching/"+studyInfo
    imgName="data/test/img/"+studyInfo
    os.makedirs(txtName, exist_ok=True)
    os.makedirs(imgName, exist_ok=True)
    assignment_table=regression(model,changed)
    #     for j in range(len(assignment_table)):
    #         print(assignment_table[j])
    os.makedirs("data/test/table/"+studyInfo, exist_ok=True)
    f= open("data/test/table/"+studyInfo+"/"+str(i)+".txt", 'w')
    for line in assignment_table:
        for j in line:
            f.write(str(j)+",")
        f.write("\n")
    row_ind,col_ind,accuracy = assignment(assignment_table,addressList,changed,txtName,i)
    for j in row_ind:
        if j%2==0:
            graph(changed[j],changed[j+1],changed[col_ind[j]],imgName,str(i))
    ac[i][k]=accuracy
    


# In[15]:


def run3(studyInfo,model,bias1,bias2,changed,addressList,i,k,ac):
    accuracyName="data/test/"+studyInfo
    txtName="data/test/matching/"+studyInfo
    imgName="data/test/img/"+studyInfo
    os.makedirs(txtName, exist_ok=True)
    os.makedirs(imgName, exist_ok=True)
    assignment_table=combine(model,changed,bias1,bias2)
    #     for j in range(len(assignment_table)):
    #         print(assignment_table[j])
    os.makedirs("data/test/table/"+studyInfo, exist_ok=True)
    f= open("data/test/table/"+studyInfo+"/"+str(i)+".txt", 'w')
    for line in assignment_table:
        for j in line:
            f.write(str(j)+",")
        f.write("\n")
    row_ind,col_ind,accuracy = assignment(assignment_table,addressList,changed,txtName,i)
    for j in row_ind:
        if j%2==0:
            graph(changed[j],changed[j+1],changed[col_ind[j]],imgName,str(i))
    ac[i][k]=accuracy


# In[16]:


def writeChanged(changed,i):
    os.makedirs("data/test/packet/",exist_ok=True)
    f= open("data/test/packet/"+datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")+str(i)+".txt", 'w')
    f.write("address,time,rssi\n")
    for address in changed:
        for packet in address:
            f.write(str(packet.address)+","+str(packet.time)+","+str(packet.rssi)+"\n")
    f.close()


# In[17]:


studyInfo1="timeDiff"+datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")

studyInfo2="Liner"+datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")

studyInfo3="svr"+datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")

studyInfo4="combine_liner"+datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")

studyInfo5="combine_svr"+datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
os.makedirs("data/test/evaluation/",exist_ok=True)
f= open("data/test/evaluation/"+datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")+".txt", 'w')


liner=LinearRegression(fit_intercept = True, copy_X = True, n_jobs = -1)
svr=svm.SVR(kernel='poly')

attempt=10
f.write("timeDiff,")
f.write("liner,")
f.write("svr,")
f.write("combine_liner,")
f.write("combine_svr\n")
ac=[[0 for _ in range(5)] for _ in range(attempt)]
sum=[0,0,0,0,0]
plist=[]
for i in range(attempt):
        changed,addressList=changeAddress()
        writeChanged(changed,i)
        studyD(changed,150)

        
        p1=multiprocessing.Process(target=run1, args=(studyInfo1,changed,addressList,i,0,ac))
        p2=multiprocessing.Process(target=run2, args=(studyInfo2,liner,changed,addressList,i,1,ac))
        p3=multiprocessing.Process(target=run2, args=(studyInfo3,svr,changed,addressList,i,2,ac))
        p4=multiprocessing.Process(target=run3, args=(studyInfo4,liner,1,1,changed,addressList,i,3,ac))
        p5=multiprocessing.Process(target=run3, args=(studyInfo5,svr,1,1,changed,addressList,i,4,ac))
#         ac.append(run1(studyInfo1,changed,addressList,i,0,ac))
#         ac.append(run2(studyInfo2,liner,changed,addressList,i,1,ac))
#         ac.append(run2(studyInfo3,svr,changed,addressList,i,2,ac))
#         ac.append(run3(studyInfo4,liner,1,1,changed,addressList,i,3,ac))
#         ac.append(run3(studyInfo5,svr,1,1,changed,addressList,i,4,ac))
        p1.start()
        p2.start()
        p3.start()
        p4.start()
        p5.start()
        plist.append(p1)
        plist.append(p2)
        plist.append(p3)
        plist.append(p4)
        plist.append(p5)

for p in plist:
    p.join()
    
for i in range(attempt):
    for j in range(5):
        f.write(str(ac[i][j]))
        sum[j]+=ac[i][j]
        if i!=4:
            f.write(",")
    f.write("\n")
        
        
       
for i in range(len(sum)):
    if i!=len(sum)-1:
            f.write(str(sum[i]/attempt)+",")
    else:
            f.write(str(sum[i]/attempt))

f.close()


# In[ ]:


clear_output(True)


# In[ ]:





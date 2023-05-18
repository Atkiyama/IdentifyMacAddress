import pandas as pd
import subprocess
df = pd.read_csv("data/result/ver3Result.csv")
print(df)
for i in df.index:
    #print(df.fileName[i])
    capture = pd.read_csv("data/capture/ver3/csv/"+str(df.fileName[i])+".csv")
    f = open("data/capture/ver3/extract/"+str(df.fileName[i])+".csv", 'w')
    for j in capture.index:
        if(capture.address[j]==df.address[i]):
            f.write(str(capture.address[j])+","+str(capture.time[j])+","+str(capture.rssi[j])+"\n")
    f.close()
    
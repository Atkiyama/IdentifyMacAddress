import pandas as pd
import os
import sys
import random

def read_all_csv_files(directory_path):
    # 指定したディレクトリ内のすべてのファイルを取得
    files = [file for file in os.listdir(directory_path) if file.endswith('.csv')]
    
    # ファイルを読み込んでDataFrameに追加
    dataframes = []
    for file in files:
        file_path = os.path.join(directory_path, file)
        try:
            # CSVファイルを読み込んでDataFrameに変換
            df = pd.read_csv(file_path)
            # 読み込んだDataFrameをリストに追加
            dataframes.append(df)
        except Exception as e:
            print(f"ファイル {file} の読み込み中にエラーが発生しました: {e}")
    
    return dataframes
    
    # # すべてのDataFrameを結合して返す
    # if len(dataframes) > 0:
    #     result = pd.concat(dataframes, ignore_index=True)
    #     return result
    # else:
    #     print("指定したディレクトリ内にCSVファイルが見つかりませんでした。")
    #     return None
    
def changeAddress(original):
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
            elif float(line.time) - afterFirst <= 5 and afterFirst!=999:
                modified_address = line.address + "_2"
                line = line._replace(address=modified_address)
                packet=[]
                packet.append(line.address)
                packet.append(line.time)
                packet.append(line.rssi)
                after.append(packet)
        changed.append(before)
        addressList.append(before[0][0])
        changed.append(after)
        addressList.append(after[0][0])
        
    return changed,addressList



#パケットの受信時刻をフォーマット
#変化タイミングを0にそろえる
def studyC(changed):
    beforeLast=0
    for data in changed:
        if not "_2" in data[0][0]:
            beforeLast=float(data[len(data)-1][1])
            
        for i, line in enumerate(data):       
        
            data[i][1] =float(line[1] - beforeLast)

def studyD(changed,D):
    studyC(changed)
    d=0
    for data in changed:
        if not "_2" in data[0][0]:
            d=random.randint(-1*D,D)
        for i, line in enumerate(data):
            data[i][1] =float(line[1]) + d

def main():
    directory_path = 'data/capture/ver3/masterPiece'
    df_list = read_all_csv_files(directory_path)
    
    for i in range(len(df_list)):
        df_list[i].address="address" + str(i)
  
    #使いたいデータ数
    USE_NUM= int(sys.argv[1])
    #何番目の検証か
    CASE_NUM=int(sys.argv[2])
    selects=random.sample(df_list, k=USE_NUM)
    #print(selects[0])
    #print("selects",selects)
    #ここまでは時系列順になっている
    changed,addressList=changeAddress(selects)
    studyD(changed,150)
    for data in changed:
        for i in data:
            output_string = ','.join(map(str,i))
            print(output_string)
    
    



if __name__=='__main__' :
    main()
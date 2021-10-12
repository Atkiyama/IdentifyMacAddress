import sys
import pandas as pd
import numpy as np

#コマンドライン引数 1学習ファイル名 2テストファイル 3閾値P 4書き込みファイル名
args = sys.argv

def main(arg1,arg2,arg3,arg4):
    capture = pd.read_csv(arg1, sep=",")
    P = int(arg3)
    print(capture)
    return 0

main(args[1],args[2],args[3],args[4])

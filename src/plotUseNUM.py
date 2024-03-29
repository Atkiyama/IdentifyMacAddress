import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

plt.rcParams['pdf.fonttype'] = 42
plt.rcParams['font.size'] = 16
plt.rcParams['axes.labelsize'] = 19
plt.rcParams['axes.titlesize'] = 24
plt.rcParams['xtick.labelsize'] = 20
plt.rcParams['ytick.labelsize'] = 20
plt.rcParams['legend.fontsize'] = 19
plt.rcParams['legend.loc'] = 'lower right'
plt.rcParams['figure.figsize'] = (8, 6)
plt.rcParams['figure.dpi'] = 100

datafile_separator = ","
for i in [75, 150, 225, 300]:
    plt.figure()

    plt.xlabel("The number of devices M")
    plt.ylabel("Accuracy")
    plt.ylim(0, 100)
    plt.xlim(0, 20)

    # CSVファイルを読み取る
    data1 = pd.read_csv("data/result/evaluation/ver3/actual/USE_NUM/timeDiff_" + str(i) + ".csv", header=None, delimiter=datafile_separator)
    data2 = pd.read_csv("data/result/evaluation/ver3/actual/USE_NUM/liner_" + str(i) + ".csv", header=None, delimiter=datafile_separator)
    data3 = pd.read_csv("data/result/evaluation/ver3/actual/USE_NUM/combine_liner_dist_" + str(i) + ".csv", header=None, delimiter=datafile_separator)
    #data4 = pd.read_csv("data/result/evaluation/ver3/actual/USE_NUM/packetDiff_" + str(i) + ".csv", header=None, delimiter=datafile_separator)
    

    # Pandas DataFrameからNumPy配列に変換
    data1 = data1.values
    data2 = data2.values
    data3 = data3.values
  #  data4 = data4.values

    plt.plot(data1[:, 0], data1[:, 1] * 100, linewidth=6, label="Time-based method", linestyle=":", color="dodgerblue")
    plt.plot(data2[:, 0], data2[:, 1] * 100, linewidth=6, label="RSSI-based method", linestyle="--", color="green")
    plt.plot(data3[:, 0], data3[:, 1] * 100, linewidth=6, label="Proposed method", linestyle="-", color="magenta")
   # plt.plot(data3[:, 0], data3[:, 1] * 100, linewidth=6, label="Packet diff method", linestyle="--", color="red")
    #plt.plot(data3[:, 0], data3[:, 1] * 100, linewidth=6, label="Packet diff method", linestyle="--", color="red")

    plt.legend(loc='lower right', frameon=False)
    D=i*2

    plt.savefig("data/result/graph/ver3/m" + str(D) + ".pdf")

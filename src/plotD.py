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

plt.xlabel("D")
plt.ylabel("Accuracy")
plt.ylim(0, 100)
plt.xlim(0, 300)

datafile_separator = ","

# CSVファイルを読み取る
data1 = pd.read_csv("data/result/evaluation/ver3/actual/D/timeDiff_D.csv", header=None,delimiter=datafile_separator)
data2 = pd.read_csv("data/result/evaluation/ver3/actual/D/liner_D.csv",header=None, delimiter=datafile_separator)
data3 = pd.read_csv("data/result/evaluation/ver3/actual/D/combine_liner_dist_D.csv", header=None,delimiter=datafile_separator)

# Pandas DataFrameからNumPy配列に変換
data1 = data1.values
data2 = data2.values
data3 = data3.values




plt.plot(data1[:, 0], data1[:, 1]*100, linewidth=6, label="LA using time (traditional)", linestyle=":",color="dodgerblue")
plt.plot(data2[:, 0], data2[:, 1]*100, linewidth=6, label="LA using RSSI", linestyle="--",color="green")
plt.plot(data3[:, 0], data3[:, 1]*100, linewidth=6, label="LA using time and RSSI (proposal) ", linestyle="-",color="magenta")

plt.legend(loc='lower right',frameon=False)

plt.savefig("data/result/graph/ver3/d.pdf")

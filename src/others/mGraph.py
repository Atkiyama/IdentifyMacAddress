import numpy as np
import matplotlib.pyplot as plt

plt.rcParams['pdf.fonttype'] = 42
plt.rcParams['font.size'] = 16
plt.rcParams['axes.labelsize'] = 19
plt.rcParams['axes.titlesize'] = 24
plt.rcParams['xtick.labelsize'] = 20
plt.rcParams['ytick.labelsize'] = 20

plt.rcParams['legend.fontsize'] = 20
plt.rcParams['legend.loc'] = 'lower right'

plt.rcParams['figure.figsize'] = (8, 6)
plt.rcParams['figure.dpi'] = 100

plt.xlabel("端末台数")
plt.ylabel("accuracy")
plt.ylim(75, 100)
plt.xlim(0, 20)

datafile_separator = ","

data = np.loadtxt("data/result/evaluation/move/M,oldLiner.txt", delimiter=datafile_separator, skiprows=0)
plt.plot(data[:, 0], data[:, 1], linewidth=6, label="Traditional (LR)", linestyle="-")

data = np.loadtxt("data/result/evaluation/move/M,linerRegression.txt", delimiter=datafile_separator, skiprows=0)
plt.plot(data[:, 0], data[:, 1], linewidth=6, label="Proposal (LR)", linestyle="-")

data = np.loadtxt("data/result/evaluation/move/M,svr.txt", delimiter=datafile_separator, skiprows=0)
plt.plot(data[:, 0], data[:, 1], linewidth=6, label="Proposal (SVR)", linestyle="-")

plt.legend(loc='lower right')

plt.savefig("data/result/graph/graph/move/m.pdf")

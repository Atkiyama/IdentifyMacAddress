import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import bisect

# CSVファイルからデータを読み込む（ヘッダーがない場合）
df_timeDiff = pd.read_csv('data/result/evaluation/ver3/diff_timeDiff_20.csv', header=None, names=['values'])

# データをソート
sorted_timeDiff = np.sort(df_timeDiff['values'])

# 累積分布関数（CDF）の計算
cdf_timeDiff = np.arange(1, len(sorted_timeDiff) + 1) / len(sorted_timeDiff)


# CSVファイルからデータを読み込む（ヘッダーがない場合）
df_liner = pd.read_csv('data/result/evaluation/ver3/diff_liner_20.csv', header=None, names=['values'])

# データをソート
sorted_liner = np.sort(df_liner['values'])

# sorted_linerの値を0.14倍する
sorted_liner *= 0.14

# 累積分布関数（CDF）の計算
cdf_liner = np.arange(1, len(sorted_liner) + 1) / len(sorted_liner)


# CDFをプロット
plt.figure(figsize=(8, 6))
plt.step(sorted_timeDiff, cdf_timeDiff, label='time',linewidth=5.0)
plt.step(sorted_liner, cdf_liner, label='RSSI',linewidth=5.0)
#plt.legend(fontsize='large')
plt.legend(prop={'size': 20})
plt.xticks(fontsize=20)  # x軸のフォントサイズを12に設定
plt.yticks(fontsize=20)  # y軸のフォントサイズを12に設定
plt.xlim(0, 14)
plt.savefig("data/result/evaluation/ver3/cdf2.pdf")

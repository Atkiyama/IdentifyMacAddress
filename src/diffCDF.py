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

# 累積分布関数（CDF）の計算
cdf_liner = np.arange(1, len(sorted_liner) + 1) / len(sorted_liner)

# CDFをプロット
plt.figure(figsize=(8, 6))
plt.step(sorted_timeDiff, cdf_timeDiff, label='timeDiff')
plt.step(sorted_liner, cdf_liner, label='liner')
plt.xlabel('Values')
plt.ylabel('Cumulative Probability')
plt.title('Cumulative Distribution Function (CDF)')
plt.legend()
plt.grid(True)
plt.savefig("data/result/evaluation/ver3/diffCDF_20.png")

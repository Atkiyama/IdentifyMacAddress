import pandas as pd
import numpy as np
import bisect

# CSVファイルからデータを読み込む（ヘッダーがない場合）
df_liner = pd.read_csv('data/result/evaluation/ver3/diff_liner_20.csv', header=None, names=['values'])

# データをソート
sorted_liner = np.sort(df_liner['values'])

# 累積分布関数（CDF）の計算
cdf_liner = np.arange(1, len(sorted_liner) + 1) / len(sorted_liner)

# 0.9に最も近い値を求める
target_prob = 0.9
idx = bisect.bisect_left(cdf_liner, target_prob)

# CDFのy軸の値が0.9に最も近い値を表示
print("CDFのy軸の値が0.9に最も近い値:", sorted_liner[idx])

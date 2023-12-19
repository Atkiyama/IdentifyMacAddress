import matplotlib.pyplot as plt
import numpy as np

# グラフの描画範囲
x = np.linspace(0, 10, 100)
y1 = 30 - 5 * x
y2 = 10 - x

# y1以下かつy2以下の領域を特定して色をつける
feasible_region = np.minimum(y1, y2)
plt.fill_between(x, feasible_region, 0, where=(y1 >= 0) & (y2 >= 0), alpha=0.4, label='problem executable area')

# 不等式制約を描画
plt.plot(x, y1, label=r'$5x_1 + x_2 \geq 30$')
plt.plot(x, y2, label=r'$x_1 + x_2 \geq 10$')

# グラフの設定
plt.xlim((0, 10))
plt.ylim((0, 30))
plt.xlabel(r'$x_1$')
plt.ylabel(r'$x_2$')
plt.legend()
plt.show()

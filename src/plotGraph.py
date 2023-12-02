import networkx as nx
import matplotlib.pyplot as plt

# # 空のグラフを作成
G = nx.Graph()

# # 部分集合AとBを作成
# subset_A = ['A1', 'A2', 'A3']  # 部分集合Aのノード
# subset_B = ['B1', 'B2', 'B3']  # 部分集合Bのノード

# # 部分集合AとBをグラフに追加
# G.add_nodes_from(subset_A, bipartite=0)  # bipartite=0 は部分集合Aを示す
# G.add_nodes_from(subset_B, bipartite=1)  # bipartite=1 は部分集合Bを示す

# # 完全二部グラフを作成
# G.add_edges_from([(a, b) for a in subset_A for b in subset_B])

# グラフの描画#
#pos = nx.bipartite_layout(G, subset_A)  # 部分集合Aを左側に配置
pos=nx.complete_bipartite_graph(3,3)
nx.draw(pos, with_labels=True, node_color='lightblue', node_size=500, font_weight='bold')
plt.title("Bipartite Graph of subsets A and B")
plt.show()
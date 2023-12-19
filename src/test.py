import networkx as nx
import sys

# グラフを作成
G = nx.DiGraph()
edges = [(1, 2, {'capacity': 8, 'weight': 5}),
         (1, 4, {'capacity': 10, 'weight': 3}),
         (2, 4, {'capacity': 5, 'weight': 2}),
         (2, 3, {'capacity': 7, 'weight': 7}),
         (4, 5, {'capacity': 9, 'weight': 4}),
         (4, 3, {'capacity': 6, 'weight': 1}),
         (3, 5, {'capacity': 3, 'weight': 6}),
         (3, 6, {'capacity': 8, 'weight': 8}),
         (5, 6, {'capacity': 7, 'weight': 9})]

G.add_edges_from(edges)

# ノード1からノード6にフローを流すための最小費用パスを見つける
try:
    # ノード1からノード6への最短パスを求める
    min_cost_path = nx.shortest_path(G, source=1, target=6, weight='weight')

    # 最小費用パスの各エッジをフローする
    flow = sys.maxsize  # フロー量を決定
    for i in range(len(min_cost_path) - 1):
        u, v = min_cost_path[i], min_cost_path[i + 1]
        flow_on_edge = G[u][v]['capacity']
        flow = min(flow, flow_on_edge)

    print("最小費用パス:", min_cost_path)
    print("最小費用パスのフロー量:", flow)
    total_cost = sum(G[u][v]['weight'] for u, v in zip(min_cost_path[:-1], min_cost_path[1:]))
    print("最小費用:", total_cost * flow)
except nx.NetworkXNoPath:
    print("パスが存在しません")
except nx.NetworkXError as e:
    print("エラーが発生しました:", e)

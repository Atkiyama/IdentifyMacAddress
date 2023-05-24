import os
import pandas as pd
import matplotlib.pyplot as plt

# 入力ディレクトリと出力ディレクトリのパス
input_dir = 'data/capture/ver3/masterPiece/'
output_dir = 'data/capture/ver3/masterPieceGraph/'

# 出力ディレクトリが存在しない場合は作成
if not os.path.exists(output_dir):
    os.makedirs(output_dir)

# 入力ディレクトリ内のすべてのファイルに対して処理
for filename in os.listdir(input_dir):
    if filename.endswith('.csv'):
        # CSVファイルのパス
        csv_file = os.path.join(input_dir, filename)

        # CSVデータを読み込む
        data = pd.read_csv(csv_file)

        # 横軸をtime、縦軸をrssiとしてグラフを作成
        plt.plot(data['time'], data['rssi'])

        # グラフのタイトルと軸ラベルを設定
        plt.title('RSSI over Time')
        plt.xlabel('Time')
        plt.ylabel('RSSI')

        # 出力ファイルのパス
        output_file = os.path.join(output_dir, f'{os.path.splitext(filename)[0]}.png')

        # グラフを保存
        plt.savefig(output_file)

        # グラフをクリア
        plt.clf()

# 完了メッセージ
print('グラフの作成と保存が完了しました。')

import re

# パターンを作成
uuid_pattern = re.compile(r'fd6f')

# ファイルを開く
with open('test.txt') as f:
    # ファイルの各行について
    for line in f:
        # 行からAdvDataを取得
        if 'AdvData:' in line:
            adv_data = line.split('AdvData: ')[1]
            # AdvDataにUUIDが含まれるかチェック
            if re.search(uuid_pattern, adv_data):
                # ENSパケットを含む行を出力
                print(line)

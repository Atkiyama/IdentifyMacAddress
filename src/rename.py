import os

def add_suffix_to_files(directory_path, suffix):
    # 指定されたディレクトリ内のファイルを取得
    files = os.listdir(directory_path)

    # ファイルを1つずつ処理してリネーム
    for file_name in files:
        # ファイルのフルパスを作成
        old_file_path = os.path.join(directory_path, file_name)

        # 新しいファイル名を作成
        new_file_name = file_name.replace(".csv","") + suffix + ".csv"
        new_file_path = os.path.join(directory_path, new_file_name)
        print(new_file_name)

        # ファイルをリネーム
        os.rename(old_file_path, new_file_path)

# リネームを実行するディレクトリと追加するサフィックスを指定
directory_path = '/Users/akiyamashuuhei/data/IdentifyMacAddress/src/data/result/evaluation/ver3/case/USE_NUM'  # ディレクトリのパスを指定
suffix = '_150'  # 追加するサフィックスを指定

# ファイル名にサフィックスを追加する関数を呼び出し
add_suffix_to_files(directory_path, suffix)

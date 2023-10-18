import sys
import numpy

def main():
    data=[]

    # 標準入力からデータを読み取り、合計とカウントを計算
    for line in sys.stdin:
        try:
            number = float(line)  # パイプから渡されたデータを数値に変換
            data.append(number)
        except ValueError:
            # 数値以外の入力があった場合は無視
            continue
    #引数はデータ数
    print(sys.argv[1]+","+str(numpy.average(data)))

if __name__ == "__main__":
    main()

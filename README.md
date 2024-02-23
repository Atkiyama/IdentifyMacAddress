# IdentifyMacAddress

## 概要

修論のコードです。どんなことをやっていたのかは修論を参照すること(他にも色々論文はありますが改善点があったりバグがあったり間違いがあるので非推奨)

## 注意事項

- ここに記載があるものは修論で使ったものに限定されます
- それ以前の過程で使われたデータやコードも残してはありますが整備してないので参考程度に止めること
- 実行時間がかなりかかります
  - データ生成には数時間、本実験は数日かかります(MacBookPro M1 16Gb 2020 モデルの場合)

## コードの実行方法

### パケットキャプチャ

パケットキャプチャのドキュメント.md を参照

### パラメータ$\alpha$の決定

修論 6.2 章に該当する実験

1\. makeData_USE_NUM.sh を起動して、3000 通りのデータを生成します
2\. researchDiff.sh を起動します
3\. 全ての実験が終わったら結果を diffCDF2.py で出力

### データ数$M$を変更する場合の実験

修論 6.3.1 章に該当する実験

1\. makeData_USE_NUM.sh を起動して、3000 通りのデータを生成します
2\. studyUSE_NUM.sh を起動します($D$ を決定するコマンドライン引数があるので注意)
3\. 全ての実験が終わったら結果を plotUSENUM.py で出力

### MAC アドレス間の時間差$D$を変動させた場合の評価

修論 6.3.2 章に該当する実験

1\. makeData_D.sh を起動して、3000 通りのデータを生成します
2\. studyD.sh を起動します($M$ を決定するコマンドライン引数があるので注意)
3\. 全ての実験が終わったら結果を plotD.py で出力

## 各ファイル、フォルダ　の概要

修論で使われたファイル、フォルダの説明です
ここで記述がないものは未整備です。

### src 以下のファイル

- calcAverageAccuracy.py
  - 実行結果の精度の平均を計算する
- data
  - 色々なデータが入っています(詳細は別セクションで)
- dataAnalyze
  - キャプチャした生データを解析して、実験で使った機器のパケットを抽出するためのパッケージ
- dataAnalyze.sh
  - dataAnalyze を起動するスクリプト
- diffCDF2.py
  - パラメータ$\alpha$の決定に使う
  - アドレス間の時間差と RSSI 差を CDF として出力する
- identifyMethod.py
  - 色々な同一機器推定手法が実装されている
  - コマンドライン引数で処理内容が変化
  - study\*\*.sh 系のスクリプトから呼び出される
- makeData.py
  - 実験データを生成する
  - makeData\_\*.sh 系スクリプトから呼び出される
- makeData_D.sh
  - $D$を変動させる実験用のデータを生成する
- makeData_USE_NUM.sh
  - $M$を変動させる実験用データを生成する
- plotD.py
  - $D$を変動させる実験のグラフを出力
  <!-- plotMasterPiece.py -->
- plotUseNUM.py
  - $M$を変動させた場合のグラフを出力する
- processed
  - 細かなデータ処理を行う
  - 一部未整備(パケットキャプチャ関連に使わないものは未整備)
- researchDiff.py
  - $\alpha$を決定する実験に使うコード
- researchDiff.sh
  - $\alpha$を決定する実験をするスクリプト
- scan.py
  - パケットキャプチャに使うスクリプト
- scan.sh
  - パケットキャプチャをするスクリプト
- script
  - 過去に使用したスクリプトです(未整備)
- simplex.py
  - 単体法の図を作るのに使ったスクリプト
- splitVer3.sh
  - キャプチャファイルを分割するのに使ったスクリプト
- studyD.sh
  - $D$ を変動する実験に使うスクリプト
- studyUSE_NUM.sh
  - $M$ を変動する実験に使うスクリプト
- パケットキャプチャのドキュメント.md
  - パケットキャプチャする方法をまとめたドキュメント

### data 以下のファイル

- capture
  - キャプチャしたデータやその加工データがあるフォルダ
  - single/move/original/
    - 秋山卒論から[ICCE-ASIA2023](https://ieeexplore.ieee.org/abstract/document/10326401?casa_token=sGkqiTnJrqQAAAAA:g-_4m1ZhkbkOlVqa5r1gRkM02UTIIQXW05wBMh_2WOi67Xx5D4gpBFQrunyMdVEibqyKdp1GHX79bQ)までの論文で使ったデータのオリジナル
  - ver3
    - 修論で使ったデータが入っているフォルダ
    - どこに何があるのかはパケットキャプチャのドキュメントを参照
    - 通算三度目のデータ収集なので ver3 となっている
      - 一度目は[IPSJ 関西 2021](https://www.icnl.jp/local/papers/security/wnet/s-akiyama21icceasia-BLERandomMAC.pdf)、二度目は秋山卒論、三度目は[ジャーナル](https://www.icnl.jp/local/papers/security/wnet/s-akiyama24comex-BLERandomMAC.pdf)以降
    - simulate
      - makeData\*\*.sh 系で生成されるファイルの設置場所
      - 容量、ファイル数共に多いので扱いには注意すること
- result
  - evaluation/ver3
    - case
      - 各データごとに結果
    - actual
      - case の結果を集計したデータ
  - graph/ver3
    - 実験結果のグラフの出力結果の置き場所

# IdentifyMacAddress

## 概要

修論のコードです。どんなことをやっていたのかは修論を参照すること(他にも色々論文はありますが改善点があったりバグがあったり間違いがあるので非推奨)

## 注意事項

- ここに記載があるものは修論で使ったものに限定されます
- それ以前の過程で使われたデータやコードも残してはありますが整備してないので参考程度に止めること
- 実行時間がかなりかかります
  - データ生成には数時間、本実験は数日かかります(MacBookPro M1 16Gb 2020 モデルの場合)
- 可能な限り間違いやバグは修正しましたが規模が規模だけにまだ不具合が潜んでいる可能性が 0 とは言い切れません。ご了承ください

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
  - single/move/original
    - 2021 卒論から[ICCE-ASIA2023](https://ieeexplore.ieee.org/abstract/document/10326401?casa_token=sGkqiTnJrqQAAAAA:g-_4m1ZhkbkOlVqa5r1gRkM02UTIIQXW05wBMh_2WOi67Xx5D4gpBFQrunyMdVEibqyKdp1GHX79bQ)までの論文で使ったデータのオリジナル
  - ver3
    - 修論で使ったデータが入っているフォルダ
    - どこに何があるのかはパケットキャプチャのドキュメントを参照
    - 通算三度目のデータ収集なので ver3 となっている
      - 一度目は[IPSJ 関西 2021](https://www.icnl.jp/local/papers/security/wnet/s-akiyama21icceasia-BLERandomMAC.pdf)、二度目は 2021 卒論、三度目は[ジャーナル](https://www.icnl.jp/local/papers/security/wnet/s-akiyama24comex-BLERandomMAC.pdf)以降
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

## お役立ち

- [bluepy のドキュメント](https://ianharvey.github.io/bluepy-doc/)
  - 研究室のパケットキャプチャする系の研究は実は同じ scan.py を使っているケースが多いです
  - なので不具合が見つかったら先生にも報告してあげてください
- [割当問題のハンガリアン法を python で実装してみた](割当問題のハンガリアン法をpythonで実装してみた)
  - 数理計画法というアルゴリズムの延長線上のような学問の知識を使っています
  - 先生が本をもっていると思うのでそれ使ったりして勉強してみてください
  - 数理計画方の基礎->割当問題というルートで学習していくのがベスト
- [Bluetooth® テクノロジーウェブサイト - Bluetooth SIG](https://www.bluetooth.com/ja-jp/)
  - 厳密な仕様はよく見ておくことを推奨します
  - 実装に関係なくても質問とかで聞かれることがあります
  - 特に MAC アドレスの種類の関連、ランダム化の関連等、修論 3 章でまとめた内容を元に要チェックしておくこと
  - 正直アドレスの種類や用途に関しては追いきれなかった部分があるのでチェックしておいてください
- [【サルでもわかる BLE 入門】（８） ペアリング](https://www.musen-connect.co.jp/blog/course/trial-production/ble-beginner-8/)
  - BLE のペアリングについてわかりやすくまとめられている -　仕様書から入るよりもこういったサイトでイメージをつかむ->仕様書を見るの方がいいかも
- [実世界で超頻出！二部マッチング (輸送問題、ネットワークフロー問題）の解法を総整理！](https://qiita.com/drken/items/e805e3f514acceb87602)
  - 割当問題は二部マッチング問題とも言い換えられる。その関連記事
- [Python の scikit-learn の n_jobs の設定ミスによる動作不良](https://qiita.com/KROYO/items/6300b682f0c7a0ac8bd5)
  - 機械学習を使う際のパラメータ調整記事
  - このパラメータ次第で実行速度が変わる
- [【GitHub】学生申請をして無料で GitHub Copilot を使う](https://qiita.com/SNQ-2001/items/796dc5e794ac3f57a945)
  - 作業効率が倍速になるので是非(タダ)
  - github 垢がないとできないのでこれを機に github もやってみるといいかも(ラズパイと MAC 間のデータ以降もスムーズになるのでおすすめ)
- [機械学習 〜 線形モデル（回帰）](https://qiita.com/fujin/items/7f0a7b6fc8fb662f510d)
- [scp コマンド](https://qiita.com/chihiro/items/142ebe6980a498b5d4a7)
  - ラズパイにモニターをつけるのは面倒なので是非習得しておきましょう
  - ssh とか scp をラズパイでするにはラズパイ側での[設定](https://www.indoorcorgielec.com/resources/raspberry-pi/raspberry-pi-ssh/#:~:text=Raspberry%20Pi%E3%81%AESSH%E8%A8%AD%E5%AE%9A&text=%E3%82%B9%E3%82%BF%E3%83%BC%E3%83%88%E3%83%A1%E3%83%8B%E3%83%A5%E3%83%BC%E3%81%8B%E3%82%89%E3%80%81%E3%80%8C%E8%A8%AD%E5%AE%9A%20%2D,OK%E3%80%8D%E3%82%92%E3%82%AF%E3%83%AA%E3%83%83%E3%82%AF%E3%81%97%E3%81%BE%E3%81%99%E3%80%82&text=%E4%BB%A5%E4%B8%8A%E3%81%A7Raspberry%20Pi%E3%81%AESSH%E8%A8%AD%E5%AE%9A%E3%81%AF%E5%AE%8C%E4%BA%86%E3%81%A7%E3%81%99%E3%80%82)が要るので調べて設定しましょう
- [ssh](https://raspi-school.com/ssh/)
- [VS Code エディタ入門 デバック入門](https://zenn.dev/karaage0703/books/80b6999d429abc8051bb/viewer/898591)
  - VS Code のデバッガの記事です
  - これがあればデバッグが爆速になるのでおすすめ

## 今後の方向性

大体以下が考えられるかなと思います

- より大規模な環境(複数観測端末)における実験
- 現状のまま、いろんなデータ(生データではなくこちらで生成した人工データ)による実験
- 機械学習部分の改良
- 特性時間も用いてみる[Associating the Randomized Bluetooth MAC Addresses of a Device](https://ieeexplore.ieee.org/document/9369628)

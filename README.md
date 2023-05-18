# IdentifyMacAddress
卒業研究に使用したプログラム群のリポジトリになります。
src内には主に以下のパッケージが存在します

convertMacAddress
パケットのキャプチャデータを結合します

IdentifyMacAddress
MACアドレスの同定を行います

dataAnalyze
キャプチャデータをアドレス間の時間のみで擬似的に同定します。
MACアドレス変化の正解を追うのに役立つツールです

evaluation
同定精度の評価を行います

graph
gnuplotによるグラフを生成するためのツールです

makeCDF
gnuplotでCDF(累積分布関数)を作成するためのツールです

その他ファイルやフォルダ

regression.py
identifyMaCAddress内にて回帰を行うスクリプトです

scan.py
パケットキャプチャを行うスクリプトです

allMove.sh
結合データ生成,同定,評価を一気に行います

compile.sh
全てのjavaファイルを一気にコンパイルします

convertMacAddress.sh
結合データを一気に100パターン作成します

dataAnalyze.sh
全キャプチャデータに対してdataAnalyzeパッケージ内のプログラムを使用します

evaluationMove.sh
全ての同定結果に対して評価を行います

identifyMove.sh
全結合データに対して同定を行います

data
本システムに使うデータが入っています(未公開)

script
過去に使用したスクリプトです
現在使用してもまともに動かないものが大半ですのでご注意ください


**ドキュメントについて
doc/index.htmlに本リポジトリのjavadocドキュメントがあります。

**dataフォルダについて

dataフォルダはsrc以下に保存してください。(getData.shを使用すればその通りになります.scpirtフォルダ内にあります)

詳しい説明はdataフォルダのREADMEファイルをご確認ください

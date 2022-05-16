#bin/bash
#バックグラウンド側で実行しないこと
numOfData=$1
R=15
T=6
I=15
java processed/lineUp/LineUp $numOfData
for method in randomForest bagging svr linerRegression
do
  ./identifyForLineUp2_sub.sh $numOfData $method $R $T $I&
done

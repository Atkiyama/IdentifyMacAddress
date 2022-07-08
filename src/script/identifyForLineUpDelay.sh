#bin/bash
#バックグラウンド側で実行しないこと
numOfData=$1
delay=$2

java processed/lineUp/LineUp $numOfData $delay
for method in randomForest bagging svr linerRegression
do
  ./identifyForLineUpDelay_sub.sh $numOfData $method $delay &
done

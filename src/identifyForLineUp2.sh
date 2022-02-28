#bin/bash
#バックグラウンド側で実行しないこと
numOfData=$1

java processed/lineUp/LineUp $numOfData
for method in randomForest bagging svr linerRegression
do
  ./identifyForLineUp_2sub.sh $numOfData $method &
done

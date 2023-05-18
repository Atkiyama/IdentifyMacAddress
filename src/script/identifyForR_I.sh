#bin/bash
#バックグラウンド側で実行しないこと
numOfData=$1
java processed/delay/DelayForR_I $numOfData
for method in randomForest svr linerRegression
do
  ./identifyForR_I_sub.sh $numOfData $method &
done

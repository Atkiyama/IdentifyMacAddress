#bin/bash
#バックグラウンド側で実行しないこと
numOfData=$1
java delay/DelayForR_I $numOfData
for method in bagging svr linerRegression
do
  ./identifyForR_I_sub.sh $numOfData $method &
done

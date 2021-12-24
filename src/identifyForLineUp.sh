#bin/bash
#バックグラウンド側で実行しないこと
java processed/lineUp/LineUp
for method in bagging svr linerRegression
do
  ./identifyForR_I_sub.sh $numOfData $method &
done

#bin/bash
#バックグラウンド側で実行しないこと
for numOfData in 20 5 10 15
do
  java delay/DelayForR_I $numOfData
  for method in bagging svr linerRegression
  do
    python $method.py
    ./idenfityForR_I_sub.sh $numOfData $method &
  done
done

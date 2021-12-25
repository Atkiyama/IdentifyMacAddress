#bin/bash
#バックグラウンド側で実行しないこと
java processed/lineUp/LineUp
python regressionForLineUp.py
for method in bagging svr linerRegression
do
  ./identifyForLineUp_sub.sh $method &
done

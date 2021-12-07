#bin/bash
#バックグラウンド側で実行しないこと
for numOfData in 20 5 10 15
do
  java delay/DelayForR_I $numOfData
  ./identifyForR_I_sub1.sh $numOfData
  echo "$numOfData is finish"
done

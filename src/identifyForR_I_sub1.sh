#bin/bash

numOfData=$1
for method in bagging svr linerRegression
do
  ./identifyForR_I_sub2 $numOfData $method &
done

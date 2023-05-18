#bin/bash
rm -rf data/address/processed/
mkdir data/address/processed
mkdir data/address/processed/addressList
mkdir data/address/processed/fAddress
mkdir data/address/processed/lAddress
mkdir  data/address/processed/regression
mkdir  data/address/processed/regression/svr
mkdir  data/address/processed/regression/bagging
mkdir  data/address/processed/regression/linerRegression
mkdir  data/address/processed/regression/randomForest
mkdir  data/address/processed/regression/approximate
mkdir  data/address/processed/costTable
mkdir  data/address/processed/costTable/old
mkdir  data/address/processed/costTable/svr
mkdir  data/address/processed/costTable/bagging
mkdir  data/address/processed/costTable/linerRegression
mkdir  data/address/processed/costTable/randomForest
mkdir  data/address/processed/costTable/approximate
mkdir  data/address/processed/costTable/timeDifference
rm -rf data/capture/convert/move/
mkdir data/capture/convert/move
rm -rf data/result/multi/move
#windows用なのでMACではエラー出るけど気にしないこと
#mkdir  data/result/multi
mkdir  data/result/multi/move
mkdir  data/result/multi/move/svr/
mkdir  data/result/multi/move/svr/{0..100}
mkdir  data/result/multi/move/approximate/
mkdir  data/result/multi/move/approximate/{0..100}
mkdir  data/result/multi/move/distance/
mkdir  data/result/multi/move/distance/{0..100}
mkdir  data/result/multi/move/false/
mkdir  data/result/multi/move/false/{0..100}
mkdir  data/result/multi/move/timeDifference/
mkdir  data/result/multi/move/timeDifference/{0..100}
mkdir  data/result/multi/move/bagging
mkdir  data/result/multi/move/linerRegression
mkdir  data/result/multi/move/randomForest
mkdir  data/result/multi/move/old
mkdir  data/result/multi/move/bagging/{0..100}
mkdir  data/result/multi/move/linerRegression/{0..100}
mkdir  data/result/multi/move/randomForest/{0..100}
mkdir  data/result/multi/move/old/{0..100}
#g++ -O3 identifyMacAddress/new/identifyMacAddress.cpp -o identify

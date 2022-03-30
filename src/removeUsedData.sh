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
rm -rf data/capture/convert/move/
mkdir data/capture/convert/move
rm -rf data/result/multi/move
mkdir  data/result/multi/move
mkdir  data/result/multi/move/svr/
mkdir  data/result/multi/move/svr/{0..100}
mkdir  data/result/multi/move/bagging
mkdir  data/result/multi/move/linerRegression
mkdir  data/result/multi/move/randomForest
mkdir  data/result/multi/move/old
mkdir  data/result/multi/move/bagging/{0..100}
mkdir  data/result/multi/move/linerRegression/{0..100}
mkdir  data/result/multi/move/randomForest/{0..100}
mkdir  data/result/multi/move/old/{0..100}

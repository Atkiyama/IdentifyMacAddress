#bin/bash

#ディレクトリ内の全てのクラスファイルを削除後、再コンパイルします
find ./ -name "*.class" -delete
javac others/*.java &
javac dataAnalyze/*.java &
javac evaluation/evaluation/*.java &
javac evaluation/table/move/*.java &
javac evaluation/table/stay/*.java &
javac graph/Graph.java &
javac graph/makeCDF/MakeCDF.java &
javac identifyMacAddress/identify/*.java &
javac processed/lineUp/*.java &
javac processed/extract/*.java &
javac processed/delay/*.java &
javac processed/timeAdjustment/*.java &
javac processed/selectAddress/*.java &
g++ -O3 identifyMacAddress/new/identifyMacAddress.cpp -o identify &
g++ -O3 identifyMacAddress/average/identifyAverage.cpp -o identifyAverage &
g++ -O3 identifyMacAddress/distance/identifyDistance.cpp -o identifyDistance
g++ -O3 identifyMacAddress/false/identifyMacAddress.cpp -o identifyFalse
g++ -O3 identifyMacAddress/allocation/identifyMacAddress.cpp -o identifyAllocation
g++ -O3 identifyMacAddress/timeDifference/identifyMacAddress.cpp -o identifyTimeDifference

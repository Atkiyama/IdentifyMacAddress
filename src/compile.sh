#bin/bash

#ディレクトリ内の全てのクラスファイルを削除後、再コンパイルします
find ./ -name "*.class" -delete
g++ -O3 identifyMacAddress/new/identifyMacAddress.cpp -o identify
g++ -O3 identifyMacAddress/avarage/identifyAverage.cpp -o identifyAverage
g++ -O3 identifyMacAddress/distance/identifyDistance.cpp -o identifyDistance
javac dataAnalyze/DataAnalyze.java
javac evaluation/evaluation/*.java
javac evaluation/table/move/*.java
javac evaluation/table/stay/*.java
javac graph/Graph.java
javac graph/makeCDF/MakeCDF.java
javac identifyMacAddress/identify/*.java
javac processed/lineUp/*.java
javac processed/extract/*.java
javac processed/delay/*.java

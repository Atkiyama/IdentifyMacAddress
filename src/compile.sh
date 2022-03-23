#bin/bash

#ディレクトリ内の全てのクラスファイルを削除後、再コンパイルします
find ./ -name "*.class" -delete
javac dataAnalyze/DataAnalyze.java
javac evaluation/evaluation/*.java
javac /Users/akiyamashuuhei/data/IdentifyMacAddress/src/evaluation/table/move/*.java
javac /Users/akiyamashuuhei/data/IdentifyMacAddress/src/evaluation/table/stay/*.java
javac /Users/akiyamashuuhei/data/IdentifyMacAddress/src/graph/makeCDF/MakeCDF.java
javac /Users/akiyamashuuhei/data/IdentifyMacAddress/src/identifyMacAddress/identify/*.java
javac processed/lineUp/*.java
javac processed/extract/*.java
javac processed/delay/*.java

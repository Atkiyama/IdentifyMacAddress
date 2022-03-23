#bin/bash

#ディレクトリ内の全てのクラスファイルを削除後、再コンパイルします
find ./ -name "*.class" -delete
javac dataAnalyze/DataAnalyze.java -encoding UTF-8
javac evaluation/evaluation/*.java -encoding UTF-8
javac /Users/akiyamashuuhei/data/IdentifyMacAddress/src/evaluation/table/move/*.java -encoding UTF-8
javac /Users/akiyamashuuhei/data/IdentifyMacAddress/src/evaluation/table/stay/*.java -encoding UTF-8
javac /Users/akiyamashuuhei/data/IdentifyMacAddress/src/graph/makeCDF/MakeCDF.java -encoding UTF-8
javac /Users/akiyamashuuhei/data/IdentifyMacAddress/src/identifyMacAddress/identify/*.java -encoding UTF-8
javac processed/lineUp/*.java -encoding UTF-8
javac processed/extract/*.java -encoding UTF-8
javac processed/delay/*.java -encoding UTF-8

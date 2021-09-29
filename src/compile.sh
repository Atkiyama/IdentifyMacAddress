#!/bin/bash

javac dataAnalyze/analyze/DataAnalyze.java -encoding UTF-8
javac convertMacAddress/ConvertMacAddress.java -encoding UTF-8
javac identifyMacAddress/identify/IdentifyStay.java -encoding UTF-8
javac identifyMacAddress/read/ReadTXT.java -encoding UTF-8
javac identifyMacAddress/read/ReadCSV.java -encoding UTF-8
javac evaluation/Evaluation.java -encoding UTF-8
javac evaluation/table/EvaluationTable.java -encoding UTF-8
#javac evaluation/Evaluation100.java -encoding UTF-8
javac makeCDF/MakeCDF.java -encoding UTF-8
javac graph/stay/Graph.java -encoding UTF-8
javac graph/singleAddress/Graph.java -encoding UTF-8
javac evaluation/Evaluation2.java -encoding UTF-8
javac evaluation/Evaluation100_2.java -encoding UTF-8
echo "全javaファイルのコンパイルが完了しました"

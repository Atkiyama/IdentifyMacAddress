#!/bin/bash

javac dataAnalyze/DataAnalyze.java -encoding UTF-8
javac convertMacAddress/ConvertMacAddress.java -encoding UTF-8
javac convertMacAddress/ConvertOriginal.java -encoding UTF-8
javac identifyMacAddress/identify/IdentifyStay.java -encoding UTF-8
javac identifyMacAddress/identify/IdentifyMove.java -encoding UTF-8
javac identifyMacAddress/io/ReadTXT.java -encoding UTF-8
javac identifyMacAddress/io/ReadCSV.java -encoding UTF-8
javac evaluation/evaluation/Evaluation.java -encoding UTF-8
javac evaluation/table/stay/EvaluationTable.java -encoding UTF-8
#javac evaluation/evaluation/Evaluation100.java -encoding UTF-8
javac makeCDF/MakeCDF.java -encoding UTF-8
javac graph/stay/Graph.java -encoding UTF-8
javac graph/SingleAddress.java -encoding UTF-8
javac evaluation/evaluation/Evaluation2.java -encoding UTF-8
javac evaluation/evaluation/Evaluation100_2.java -encoding UTF-8
javac evaluation/evaluation/EvaluationMove.java -encoding UTF-8
echo "全javaファイルのコンパイルが完了しました"

#bin/bash

#ディレクトリ内の全てのクラスファイルを削除後、再コンパイルします
find ./ -name "*.class" -delete
javac dataAnalyze/DataAnalyze.java -encoding utf-8
javac evaluation/evaluation/*.java -encoding utf-8
javac evaluation/table/move/EvaluationTable.java -encoding utf-8
javac evaluation/table/stay/EvaluationTable.java -encoding utf-8
javac graph/makeCDF/MakeCDF.java -encoding utf-8
javac identifyMacAddress/identify/IdentifyMove.java -encoding utf-8
javac identifyMacAddress/identify/IdentifyStay.java -encoding utf-8
javac processed/lineUp/*.java -encoding utf-8
javac processed/extract/*.java -encoding utf-8
javac processed/delay/*.java -encoding utf-8

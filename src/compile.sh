#bin/bash

#ディレクトリ内の全てのクラスファイルを削除後、再コンパイルします
find ./ -name "*.class" -delete
<<<<<<< HEAD
javac dataAnalyze/DataAnalyze.java -encoding UTF-8
javac evaluation/evaluation/*.java -encoding UTF-8
javac evaluation/table/move/*.java -encoding UTF-8
javac evaluation/table/stay/*.java -encoding UTF-8
javac graph/makeCDF/MakeCDF.java -encoding UTF-8
javac identifyMacAddress/identify/*.java -encoding UTF-8
javac processed/lineUp/*.java -encoding UTF-8
javac processed/extract/*.java -encoding UTF-8
javac processed/delay/*.java -encoding UTF-8
=======
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
>>>>>>> 29c831cf96fe48cc2024d8a87c410dcbe59e9f22

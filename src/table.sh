#bin/bash

numOfTime=$1
#コマンドライン引数
LS=$(ls data/result/evaluation/move/$numOfTime/)

for inputFileName in ${LS}; do
    java evaluation/table/move/EvaluationTable data/result/evaluation/move/$numOfTime/$inputFileName data/result/table/move/$numOfTime/$inputFileName
done

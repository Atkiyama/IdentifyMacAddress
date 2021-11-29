#bin/bash


#コマンドライン引数
LS=$(ls data/result/evaluation/move/)

for inputFileName in ${LS}; do
    java evaluation/table/move/EvaluationTable data/result/evaluation/move/$numOfTime/$inputFileName data/result/table/move/$inputFileName
done

#!/bin/bash
USE_NUM=20
# 変数Dを0から300まで10ずつ増加させるループ
for D in $(seq 0 10 300)
do
    for CASE_NUM in {1001..3000}
    do
        #echo "data/capture/ver3/simulate/data_${USE_NUM}_${CASE_NUM}_${D}.csv"
        # CASE_NUMの8の剰余が0の場合
        if [ $((CASE_NUM % 6)) -eq 5 ]; then
            python makeData.py $USE_NUM $CASE_NUM $D > "data/capture/ver3/simulate/D/data_${USE_NUM}_${CASE_NUM}_${D}.csv"
        else
            # CASE_NUMの8の剰余が0でない場合
            python makeData.py $USE_NUM $CASE_NUM $D> "data/capture/ver3/simulate/D/data_${USE_NUM}_${CASE_NUM}_${D}.csv" &
        fi
    done
    echo "${D} is done"
done

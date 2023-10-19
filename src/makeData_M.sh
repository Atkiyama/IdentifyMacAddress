#bin/bash
D=150
for USE_NUM in {1..20}
do
    for CASE_NUM in {1..1000}
    do
        # CASE_NUMの8の剰余が0の場合
        if [ $((CASE_NUM % 8)) -eq 7 ]; then
            python makeData.py $USE_NUM $CASE_NUM $D> "data/capture/ver3/simulate/data_${USE_NUM}_${CASE_NUM}.csv"
        else
            # CASE_NUMの8の剰余が0でない場合
            python makeData.py $USE_NUM $CASE_NUM $D> "data/capture/ver3/simulate/data_${USE_NUM}_${CASE_NUM}.csv" &
        fi
    done
    echo "${USE_NUM} is done"
done
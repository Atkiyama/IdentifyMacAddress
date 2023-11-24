#bin/bash
D=150
for D in 75 225 300
do
    for USE_NUM in {1..20}
    do
        for CASE_NUM in {1..3000}
        do
            # CASE_NUMの8の剰余が0の場合
            if [ $((CASE_NUM % 6)) -eq 5 ]; then
                python makeData.py $USE_NUM $CASE_NUM $D> "data/capture/ver3/simulate/USE_NUM/data_${USE_NUM}_${CASE_NUM}_${D}.csv"
            else
                # CASE_NUMの8の剰余が0でない場合
                python makeData.py $USE_NUM $CASE_NUM $D> "data/capture/ver3/simulate/USE_NUM/data_${USE_NUM}_${CASE_NUM}_${D}.csv" &
            fi
        done
        echo "${USE_NUM} is done"
    done
done
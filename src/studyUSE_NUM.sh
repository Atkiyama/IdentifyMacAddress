#bin/bash
#データ作成 60 *1000

D=$1
# D= 75 150 225 300の4ケース 論文上は150 300 450 600
for USE_NUM in {1..20}
do
    for CASE_NUM in {1..3000}
    do
        if [ $CASE_NUM -eq "1" ]; then
            cat data/capture/ver3/simulate/USE_NUM/data_${USE_NUM}_${CASE_NUM}_${D}.csv| python identifyMethod.py timeDiff ${USE_NUM} ${CASE_NUM}> data/result/evaluation/ver3/case/USE_NUM/timeDiff_${USE_NUM}_${D}.csv &
            cat data/capture/ver3/simulate/USE_NUM/data_${USE_NUM}_${CASE_NUM}_${D}.csv| python identifyMethod.py liner ${USE_NUM} ${CASE_NUM}> data/result/evaluation/ver3/case/USE_NUM/liner_${USE_NUM}_${D}.csv &
            cat data/capture/ver3/simulate/USE_NUM/data_${USE_NUM}_${CASE_NUM}_${D}.csv| python identifyMethod.py combine_liner_dist 0.14 1 ${USE_NUM} ${CASE_NUM}> data/result/evaluation/ver3/case/USE_NUM/combine_liner_dist_${USE_NUM}_${D}.csv
        else
            cat data/capture/ver3/simulate/USE_NUM/data_${USE_NUM}_${CASE_NUM}_${D}.csv| python identifyMethod.py timeDiff ${USE_NUM} ${CASE_NUM}>> data/result/evaluation/ver3/case/USE_NUM/timeDiff_${USE_NUM}_${D}.csv &
            cat data/capture/ver3/simulate/USE_NUM/data_${USE_NUM}_${CASE_NUM}_${D}.csv| python identifyMethod.py liner ${USE_NUM} ${CASE_NUM}>> data/result/evaluation/ver3/case/USE_NUM/liner_${USE_NUM}_${D}.csv &
            cat data/capture/ver3/simulate/USE_NUM/data_${USE_NUM}_${CASE_NUM}_${D}.csv| python identifyMethod.py combine_liner_dist 0.14 1 ${USE_NUM} ${CASE_NUM}>> data/result/evaluation/ver3/case/USE_NUM/combine_liner_dist_${USE_NUM}_${D}.csv
        fi
    done
    
    if [ $USE_NUM -eq "1" ]; then
        cat data/result/evaluation/ver3/case/USE_NUM/timeDiff_${USE_NUM}_${D}.csv |python calcAverageAccuracy.py ${USE_NUM}>data/result/evaluation/ver3/actual/USE_NUM/timeDiff_${D}.csv &
        cat data/result/evaluation/ver3/case/USE_NUM/liner_${USE_NUM}_${D}.csv |python calcAverageAccuracy.py ${USE_NUM}>data/result/evaluation/ver3/actual/USE_NUM/liner_${D}.csv &
        cat data/result/evaluation/ver3/case/USE_NUM/combine_liner_dist_${USE_NUM}_${D}.csv |python calcAverageAccuracy.py ${USE_NUM}>data/result/evaluation/ver3/actual/USE_NUM/combine_liner_dist_${D}.csv &
    else
        cat data/result/evaluation/ver3/case/USE_NUM/timeDiff_${USE_NUM}_${D}.csv |python calcAverageAccuracy.py ${USE_NUM}>>data/result/evaluation/ver3/actual/USE_NUM/timeDiff_${D}.csv &
        cat data/result/evaluation/ver3/case/USE_NUM/liner_${USE_NUM}_${D}.csv |python calcAverageAccuracy.py ${USE_NUM}>>data/result/evaluation/ver3/actual/USE_NUM/liner_${D}.csv &
        cat data/result/evaluation/ver3/case/USE_NUM/combine_liner_dist_${USE_NUM}_${D}.csv |python calcAverageAccuracy.py ${USE_NUM}>>data/result/evaluation/ver3/actual/USE_NUM/combine_liner_dist_${D}.csv &
    fi
    echo "${USE_NUM} is done"
done
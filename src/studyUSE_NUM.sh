#bin/bash
#データ作成 60 *1000


for USE_NUM in {1..20}
do
    for CASE_NUM in {1..1000}
    do
        if [ $CASE_NUM -eq "1" ]; then
            cat data/capture/ver3/simulate/USE_NUM/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod.py timeDiff ${USE_NUM} ${CASE_NUM}> data/result/evaluation/ver3/case/USE_NUM/timeDiff_${USE_NUM}.csv &
            cat data/capture/ver3/simulate/USE_NUM/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod.py liner ${USE_NUM} ${CASE_NUM}> data/result/evaluation/ver3/case/USE_NUM/liner_${USE_NUM}.csv &
            cat data/capture/ver3/simulate/USE_NUM/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod.py combine_liner_dist 0.14 1 ${USE_NUM} ${CASE_NUM}> data/result/evaluation/ver3/case/USE_NUM/combine_liner_dist_${USE_NUM}.csv
        else
            cat data/capture/ver3/simulate/USE_NUM/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod.py timeDiff ${USE_NUM} ${CASE_NUM}>> data/result/evaluation/ver3/case/USE_NUM/timeDiff_${USE_NUM}.csv &
            cat data/capture/ver3/simulate/USE_NUM/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod.py liner ${USE_NUM} ${CASE_NUM}>> data/result/evaluation/ver3/case/USE_NUM/liner_${USE_NUM}.csv &
            cat data/capture/ver3/simulate/USE_NUM/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod.py combine_liner_dist 0.14 1 ${USE_NUM} ${CASE_NUM}>> data/result/evaluation/ver3/case/USE_NUM/combine_liner_dist_${USE_NUM}.csv
        fi
    done
    
    if [ $USE_NUM -eq "20" ]; then
        cat data/result/evaluation/ver3/case/USE_NUM/timeDiff_${USE_NUM}.csv |python calcAverageAccuracy.py ${USE_NUM}>data/result/evaluation/ver3/actual/USE_NUM/timeDiff.csv &
        cat data/result/evaluation/ver3/case/USE_NUM/liner_${USE_NUM}.csv |python calcAverageAccuracy.py ${USE_NUM}>data/result/evaluation/ver3/actual/USE_NUM/liner.csv &
        cat data/result/evaluation/ver3/case/USE_NUM/combine_liner_dist_${USE_NUM}.csv |python calcAverageAccuracy.py ${USE_NUM}>data/result/evaluation/ver3/actual/USE_NUM/combine_liner_dist.csv &
    else
        cat data/result/evaluation/ver3/case/USE_NUM/timeDiff_${USE_NUM}.csv |python calcAverageAccuracy.py ${USE_NUM}>>data/result/evaluation/ver3/actual/USE_NUM/timeDiff.csv &
        cat data/result/evaluation/ver3/case/USE_NUM/liner_${USE_NUM}.csv |python calcAverageAccuracy.py ${USE_NUM}>>data/result/evaluation/ver3/actual/USE_NUM/liner.csv &
        cat data/result/evaluation/ver3/case/USE_NUM/combine_liner_dist_${USE_NUM}.csv |python calcAverageAccuracy.py ${USE_NUM}>>data/result/evaluation/ver3/actual/USE_NUM/combine_liner_dist.csv &
    fi
    echo "${USE_NUM} is done"
done
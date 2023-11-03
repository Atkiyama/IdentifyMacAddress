#bin/bash
#データ作成 60 *1000


for USE_NUM in 5 10 15 20
do
    for CASE_NUM in {1..1000}
    do
        if [ $CASE_NUM -eq "1" ]; then
            # cat data/capture/ver3/simulate/$USE_NUM/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod.py timeDiff ${USE_NUM} ${CASE_NUM}> data/result/evaluation/ver3/case/USE_NUM/timeDiff_${USE_NUM}.csv &
            # cat data/capture/ver3/simulate/$USE_NUM/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod.py liner ${USE_NUM} ${CASE_NUM}> data/result/evaluation/ver3/case/USE_NUM/liner_${USE_NUM}.csv &
            cat data/capture/ver3/simulate/USE_NUM/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod.py combine_liner_dist 1 1 ${USE_NUM} ${CASE_NUM}> data/result/evaluation/ver3/case/USE_NUM/combine_liner_dist_${USE_NUM}_1.csv &
            cat data/capture/ver3/simulate/USE_NUM/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod.py combine_liner_dist 0.75 1 ${USE_NUM} ${CASE_NUM}> data/result/evaluation/ver3/case/USE_NUM/combine_liner_dist_${USE_NUM}_075.csv &
            cat data/capture/ver3/simulate/USE_NUM/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod.py combine_liner_dist 0.5 1 ${USE_NUM} ${CASE_NUM}> data/result/evaluation/ver3/case/USE_NUM/combine_liner_dist_${USE_NUM}_05.csv &
            cat data/capture/ver3/simulate/USE_NUM/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod.py combine_liner_dist 0.25 1 ${USE_NUM} ${CASE_NUM}> data/result/evaluation/ver3/case/USE_NUM/combine_liner_dist_${USE_NUM}_025.csv &
            cat data/capture/ver3/simulate/USE_NUM/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod.py combine_liner_dist 0.14 1 ${USE_NUM} ${CASE_NUM}> data/result/evaluation/ver3/case/USE_NUM/combine_liner_dist_${USE_NUM}_014.csv &
            cat data/capture/ver3/simulate/USE_NUM/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod.py combine_liner_dist 0.15 1 ${USE_NUM} ${CASE_NUM}> data/result/evaluation/ver3/case/USE_NUM/combine_liner_dist_${USE_NUM}_015.csv &
            cat data/capture/ver3/simulate/USE_NUM/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod.py combine_liner_dist 0.14142875947483788 1 ${USE_NUM} ${CASE_NUM}> data/result/evaluation/ver3/case/USE_NUM/combine_liner_dist_${USE_NUM}_alpha.csv
        else
            # cat data/capture/ver3/simulate/$USE_NUM/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod.py timeDiff ${USE_NUM} ${CASE_NUM}>> data/result/evaluation/ver3/case/USE_NUM/timeDiff_${USE_NUM}.csv &
            # cat data/capture/ver3/simulate/$USE_NUM/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod.py liner ${USE_NUM} ${CASE_NUM}>> data/result/evaluation/ver3/case/USE_NUM/liner_${USE_NUM}.csv &
            cat data/capture/ver3/simulate/USE_NUM/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod.py combine_liner_dist 1 1 ${USE_NUM} ${CASE_NUM}>> data/result/evaluation/ver3/case/USE_NUM/combine_liner_dist_${USE_NUM}_1.csv &
            cat data/capture/ver3/simulate/USE_NUM/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod.py combine_liner_dist 0.75 1 ${USE_NUM} ${CASE_NUM}>> data/result/evaluation/ver3/case/USE_NUM/combine_liner_dist_${USE_NUM}_075.csv &
            cat data/capture/ver3/simulate/USE_NUM/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod.py combine_liner_dist 0.5 1 ${USE_NUM} ${CASE_NUM}>> data/result/evaluation/ver3/case/USE_NUM/combine_liner_dist_${USE_NUM}_05.csv &
            cat data/capture/ver3/simulate/USE_NUM/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod.py combine_liner_dist 0.25 1 ${USE_NUM} ${CASE_NUM}>> data/result/evaluation/ver3/case/USE_NUM/combine_liner_dist_${USE_NUM}_025.csv &
            cat data/capture/ver3/simulate/USE_NUM/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod.py combine_liner_dist 0.14 1 ${USE_NUM} ${CASE_NUM}>> data/result/evaluation/ver3/case/USE_NUM/combine_liner_dist_${USE_NUM}_014.csv &
            cat data/capture/ver3/simulate/USE_NUM/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod.py combine_liner_dist 0.15 1 ${USE_NUM} ${CASE_NUM}>> data/result/evaluation/ver3/case/USE_NUM/combine_liner_dist_${USE_NUM}_015.csv &
            cat data/capture/ver3/simulate/USE_NUM/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod.py combine_liner_dist 0.14142875947483788 1 ${USE_NUM} ${CASE_NUM}>> data/result/evaluation/ver3/case/USE_NUM/combine_liner_dist_${USE_NUM}_alpha.csv
        fi
    done
    
    if [ $USE_NUM -eq "20" ]; then
        # cat data/result/evaluation/ver3/case/USE_NUM/timeDiff_${USE_NUM}.csv |python calcAverageAccuracy.py ${USE_NUM}>data/result/evaluation/ver3/actual/timeDiff.csv &
        # cat data/result/evaluation/ver3/case/USE_NUM/liner_${USE_NUM}.csv |python calcAverageAccuracy.py ${USE_NUM}>data/result/evaluation/ver3/actual/liner.csv &
        cat data/result/evaluation/ver3/case/USE_NUM/combine_liner_dist_${USE_NUM}_1.csv |python calcAverageAccuracy.py ${USE_NUM}>data/result/evaluation/ver3/actual/combine_liner_dist_1.csv &
        cat data/result/evaluation/ver3/case/USE_NUM/combine_liner_dist_${USE_NUM}_075.csv |python calcAverageAccuracy.py ${USE_NUM}>data/result/evaluation/ver3/actual/combine_liner_dist_075.csv &
        cat data/result/evaluation/ver3/case/USE_NUM/combine_liner_dist_${USE_NUM}_05.csv |python calcAverageAccuracy.py ${USE_NUM}>data/result/evaluation/ver3/actual/combine_liner_dist_05.csv &
        cat data/result/evaluation/ver3/case/USE_NUM/combine_liner_dist_${USE_NUM}_025.csv |python calcAverageAccuracy.py ${USE_NUM}>data/result/evaluation/ver3/actual/combine_liner_dist_025.csv &
        cat data/result/evaluation/ver3/case/USE_NUM/combine_liner_dist_${USE_NUM}_014.csv |python calcAverageAccuracy.py ${USE_NUM}>data/result/evaluation/ver3/actual/combine_liner_dist_014.csv &
        cat data/result/evaluation/ver3/case/USE_NUM/combine_liner_dist_${USE_NUM}_015.csv |python calcAverageAccuracy.py ${USE_NUM}>data/result/evaluation/ver3/actual/combine_liner_dist_015.csv &
        cat data/result/evaluation/ver3/case/USE_NUM/combine_liner_dist_${USE_NUM}_alpha.csv |python calcAverageAccuracy.py ${USE_NUM}>data/result/evaluation/ver3/actual/combine_liner_dist_alpha.csv &
        
    else
        cat data/result/evaluation/ver3/case/USE_NUM/combine_liner_dist_${USE_NUM}_1.csv |python calcAverageAccuracy.py ${USE_NUM}>>data/result/evaluation/ver3/actual/combine_liner_dist_1.csv &
        cat data/result/evaluation/ver3/case/USE_NUM/combine_liner_dist_${USE_NUM}_075.csv |python calcAverageAccuracy.py ${USE_NUM}>>data/result/evaluation/ver3/actual/combine_liner_dist_075.csv &
        cat data/result/evaluation/ver3/case/USE_NUM/combine_liner_dist_${USE_NUM}_05.csv |python calcAverageAccuracy.py ${USE_NUM}>>data/result/evaluation/ver3/actual/combine_liner_dist_05.csv &
        cat data/result/evaluation/ver3/case/USE_NUM/combine_liner_dist_${USE_NUM}_025.csv |python calcAverageAccuracy.py ${USE_NUM}>>data/result/evaluation/ver3/actual/combine_liner_dist_025.csv &
        cat data/result/evaluation/ver3/case/USE_NUM/combine_liner_dist_${USE_NUM}_014.csv |python calcAverageAccuracy.py ${USE_NUM}>>data/result/evaluation/ver3/actual/combine_liner_dist_014.csv &
        cat data/result/evaluation/ver3/case/USE_NUM/combine_liner_dist_${USE_NUM}_015.csv |python calcAverageAccuracy.py ${USE_NUM}>>data/result/evaluation/ver3/actual/combine_liner_dist_015.csv &
        cat data/result/evaluation/ver3/case/USE_NUM/combine_liner_dist_${USE_NUM}_alpha.csv |python calcAverageAccuracy.py ${USE_NUM}>>data/result/evaluation/ver3/actual/combine_liner_dist_alpha.csv &
    fi
    echo "${USE_NUM} is done"
done
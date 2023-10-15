#bin/bash
#データ作成 60 *1000


for USE_NUM in 20
do
    for CASE_NUM in {1..1000}
    do
        # if [ $CASE_NUM -eq "1" ]; then
        #     cat data/capture/ver3/simulate/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod/timeDiff.py ${USE_NUM} ${CASE_NUM}> data/result/evaluation/ver3/case/timeDiff_${USE_NUM}.csv &
        #     cat data/capture/ver3/simulate/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod/liner.py ${USE_NUM} ${CASE_NUM}> data/result/evaluation/ver3/case/liner_${USE_NUM}.csv &
        #     cat data/capture/ver3/simulate/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod/svr.py ${USE_NUM} ${CASE_NUM}> data/result/evaluation/ver3/case/svr_${USE_NUM}.csv &
        #     cat data/capture/ver3/simulate/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod/combine_liner.py ${USE_NUM} ${CASE_NUM}> data/result/evaluation/ver3/case/combine_liner_${USE_NUM}.csv &
        #     cat data/capture/ver3/simulate/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod/combine_svr.py ${USE_NUM} ${CASE_NUM}> data/result/evaluation/ver3/case/combine_svr_${USE_NUM}.csv
        # else
        #     cat data/capture/ver3/simulate/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod/timeDiff.py ${USE_NUM} ${CASE_NUM}>> data/result/evaluation/ver3/case/timeDiff_${USE_NUM}.csv &
        #     cat data/capture/ver3/simulate/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod/liner.py ${USE_NUM} ${CASE_NUM}>> data/result/evaluation/ver3/case/liner_${USE_NUM}.csv &
        #     cat data/capture/ver3/simulate/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod/svr.py ${USE_NUM} ${CASE_NUM}>> data/result/evaluation/ver3/case/svr_${USE_NUM}.csv &
        #     cat data/capture/ver3/simulate/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod/combine_liner.py ${USE_NUM} ${CASE_NUM}>> data/result/evaluation/ver3/case/combine_liner_${USE_NUM}.csv &
        #     cat data/capture/ver3/simulate/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod/combine_svr.py ${USE_NUM} ${CASE_NUM}>> data/result/evaluation/ver3/case/combine_svr_${USE_NUM}.csv
        # fi
        if [ $CASE_NUM -eq "1" ]; then
            cat data/capture/ver3/simulate/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod.py timeDiff ${USE_NUM} ${CASE_NUM}> data/result/evaluation/ver3/case/timeDiff_${USE_NUM}.csv &
            cat data/capture/ver3/simulate/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod.py liner ${USE_NUM} ${CASE_NUM}> data/result/evaluation/ver3/case/liner_${USE_NUM}.csv &
            cat data/capture/ver3/simulate/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod.py svr ${USE_NUM} ${CASE_NUM}> data/result/evaluation/ver3/case/svr_${USE_NUM}.csv &
            cat data/capture/ver3/simulate/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod.py combine_liner 0.75 1 ${USE_NUM} ${CASE_NUM}> data/result/evaluation/ver3/case/combine_liner_${USE_NUM}.csv &
            cat data/capture/ver3/simulate/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod.py combine_svr 0.75 1 ${USE_NUM} ${CASE_NUM}> data/result/evaluation/ver3/case/combine_svr_${USE_NUM}.csv
        else
            cat data/capture/ver3/simulate/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod.py timeDiff ${USE_NUM} ${CASE_NUM}>> data/result/evaluation/ver3/case/timeDiff_${USE_NUM}.csv &
            cat data/capture/ver3/simulate/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod.py liner ${USE_NUM} ${CASE_NUM}>> data/result/evaluation/ver3/case/liner_${USE_NUM}.csv &
            cat data/capture/ver3/simulate/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod.py svr ${USE_NUM} ${CASE_NUM}>> data/result/evaluation/ver3/case/svr_${USE_NUM}.csv &
            cat data/capture/ver3/simulate/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod.py combine_liner 0.75 1 ${USE_NUM} ${CASE_NUM}>> data/result/evaluation/ver3/case/combine_liner_${USE_NUM}.csv &
            cat data/capture/ver3/simulate/data_${USE_NUM}_${CASE_NUM}.csv| python identifyMethod.py combine_svr 0.75 1 ${USE_NUM} ${CASE_NUM}>> data/result/evaluation/ver3/case/combine_svr_${USE_NUM}.csv
        fi
    done
    
    if [ $USE_NUM -eq "1" ]; then
        cat data/result/evaluation/ver3/case/timeDiff_${USE_NUM}.csv |python calcAverageAccuracy.py >data/result/evaluation/ver3/actual/timeDiff.csv &
        cat data/result/evaluation/ver3/case/liner_${USE_NUM}.csv |python calcAverageAccuracy.py >data/result/evaluation/ver3/actual/liner.csv &
        cat data/result/evaluation/ver3/case/svr_${USE_NUM}.csv |python calcAverageAccuracy.py >data/result/evaluation/ver3/actual/svr.csv &
        cat data/result/evaluation/ver3/case/combine_liner_${USE_NUM}.csv |python calcAverageAccuracy.py >data/result/evaluation/ver3/actual/combine_liner.csv &
        cat data/result/evaluation/ver3/case/combine_svr_${USE_NUM}.csv |python calcAverageAccuracy.py >data/result/evaluation/ver3/actual/combine_svr.csv &
    else
        cat data/result/evaluation/ver3/case/timeDiff_${USE_NUM}.csv |python calcAverageAccuracy.py >>data/result/evaluation/ver3/actual/timeDiff.csv &
        cat data/result/evaluation/ver3/case/liner_${USE_NUM}.csv |python calcAverageAccuracy.py >>data/result/evaluation/ver3/actual/liner.csv &
        cat data/result/evaluation/ver3/case/svr_${USE_NUM}.csv |python calcAverageAccuracy.py >>data/result/evaluation/ver3/actual/svr.csv &
        cat data/result/evaluation/ver3/case/combine_liner_${USE_NUM}.csv |python calcAverageAccuracy.py >>data/result/evaluation/ver3/actual/combine_liner.csv &
        cat data/result/evaluation/ver3/case/combine_svr_${USE_NUM}.csv |python calcAverageAccuracy.py >>data/result/evaluation/ver3/actual/combine_svr.csv &
    fi
    echo "${USE_NUM} is done"
done
#bin/bash
#データ作成 30つづやってみる?

USE_NUM=20
for D in $(seq 0 10 300)
do
    for CASE_NUM in {1..1000}
    do
        if [ $CASE_NUM -eq "1" ]; then
            cat data/capture/ver3/simulate/D/data_${USE_NUM}_${CASE_NUM}_${D}.csv| python identifyMethod.py timeDiff ${USE_NUM} ${CASE_NUM} ${D}> data/result/evaluation/ver3/case/D/timeDiff_${USE_NUM}_${D}.csv &
            cat data/capture/ver3/simulate/D/data_${USE_NUM}_${CASE_NUM}_${D}.csv| python identifyMethod.py liner ${USE_NUM} ${CASE_NUM} ${D}> data/result/evaluation/ver3/case/D/liner_${USE_NUM}_${D}.csv &
            cat data/capture/ver3/simulate/D/data_${USE_NUM}_${CASE_NUM}_${D}.csv| python identifyMethod.py combine_liner_dist 0.14 1 ${USE_NUM} ${CASE_NUM} ${D}> data/result/evaluation/ver3/case/D/combine_liner_dist_${USE_NUM}_${D}.csv
        else
            cat data/capture/ver3/simulate/D/data_${USE_NUM}_${CASE_NUM}_${D}.csv| python identifyMethod.py timeDiff ${USE_NUM} ${CASE_NUM} ${D}>> data/result/evaluation/ver3/case/D/timeDiff_${USE_NUM}_${D}.csv &
            cat data/capture/ver3/simulate/D/data_${USE_NUM}_${CASE_NUM}_${D}.csv| python identifyMethod.py liner ${USE_NUM} ${CASE_NUM} ${D}>> data/result/evaluation/ver3/case/D/liner_${USE_NUM}_${D}.csv &
            cat data/capture/ver3/simulate/D/data_${USE_NUM}_${CASE_NUM}_${D}.csv| python identifyMethod.py combine_liner_dist 0.14 1 ${USE_NUM} ${CASE_NUM} ${D}>> data/result/evaluation/ver3/case/D/combine_liner_dist_${USE_NUM}_${D}.csv
        fi
    done
    
    if [ $D -eq "0" ]; then
        cat data/result/evaluation/ver3/case/D/timeDiff_${USE_NUM}_${D}.csv |python calcAverageAccuracy.py ${D}>data/result/evaluation/ver3/actual/D/timeDiff_D.csv &
        cat data/result/evaluation/ver3/case/D/liner_${USE_NUM}_${D}.csv |python calcAverageAccuracy.py ${D}>data/result/evaluation/ver3/actual/D/liner_D.csv &
        cat data/result/evaluation/ver3/case/D/combine_liner_dist_${USE_NUM}_${D}.csv |python calcAverageAccuracy.py ${D}>data/result/evaluation/ver3/actual/D/combine_liner_dist_D.csv &
    else
        cat data/result/evaluation/ver3/case/D/timeDiff_${USE_NUM}_${D}.csv |python calcAverageAccuracy.py ${D}>>data/result/evaluation/ver3/actual/D/timeDiff_D.csv &
        cat data/result/evaluation/ver3/case/D/liner_${USE_NUM}_${D}.csv |python calcAverageAccuracy.py ${D}>>data/result/evaluation/ver3/actual/D/liner_D.csv &
        cat data/result/evaluation/ver3/case/D/combine_liner_dist_${USE_NUM}_${D}.csv |python calcAverageAccuracy.py ${D}>>data/result/evaluation/ver3/actual/D/combine_liner_dist_D.csv &
    fi
    echo "${D} is done"
done
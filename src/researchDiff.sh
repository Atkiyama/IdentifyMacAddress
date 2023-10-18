USE_NUM=20
for CASE_NUM in {1..1000}
do
    if [ $CASE_NUM -eq "1" ]; then
        cat data/capture/ver3/simulate/data_${USE_NUM}_${CASE_NUM}.csv| python researchDiff.py timeDiff ${USE_NUM} ${CASE_NUM}> data/result/evaluation/ver3/diff_timeDiff_${USE_NUM}.csv &
        cat data/capture/ver3/simulate/data_${USE_NUM}_${CASE_NUM}.csv| python researchDiff.py liner ${USE_NUM} ${CASE_NUM}> data/result/evaluation/ver3/diff_liner_${USE_NUM}.csv
    else
        cat data/capture/ver3/simulate/data_${USE_NUM}_${CASE_NUM}.csv| python researchDiff.py timeDiff ${USE_NUM} ${CASE_NUM}>> data/result/evaluation/ver3/diff_timeDiff_${USE_NUM}.csv &
        cat data/capture/ver3/simulate/data_${USE_NUM}_${CASE_NUM}.csv| python researchDiff.py liner ${USE_NUM} ${CASE_NUM}>> data/result/evaluation/ver3/diff_liner_${USE_NUM}.csv
    fi
done
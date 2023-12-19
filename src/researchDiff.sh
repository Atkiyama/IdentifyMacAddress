USE_NUM=20
D=150
for CASE_NUM in {1..3000}
do
    if [ $CASE_NUM -eq "1" ]; then
        cat data/capture/ver3/simulate/USE_NUM/data_${USE_NUM}_${CASE_NUM}_${D}.csv| python researchDiff.py timeDiff ${USE_NUM} ${CASE_NUM}> data/result/evaluation/ver3/diff_timeDiff_${USE_NUM}.csv &
        cat data/capture/ver3/simulate/USE_NUM/data_${USE_NUM}_${CASE_NUM}_${D}.csv| python researchDiff.py liner ${USE_NUM} ${CASE_NUM}> data/result/evaluation/ver3/diff_liner_${USE_NUM}.csv
    else
        cat data/capture/ver3/simulate/USE_NUM/data_${USE_NUM}_${CASE_NUM}_${D}.csv| python researchDiff.py timeDiff ${USE_NUM} ${CASE_NUM}>> data/result/evaluation/ver3/diff_timeDiff_${USE_NUM}.csv &
        cat data/capture/ver3/simulate/USE_NUM/data_${USE_NUM}_${CASE_NUM}_${D}.csv| python researchDiff.py liner ${USE_NUM} ${CASE_NUM}>> data/result/evaluation/ver3/diff_liner_${USE_NUM}.csv
    fi
done
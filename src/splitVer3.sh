LS=$(ls data/capture/ver3/original/)

LS=$(ls data/capture/ver3/txt/)
for inputFileName in ${LS}
do
    java processed/timeAdjustment/Split data/capture/ver3/txt/$inputFileName
done

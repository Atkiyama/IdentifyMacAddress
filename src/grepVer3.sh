#!/bin/bash

#COCOAパケットを抽出
LS=$(ls data/capture/ver3/original/)
for inputFileName in ${LS}
do
    grep -a 0000fd6f-0000-1000-8000-00805f9b34fb  data/capture/ver3/original/$inputFileName > data/capture/ver3/txt/$inputFileName
done


# ENSのUUID0000fd6f-0000-1000-8000-00805f9b34fb
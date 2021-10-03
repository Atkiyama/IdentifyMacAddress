#bin/bash

for numOfData in 8
do
  ./convertMacAddress.sh move $numOfData
  ./identifyMove.sh $numOfData
  ./EvaluationMove.sh $numOfData
#  ./moveGraph.sh $numOfData
done

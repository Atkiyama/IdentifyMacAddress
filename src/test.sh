#bin/bash

commandString='ps|grep java|wc -l'
count=$(eval $commandString)
while [ "$count" -gt 0 ]
do
  commandString='ps|grep java|wc -l'
  count=$(eval $commandString)
    echo "成功"
done

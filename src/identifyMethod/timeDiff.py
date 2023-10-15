import pandas as pd
import os
import sys
import random
import numpy as np
from sklearn.linear_model import LinearRegression
from scipy.optimize import linear_sum_assignment
from sklearn.base import clone
from collections import defaultdict

def timeDiff(changed):
    assignment_table=[]
    for data in changed:
        assignment_line=[]
        for data2 in changed:
            diff =float(data2[0][1])-float(data[len(data)-1][1])
            if data[0][0] != data2[0][0] and 0 <= diff and diff<= 6:
                assignment_line.append(diff)
            else:
                assignment_line.append(sys.float_info.max)
        assignment_table.append(assignment_line)
    return assignment_table

def assignment(assignment_table,changed):

    row_ind, col_ind = linear_sum_assignment(np.array(assignment_table))

    isTrue = 0
    for i in row_ind:
        if i % 2 == 0 and i + 1 == col_ind[i]:
            isTrue += 1
    accuracy = isTrue / float(len(changed) / 2)
    return row_ind,col_ind,accuracy

def main():
    changed=[]
    addressDict=defaultdict(list)
    i=0
    for line in sys.stdin:
        packet=list(line.replace("\n", "").split(","))
        addressDict[packet[0]].append(packet)
    for k,v in addressDict.items():
        changed.append(v)
    assignment_table=timeDiff(changed)
    row_ind,col_ind,accuracy = assignment(assignment_table,changed)
    print(accuracy)



if __name__=='__main__' :
    main()
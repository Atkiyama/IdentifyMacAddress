import csv
import pandas as pd
import os


SERECT_ADDRESS="data/capture/ver4/selectAddress.csv"

def readCSV(filename):
    df = pd.read_csv(filename)
    return df
        
def main():
    fileList = readCSV(SERECT_ADDRESS)

if __name__ == "__main__":
    main()
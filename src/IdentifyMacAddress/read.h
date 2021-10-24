#include<stdio.h>
#include<fstream>
#include<string>
#include <sstream>
#include <iostream>

void readAddressList(){
    std::string fileName = "data/address/addressList.csv";
    std::ifstream ifs_csv_file(fileName);
    std::string line;
    std::string data;
    while (getline(ifs_csv_file, line)) {    
    // 「,」区切りごとにデータを読み込むためにistringstream型にする
    std::istringstream i_stream(line);
    std::cout << "おこ" << "\n";

    // 「,」区切りごとにデータを読み込む
     std::string lineArray[4];
     int i =0;
        while (getline(i_stream, data, ',')) {
            // csvファイルに書き込む
            lineArray[i] = data;
            i++;
            std::cout << "おこ" << "\n";
        }
    std::cout << lineArray[0] << "," << lineArray[1] << "," << lineArray[2] << lineArray[3] << "\n";
    }
}
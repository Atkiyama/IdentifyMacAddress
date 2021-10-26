#include<stdio.h>
#include<fstream>
#include<string>
#include <sstream>
#include <iostream>
#include <vector>
#pragma once
#include"address.h"
class Address;

Address makeAddress(std::string);
std::vector<Address> readAddressList();
int readDelay(int,int);

Address makeAddress(std::string buf){
    int first = buf.find(",");
    int second = buf.find(",",first+1);
    int third = buf.find(",",second+1);
    std::string fileName = buf.substr(0,first);
    std::string address = buf.substr(first+1,second-first-1);
    std::string fTime = buf.substr(second+1,third-second-1);
    std::string lTime = buf.substr(third+1);
    return Address(fileName,address,std::stod(fTime),std::stod(lTime));
}

std::vector<Address> readAddressList(){

    const char *inputName = "./data/address/addressList.csv";
    std::ifstream ifs(inputName);
    std::string data;
    std::string buf;
    if (!ifs){
        std::cout << "ファイルが開けませんでした。" << std::endl;
        std::cin.get();
    }
    

    //ヘッダを読み飛ばす
    std::getline(ifs, buf);
    std::vector<Address> addressList; 

    while (!ifs.eof()){
        std::getline(ifs, buf);
        if(buf.size()!=0)
            addressList.push_back(makeAddress(buf));    
    }

    return addressList;
}

int readDelay(int dataNumber,int numOfTimes){
    std::ostringstream oss;
    oss << dataNumber;

    std::string inputName = "./data/address/delay/randomDelay";
    inputName += oss.str();
    inputName +=".csv";
    std::ifstream ifs(inputName);
    std::string data;
    std::string buf;
    if (!ifs){
        std::cout << "ファイルが開けませんでした。" << std::endl;
        std::cin.get();
    }
    

    //ヘッダを読み飛ばす
    int delay;
    int i=1;
    while(true){
        std::getline(ifs, buf);
        if(i==numOfTimes)
            return std::stoi(buf);
        i++;
    }

    
}


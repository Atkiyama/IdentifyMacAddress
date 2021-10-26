#include <string>
#include <iostream>
#include <stdio.h>
#include <vector>
#include "read.h"

int readDelay(int,int);


class Address{
public:
    std::string fileName;
    std::string address;
    double fTime;
    double lTime;
    std::vector<Address> nextAddressList;
    Address(std::string fileName,std::string address,double fTime,double lTime){
        this->fileName = fileName;
        this->address = address;
        this->fTime = fTime;
        this->lTime = lTime;
    }

    void setDelay(int numOfTimes){
        int dataNumber =std::stoi(fileName.substr(4));
        int delay = readDelay(dataNumber,numOfTimes);
        fTime +=delay;
        lTime += delay;

    }

    void addNextAddressList(Address address){
        nextAddressList.push_back(address);
    }
    std::string getFileName(){
        return fileName;
    }


    double getFTime(){
        return fTime;
    }

    double getLTime(){
        return lTime;
    }

    void print(){
        std::cout << fileName <<","<< address << "," << fTime << "," << lTime << std::endl;

    }

};

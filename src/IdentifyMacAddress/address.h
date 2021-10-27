#include <string>
#include <iostream>
#include <stdio.h>
#include <vector>
#include "read.h"
#include "packet.h"

int readDelay(int,int);
double readFPackets(std::string,double,int);
void setFPackets(int);
void setRegression(int);
double readRegression(std::string,std::string,double,int);


class Address{
public:
    std::string fileName;
    std::string address;
    double fTime;
    double lTime;
    double fPackets;
    double regression;
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

    void setNextAddressList(std::vector<Address> addressList){
        this->nextAddressList = nextAddressList;
    }

    std::vector<Address> getNextAddressList(){
        return nextAddressList;
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

   double getFPacket(){
        return fPackets;
    }

    double getRegression(){
        return regression;
    }

    void setFPackets(int I){
        fPackets = readFPackets(address,fTime,I);
    }

    void setRegression(std::string method,int I){
        regression = readRegression(address,method,fTime,I);
    }


    void print(){
        std::cout << fileName <<","<< address << "," << fTime << "," << lTime << std::endl;

    }

};

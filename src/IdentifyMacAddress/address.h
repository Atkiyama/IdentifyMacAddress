#include <string>
#include <iostream>
#include <stdio.h>
#include <vector>
#include "read.h"
#include<math.h>

int readDelay(int,int);
double readFPackets(std::string,double,int,int);
void setFPackets(int);
void setRegression(int);
double readRegression(std::string,std::string,double,int,int);
double getNormalized();


class Address{
public:
    std::string fileName;
    std::string address;
    double fTime;
    double lTime;
    int delay;
    double fPackets;
    double regression;
    double normalizedT;
    double normalizedR;
    std::vector<Address> nextAddressList;
    Address(std::string fileName,std::string address,double fTime,double lTime){
        this->fileName = fileName;
        this->address = address;
        this->fTime = fTime;
        this->lTime = lTime;
    }

    void setDelay(int numOfTimes){
        int dataNumber =std::stoi(fileName.substr(4));
        delay = readDelay(dataNumber,numOfTimes);
        fTime +=delay;
        lTime += delay;

    }

    void setNormalizedR(double normalizedR){
        this->normalizedR = normalizedR;
    }

    void setNormalizedT(double normalizedT){
        this->normalizedT = normalizedT;
    }

    double getNormaliedR(){
        return normalizedR;
    }

    double getNormaliedT(){
        return normalizedT;
    }

    double getNormalized(){
        return sqrt(normalizedR+normalizedT);
    }

    void addNextAddressList(Address address){
        nextAddressList.push_back(address);
    }

    void setNextAddressList(std::vector<Address> addressList){
        this->nextAddressList = addressList;
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
        fPackets = readFPackets(address,fTime,I,delay);
    }

    void setRegression(std::string method,int I){
        regression = readRegression(address,method,fTime,I,delay);
    }


    void printData(){
        std::cout << fileName <<","<< address << "," << fTime << "," << lTime << std::endl;
        for(int i=0;i<nextAddressList.size();i++)
            nextAddressList[i].printData();

        std::cout << std::endl;


    }

};

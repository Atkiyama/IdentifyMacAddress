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
std::vector<std::vector<double> > readRegression(std::string,std::string,double,int,int);
double getNormalized();


class Address{
public:
    std::string fileName;
    std::string address;
    double fTime;
    double lTime;
    int delay;
    double fPackets;
    std::vector<std::vector<double> > regression;
    double eRegression;
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

    double extract(int j,int I){
        //NextAddressの引数
        double nextFTime = nextAddressList[j].getFTime();
        double sum=0;
        double count=0;
        for(int i=0;i<nextAddressList[j].getRegression().size();i++){
            double sub = nextAddressList[j].getRegression()[i][0]-nextFTime;
            if(0<=sub&&sub<=I){
                sum+=nextAddressList[j].getRegression()[i][1];
                count++;
            }

        }
        if(count!=0)
            eRegression = sum/count;
        else
            eRegression = 0;
        return eRegression;
        
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
        nextAddressList = addressList;
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

   double getFPackets(){
        return fPackets;
    }

    std::vector<std::vector<double> > getRegression(){
        return regression;
    }

    void setFPackets(int I){
        fPackets = readFPackets(address,fTime,I,delay);
    }

    void setRegression(std::string method,int I){
        regression = readRegression(address,method,fTime,I,delay);
    }

    double getERegression(){
        return eRegression;
    }


    void printData(){
        std::cout << "fileName:"<<fileName <<",address:"<< address << ",fTime:" << fTime << ",lTime:" << lTime << ",nextAddSize:"<<nextAddressList.size()<<std::endl;
        for(int i=0;i<nextAddressList.size();i++)
            nextAddressList[i].printData();
        std::cout << std::endl;


    }

};

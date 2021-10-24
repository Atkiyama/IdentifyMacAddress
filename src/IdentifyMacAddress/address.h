#include <string>
#include <iostream>
#include <stdio.h>


class Address{
public:
    std::string fileName;
    std::string address;
    double fTime;
    double lTime;
    Address(std::string fileName,std::string address,double fTime,double lTime){
        this->fileName = fileName;
        this->address = address;
        this->fTime = fTime;
        this->lTime = lTime;
    }

    void print(){
        std::cout << fileName <<","<< address << "," << fTime << "," << lTime << std::endl;

    }

};

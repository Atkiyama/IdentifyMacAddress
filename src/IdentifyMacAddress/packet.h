#include <string>
#include <iostream>
#include <stdio.h>


class Packet{
public:
  std::string address;
  double time;
  int rssi;
  Packet(std::string address,double time,int rssi){
    this->address = address;
    this->time = time;
    this->rssi = rssi;
  }

  void print(){
    std::cout << address << "," << time << rssi;

  }

};

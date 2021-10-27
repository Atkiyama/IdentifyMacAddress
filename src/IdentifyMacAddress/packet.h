#include <string>
#include <iostream>
#include <stdio.h>


class Packet{
public:
  double time;
  int rssi;
  Packet(double time,int rssi){
    this->time = time;
    this->rssi = rssi;
  }

  double getTime(){
    return time;
  }

  int getRssi(){
    return rssi;
  }



};

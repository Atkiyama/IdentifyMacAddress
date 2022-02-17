#ifndef INCLUDED_packet_h_
#define INCLUDED_packet_h_
#include <stdio.h>
#include <fstream>
#include <string>
#include <sstream>
#include <iostream>
#include <vector>
using namespace std;//stdを省略

/**
 * @brief
 * addressList
 *  
 * linerRegression
 * svr
 * bagging
 * 
 * 
 */
class Packet{
    public:
       double time;
       double rssi;
        
    Packet(double time, double rssi)
    {
        this->time = time;
        this->rssi = rssi;
    }

    inline double getTime(){
        return time;
    }

    inline double getRssi(){
        return rssi;
    }
};
#endif
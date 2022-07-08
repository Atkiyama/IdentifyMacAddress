#ifndef INCLUDED_address_h_
#define INCLUDED_address_h_
#include <string>
#include <iostream>
#include <stdio.h>
#include <vector>
#include <math.h>
#include "data.h"
#include "packet.h"
using namespace std; // stdを省略

class Data;
class Packet;

/**
 * アドレスを示すクラス
 * fileName アドレスが格納されているファイル名
 * address アドレス名
 * fTime 初回受診時刻
 * lTime 最終受診時刻
 * fPackets 初回受診時刻から閾値I秒までのパケットのRSSIの平均
 * regression パケットの回帰データを格納したリスト 0にtime 1にrssiを格納する
 * nextAddressList 次アドレス候補のリスト　最終的に0に次アドレスを格納する
 */
class Address
{
public:
    string fileName;
    string address;
    double fTime;
    double lTime;
    // double normalizedT;
    double normalizedR;
    vector<Packet> fPackets;
    vector<Packet> regression;
    vector<Address> nextAddressList;

    /**
     * 初期化して代入する
     */
    Address(string fileName, string address, double fTime, double lTime)
    {
        this->fileName = fileName;
        this->address = address;
        this->fTime = fTime;
        this->lTime = lTime;
    }

    inline string getAddress()
    {
        return address;
    }

    //閾値Iに応じた各MACアドレスの開始付近のパケットをセットする
    inline void setFPackets(int I, map<std::string, vector<Packet> > fAddress)
    {
        ostringstream ossI;
        ossI << I;
        vector<Packet> packetData = fAddress[address];
        for (int i = 0; i < packetData.size(); i++)
        {
            if (packetData[i].getTime() - fTime <= I)
                fPackets.push_back(packetData[i]);
        }
    }

    inline double getFTime()
    {
        return fTime;
    }
    inline double getLTime()
    {
        return lTime;
    }

    //次アドレスリストを登録する
    inline void addNextAddressList(Address address)
    {
        nextAddressList.push_back(address);
    }

    //回帰値をセットするメソッド
    inline void setRegression(map<string, vector<Packet> > regMap, int I)
    {
        ostringstream ossI;
        ossI << I;
        regression = regMap[address + "_" + ossI.str()];
    }

    inline vector<Address> getNextAddressList()
    {
        return nextAddressList;
    }

    inline vector<Packet> getFPackets()
    {
        return fPackets;
    }

    inline vector<Packet> getRegression()
    {
        return regression;
    }

    void setNextAddressList(std::vector<Address> addressList)
    {
        nextAddressList = addressList;
    }

    inline string getFileName()
    {
        return fileName;
    }

    inline void setNormalizedR(double normalizedR)
    {
        this->normalizedR = normalizedR;
    }
    /*
        inline void setNormalizedT(double normalizedT){
            this->normalizedT = normalizedT;
        }
    */
    inline double getNormalized()
    {
        return normalizedR;
    }

    //データを出力するメソッド
    inline void printData()
    {
        std::cout << "fileName>" << fileName << ",address>" << address << ",fTime>" << fTime << ",lTime>" << lTime << std::endl;
        if (nextAddressList.size() == 1)
        {
            std::cout << "fileName>" << nextAddressList[0].getFileName() << ",address>" << nextAddressList[0].getAddress() << ",fTime>" << nextAddressList[0].getFTime() << ",lTime>" << nextAddressList[0].getLTime() << std::endl;
            if (fileName == nextAddressList[0].getFileName())
                std::cout << nextAddressList[0].getNormalized()<< std::endl;
        }
        else if (nextAddressList.size() > 1)
            std::cout << "warning nextAddress size is over 1" << std::endl;
        std::cout << "" << std::endl;
    }

    inline string getMyString()
    {
        ostringstream ossFTime;
        ossFTime << fTime;
        ostringstream ossLTime;
        ossLTime << lTime;
        return "fileName>" + fileName + ",address>" + address + ",fTime>" + ossFTime.str() + ",lTime>" + ossLTime.str();
    }

    inline string getNextAddressString()
    {
        if (nextAddressList.size() == 1)
        {
            ostringstream ossFTime;
            ossFTime << nextAddressList[0].getFTime();
            ostringstream ossLTime;
            ossLTime << nextAddressList[0].getLTime();
            return "fileName>" + nextAddressList[0].getFileName() + ",address>" + nextAddressList[0].getAddress() + ",fTime>" + ossFTime.str() + ",lTime>" + ossLTime.str();
        }
        else
        {
            return "";
        }
    }
}

;
#endif
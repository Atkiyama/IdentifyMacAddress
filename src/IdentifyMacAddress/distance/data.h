#ifndef INCLUDED_data_h_
#define INCLUDED_data_h_
#include <stdio.h>
#include <fstream>
#include <string>
#include <sstream>
#include <iostream>
#include <vector>
#include <map>
#include "packet.h"
#include "address.h"
using namespace std;//stdを省略

class Address;
class Packet;

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
class Data{
    public:
        vector<Address> addressList;
        map<string,vector<Packet> > fAddress;
        map<string,vector<Packet> > lAddress;
        map<string,vector<Packet> > regression;


    /**
     * @brief 
     * アドレスリストを読みこむメソッド
     * @param fileName 
     */
    inline void readAddressList(string fileName){
        ostringstream oss;
        oss << fileName;
        ifstream ifs(oss.str());
        string data;
        string buf;
        if (!ifs)
        {
            cout << "ファイルが開けませんでした。" << endl;
            cout << oss.str() << endl;
            cin.get();
        }

        //ヘッダを読み飛ばす
        getline(ifs, buf);

        while (!ifs.eof())
        {
            getline(ifs, buf);
            if (buf.size() != 0)
                addressList.push_back(makeAddress(buf));
        }


    }

    /**
    * 引数の文字列からアドレスのクラスを生成するメソッド
    */
    inline Address makeAddress(string buf)
    {   
        int first = buf.find(",");
        int second = buf.find(",", first + 1);
        int third = buf.find(",", second + 1);
        string fileName = buf.substr(0, first);
        string address = buf.substr(first + 1, second - first - 1);
        string fTime = buf.substr(second + 1, third - second - 1);
        string lTime = buf.substr(third + 1);
        //cout << "fileName>" << fileName << ",address>" << address << ",fTime>" << fTime << ",lTime>" << lTime << endl;
        return Address(fileName, address, stod(fTime), stod(lTime));
    }


    /**
     * @brief 
     * 各MACアドレスの先頭パケットを取り出すメソッド
     * @param dataNumber  データの通し番号
     */
    inline void readFAddress(int dataNumber){
        for(int i=0;i<addressList.size();i++){
            ostringstream oss;
            ostringstream ossDataNumber;
            ossDataNumber << dataNumber;
            string fileName =  addressList[i].getAddress() + "_" + ossDataNumber.str();
            string filePath = "data/address/processed/fAddress/"+ fileName + ".csv";
            oss << filePath;
            ifstream ifs(oss.str());
            string data;
            string buf;
            if (!ifs)
            {
                cout << "ファイルが開けませんでした。" << endl;
                cout << oss.str() << endl;
                cin.get();
            }

            //ヘッダを読み飛ばす
            getline(ifs, buf);
            vector<Packet> fPacket;

            while (!ifs.eof())
            {
                getline(ifs, buf);
                if (buf.size() != 0)
                   fPacket.push_back(makePacket(buf));
            }

            fAddress[addressList[i].getAddress()] = fPacket;
        }
    }

    /**
     * @brief 
     * 各MACアドレスの先頭パケットを取り出すメソッド
     * @param dataNumber  データの通し番号
     */
    inline void readLAddress(int dataNumber){
        for(int i=0;i<addressList.size();i++){
            ostringstream oss;
            ostringstream ossDataNumber;
            ossDataNumber << dataNumber;
            string fileName =  addressList[i].getAddress() + "_" + ossDataNumber.str();
            string filePath = "data/address/processed/lAddress/"+ fileName + ".csv";
            oss << filePath;
            ifstream ifs(oss.str());
            string data;
            string buf;
            if (!ifs)
            {
                cout << "ファイルが開けませんでした。" << endl;
                cout << oss.str() << endl;
                cin.get();
            }

            //ヘッダを読み飛ばす
            getline(ifs, buf);
            vector<Packet> lPacket;

            while (!ifs.eof())
            {
                getline(ifs, buf);
                if (buf.size() != 0)
                   lPacket.push_back(makePacket(buf));
            }

            lAddress[addressList[i].getAddress()] = lPacket;
        }
    }


    /**
     * @brief 
     * ファイルの一行からパケットのインスタンスを生成するメソッド
     * @param buf 元の文字列
     * @return Packet 生成したインスタンス
     */
    inline Packet makePacket(string buf){
        int point = buf.find(",");
        string time = buf.substr(0, point);
        string rssi = buf.substr(point + 1);
        //cout << "time>" << time << ",rssi" << rssi << endl;
        return Packet(stod(time), stod(rssi));
    }

    inline void readRegression(int dataNumber,string method){
       for(int I=1;I<=20;I++){
           for(int i=0;i<addressList.size();i++){
            ostringstream oss;
            ostringstream ossDataNumber;
            ostringstream ossI;
            ossDataNumber << dataNumber;
            ossI << I;
            string fileName =  addressList[i].getAddress() +"_" + ossI.str() + "_" + ossDataNumber.str();
            string filePath = "data/address/processed/regression/" + method +"/"+ fileName + ".csv";
            oss << filePath;
            ifstream ifs(oss.str());
            string data;
            string buf;
            if (!ifs)
            {
                cout << "ファイルが開けませんでした。" << endl;
                cout << oss.str() << endl;
                cin.get();
            }

            //ヘッダを読み飛ばす
            getline(ifs, buf);
            vector<Packet> fPacket;

            while (!ifs.eof())
            {
                getline(ifs, buf);
                if (buf.size() != 0)
                   fPacket.push_back(makePacket(buf));
            }

            regression[addressList[i].getAddress() +"_" + ossI.str()] = fPacket;
           }
       }

    }

    inline void readRegression(int dataNumber,string method,int I){
        for(int i=0;i<addressList.size();i++){
            ostringstream oss;
            ostringstream ossDataNumber;
            ostringstream ossI;
            ossDataNumber << dataNumber;
            ossI << I;
            string fileName =  addressList[i].getAddress() +"_" + ossI.str() + "_" + ossDataNumber.str();
            string filePath = "data/address/processed/regression/" + method +"/"+ fileName + ".csv";
            oss << filePath;
            ifstream ifs(oss.str());
            string data;
            string buf;
            if (!ifs)
            {
                cout << "ファイルが開けませんでした。" << endl;
                cout << oss.str() << endl;
                cin.get();
            }

            //ヘッダを読み飛ばす
            getline(ifs, buf);
            vector<Packet> fPacket;

            while (!ifs.eof())
            {
                getline(ifs, buf);
                if (buf.size() != 0)
                   fPacket.push_back(makePacket(buf));
            }

            regression[addressList[i].getAddress() +"_" + ossI.str()] = fPacket;
            
       }


    }

    inline vector<Address> getAddressList(){
        return addressList;
    }

    inline map<string,vector<Packet> > getFAddress(){
        return fAddress;
    }
    inline map<string,vector<Packet> > getLAddress(){
        return lAddress;
    }
    inline map<string ,vector<Packet> >getRegression(){
        return regression;
    }


};

#endif
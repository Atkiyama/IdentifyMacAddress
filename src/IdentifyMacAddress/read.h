#include <stdio.h>
#include <fstream>
#include <string>
#include <sstream>
#include <iostream>
#include <vector>
#pragma once
#include "address.h"

class Address;

inline Address makeAddress(std::string);
inline std::vector<Address> readAddressList();
inline std::vector<std::vector<double> > readFPackets(std::string, double, int, int);
inline bool checkR(Address, Address, int);
inline double getR(Address, Address);

/**
 * 様々なデータを読み込むファイル
 */

/**
 * 引数の文字列からアドレスのクラスを生成するメソッド
 */
inline Address makeAddress(std::string buf)
{
    int first = buf.find(",");
    int second = buf.find(",", first + 1);
    int third = buf.find(",", second + 1);
    std::string fileName = buf.substr(0, first);
    std::string address = buf.substr(first + 1, second - first - 1);
    std::string fTime = buf.substr(second + 1, third - second - 1);
    std::string lTime = buf.substr(third + 1);
    return Address(fileName, address, std::stod(fTime), std::stod(lTime));
}

/**
 * アドレスリストを読み込むクラス
 */
inline std::vector<Address> readAddressList(int numOfTimes)
{

    std::string inputName = "./data/address/delay/addressList/addressList";
    std::ostringstream oss;
    oss << numOfTimes;
    inputName += oss.str();
    inputName += ".csv";
    std::ifstream ifs(inputName);
    std::string data;
    std::string buf;
    if (!ifs)
    {
        std::cout << "ファイルが開けませんでした。" << std::endl;
        std::cout << inputName << std::endl;
        std::cin.get();
    }

    //ヘッダを読み飛ばす
    std::getline(ifs, buf);
    std::vector<Address> addressList;

    while (!ifs.eof())
    {
        std::getline(ifs, buf);
        if (buf.size() != 0)
            addressList.push_back(makeAddress(buf));
    }

    return addressList;
}

/**
 * fPacketsを読み出してI秒までの平均を返すメソッド
 */
inline std::vector<std::vector<double> > readFPackets(std::string address, double fTime, int I,int numOfTimes)
{
    std::string inputName = "./data/address/delay/fAddress/";
    inputName += address;
    inputName += "_";
    std::ostringstream oss;
    oss << numOfTimes;
    inputName += oss.str();
    inputName += ".csv";
    std::ifstream ifs(inputName);
    std::string data;
    std::string buf;
    if (!ifs)
    {
        std::cout << "ファイルが開けませんでした。" << std::endl;
        std::cout << inputName << std::endl;
        std::cin.get();
    }
    std::vector<std::vector<double> > fPackets;
    //ヘッダを読み飛ばす
    std::getline(ifs, buf);
    while (!ifs.eof())
    {
        std::getline(ifs, buf);
        if (buf.size() != 0)
        {
            int first = buf.find(",");
            //遅延時間もここで設定する
            double time = std::stod(buf.substr(0, first));
            double rssi = std::stod(buf.substr(first + 1));
            if (time - fTime <= I)
            {
                std::vector<double> fPacket;
                fPacket.push_back(time);
                fPacket.push_back(rssi);
                fPackets.push_back(fPacket);
            }
        }
    }
    return fPackets;
}

/**
 *回帰値を読み込むメソッド
 */

inline std::vector<std::vector<double> > readRegression(std::string address, std::string method,int I,int numOfTimes)
{
    std::string inputName = "./data/address/delay/regression/";
    inputName += method;
    inputName += "/";
    inputName += address;
    std::ostringstream oss;
    oss << I;
    inputName += "_";
    inputName += oss.str();
    std::ostringstream oss2;
    oss2 << numOfTimes;
    inputName += "_";
    inputName += oss2.str();
    inputName += ".csv";
    std::ifstream ifs(inputName);
    std::string data;
    std::string buf;
    if (!ifs)
    {
        std::cout << "ファイルが開けませんでした。" << std::endl;
        std::cout << inputName << std::endl;
        std::cin.get();
    }
    std::vector<std::vector<double> > regressions;
    while (!ifs.eof())
    {
        std::getline(ifs, buf);
        if (buf.size() != 0)
        {
            int first = buf.find(",");
            std::vector<double> data;
            //0にtime.1にrssiをつっこむ
            data.push_back(std::stod(buf.substr(0, first)));
            data.push_back(std::stod(buf.substr(first + 1)));
            regressions.push_back(data);
        }
    }
    return regressions;
}

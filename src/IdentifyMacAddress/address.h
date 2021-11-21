#include <string>
#include <iostream>
#include <stdio.h>
#include <vector>
#include "read.h"
#include <math.h>

inline std::vector<std::vector<double> > readFPackets(std::string, double,  int);
inline void setFPackets(int);
inline void setRegression(std::string);
inline std::vector<std::vector<double> > getFPackets();
inline std::vector<std::vector<double> > readRegression(std::string, std::string);
inline double getNormalized();

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
    std::string fileName;
    std::string address;
    double fTime;
    double lTime;
    double normalizedT;
    double normalizedR;
    std::vector<std::vector<double> > fPackets;
    std::vector<std::vector<double> > regression;
    std::vector<Address> nextAddressList;

    /**
     * 初期化して代入する
     */
    Address(std::string fileName, std::string address, double fTime, double lTime)
    {
        this->fileName = fileName;
        this->address = address;
        this->fTime = fTime;
        this->lTime = lTime;
    }

    
    /**
     * 特定区間から回帰値の平均を抽出するメソッド
     */
    inline void extract(int j, int I)
    {
        //NextAddressの引数
        double nextFTime = nextAddressList[j].getFTime();
        std::vector<std::vector<double> > replace;

        for (int i = 0; i < regression.size(); i++)
        {
            double sub = regression[i][0] - nextFTime;
            if (-0.5 <= sub && sub <= I)
            {
                std::vector<double> replaceData;
                replaceData.push_back(regression[i][0]);
                replaceData.push_back(regression[i][1]);
                replace.push_back(replaceData);
            }
        }
        regression = replace;
    }
    inline void setNormalizedR(double normalizedR){
        this->normalizedR = normalizedR;
    }

    inline void setNormalizedT(double normalizedT){
        this->normalizedT = normalizedT;
    }

    inline double getNormalized(){
        return normalizedR + normalizedT;
    }

    inline void addNextAddressList(Address address)
    {
        nextAddressList.push_back(address);
    }

    void setNextAddressList(std::vector<Address> addressList)
    {
        nextAddressList = addressList;
    }

    inline std::vector<Address> getNextAddressList()
    {
        return nextAddressList;
    }
    inline std::string getFileName()
    {
        return fileName;
    }

    inline double getFTime()
    {
        return fTime;
    }

    inline double getLTime()
    {
        return lTime;
    }

    inline std::vector<std::vector<double> > getFPackets()
    {
        return fPackets;
    }

    inline std::vector<std::vector<double> > getRegression()
    {
        return regression;
    }

    /**
     * read.hのメソッドを用いてfPacketsを設定する
     */
    inline void setFPackets(int I)
    {
        fPackets = readFPackets(address, fTime,I);
    }

    /**
     * read.hのメソッドを用いてregressionを設定する
     */
    inline void setRegression(std::string method)
    {
        regression = readRegression(address, method);
    }


    inline std::string getAddress()
    {
        return address;
    }

    /**
     * 自アドレスの情報と次アドレスの情報を表示する
     */
    inline void printData()
    {
        std::cout << "fileName>" << fileName << ",address>" << address << ",fTime>" << fTime << ",lTime>" << lTime << std::endl;
        if (nextAddressList.size() == 1)
            std::cout << "fileName>" << nextAddressList[0].getFileName() << ",address>" << nextAddressList[0].getAddress() << ",fTime>" << nextAddressList[0].getFTime() << ",lTime>" << nextAddressList[0].getLTime() << std::endl;
        else if (nextAddressList.size() > 1)
            std::cout << "warning nextAddress size is over 1" << std::endl;
        std::cout << std::endl;
    }
};

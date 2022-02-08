#include <string>
#include <iostream>
#include <stdio.h>
#include <vector>
#include <math.h>



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

    
};

#include <string>
#include <iostream>
#include <stdio.h>
#include <vector>
#include "read.h"
#include<math.h>

inline int readDelay(int,int);
inline double readFPackets(std::string,double,int,int);
inline void setFPackets(int);
inline void setRegression(int);
inline std::vector<std::vector<double> > readRegression(std::string,std::string,double,int,int);
inline double getNormalized();

/**
 * アドレスを示すクラス
 * fileName アドレスが格納されているファイル名
 * address アドレス名
 * fTime 初回受診時刻
 * lTime 最終受診時刻
 * delay 遅延時刻
 * fPackets 初回受診時刻から閾値I秒までのパケットのRSSIの平均
 * regression パケットの回帰データを格納したリスト 0にtime 1にrssiを格納する
 * eRegression 同定用に特定区間のRSSIの平均を格納した変数
 * normalizedT 正規化した閾値Tの値
 * normalizedR 正規化した閾値Rの値
 * nextAddressList 次アドレス候補のリスト　最終的に0に次アドレスを格納する
 */
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
    
    /**
     * 初期化して代入する
     */
    Address(std::string fileName,std::string address,double fTime,double lTime){
        this->fileName = fileName;
        this->address = address;
        this->fTime = fTime;
        this->lTime = lTime;
    }

    /**
    *遅延時間を設定する
    *read.hのメソッドから読みこむ
    */
    inline void setDelay(int numOfTimes){
        int dataNumber =std::stoi(fileName.substr(4));
        delay = readDelay(dataNumber,numOfTimes);
        fTime +=delay;
        lTime += delay;

    }

    /**
     * 特定区間から回帰値の平均を抽出するメソッド
     */
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
        //データ0の場合0除算を防ぐためにこの処理をする
        if(count!=0)
            eRegression = sum/count;
        else
            eRegression = 0;
        return eRegression;
        
    }

    inline void setNormalizedR(double normalizedR){
        this->normalizedR = normalizedR;
    }

    

    inline void setNormalizedT(double normalizedT){
        this->normalizedT = normalizedT;
    }

    inline double getNormaliedR(){
        return normalizedR;
    }

    inline double getNormaliedT(){
        return normalizedT;
    }

    inline double getNormalized(){
        return sqrt(normalizedR+normalizedT);
    }

    inline void addNextAddressList(Address address){
        nextAddressList.push_back(address);
    }

    void setNextAddressList(std::vector<Address> addressList){
        nextAddressList = addressList;
    }

    inline std::vector<Address> getNextAddressList(){
        return nextAddressList;
    }
    inline std::string getFileName(){
        return fileName;
    }

    

    inline double getFTime(){
        return fTime;
    }

    inline double getLTime(){
        return lTime;
    }

   inline double getFPackets(){
        return fPackets;
    }

    inline std::vector<std::vector<double> > getRegression(){
        return regression;
    }

    /**
     * read.hのメソッドを用いてfPacketsを設定する
     */
    void setFPackets(int I){
        fPackets = readFPackets(address,fTime,I,delay);
    }

    /**
     * read.hのメソッドを用いてregressionを設定する
     */
    inline void setRegression(std::string method,int I){
        regression = readRegression(address,method,fTime,I,delay);
    }

    inline double getERegression(){
        return eRegression;
    }

    inline std::string getAddress(){
        return address;
    }

  

    /**
     * 自アドレスの情報と次アドレスの情報を表示する
     */
    inline void printData(){
        std::cout << "fileName:"<<fileName <<",address:"<< address << ",fTime:" << fTime << ",lTime:" << lTime <<std::endl;
        if(nextAddressList.size()==1)
            std::cout << "fileName:"<<nextAddressList[0].getFileName() <<",address:"<< nextAddressList[0].getAddress() << ",fTime:" << nextAddressList[0].getFTime() << ",lTime:" << nextAddressList[0].getLTime() <<std::endl;
        else if(nextAddressList.size()>1)
            std::cout <<"warning nextAddress size is over 1"<<std::endl;
        std::cout << std::endl;


    }

};

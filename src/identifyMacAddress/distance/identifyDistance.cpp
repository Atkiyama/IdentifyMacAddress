#ifndef INCLUDED_identifyDistance_cpp_
#define INCLUDED_identifyDistance_cpp_
#include <string>
#include <iostream>
#include "packet.h"
#include "data.h"
#include "address.h"
#include <vector>
#include <random>
#include <cmath>
using namespace std;//stdを省略

vector<Address> normalize(Address, int, int);
inline double getR(Address, Address);
inline bool checkR(Address,Address,int);
inline void write(vector<string> ,int,int,int,string,int);
inline double getDistance(double,double);
/**
* @brief
* 引数によって処理の仕方を変更する
* 1　閾値RとIを1~20の範囲で変更した場合
* 2 データ数を変動させた場合
* 3 LineUp.javaを使用した場合
* 4
*
*/

inline void identify(int R,int T,int I,Data data,string method,int dataNumber){
  vector<Address> addressList = data.getAddressList();
  //fPacketとlPacketを設定
  for (int i = 0; i < addressList.size(); i++)
  {
    addressList[i].setFPackets(I,data.getFAddress());
    addressList[i].setLPackets(I,data.getLAddress());
  }
  

//距離で同定
  for (int i = 0; i < addressList.size(); i++)
  {
    Address nextAddress =Address("brank", "brank", 0, 0);
    double subTime = addressList[0].getFTime() - addressList[i].getLTime();
    double subRssi = addressList[0].getLPacket()- addressList[i].getFPacket();
    double nextDistance = getDistance(subTime,subRssi);
    for (int j = 0; j < addressList.size(); j++)
    {
      subTime = addressList[j].getFTime() - addressList[i].getLTime();
      if(i!=j&&subTime>=0){
        subRssi = addressList[j].getLPacket()- addressList[i].getFPacket();
        double distance = getDistance(subTime,subRssi);
        if(nextDistance>distance){
          nextDistance = distance;
          nextAddress = addressList[j]; 
        }
      }
    }
     addressList[i].addNextAddressList(nextAddress);
  }  


  //結合分の処理

  //出力するデータのリスト
  vector<string> output;
  //データ表示
  for (int i = 0; i < addressList.size(); i++)
  {
    output.push_back(addressList[i].getMyString());
    output.push_back(addressList[i].getNextAddressString());
    output.push_back("\n");
  }

  write(output,R,T,I,method,dataNumber);
}



//閾値Rの条件を満たしているかチェックするメソッド
inline bool checkR(Address address, Address nextAddress, int R)
{
  //cout << getR(address, nextAddress) <<endl;
  if (getR(address, nextAddress) <= R)
    return true;
  else
    return false;
}

//敷地Rの差をセットするメソッド
inline double getR(Address address, Address nextAddress)
{
  return fabs(nextAddress.getFAverage()-address.getLAverage());
}

inline double getDistance(double subTime,double subRssi){
  return pow(subTime,2) + pow(subRssi,2);
}

//正規化した後にnextAddressListから厳選するためのメソッド
std::vector<Address> normalize(Address address, int R, int T)
{
  int times = address.getNextAddressList().size();
  double min = 999999;
  //距離が最小のアドレスを格納するための変数
  Address minAddress = address.getNextAddressList()[0];
  for (int i = 0; i < times; i++)
  {
    address.getNextAddressList()[i].setNormalizedT(std::pow(address.getNextAddressList()[i].getFTime() - address.getLTime() / (double)T, 2));
    address.getNextAddressList()[i].setNormalizedR(std::pow(getR(address, address.getNextAddressList()[i]) / (double)R, 2));
    //距離
    double distance = address.getNextAddressList()[i].getNormalized();
    if (distance < min)
    {
      //最小アドレスの更新
      min = distance;
      minAddress = address.getNextAddressList()[i];
    }
  }
  //置換
  std::vector<Address> replace;
  replace.push_back(minAddress);
  return replace;
}

inline void write(vector<string> output,int R,int T,int I,string method,int dataNumber){
  std::ofstream writing_file;
  ostringstream ossR;
  ossR << R;
  ostringstream ossT;
  ossT << T;
  ostringstream ossI;
  ossI << I;
  ostringstream ossDataNumber;
  ossDataNumber << dataNumber;
  std::string filename = "data/result/multi/move/"+method +"/"+ossDataNumber.str()+"/"+ossR.str()+","+ossT.str()+","+ossI.str()+".txt";
  writing_file.open(filename, std::ios::out);
  for(int i=0;i<output.size();i++){
    writing_file << output[i] << std::endl;
  }
  writing_file.close();
}

/**
* @brief
*
* @param argc
* @param argv 1に使用する機能、2に回帰手法(linerRegression,svr,bagging)を設定する
* @return int
*/
int main(int argc, char *argv[])
{
  int R=stoi(argv[1]);
  int T=stoi(argv[2]);
  int I=stoi(argv[3]);
 

  string method = "distance";
    for(int dataNumber=1;dataNumber<=100;dataNumber++){
      Data data;
      ostringstream ossDataNumber;
      ossDataNumber << dataNumber;
      data.readAddressList("./data/address/processed/addressList/addressList"+ossDataNumber.str()+".csv");
      data.readFAddress(dataNumber);
      data.readLAddress(dataNumber);
      identify(R,T,I,data,method,dataNumber);
    }
  
}

#endif

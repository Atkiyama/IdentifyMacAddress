#ifndef INCLUDED_identifyMacAddress_cpp_
#define INCLUDED_identifyMacAddress_cpp_
#include <string>
#include <iostream>
#include "packet.h"
#include "data.h"
#include "address.h"
#include <vector>
#include <random>
using namespace std;//stdを省略

vector<Address> normalize(Address, int, int,Data);
inline double getR(Address, Address);
inline bool checkR(Address,Address,int);
inline void write(vector<string> ,int,int,int,string,int);
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
  //fPacketを設定
  for (int i = 0; i < addressList.size(); i++)
  {
    addressList[i].setFPackets(I,data.getFAddress());
  }

  //Tで絞り込み
  for (int i = 0; i < addressList.size(); i++)
  {
    for (int j = 0; j < addressList.size(); j++)
    {
      double sub = addressList[j].getFTime() - addressList[i].getLTime();
      if (0 < sub && sub < T && i != j)
      addressList[i].addNextAddressList(addressList[j]);
    }
  }

  //回帰値をセット
  for (int i = 0; i < addressList.size(); i++)
  {
    addressList[i].setRegression(data.getRegression(),I);
  }
  
  //Rで絞り込み ここで候補が消えてる
  for (int i = 0; i < addressList.size(); i++)
  {
    std::vector<Address> replace;
    int times = addressList[i].getNextAddressList().size();
    for (int j = 0; j < times; j++)
    {
      //addressList[i].extract(j, I);
      if (checkR(addressList[i], addressList[i].getNextAddressList()[j], R))
        replace.push_back(addressList[i].getNextAddressList()[j]);
    }

    addressList[i].setNextAddressList(replace);
  }
  
  
  //正規化
  for (int i = 0; i < addressList.size(); i++)
  {
    if (addressList[i].getNextAddressList().size() >= 2)
    addressList[i].setNextAddressList(normalize(addressList[i], R, T,data));
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

  write(output,R,T,I,"false",dataNumber);
}

//閾値Rの条件を満たしているかチェックするメソッド
inline bool checkR(Address address, Address nextAddress, int R)
{
  //cout << getR(address, nextAddress) << endl;
  if (getR(address, nextAddress) <= R)
    return true;
  return false;
}

//敷地Rの差をセットするメソッド
inline double getR(Address address, Address nextAddress)
{
  double count = 0;
  double sum = 0;
  for (int i = 0; i < nextAddress.getFPackets().size(); i++)
  {
    for (int j = 0; j < address.getRegression().size(); j++)
    {
      //std::cout <<"00>"<<address.getRegression()[j].getRssi()<<","<<nextAddress.getFPackets()[i].getRssi()<<std::endl;
      if (address.getRegression()[j].getTime() == nextAddress.getFPackets()[i].getTime())
      {
        sum += std::abs(address.getRegression()[j].getRssi() - nextAddress.getFPackets()[i].getRssi());
        //std::cout <<"00>"<<address.getRegression()[j].getRssi()<<","<<nextAddress.getFPackets()[i].getRssi()<<std::endl;
        count++;
        break;
      }

    }
  }
  //std::cout <<"sum="<< sum << ",count =" << count <<",sum/count="<<sum/count<< std::endl;
  if (count == 0)
    return 99;
  return sum / count;
}

//正規化した後にnextAddressListから厳選するためのメソッド
std::vector<Address> normalize(Address address, int R, int T,Data data)
{
  int times = address.getNextAddressList().size();
  //距離が最小のアドレスを格納するための変数
  Address minAddress = address.getNextAddressList()[0];
  for (int i = 0; i < times; i++)
  {
    //address.getNextAddressList()[i].setNormalizedT(std::pow(address.getNextAddressList()[i].getFTime() - address.getLTime() / (double)T, 2));
    address.getNextAddressList()[i].setNormalizedR(getR(address, address.getNextAddressList()[i]) / R);
    for(int j=0;j<data.getAddressList().size()-1;j++){
      if(data.getAddressList()[j].getAddress()==address.getAddress()&&data.getAddressList()[j+1].getAddress()!=address.getNextAddressList()[i].getAddress()){
        minAddress=address.getNextAddressList()[i];
        break;
      }
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
 

  string method = "svr";
    for(int dataNumber=1;dataNumber<=100;dataNumber++){
      Data data;
      ostringstream ossDataNumber;
      ossDataNumber << dataNumber;
      data.readAddressList("./data/address/processed/addressList/addressList"+ossDataNumber.str()+".csv");
      data.readFAddress(dataNumber);
      data.readRegression(dataNumber,method,I);
      identify(R,T,I,data,method,dataNumber);
    }
}

#endif

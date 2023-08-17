#ifndef INCLUDED_identifyMacAddress_cpp_
#define INCLUDED_identifyMacAddress_cpp_
#include <string>
#include <iostream>
#include "packet.h"
#include "data.h"
#include "address.h"
#include <vector>
#include <random>
using namespace std; // stdを省略

vector<Address> normalize(Address, int, int);
inline double getR(Address, Address);
inline bool checkR(Address, Address, int);
inline void writeCostTable(vector<Address>, int, int, int, string, int);
/**
 * @brief
 * 引数によって処理の仕方を変更する
 * 1　閾値RとIを1~20の範囲で変更した場合
 * 2 データ数を変動させた場合
 * 3 LineUp.javaを使用した場合
 * 4
 *
 */

inline void identify(int R, int T, int I, Data data, string method, int dataNumber)
{
  vector<Address> addressList = data.getAddressList();
  // fPacketを設定
  for (int i = 0; i < addressList.size(); i++)
  {
    addressList[i].setFPackets(I, data.getFAddress());
  }

  // Tで絞り込み
  for (int i = 0; i < addressList.size(); i++)
  {
    for (int j = 0; j < addressList.size(); j++)
    {
      double sub = addressList[j].getFTime() - addressList[i].getLTime();
      if (0 < sub && sub < T && i != j)
        addressList[i].addNextAddressList(addressList[j]);
    }
  }
  /*
    // 回帰値をセット
    for (int i = 0; i < addressList.size(); i++)
    {
      addressList[i].setRegression(data.getRegression(), I);
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

    //正規化の値をセット
    for (int i = 0; i < addressList.size(); i++)
    {
      //if(addressList[i].getNextAddressList().size()>2)
      addressList[i].setNextAddressList(normalize(addressList[i], R, T));
    }
    */

  writeCostTable(addressList, R, T, I, method, dataNumber);
}

// 閾値Rの条件を満たしているかチェックするメソッド
inline bool checkR(Address address, Address nextAddress, int R)
{
  // cout << getR(address, nextAddress) << endl;
  if (getR(address, nextAddress) <= R)
    return true;
  return false;
}

// 敷地Rの差をセットするメソッド
inline double getR(Address address, Address nextAddress)
{
  double count = 0;
  double sum = 0;
  for (int i = 0; i < nextAddress.getFPackets().size(); i++)
  {
    for (int j = 0; j < address.getRegression().size(); j++)
    {

      if (address.getRegression()[j].getTime() == nextAddress.getFPackets()[i].getTime())
      {
        sum += std::abs(address.getRegression()[j].getRssi() - nextAddress.getFPackets()[i].getRssi());
        // std::cout <<"00>"<<address.getRegression()[j].getRssi()<<","<<nextAddress.getFPackets()[i].getRssi()<<std::endl;
        count++;
        break;
      }
    }
  }
  // std::cout <<"sum="<< sum << ",count =" << count <<",sum/count="<<sum/count<< std::endl;
  if (count == 0)
    return 99;
  return sum / count;
}

// 正規化した後にnextAddressListから厳選するためのメソッド
std::vector<Address> normalize(Address address, int R, int T)
{
  int times = address.getNextAddressList().size();
  // 距離が最小のアドレスを格納するための変数
  for (int i = 0; i < times; i++)
  {
    // address.getNextAddressList()[i].setNormalizedT(std::pow(address.getNextAddressList()[i].getFTime() - address.getLTime() / (double)T, 2));
    address.getNextAddressList()[i].setNormalizedR(getR(address, address.getNextAddressList()[i]));
    // cout <<address.getAddress()<<","<<getR(address, address.getNextAddressList()[i]) <<","<<address.getNextAddressList()[i].getNormalized()<<endl;
  }
  return address.getNextAddressList();
}

inline void writeCostTable(vector<Address> addressList, int R, int T, int I, string method, int dataNumber)
{
  std::ofstream writing_file;
  ostringstream ossR;
  ossR << R;
  ostringstream ossT;
  ossT << T;
  ostringstream ossI;
  ossI << I;
  ostringstream ossDataNumber;
  ossDataNumber << dataNumber;
  std::string filename = "data/address/processed/costTable/" + method + "/" + ossDataNumber.str() + "_" + ossR.str() + "," + ossT.str() + "," + ossI.str() + ".csv";
  writing_file.open(filename, std::ios::out);

  // ランダムデバイスを生成して初期化
  std::random_device rd;

  // 乱数生成エンジンを初期化（メルセンヌ・ツイスタ法を使用）
  std::mt19937 gen(rd());

  // 生成するランダム値の範囲を指定
  std::uniform_int_distribution<> distribution(0, 100);
  for (int i = 0; i < addressList.size(); i++)
  {
    for (int j = 0; j < addressList.size(); j++)
    {
      double output = 999;
      for (int k = 0; k < addressList[i].getNextAddressList().size(); k++)
      {
        // writing_file << output[i] << std::endl;
        if (addressList[j].getAddress() == addressList[i].getNextAddressList()[k].getAddress())
          output = distribution(gen);
        // output=addressList[i].getNextAddressList()[k].getNormalized();
      }
      ostringstream ossOutput;
      ossOutput << output;
      // cout << addressList[i].getAddress() << ","<<addressList[j].getAddress() << "," << getR(addressList[i],addressList[j]) << "," << ossOutput.str() <<endl;
      writing_file << ossOutput.str();
      if (j != addressList.size() - 1)
        writing_file << ",";
    }
    writing_file << std::endl;
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
  string method = argv[2];
  int R = stoi(argv[3]);
  int T = stoi(argv[4]);
  int I = stoi(argv[5]);
  if (stoi(argv[1]) == 1)
  {
    for (int dataNumber = 1; dataNumber <= 100; dataNumber++)
    {
      Data data;
      ostringstream ossDataNumber;
      ossDataNumber << dataNumber;
      data.readAddressList("./data/address/processed/addressList/addressList" + ossDataNumber.str() + ".csv");
      data.readFAddress(dataNumber);
      // data.readRegression(dataNumber, method);
      for (int R = 1; R <= 20; R++)
      {
        for (int I = 1; I <= 20; I++)
        {
          identify(R, T, I, data, method, dataNumber);
        }
      }
    }
  }
  else if (stoi(argv[1]) == 2)
  {
    int R = stoi(argv[3]);
    T = stoi(argv[4]);
    int I = stoi(argv[5]);
    for (int dataNumber = 1; dataNumber <= 100; dataNumber++)
    {
      Data data;
      ostringstream ossDataNumber;
      ossDataNumber << dataNumber;
      data.readAddressList("./data/address/processed/addressList/addressList" + ossDataNumber.str() + ".csv");
      data.readFAddress(dataNumber);
      // data.readRegression(dataNumber, method, I);
      identify(R, T, I, data, method, dataNumber);
    }
  }
  else if (stoi(argv[1]) == 3)
  {

    Data data;
    data.readAddressList("./data/address/processed/addressList/addressList0.csv");
    int dataNumber = 0;
    data.readFAddress(dataNumber);
    // data.readRegression(dataNumber, method);
    //  データのリストを作成してから同定へ
    for (int R = 1; R <= 20; R++)
    {
      for (int I = 1; I <= 20; I++)
      {
        identify(R, T, I, data, method, dataNumber);
      }
    }
  }
  else if (stoi(argv[1]) == 4)
  {
    int dataNumber = 0;
    Data data;
    ostringstream ossDataNumber;
    ossDataNumber << dataNumber;
    data.readAddressList("./data/address/processed/addressList/addressList" + ossDataNumber.str() + ".csv");
    data.readFAddress(dataNumber);
    // data.readRegression(dataNumber, method, I);
    identify(R, T, I, data, method, dataNumber);
  }
  else
  {
    // 動作確認用
    Data data;
    data.readAddressList("./data/address/processed/addressList/addressList1.csv");
    int dataNumber = 1;
    data.readFAddress(dataNumber);
    // data.readRegression(dataNumber, method);
    //  データのリストを作成してから同定へ
    int R = 20;
    int I = 20;
    identify(R, T, I, data, method, dataNumber);
  }
}

#endif

#include <string>
#include <iostream>
#include "read.h"
#include <vector>
#include <random>
#include <ctime>   // time
#include <cstdlib> // srand,rand

//#include "address.h"
/*
 * アドレスの同定を行うプログラム
srcディレクトリからパスを通してコンパイル、実行すること
コマンドライン引数
1 閾値R
2 閾値T
3 閾値I
4 使用するデータ数
5 シュミレーション回数(n番目)
6 回帰手法 
*/

inline void identify(int, int, int, int, std::vector<Address>, std::string method);
inline std::vector<Address> selectData(std::vector<Address>, int);
inline bool contains(int, std::vector<int>);
std::vector<Address> normalize(Address, int, int);
inline std::vector<std::vector<double> > getFPackets();

/*
同定を行うメソッド
実質メインメソッド
R、T、Iはそれぞれ閾値
numOfTimes 実行回数(n番目)
numOfData 使用するデータ数
originalAddressList 全てのデータが入ったvector
method 回帰手法
*/

inline void identify(int R, int T, int I, int numOfData, std::vector<Address> originalAddressList, std::string method)
{
   //データ選出
   //選出後のデータをここに格納する
   std::vector<Address> addressList;
   if (numOfData != 20)
      addressList = selectData(originalAddressList, numOfData);
   else
      addressList = originalAddressList;

   //遅延設定とfPacketを設定
   for (int i = 0; i < addressList.size(); i++)
   {
      //addressList[i].setDelay(numOfTimes);
      addressList[i].setFPackets(I);
   }
   /*
    //アドレスリストのソート
   for(int i=0;i<addressList.size()-1;i++){
      for(int j = addressList.size()-1 ;j>i;j--){
         if(addressList[j-1].getFTime() > addressList[i].getFTime()){
            Address swap = addressList[i];
            addressList[i]=addressList[j-1];
            addressList[j-1] = swap;

         }
      }
   }*/

  

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
      addressList[i].setRegression(method,I);
      addressList[i].setFPackets(I);
   }


   //RとIで絞り込み
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
   //ここの処理に多大な時間が必要?

   //正規化
   for (int i = 0; i < addressList.size(); i++)
   {
      if (addressList[i].getNextAddressList().size() >= 2)
         addressList[i].setNextAddressList(normalize(addressList[i], R, T));
   }

   //結合分の処理
   
   //データ表示
   for (int i = 0; i < addressList.size(); i++)
   {
      addressList[i].printData();
   }
}

/**
 * データをランダムに選出するためのメソッド
 */
inline std::vector<Address> selectData(std::vector<Address> originalAddressList, int numOfData)
{
   //リストから削除していく方向に切り替える
   //ランダム整数選出のための処理
   int MIN = 1;
   int MAX = 20;
   std::random_device rd;
   std::default_random_engine eng(rd());
   std::uniform_int_distribution<int> distr(MIN, MAX);
   //選出済みの整数のリスト
   std::vector<int> dataNumbers;
   //選出データの格納先
   std::vector<Address> addressList;
   while (dataNumbers.size() < numOfData)
   {
      int random = distr(eng);
      std::ostringstream oss;
      oss << random;
      if (!contains(random, dataNumbers))
      {
         dataNumbers.push_back(random);
         for (int i = 0; i < originalAddressList.size(); i++)
         {
            std::ostringstream oss;
            oss << random;
            std::string randomStr = oss.str();
            if (randomStr.size() == 2)
            {
               //二桁の場合
               if (originalAddressList[i].getFileName().find(randomStr) != std::string::npos)
               {
                  addressList.push_back(originalAddressList[i]);
               }
            }
            else
            {
               //一桁の場合
               if (originalAddressList[i].getFileName().find(randomStr) != std::string::npos && originalAddressList[i].getFileName().size() == 5)
               {
                  addressList.push_back(originalAddressList[i]);
               }
            }
         }
      }
   }
   return addressList;
}

//引数のリストに引数の整数が含まれているか判定するメソッド
inline bool contains(int random, std::vector<int> dataNumbers)
{
   for (int i = 0; i < dataNumbers.size(); i++)
      if (dataNumbers[i] == random)
         return true;
   return false;
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

inline bool checkR(Address address, Address nextAddress, int R)
{
   if (getR(address, nextAddress) <= R)
      return true;
   return false;
}

inline double getR(Address address, Address nextAddress)
{
   double count = 0;
   double sum = 0;
   for (int i = 0; i < nextAddress.getFPackets().size(); i++)
   {
      for (int j = 0; j < address.getRegression().size(); j++)
      {
       
         if (address.getRegression()[j][0] == nextAddress.getFPackets()[i][0])
         {
            sum += std::abs(address.getRegression()[j][1] - nextAddress.getFPackets()[i][1]);
            //std::cout <<"00>"<<address.getRegression()[j][1]<<","<<nextAddress.getFPackets()[i][1]<<std::endl;
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

//変数の型を修正してidentifyメソッドに全てを投げる
int main(int argc, char *argv[])
{
   int R = std::stoi(argv[1]);
   int T = std::stoi(argv[2]);
   int I = std::stoi(argv[3]);
   int numOfData = std::stoi(argv[4]);
   std::string method = argv[5];
   identify(R, T, I, numOfData, readAddressList(), method);
}
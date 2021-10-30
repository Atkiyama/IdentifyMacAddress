#include <string>
#include <iostream>
#include "read.h"
#include <vector>
#include <random>
#include <ctime>        // time
#include <cstdlib>      // srand,rand

//#include "address.h"
/*
srcディレクトリからパスを通してコンパイル、実行すること
コマンドライン引数
1 閾値R
2 閾値T
3 閾値I
4 使用するデータ数
5 シュミレーション回数(n番目)
6 回帰手法 
*/

void identify(int,int,int,int,int,std::vector<Address>,std::string method);
std::vector<Address> selectData(std::vector<Address>,int);
bool contains(int,std::vector<int>);
std::vector<Address> normalize(Address,int,int);

void identify(int R,int T,int I,int numOfTimes,int numOfData,std::vector<Address> originalAddressList,std::string method){
   //データ選出
   std::vector<Address> addressList;
   if(numOfData !=20)
      addressList = selectData(originalAddressList,numOfData);
   else
      addressList = originalAddressList;
   //std::cout << addressList.size() <<std::endl;


   //遅延設定
   for(int i=0;i<addressList.size();i++){
      //std::cout << addressList.size()<<std::endl;
      addressList[i].setDelay(numOfTimes);
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
   for(int i = 0; i < addressList.size() ;i++){
      for(int j = 0; j < addressList.size() ;j++){
         double sub = addressList[j].getFTime()-addressList[i].getLTime();
         if(0<sub&&sub<T&&i!=j)
            addressList[i].addNextAddressList(addressList[j]);
      }
    }
   
   
   //回帰
    for(int i=0;i<addressList.size();i++){
      addressList[i].setFPackets(I);
      addressList[i].setRegression(method,I);
    }

    
   
   //RとIで絞り込み
    for(int i=0;i<addressList.size();i++){
      std::vector<Address> replace;
      int times = addressList[i].getNextAddressList().size();
      for(int j=0;j<times;j++){
         if(std::abs(addressList[i].extract(j,I)-addressList[i].getNextAddressList()[j].getFPackets())<=R){
            replace.push_back(addressList[i].getNextAddressList()[j]);
         }
      }
      addressList[i].setNextAddressList(replace);
    }
    

   
   
   //正規化
    for(int i=0;i<addressList.size();i++){
       if(addressList[i].getNextAddressList().size()>=2)
         addressList[i].setNextAddressList(normalize(addressList[i],R,T));
    }

    

    //結合分の処理

   //データ表示
    for(int i=0;i<addressList.size();i++){
         addressList[i].printData();
    }
}

std::vector<Address> selectData(std::vector<Address> originalAddressList,int numOfData){
   //リストから削除していく方向に切り替える
   int MIN = 1;
   int MAX = numOfData;
   std::random_device rd;
   std::default_random_engine eng(rd());
   std::uniform_int_distribution<int> distr(MIN, MAX);
   std::vector<int> dataNumbers ; 
   std::vector<Address> addressList;
   while(dataNumbers.size()<numOfData){
      int random = distr(eng);
      //std::cout << random <<std::endl;
      if(!contains(random,dataNumbers)){
         dataNumbers.push_back(random);
         for(int i=0;i<originalAddressList.size();i++){
            if(originalAddressList[i].getFileName().find(random)!=std::string::npos)
               addressList.push_back(originalAddressList[i]);
         }     
      }
   }

   return addressList;

   
}

bool contains(int random,std::vector<int> dataNumbers){
   for(int i=0;i<dataNumbers.size();i++)
      if(dataNumbers[i]==random)
         return true;
   return false;
}

std::vector<Address> normalize(Address address,int R,int T){
   int times = address.getNextAddressList().size();
   double min = 999999;
   Address minAddress =address.getNextAddressList()[0];
   for(int i=0;i<times;i++){
      address.getNextAddressList()[i].setNormalizedT(std::pow(address.getNextAddressList()[i].getFTime()-address.getLTime()/(double)T,2));
      address.getNextAddressList()[i].setNormalizedR(std::pow(address.getNextAddressList()[i].getFPackets()-address.getERegression()/(double)R,2));
      double distance = address.getNextAddressList()[i].getNormalized();
      if(distance<min){
         min = distance;
         minAddress = address.getNextAddressList()[i];
      }

   }
   std::vector<Address> replace;
   replace.push_back(minAddress);
   return replace;
   
    
  
}


int main(int argc, char *argv[]){
   int R = std::stod(argv[1]);
   int T = std::stod(argv[2]);
   int I = std::stod(argv[3]);
   int numOfData = std::stod(argv[4]);
   int numOfTimes = std::stod(argv[5]);
   std::string  method = argv[6];
   identify(R,T,I,numOfTimes,numOfData,readAddressList(),method);
  
    
   
}
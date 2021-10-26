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
1 閾値Rの上限
2 閾値T
3 閾値Iの上限
4 使用するデータ数
5 シュミレーション回数 
*/

void identify(int,int,int,int,int,std::vector<Address>);
std::vector<Address> selectData(std::vector<Address>,int);
bool contains(int,std::vector<int>);

void identify(int R,int T,int I,int numOfTimes,int numOfData,std::vector<Address> originalAddressList){
   std::vector<Address> addressList;
   if(numOfData !=20)
      addressList = selectData(originalAddressList,numOfData);
   else
      addressList = originalAddressList;
   //std::cout << addressList.size() <<std::endl;

   for(int i=0;i<addressList.size();i++){
      std::cout << addressList.size()<<std::endl;
      addressList[i].setDelay(numOfTimes);
    }

   for(int i = 0; i < addressList.size() ;i++){
      for(int j = 0; j < addressList.size() ;j++){
         double sub = addressList[j].getFTime()-addressList[i].getLTime();
         if(0<sub&&sub<T&&i!=j)
            addressList[i].addNextAddressList(addressList[j]);
      }
    }

    for(int i=0;i<addressList.size();i++){
       //回帰
    }
   //正規化
   //データ表示
}

std::vector<Address> selectData(std::vector<Address> originalAddressList,int numOfData){
   //リストから削除していく方向に切り替える
   int MIN = 1;
   int MAX = 20;
   std::random_device rd;
   std::default_random_engine eng(rd());
   std::uniform_int_distribution<int> distr(MIN, MAX);
   std::vector<int> dataNumbers; 
   std::vector<Address> addressList;
   while(dataNumbers.size()<=numOfData){
      int random = distr(eng);
      //std::cout << random <<std::endl;
      if(!contains(random,dataNumbers)){
         dataNumbers.push_back(random);
         for(int i=0;i<originalAddressList.size();i++){
            if(originalAddressList[i].getFileName().find(random)==std::string::npos)
               addressList.push_back(originalAddressList[i]);
         }     
      }
   }

   return addressList;

   //これから作る
}

bool contains(int random,std::vector<int> dataNumbers){
   for(int i=0;i<dataNumbers.size();i++){
      if(dataNumbers[i]==random){
         std::cout << dataNumbers.size() <<std::endl;
         return true;
      }
   }
   std::cout << dataNumbers.size() <<std::endl;
   return false;
}


int main(int argc, char *argv[]){
   int R = std::stod(argv[1]);
   int T = std::stod(argv[2]);
   int I = std::stod(argv[3]);
   int numOfData = std::stod(argv[4]);
   int numOfTimes = std::stod(argv[5]);
   int r;
   int i;
   int n;
   for(r=1;r<R;r++){
       for(i=1;i<I;i++){
          for(n=1;n<numOfTimes;n++){
             identify(r,T,I,n,numOfData,readAddressList());
         }
      }
   }
    
   
}
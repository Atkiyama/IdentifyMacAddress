#include <string>
#include <iostream>
#include "data.h"
#include <vector>
#include <random>


/**
 * @brief 
 * 引数によって処理の仕方を変更する
 * 1
 * 2
 * 3 LineUp.javaを使用した場合
 * 4
 * 
 */

inline void readAddressList(std::string);

int main(int argc, char *argv[])
{

   
   if(std::stoi(argv[1])==3){
      std::cout << argv[1] <<std::endl;
      Data data;
      data.readAddressList("./data/address/processed/addressList/addressList0.csv");
   }
}
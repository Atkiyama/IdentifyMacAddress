#include <stdio.h>
#include <fstream>
#include <string>
#include <sstream>
#include <iostream>
#include <vector>
#include <map>
#include "address.h"
#include "packet.h"

/**
 * @brief
 * addressList
 *  
 * linerRegression
 * svr
 * bagging
 * 
 * 
 */
class Data{
    public:
        std::vector<Address> addressList;
        std::map<std::string,std::vector<Packet> > fAddress;
        std::map<std::string,std::vector<Packet> > linerRegression;
        std::map<std::string,std::vector<Packet> > svr;
        std::map<std::string,std::vector<Packet> > bagging;

    inline void readAddressList(std::string fileName){
        std::ostringstream oss;
        oss << fileName;
        std::ifstream ifs(oss.str());
        std::string data;
        std::string buf;
        if (!ifs)
        {
            std::cout << "ファイルが開けませんでした。" << std::endl;
            std::cout << oss.str() << std::endl;
            std::cin.get();
        }

        //ヘッダを読み飛ばす
        std::getline(ifs, buf);

        while (!ifs.eof())
        {
            std::getline(ifs, buf);
            if (buf.size() != 0)
                addressList.push_back(makeAddress(buf));
        }


    }

    /**
    * 引数の文字列からアドレスのクラスを生成するメソッド
    */
    inline Address makeAddress(std::string buf)
    {   
        int first = buf.find(",");
        int second = buf.find(",", first + 1);
        int third = buf.find(",", second + 1);
        std::string fileName = buf.substr(0, first);
        std::string address = buf.substr(first + 1, second - first - 1);
        std::string fTime = buf.substr(second + 1, third - second - 1);
        std::string lTime = buf.substr(third + 1);
        //std::cout << "fileName>" << fileName << ",address>" << address << ",fTime>" << fTime << ",lTime>" << lTime << std::endl;
        return Address(fileName, address, std::stod(fTime), std::stod(lTime));
    }
};
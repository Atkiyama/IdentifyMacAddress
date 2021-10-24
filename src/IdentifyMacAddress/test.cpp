#include <stdio.h>
#include "packet.h"
#include <iostream>
#include <string>

int main(){
  Packet packet("テスト",100,50);
  packet.print();
  return 0;
}

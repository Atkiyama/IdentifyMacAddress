from bluepy.btle import Scanner, DefaultDelegate
import bluepy.btle as btle
import datetime
import tkinter.messagebox as MSG
class ScanDelegate(DefaultDelegate):
       def __init__(self):
               DefaultDelegate.__init__(self)
       def handleDiscovery(self, dev, isNewDev, isNewData):
               t_now = datetime.datetime.now().time()
               print("%s %s (%s) [%s] %s dBm" % (t_now, dev.addr, dev.addrType, dev.iface, dev.rssi), end="")
               for (adtype, desc, value) in dev.getScanData():
                     print(" %s(%s) = %s" % (desc, adtype, value), end="")
               print("")
scanner = Scanner().withDelegate(ScanDelegate())
while True:
       try:
               scanner.scan(1.0)
       except btle.BTLEException:
           print('BTLE Exception while scanning')
               #MSG('BTLE Exception while scanning')

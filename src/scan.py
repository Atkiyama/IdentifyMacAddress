from bluepy.btle import Scanner, DefaultDelegate
import datetime
class ScanDelegate(DefaultDelegate):
       def __init__(self):
               DefaultDelegate.__init__(self)
       def handleDiscovery(self, dev, isNewDev, isNewData):
               t_now = datetime.datetime.now().time()
               print("%s %s %s dBm" % (t_now, dev.addr, dev.rssi))
scanner = Scanner().withDelegate(ScanDelegate())
scanner.scan(3600.0)

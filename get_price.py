#!/usr/bin/env python
# coding=utf-8

import requests
import time
import json


API_URL = 'https://api.binance.com/'

headers = {'Accept': 'application/json',
           'User-Agent': 'binance/python'
           }

url = API_URL + "/api/v1/ticker/24hr?"
payload = "symbol=IOTABTC"
print url + payload

while True:
    r = requests.get(url + payload, headers=headers)
    #print(r.text)
    iotaData = json.loads(r.text)
    print "Price: " + iotaData["bidPrice"]
    time.sleep(5)

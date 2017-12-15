#!/usr/bin/env python
# coding=utf-8

import hashlib
import hmac
import requests
import six
import time
from urllib import urlencode


API_URL = 'https://api.binance.com/'
API_KEY = ''
API_SECRET = ''

#
#
#
#
#
#
#
#
#
#
#
#
#

headers = {'Accept': 'application/json',
           'User-Agent': 'binance/python',
           'X-MBX-APIKEY': API_KEY}

session = requests.session()
session.headers.update(headers)

timestamp = int(time.time() * 1000)
print str(timestamp)

url = API_URL + "api/v3/order?"
# symbol=LTCBTC&side=BUY&type=LIMIT&timeInForce=GTC&quantity=1&price=0.1&recvWindow=5000&timestamp=1499827319559
payload = "symbol=IOTABTC&side=SELL&type=LIMIT&timeInForce=GTC&quantity=10&price=0.0003&recvWindow=5000&timestamp=" + str(timestamp)
#payload = "timestamp=" + str(timestamp)
print url + payload

postData = {
    "symbol": "IOTABTC",
    "side": "SELL",
    "type": "LIMIT",
    "timeInForce": "GTC",
    "quantity": 1,
    "price": 0.0003,
    "timestamp": long(timestamp)
}

# Generate signature
# query_string = urlencode(url)
m = hmac.new(API_SECRET.encode('utf-8'), payload.encode('utf-8'), hashlib.sha256)
signature = m.hexdigest()
print signature
r = requests.post(url + payload+ "&signature=" + signature, headers=headers)
print(r.text)

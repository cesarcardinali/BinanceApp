#!/usr/bin/env python
# coding=utf-8

import hashlib
import hmac
import requests
import six
import time
from urllib import urlencode


API_URL = 'https://api.binance.com/'
API_KEY = '#sua key#'
API_SECRET = '#seu secret#'

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

headers = {'X-MBX-APIKEY': API_KEY}

session = requests.session()
session.headers.update(headers)

timestamp = int(time.time() * 1000)
print str(timestamp)

url = API_URL + "api/v3/order?"
payload = "symbol=IOTABTC&side=SELL&type=LIMIT&timeInForce=GTC&quantity=1&price=0.0003&timestamp=" + str(timestamp)
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

r = requests.get(url + payload + "&signature=" + signature, headers=headers)
print(r.text)

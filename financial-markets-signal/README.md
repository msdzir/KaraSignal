## About
This is for reading Assets Live Price from markets end point and caching the data.
Also gives some end point for working with cach provider. 

## Endpoints
There are two kindes of end points:
- Friest for manageing the service (like adding symbosl for reading data and stoping and running reading process)
- Second for reading data from cach provider

## Redis configuration 
RedisConfig.hostName = your redis host name 
RedisConfig.portNum = your redis port number

## Properties
server.port=8090

HINT: For working with this service:
- Friest you should lunch the service and set the market name with "http://localhost:8090/assetsLivePriceService/setMarket/{Market_Name}" end point. 
- Then add the symbols name to the symbols array with "http://localhost:8090/assetsLivePriceService/addAsset/{Asset_Name}" end point.
- Then lunch live price reader with "http://localhost:8090/assetsLivePriceService/startReading"
- There are other endpoints for adding new asset to list for reading their data and restarting service.

--> Market name for reading data : "Binance"
---> List of assets in Binance marker : "BTCUSDT" , "BNBUSDT" , "TTRUSDT"

--- Binance Data Payload ---
{
  "e": "aggTrade",    // Event type
  "E": 1672515782136, // Event time
  "s": "BNBBTC",      // Symbol
  "a": 12345,         // Aggregate trade ID
  "p": "0.001",       // Price
  "q": "100",         // Quantity
  "f": 100,           // First trade ID
  "l": 105,           // Last trade ID
  "T": 1672515782136, // Trade time
  "m": true,          // Is the buyer the market maker?
  "M": true           // Ignore
}

Binance Link : https://github.com/binance/binance-spot-api-docs/blob/master/web-socket-streams.md

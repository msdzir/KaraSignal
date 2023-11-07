/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.finitx.karasignal.controller;

import com.finitx.karasignal.AssetLivePriceService;
import com.finitx.karasignal.IAssetsLivePriceReader;
import com.finitx.karasignal.constant.StrategyConstantsHolder;
import com.finitx.karasignal.BinanceDataReader.assetsLiveDataReaderFromBinance;
import com.finitx.karasignal.Redis.ICacheProvider;
import com.finitx.karasignal.Redis.RedisCacheProvider;
import java.io.IOException;
import java.util.Date;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller class for URL handling
 *
 */
@Controller
@Component
public class AssetsLivePriceServiceController {

    /**
     * Strategy design pattern to describe the assets data from witch market
     * should read
     *
     */
    private IAssetsLivePriceReader assetsLivePriceReader;
    
    @Autowired
    private RedisCacheProvider cachProvider; 

    private final Date date = new Date();

    public AssetsLivePriceServiceController(IAssetsLivePriceReader assetsLivePriceReader) {
        this.assetsLivePriceReader = assetsLivePriceReader;
    }
    
    /**
     * This endpoint sends assets live price from cached data
     * @param assetName
     * @return 
     **/
    @RequestMapping("assetsLivePriceService/getLivePrice/{assetName}")
    public ResponseEntity<String> getAssetLivePrice(@PathVariable("assetName") String assetName) {
        return this.responseMaker(this.cachProvider.getTopFromList(assetName), HttpStatus.OK);
    }
    
    /**
     * This endpoint sends all assets live price from cached data
     * @return 
     **/
    @RequestMapping("assetsLivePriceService/getAllAssetsLivePrice")
    public ResponseEntity<String> getAllAssetsLivePrice() {
        JSONObject obj = new JSONObject();
        for(String assetName : this.assetsLivePriceReader.getAllAvailableSymbols()){
            System.out.println((assetName.split("@")[0]).toUpperCase());
            obj.put((assetName.split("@")[0]).toUpperCase(), this.cachProvider.getTopFromList((assetName.split("@")[0]).toUpperCase()));
        }
        return this.responseMaker(obj.toJSONString(), HttpStatus.OK);
    }
 
    /**
     * This endpoint sets the market name.
     * @param marketName
     * @return 
     * @throws java.io.IOException
     **/
    @RequestMapping("assetsLivePriceService/setMarket/{marketName}")
    @ResponseBody
    public ResponseEntity<String> setMarketToGetAssetsLivePrice(@PathVariable("marketName") String marketName) throws IOException {
        System.out.println("#Market Name : " + marketName);
        switch (marketName) {
            case StrategyConstantsHolder.BinanceMarketName -> {
                this.assetsLivePriceReader = AssetLivePriceService.context.getBean(assetsLiveDataReaderFromBinance.class);
                return this.responseMaker("MARKET SETS AS BINANCE", HttpStatus.OK);
            }
            default -> {
                return this.responseMaker("THERE IS NOT SUCH MARKET", HttpStatus.BAD_REQUEST);
            }
        }
    }

    /**
     * This endpoint adds asset name to the Subscription List
     * @param assetName
     * @return 
     **/
    @RequestMapping("assetsLivePriceService/addAsset/{assetName}")
    @ResponseBody
    public ResponseEntity<String> addAsset(@PathVariable("assetName") String assetName) {
        this.assetsLivePriceReader.addAsset(assetName);
        return this.responseMaker("ASSET ADDED", HttpStatus.OK);
    }

    /**
     * Starts reading the Subscription Lists live price from the mentioned Market
     * @return 
     **/
    @RequestMapping("assetsLivePriceService/startReading")
    @ResponseBody
    public ResponseEntity<String> startAssetsLivePriceReading() {
        this.assetsLivePriceReader.readAssetsLivePrice();
        return this.responseMaker("READING STARTED", HttpStatus.OK);
    }
    
//    @RequestMapping("assetsLivePriceService/getLivePrice/{assetName}")
//    public ResponseEntity getAssetsLivePrice() {
//        this.assetsLivePriceReader.readAssetsLivePrice();
//        return this.responseMaker("READING STARTED", HttpStatus.OK);
//    }

    /**
     * Restarts the reading process
     * @return 
     **/
    @RequestMapping("assetsLivePriceService/restartService")
    @ResponseBody
    public ResponseEntity<String> restartService() {
        this.assetsLivePriceReader.restartReading();
        return this.responseMaker("SERVICE RESTARTED", HttpStatus.OK);
    }

    /**
     * Stops reading
     * @return 
     **/
    @RequestMapping("assetsLivePriceService/stopReading")
    @ResponseBody
    public ResponseEntity<String> stopAssetsLivePriceReading() {
        this.assetsLivePriceReader.stopReading();
        return this.responseMaker("SERVICE STOPED", HttpStatus.OK);
    }

    /**
     * Private function for making the response messages
     **/
    private ResponseEntity<String> responseMaker(String payload, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("DATE", this.date.toGMTString());
        return new ResponseEntity<>(
                payload,
                headers,
                status
        );
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.finitx.karasignal.BinanceDataReader;

import com.binance.connector.client.WebSocketStreamClient;
import com.binance.connector.client.impl.WebSocketStreamClientImpl;
import com.binance.connector.client.utils.websocketcallback.WebSocketMessageCallback;
import com.finitx.karasignal.IAssetsLivePriceReader;
import java.util.ArrayList;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Commands of functions are in the implemented interface
 * 
 * HINT: This class reads assets data from the Binance APIs and caches in the Redis with special format
 */
@Component
public class assetsLiveDataReaderFromBinance implements IAssetsLivePriceReader{
    
    @Autowired
    @Qualifier("redisDataCacher")
    private WebSocketMessageCallback binanceMessageHandler;
    
    private int binanceStreamID;
    private WebSocketStreamClient wsStreamClient;
    
    /**
     * List of subscription symbols to read their data
     **/
    private final ArrayList<String> assetsSymboleName = new ArrayList<>();
    
    public assetsLiveDataReaderFromBinance(){
        this.assetsSymboleName.add("BTCUSDT");
        this.assetsSymboleName.add("BNBUSDT");
    }

    @Override
    public String addAsset(String assetsSymboleName) {
        //This lines make the Signals Stream name with the Binance formate
        //Binance Stream format : <SYMBOL-NAME>@trade
        String stream = assetsSymboleName + "@trade"; 
        
        this.assetsSymboleName.add(stream);
        return "SYMBOLE ADDED";
    }

    @Override
    public ArrayList<String> getAllAvailableSymbols() {
        return this.assetsSymboleName;
    }

    @Override
    public void readAssetsLivePrice() {
        this.wsStreamClient = new WebSocketStreamClientImpl();
        this.binanceStreamID = wsStreamClient.combineStreams(this.assetsSymboleName, this.binanceMessageHandler);
    }

    @Override
    public void restartReading() {
        this.wsStreamClient.closeConnection(this.binanceStreamID);
        this.readAssetsLivePrice();
    }

    @Override
    public void stopReading() {
        this.wsStreamClient.closeConnection(binanceStreamID);
    }

}

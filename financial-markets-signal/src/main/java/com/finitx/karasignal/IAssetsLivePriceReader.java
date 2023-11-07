/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.finitx.karasignal;

import java.util.ArrayList;

/**
 * This interface defines data reader classes functions.
 * The Data Reader Classes, read data of assets from different markets with different function deployment.
 * @author alireza
 */
public interface IAssetsLivePriceReader {
    
    /**
     * Subscription list:
     * Contains the name of assets to read their live data
     * 
     * HINT: Because of the problems of static parameters in interface we make a
     * local private variable exactly as same as bellow:
    **/
    //ArrayList<String> assetsSymboleName = new ArrayList<>();
    
    /**
     * Adds new asset to subscription list
     * @param assetsSymboleName
     * @return 
    **/ 
    String addAsset(String assetsSymboleName);
    
    /**
     * Returns all the symbols name in subscription list
     * @return 
    **/
    ArrayList<String> getAllAvailableSymbols();
    
    /**
     * Reads Live Price of all symbols in the Subscription List
     * 
     * Its deployment is base on the market
    **/
    void readAssetsLivePrice();
    
    /**
     * Restarts server and reads symbols live price base on the Subscription List changes
    **/
    
    void restartReading();
    
    /**
     * Stops reading
    **/
    void stopReading();
    
}

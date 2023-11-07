/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.finitx.karasignal.constant;

/**
 *
 * @author alireza
 */
public class MicroServiceURL {
    
    public static String usersListOfActiveSignal(String userID){
        return "http://localhost:8091/ShowUsersSubscriptionList/" + userID;
    }
    
    public static String usersListOfActiveSignalInMarket(String userID, String marketName){
        return "http://localhost:8091/ShowUsersSubscriptionList/" + userID + "/" + marketName;
    }
    
    public static String deleteAssetSubscription(String userID , String assetID){
        return "http://localhost:8091/deleteSubscriptedSignal/"+userID+"/"+ assetID;
    }
    
    public static String getAllAssetsInfo(){
        return "http://localhost:8091/ShowAllAssets";
    }
    
    public static String addAssetToUserSubscriptionList(String userID , String assetID , String userSavedName){
        return "http://localhost:8091/addNewSiganlSubscription/"+userID+"/" + assetID + "/" + userSavedName;
    }
    
    public static String addAssetToUserSubscriptionList(String userID , String assetID ,String userSavedName , String targetPrice){
        return "http://localhost:8091/addNewSiganlSubscription/"+userID+"/" + assetID + "/" + userSavedName + "/" + targetPrice;
    }
    
    public static String editSignalSubscription(){
        return "http://localhost:8091/editSubscriptedSignal";
    }

    public static String getAssetsLivePrice(String assetName){
        return "http://localhost:8090/assetsLivePriceService/getLivePrice/" + assetName;
    }
    
    public static String allAssetsLivePrice(){
        return "http://localhost:8090/assetsLivePriceService/getAllAssetsLivePrice";
    }
    
}

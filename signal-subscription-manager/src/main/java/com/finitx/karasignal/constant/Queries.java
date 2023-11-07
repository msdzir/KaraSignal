/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.finitx.karasignal.constant;

/**
 * This class contains all queries for working with database
 *
 * @author alireza
 */
public class Queries {

    /**
     * Creates Query of getting users subscription list
     *
     * @param userID
     * @return 
     *
     */
    public static String getUserSubscriptionList(String userID) {
        return "select * from usersubscription where userid = '" + userID + "';";
    }
    
    /**
     * Creates Query of getting All Assets Information
     *
     * @return 
     *
     */
    public static String getAllAssetsInfo(){
        return "select * from assetssignal inner join markets on assetssignal.marketID = markets.marketID;";
    }

    /**
     * Creates Query of inserting signal to users subscription list
     *
     * @param userID
     * @param assetID
     * @param userSavedName
     * @param targetNumber
     * @return 
     *
     */
    public static String insertNewSubscriptionToUserList(String userID, String assetID, String userSavedName, int targetNumber) {
        return "INSERT INTO usersubscription VALUES('" + userID + "' , '" + assetID + "' , '" + userSavedName + "' , " + targetNumber + ");";
    }

    /**
     * Creates Query of Editing users subscription list
     *
     * @param userSavedName
     * @param userID
     * @param assetID
     * @param tergetPrice
     * @return 
     *
     */
    public static String updateSubscriptionValues(String userSavedName, String userID, String assetID, int tergetPrice) {
        return "UPDATE usersubscription SET usersavedname = '"+userSavedName+"' ,targetprice = "+tergetPrice+"\n"
                + "WHERE userID = '"+userID+"' and assetID = '"+assetID+"';";
    }

    /**
     * Creates Query of deleting signal from users subscription list
     *
     * @param userID
     * @return 
     *
     */
    public static String deleteSubscriptionFromUserList(String userID, String assetID) {
        return "DELETE FROM usersubscription  WHERE userID = '" + userID + "' and assetID = '" + assetID + "';";
    }

}

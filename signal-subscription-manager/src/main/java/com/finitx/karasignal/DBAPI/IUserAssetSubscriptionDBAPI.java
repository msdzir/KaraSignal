/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.finitx.karasignal.DBAPI;

import java.util.ArrayList;
import org.json.simple.JSONObject;

/**
 * It's an API for working with subscription database
 *
 * @author alireza
 */
public interface IUserAssetSubscriptionDBAPI {

    /**
     * Returns all assets descriptions as a list of JSON objects that contains
     * assets ID and its description.The returned information of this function
     * would used for the SEARCHING that we use the descriptions as documents
     * for INFORMATION RETRIVAL
     *
     * @return
     */
    ArrayList<JSONObject> getAllAssetsDescription();

    /**
     * Returns all markets descriptions as a list of JSON objects that contains
     * markets ID and its description.The returned information of this function
     * would used for the SEARCHING that we use the descriptions as documents
     * for INFORMATION RETRIVAL
     *
     * @return
     *
     */
    ArrayList<JSONObject> getAllMarketsDesctiption();

    /**
     * returns all Assets Information including asset name and ID and
     * description with it's market info
     *
     * @return 
     *
     */
    ArrayList<JSONObject> getAllAssetsInfo();

    /**
     * returns users subscriptions information as ArrayList of JSONObject
     *
     * Returned JSON formate: {assetID : "number" , "userSavedName" : "String" ,
     * "userID" : "String"}
     *
     * @param userID
     * @return
     */
    ArrayList<JSONObject> getUsersSubscriptionList(String userID);

    /**
     * adds the signal into users subscription list
     *
     * @param userID
     * @param assetID
     * @param userSavedName
     * @param targetNumber
     * @return
     */
    String addNewSignalSubscription(String userID, String assetID, String userSavedName, int targetNumber);

    /**
     * edits the subscribe signal
     *
     * @param userID
     * @param assetID
     * @param userSavedName
     * @param targetPrice
     * @return
     *
     */
    String editSubscribedSignal(String userID, String assetID, String userSavedName, int targetPrice);

    /**
     * deletes the subscribed signal from users subscriptions
     *
     * @param userID
     * @param assetID
     * @return
     *
     */
    String deleteSubscribedSignal(String userID, String assetID);

}

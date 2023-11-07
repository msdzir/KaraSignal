/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.finitx.karasignal.controllers;

import com.finitx.karasignal.DBAPI.UserAssetsSbuscribedDBAPI;
import java.util.ArrayList;
import java.util.Map;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.finitx.karasignal.constant.DBConfig;
import com.finitx.karasignal.constant.LogConstant;
import java.util.Iterator;
import com.finitx.karasignal.DBAPI.IUserAssetSubscriptionDBAPI;
import org.json.simple.JSONArray;

/**
 *  This class is a URL controller
 * @author alireza
 */
@Controller
public class ListOfSignalSubscriptedAPIController {

    private String message = "";

    private final IUserAssetSubscriptionDBAPI userAssetsSubscriptionDBAPI;

    /**
     * Constructor
     *
     */
    public ListOfSignalSubscriptedAPIController() {
        this.userAssetsSubscriptionDBAPI = new UserAssetsSbuscribedDBAPI(
                DBConfig.url,
                DBConfig.userName,
                DBConfig.passWord
        );
    }

    /**
     * Shows users subscription list as a JSON object 
     * List Format : {"counter number" , {JSONArray contains the data of subscription list}}
     * @param userID
     * @return 
     **/
    @RequestMapping("/ShowUsersSubscriptionList/{userID}")
    public ResponseEntity showUsersSubscriptionList(@PathVariable String userID) {
        JSONArray responseList = new JSONArray();
        try {
            ArrayList<JSONObject> userSubscriptionList = this.userAssetsSubscriptionDBAPI.getUsersSubscriptionList(userID);
            System.out.println(userID);
            Iterator itr = userSubscriptionList.iterator();
            while(itr.hasNext()){
                responseList.add(itr.next());
            }
            
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.ok("Null pointer Exception");
        }
        return ResponseEntity.ok(responseList.toJSONString());
    }
    
    /**
     * This function returns all Assets Information 
     * 
     * @return 
     * 
     **/
    @RequestMapping("/ShowAllAssets")
    public ResponseEntity showAllAssetsList() {
        JSONArray responseList = new JSONArray();
        try {
            ArrayList<JSONObject> userSubscriptionList = this.userAssetsSubscriptionDBAPI.getAllAssetsInfo();
            Iterator itr = userSubscriptionList.iterator();
            while(itr.hasNext()){
                responseList.add(itr.next());
            }
            
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.ok("Null pointer Exception");
        }
        return ResponseEntity.ok(responseList.toJSONString());
    }

    /**
     * Adds asset to the users subscription list
     * 
     * @param userID
     * @param assetsID
     * @return 
     **/
    @RequestMapping("/addNewSiganlSubscription/{userID}/{assetsID}")
    public ResponseEntity addNewSiganlSubscription(@PathVariable String userID, @PathVariable String assetsID) {
        return ResponseEntity.ok(this.userAssetsSubscriptionDBAPI.addNewSignalSubscription(userID, assetsID, "NEW SIGNAL" , -1));
    }
    
    /**
     * Adds asset to the users subscription list
     * 
     * @param userID
     * @param assetsID
     * @param targetPrice
     * @return 
     **/
    @RequestMapping("/addNewSiganlSubscription/{userID}/{assetsID}/{targetPrice}")
    public ResponseEntity addNewSiganlSubscription(
            @PathVariable String userID, 
            @PathVariable String assetsID , 
            @PathVariable String targetPrice) {
        return ResponseEntity.ok(this.userAssetsSubscriptionDBAPI.addNewSignalSubscription(userID, assetsID, "NEW SIGNAL" , Integer.parseInt(targetPrice)));
    }

    /**
     * Edits subscribed signal info
     * @param request
     * @return 
     **/
    @RequestMapping("/editSubscriptedSignal")
    public ResponseEntity editSubscriptedSignal(@RequestBody Map<String, String> request) {
        return ResponseEntity.ok(this.userAssetsSubscriptionDBAPI.editSubscribedSignal(
                request.get("userID"),
                request.get("assetID"),
                request.get("userSavedName"),
                Integer.parseInt(request.get("targetPrice"))
        ));
    }

    /**
     * Deletes subscribed signal from users list
     * @param userID
     * @param assetsID
     * @return 
     **/
    @RequestMapping("/deleteSubscriptedSignal/{userID}/{assetsID}")
    public ResponseEntity deleteSubscriptedSignal(@PathVariable String userID, @PathVariable String assetsID) {
        return ResponseEntity.ok(this.userAssetsSubscriptionDBAPI.deleteSubscribedSignal(userID, assetsID));
    }

}

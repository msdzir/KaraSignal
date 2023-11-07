package com.finitx.karasignal.controller;

import com.finitx.karasignal.constant.MicroServiceURL;
import com.finitx.karasignal.service.UserService;
import com.finitx.karasignal.websocket.messageDTO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@CrossOrigin(origins = "*")
public class DashboardController {

    private URL url;
    private HttpURLConnection connection;
    private Scanner inputReader;

    private final JSONParser jsonParser = new JSONParser();

    private final ArrayList<String> AssetsList = new ArrayList<String>();

    private JSONObject allAssetsLivePrice;

    private final UserService userService;

    /**
     * At the constructor function the timer function scheduled to read all
     * assets live price
     *
     */
    public DashboardController(UserService userService) {
        this.userService = userService;
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> iterativeReadAllAssetsLivePrice(), 0, 1, TimeUnit.SECONDS);
    }

    /**
     * This Private function reads all assets live price in every 1 seconds
     *
     */
    private void iterativeReadAllAssetsLivePrice() {
        String data = this.httpGetMessageMaker(MicroServiceURL.allAssetsLivePrice());
        try {
            this.allAssetsLivePrice = (JSONObject) this.jsonParser.parse(data);
            System.out.println("All assets live price are : \n" + this.allAssetsLivePrice.toJSONString());
        } catch (ParseException ex) {
            System.out.println("SomeException");
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * There is web socket end point handler. Gets a list of symbol from user
     * and returns their live price in JSON formate
     *
     * @param dto
     * @return
     */
    @MessageMapping(value = "/getLivePrice")
    @SendTo("/topic/livePrice")
    public JSONObject getAssetsLivePriceWS(@RequestBody messageDTO dto) {
        String[] subscribedAssets = dto.getMessage();
        if (subscribedAssets[0].equals("ALL")) {
            return this.allAssetsLivePrice;
        }
        JSONObject responseArray = new JSONObject();
        System.out.println(this.allAssetsLivePrice.toJSONString());
        System.out.println("user assets : ");
        for (String asset : subscribedAssets) {
            responseArray.put(asset, this.allAssetsLivePrice.get(asset));
        }
        return responseArray;
    }

    /**
     * Returns all signals info
     *
     * @return
     */
    @RequestMapping("dashBoard/getAllSiganls")
    public ResponseEntity getAllSignals() {
        JSONObject responseJSON = new JSONObject();
        System.out.println("allsiganl");
        String data = this.httpGetMessageMaker(MicroServiceURL.getAllAssetsInfo());
        System.out.println("data is : " + data);
        try {
            responseJSON.put("root", (JSONArray) this.jsonParser.parse(data));

        } catch (ParseException ex) {
            Logger.getLogger(DashboardController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return ResponseEntity.ok(responseJSON);
    }

    /**
     * Returns users subscription list
     *
     * @param token
     * @return
     */
    @RequestMapping("dashBoard/showUsersListOfActiveSignal")
    public ResponseEntity<JSONObject> showListOfActiveSignalWithTarget(@RequestHeader("Authorization") String token) {
        String userID = String.valueOf(userService.getByAuthentication(token).getId());

        JSONObject responseJSON = new JSONObject();
        String data = this.httpGetMessageMaker(MicroServiceURL.usersListOfActiveSignal(userID));
        System.out.println("Data for " + userID + " : " + data);
        try {
            responseJSON.put("root", (JSONArray) this.jsonParser.parse(data));

        } catch (ParseException ex) {
            Logger.getLogger(DashboardController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return ResponseEntity.ok(responseJSON);
    }

    /**
     * Returns user subscription list in a special signals
     *
     * @param token
     * @param marketName
     * @return
     */
    @RequestMapping("dashBoard/showUsersListOfActiveSignal/{marketName}")
    public ResponseEntity<JSONObject> showListOfActiveSignalWithTargetInMarket(
            @RequestHeader("Authorization") String token,
            @PathVariable("marketName") String marketName
    ) {
        String userID = String.valueOf(userService.getByAuthentication(token).getId());

        JSONObject responseJSON = new JSONObject();
        String data = this.httpGetMessageMaker(MicroServiceURL.usersListOfActiveSignalInMarket(userID, marketName));
        System.out.println("Data for " + userID + " : " + data);
        try {
            responseJSON.put("root", (JSONArray) this.jsonParser.parse(data));

        } catch (ParseException ex) {
            Logger.getLogger(DashboardController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return ResponseEntity.ok(responseJSON);
    }

    /**
     * Adds new signal to users subscription list
     *
     * @param token
     * @param assetID
     * @param userSavedName
     * @return
     */
    @RequestMapping("dashBoard/addNewAssetsSubscription/{assetID}/{userSavedName}")
    public ResponseEntity addNewAssetsSubscription(
            @RequestHeader("Authorization") String token, 
            @PathVariable("assetID") String assetID , 
            @PathVariable("userSavedName") String userSavedName
    ) {
        String userID = String.valueOf(userService.getByAuthentication(token).getId());

        System.out.println("user id is : " + userID + " , AssetID" + assetID);
        String data = this.httpGetMessageMaker(MicroServiceURL.addAssetToUserSubscriptionList(userID, assetID , userSavedName));
        System.out.println("Data for " + userID + " : " + data);
        return ResponseEntity.ok(data);
    }

    /**
     * Adds new signal to users subscription list with a target price.
     *
     * @param token
     * @param assetID
     * @param userSavedName
     * @param targetPrice
     * @return
     */
    @RequestMapping("dashBoard/addNewAssetsSubscription/{assetID}/{userSavedName}/{targetPrice}")
    public ResponseEntity addNewAssetsSubscription(
            @RequestHeader("Authorization") String token,
            @PathVariable("assetID") String assetID,
            @PathVariable("userSavedName") String userSavedName,
            @PathVariable("targetPrice") String targetPrice) {
        String userID = String.valueOf(userService.getByAuthentication(token).getId());

        System.out.println("user id is : " + userID + " , AssetID" + assetID + " , TargetPrice " + targetPrice);
        String data = this.httpGetMessageMaker(MicroServiceURL.addAssetToUserSubscriptionList(userID, assetID,userSavedName, targetPrice));
        System.out.println("Data for " + userID + " : " + data);
        return ResponseEntity.ok(data);
    }

    /**
     * Editing assets info by sending its new info to this end poing.
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "dashBoard/editAssetsSubscription", method = RequestMethod.POST)
    public ResponseEntity editAssetsSubscription(@RequestBody Map<String, String> request) {
        System.out.println("here");
        System.out.println(request.toString());
        JSONObject obj = new JSONObject();
        request.keySet().forEach((item) -> {
            obj.put(item, request.get(item));
        });
        String data = this.httpPostMessageMaker(MicroServiceURL.editSignalSubscription(), obj.toJSONString());

        return ResponseEntity.ok(data);
    }

    /**
     * Delete assets subscription
     *
     * @param token
     * @param assetID
     * @return
     */
    @RequestMapping("dashboard/deleteAssetSubscription/{userID}/{assetID}")
    public ResponseEntity deleteAssetSubscription(@RequestHeader("Authorization") String token, @PathVariable("assetID") String assetID) {
        String userID = String.valueOf(userService.getByAuthentication(token).getId());

        String data = this.httpGetMessageMaker(MicroServiceURL.deleteAssetSubscription(userID, assetID));
        return ResponseEntity.ok(data);
    }

    /**
     * This Private method used for sending HTTP Post messaged to an URL of
     * other services and returns the response in string
     *
     * @param inputURL
     * @param payload
     * @return String
     */
    private String httpPostMessageMaker(String inputURL, String payload) {
        String message = "";
        try {
            this.url = new URL(inputURL);
            this.connection = (HttpURLConnection) this.url.openConnection();
            this.connection.setRequestMethod("POST");
            this.connection.setRequestProperty("Content-Type", "application/json");
            this.connection.setRequestProperty("Accept", "application/json");
            this.connection.setDoOutput(true);
            try (OutputStream os = this.connection.getOutputStream()) {
                byte[] input = payload.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = this.connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                this.inputReader = new Scanner(new InputStreamReader(this.connection.getInputStream()));
                while (inputReader.hasNextLine()) {
                    message += this.inputReader.nextLine();
                }
                this.inputReader.close();
            } else {
                message = "Connection Problem in Microservices<br/>";
                message += this.connection.getResponseMessage() + "<br/>";
            }
        } catch (MalformedURLException ex) {
            System.out.println("MalformedURLException : " + ex.getMessage());
            Logger
                    .getLogger(DashboardController.class
                            .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("IOException : " + ex.getMessage());
            Logger
                    .getLogger(DashboardController.class
                            .getName()).log(Level.SEVERE, null, ex);
        }
        return message;
    }

    /**
     * This Private method used for sending HTTP Get messaged to other services
     * URLs and returns the response as a string.
     *
     * @param inputURL
     * @return String
     */
    private String httpGetMessageMaker(String inputURL) {
        String message = "";
        try {
            this.url = new URL(inputURL);
            this.connection = (HttpURLConnection) this.url.openConnection();
            this.connection.setRequestMethod("GET");
            int responseCode = this.connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("here no problem");
                this.inputReader = new Scanner(new InputStreamReader(this.connection.getInputStream()));
                while (inputReader.hasNextLine()) {
                    message += this.inputReader.nextLine();
                }
                this.inputReader.close();
            } else {
                message = "Connection Problem in Microservices<br/>";
                message += this.connection.getResponseMessage() + "<br/>";
            }
        } catch (MalformedURLException ex) {
            System.out.println("MalformedURLException : " + ex.getMessage());
            Logger
                    .getLogger(DashboardController.class
                            .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("IOException : " + ex.getMessage());
            Logger
                    .getLogger(DashboardController.class
                            .getName()).log(Level.SEVERE, null, ex);
        }
        return message;
    }

}

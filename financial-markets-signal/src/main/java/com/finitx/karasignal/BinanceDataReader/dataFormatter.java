/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.finitx.karasignal.BinanceDataReader;

import com.binance.connector.client.utils.websocketcallback.WebSocketMessageCallback;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

/**
 * The class reads data from Binance market and for every rows of data the onMessage
 * function of this class would be called and the data would be formatted
 * @author alireza
 */
@Component
public class dataFormatter implements WebSocketMessageCallback {
    
    public JSONObject jsonMessage;
    public JSONObject messageData;
    private final JSONParser jParser = new JSONParser();
    
    /**
     * Formats the received data from Binance APIs
     * Binance data formats:
     * https://github.com/binance/binance-connector-java
     * @param message
     **/
    @Override
    public void onMessage(String message){
        System.out.println("#" + message);
        try {
            this.jsonMessage = (JSONObject)this.jParser.parse(message);
            this.messageData = (JSONObject)this.jsonMessage.get("data");
            System.out.print(((JSONObject)this.jsonMessage.get("data")).get("s"));
            System.out.println(" : " + ((JSONObject)this.jsonMessage.get("data")).get("p"));
        } catch (ParseException ex) {
            Logger.getLogger(dataFormatter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}

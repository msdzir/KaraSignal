
package com.finitx.karasignal.BinanceDataReader;

import com.binance.connector.client.utils.websocketcallback.WebSocketMessageCallback;
import com.finitx.karasignal.Redis.RedisCacheProvider;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * The class caches data in Redis cache provider
 * @author alireza
 */
@Component
public class redisDataCacher implements WebSocketMessageCallback {
    
    @Autowired
    @Qualifier("dataFormatter")
    private dataFormatter dataformat;
    
    @Autowired
//    @Qualifier("RedisCacheProvider")
    private RedisCacheProvider redisDB;
    
    private Scanner fileInput ;
    
    /**
     * Start connection to the Redis
     * @throws java.io.FileNotFoundException
     **/
    public void startConnection() throws FileNotFoundException{
        this.redisDB.initServer();
    }
    
    /**
     * For the every received message from the Binance APIs this function will be called
     * @param message
     **/
    @Override
    public void onMessage(String message) {
        this.dataformat.onMessage(message);
        String symbolName = String.valueOf(this.dataformat.messageData.get("s"));
        this.redisDB.addToCache(symbolName ,
                Double.parseDouble(String.valueOf(this.dataformat.messageData.get("E"))),
                String.valueOf(this.dataformat.messageData.get("p")));
        this.redisDB.close();
    }
    
}

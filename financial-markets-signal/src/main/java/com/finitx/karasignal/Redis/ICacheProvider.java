/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.finitx.karasignal.Redis;

import java.util.List;

/**
 *
 * @author alireza
 */
public interface ICacheProvider {

    /**
     * Connects to the Redis cache server Config-Info:
     * com.Finitx.karasiganl.constant.RedisConfig
     *
     * @return
     *
     */
    boolean initServer();

    /**
     * Sets the cache time
     *
     * @param cacheTime
     *
     */
    void cacheTimeSetter(double cacheTime);

    /**
     * Adds key-value entity to cache (TTL is Time To Live)
     *
     * @param key
     * @param value
     * @param ttl
     *
     */
    void addKeyValue(String key, String value, int ttl);

    /**
     * This function is used to add data to cache.It pushes value on symbolName
     * (as a sorted set) with the score.Usually score is the event time (means
     * the trade happens).After pushing new value to sorted set, this function
     * automatically removes entities that has bean in cache more than cacheTime
     * property
     *
     * @param symbolName
     * @param score
     * @param value
     *
     */
    void addToCache(String symbolName, double score, String value);

    /**
     * For getting the top element in listName (listName is a sorted set that
     * holds cached data from a specific asset symbol)
     *
     * @param listName
     * @return 
     */
    String getTopFromList(String listName);

    /**
     * Returns all of elements in listName (listName is a sorted set)
     * @param listName
     * @return 
    **/
    List<String> showAllList(String listName);

    /**
     * To close the connection
     **/
    void close();

}

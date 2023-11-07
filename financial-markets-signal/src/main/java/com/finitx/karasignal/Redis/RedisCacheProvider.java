/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.finitx.karasignal.Redis;

import com.finitx.karasignal.constant.RedisConfig;
import java.util.List;
import org.springframework.stereotype.Component;
import redis.clients.jedis.*;

import java.util.Date;

//import redis.clients.jedis.args.SortedSetOption;
/**
 * Functions commands are on the implemented interface 
 * @author alireza
 * The class provides the cache services 
 */

//this is a public class for wsorking wsith redis database
@Component
public class RedisCacheProvider implements ICacheProvider{
    
    private JedisPool jedisPool;
    private Jedis jedis;
    
    public double cacheTime = 1.0;//seconds
    private final Date date = new Date();
    
    public RedisCacheProvider(){
        this.jedisPool = new JedisPool();
        this.jedis = new Jedis();
    }
    
    @Override
    public boolean initServer(){
        System.out.println("Connecting to Redis Database....");
        boolean status = true;
        this.jedisPool = new JedisPool(new JedisPoolConfig(), RedisConfig.hostName, RedisConfig.portNum);
        try{
            this.jedis = this.jedisPool.getResource();
        }catch(Exception e){
            status = false;
            System.out.println("Some problem happens!!!");
        }
        System.out.println("Redis insisialized : \n" + jedisPool.toString());
        return status;
    }
    
    @Override
    public void cacheTimeSetter(double cacheTime){
        this.cacheTime = cacheTime;
    }
    
    /**
     *
     * @param key
     * @param value
     * @param ttl
     */
    @Override
    public void addKeyValue(String key , String value , int ttl){
       this.jedis.set(key, value);
       this.jedis.expire(key, ttl);
    }
    
    @Override
    public void addToCache(String symbolName, double score , String value){
        this.jedis.zadd(symbolName, score, value);
        this.jedis.zrangeByScore(symbolName, 0, score);
        System.out.println(this.date.getTime() - (this.cacheTime));
        this.jedis.zremrangeByScore(symbolName, 0, this.date.getTime() - (this.cacheTime));
    }
    
    //ZREVRANGEBYSCORE <key> +inf -inf LIMIT 0 1
    @Override
    public String getTopFromList(String listName){
        List<String> temp = this.jedis.zrevrangeByScore(listName, "+inf", "-inf", 0, 1);
//        List<String> temp = this.jedis.zrange(listName, 0,0);
        return temp.get(0);
    }
    
    @Override
    public List<String> showAllList(String listName){
        List<String> listItems = this.jedis.zrange(listName, 0, -1);
        return listItems;
    }
    
    @Override
    public void close(){
        this.jedis.close();
        this.jedisPool.close();
    }
    
}

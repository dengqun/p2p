package com.fzm.redis;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSONObject;
import com.fzm.tools.ConfigReader;

/** 
 * @file_name: RedisUtil.java
 * @Description: TODO 使用jedis操作redis数据库
 * @author: Ma Amin
 * @date: 2017-8-31 下午3:01:00 
 * @version 1.0
 */
public class RedisUtil {
	private static Jedis redis;
	/**
	 * 
	* @Title: writeListMapToRedis
	* @Description: TODO 将list<Map<String,String>存放到redis中
	* @param 
	* @return void
	* @author: Ma Amin
	 * @throws FileNotFoundException 
	* @date: 2017-9-19 下午3:41:14
	 */
	public static void writeListMapToRedis(List<Map<String,String>> lists) throws FileNotFoundException{
		getRadis();
		//将list放入redis里面
		int num = 0;
		for(Map<String,String> map : lists){
			redis.hmset("user:"+(num+1), map);
		}
	}
	/**
	 * 
	* @Title: writeEntityToRedis
	* @Description: TODO 将List<E>写入redis中
	* @param @param lists
	* @return void
	* @author: Ma Amin
	* @date: 2017-9-19 下午5:36:01
	 */
	public static <E> void writeEntityToRedis(List<E> lists){
		getRadis();
		for(E e : lists){
			String json = JSONObject.toJSONString(e);
			redis.lpush("yy", json);
		}
	}
	/**
	 * 
	* @Title: getElementByIndex
	* @Description: TODO 去除list列表里面的下表为index的元素
	* @param @param key
	* @param @param index
	* @param @return
	* @return List<String>
	* @author: Ma Amin
	* @date: 2017-9-19 下午5:46:24
	 */
	public static List<String> getElementByIndex(String key,int index){
		getRadis();
		return redis.lrange(key, index-1, index);	
	}
	public static Jedis getRadis(){
		try {
			redis = new Jedis(new ConfigReader().read().get("redisIp").toString(),Integer.parseInt(new ConfigReader().read().get("redisPort").toString()));
			redis.auth(new ConfigReader().read().get("authPassword").toString());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return redis;
	}
	public static void main(String[] args) {
		List<String> result = getElementByIndex("yy", 2);
		System.out.println(result.get(0));
	}
}

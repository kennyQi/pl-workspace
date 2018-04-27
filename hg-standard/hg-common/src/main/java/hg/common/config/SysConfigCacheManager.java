package hg.common.config;

import hg.common.model.system.SysConfig;
import hg.common.redis.JedisKeyConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.alibaba.fastjson.JSON;

public class SysConfigCacheManager {

	private JedisPool jedisPool;
	
	/**
	 * 获取所有系统配置（不可直接修改）
	 * @return
	 */
	public Map<String, SysConfig> getAllSysConfig(){
		
		Jedis jedis = jedisPool.getResource();
		
		if(!jedis.exists(JedisKeyConstants.SYS_CONFIG_CACHE_KEY)){
			return new HashMap<String, SysConfig>();
		}
		
		Map<String, String> jsonMap=jedis.hgetAll(JedisKeyConstants.SYS_CONFIG_CACHE_KEY);
		Map<String, SysConfig> allSysConfig=new HashMap<String, SysConfig>();
		
		for(String key:jsonMap.keySet()){
			SysConfig sc=JSON.parseObject(jsonMap.get(key), SysConfig.class);
			allSysConfig.put(key, sc);
		}
		
		return allSysConfig;
	}
	
	/**
	 * 更新所有系统配置到缓存
	 * @param allSysConfig
	 */
	public void updateAllSysConfig(Map<String, SysConfig> allSysConfig) {
		if (allSysConfig != null) {
			Jedis jedis = jedisPool.getResource();

			HashMap<String, String> jsonMap = new HashMap<String, String>();

			for (String key : allSysConfig.keySet()) {
				jsonMap.put(key, JSON.toJSONString(allSysConfig.get(key)));
			}

			jedis.del(JedisKeyConstants.SYS_CONFIG_CACHE_KEY);
			jedis.hmset(JedisKeyConstants.SYS_CONFIG_CACHE_KEY, jsonMap);
			
			jedis.bgsave();
		}
	}
	
	/**
	 * 存放或更新系统配置
	 * @param sysConfig
	 */
	public void putSysConfig(SysConfig sysConfig) {
		if (sysConfig != null) {
			Jedis jedis = jedisPool.getResource();
			jedis.hset(JedisKeyConstants.SYS_CONFIG_CACHE_KEY, sysConfig.getConfName(), JSON.toJSONString(sysConfig));
			jedis.bgsave();
		}
	}
	
	/**
	 * 根据配置名删除系统配置
	 * @param confName
	 */
	public void removeSysConfigByName(String confName) {
		if (StringUtils.isNotBlank(confName)) {
			Jedis jedis = jedisPool.getResource();
			jedis.hdel(JedisKeyConstants.SYS_CONFIG_CACHE_KEY, confName);
			jedis.bgsave();
		}
	}
	
	/**
	 * 根据配置ID删除系统配置
	 * @param id
	 */
	public void removeSysConfigById(String id) {
		if(id != null){
			Jedis jedis = jedisPool.getResource();
			List<String> jsonList = jedis.hvals(JedisKeyConstants.SYS_CONFIG_CACHE_KEY);
			for (String json : jsonList) {
				SysConfig sysConfig = JSON.parseObject(json, SysConfig.class);
				if (id.equals(sysConfig.getId())) {
					jedis.hdel(JedisKeyConstants.SYS_CONFIG_CACHE_KEY, sysConfig.getConfName());
					jedis.bgsave();
					break;
				}
			}
		}
	}
	
	/**
	 * 根据配置名获取配置信息
	 * @param confName
	 * @return
	 */
	public SysConfig getConfig(String confName) {
		if (StringUtils.isNotBlank(confName)) {
			Jedis jedis = jedisPool.getResource();
			String json = jedis.hget(JedisKeyConstants.SYS_CONFIG_CACHE_KEY, confName);
			if (StringUtils.isNotBlank(json)) {
				return JSON.parseObject(json, SysConfig.class);
			}
		}
		return null;
	}
	
	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

}

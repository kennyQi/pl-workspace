package hg.system.cache;

import hg.system.model.config.KVConfig;

import java.util.List;

import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

@Component
public class KVConfigCacheManager extends RedisCacheManager {

	public final static String KV_CONFIG = "kv_config";

	public void putKV(KVConfig kvConfig) {
		Jedis jedis = getJedis();
		if (kvConfig != null && kvConfig.getDataKey() != null) {
			jedis.hset(KV_CONFIG, kvConfig.getDataKey(), kvConfig.getDataValue());
		}
	}

	public String getKV(String dataKey) {
		if (dataKey == null) return null;
		Jedis jedis = getJedis();
		String v = jedis.hget(KV_CONFIG, dataKey);
		return v;
	}

	public void removeKV(String dataKey) {
		if (dataKey == null) return;
		Jedis jedis = getJedis();
		jedis.hdel(KV_CONFIG, dataKey);
	}

	public void reflushKVConfig(List<KVConfig> kvConfigList) {
		Jedis jedis = getJedis();
		jedis.del(KV_CONFIG);
		for (KVConfig kvConfig : kvConfigList) {
			putKV(kvConfig);
		}
	}
}

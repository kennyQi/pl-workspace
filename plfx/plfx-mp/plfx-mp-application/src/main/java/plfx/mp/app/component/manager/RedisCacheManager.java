package plfx.mp.app.component.manager;

import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisCacheManager {

	@Autowired
	private JedisPool jedisPool;
	
	public Jedis getJedis() {
		return jedisPool.getResource();
	}
	
	public void returnResource(Jedis jedis) {
		if (jedis.isConnected())
			jedisPool.returnResource(jedis);
		else
			jedisPool.returnBrokenResource(jedis);
	}
}

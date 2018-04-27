package hg.demo.web.component.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class FixedPriceSetManager {
	
	@Autowired
	private JedisPool jedisPool;
	
	private static final String FX_PROD_ID = "HGFX_PROD_ID_";
	
	
	/**更新缓存中同一商户商品的*/
	public void updateFixedPriceSet(String prodId, String distributorId, String percent){
		Jedis jedis = jedisPool.getResource();
		
		try {
			// FX_PROD_ID_商品ID为KEY
			jedis.hset(FX_PROD_ID + prodId, distributorId, percent);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			jedis.close();
		}
		
	}
	
}

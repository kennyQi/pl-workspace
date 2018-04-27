package hg.dzpw.app.component.manager;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class DealerCacheManager {
	
	/**
	 * 经销商密钥HASH(经销商KEY:密钥)
	 */
	public final static String DEALER_SECRET_KEY_HASH = "cache_dealer_secret_key_hash";
	/**
	 * 经销商标识HASH(经销商KEY:经销商ID)
	 */
	public final static String DEALER_ID_HASH = "cache_dealer_id_hash";
	/**
	 * 经销商推送地址HASH(经销商KEY:推送地址)
	 */
	public final static String DEALER_PUBLISH_URL_HASH = "cache_dealer_publish_url_hash";

	@Autowired
	private JedisPool jedisPool;
	
	/**
	 * @方法功能说明：清除所有缓存
	 * @修改者名字：zhurz
	 * @修改时间：2014-12-17下午4:18:55
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public void clearAll(){
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.del(DEALER_SECRET_KEY_HASH);
			jedis.del(DEALER_ID_HASH);
			jedis.del(DEALER_PUBLISH_URL_HASH);
			jedisPool.returnResource(jedis);
		} catch (Exception e) {
			e.printStackTrace();
			jedisPool.returnBrokenResource(jedis);
		}
	}
	
	/**
	 * @方法功能说明：为经销商设置密钥缓存
	 * @修改者名字：zhurz
	 * @修改时间：2014-12-17下午4:22:51
	 * @修改内容：
	 * @参数：@param dealerId
	 * @参数：@param dealerKey
	 * @参数：@param secretKey
	 * @return:void
	 * @throws
	 */
	public void setSecretKey(String dealerId, String dealerKey, String secretKey) {
		if (StringUtils.isBlank(dealerId) || StringUtils.isBlank(dealerKey) || StringUtils.isBlank(secretKey))
			return;
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.hset(DEALER_ID_HASH, dealerKey, dealerId);
			jedis.hset(DEALER_SECRET_KEY_HASH, dealerKey, secretKey);
			jedisPool.returnResource(jedis);
		} catch (Exception e) {
			e.printStackTrace();
			jedisPool.returnBrokenResource(jedis);
		}
	}
	
	/**
	 * @方法功能说明：根据经销商KEY获取ID
	 * @修改者名字：zhurz
	 * @修改时间：2014-12-17下午5:49:23
	 * @修改内容：
	 * @参数：@param dealerKey
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public String getDealerId(String dealerKey) {
		if (StringUtils.isBlank(dealerKey))
			return null;
		Jedis jedis = jedisPool.getResource();
		try {
			String id = jedis.hget(DEALER_ID_HASH, dealerKey);
			jedisPool.returnResource(jedis);
			return StringUtils.isBlank(id) ? null : id;
		} catch (Exception e) {
			e.printStackTrace();
			jedisPool.returnBrokenResource(jedis);
		}
		return null;
	}
	
	/**
	 * @方法功能说明：获取经销商密钥，不存在时返回null。
	 * @修改者名字：zhurz
	 * @修改时间：2014-12-17下午4:24:12
	 * @修改内容：
	 * @参数：@param dealerKey
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public String getSecretKey(String dealerKey) {
		if (StringUtils.isBlank(dealerKey))
			return null;
		Jedis jedis = jedisPool.getResource();
		try {
			String key = jedis.hget(DEALER_SECRET_KEY_HASH, dealerKey);
			jedisPool.returnResource(jedis);
			return StringUtils.isBlank(key) ? null : key;
		} catch (Exception e) {
			e.printStackTrace();
			jedisPool.returnBrokenResource(jedis);
		}
		return null;
	}

	/**
	 * @方法功能说明：移除经销商
	 * @修改者名字：zhurz
	 * @修改时间：2014-12-17下午4:22:39
	 * @修改内容：
	 * @参数：@param dealerKey
	 * @return:void
	 * @throws
	 */
	public void removeDealer(String dealerKey) {
		if (StringUtils.isBlank(dealerKey))
			return;
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.hdel(DEALER_SECRET_KEY_HASH, dealerKey);
			jedis.hdel(DEALER_ID_HASH, dealerKey);
			jedis.hdel(DEALER_PUBLISH_URL_HASH, dealerKey);
			jedisPool.returnResource(jedis);
		} catch (Exception e) {
			e.printStackTrace();
			jedisPool.returnBrokenResource(jedis);
		}
	}

	/**
	 * @方法功能说明：设置经销商推送地址
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-29下午4:23:56
	 * @修改内容：
	 * @参数：@param dealerKey
	 * @参数：@param url
	 * @return:void
	 * @throws
	 */
	public void setDealerPushlishUrl(String dealerKey, String url) {
		if (StringUtils.isBlank(dealerKey)||StringUtils.isBlank(url))
			return;
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.hset(DEALER_PUBLISH_URL_HASH, dealerKey, url);
			jedisPool.returnResource(jedis);
		} catch (Exception e) {
			e.printStackTrace();
			jedisPool.returnBrokenResource(jedis);
		}
	}
	
	/**
	 * @方法功能说明：删除经销商推送地址
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-29下午4:23:56
	 * @修改内容：
	 * @参数：@param dealerKey
	 * @参数：@param url
	 * @return:void
	 * @throws
	 */
	public void removeDealerPushlishUrl(String dealerKey) {
		if (StringUtils.isBlank(dealerKey))
			return;
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.hdel(DEALER_PUBLISH_URL_HASH, dealerKey);
			jedisPool.returnResource(jedis);
		} catch (Exception e) {
			e.printStackTrace();
			jedisPool.returnBrokenResource(jedis);
		}
	}

	/**
	 * @方法功能说明：获取所有需要推送给经销商URL的MAP
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-29下午4:25:41
	 * @修改内容：
	 * @参数：@return
	 * @return:Map<String,String>
	 * @throws
	 */
	public Map<String, String> getDealerPushlishUrlMap() {
		Jedis jedis = jedisPool.getResource();
		try {
			Map<String, String> map = jedis.hgetAll(DEALER_PUBLISH_URL_HASH);
			jedisPool.returnResource(jedis);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			jedisPool.returnBrokenResource(jedis);
		}
		return null;
	}
}

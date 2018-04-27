package hg.dzpw.app.component.manager;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @类功能说明：景区缓存
 * @类修改者：
 * @修改日期：2014-12-16下午4:36:32
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-12-16下午4:36:32
 */
@Component
public class ScenicSpotCacheManager {
	
	/**
	 * 景区密钥HASH(景区ID:密钥)
	 */
	public final static String SCENIC_SPOT_SECRET_KEY_HASH = "cache_scenic_spot_secret_key_hash";
	
	/**
	 * 入园设备HASH(设备ID:密钥)
	 */
	public final static String CLIENT_DEVICE_HASH = "cache_client_device_hash";
	
	@Autowired
	private JedisPool jedisPool;
	
	/**
	 * @方法功能说明：清除所有缓存
	 * @修改者名字：zhurz
	 * @修改时间：2014-12-16下午5:59:27
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public void clearAll() {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.del(SCENIC_SPOT_SECRET_KEY_HASH);
			jedis.del(CLIENT_DEVICE_HASH);
			jedisPool.returnResource(jedis);
		} catch (Exception e) {
			e.printStackTrace();
			jedisPool.returnBrokenResource(jedis);
		}
	}
	
	/**
	 * @方法功能说明：刷新景区密钥缓存
	 * @修改者名字：zhurz
	 * @修改时间：2014-12-16下午4:47:49
	 * @修改内容：
	 * @参数：@param scenicSpotId
	 * @参数：@param secretKey
	 * @return:void
	 * @throws
	 */
	public void refreshScenicSpotSecretKey(String scenicSpotId, String secretKey) {
		if (StringUtils.isBlank(scenicSpotId) || StringUtils.isBlank(secretKey))
			return;
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.hset(SCENIC_SPOT_SECRET_KEY_HASH, scenicSpotId, secretKey);
			jedisPool.returnResource(jedis);
		} catch (Exception e) {
			e.printStackTrace();
			jedisPool.returnBrokenResource(jedis);
		}
	}
	
	/**
	 * @方法功能说明：刷新设备对应的景区
	 * @修改者名字：zhurz
	 * @修改时间：2014-12-16下午4:40:40
	 * @修改内容：
	 * @参数：@param scenicSpotId
	 * @参数：@param ids
	 * @return:void
	 * @throws
	 */
	public void refreshClientDevice(String scenicSpotId, String... ids) {
		if (StringUtils.isBlank(scenicSpotId) || ids == null)
			return;
		Jedis jedis = jedisPool.getResource();
		try {
			for (String id : ids)
				jedis.hset(CLIENT_DEVICE_HASH, id, scenicSpotId);
			jedisPool.returnResource(jedis);
		} catch (Exception e) {
			e.printStackTrace();
			jedisPool.returnBrokenResource(jedis);
		}
	}
	
	/**
	 * @方法功能说明：根据设备ID获取景区ID，不存在时返回NULL。
	 * @修改者名字：zhurz
	 * @修改时间：2014-12-16下午5:38:50
	 * @修改内容：
	 * @参数：@param id
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public String getScenicSpotIdByDeviceId(String deviceId) {
		if (StringUtils.isBlank(deviceId))
			return null;
		Jedis jedis = jedisPool.getResource();
		try {
			String id = jedis.hget(CLIENT_DEVICE_HASH, deviceId);
			jedisPool.returnResource(jedis);
			return StringUtils.isBlank(id) ? null : id;
		} catch (Exception e) {
			e.printStackTrace();
			jedisPool.returnBrokenResource(jedis);
		}
		return null;
	}

	/**
	 * @方法功能说明：根据景区ID获取景区密钥，不存在时返回NULL。
	 * @修改者名字：zhurz
	 * @修改时间：2014-12-16下午5:38:50
	 * @修改内容：
	 * @参数：@param id
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public String getSecretKeyByScenicSpotId(String scenicSpotId) {
		if (StringUtils.isBlank(scenicSpotId))
			return null;
		Jedis jedis = jedisPool.getResource();
		try {
			String id = jedis.hget(SCENIC_SPOT_SECRET_KEY_HASH, scenicSpotId);
			jedisPool.returnResource(jedis);
			return StringUtils.isBlank(id) ? null : id;
		} catch (Exception e) {
			e.printStackTrace();
			jedisPool.returnBrokenResource(jedis);
		}
		return null;
	}
	
	/**
	 * @方法功能说明：根据设备ID获取景区密钥，不存在时返回NULL。
	 * @修改者名字：zhurz
	 * @修改时间：2014-12-16下午5:46:28
	 * @修改内容：
	 * @参数：@param deviceId
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public String getSecretKeyByDeviceId(String deviceId) {
		return getSecretKeyByScenicSpotId(getScenicSpotIdByDeviceId(deviceId));
	}
	
}

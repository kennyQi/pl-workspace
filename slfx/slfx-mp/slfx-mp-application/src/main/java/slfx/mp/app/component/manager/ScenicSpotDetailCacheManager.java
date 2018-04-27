package slfx.mp.app.component.manager;

import static slfx.mp.spi.common.MpEnumConstants.CacheKeyEnum.CACHE_KEY_SCENIC_SPOT_DETAIL;

import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

@Component
public class ScenicSpotDetailCacheManager extends RedisCacheManager {

	/**
	 * @方法功能说明：将景区详情放置到缓存
	 * @修改者名字：zhurz
	 * @修改时间：2014-9-16上午11:19:43
	 * @修改内容：
	 * @参数：@param id
	 * @参数：@param content
	 * @return:void
	 * @throws
	 */
	public void putDetail(String id, String content) {
		Jedis jedis = getJedis();
		try {
			jedis.hset(CACHE_KEY_SCENIC_SPOT_DETAIL, id, content);
		} finally {
			returnResource(jedis);
		}
	}
	
	/**
	 * @方法功能说明：从缓存取景区详情
	 * @修改者名字：zhurz
	 * @修改时间：2014-9-16上午11:20:16
	 * @修改内容：
	 * @参数：@param id
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public String getDetail(String id) {
		Jedis jedis = getJedis();
		try {
			String detail = jedis.hget(CACHE_KEY_SCENIC_SPOT_DETAIL, id);
			return detail;
		} finally {
			returnResource(jedis);
		}
	}
	
}

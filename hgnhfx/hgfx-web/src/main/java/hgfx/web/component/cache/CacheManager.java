package hgfx.web.component.cache;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.RedissonClient;
import org.redisson.core.RBucket;

import java.util.concurrent.TimeUnit;

/**
 * 缓存管理
 *
 * @author zhurz
 */
public class CacheManager {

	/**
	 * redis客户端（缓存用）
	 */
	private RedissonClient redissonClient;

	public CacheManager(String singleAddress) {
		Config config = new Config();
		config.useSingleServer().setAddress(singleAddress);
		redissonClient = Redisson.create(config);
	}

	private String getCacheKey(Object req) {
		return "HG-DEMO-WEB:" + DigestUtils.md5Hex(JSON.toJSONString(req));
	}

	public <T> T getCache(Object req, Class<T> target) {
		String key = getCacheKey(req);
		RBucket<T> bucket = redissonClient.getBucket(key);
		return bucket.get();
	}

	public void cache(Object req, Object val) {
		String key = getCacheKey(req);
		RBucket<Object> bucket = redissonClient.getBucket(key);
		bucket.set(val, 3, TimeUnit.MINUTES);
	}
}

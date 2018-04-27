package hsl.app.component.cache;

import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;
import hsl.pojo.exception.ShowMessageException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author zhurz
 */
@Component
public class LineOrderTokenCacheManager {

	/**
	 * H5线路订单token
	 */
	public static final String H5_XL_ORDER_TOKENT = "HSL:H5_XL_ORDER_TOKENT:";

	@Autowired
	private JedisPool jedisPool;

	/**
	 * 当天剩余秒数
	 *
	 * @return
	 */
	private int ceilingTime() {
		Date now = new Date();
		Date nowCeiling = DateUtils.ceiling(now, Calendar.DATE);
		return (int) TimeUnit.MILLISECONDS.toSeconds(nowCeiling.getTime() - now.getTime());
	}

	/**
	 * 生成一个线路订单TOKEN，有效期为当天
	 *
	 * @return
	 */
	public String generateToken() {

		String token = UUIDGenerator.getUUID();

		Jedis jedis = jedisPool.getResource();
		try {
			String key = getKey(token);
			jedis.set(key, "");
			jedis.expire(key, ceilingTime());
			jedisPool.returnResource(jedis);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			HgLogger.getInstance().error(LineOrderTokenCacheManager.class, "zhurz", "生成线路订单TOKEN异常", e);
		}

		return token;
	}

	/**
	 * 线路订单TOKEN是否为有值
	 *
	 * @param token
	 * @return
	 */
	public boolean hasLineOrderTokenValue(String token) {
		if (StringUtils.isBlank(token)) return false;
		Jedis jedis = jedisPool.getResource();
		try {
			String key = getKey(token);
			try {
				if (!jedis.exists(key))
					throw new ShowMessageException("令牌过期");
				return StringUtils.isNotBlank(jedis.get(key));
			} finally {
				jedisPool.returnResource(jedis);
			}
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			HgLogger.getInstance().error(LineOrderTokenCacheManager.class, "zhurz", "判断是否存在TOKEN异常", e);
			if (e instanceof ShowMessageException)
				throw (ShowMessageException) e;
		}
		return false;
	}

	/**
	 * 设置TOKEN信息
	 *
	 * @param token
	 * @param dealerOrderNo
	 */
	public void setLineOrderToken(String token, String dealerOrderNo) {
		if (StringUtils.isBlank(token)) return;
		Jedis jedis = jedisPool.getResource();
		try {
			String key = getKey(token);
			if (jedis.exists(key)) {
				jedis.set(key, dealerOrderNo);
				jedis.expire(key, (int) TimeUnit.DAYS.toSeconds(1));
			}
			jedisPool.returnResource(jedis);
		} catch (Exception e) {
			jedisPool.returnBrokenResource(jedis);
			HgLogger.getInstance().error(LineOrderTokenCacheManager.class, "zhurz", "设置TOKEN信息异常", e);
		}
	}

	/**
	 * 获取缓存KEY
	 *
	 * @param token
	 * @return
	 */
	private String getKey(String token) {
		return H5_XL_ORDER_TOKENT + token;
	}

}

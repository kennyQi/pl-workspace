package hg.common.component;

import hg.common.util.SpringContextUtil;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @类功能说明：REDIS实现的分布式锁（尚未实现同一个JVM内的锁）
 * @类修改者：
 * @修改日期：2015-3-31下午6:02:32
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-3-31下午6:02:32
 */
public class RedisLock {

	private JedisPool jedisPool;
	private String key;
	private final static int DEFAULT_TIME_OUT = 600;
	private final static String PREFIX = "redis_lock_";

	private JedisPool getJedisPool() {
		if (jedisPool == null) {
			ApplicationContext context = SpringContextUtil.getApplicationContext();
			if (context == null)
				throw new RuntimeException("SPRING尚未初始化");
			jedisPool = context.getBean(JedisPool.class);
		}
		return jedisPool;
	}

	public RedisLock(String key) {
		if (key == null || key.trim().length() == 0)
			throw new RuntimeException("未指定锁的KEY值");
		this.key = PREFIX + key;
	}

	/**
	 * @方法功能说明：尝试加锁（默认十分钟）
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-31下午6:02:47
	 * @修改内容：
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public synchronized boolean tryLock() {
		return tryLock(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
	}

	/**
	 * @方法功能说明：尝试加锁
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-31下午6:02:59
	 * @修改内容：
	 * @参数：@param time
	 * @参数：@param unit
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public synchronized boolean tryLock(long time, TimeUnit unit) {
		Jedis jedis = getJedisPool().getResource();
		try {
			Long n = jedis.setnx(key, String.valueOf(System.currentTimeMillis()));
			if (n > 0) {
				jedis.expire(key, Long.valueOf(unit.toSeconds(time)).intValue());
				return true;
			} else {
				String lockTimeStr = jedis.get(key);
				if (StringUtils.isBlank(lockTimeStr)) {
					unlock();
					return tryLock(time, unit);
				} else {
					Long lockTime = Long.valueOf(lockTimeStr);
					if (System.currentTimeMillis() > lockTime + unit.toMillis(time)) {
						unlock();
						return tryLock(time, unit);
					} else {
						return false;
					}
				}
			}
		} catch (Exception e) {
			getJedisPool().returnBrokenResource(jedis);
		} finally {
			getJedisPool().returnResource(jedis);
		}
		return false;
	}

	/**
	 * @方法功能说明：解锁
	 * @修改者名字：zhurz
	 * @修改时间：2015-3-31下午6:03:17
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public synchronized void unlock() {
		Jedis jedis = getJedisPool().getResource();
		try {
			jedis.del(key);
		} catch (Exception e) {
			getJedisPool().returnBrokenResource(jedis);
		} finally {
			getJedisPool().returnResource(jedis);
		}
	}

}

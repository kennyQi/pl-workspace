package hg.common.component;

import hg.common.util.SpringContextUtil;

import org.springframework.context.ApplicationContext;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @类功能说明：数字ID生成器
 * @类修改者：
 * @修改日期：2014-8-14上午10:30:09
 * @修改说明：需要依赖redis
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-8-14上午10:30:09
 */
public class NumberIdGenerator {
	
	public final static String ID_PREFIX = "NUM_ID_";
	
	private JedisPool jedisPool;
	
	/**
	 * 工程ID
	 */
	private String projectId;
	
	/**
	 * 环境名称
	 */
	private String envName;

	protected String getKey(Class<?> entityClass) {
		return ID_PREFIX + projectId + "_" + envName + "_" + entityClass.getName();
	}
	
	protected String getKey(String generatorName) {
		return ID_PREFIX + projectId + "_" + envName + "_" + generatorName;
	}

	/**
	 * @方法功能说明：获取Int类型自增长ID
	 * @修改者名字：zhurz
	 * @修改时间：2014-8-14上午11:11:29
	 * @修改内容：
	 * @参数：@param entityClass
	 * @参数：@return
	 * @return:Integer
	 * @throws
	 */
	public Integer getIntId(Class<?> entityClass) {
		return getLongId(entityClass).intValue();
	}
	
	/**
	 * @方法功能说明：获取long类型自增长ID
	 * @修改者名字：zhurz
	 * @修改时间：2014-8-14上午11:12:07
	 * @修改内容：
	 * @参数：@param entityClass
	 * @参数：@return
	 * @return:Long
	 * @throws
	 */
	public Long getLongId(Class<?> entityClass) {
		Jedis jedis = jedisPool.getResource();
		Long id = jedis.incr(getKey(entityClass));
		jedisPool.returnResource(jedis);
		return id;
	}
	
	/**
	 * @方法功能说明：设置当前实体的自增长ID
	 * @修改者名字：zhurz
	 * @修改时间：2014-8-14上午11:12:44
	 * @修改内容：
	 * @参数：@param entityClass
	 * @参数：@param id
	 * @return:void
	 * @throws
	 */
	public void setCurrentId(Class<?> entityClass, Long id){
		Jedis jedis = jedisPool.getResource();
		jedis.set(getKey(entityClass), id.toString());
		jedisPool.returnResource(jedis);
	}
	
	/**
	 * @方法功能说明：根据名称设置自增长ID
	 * @修改者名字：zhurz
	 * @修改时间：2014-8-14下午1:39:59
	 * @修改内容：
	 * @参数：@param generatorName
	 * @参数：@param id
	 * @return:void
	 * @throws
	 */
	public void setCurrentId(String generatorName, Long id){
		Jedis jedis = jedisPool.getResource();
		jedis.set(getKey(generatorName), id.toString());
		jedisPool.returnResource(jedis);
	}

	/**
	 * @方法功能说明：根据名称获取Int类型自增长ID
	 * @修改者名字：zhurz
	 * @修改时间：2014-8-14上午11:11:29
	 * @修改内容：
	 * @参数：@param generatorName
	 * @参数：@return
	 * @return:Integer
	 * @throws
	 */
	public Integer getGeneratorIntId(String generatorName) {
		return getGeneratorLongId(generatorName).intValue();
	}
	
	/**
	 * @方法功能说明：根据名称获取long类型自增长ID
	 * @修改者名字：zhurz
	 * @修改时间：2014-8-14上午11:12:07
	 * @修改内容：
	 * @参数：@param generatorName
	 * @参数：@return
	 * @return:Long
	 * @throws
	 */
	public Long getGeneratorLongId(String generatorName) {
		Jedis jedis = jedisPool.getResource();
		Long id = jedis.incr(getKey(generatorName));
		jedisPool.returnResource(jedis);
		return id;
	}
	
	private static NumberIdGenerator numberIdGenerator;
	
	public static NumberIdGenerator getInstrance() {
		if (numberIdGenerator != null) {
			return numberIdGenerator;
		}
		ApplicationContext ctx = SpringContextUtil.getApplicationContext();
		if (ctx == null || ctx.getBean(NumberIdGenerator.class) == null) {
			throw new RuntimeException("SPRING BEAN 尚未初始化，数字ID生成器需要设置工程ID和环境名称！");
		}
		numberIdGenerator = ctx.getBean(NumberIdGenerator.class);
		return numberIdGenerator;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public void setEnvName(String envName) {
		this.envName = envName;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}
	
}

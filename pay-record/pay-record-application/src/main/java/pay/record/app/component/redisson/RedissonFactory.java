package pay.record.app.component.redisson;

import org.redisson.ClusterServersConfig;
import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.SingleServerConfig;

/**
 * @类功能说明：Redisson工厂类
 * @类修改者：
 * @修改日期：2015-7-15下午5:32:09
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-15下午5:32:09
 */
public class RedissonFactory {

	private RedissonConfig redissonConfig;

	private static Redisson redisson;

	private void init() {
		Config config = new Config();
		if (redissonConfig.getAddressList() == null || redissonConfig.getAddressList().size() == 0) {
			throw new RuntimeException("redis地址未配置");
		} else if (redissonConfig.getAddressList().size() == 1) {
			SingleServerConfig singleServerConfig = config.useSingleServer();
			singleServerConfig.setTimeout(redissonConfig.getTimeout());
			singleServerConfig.setDatabase(redissonConfig.getDatabase());
			singleServerConfig.setAddress(redissonConfig.getAddressList().get(0));
		} else {
			ClusterServersConfig clusterServersConfig = config.useClusterServers();
			clusterServersConfig.setTimeout(redissonConfig.getTimeout());
			clusterServersConfig.setDatabase(redissonConfig.getDatabase());
			clusterServersConfig.setMasterConnectionPoolSize(redissonConfig.getConnectionPoolSize());
			clusterServersConfig.setSlaveConnectionPoolSize(redissonConfig.getConnectionPoolSize());
			String[] addresses = new String[redissonConfig.getAddressList().size()];
			redissonConfig.getAddressList().toArray(addresses);
			clusterServersConfig.addNodeAddress(addresses);
		}
		redisson = Redisson.create(config);
	}

	public Redisson getRedisson() {
		synchronized (this) {
			if (redisson == null)
				init();
		}
		return redisson;
	}

	public RedissonConfig getRedissonConfig() {
		return redissonConfig;
	}

	public void setRedissonConfig(RedissonConfig redissonConfig) {
		this.redissonConfig = redissonConfig;
	}

}

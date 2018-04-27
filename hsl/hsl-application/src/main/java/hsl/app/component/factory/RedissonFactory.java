package hsl.app.component.factory;

import hsl.app.component.config.SysProperties;
import org.redisson.*;
import org.springframework.beans.factory.FactoryBean;

import java.util.Collections;

/**
 * Redisson工厂类
 *
 * @author zhurz
 * @since 1.7
 */
//@Component
public class RedissonFactory implements FactoryBean<RedissonClient> {

	private RedissonConfig redissonConfig;

	private static RedissonClient redissonClient;

	private void loadConfig() {
		redissonConfig = new RedissonConfig();
		String redis1_ip = SysProperties.getInstance().get("redis1_ip");
		String redis1_port = SysProperties.getInstance().get("redis1_port", "6379");
		redissonConfig.setAddressList(Collections.singletonList(redis1_ip + ":" + redis1_port));
	}

	private void init() {
		Config config = new Config();
		if (redissonConfig.getAddressList() == null || redissonConfig.getAddressList().size() == 0) {
			throw new RuntimeException("redis地址未配置");
		} else if (redissonConfig.getAddressList().size() == 1) {
			SingleServerConfig singleServerConfig = config.useSingleServer();
			singleServerConfig.setTimeout(redissonConfig.getTimeout());
			singleServerConfig.setDatabase(redissonConfig.getDatabase());
			singleServerConfig.setAddress(redissonConfig.getAddressList().get(0));
			singleServerConfig.setConnectionPoolSize(redissonConfig.getConnectionPoolSize());
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
		redissonClient = Redisson.create(config);
	}

	@Override
	public RedissonClient getObject() throws Exception {
		synchronized (this) {
			if (redissonClient == null) {
				loadConfig();
				init();
			}
		}
		return redissonClient;
	}

	@Override
	public Class<?> getObjectType() {
		return RedissonClient.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}

package hsl.app.component.factory;

import java.util.List;

/**
 * Redisson配置
 *
 * @author zhurz
 * @since 1.7
 */
public class RedissonConfig {

	/**
	 * redis服务地址 (ip:port)
	 */
	private List<String> addressList;

	/**
	 * 连接池大小
	 */
	private int connectionPoolSize = 100;

	/**
	 * 超时时间
	 */
	private int timeout = 60000;

	/**
	 * 数据库索引
	 */
	private int database = 0;

	public List<String> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<String> addressList) {
		this.addressList = addressList;
	}

	public int getConnectionPoolSize() {
		return connectionPoolSize;
	}

	public void setConnectionPoolSize(int connectionPoolSize) {
		this.connectionPoolSize = connectionPoolSize;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getDatabase() {
		return database;
	}

	public void setDatabase(int database) {
		this.database = database;
	}

}

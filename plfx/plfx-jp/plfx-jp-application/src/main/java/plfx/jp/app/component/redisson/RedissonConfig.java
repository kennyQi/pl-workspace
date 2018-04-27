package plfx.jp.app.component.redisson;

import java.util.List;

/**
 * @类功能说明：Redisson配置
 * @类修改者：
 * @修改日期：2015-7-14下午3:13:33
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-14下午3:13:33
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

package hg.framework.common.base.log;

/**
 * 汇购日志配置
 *
 * @author zhurz
 */
public class HgLogConfig {

	/**
	 * 工程名称
	 */
	private String projectName;

	/**
	 * 工程版本
	 */
	private String projectVersion;

	/**
	 * MongoDB服务地址，多个地址用半角逗号分隔（ip:port[,ip:port]）
	 */
	private String address;

	/**
	 * 记录日志的Mongo库名
	 */
	private String dbName;

	/**
	 * 记录日志的Mongo表名
	 */
	private String collectionName;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectVersion() {
		return projectVersion;
	}

	public void setProjectVersion(String projectVersion) {
		this.projectVersion = projectVersion;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}
}

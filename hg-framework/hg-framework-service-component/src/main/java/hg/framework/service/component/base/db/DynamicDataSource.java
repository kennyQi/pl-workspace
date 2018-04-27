package hg.framework.service.component.base.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源
 *
 * @author zhurz
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	private static ThreadLocal<String> dataSourceKey = new ThreadLocal<>();

	@Override
	protected Object determineCurrentLookupKey() {
		return getDataSourceKey();
	}

	public static String getDataSourceKey() {
		return dataSourceKey.get();
	}

	/**
	 * 切换数据源（注意：切换数据源在数据库会话开启前操作）
	 *
	 * @param dataSourceKey 数据源的KEY
	 */
	public static void changeDataSource(String dataSourceKey) {
		DynamicDataSource.dataSourceKey.set(dataSourceKey);
	}

	/**
	 * 使用默认数据源
	 */
	public static void useDefaultDataSource() {
		DynamicDataSource.dataSourceKey.remove();
	}
}

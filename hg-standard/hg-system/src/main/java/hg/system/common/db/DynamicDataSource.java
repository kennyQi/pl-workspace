package hg.system.common.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @类功能说明：动态数据源
	配置说明
	
	<!-- 数据源配置 -->
	<bean id="parentDataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
		省略...
	</bean>
	<!-- 主数据源 -->
	<bean id="mainDataSource" parent="parentDataSource">
		<property name="url" value="${jdbc_url}" />
		<property name="username" value="${db_username}" />
		<property name="password" value="${db_password}" />
	</bean>
	<!-- 其他数据源 -->
	<bean id="otherDataSource" parent="parentDataSource">
		<property name="url" value="${jdbc_other_url}" />
		<property name="username" value="${db_other_username}" />
		<property name="password" value="${db_other_password}" />
	</bean>
	<bean id="dataSource" class="hg.system.common.db.DynamicDataSource">
		<property name="targetDataSources">
			<map key-type="java.lang.String">
				<entry key="other" value-ref="otherDataSource" />
			</map>
		</property>
		<property name="defaultTargetDataSource" ref="mainDataSource" />
	</bean>
	
	使用其他数据源之前调用DynamicDataSource.changeDataSource("other");
	
 * @类修改者：
 * @修改日期：2015-4-15下午3:08:40
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-4-15下午3:08:40
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	private static ThreadLocal<String> dataSourceKey = new ThreadLocal<String>();

	@Override
	protected Object determineCurrentLookupKey() {
		return getDataSourceKey();
	}

	public static String getDataSourceKey() {
		return dataSourceKey.get();
	}

	/**
	 * @方法功能说明：切换数据源
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-15下午3:39:57
	 * @修改内容：
	 * @参数：@param contextHolder			数据源的KEY
	 * @return:void
	 * @throws
	 */
	public static void changeDataSource(String dataSourceKey) {
		DynamicDataSource.dataSourceKey.set(dataSourceKey);
	}

	/**
	 * @方法功能说明：使用默认数据源
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-15下午3:45:01
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public static void useDefaultDataSource() {
		DynamicDataSource.dataSourceKey.remove();
	}
}

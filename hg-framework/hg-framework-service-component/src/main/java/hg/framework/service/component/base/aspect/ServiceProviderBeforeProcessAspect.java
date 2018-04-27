package hg.framework.service.component.base.aspect;

import hg.framework.common.base.BaseSPIRequest;
import hg.framework.service.component.base.db.DynamicDataSource;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.aspectj.lang.JoinPoint;

import java.util.List;
import java.util.Map;

/**
 * 服务提供者数据源切换处理
 */
public class ServiceProviderBeforeProcessAspect {

	/**
	 * 平台标识与数据源KEY的MAP
	 */
	private Map<String, List<String>> dataSourceMap;

	public Map<String, List<String>> getDataSourceMap() {
		return dataSourceMap;
	}

	public void setDataSourceMap(Map<String, List<String>> dataSourceMap) {
		this.dataSourceMap = dataSourceMap;
	}

	public void before(JoinPoint joinPoint) throws Throwable {
		// 切换数据源
		Object[] args = joinPoint.getArgs();
		if (args.length >= 1 && args[0] instanceof BaseSPIRequest) {
			BaseSPIRequest request = (BaseSPIRequest) args[0];
			List<String> dbKeys = dataSourceMap.get(request.getFromPlatform());
			if (dbKeys != null && dbKeys.size() > 0) {
				DynamicDataSource.changeDataSource(dbKeys.get(RandomUtils.nextInt(dbKeys.size())));
			}
		}
	}

}

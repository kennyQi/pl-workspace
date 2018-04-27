package zzpl.app.service.local.util;

import java.util.Map;

public class ServiceContext {

	private Map<String, CommonService> serviceMap;

	public CommonService get(final String serviceName) {
		CommonService service = serviceMap.get(serviceName);
		return service == null ? serviceMap.get("default") : service;
	}

	public void setServiceMap(Map<String, CommonService> serviceMap) {
		this.serviceMap = serviceMap;
	}
}

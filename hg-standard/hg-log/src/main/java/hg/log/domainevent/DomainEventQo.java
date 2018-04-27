package hg.log.domainevent;

import hg.log.base.BaseLogQo;

public class DomainEventQo extends BaseLogQo {

	private String modelName;

	private String methodName;

	private String param;

	private Integer[] modelVersion;

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Integer[] getModelVersion() {
		return modelVersion;
	}

	public void setModelVersion(Integer[] modelVersion) {
		this.modelVersion = modelVersion;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

}

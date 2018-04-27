package slfx.mp.tcclient.tc.dto;


public class Dto {
	private String param;
	private String result;
	private String serviceName;
	private String url;
	private Boolean unlist=false;
	
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Boolean isUnlist() {
		return unlist;
	}
	public void setUnlist(Boolean unlist) {
		this.unlist = unlist;
	}
	
	
}

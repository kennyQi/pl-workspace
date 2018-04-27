package zzpl.pojo.command.log;

import hg.common.component.BaseCommand;


@SuppressWarnings("serial")
public class SystemCommunicationLogHTTPCommand extends BaseCommand{

	/**
	 * 请求发送地址
	 */
	private String url;

	/**
	 * 此系统在经销商系统中key
	 */
	private String dealerKey;

	/**
	 * 密匙
	 */
	private String secretKey;

	/**
	 * 请求体
	 */
	private String requset;

	/**
	 * 请求结果
	 */
	private String response;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDealerKey() {
		return dealerKey;
	}

	public void setDealerKey(String dealerKey) {
		this.dealerKey = dealerKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getRequset() {
		return requset;
	}

	public void setRequset(String requset) {
		this.requset = requset;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

}

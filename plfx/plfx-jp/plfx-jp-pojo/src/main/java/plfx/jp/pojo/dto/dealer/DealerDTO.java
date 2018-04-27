package plfx.jp.pojo.dto.dealer;

import java.util.Date;

import plfx.jp.pojo.dto.BaseJpDTO;

public class DealerDTO extends BaseJpDTO {

	private static final long serialVersionUID = -1469167772805171158L;
	/**
	 * 经销商名字
	 */
	private String name;
	
	/**
	 * 经销商代码
	 */
	private String code;
	
	/**
	 * 经销商状态
	 */
	private String status;
	
	/**
	 * 经销商通知地址
	 */
	private String notifyUrl;
	
	/**
	 * 经销商通知字段名
	 */
	private String notifyValue;
	
	/** 
	 * 密钥
	 */
	private String secretKey;
	
	
	
	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	private Date createDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNotifyValue() {
		return notifyValue;
	}

	public void setNotifyValue(String notifyValue) {
		this.notifyValue = notifyValue;
	}
}

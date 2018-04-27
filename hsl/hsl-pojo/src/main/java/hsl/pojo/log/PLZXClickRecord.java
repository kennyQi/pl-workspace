package hsl.pojo.log;

import java.util.Date;

import hg.log.po.base.BaseLog;

public class PLZXClickRecord extends BaseLog{
	

	public final static String PLZX_CLICK_RECORD_HOTEL = "1";
	public final static String PLZX_CLICK_RECORD_LINE = "2";
	/**
	 * 点击链接
	 */
	private String url;
	
	/**
	 * 点击类别(在下面定义常量)
	 */
	private Integer type;
	
	/**
	 * 实体ID
	 */
	private String objectId;
	
	/**
	 * 实体类型
	 */
	private String objectType;
	
	/**
	 * 点击用户id（匿名点击无id）
	 */
	private String userId;

	/**
	 * 点击来源
	 */
	private String fromIP;
	
	
	/** 以下两个字段查询酒店用户点击记录的时候用到**/         
	
	/**
	 * 到店日期
	 */
	private Date arrivalDate;
	
	/**
	 * 离店日期
	 */
	private Date departureDate;
	
	
	
	

	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getFromIP() {
		return fromIP;
	}

	public void setFromIP(String fromIP) {
		this.fromIP = fromIP;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
}

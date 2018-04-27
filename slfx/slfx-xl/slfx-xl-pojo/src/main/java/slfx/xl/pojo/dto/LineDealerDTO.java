package slfx.xl.pojo.dto;

import java.util.Date;


public class LineDealerDTO extends BaseXlDTO{
private static final long serialVersionUID = -1469167772805171158L;
	
	private String name;
	
	private String code;
	
	/** 经销商商城访问地址：http://www.aa.com */
	private String dealerUrl;
	
	private String status;
	
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

	public String getDealerUrl() {
		return dealerUrl;
	}

	public void setDealerUrl(String dealerUrl) {
		this.dealerUrl = dealerUrl;
	}
	
}

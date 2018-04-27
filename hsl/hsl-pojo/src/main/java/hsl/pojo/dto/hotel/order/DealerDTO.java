package hsl.pojo.dto.hotel.order;

import java.util.Date;

public class DealerDTO extends BaseJDDTO{
	private static final long serialVersionUID = 1L;

	/**  经销商名称*/
	private String name;

	/**  经销商代码*/
	private String code;
	
	/** 经销商商城访问地址：http://www.aa.com */
	private String dealerUrl;
	
	/**启用状态*/
	private String status;
	
	/** 创建日期*/
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
	
}

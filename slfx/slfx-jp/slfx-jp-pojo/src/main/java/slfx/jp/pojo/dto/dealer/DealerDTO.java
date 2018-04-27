package slfx.jp.pojo.dto.dealer;

import java.util.Date;

import slfx.jp.pojo.dto.BaseJpDTO;

public class DealerDTO extends BaseJpDTO {

	private static final long serialVersionUID = -1469167772805171158L;
	
	private String name;
	
	private String code;
	
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
	
}

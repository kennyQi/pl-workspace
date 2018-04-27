package slfx.jp.pojo.dto.supplier;

import java.util.Date;

import slfx.jp.pojo.dto.BaseJpDTO;

public class SupplierDTO extends BaseJpDTO {
	
	private static final long serialVersionUID = -7152346889038413691L;
	private String code;
	private String name;
	private String number;
	private String status;
	private Date createDate;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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

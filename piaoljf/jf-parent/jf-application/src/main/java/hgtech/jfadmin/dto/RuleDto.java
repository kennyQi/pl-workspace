/**
 * @RuleDto.java Create on 2015年1月29日下午2:06:45
 * Copyright (c) 2012 by www.hg365.com。
 */
package hgtech.jfadmin.dto;

import hg.common.model.qo.DwzPaginQo;

import java.util.Date;

public class RuleDto extends DwzPaginQo {
	private String code;
	private String name;
	private Date startDate;
	private Date endDate;
	private String logicClass;
	private String  accountType;
	private String status;
	private String isLimit;
	
	public String getIsLimit() {
		return isLimit;
	}

	public void setIsLimit(String isLimit) {
		this.isLimit = isLimit;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getLogicClass() {
		return logicClass;
	}

	public void setLogicClass(String logicClass) {
		this.logicClass = logicClass;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
}
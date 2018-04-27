/**
 * @jfAdjustDto.java Create on 2015年1月29日下午2:02:05
 * Copyright (c) 2012 by www.hg365.com。
 */
package hgtech.jfadmin.dto;

public class jfAdjustDto {

	private String account;

	private String accountType;

	private String score;

	private String description;

	private String batchNo;

	private String description2;
	private String jfValidYear;
	public String getJfValidYear() {
		return jfValidYear;
	}

	public void setJfValidYear(String jfValidYear) {
		this.jfValidYear = jfValidYear;
	}

	public String getDescription2() {
		return description2;
	}

	public void setDescription2(String description2) {
		this.description2 = description2;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
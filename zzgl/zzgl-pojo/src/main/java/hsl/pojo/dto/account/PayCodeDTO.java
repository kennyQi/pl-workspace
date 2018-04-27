package hsl.pojo.dto.account;


import hsl.pojo.dto.BaseDTO;

import java.util.Date;



@SuppressWarnings("serial")
public class PayCodeDTO extends BaseDTO{
	/**
	 * 所属的方法纪录
	 */
	private GrantCodeRecordDTO grantCodeRecord;
	/**
	 * 充值码
	 */
	private String code;
	/**
	 * 价格
	 */
	private Double price;
	/**
	 * 来源
	 */
	private String source;
	/**
	 * 创建日期
	 */
	private Date createDate;
	/**
	 * 持用用户快照
	 */
	private HoldUserSnapshotDTO holdUserSnapshot;
	/**
	 * 充值日期
	 */
	private Date rechargeDate;
	
	public GrantCodeRecordDTO getGrantCodeRecord() {
		return grantCodeRecord;
	}
	public void setGrantCodeRecord(GrantCodeRecordDTO grantCodeRecord) {
		this.grantCodeRecord = grantCodeRecord;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public HoldUserSnapshotDTO getHoldUserSnapshot() {
		return holdUserSnapshot;
	}
	public void setHoldUserSnapshot(HoldUserSnapshotDTO holdUserSnapshot) {
		this.holdUserSnapshot = holdUserSnapshot;
	}
	public Date getRechargeDate() {
		return rechargeDate;
	}
	public void setRechargeDate(Date rechargeDate) {
		this.rechargeDate = rechargeDate;
	}
	
}

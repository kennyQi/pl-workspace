package plfx.api.client.api.v1.jd.request.command;

import java.util.Date;

import plfx.api.client.base.slfx.ApiPayload;

@SuppressWarnings("serial")
public class JDValidateCreditCardApiCommand extends ApiPayload{
	/**
	 * 信用卡卡号
	 */
	private String creditCardNo;
	/**
	 * 持卡者姓名
	 */
	private String cardHolderName;
	
	/**
	 * 有效期
	 */
	private Date validDate;
	
	/**
	 * 证件类型(身份证)
	 */
	private int idType;
	
	/**
	 * 身份证号
	 */
	private String idNo;
	
	/**
	 * 效验码
	 */
	private String efficacyCode;

	public String getCreditCardNo() {
		return creditCardNo;
	}

	public void setCreditCardNo(String creditCardNo) {
		this.creditCardNo = creditCardNo;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public Date getValidDate() {
		return validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	public int getIdType() {
		return idType;
	}

	public void setIdType(int idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getEfficacyCode() {
		return efficacyCode;
	}

	public void setEfficacyCode(String efficacyCode) {
		this.efficacyCode = efficacyCode;
	}
	
	
}

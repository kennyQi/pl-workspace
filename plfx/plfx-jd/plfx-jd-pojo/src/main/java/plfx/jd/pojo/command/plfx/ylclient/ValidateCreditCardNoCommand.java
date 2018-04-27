package plfx.jd.pojo.command.plfx.ylclient;

import java.io.Serializable;
import java.util.Date;


/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江票量云数据科技股份有限公司
 * @部门：技术部
 * @作者：renfeng
 * @创建时间：2015年7月14日下午5:16:07
 * @版本：V1.1
 *
 */
@SuppressWarnings("serial")
public class ValidateCreditCardNoCommand implements Serializable{
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

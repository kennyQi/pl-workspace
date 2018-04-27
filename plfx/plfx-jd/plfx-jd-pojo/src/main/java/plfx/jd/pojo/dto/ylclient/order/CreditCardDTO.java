
package plfx.jd.pojo.dto.ylclient.order;

import java.io.Serializable;

import plfx.jd.pojo.system.enumConstants.EnumIdType;

/**
 * 
 * @类功能说明：信用卡
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年3月26日上午10:33:10
 * @版本：V1.0
 *
 */

public class CreditCardDTO implements Serializable{
	/**
	 * 卡号
	 */
    protected String number;
    /**
     * cvv
     */
    protected String cvv;
    /**
     * 有效期-年
     */
    protected int expirationYear;
    /**
     * 有效期-月
     */
    protected int expirationMonth;
    /**
     * 持卡人
     */
    protected String holderName;
    /**
     * 证件类型
     */
    protected EnumIdType idType;
    /**
     * 证件号码
     */
    protected String idNo;
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getCvv() {
		return cvv;
	}
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	public int getExpirationYear() {
		return expirationYear;
	}
	public void setExpirationYear(int expirationYear) {
		this.expirationYear = expirationYear;
	}
	public int getExpirationMonth() {
		return expirationMonth;
	}
	public void setExpirationMonth(int expirationMonth) {
		this.expirationMonth = expirationMonth;
	}
	public String getHolderName() {
		return holderName;
	}
	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}
	public EnumIdType getIdType() {
		return idType;
	}
	public void setIdType(EnumIdType idType) {
		this.idType = idType;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
    
  
}

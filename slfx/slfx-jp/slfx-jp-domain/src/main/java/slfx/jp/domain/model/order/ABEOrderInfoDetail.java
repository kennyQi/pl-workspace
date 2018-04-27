package slfx.jp.domain.model.order;

import java.io.Serializable;
import java.util.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import slfx.jp.domain.model.J;

/**
 * 
 * @类功能说明：ABE订单信息明细 model
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:25:55
 * @版本：V1.0
 *
 */
@Embeddable
public class ABEOrderInfoDetail implements Serializable{
	private static final long serialVersionUID = 6575608012230988664L;

   /** 联系人 */
	@Column(name = "LINKER", length = 64)
   private String linker;
   
   /** 送票地址 */
   @Column(name = "TICKET_ADDRESS", length = 64)
   private String address;
   
   /** 联系电话 */
   @Column(name = "TEL", length = 64)
   private String tel;
   
   /** 国内(D)   国际(I) 
    *  设代码
    * */
   @Column(name = "DOMC", length = 8)
   private String domc;
   
   /** 出票时限 */
   @Column(name = "TICKET_LIMIT_DATE", columnDefinition = J.DATE_COLUM)
   private Date ticketLimitDate;
   
   /** 支付平台 */
   @Column(name = "PAY_PLAT_FORM", length = 64)
   private String payPlatform;
   
   /** 银行代码 */
   @Column(name = "BANK_CODE", length = 64)
   private String bankCode;
   
   /** 备注 */
   @Column(name = "REMARK", length = 4000)
   private String remark;
   
   /** 结算价格 */
   @Column(name = "BALANCE_MONEY", columnDefinition = J.MONEY_COLUM)
   private Double balanceMoney;

	public String getLinker() {
		return linker;
	}
	
	public void setLinker(String linker) {
		this.linker = linker;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getTel() {
		return tel;
	}
	
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	public String getDomc() {
		return domc;
	}
	
	public void setDomc(String domc) {
		this.domc = domc;
	}
	
	public Date getTicketLimitDate() {
		return ticketLimitDate;
	}
	
	public void setTicketLimitDate(Date ticketLimitDate) {
		this.ticketLimitDate = ticketLimitDate;
	}
	
	public String getPayPlatform() {
		return payPlatform;
	}
	
	public void setPayPlatform(String payPlatform) {
		this.payPlatform = payPlatform;
	}
	
	public String getBankCode() {
		return bankCode;
	}
	
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Double getBalanceMoney() {
		return balanceMoney;
	}
	
	public void setBalanceMoney(Double balanceMoney) {
		this.balanceMoney = balanceMoney;
	}

}
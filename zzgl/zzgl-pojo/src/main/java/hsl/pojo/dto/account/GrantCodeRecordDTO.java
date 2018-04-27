package hsl.pojo.dto.account;
import hsl.pojo.dto.BaseDTO;

import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class GrantCodeRecordDTO extends BaseDTO{
	/**
	 * 所属的商业合作伙伴
	 */
	private BusinessPartnersDTO businessPartners;
	
	/**
	 * 对接人
	 */
	private String dockingPerson;
	
	/**
	 * 对接人电话
	 */
	private String dockingPersonTel;
	/**
	 * 方法的充值码
	 */
    private List<PayCodeDTO> payCodes;
	/**
	 * 发放人姓名
	 */
	private String granterName;
	/**
	 * 发放人电话
	 */
	private String granterTel;
	/**
	 * 充值码金额
	 */
	private Double payCodeMoney;
	/**
	 * 充值码数量
	 */
	private Integer payCodeNum;
	/**
	 * 创建日期
	 */
	private Date createTime;
	/**
	 * 状态
	 */
	private Integer status;
	
	public BusinessPartnersDTO getBusinessPartners() {
		return businessPartners;
	}
	public void setBusinessPartners(BusinessPartnersDTO businessPartners) {
		this.businessPartners = businessPartners;
	}
	public List<PayCodeDTO> getPayCodes() {
		return payCodes;
	}
	public void setPayCodes(List<PayCodeDTO> payCodes) {
		this.payCodes = payCodes;
	}
	public String getGranterName() {
		return granterName;
	}
	public void setGranterName(String granterName) {
		this.granterName = granterName;
	}
	public String getGranterTel() {
		return granterTel;
	}
	public void setGranterTel(String granterTel) {
		this.granterTel = granterTel;
	}
	public Double getPayCodeMoney() {
		return payCodeMoney;
	}
	public void setPayCodeMoney(Double payCodeMoney) {
		this.payCodeMoney = payCodeMoney;
	}
	public Integer getPayCodeNum() {
		return payCodeNum;
	}
	public void setPayCodeNum(Integer payCodeNum) {
		this.payCodeNum = payCodeNum;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getDockingPerson() {
		return dockingPerson;
	}
	public void setDockingPerson(String dockingPerson) {
		this.dockingPerson = dockingPerson;
	}
	public String getDockingPersonTel() {
		return dockingPersonTel;
	}
	public void setDockingPersonTel(String dockingPersonTel) {
		this.dockingPersonTel = dockingPersonTel;
	}
	
	
}

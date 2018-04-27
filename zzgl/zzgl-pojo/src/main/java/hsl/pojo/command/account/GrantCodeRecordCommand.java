package hsl.pojo.command.account;

import hg.common.component.BaseCommand;

import java.util.Date;
/**
 * 
 * @类功能说明：发放码记录
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhaows
 * @创建时间：2015-9-1上午9:29:04
 * @类修改者：
 * @修改日期：
 * @修改说明：
 */
@SuppressWarnings("serial")
public class GrantCodeRecordCommand extends BaseCommand{
	private String id;
	/**
	 * 所属的商业合作伙伴
	 */
	private BusinessPartnersCommand businessPartnersCommand;
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
    private PayCodeCommand payCodes;
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
	public BusinessPartnersCommand getBusinessPartnersCommand() {
		return businessPartnersCommand;
	}
	public void setBusinessPartnersCommand(
			BusinessPartnersCommand businessPartnersCommand) {
		this.businessPartnersCommand = businessPartnersCommand;
	}
	public PayCodeCommand getPayCodes() {
		return payCodes;
	}
	public void setPayCodes(PayCodeCommand payCodes) {
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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

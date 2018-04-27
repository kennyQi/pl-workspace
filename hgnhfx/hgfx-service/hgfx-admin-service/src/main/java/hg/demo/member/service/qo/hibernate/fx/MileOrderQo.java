package hg.demo.member.service.qo.hibernate.fx;

import hg.framework.common.base.BaseSPIQO;
import hg.framework.service.component.base.hibernate.BaseHibernateQO;

@SuppressWarnings("serial")
public class MileOrderQo extends BaseHibernateQO<String> {
	private Boolean queryOrderCodeLike = true;
	
	private String distributorId;

	/**
	 * 订单号
	 */
	private String orderCode;

	/**
	 * 南航卡号
	 */
	private String csairCard;

	/**
	 * 南航姓名
	 */
	private String csairName;


	/**
	 * 支付时间
	 */
	private String payDateStart;
	private String payDateEnd;

	/**
	 * 导入时间\订单建立时间
	 */
	private String importDateStart;
	private String importDateEnd;

	/**
	 * 状态
	 */
	private Integer status;

	private int sendStatus;

	public String getDistributorId() {
		return distributorId;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public String getCsairCard() {
		return csairCard;
	}

	public String getCsairName() {
		return csairName;
	}

	public String getPayDateStart() {
		return payDateStart;
	}

	public String getPayDateEnd() {
		return payDateEnd;
	}

	public String getImportDateStart() {
		return importDateStart;
	}

	public String getImportDateEnd() {
		return importDateEnd;
	}

	public Integer getStatus() {
		return status;
	}

	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public void setCsairCard(String csairCard) {
		this.csairCard = csairCard;
	}

	public void setCsairName(String csairName) {
		this.csairName = csairName;
	}

	public void setPayDateStart(String payDateStart) {
		this.payDateStart = payDateStart;
	}

	public void setPayDateEnd(String payDateEnd) {
		this.payDateEnd = payDateEnd;
	}

	public void setImportDateStart(String importDateStart) {
		this.importDateStart = importDateStart;
	}

	public void setImportDateEnd(String importDateEnd) {
		this.importDateEnd = importDateEnd;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Boolean getQueryOrderCodeLike() {
		return queryOrderCodeLike;
	}

	public void setQueryOrderCodeLike(Boolean queryOrderCodeLike) {
		this.queryOrderCodeLike = queryOrderCodeLike;
	}

	public void setSendStatus(int sendStatus) {
		this.sendStatus = sendStatus;
	}

	public Integer getSendStatus() {
		return sendStatus;
	}

}

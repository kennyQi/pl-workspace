package hg.pojo.command.mileOrder;

import java.util.Date;

import hg.pojo.command.JxcCommand;

@SuppressWarnings("serial")
public class CreateMileOrderCommand extends JxcCommand{
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
	 * 数量
	 */
	private Integer num;

	/**
	 * 支付时间
	 */
	private Date payDate;

	public String getOrderCode() {
		return orderCode;
	}

	public String getCsairCard() {
		return csairCard;
	}

	public String getCsairName() {
		return csairName;
	}

	public Integer getNum() {
		return num;
	}

	public Date getPayDate() {
		return payDate;
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

	public void setNum(Integer num) {
		this.num = num;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public String getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(String distributorId) {
		this.distributorId = distributorId;
	}


	
}

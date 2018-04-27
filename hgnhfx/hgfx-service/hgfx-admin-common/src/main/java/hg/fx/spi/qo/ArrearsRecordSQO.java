package hg.fx.spi.qo;

import hg.framework.common.base.BaseSPIQO;

/**
 * @author cangs
 */
@SuppressWarnings("serial")
public class ArrearsRecordSQO extends BaseSPIQO {

	/**
	 * 先按照审核状态降序排序
	 * 再按照申请时间排序
	 */
	public static String ORDERWAY_1="0001";
	/**
	 * 可欠费里程变更记录ID
	 */
	private String arrearsRecordID;

	/**
	 * 商户ID
	 */
	private String distributorID;
	/**
	 * 排序方式(自定义)
	 */
	private String orderWay;

	
	public String getOrderWay() {
		return orderWay;
	}

	public void setOrderWay(String orderWay) {
		this.orderWay = orderWay;
	}

	public String getArrearsRecordID() {
		return arrearsRecordID;
	}

	public void setArrearsRecordID(String arrearsRecordID) {
		this.arrearsRecordID = arrearsRecordID;
	}

	public String getDistributorID() {
		return distributorID;
	}

	public void setDistributorID(String distributorID) {
		this.distributorID = distributorID;
	}

}

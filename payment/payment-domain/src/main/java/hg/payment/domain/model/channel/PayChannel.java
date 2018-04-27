package hg.payment.domain.model.channel;

import hg.common.component.BaseModel;

/**
 * 
 * @类功能说明：支付渠道
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年8月26日下午7:44:39
 *
 */
@SuppressWarnings("serial")
public class PayChannel extends BaseModel implements Comparable<PayChannel>{

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 全名
	 */
	private String fullName;
	
	/**
	 * 渠道ID
	 */
	private Integer type;
	
	/**
	 * 支付宝
	 */
	public static Integer PAY_TYPE_ALIPAY = 1;

	/**
	 * 快钱
	 */
	public static Integer PAY_TYPE_KQ = 2;
	
	/**
	 * 汇金宝
	 */
	public static Integer PAY_TYPE_HJB = 3;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Override
	public int compareTo(PayChannel payChannel) {
		return type.compareTo(payChannel.getType());
	}

}

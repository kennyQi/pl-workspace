package slfx.jp.qo.admin;

import hg.common.component.BaseQo;

/**
 * 
 * @类功能说明：平台退废订单查询QO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：qiuxianxiang
 * @创建时间：2014年7月31日下午5:13:28
 * @版本：V1.0
 *
 */
public class PlatformOrderBackQO extends BaseQo {

	private static final long serialVersionUID = -7739716214755560465L;
	
	/** 机票普通订单查询QO */
	private PlatformOrderQO platformOrderQO;
	
	/** slfx订单号  */
	private String orderId;
	
	/** 易购退票订单号  */
	private String ygRefundOrderNo;
	
	/** 退废票平台订单号  */
	private String refundPlatformOrderNo;

	/** 
	 * 退废类型：详情参考 JPOrderStatus.CATEGORY_REFUND 
	 */
	private Integer backCategory;


	public PlatformOrderQO getPlatformOrderQO() {
		return platformOrderQO;
	}


	public void setPlatformOrderQO(PlatformOrderQO platformOrderQO) {
		this.platformOrderQO = platformOrderQO;
	}


	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public String getYgRefundOrderNo() {
		return ygRefundOrderNo;
	}


	public void setYgRefundOrderNo(String ygRefundOrderNo) {
		this.ygRefundOrderNo = ygRefundOrderNo;
	}


	public String getRefundPlatformOrderNo() {
		return refundPlatformOrderNo;
	}


	public void setRefundPlatformOrderNo(String refundPlatformOrderNo) {
		this.refundPlatformOrderNo = refundPlatformOrderNo;
	}


	public Integer getBackCategory() {
		return backCategory;
	}


	public void setBackCategory(Integer backCategory) {
		this.backCategory = backCategory;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

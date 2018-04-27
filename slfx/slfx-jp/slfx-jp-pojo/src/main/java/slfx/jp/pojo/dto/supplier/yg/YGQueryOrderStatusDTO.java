package slfx.jp.pojo.dto.supplier.yg;

import slfx.jp.pojo.dto.BaseJpDTO;

/**
 * 
 * @类功能说明：易购预订单状态DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午5:00:30
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class YGQueryOrderStatusDTO extends BaseJpDTO{

		
	/**
	 * 预订单状态
	 */
	private String			status = null;
	
	/**
	 * 备注
	 */
	private String			remark = null;
	
	/**
	 * 出票平台（例如：BT）
	 */
	private String 			platCode = null;
	
	/**
	 * 平台订单号
	 */
	private String 			platOrderNo = null;
	
	/**
	 * 易购订单号
	 */
	private String 			orderNo = null;
	
	/**
	 * 订座记录
	 */
	private String 			pnr = null;


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPlatCode() {
		return platCode;
	}

	public void setPlatCode(String platCode) {
		this.platCode = platCode;
	}

	public String getPlatOrderNo() {
		return platOrderNo;
	}

	public void setPlatOrderNo(String platOrderNo) {
		this.platOrderNo = platOrderNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}
	
}

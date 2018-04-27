package slfx.jp.pojo.dto.supplier.yg;

import slfx.jp.pojo.dto.BaseJpDTO;

/**
 * 
 * @类功能说明：YG退废票接口DTO 
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:58:39
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class YGApplyRefundDTO extends BaseJpDTO{
	
	/**
	 * 接口错误代码，0表示调用成功
	private String errorCode;
	 */
	
	/**
	 * 错误描述信息
	private String errorMsg;
	 */
	
	/**
	 * 退票订单号
	 */
	private String refundOrderNo;
	
	private String refundPlatformOrderNo; 
/*
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}*/

	public String getRefundOrderNo() {
		return refundOrderNo;
	}

	public void setRefundOrderNo(String refundOrderNo) {
		this.refundOrderNo = refundOrderNo;
	}

	public String getRefundPlatformOrderNo() {
		return refundPlatformOrderNo;
	}

	public void setRefundPlatformOrderNo(String refundPlatformOrderNo) {
		this.refundPlatformOrderNo = refundPlatformOrderNo;
	}
}

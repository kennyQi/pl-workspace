package slfx.jp.domain.model.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import slfx.jp.domain.model.J;
import hg.common.component.BaseModel;

/**
 * 
 * @类功能说明：出票实体
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月30日下午3:32:55
 * @版本：V1.0
 *
 */

@SuppressWarnings("serial")
@Entity
@Table(name = J.TABLE_PREFIX + "ASK_ORDER_TICKET")
public class AskOrderTicket extends BaseModel{
	
	/** 接口错误代码 */
	@Column(name = "ERROR_CODE", columnDefinition = J.NUM_COLUM)
	private Integer errorCode;
	   
	/** 错误描述信息 */
	@Column(name = "ERROR_MSG", length = 255)
	private String errorMsg;
	  
	/** 出票平台 */
	@Column(name = "PLAT_CODE", length = 8)
	private String platCode;
	   
	/** 平台订单号 */
	@Column(name = "PLAT_ORDER_NO", length = 48)
	private String platOrderNo;
	   
	/** 易购订单号 */
	@Column(name = "ORDER_NO", length = 48)
	private String orderNo;
	   
	/** 支付链接 */
	@Column(name = "PAYMENT_URL", length = 255)
	private String paymentUrl;

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
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

	public String getPaymentUrl() {
		return paymentUrl;
	}

	public void setPaymentUrl(String paymentUrl) {
		this.paymentUrl = paymentUrl;
	}

}
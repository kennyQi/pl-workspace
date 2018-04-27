package lxs.domain.model.line;

import hg.common.component.BaseModel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lxs.domain.model.M;

@Entity
@Table(name = M.TABLE_PREFIX_XL + "ALIPAY_ORDER")
public class LineAlipayOrder extends BaseModel {
	/**
	 * 阿里订单号
	 */
	@Column(name = "ALIPAY_ORDER_NO",length = 512)
	private String alipayOrderNO;
	
	/**
	 * 本地订单号
	 */
	@Column(name = "LOCAL_ORDER_NO",length = 512)
	private String localOrderNO;
	
	/**
	 * 创建日期
	 */
	@Column(name = "CREATE_DATE",columnDefinition = M.DATE_COLUM)
	private Date createDate;
	
	/**
	 * 支付状态
	 */
	@Column(name = "PAYER_ACCOUNT",length = 512)
	private String payerAccount;
	
	/**
	 * 支付状态
	 */
	@Column(name = "STATUS",length = 512)
	private String status;
}

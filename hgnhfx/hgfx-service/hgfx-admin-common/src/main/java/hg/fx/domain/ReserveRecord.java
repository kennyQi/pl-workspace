package hg.fx.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import hg.demo.member.common.domain.model.AuthUser;
import hg.demo.member.common.domain.model.def.O;
import hg.framework.common.base.BaseStringIdModel;

/**
 * @类功能说明：里程余额变更记录
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2016-5-31下午8:20:37
 * @版本：V1.0
 *
 */
@Entity
@Table(name = O.FX_TABLE_PREFIX + "RESERVE_RECORD")
public class ReserveRecord extends BaseStringIdModel {

	private static final long serialVersionUID = 1L;
	
	/** 1--交易 */
	public static final Integer RECORD_TYPE_TRADE = 1;
	/** 2--交易退款 */
	public static final Integer RECORD_TYPE_REFUND = 2;
	/** 3--(后台)备付金充值 */
	public static final Integer RECORD_TYPE_RECHARGE = 3;
	/** 4--(在线)备付金充值  0.1版本无该类型*/
	public static final Integer RECORD_TYPE_RECHARGE_ONLINE = 4;
	/** 5--月末返利 */
	public static final Integer RECORD_TYPE_REBATE = 5;
	
	public static final String RECORD_TYPE_RECHARGE_MESSAGE = "积分充值";
	public static final String RECORD_TYPE_REBATE_MESSAGE = "月末返利";
	
	/** 扣款成功 */
	public static final Integer RECORD_STATUS_KOUKUAN_SUCC = 1;
	/** 充值成功 */
	public static final Integer RECORD_STATUS_CHONGZHI_SUCC = 2;
	/** 退款成功 */
	public static final Integer RECORD_STATUS_REFUND_SUCC = 3;
	
	/** -1--已拒绝 */
	public static final int CHECK_STATUS_REFUSE = -1;
	/** 1--待审核   */
	public static final int CHECK_STATUS_WAITTING = 1;
	/** 0--已通过 */
	public static final int CHECK_STATUS_PASS = 0;
	
	
	/**
	 * 关联商户
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DISTRIBUTOR_ID")
	private Distributor distributor;
	
	/**
	 * 变化额 
	 */
	@Column(name = "INCR_EMENT", columnDefinition = O.LONG_NUM_COLUM)
	private Long increment;
	
	/**
	 * 明细类型  
	 * 1--交易  
	 * 2--交易退款 
	 * 3--后台备付金充值  
	 * 4--在线备付金充值  
	 * 5--月末返利
	 */
	@Column(name ="TYPE", columnDefinition = O.TYPE_NUM_COLUM)
	private Integer type;
	
	/**
	 * 交易时间
	 */
	@Column(name = "TRADE_DATE", columnDefinition = O.DATE_COLUM)
	private Date tradeDate;
	
	/**
	 * 商品名称
	 * type=3,4时 商品名字=积分充值
	 * type=5时 商品名字=月末返利
	 * 其他类型名字为product里的商品名称
	 * */
	@Column(name = "PROD_NAME", length = 32)
	private String prodName;
	
	/**
	 * 订单编号  本系统订单编号
	 * type=1,2(交易或交易退)时  此字段才有值
	 */
	@Column(name = "ORDER_CODE", length = 32)
	private String orderCode;
	
	/**
	 * 交易号
	 */
	@Column(name = "TRADE_NO", length = 32)
	private String tradeNo;
	
	/**
	 * 会员卡号
	 * type=1,2(交易或交易退)时  此字段才有值
	 */
	@Column(name = "CARD_NO", length = 64)
	private String cardNo;
	
	/**
	 * 状态
	 * 1--扣款成功   
	 * 2--充值成功  
	 * 3--退款成功
	 */
	@Column(name = "STATUS", columnDefinition = O.TYPE_NUM_COLUM)
	private Integer status;
	
	/**
	 * 截图证明
	 * type=3 此字段有值
	 */
	@Column(name = "PROVE_PATH", length = 1024)
	private String provePath;
	
	/**
	 * 申请时间
	 * type=3 此字段有值
	 */
	@Column(name = "APPLY_DATE", columnDefinition = O.DATE_COLUM)
	private Date applyDate;
	
	/**
	 * 申请人  添加申请的操作人
	 * type=3 此字段有值
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OPERATOR_ID")
	private AuthUser operator;

	/**
	 * 审核状态  
	 * -1--已拒绝  
	 * 1--待审核 
	 * 0--已通过
	 * type=3 此字段有值
	 */
	@Column(name = "CHECK_STATUS", columnDefinition = O.TYPE_NUM_COLUM)
	private Integer checkStatus;

	public Distributor getDistributor() {
		return distributor;
	}

	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
	}

	public Long getIncrement() {
		return increment;
	}

	public void setIncrement(Long increment) {
		this.increment = increment;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getProvePath() {
		return provePath;
	}

	public void setProvePath(String provePath) {
		this.provePath = provePath;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public AuthUser getOperator() {
		return operator;
	}

	public void setOperator(AuthUser operator) {
		this.operator = operator;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getCheckStatusName(){
		switch (checkStatus) {
		case CHECK_STATUS_REFUSE:
			return "已拒绝";
		case CHECK_STATUS_PASS:
			return "已通过";
		case CHECK_STATUS_WAITTING:
			return "待审核";
		default:
			break;
		}
		return "";
	}
	
}

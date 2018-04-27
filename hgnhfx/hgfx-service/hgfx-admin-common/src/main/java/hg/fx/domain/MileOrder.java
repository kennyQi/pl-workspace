package hg.fx.domain;

import hg.demo.member.common.domain.model.AuthUser;
import hg.demo.member.common.domain.model.def.M;
import hg.demo.member.common.domain.model.def.O;
import hg.framework.common.base.BaseStringIdModel;
import hg.framework.common.util.UUIDGenerator;
import hg.fx.command.mileOrder.CreateMileOrderCommand;
import hg.fx.command.mileOrder.ImportMileOrderCommand;
//import hg.fx.domain.util.CodeUtil;

import hg.fx.util.CodeUtil;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 里程订单
 * 
 */
@Entity
@Table(name =  O.FX_TABLE_PREFIX + "ORDER")
public class MileOrder extends BaseStringIdModel {

	private static final long serialVersionUID = 1L;
	/**
	 * -1--订单取消
	 */
	public static final int STATUS_CANCEL = -1;
	/**
	 *  0--已付款（冻结）
	 */
	public static final int STATUS_PAID = 0;
	
	/**
	 * 1--审核中,待审核
	 */
	public static final int STATUS_NO_CHECK = 1;

	/**
	 * 2--确认中(审核通过）
	 */
	public static final int STATUS_CHECK_PASS = 2;

	/**
	 * 3--已拒绝
	 *  审核不通过（拒绝）
	 */
	public static final int STATUS_CHECK_NO_PASS = 3;

	/**4--订单成功
	 *  南航成功
	 */
	public static final int STATUS_CSAIR_SUCCEED = 4;

	/** 5--订单失败
	 *  南航失败
	 */
	public static final int STATUS_CSAIR_ERROR = 5;
	
	/**
	 * 6--确认通过，处理中
	 */
	public static final int STATUS_CONFIRM_PASS = 6;
	
	
	/**
	 *  已提交南航
	 */
	public static final int SEND_STATUS_YES=1;
	/**
	 *  未提交南航
	 */
	public static final int SEND_STATUS_NO=0;
	
	/**
	 * 下单商户,分销商
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MER_ID")
	private Distributor distributor;

	 
	/**
	 * 商户订单号
	 * 由商户生成
	 */
	@Column(name = "ORDER_NO", length = 32)
	private String orderCode;

	/**
	 * 系统订单号，流水号
	 * 由系统生成
	 */
	@Column(name = "FLOW_CODE", length = 32)
	private String flowCode;

	/**
	 * 南航卡号/会员卡号
	 */
	@Column(name = "CARD_NO", length = 64)
	private String csairCard;

	/**
	 * 南航姓名/会员卡所有者姓名
	 */
	@Column(name = "OWNER_NAME", length = 40)
	private String csairName;

	/**
	 * 份数、个数
	 */
	@Column(name = "NUM", columnDefinition = O.NUM_COLUM)
	private Integer num;
	
	/**
	 * 总数量(订单总里程数)
	 */
	@Column(name = "COUNT", columnDefinition = O.LONG_NUM_COLUM)
	private Long  count;

	/**
	 * 单价UNIT_PRICE
	 */
	@Column(name="UNIT_PRICE", columnDefinition = O.NUM_COLUM)
	private Integer unitPrice = 1;
	
	/**
	 * 支付时间
	 */
	@Column(name = "PAY_DATE", columnDefinition = O.DATE_COLUM)
	private Date payDate;

	/**
	 * 订单创建时间/导入时间
	 */
	@Column(name = "CREATE_DATE", columnDefinition = O.DATE_COLUM)
	private Date importDate;

	/**
	 * 导入、创建人帐号
	 * 如果是接口导入，则为空
	 */
	@Column(name="CREATE_USER", length = 128)
	private String importUser;
	
	/**
	 * 状态
	 * -1--订单取消
	 * 0--已付款（冻结）
	 * 1--审核中,待审核
	 * 2--处理中(审核通过）
	 * 3--已拒绝
	 * 4--订单成功 南航成功
	 * 5--订单失败 南航失败
	 * 25--提交南航
	 */
	@Column(name = "STATUS", columnDefinition = O.NUM_COLUM)
	private Integer status;

	/**
	 * 发送到外部系统状态
	 * 1--已提交南航
	 * 0--未提交南航
	 */
	@Column(name="SEND_STATUS",columnDefinition = O.NUM_COLUM)
	private Integer sendStatus = SEND_STATUS_NO;
	
	/**
	 * 订单类型 
	 * 1--异常订单  
	 * 2--正常订单
	 */
	@Column(name="TYPE", columnDefinition = O.TYPE_NUM_COLUM)
	private Integer type;
	
	/**
	 * 审核人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OPERATOR_ID")
	private AuthUser checkPerson;

	/**
	 * 审核通过原因
	 */
	@Column(name="REASON", length = 512)
	private String reason;
	
	/**
	 * 异常订单原因
	 */
	@Column(name="ERROR_REASON", length = 512)
	private String errorReason;
	
	/**
	 * 订单确认拒绝原因
	 */
	@Column(name="REFUSE_REASON", length = 512)
	private String refuseReason;
	
	/**
	*审核时间
	*/
	@Column(name = "CHECK_DATE", columnDefinition = O.DATE_COLUM)
	private Date checkDate;
	
	/**
	*南航处理时间/提交渠道处理时间
	*/
	@Column(name = "SUBMIT_TIME", columnDefinition = O.DATE_COLUM)
	private Date toCsairDate;
	
	/**
	*南航处理结果信息/渠道处理结果信息
	*/
	@Column(name = "RESULT", length = 64)
	private String csairInfo;
	
	/**
	*南航返回处理时间/渠道处理返回时间
	*/
	@Column(name = "RETURN_TIME", columnDefinition = O.DATE_COLUM)
	private Date csairReturnDate;

	/**购买商品	
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PROD_ID")
	private Product product;
	/**
	 * 商品名称
	 */
	@Column(name="PROD_NAME")
	private String productName;
	
	/**
	 * 渠道商名称
	 */
	@Column(name="CHANNEL_NAME")
	private String channelName;
	
	/**
	 * 商户名称
	 */
	@Column(name="MER_Name")
	private String merName;
	/**
	 * 审核人
	 */
	@Column(name="ADUIT_PERSON")
	private String aduitPerson;

	/**
	 * 确认人
	 */
	@Column(name="CONFIRM_PERSON")
	private String confirmPerson;
	/**
	 * 确认时间
	 */
	@Column(name="CONFIRM_DATE",columnDefinition=O.DATE_COLUM)
	private Date confirmDate;
	
	/**
	 * 订单创建/导入  日期字符串
	 * 格式2016-06-15
	 * v0.1暂无用处
	 */
	@Column(name = "CREATE_DATE_STR", length = 16)
	private String importDateStr;

	public void create(CreateMileOrderCommand command, ImportMileOrderCommand importMileOrderCommand, String distributorCode) {

		setId(UUIDGenerator.getUUID());

		status = STATUS_NO_CHECK;

		Distributor d = new Distributor();
		d.setId(importMileOrderCommand.getDistributorId());
		this.setDistributor(d);
		this.setProduct(command.getProduct());

		this.setOrderCode(command.getOrderCode());
		this.setCsairCard(command.getCsairCard());
		this.setCsairName(command.getCsairName());
		this.setNum(command.getNum());
		this.setPayDate(command.getPayDate());
		this.setImportDate(new Date());
		this.setCount(command.getNum().longValue() * command.getUnitPrice().longValue());
		this.importUser = command.getImportUser();
		flowCode = CodeUtil.getMileOrderFlowCode(distributorCode);

	}

 
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getMerName() {
		return merName;
	}

	public void setMerName(String merName) {
		this.merName = merName;
	}

	public String getAduitPerson() {
		return aduitPerson;
	}

	public void setAduitPerson(String aduitPerson) {
		this.aduitPerson = aduitPerson;
	}

	public Distributor getDistributor() {
		return distributor;
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

	public Integer getNum() {
		return num;
	}

	public Date getPayDate() {
		return payDate;
	}

	public Date getImportDate() {
		return importDate;
	}

	public Integer getStatus() {
		return status;
	}
	
	public String getStatusName() {
		
		switch (status) {
		case STATUS_CANCEL:
			return "订单取消";
		case STATUS_PAID:
			return "已付款";
		case STATUS_NO_CHECK:
			return "审核中";
		case STATUS_CHECK_PASS:
			return "确认中";
		case STATUS_CONFIRM_PASS:
			return "处理中";
		case STATUS_CHECK_NO_PASS:
			return "已拒绝";
		case STATUS_CSAIR_SUCCEED:
			return "订单成功";
		case STATUS_CSAIR_ERROR:
			return "订单失败";
		
		default:
			break;
		}
		return "";
	}

	public void setDistributor(Distributor distributor) {
		this.distributor = distributor;
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

	public void setImportDate(Date importDate) {
		this.importDate = importDate;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getFlowCode() {
		return flowCode;
	}

	public void setFlowCode(String flowCode) {
		this.flowCode = flowCode;
	}

	public AuthUser getCheckPerson() {
		return checkPerson;
	}

	public void setCheckPerson(AuthUser checkPerson) {
		this.checkPerson = checkPerson;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public Date getToCsairDate() {
		return toCsairDate;
	}

	public void setToCsairDate(Date toCsairDate) {
		this.toCsairDate = toCsairDate;
	}

	public String getCsairInfo() {
		return csairInfo;
	}

	public void setCsairInfo(String csairInfo) {
		this.csairInfo = csairInfo;
	}

	public Date getCsairReturnDate() {
		return csairReturnDate;
	}

	public void setCsairReturnDate(Date csairReturnDate) {
		this.csairReturnDate = csairReturnDate;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Integer getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Integer unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getErrorReason() {
		return errorReason;
	}

	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "card:"+csairCard +",count:"+count +",status:"+ status;
	}

	public String getImportUser() {
		return importUser;
	}

	public void setImportUser(String importUser) {
		this.importUser = importUser;
	}

	public Integer getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(Integer sendStatus) {
		this.sendStatus = sendStatus;
	}

	public String getImportDateStr() {
		return importDateStr;
	}

	public void setImportDateStr(String importDateStr) {
		this.importDateStr = importDateStr;
	}


	public String getConfirmPerson() {
		return confirmPerson;
	}


	public void setConfirmPerson(String confirmPerson) {
		this.confirmPerson = confirmPerson;
	}


	public Date getConfirmDate() {
		return confirmDate;
	}


	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}


	public String getRefuseReason() {
		return refuseReason;
	}


	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}
	
}

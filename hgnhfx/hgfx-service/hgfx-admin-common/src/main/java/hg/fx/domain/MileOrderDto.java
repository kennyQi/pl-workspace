package hg.fx.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 里程订单 dto
 * 
 */
public class MileOrderDto   implements Serializable {

	private static final long serialVersionUID = 1L;
	 
	/**
	 * 订单号
	 */
	private String orderCode;

	/**
	 * 流水号
	 */
	private String flowCode;

	/**
	 * 南航卡号/会员卡号
	 */
	private String csairCard;

	/**
	 * 南航姓名/会员卡所有者姓名
	 */
	private String csairName;

	/**
	 * 份数、个数
	 */
	private Integer num;
	/**
	 * 总数量
	 */
	private Integer  count;

	/**
	 * 单价UNIT_PRICE
	 */
	private Integer unitPrice = 1;
	
	/**
	 * 支付时间
	 */
	private Date payDate;

	/**
	 * 订单创建时间/导入时间
	 */
	private Date importDate;

	/**
	 * 导入、创建人帐号
	 */
	private String importUser;
	
	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 订单类型.订单类型  1--异常订单  2--正常订单
	 */
	private Integer type;
	
	 
	/**
	 * 审核通过原因
	 */
	private String reason;
	
	/**
	 * 异常订单原因
	 */
	private String errorReason;
	
	/**
	*审核时间
	*/
	private Date checkDate;
	
	/**
	*南航处理时间/提交渠道处理时间
	*/
	private Date toCsairDate;
	
	/**
	*南航处理结果信息/渠道处理结果信息
	*/
	private String csairInfo;
	
	/**
	*南航返回处理时间/渠道处理返回时间
	*/
	private Date csairReturnDate;

	/**购买商品	
	 */
	private Product product;

	 

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
		case MileOrder.STATUS_CANCEL:
			return "订单取消";
		case MileOrder.STATUS_PAID:
			return "已付款(冻结)";
		case MileOrder.STATUS_NO_CHECK:
			return "审核中,待审核";
		case MileOrder.STATUS_CHECK_PASS:
			return "处理中(审核通过)";
		case MileOrder.STATUS_CHECK_NO_PASS:
			return "审核不通过(拒绝)";
		case MileOrder.STATUS_CSAIR_SUCCEED:
			return "订单成功";
		case MileOrder.STATUS_CSAIR_ERROR:
			return "订单失败";
		
		default:
			break;
		}
		return "";
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

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
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

	public String getProductName() {
		return product.getProdName();
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
}

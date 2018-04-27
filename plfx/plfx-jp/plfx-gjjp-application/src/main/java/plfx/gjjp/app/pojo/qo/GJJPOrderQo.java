package plfx.gjjp.app.pojo.qo;

import hg.common.annotation.QOAttr;
import hg.common.annotation.QOAttrType;
import hg.common.annotation.QOConfig;
import hg.common.component.BaseQo;

import java.util.Date;
import java.util.List;

import plfx.gjjp.domain.common.GJJPConstants;
import plfx.jp.qo.admin.dealer.DealerQO;
import plfx.jp.qo.admin.supplier.SupplierQO;

@SuppressWarnings("serial")
@QOConfig(daoBeanId = "gjjpOrderDao")
public class GJJPOrderQo extends BaseQo {

	/**
	 * 乘客姓名
	 */
	private String passengerName;

	/**
	 * 机票状态
	 */
	private Integer ticketStatus;

	/**
	 * 初始化所有延迟加载对象
	 */
	private Boolean initAll;

	// 航段信息

	/**
	 * 航程信息
	 * 
	 * 1.单程2.来回程
	 */
	@QOAttr(name = "segmentInfo.lineType")
	private Integer lineType;

	/**
	 * 始发地三字码
	 */
	@QOAttr(name = "segmentInfo.orgCity")
	private String orgCity;

	/**
	 * 目的地三字码
	 */
	@QOAttr(name = "segmentInfo.dstCity")
	private String dstCity;

	// 订单基础信息

	/**
	 * 经销商订单号
	 */
	@QOAttr(name = "baseInfo.dealerOrderId", type = QOAttrType.IN)
	private List<String> dealerOrderIds;

	/**
	 * 来源经销商
	 */
	@QOAttr(name = "baseInfo.fromDealer", type = QOAttrType.LEFT_JOIN)
	private DealerQO fromDealerQo;

	/**
	 * 供应商订单号
	 */
	@QOAttr(name = "baseInfo.supplierOrderId")
	private String supplierOrderId;

	/**
	 * 来源供应商
	 */
	@QOAttr(name = "baseInfo.fromSupplier", type = QOAttrType.LEFT_JOIN)
	private SupplierQO fromSupplierQo;

	/**
	 * Pnr
	 */
	@QOAttr(name = "baseInfo.pnr")
	private String pnr;

	/**
	 * 新pnr(换编码出票后的pnr)
	 */
	@QOAttr(name = "baseInfo.newPnr")
	private String newPnr;

	/**
	 * 经销商在分销平台下单时间
	 */
	@QOAttr(name = "baseInfo.createDate", type = QOAttrType.GE)
	private Date createDateBegin;
	@QOAttr(name = "baseInfo.createDate", type = QOAttrType.LE)
	private Date createDateEnd;

	/**
	 * 创建时间排序
	 */
	@QOAttr(name = "baseInfo.createDate", type = QOAttrType.ORDER)
	private Integer createDateSort;

	/**
	 * 备注
	 */
	@QOAttr(name = "baseInfo.remark", type = QOAttrType.LIKE_ANYWHERE)
	private String remark;

	/**
	 * 是否为异常订单
	 */
	@QOAttr(name = "exceptionInfo.exceptionOrder")
	private Boolean exceptionOrder;

	// 联系人信息

	/**
	 * 联系人姓名
	 */
	@QOAttr(name = "contacterInfo.contactName", ifTrueUseLike = "contactNameLike")
	private String contactName;
	private boolean contactNameLike;

	/**
	 * 联系人电话
	 */
	@QOAttr(name = "contacterInfo.contactMobile", ifTrueUseLike = "contactMobileLike")
	private String contactMobile;
	private boolean contactMobileLike;

	// 支付信息

	/**
	 * 平台订单总支付价格
	 */
	@QOAttr(name = "payInfo.totalPrice", type = QOAttrType.GE)
	private Double totalPriceMin;
	@QOAttr(name = "payInfo.totalPrice", type = QOAttrType.LE)
	private Double totalPriceMax;

	/**
	 * 平台支付状态
	 * 
	 * @see GJJPConstants#ORDER_PAY_STATUS_MAP
	 */
	@QOAttr(name = "payInfo.status")
	private Integer payStatus;

	// 订单状态

	/**
	 * 供应商订单状态
	 * 
	 * @see GJJPConstants#SUPPLIER_ORDER_STATUS_MAP
	 */
	@QOAttr(name = "status.supplierCurrentValue")
	private Integer supplierOrderStatus;

	/**
	 * 平台订单状态
	 * 
	 * @see GJJPConstants#ORDER_STATUS_MAP
	 */
	@QOAttr(name = "status.currentValue")
	private Integer orderStatus;

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public Integer getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(Integer ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public Boolean getInitAll() {
		return initAll;
	}

	public void setInitAll(Boolean initAll) {
		this.initAll = initAll;
	}

	public Integer getLineType() {
		return lineType;
	}

	public void setLineType(Integer lineType) {
		this.lineType = lineType;
	}

	public String getOrgCity() {
		return orgCity;
	}

	public void setOrgCity(String orgCity) {
		this.orgCity = orgCity;
	}

	public String getDstCity() {
		return dstCity;
	}

	public void setDstCity(String dstCity) {
		this.dstCity = dstCity;
	}

	public List<String> getDealerOrderIds() {
		return dealerOrderIds;
	}

	public void setDealerOrderIds(List<String> dealerOrderIds) {
		this.dealerOrderIds = dealerOrderIds;
	}

	public DealerQO getFromDealerQo() {
		return fromDealerQo;
	}

	public void setFromDealerQo(DealerQO fromDealerQo) {
		this.fromDealerQo = fromDealerQo;
	}

	public String getSupplierOrderId() {
		return supplierOrderId;
	}

	public void setSupplierOrderId(String supplierOrderId) {
		this.supplierOrderId = supplierOrderId;
	}

	public SupplierQO getFromSupplierQo() {
		return fromSupplierQo;
	}

	public void setFromSupplierQo(SupplierQO fromSupplierQo) {
		this.fromSupplierQo = fromSupplierQo;
	}

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public String getNewPnr() {
		return newPnr;
	}

	public void setNewPnr(String newPnr) {
		this.newPnr = newPnr;
	}

	public Date getCreateDateBegin() {
		return createDateBegin;
	}

	public void setCreateDateBegin(Date createDateBegin) {
		this.createDateBegin = createDateBegin;
	}

	public Date getCreateDateEnd() {
		return createDateEnd;
	}

	public void setCreateDateEnd(Date createDateEnd) {
		this.createDateEnd = createDateEnd;
	}

	public Integer getCreateDateSort() {
		return createDateSort;
	}

	public void setCreateDateSort(Integer createDateSort) {
		this.createDateSort = createDateSort;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getExceptionOrder() {
		return exceptionOrder;
	}

	public void setExceptionOrder(Boolean exceptionOrder) {
		this.exceptionOrder = exceptionOrder;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public boolean isContactNameLike() {
		return contactNameLike;
	}

	public void setContactNameLike(boolean contactNameLike) {
		this.contactNameLike = contactNameLike;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public boolean isContactMobileLike() {
		return contactMobileLike;
	}

	public void setContactMobileLike(boolean contactMobileLike) {
		this.contactMobileLike = contactMobileLike;
	}

	public Double getTotalPriceMin() {
		return totalPriceMin;
	}

	public void setTotalPriceMin(Double totalPriceMin) {
		this.totalPriceMin = totalPriceMin;
	}

	public Double getTotalPriceMax() {
		return totalPriceMax;
	}

	public void setTotalPriceMax(Double totalPriceMax) {
		this.totalPriceMax = totalPriceMax;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getSupplierOrderStatus() {
		return supplierOrderStatus;
	}

	public void setSupplierOrderStatus(Integer supplierOrderStatus) {
		this.supplierOrderStatus = supplierOrderStatus;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

}

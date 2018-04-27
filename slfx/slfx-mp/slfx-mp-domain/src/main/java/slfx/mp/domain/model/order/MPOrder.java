package slfx.mp.domain.model.order;

import static slfx.mp.spi.common.MpEnumConstants.OrderStatusEnum.ORDER_STATUS_CANCEL;
import static slfx.mp.spi.common.MpEnumConstants.OrderStatusEnum.ORDER_STATUS_OUTOFDATE;
import static slfx.mp.spi.common.MpEnumConstants.OrderStatusEnum.ORDER_STATUS_PREPARED;
import static slfx.mp.spi.common.MpEnumConstants.OrderStatusEnum.ORDER_STATUS_USED;
import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.beanutils.BeanUtils;

import slfx.api.v1.request.command.mp.MPOrderCancelCommand;
import slfx.api.v1.request.command.mp.MPOrderCreateCommand;
import slfx.mp.command.ModifyMPOrderStatusCommand;
import slfx.mp.command.admin.AdminCancelOrderCommand;
import slfx.mp.domain.model.M;
import slfx.mp.domain.model.platformpolicy.SalePolicySnapshot;
import slfx.mp.domain.model.supplierpolicy.TCSupplierPolicySnapshot;
import slfx.mp.spi.common.MpEnumConstants.OrderCancelReason;

/**
 * 景区门票平台订单
 * 
 * @author Administrator
 */
@Entity
@Table(name = M.TABLE_PREFIX + "MP_ORDER")
public class MPOrder extends BaseModel {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 经销商渠道
	 */
	@Column(name = "DEALER_ID", length = 64)
	private String dealerId;
	/**
	 * 平台订单号
	 */
	@Column(name = "PLATFORM_ORDER_CODE", length = 64)
	private String platformOrderCode;
	/**
	 * 供应商订单号
	 */
	@Column(name = "SUPPLIER_ORDER_CODE", length = 64)
	private String supplierOrderCode;
	/**
	 * 经销商订单号
	 */
	@Column(name = "DEALER_ORDER_CODE", length = 64)
	private String dealerOrderCode;
	/**
	 * 下单时间
	 */
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;
	/**
	 * 市场价
	 */
	@Column(name = "MARKET_PRICE", columnDefinition = M.MONEY_COLUM)
	private Double marketPrice;
	/**
	 * 实付价
	 */
	@Column(name = "PRICE", columnDefinition = M.MONEY_COLUM)
	private Double price;
	/**
	 * 供应商
	 */
	@Column(name = "SUPPLIER_ID", length = 64)
	private String supplierId;
	/**
	 * 所购政策名称
	 */
	@Column(name = "POLICY_NAME", length = 128)
	private String policyName;
	/**
	 * 订购数量
	 */
	@Column(name = "NUMBER_", columnDefinition = M.NUM_COLUM)
	private Integer number;
	/**
	 * 下单人信息
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_USER_ID")
	public OrderUserInfo orderUserInfo;
	/**
	 * 经销商价格政策快照
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SALE_POLICY_SNAPSHOT_ID")
	public SalePolicySnapshot salePolicySnapshot;
	/**
	 * 同城(供应商)价格政策快照
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SUPPLIER_POLICY_SNAPSHOT_ID")
	public TCSupplierPolicySnapshot supplierPolicySnapshot;

	/**
	 * 门票订单状态
	 */
	@Embedded
	public MPOrderStatus status;

	/**
	 * 取消原因
	 */
	@Column(name = "CANCEL_REMARK", length = 256)
	public String cancelRemark;

	/**
	 * 订单提交到供应商接口完成
	 * 
	 * @param serialId
	 */
	public void submitOver(String serialId) {
		setSupplierOrderCode(serialId);
		getStatus().setPrepared(true);
	}

	/**
	 * 取消订单
	 * 
	 * @param command
	 */
	public void apiCancelOrder(MPOrderCancelCommand command) {
		getStatus().setCancel(true);
		getStatus().setPrepared(false);
		if (command.getCancelReason() != null) {
			String cancelRemark = OrderCancelReason.cancelReasonMap.get(String
					.valueOf(command.getCancelReason()));
			setCancelRemark(cancelRemark);
		}
	}

	/**
	 * 管理员取消订单
	 * 
	 * @param command
	 */
	public void adminCancelOrder(AdminCancelOrderCommand command) {
		if (!command.isCancel())
			return;
		getStatus().setCancel(true);
		getStatus().setPrepared(false);
		if (command.getCancelRemark() != null) {
			String cancelRemark = OrderCancelReason.cancelReasonMap.get(command.getCancelRemark());
			setCancelRemark(cancelRemark);
		}
	}
	
	/**
	 * 订票
	 * 
	 * @param command	
	 * @param salePolicySnapshot			平台价格政策
	 * @param supplierPolicySnapshot		供应商价格政策
	 * @param platformOrderCode				平台订单号
	 * @param supplierId					供应商ID
	 */
	public void apiOrderTicket(MPOrderCreateCommand command, SalePolicySnapshot salePolicySnapshot,
			TCSupplierPolicySnapshot supplierPolicySnapshot, String platformOrderCode, String supplierId) {
//		setId(UUIDGenerator.getUUID());
		// 使用平台订单号作为ID 20140929
		setId(platformOrderCode);
		setSupplierId(supplierId);
		setPlatformOrderCode(platformOrderCode);
		setCreateDate(new Date());
		setDealerId(command.getFromClientKey());
		setDealerOrderCode(command.getDealerOrderId());
		setMarketPrice(supplierPolicySnapshot.getRetailPrice());
		setPrice(command.getPrice());
		setPolicyName(supplierPolicySnapshot.getName());
		setSalePolicySnapshot(salePolicySnapshot);
		setSupplierPolicySnapshot(supplierPolicySnapshot);
		setNumber(command.getNumber());
		getStatus().setPrepared(true);
		OrderUserInfo orderUserInfo = new OrderUserInfo();
		try {
			BeanUtils.copyProperties(orderUserInfo, command.getOrderUserInfo());
		} catch (Exception e) {
			e.printStackTrace();
		}
		orderUserInfo.setId(UUIDGenerator.getUUID());
		setOrderUserInfo(orderUserInfo);
	}
	
	public void modifyMPOrderStatus(ModifyMPOrderStatusCommand command){
		if (command.getCancelAble() != null) {
			getStatus().setCancelAble(command.getCancelAble());
		}
		if (command.getOrderStatus() != null) {
			getStatus().setCancel(Boolean.FALSE);
			getStatus().setOutOfDate(Boolean.FALSE);
			getStatus().setPrepared(Boolean.FALSE);
			getStatus().setUsed(Boolean.FALSE);
			if (ORDER_STATUS_PREPARED.intValue() == command.getOrderStatus()
					.intValue()) {
				getStatus().setPrepared(Boolean.TRUE);
			} else if (ORDER_STATUS_CANCEL.intValue() == command
					.getOrderStatus().intValue()) {
				getStatus().setCancel(Boolean.TRUE);
			} else if (ORDER_STATUS_USED.intValue() == command.getOrderStatus()
					.intValue()) {
				getStatus().setUsed(Boolean.TRUE);
			} else if (ORDER_STATUS_OUTOFDATE.intValue() == command
					.getOrderStatus().intValue()) {
				getStatus().setOutOfDate(Boolean.TRUE);
			}
		}
	}
	
	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public String getPlatformOrderCode() {
		return platformOrderCode;
	}

	public void setPlatformOrderCode(String platformOrderCode) {
		this.platformOrderCode = platformOrderCode;
	}

	public String getSupplierOrderCode() {
		return supplierOrderCode;
	}

	public void setSupplierOrderCode(String supplierOrderCode) {
		this.supplierOrderCode = supplierOrderCode;
	}

	public String getDealerOrderCode() {
		return dealerOrderCode;
	}

	public void setDealerOrderCode(String dealerOrderCode) {
		this.dealerOrderCode = dealerOrderCode;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public OrderUserInfo getOrderUserInfo() {
		return super.getProperty(orderUserInfo, OrderUserInfo.class);
	}

	public void setOrderUserInfo(OrderUserInfo orderUserInfo) {
		this.orderUserInfo = orderUserInfo;
	}

	public SalePolicySnapshot getSalePolicySnapshot() {
		return super.getProperty(salePolicySnapshot, SalePolicySnapshot.class);
	}

	public void setSalePolicySnapshot(SalePolicySnapshot salePolicySnapshot) {
		this.salePolicySnapshot = salePolicySnapshot;
	}

	public TCSupplierPolicySnapshot getSupplierPolicySnapshot() {
		return super.getProperty(supplierPolicySnapshot,
				TCSupplierPolicySnapshot.class);
	}

	public void setSupplierPolicySnapshot(
			TCSupplierPolicySnapshot supplierPolicySnapshot) {
		this.supplierPolicySnapshot = supplierPolicySnapshot;
	}

	public MPOrderStatus getStatus() {
		if (status == null) {
			status = new MPOrderStatus();
		}
		return status;
	}

	public void setStatus(MPOrderStatus status) {
		this.status = status;
	}

	public String getCancelRemark() {
		return cancelRemark;
	}

	public void setCancelRemark(String cancelRemark) {
		this.cancelRemark = cancelRemark;
	}

}
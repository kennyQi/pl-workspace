package slfx.mp.pojo.dto.order;

import java.util.Date;

import slfx.mp.pojo.dto.BaseMpDTO;
import slfx.mp.pojo.dto.platformpolicy.SalePolicySnapshotDTO;
import slfx.mp.pojo.dto.supplierpolicy.TCSupplierPolicySnapshotDTO;

/**
 * 景区门票平台订单
 * 
 * @author Administrator
 */
public class MPOrderDTO extends BaseMpDTO {
	private static final long serialVersionUID = 1L;
	/**
	 * 经销商（同程订单）
	 */
	private TCOrderDTO tcOrder;
	/**
	 * 经销商渠道
	 */
	private String dealerId;
	/**
	 * 平台订单号
	 */
	private String platformOrderCode;
	/**
	 * 供应商订单号
	 */
	private String supplierOrderCode;
	/**
	 * 经销商订单号
	 */
	private String dealerOrderCode;
	/**
	 * 下单时间
	 */
	private Date createDate;
	/**
	 * 市场价
	 */
	private Double marketPrice;
	/**
	 * 实付价
	 */
	private Double price;
	/**
	 * 供应商
	 */
	private String supplierId;
	/**
	 * 所购政策名称
	 */
	private String policyName;
	/**
	 * 订购数量
	 */
	private Integer number;
	/**
	 * 下单人信息
	 */
	public MPOrderUserInfoDTO orderUserInfo;
	/**
	 * 经销商价格政策快照
	 */
	public SalePolicySnapshotDTO salePolicySnapshot;
	/**
	 * 同城(供应商)价格政策快照
	 */
	public TCSupplierPolicySnapshotDTO supplierPolicySnapshot;

	/**
	 * 门票订单状态
	 */
	public MPOrderStatusDTO status;

	/**
	 * 取消原因
	 */
	public String cancelRemark;

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

	public MPOrderUserInfoDTO getOrderUserInfo() {
		return orderUserInfo;
	}

	public void setOrderUserInfo(MPOrderUserInfoDTO orderUserInfo) {
		this.orderUserInfo = orderUserInfo;
	}

	public SalePolicySnapshotDTO getSalePolicySnapshot() {
		return salePolicySnapshot;
	}

	public void setSalePolicySnapshot(SalePolicySnapshotDTO salePolicySnapshot) {
		this.salePolicySnapshot = salePolicySnapshot;
	}

	public TCSupplierPolicySnapshotDTO getSupplierPolicySnapshot() {
		return supplierPolicySnapshot;
	}

	public void setSupplierPolicySnapshot(
			TCSupplierPolicySnapshotDTO supplierPolicySnapshot) {
		this.supplierPolicySnapshot = supplierPolicySnapshot;
	}

	public MPOrderStatusDTO getStatus() {
		return status;
	}

	public void setStatus(MPOrderStatusDTO status) {
		this.status = status;
	}

	public String getCancelRemark() {
		return cancelRemark;
	}

	public void setCancelRemark(String cancelRemark) {
		this.cancelRemark = cancelRemark;
	}

	public TCOrderDTO getTcOrder() {
		return tcOrder;
	}

	public void setTcOrder(TCOrderDTO tcOrder) {
		this.tcOrder = tcOrder;
	}

}
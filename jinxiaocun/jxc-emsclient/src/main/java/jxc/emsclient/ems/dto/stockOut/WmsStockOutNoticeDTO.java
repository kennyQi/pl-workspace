package jxc.emsclient.ems.dto.stockOut;

import java.io.Serializable;
import java.util.List;


/**
 * 出库单通知(退货出库，非销售订单)
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class WmsStockOutNoticeDTO implements Serializable{
	
	/**
	 * 仓库机构编码(EMS提供)
	 */
	private String warehouse_code;
	
	/**
	 * 货主编码(EMS提供)
	 */
	private String owner_code;
	
	/**
	 * 出库单号
	 */
	private String order_id;
	
	/**
	 * 类型编码(自定义，定义后告知EMS)
	 */
	private String order_type_code;
	
	/**
	 * 类型名称
	 */
	private String order_type_name;
	
	/**
	 * 收货人姓名
	 */
	private String consignee;
	
	/**
	 * 收货人所在省
	 */
	private String prov;
	
	/**
	 * 收货人所在市
	 */
	private String city;
	
	/**
	 * 收货人所在县（区），若没有则不填
	 */
	private String district;
	
	/**
	 * 收货人详细地址
	 */
	private String address;
	
	/**
	 * 收货人手机号
	 */
	private String mobile;
	
	/**
	 * 收货人电话
	 */
	private String phone;
	
	/**
	 * 总件数
	 */
	private Long count;
	
	/**
	 * 该出库单对应商品种类数
	 */
	private Long sku_count;
	
	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 出库商品信息
	 */
	private List<StockOutProductDTO> details;
	
	/**
	 * 物流公司编码（暂不使用）
	 */
	private String LogisticProviderId;
	
	/**
	 * 物流公司名称（暂不使用）
	 */
	private String LogisticProviderName;	
	
	/**
	 * 收货方编码，适用于公司客户、调入仓库编码、供应商编码（暂不使用）
	 */
	private String receiver_code;
	
	/**
	 * 收货方名称（暂不使用）
	 */
	private String receiver_name;
	
	/**
	 * 收货人邮编（暂不使用）
	 */
	private String post_Code;
	
	public String getWarehouse_code() {
		return warehouse_code;
	}

	public void setWarehouse_code(String warehouse_code) {
		this.warehouse_code = warehouse_code;
	}

	public String getOwner_code() {
		return owner_code;
	}

	public void setOwner_code(String owner_code) {
		this.owner_code = owner_code;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getOrder_type_code() {
		return order_type_code;
	}

	public void setOrder_type_code(String order_type_code) {
		this.order_type_code = order_type_code;
	}

	public String getOrder_type_name() {
		return order_type_name;
	}

	public void setOrder_type_name(String order_type_name) {
		this.order_type_name = order_type_name;
	}


	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Long getSku_count() {
		return sku_count;
	}

	public void setSku_count(Long sku_count) {
		this.sku_count = sku_count;
	}

	public List<StockOutProductDTO> getDetails() {
		return details;
	}

	public void setDetails(List<StockOutProductDTO> details) {
		this.details = details;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLogisticProviderId() {
		return LogisticProviderId;
	}

	public void setLogisticProviderId(String logisticProviderId) {
		LogisticProviderId = logisticProviderId;
	}

	public String getLogisticProviderName() {
		return LogisticProviderName;
	}

	public void setLogisticProviderName(String logisticProviderName) {
		LogisticProviderName = logisticProviderName;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getProv() {
		return prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getReceiver_code() {
		return receiver_code;
	}

	public void setReceiver_code(String receiver_code) {
		this.receiver_code = receiver_code;
	}

	public String getReceiver_name() {
		return receiver_name;
	}

	public void setReceiver_name(String receiver_name) {
		this.receiver_name = receiver_name;
	}

	public String getPost_Code() {
		return post_Code;
	}

	public void setPost_Code(String post_Code) {
		this.post_Code = post_Code;
	}
	
}

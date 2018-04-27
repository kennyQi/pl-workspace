package jxc.emsclient.ems.dto.stockOut;

import java.io.Serializable;
import java.util.List;



/**
 * 订单通知
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class WmsOrderNoticeDTO implements Serializable{



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
	 * 参考订单号，例如淘宝交易单号（暂不使用）
	 */
	private String ref_order_id;
	
	/**
	 * 物流公司编码（暂不使用）
	 */
	private String LogisticProviderId="EMS";
	
	/**
	 * 物流公司名称（暂不使用）
	 */
	private String LogisticProviderName="EMS";
	
	/**
	 * 总价（暂不使用）
	 */
	private Double total_price;
	
	/**
	 * 是否货到付款（暂不使用）
	 */
	private String iscod;
	
	/**
	 * 货到付款金额（暂不使用）
	 */
	private Double cod_amount;
	
	/**
	 * 店铺名称（暂不使用）
	 */
	private String shop_name;
	
	/**
	 * 订单来源(TAOBAO 淘宝 JD 京东 PAIPAI 拍拍 均大写)（暂不使用）
	 */
	private String order_source;
	
	/**
	 * 发货模式(SOP-发给个人  SOPL、LBP-发给DC)（暂不使用）
	 */
	private String ship_mode;
	
	/**
	 * Dc名称(京东模式使用)（暂不使用）
	 */
	private String dc_name;
	
	/**
	 * 发货备注  买家留言（暂不使用）
	 */
	private String ship_memo;
	
	/**
	 * 订单扩展属性1   支付方式（如 4-在线支付 ，1-货到付款）（暂不使用）
	 */
	private String ext_attr1;
	
	/**
	 * 订单扩展属性2   送货时间（只工作日送货(双休日、假日不用送)）（暂不使用）
	 */
	private String ext_attr2;
	
	/**
	 * 订单扩展属性3  是否送货前通知（暂不使用）
	 */
	private String ext_attr3;
	
	/**
	 * 订单扩展属性4（暂不使用）
	 */
	private String ext_attr4;
	
	/**
	 * 订单扩展属性5（暂不使用）
	 */
	private String ext_attr5;
	
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

	public String getRef_order_id() {
		return ref_order_id;
	}

	public void setRef_order_id(String ref_order_id) {
		this.ref_order_id = ref_order_id;
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

	public String getIscod() {
		return iscod;
	}

	public void setIscod(String iscod) {
		this.iscod = iscod;
	}

	public Double getTotal_price() {
		return total_price;
	}

	public void setTotal_price(Double total_price) {
		this.total_price = total_price;
	}

	public Double getCod_amount() {
		return cod_amount;
	}

	public void setCod_amount(Double cod_amount) {
		this.cod_amount = cod_amount;
	}

	public String getShop_name() {
		return shop_name;
	}

	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}

	public String getOrder_source() {
		return order_source;
	}

	public void setOrder_source(String order_source) {
		this.order_source = order_source;
	}

	public String getShip_mode() {
		return ship_mode;
	}

	public void setShip_mode(String ship_mode) {
		this.ship_mode = ship_mode;
	}

	public String getDc_name() {
		return dc_name;
	}

	public void setDc_name(String dc_name) {
		this.dc_name = dc_name;
	}

	public String getShip_memo() {
		return ship_memo;
	}

	public void setShip_memo(String ship_memo) {
		this.ship_memo = ship_memo;
	}

	public String getExt_attr1() {
		return ext_attr1;
	}

	public void setExt_attr1(String ext_attr1) {
		this.ext_attr1 = ext_attr1;
	}

	public String getExt_attr2() {
		return ext_attr2;
	}

	public void setExt_attr2(String ext_attr2) {
		this.ext_attr2 = ext_attr2;
	}

	public String getExt_attr3() {
		return ext_attr3;
	}

	public void setExt_attr3(String ext_attr3) {
		this.ext_attr3 = ext_attr3;
	}

	public String getExt_attr4() {
		return ext_attr4;
	}

	public void setExt_attr4(String ext_attr4) {
		this.ext_attr4 = ext_attr4;
	}

	public String getExt_attr5() {
		return ext_attr5;
	}

	public void setExt_attr5(String ext_attr5) {
		this.ext_attr5 = ext_attr5;
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

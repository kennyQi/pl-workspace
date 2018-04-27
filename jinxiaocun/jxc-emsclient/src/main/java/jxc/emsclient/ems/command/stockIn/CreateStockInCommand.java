package jxc.emsclient.ems.command.stockIn;

import java.io.Serializable;
import java.util.List;

import jxc.emsclient.ems.dto.stockIn.StockInProductDTO;


public class CreateStockInCommand implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 仓库机构编码(EMS提供)
	 */
	private String warehouse_code;
	
	/**
	 * 货主编码(EMS提供)
	 */
	private String owner_code;
	
	/**
	 * 入库单号
	 */
	private String asn_id;
	
	/**
	 * 入库单类型编码(自定义，定义后告知EMS)
	 */
	private String order_type_code;
	
	/**
	 * 入库单类型名称
	 */
	private String order_type_name;
	
	/**
	 * 总件数
	 */
	private Long count;
	
	/**
	 * 该入库单对应商品种数
	 */
	private Long sku_count;
	
	/**
	 * 备注
	 */
	private String remark;

	
	/**
	 * 入库单中商品列表
	 */
	private List<StockInProductDTO> stockInProductList;


	
	//不用的字段
	/**
	 * 销售订单号，客户退货入库时使用，记录退货相关的源订单号
	 */
	private String sale_order_id;
	
	/**
	 * 发货方编码，适用于公司客户、调出仓库编码、供应商编码
	 */
	private String sender_code;
	
	/**
	 * 发货方名称
	 */
	private String sender_name;
	
	/**
	 * 发货方联系人
	 */
	private String sender_contact;
	
	/**
	 * 发货方联系电话
	 */
	private String sender_phone;
	
	/**
	 * 物流公司编码
	 */
	private String LogisticProviderId;
	
	/**
	 * 运输公司运单号
	 */
	private String tms_order_code;
	
	/**
	 * 运输公司名称
	 */
	private String LogisticProviderName;
	
	/**
	 * 发货时间（格式：yyyy-MM-dd HH:mm:ss）
	 */
	private String send_Time;
	
	/**
	 * 预计到达的时间（格式：yyyy-MM-dd HH:mm:ss）
	 */
	private String pre_arrive_time;
	
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


	public String getAsn_id() {
		return asn_id;
	}


	public void setAsn_id(String asn_id) {
		this.asn_id = asn_id;
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


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}



	public List<StockInProductDTO> getStockInProductList() {
		return stockInProductList;
	}


	public void setStockInProductList(List<StockInProductDTO> stockInProductList) {
		this.stockInProductList = stockInProductList;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getSale_order_id() {
		return sale_order_id;
	}


	public void setSale_order_id(String sale_order_id) {
		this.sale_order_id = sale_order_id;
	}


	public String getSender_code() {
		return sender_code;
	}


	public void setSender_code(String sender_code) {
		this.sender_code = sender_code;
	}


	public String getSender_name() {
		return sender_name;
	}


	public void setSender_name(String sender_name) {
		this.sender_name = sender_name;
	}


	public String getSender_contact() {
		return sender_contact;
	}


	public void setSender_contact(String sender_contact) {
		this.sender_contact = sender_contact;
	}


	public String getSender_phone() {
		return sender_phone;
	}


	public void setSender_phone(String sender_phone) {
		this.sender_phone = sender_phone;
	}


	public String getLogisticProviderId() {
		return LogisticProviderId;
	}


	public void setLogisticProviderId(String logisticProviderId) {
		LogisticProviderId = logisticProviderId;
	}


	public String getTms_order_code() {
		return tms_order_code;
	}


	public void setTms_order_code(String tms_order_code) {
		this.tms_order_code = tms_order_code;
	}


	public String getLogisticProviderName() {
		return LogisticProviderName;
	}


	public void setLogisticProviderName(String logisticProviderName) {
		LogisticProviderName = logisticProviderName;
	}


	public String getSend_Time() {
		return send_Time;
	}


	public void setSend_Time(String send_Time) {
		this.send_Time = send_Time;
	}


	public String getPre_arrive_time() {
		return pre_arrive_time;
	}


	public void setPre_arrive_time(String pre_arrive_time) {
		this.pre_arrive_time = pre_arrive_time;
	}


}

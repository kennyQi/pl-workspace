package jxc.emsclient.ems.command.stockOut;

import java.io.Serializable;
import java.util.List;

import jxc.emsclient.ems.dto.stockOut.OrderReceiveInfoDTO;
import jxc.emsclient.ems.dto.stockOut.StockOutProductDTO;

public class CreateOrderCommand implements Serializable{
	
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
	 * 收货人信息
	 */
	private OrderReceiveInfoDTO orderReceiveInfo;
	
	/**
	 * 总件数
	 */
	private Long count;
	
	/**
	 * 该出库单对应商品种类数
	 */
	private Long sku_count;
	
	/**
	 * 出库商品信息
	 */
	private List<StockOutProductDTO> stockOutProductList;
	
	/**
	 * 备注
	 */
	private String remark;

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

	public OrderReceiveInfoDTO getOrderReceiveInfo() {
		return orderReceiveInfo;
	}

	public void setOrderReceiveInfo(OrderReceiveInfoDTO orderReceiveInfo) {
		this.orderReceiveInfo = orderReceiveInfo;
	}

	public List<StockOutProductDTO> getStockOutProductList() {
		return stockOutProductList;
	}

	public void setStockOutProductList(List<StockOutProductDTO> stockOutProductList) {
		this.stockOutProductList = stockOutProductList;
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

}

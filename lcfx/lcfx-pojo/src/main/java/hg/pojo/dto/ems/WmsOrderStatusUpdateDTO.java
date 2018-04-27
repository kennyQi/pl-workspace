package hg.pojo.dto.ems;

import java.io.Serializable;
import java.util.List;

/**
 * 订单状态回传
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class WmsOrderStatusUpdateDTO implements Serializable{
	
	/**
	 * 仓库编码
	 */
	private String warehouse_code;
	
	/**
	 * 货主编码
	 */
	private String owner_code;
	
	/**
	 * 出库单号
	 */
	private String order_id;
	
	/**
	 * 出库单状态
	 */
	private String status;

	/**
	 * 备注，接单失败或取消发货时说明原因
	 */
	private String remark;
	
	/**
	 * 操作时间，（格式：yyyy-MM-dd HH:mm:ss）
	 */
	private String operate_time;
	
	/**
	 * 运单信息
	 */
	private List<DeliveryDTO> ship_details;
	
	
	/**
	 * 物流公司代码（WMS系统提供）（暂不使用）
	 */
	private String LogisticProviderId;
	
	/**
	 * 邮件种类（暂不使用）
	 */
	private String product_code;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOperate_time() {
		return operate_time;
	}

	public void setOperate_time(String operate_time) {
		this.operate_time = operate_time;
	}

	public String getLogisticProviderId() {
		return LogisticProviderId;
	}

	public void setLogisticProviderId(String logisticProviderId) {
		LogisticProviderId = logisticProviderId;
	}

	public String getProduct_code() {
		return product_code;
	}

	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}

	public List<DeliveryDTO> getShip_details() {
		return ship_details;
	}

	public void setShip_details(List<DeliveryDTO> ship_details) {
		this.ship_details = ship_details;
	}
	
	
}

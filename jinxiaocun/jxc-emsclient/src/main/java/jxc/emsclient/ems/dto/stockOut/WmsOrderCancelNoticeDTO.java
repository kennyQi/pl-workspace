package jxc.emsclient.ems.dto.stockOut;

import java.io.Serializable;

/**
 * 取消订单、出库单
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class WmsOrderCancelNoticeDTO implements Serializable{
	

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

}

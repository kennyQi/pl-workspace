package hg.pojo.dto.ems;

import java.io.Serializable;
import java.util.List;

/**
 * 出库单确认出库回传（ems推送给我们）
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class WmsStockOutConfirmDTO implements Serializable{
	
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
	 * 出库完成时间（格式：yyyy-MM-dd HH:mm:ss）
	 */
	private String consign_time;
	
	/**
	 * 出库单商品列表
	 */
	private List<StockOutConfirmProductDTO> details;

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

	public String getConsign_time() {
		return consign_time;
	}

	public void setConsign_time(String consign_time) {
		this.consign_time = consign_time;
	}

	public List<StockOutConfirmProductDTO> getDetails() {
		return details;
	}

	public void setDetails(List<StockOutConfirmProductDTO> details) {
		this.details = details;
	}
	
	

}

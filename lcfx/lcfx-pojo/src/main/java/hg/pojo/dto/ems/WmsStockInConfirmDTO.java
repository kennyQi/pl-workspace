package hg.pojo.dto.ems;

import java.io.Serializable;
import java.util.List;

/**
 * 入库单收货确认回传（ems推送给我们）
 * @author liujz
 *
 */
@SuppressWarnings("serial")
public class WmsStockInConfirmDTO implements Serializable{
	
	/**
	 * 货主编码
	 */
	private String owner_code;
	
	/**
	 * 仓库编码
	 */
	private String warehouse_code;
	
	/**
	 * 入库单号
	 */
	private String asn_id;
	
	/**
	 * 收货完成时间（格式：yyyy-MM-dd HH:mm:ss）
	 */
	private String receive_time;
	
	/**
	 * 入库单商品列表
	 */
	private List<StockInConfirmProductDTO> details;

	public String getOwner_code() {
		return owner_code;
	}

	public void setOwner_code(String owner_code) {
		this.owner_code = owner_code;
	}

	public String getWarehouse_code() {
		return warehouse_code;
	}

	public void setWarehouse_code(String warehouse_code) {
		this.warehouse_code = warehouse_code;
	}

	public String getAsn_id() {
		return asn_id;
	}

	public void setAsn_id(String asn_id) {
		this.asn_id = asn_id;
	}

	public String getReceive_time() {
		return receive_time;
	}

	public void setReceive_time(String receive_time) {
		this.receive_time = receive_time;
	}

	public List<StockInConfirmProductDTO> getDetails() {
		return details;
	}

	public void setDetails(List<StockInConfirmProductDTO> details) {
		this.details = details;
	}
	
	
}

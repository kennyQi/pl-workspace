package plfx.jd.pojo.dto.ylclient.hotel;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class HotelDataInventoryResultDTO implements Serializable {

	/**
	 * 库存集合
	 */
	protected List<InventoryDTO> inventories;

	public List<InventoryDTO> getInventories() {
		return inventories;
	}

	public void setInventories(List<InventoryDTO> inventories) {
		this.inventories = inventories;
	}

	
}

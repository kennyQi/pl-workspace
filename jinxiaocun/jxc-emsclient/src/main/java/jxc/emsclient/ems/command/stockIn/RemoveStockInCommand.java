package jxc.emsclient.ems.command.stockIn;

import java.io.Serializable;

public class RemoveStockInCommand implements Serializable{
	
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
	
}

package zzpl.api.client.dto.jp;


public class GJCabinInfoDTO {
	/**
	 * 舱位类型
	 */
	private String cabinCode;

	/**
	 * 剩余座位数
	 */
	private String cabinSales;

	public String getCabinCode() {
		return cabinCode;
	}

	public void setCabinCode(String cabinCode) {
		this.cabinCode = cabinCode;
	}

	public String getCabinSales() {
		return cabinSales;
	}

	public void setCabinSales(String cabinSales) {
		this.cabinSales = cabinSales;
	}

}

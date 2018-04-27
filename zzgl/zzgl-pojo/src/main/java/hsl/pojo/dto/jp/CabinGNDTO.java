package hsl.pojo.dto.jp;

import hsl.pojo.dto.BaseDTO;
@SuppressWarnings("serial")
public class CabinGNDTO extends BaseDTO{
	/**
	 * 舱位
	 */
	private String cabinCode;
	/**
	 * 舱位类型
	 */
	private String cabinType;
	/**
	 * 舱位折扣
	 */
	private String cabinDiscount;
	/**
	 * 舱位名称
	 */
	private String cabinName;
	/**
	 * 剩余座位数
	 */
	private String cabinSales;
	/**
	 * 票面价
	 */
	private String ibePrice;
	/**
	 * 加密字符串
	 */
	private String encryptString;
	
	public String getCabinCode() {
		return cabinCode;
	}
	public void setCabinCode(String cabinCode) {
		this.cabinCode = cabinCode;
	}
	public String getCabinType() {
		return cabinType;
	}
	public void setCabinType(String cabinType) {
		this.cabinType = cabinType;
	}
	public String getCabinDiscount() {
		return cabinDiscount;
	}
	public void setCabinDiscount(String cabinDiscount) {
		this.cabinDiscount = cabinDiscount;
	}
	public String getCabinName() {
		return cabinName;
	}
	public void setCabinName(String cabinName) {
		this.cabinName = cabinName;
	}
	public String getCabinSales() {
		return cabinSales;
	}
	public void setCabinSales(String cabinSales) {
		this.cabinSales = cabinSales;
	}
	public String getIbePrice() {
		return ibePrice;
	}
	public void setIbePrice(String ibePrice) {
		this.ibePrice = ibePrice;
	}
	public String getEncryptString() {
		return encryptString;
	}
	public void setEncryptString(String encryptString) {
		this.encryptString = encryptString;
	}
}

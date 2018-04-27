package plfx.yeexing.pojo.dto.flight;



import plfx.jp.pojo.dto.BaseJpDTO;

/**
 * 
 * @类功能说明：舱位信息
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年6月12日下午4:25:49
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class YeeXingCabinDTO extends BaseJpDTO{

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

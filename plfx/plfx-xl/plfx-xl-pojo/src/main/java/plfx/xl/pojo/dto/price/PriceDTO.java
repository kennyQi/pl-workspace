package plfx.xl.pojo.dto.price;


/**
 * 
 * @类功能说明：用于价格日历页面显示
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：luoyun
 * @创建时间：2014年12月17日下午5:28:32
 * @版本：V1.0
 *
 */
public class PriceDTO {
	
	/**
	 * ID
	 */
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 属于哪个线路
	 */
	private String lineID;

	/**
	 * 属于哪天
	 */
	private String saleDate;

	/**
	 * 成人价
	 */
	private Double adultPrice;

	/**
	 * 儿童价
	 */
	private Double childPrice;

	/**
	 * 剩余人数
	 */
	private Integer number;

	public String getLineID() {
		return lineID;
	}

	public void setLineID(String lineID) {
		this.lineID = lineID;
	}

	public String getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}

	public Double getAdultPrice() {
		return adultPrice;
	}

	public void setAdultPrice(Double adultPrice) {
		this.adultPrice = adultPrice;
	}

	public Double getChildPrice() {
		return childPrice;
	}

	public void setChildPrice(Double childPrice) {
		this.childPrice = childPrice;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	
	
	

}

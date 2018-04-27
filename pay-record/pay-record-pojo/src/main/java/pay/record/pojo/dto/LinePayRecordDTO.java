package pay.record.pojo.dto;

import java.util.Date;


/***
 * 
 * @类功能说明：支付记录DTO
 * @类修改者：yaosanfeng
 * @修改日期：2015年12月3日15:24
 * @修改说明：增加属性
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年12月3日下午3:24:19
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class LinePayRecordDTO extends BasePayRecordDTO{
	/**
	 * 线路id
	 */
	private String lineID;
	
	/**
	 * 线路名称
	 */
	private String lineName;
	
	/**
	 * 线路类别
	 */
	private String type;
	
	/**
	 * 成人人数
	 */
	private Integer adultNo;
	
	/**
	 * 儿童人数
	 */
	private Integer childNo;
	
	/**
	 * 成人销售单价
	 */
	private Double adultUnitPrice;
	
	/**
	 * 儿童销售单价
	 */
	private Double childUnitPrice;
	
	/**
	 * 销售总价
	 */
	private Double salePrice;
	
	/**
	 * 销售定金
	 */
	private Double bargainMoney;
	
	/**
	 * 发团日期
	 */
	private Date travelDate;

	public String getLineID() {
		return lineID;
	}

	public void setLineID(String lineID) {
		this.lineID = lineID;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getAdultNo() {
		return adultNo;
	}

	public void setAdultNo(Integer adultNo) {
		this.adultNo = adultNo;
	}

	public Integer getChildNo() {
		return childNo;
	}

	public void setChildNo(Integer childNo) {
		this.childNo = childNo;
	}

	public Double getAdultUnitPrice() {
		return adultUnitPrice;
	}

	public void setAdultUnitPrice(Double adultUnitPrice) {
		this.adultUnitPrice = adultUnitPrice;
	}

	public Double getChildUnitPrice() {
		return childUnitPrice;
	}

	public void setChildUnitPrice(Double childUnitPrice) {
		this.childUnitPrice = childUnitPrice;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public Double getBargainMoney() {
		return bargainMoney;
	}

	public void setBargainMoney(Double bargainMoney) {
		this.bargainMoney = bargainMoney;
	}

	public Date getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(Date travelDate) {
		this.travelDate = travelDate;
	}
}

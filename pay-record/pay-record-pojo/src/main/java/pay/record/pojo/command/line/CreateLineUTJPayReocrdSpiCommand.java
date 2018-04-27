package pay.record.pojo.command.line;

import java.util.Date;

import pay.record.pojo.command.BasePayRecordCommand;

/**
 * 
 * @类功能说明：保存线路支付记录SpiCommand->用户到经销商
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年11月27日下午2:29:06
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class CreateLineUTJPayReocrdSpiCommand extends BasePayRecordCommand{
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
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

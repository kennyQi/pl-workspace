package plfx.xl.pojo.qo;
import hg.common.component.BaseQo;

import java.util.Date;

/**
 *@类功能说明：线路订单Qo
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：lixx
 *@创建时间：2014年12月9日下午3:10:49
 *
 */
@SuppressWarnings("serial")
public class LineOrderQO extends BaseQo {

	/**
	 * 经销商订单编号
	 */
	private String dealerOrderNo;
	
	/**
	 * 排序条件
	 */
	private Boolean createDateAsc;

	/**
	 * 订单号
	 */
	private String orderNo;
	
	/**
	 * 线路id
	 */
	private String lineId;
	
	/**
	 * 线路名称
	 */
	private String lineName;
	
	/**
	 * 状态
	 */
	private Integer status;
	
	/**
	 * 支付状态
	 */
	private Integer payStatus;
	
	/**
	 * 出发省
	 */
	private String startingProvinceID;
	
	/**
	 * 出发市
	 */
	private String startingCityID;

	/**
	 * 出发地：1、国内；2、国外
	 *只供页面交互使用
	 */
	private String startingDepart;
	
	/**
	 * 线路类别
	 */
	private Integer type;
	/**
	 * 线路类别：1、国内线；2、国外线
	 * 只供页面交互使用
	 */
	private String domesticLine;
	
	/**
	 * 线路类型中的省编号
	 */
	private String provinceOfType;
	
	/**
	 * 线路类型中的城市编号
	 */
	private String cityOfType;
	/**
	 * 创建开始时间
	 */
	private Date createDateFrom;
	
	/**
	 * 创建结束时间
	 */
	private Date createDateTo;
	
	/**
	 * 销售总价最低
	 */
	private Double beginPrice;
		
	/**
	 * 销售总价最高
	 */
	private Double endPrice;
	
	public Boolean getCreateDateAsc() {
		return createDateAsc;
	}

	public String getDealerOrderNo() {
		return dealerOrderNo;
	}

	public void setDealerOrderNo(String dealerOrderNo) {
		this.dealerOrderNo = dealerOrderNo;
	}

	public void setCreateDateAsc(Boolean createDateAsc) {
		this.createDateAsc = createDateAsc;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public String getStartingCityID() {
		return startingCityID;
	}

	public void setStartingCityID(String startingCityID) {
		this.startingCityID = startingCityID;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getCreateDateFrom() {
		return createDateFrom;
	}

	public void setCreateDateFrom(Date createDateFrom) {
		this.createDateFrom = createDateFrom;
	}

	public Date getCreateDateTo() {
		return createDateTo;
	}

	public void setCreateDateTo(Date createDateTo) {
		this.createDateTo = createDateTo;
	}

	public Double getBeginPrice() {
		return beginPrice;
	}

	public void setBeginPrice(Double beginPrice) {
		this.beginPrice = beginPrice;
	}

	public Double getEndPrice() {
		return endPrice;
	}

	public void setEndPrice(Double endPrice) {
		this.endPrice = endPrice;
	}

	public String getStartingProvinceID() {
		return startingProvinceID;
	}

	public void setStartingProvinceID(String startingProvinceID) {
		this.startingProvinceID = startingProvinceID;
	}

	public String getStartingDepart() {
		return startingDepart;
	}

	public void setStartingDepart(String startingDepart) {
		this.startingDepart = startingDepart;
	}

	public String getDomesticLine() {
		return domesticLine;
	}

	public void setDomesticLine(String domesticLine) {
		this.domesticLine = domesticLine;
	}

	public String getProvinceOfType() {
		return provinceOfType;
	}

	public void setProvinceOfType(String provinceOfType) {
		this.provinceOfType = provinceOfType;
	}

	public String getCityOfType() {
		return cityOfType;
	}

	public void setCityOfType(String cityOfType) {
		this.cityOfType = cityOfType;
	}
	
	
	
}

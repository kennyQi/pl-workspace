package plfx.xl.pojo.dto.order;

import plfx.xl.pojo.dto.BaseXlDTO;

/**
 * 
 * @类功能说明：线路订单游客信息
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年11月26日下午3:02:11
 * 
 */
@SuppressWarnings("serial")
public class LineOrderTravelerDTO extends BaseXlDTO{
	
	/**
	 * 关联线路订单
	 */
	private LineOrderDTO lineOrder;
	
	/**
	 * 姓名
	 */
	private String name;
	
	/**
	 * 手机号
	 */	
	private String mobile;
	
	/**
	 * 游客类别
	 */
	private Integer type;

	/**
	 * 证件号
	 */
	private String idNo;

	/**
	 * 证件类型
	 */
	private Integer idType;

	/**
	 * 游玩人订单状态
	 */
	private XLOrderStatusDTO xlOrderStatus;
	
	/**
	 * 单人全款金额
	 */
	private Double singleSalePrice;
	
	/**
	 * 单人定金
	 */
	private Double singleBargainMoney;
	
	/**
	 * 修改游玩人全款备注
	 */
	private String remark;
	
	
	

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public LineOrderDTO getLineOrder() {
		return lineOrder;
	}

	public void setLineOrder(LineOrderDTO lineOrder) {
		this.lineOrder = lineOrder;
	}

	public XLOrderStatusDTO getXlOrderStatus() {
		return xlOrderStatus;
	}

	public void setXlOrderStatus(XLOrderStatusDTO xlOrderStatus) {
		this.xlOrderStatus = xlOrderStatus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public Double getSingleSalePrice() {
		return singleSalePrice;
	}

	public void setSingleSalePrice(Double singleSalePrice) {
		this.singleSalePrice = singleSalePrice;
	}

	public Double getSingleBargainMoney() {
		return singleBargainMoney;
	}

	public void setSingleBargainMoney(Double singleBargainMoney) {
		this.singleBargainMoney = singleBargainMoney;
	}

}
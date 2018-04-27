package hsl.pojo.dto.line.order;


import hsl.pojo.dto.BaseDTO;

@SuppressWarnings("serial")
public class LineOrderTravelerDTO extends BaseDTO{
	
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

	/*public final static Integer TYPE_ADULT = 1;
	public final static Integer TYPE_CHILD = 2;*/

	/**
	 * 证件号
	 */
	private String idNo;

	/**
	 * 证件类型
	 */
	private Integer idType;

	/*public final static Integer ID_TYPE_SFZ = 1; // 身份证*/	
	/**
	 * 游玩人订单状态
	 */
	private LineOrderStatusDTO lineOrderStatus;
	
	/**
	 * 单人全款金额
	 */
	private Double singleSalePrice;
	
	/**
	 * 单人定金
	 */
	private Double singleBargainMoney;
	
	
	/**
	 * 修改游玩人价格的备注
	 */
	private String remark;

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

	public LineOrderStatusDTO getLineOrderStatus() {
		return lineOrderStatus;
	}

	public void setLineOrderStatus(LineOrderStatusDTO lineOrderStatus) {
		this.lineOrderStatus = lineOrderStatus;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}

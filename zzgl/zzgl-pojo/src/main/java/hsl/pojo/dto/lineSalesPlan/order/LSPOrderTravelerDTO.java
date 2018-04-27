package hsl.pojo.dto.lineSalesPlan.order;
import hsl.pojo.dto.BaseDTO;
/**
* @类功能说明：订单的游客列表
* @类修改者：
* @公司名称： 浙江票量云科技有限公司
* @部门： 技术部
* @作者： chenxy
* @创建时间：  2015-11-28 14:57:19
* @版本： V1.0
*/
public class LSPOrderTravelerDTO extends BaseDTO {
	
	/**
	 * 关联线路订单
	 */
	private LSPOrderDTO lineOrder;
	
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

	public final static Integer ID_TYPE_SFZ = 1; // 身份证*/	
	/**
	 * 游玩人订单状态
	 */
	private LSPOrderStatusDTO lspOrderStatus;
	
	/**
	 * 单人全款金额
	 */
	private Double singleSalePrice;

	/**
	 * 单人定金
	 */
	private Double singleBargainMoney;

	/**
	 * 组织ID
	 */
	private String companyId;
	
	/**
	 * 组织名称
	 */
	private String companyName;
	
	/**
	 * 部门ID
	 */
	private String departmentId;
	
	/**
	 * 部门名称
	 */
	private String departmentName;
	
	/**
	 * 成员ID
	 */
	private String memeberId;
	
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

	public Double getSingleSalePrice() {
		return singleSalePrice;
	}

	public void setSingleSalePrice(Double singleSalePrice) {
		this.singleSalePrice = singleSalePrice;
	}


	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getMemeberId() {
		return memeberId;
	}

	public void setMemeberId(String memeberId) {
		this.memeberId = memeberId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public LSPOrderDTO getLineOrder() {
		return lineOrder;
	}

	public void setLineOrder(LSPOrderDTO lineOrder) {
		this.lineOrder = lineOrder;
	}

	public Double getSingleBargainMoney() {
		return singleBargainMoney;
	}

	public void setSingleBargainMoney(Double singleBargainMoney) {
		this.singleBargainMoney = singleBargainMoney;
	}

	public LSPOrderStatusDTO getLspOrderStatus() {
		return lspOrderStatus;
	}

	public void setLspOrderStatus(LSPOrderStatusDTO lspOrderStatus) {
		this.lspOrderStatus = lspOrderStatus;
	}
}
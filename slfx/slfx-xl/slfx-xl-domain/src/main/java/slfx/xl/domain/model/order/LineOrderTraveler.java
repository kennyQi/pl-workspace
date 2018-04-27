package slfx.xl.domain.model.order;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import slfx.xl.domain.model.M;
import slfx.xl.pojo.dto.order.LineOrderTravelerDTO;
import slfx.xl.pojo.system.XLOrderStatusConstant;

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
@Entity
@Table(name = M.TABLE_PREFIX + "LINE_ORDER_TRAVELER")
public class LineOrderTraveler extends BaseModel{
	
	/**
	 * 
	 * @方法功能说明：创建线路游玩人
	 * @修改者名字：tandeng
	 * @修改时间：2014年12月24日上午11:15:23
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@param lineOrder
	 * @return:void
	 * @throws
	 */
	public void createLineOrderTraveler(LineOrderTravelerDTO dto){
		this.setId(UUIDGenerator.getUUID());
		
//		this.setLineOrder(lineOrder);
		
		this.setName(dto.getName());
		
		this.setMobile(dto.getMobile());
		
		this.setType(dto.getType());
		
		this.setIdNo(dto.getIdNo());
		
		this.setIdType(dto.getIdType());
		
			XLOrderStatus os = new XLOrderStatus();
			os.setStatus(Integer.parseInt(XLOrderStatusConstant.SLFX_CREATE_SUCCESS_NOT_RESERVE));
			os.setPayStatus(Integer.parseInt(XLOrderStatusConstant.SLFX_WAIT_PAY_BARGAIN_MONEY));
			
		this.setXlOrderStatus(os);
	
		this.setSingleSalePrice(dto.getSingleSalePrice()); //游玩人全款价格
		
		this.setSingleBargainMoney(dto.getSingleBargainMoney()); //游玩人定金价格
		
	}
	
	/**
	 * 
	 * @方法功能说明：更新线路游玩人订单状态：定金
	 * @修改者名字：tandeng
	 * @修改时间：2014年12月24日上午11:15:23
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@param lineOrder
	 * @return:void
	 * @throws
	 */
	public LineOrderTraveler modifyLineOrderTravelerStatusBargainMoney(LineOrderTraveler traveler){

		XLOrderStatus os = new XLOrderStatus();
			os.setStatus(Integer.parseInt(XLOrderStatusConstant.SLFX_CREATE_SUCCESS_NOT_RESERVE));
			os.setPayStatus(Integer.parseInt(XLOrderStatusConstant.SLFX_PAY_BARGAIN_MONEY));
		
		traveler.setXlOrderStatus(os);
		return traveler;
	}
	/**
	 * 
	 * @方法功能说明：更新线路游玩人订单状态：尾款
	 * @修改者名字：tandeng
	 * @修改时间：2014年12月24日上午11:15:23
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@param lineOrder
	 * @return:void
	 * @throws
	 */
	public LineOrderTraveler modifyLineOrderTravelerStatusTailMoney(LineOrderTraveler traveler){
		
		XLOrderStatus os = new XLOrderStatus();
		os.setStatus(Integer.parseInt(XLOrderStatusConstant.SLFX_CREATE_SUCCESS_NOT_RESERVE));
		os.setPayStatus(Integer.parseInt(XLOrderStatusConstant.SLFX_PAY_SUCCESS));
		
		traveler.setXlOrderStatus(os);
		return traveler;
	}
	/**
	 * 
	 * @方法功能说明：更新线路游玩人订单状态：全款
	 * @修改者名字：tandeng
	 * @修改时间：2014年12月24日上午11:15:23
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@param lineOrder
	 * @return:void
	 * @throws
	 */
	public LineOrderTraveler modifyLineOrderTravelerStatusAllMoney(LineOrderTraveler traveler){
		
		XLOrderStatus os = new XLOrderStatus();
		os.setStatus(Integer.parseInt(XLOrderStatusConstant.SLFX_CREATE_SUCCESS_NOT_RESERVE));
		os.setPayStatus(Integer.parseInt(XLOrderStatusConstant.SLFX_PAY_SUCCESS));
		
		traveler.setXlOrderStatus(os);
		return traveler;
	}
	
	/**
	 * 关联线路订单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LINE_ORDER_ID")
	private LineOrder lineOrder;
	
	/**
	 * 姓名
	 */
	@Column(name = "NAME", length = 64 )
	private String name;
	
	/**
	 * 手机号
	 */	
	@Column(name = "MOBILE", length = 11 )
	private String mobile;
	
	/**
	 * 游客类别
	 */
	@Column(name = "TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer type;

	/*public final static Integer TYPE_ADULT = 1;
	public final static Integer TYPE_CHILD = 2;*/

	/**
	 * 证件号
	 */
	@Column(name = "ID_NO", length = 64 )
	private String idNo;

	/**
	 * 证件类型
	 */
	@Column(name = "ID_TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer idType;

	/*public final static Integer ID_TYPE_SFZ = 1; // 身份证*/	
	/**
	 * 游玩人订单状态
	 */
	@Embedded
	private XLOrderStatus xlOrderStatus;
	
	/**
	 * 单人全款金额
	 */
	@Column(name = "SINGLE_SALE_PRICE", columnDefinition = M.DOUBLE_COLUM)
	private Double singleSalePrice;
	
	/**
	 * 单人定金
	 */
	@Column(name = "SINGLE_BARGAIN_MONEY", columnDefinition = M.DOUBLE_COLUM)
	private Double singleBargainMoney;
	
	/**
	 * 修改游玩人价格的备注
	 */
	@Column(name = "SINGLE_SALEPRICE_REMARK")
	private String remark;

	@Column(name = "SINGLE_FREEZESTATUS")
	private String freezeStatus;
	
	public LineOrder getLineOrder() {
		return lineOrder;
	}

	public void setLineOrder(LineOrder lineOrder) {
		this.lineOrder = lineOrder;
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

	public XLOrderStatus getXlOrderStatus() {
		return xlOrderStatus;
	}

	public void setXlOrderStatus(XLOrderStatus xlOrderStatus) {
		this.xlOrderStatus = xlOrderStatus;
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

	public String getFreezeStatus() {
		return freezeStatus;
	}

	public void setFreezeStatus(String freezeStatus) {
		this.freezeStatus = freezeStatus;
	}

}
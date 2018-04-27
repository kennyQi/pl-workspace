package hsl.pojo.command.lineSalesPlan.order;
import hg.common.component.BaseCommand;
import hg.common.util.BeanMapperUtils;
import hsl.pojo.dto.lineSalesPlan.order.LSPOrderTravelerDTO;
import plfx.api.client.api.v1.xl.request.command.XLCreateLineOrderApiCommand;
import plfx.xl.pojo.dto.order.LineOrderBaseInfoDTO;
import plfx.xl.pojo.dto.order.LineOrderLinkInfoDTO;
import plfx.xl.pojo.dto.order.LineOrderTravelerDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * @类功能说明：创建线路销售方案的订单
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江票量云数据科技有限公司
 * @部门： 技术部
 * @作者： chenxy
 * @创建时间： 2015/12/1 17:32
 */
public class CreateLSPOrderCommand extends BaseCommand {
	/**
	 * 经销商订单编号
	 */
	private String dealerOrderNo;
	/**
	 * 订单类型
	 */
	private Integer orderType;

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
	 * 销售总价（销售方案价格）
	 */
	private Double salePrice;

	/**
	 * 供应商总价（分销原来价格，提供分销时需要提供该价格）
	 */
	private Double supplierPrice;

	/**
	 * 发团日期
	 */
	private Date travelDate;
	/**
	 * 联系人姓名
	 */
	private String linkName;

	/**
	 * 联系人手机号
	 */
	private String linkMobile;

	/**
	 * 联系人邮箱
	 */
	private String email;
	/**
	 * 下单用户ID
	 */
	private String userId;
	/**
	 * 下单用户名
	 */
	private String loginName;
	/**
	 * 下单用户手机号
	 */
	private String mobile;
	/**
	 *游客列表
	 */
	private List<LSPOrderTravelerDTO> travelerList;
	/**
	 * 游客ID（页面添加游客时用到）
	 */
	private List<String> travelerIds;
	
	/**
	 * 活动剩余人数(页面上检查游客人数用到)
	 */
	private int lastNum;
 
	/**
	 *关联的线路的销售方案
	 */
	private String lspId;

	/**
	 * 转化为分销线路创建订单
	 * @return
	 */
	public XLCreateLineOrderApiCommand createLineOrderApiCommand(){
		XLCreateLineOrderApiCommand xlCreateLineOrderApiCommand=new XLCreateLineOrderApiCommand();
		LineOrderBaseInfoDTO lineOrderBaseInfoDTO= BeanMapperUtils.getMapper().map(this,LineOrderBaseInfoDTO.class);
		xlCreateLineOrderApiCommand.setBaseInfo(lineOrderBaseInfoDTO);
		LineOrderLinkInfoDTO linkInfo=BeanMapperUtils.getMapper().map(this,LineOrderLinkInfoDTO.class);
		xlCreateLineOrderApiCommand.setLinkInfo(linkInfo);
		List<LineOrderTravelerDTO> lineOrderTravelerDTOs=new ArrayList<LineOrderTravelerDTO>();
		if(this.getTravelerList()!=null){
			for (LSPOrderTravelerDTO lspOrderTravelerDTO:this.getTravelerList()){
				LineOrderTravelerDTO lineOrderTravelerDTO=BeanMapperUtils.getMapper().map(lspOrderTravelerDTO,LineOrderTravelerDTO.class);
				lineOrderTravelerDTOs.add(lineOrderTravelerDTO);
			}
		}
		xlCreateLineOrderApiCommand.setTravelerList(lineOrderTravelerDTOs);
		return xlCreateLineOrderApiCommand;
	}

	public String getDealerOrderNo() {
		return dealerOrderNo;
	}

	public void setDealerOrderNo(String dealerOrderNo) {
		this.dealerOrderNo = dealerOrderNo;
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

	public Double getSupplierPrice() {
		return supplierPrice;
	}

	public void setSupplierPrice(Double supplierPrice) {
		this.supplierPrice = supplierPrice;
	}

	public Date getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(Date travelDate) {
		this.travelDate = travelDate;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getLinkMobile() {
		return linkMobile;
	}

	public void setLinkMobile(String linkMobile) {
		this.linkMobile = linkMobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public List<LSPOrderTravelerDTO> getTravelerList() {
		return travelerList;
	}

	public void setTravelerList(List<LSPOrderTravelerDTO> travelerList) {
		this.travelerList = travelerList;
	}

	public String getLspId() {
		return lspId;
	}

	public void setLspId(String lspId) {
		this.lspId = lspId;
	}

	public List<String> getTravelerIds() {
		return travelerIds;
	}

	public void setTravelerIds(List<String> travelerIds) {
		this.travelerIds = travelerIds;
	}

	public int getLastNum() {
		return lastNum;
	}

	public void setLastNum(int lastNum) {
		this.lastNum = lastNum;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
}

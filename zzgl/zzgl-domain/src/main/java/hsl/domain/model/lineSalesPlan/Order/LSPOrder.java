package hsl.domain.model.lineSalesPlan.order;
import hg.common.component.BaseModel;
import hg.common.util.BeanMapperUtils;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;
import hsl.domain.model.lineSalesPlan.LineSalesPlan;
import hsl.domain.model.xl.Line;
import hsl.pojo.command.lineSalesPlan.order.CreateLSPOrderCommand;
import hsl.pojo.dto.lineSalesPlan.LineSalesPlanConstant;
import hsl.pojo.dto.lineSalesPlan.order.LSPOrderTravelerDTO;
import plfx.api.client.api.v1.xl.request.command.XLCreateLineOrderApiCommand;
import plfx.xl.pojo.dto.line.LineSnapshotDTO;
import plfx.xl.pojo.dto.order.LineOrderBaseInfoDTO;
import plfx.xl.pojo.dto.order.LineOrderLinkInfoDTO;
import plfx.xl.pojo.dto.order.LineOrderTravelerDTO;
import plfx.xl.pojo.dto.order.XLOrderStatusDTO;

import javax.persistence.*;
import java.util.*;
/**
 * @类功能说明：线路销售方案的订单（包括团购以及秒杀）
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江票量云数据科技有限公司
 * @部门： 技术部
 * @作者： chenxy
 * @创建时间： 2015/11/28 13:55
 */
@Entity
@Table(name = M.TABLE_PREFIX_LSP + "ORDER")
public class LSPOrder extends BaseModel{
	/**
	 * 订单基本信息
	 */
	@Embedded
	private  LSPOrderBaseInfo orderBaseInfo;
	/**
	 * 订单联系人信息
	 */
	@Embedded
	private  LSPOrderLinkInfo orderLinkInfo;
	/**
	 * 订单支付信息
	 */
	@Embedded
	private  LSPOrderPayInfo orderPayInfo;
	/**
	 * 销售方案的订单状态
	 */
	@Embedded
	private  LSPOrderStatus orderStatus;
	/**
	 * 订单下单用户快照
	 */
	@Embedded
	private  LSPOrderUser orderUser;
	/**
	 * 游客信息列表set
	 * */
	@OneToMany(mappedBy="lspOrder",cascade={CascadeType.ALL},fetch = FetchType.LAZY)
	private Set<LSPOrderTraveler> travelers;
	/**
	 * 游客信息列表list
	 */
	@Transient
	private List<LSPOrderTraveler> travelerList;
	/**
	 * 关联的线路的销售方案
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "LINE_SALES_PLAN_ID")
	private LineSalesPlan lineSalesPlan;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "LINE_ID")
	private Line line;

	public void createLSPOrder(CreateLSPOrderCommand createLSPOrderCommand,LineSalesPlan lineSalesPlan){
		this.setId(UUIDGenerator.getUUID());
		//添加订单的基本信息
		orderBaseInfo=new LSPOrderBaseInfo();
		orderBaseInfo.setDealerOrderNo(createLSPOrderCommand.getDealerOrderNo());
		orderBaseInfo.setOrderType(createLSPOrderCommand.getOrderType());
		orderBaseInfo.setAdultNo(createLSPOrderCommand.getAdultNo());
		orderBaseInfo.setAdultUnitPrice(createLSPOrderCommand.getAdultUnitPrice());
		orderBaseInfo.setChildNo(createLSPOrderCommand.getChildNo());
		orderBaseInfo.setChildUnitPrice(createLSPOrderCommand.getChildUnitPrice());
		orderBaseInfo.setCreateDate(new Date());
		orderBaseInfo.setSalePrice(createLSPOrderCommand.getSalePrice());
		orderBaseInfo.setSupplierPrice(createLSPOrderCommand.getSupplierPrice());
		orderBaseInfo.setTravelDate(createLSPOrderCommand.getTravelDate());
		this.setOrderBaseInfo(orderBaseInfo);
		//订单联系人信息
		orderLinkInfo=new LSPOrderLinkInfo();
		orderLinkInfo.setEmail(createLSPOrderCommand.getEmail());
		orderLinkInfo.setLinkMobile(createLSPOrderCommand.getLinkMobile());
		orderLinkInfo.setLinkName(createLSPOrderCommand.getLinkName());
		this.setOrderLinkInfo(orderLinkInfo);
		//下单人
		orderUser=new LSPOrderUser();
		orderUser.setLoginName(createLSPOrderCommand.getLoginName());
		orderUser.setUserId(createLSPOrderCommand.getUserId());
		orderUser.setMobile(createLSPOrderCommand.getMobile());
		this.setOrderUser(orderUser);
		//订单状态
		orderStatus=new LSPOrderStatus();
		orderStatus.createOrderStatus(createLSPOrderCommand.getOrderType());
		this.setOrderStatus(orderStatus);
		//游客订单
		Set<LSPOrderTraveler> lspOrderTravelers=new HashSet<LSPOrderTraveler>();
		for(LSPOrderTravelerDTO  lspOrderTravelerDTO:createLSPOrderCommand.getTravelerList()){
			LSPOrderTraveler lspOrderTraveler= BeanMapperUtils.getMapper().map(lspOrderTravelerDTO,LSPOrderTraveler.class);
			LSPOrderStatus travelerstatus=new LSPOrderStatus();
			travelerstatus.createOrderStatus(createLSPOrderCommand.getOrderType());
			lspOrderTraveler.setLspOrderStatus(orderStatus);
			lspOrderTraveler.setLspOrder(this);
			lspOrderTravelers.add(lspOrderTraveler);
		}
		this.setTravelers(lspOrderTravelers);
		if(lineSalesPlan!=null){
			this.setLineSalesPlan(lineSalesPlan);
			this.setLine(lineSalesPlan.getLine());
		}
	}

	/**
	 * lsp订单转化为分销线路订单Command
	 * @return
	 */
	public XLCreateLineOrderApiCommand convertXLCreateLineOrderApiCommand(){
		XLCreateLineOrderApiCommand xlCreateLineOrderApiCommand=new XLCreateLineOrderApiCommand();
		LineOrderBaseInfoDTO baseInfo=BeanMapperUtils.getMapper().map(this.getOrderBaseInfo(),LineOrderBaseInfoDTO.class);
		xlCreateLineOrderApiCommand.setBaseInfo(baseInfo);
		LineOrderLinkInfoDTO linkInfo=BeanMapperUtils.getMapper().map(this.getOrderLinkInfo(),LineOrderLinkInfoDTO.class);
		xlCreateLineOrderApiCommand.setLinkInfo(linkInfo);
		List<LineOrderTravelerDTO> travelerList=new ArrayList<LineOrderTravelerDTO>();
		for (LSPOrderTraveler lspOrderTraveler:this.getTravelerList()){
			LineOrderTravelerDTO lineOrderTravelerDTO=BeanMapperUtils.getMapper().map(lspOrderTraveler,LineOrderTravelerDTO.class);
			/**apiCommand的订单状态修改为 下单成功未占位 @see LineSalesPlanConstant
			 * 因为分销不区分活动订单和正常订单，没有相应的组团的订单状态
			 */
			XLOrderStatusDTO xlOrderStatusDTO=new XLOrderStatusDTO();
			xlOrderStatusDTO.setStatus(LineSalesPlanConstant.LSP_ORDER_STATUS_SUCCESS_NOT_RESERVE);
			xlOrderStatusDTO.setPayStatus(lspOrderTraveler.getLspOrderStatus().getPayStatus());
			lineOrderTravelerDTO.setXlOrderStatus(xlOrderStatusDTO);
			travelerList.add(lineOrderTravelerDTO);
		}
		xlCreateLineOrderApiCommand.setTravelerList(travelerList);
		LineSnapshotDTO lineSnapshot=new LineSnapshotDTO();
		lineSnapshot.setId(this.getLine().getLineSnapshotId());
		xlCreateLineOrderApiCommand.setLineSnapshot(lineSnapshot);
		xlCreateLineOrderApiCommand.setLineID(this.getLine().getId());
		return  xlCreateLineOrderApiCommand;

	}
	/**
	 * @方法功能说明：检查游客是否拥有此订单支付状态
	 * @修改者名字：zhurz
	 * @修改时间：2015-10-16下午4:07:55
	 * @修改内容：
	 * @参数：@param status
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean travelerHasPayStatus(String status) {
		if (getTravelers() != null) {
			for (LSPOrderTraveler traveler : getTravelers()) {
				if (Integer.valueOf(status).equals(traveler.getLspOrderStatus().getPayStatus()))
					return true;
			}
		}
		return false;
	}
	/**
	 * @方法功能说明：检查游客是否拥有此订单状态
	 * @修改者名字：zhurz
	 * @修改时间：2015-10-16下午4:07:21
	 * @修改内容：
	 * @参数：@param status
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean travelerHasOrderStatus(String status) {
		if (getTravelers() != null) {
			for (LSPOrderTraveler traveler : getTravelers()) {
				if (Integer.valueOf(status).equals(traveler.getLspOrderStatus().getOrderStatus()))
					return true;
			}
		}
		return false;
	}
	public LSPOrderBaseInfo getOrderBaseInfo() {
		return orderBaseInfo;
	}

	public void setOrderBaseInfo(LSPOrderBaseInfo orderBaseInfo) {
		this.orderBaseInfo = orderBaseInfo;
	}

	public LSPOrderLinkInfo getOrderLinkInfo() {
		return orderLinkInfo;
	}

	public void setOrderLinkInfo(LSPOrderLinkInfo orderLinkInfo) {
		this.orderLinkInfo = orderLinkInfo;
	}

	public LSPOrderPayInfo getOrderPayInfo() {
		return orderPayInfo;
	}

	public void setOrderPayInfo(LSPOrderPayInfo orderPayInfo) {
		this.orderPayInfo = orderPayInfo;
	}

	public LSPOrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(LSPOrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public LSPOrderUser getOrderUser() {
		return orderUser;
	}

	public void setOrderUser(LSPOrderUser orderUser) {
		this.orderUser = orderUser;
	}

	public Set<LSPOrderTraveler> getTravelers() {
		return travelers;
	}

	public void setTravelers(Set<LSPOrderTraveler> travelers) {
		this.travelers = travelers;
	}

	public List<LSPOrderTraveler> getTravelerList() {
		return travelerList;
	}

	public void setTravelerList(List<LSPOrderTraveler> travelerList) {
		this.travelerList = travelerList;
	}

	public LineSalesPlan getLineSalesPlan() {
		return lineSalesPlan;
	}

	public void setLineSalesPlan(LineSalesPlan lineSalesPlan) {
		this.lineSalesPlan = lineSalesPlan;
	}

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}
}

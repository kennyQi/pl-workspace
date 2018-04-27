package lxs.domain.model.line;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lxs.domain.model.M;
import lxs.pojo.command.line.CreateLineOrderCommand;
//import lxs.xl.domain.model.salepolicy.SalePolicySnapshot;
/**
 * @类功能说明：
 * @备注：
 * @类修改者：
 * @修改日期：2015-01-27 14:30:59
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：cc
 * @创建时间：2015-01-27 14:30:59
 */
@Entity
@Table(name = M.TABLE_PREFIX_XL + "LINE_ORDER")
public class LineOrder extends BaseModel {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 订单基本信息
	 */
	@Embedded
	private LineOrderBaseInfo baseInfo;
	
	/**
	 * 联系人信息
	 */
	@Embedded
	private LineOrderLinkInfo linkInfo;
	
	/**
	 * 游客信息列表set
	 * */
	@OneToMany(mappedBy="lineOrder",cascade={CascadeType.ALL})
	private Set<LineOrderTraveler> travelers;
	 
	
	/**
	 * 游客信息列表list
	 */
	@Transient
	private List<LineOrderTraveler> travelerList;
	
//	/**
//	 * 成人类销售政策快照
//	 * 销售政策分为 0成人儿童都适用 1成人适用 2儿童适用 三类
//	 * 该值为下单当前有效的0和1类政策中优先度最高的
//	 */
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "ADULT_SALE_POLICY_SNAPSHOT_ID")
//	private SalePolicySnapshot adultSalePolicySnapshot;
//	
//	/**
//	 * 儿童类销售政策快照
//	 * 销售政策分为 0成人儿童都适用 1成人适用 2儿童适用 三类
//	 * 该值为下单当前有效的0和2类政策中优先度最高的
//	 */
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "CHILD_SALE_POLICY_SNAPSHOT_ID")
//	private SalePolicySnapshot childSalePolicySnapshot;
	
	/**
	 * 线路快照
	 */
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "LINE_SNAPSHOT_ID")
	private LineSnapshot lineSnapshot;
	
	/**
	 * 线路订单状态
	 */
	@Embedded
	private LineOrderStatus status;
	
	/**
	 * 线路下单用户
	 */
	@Embedded
	private LineOrderUser lineOrderUser;
	/**
	 * 支付平台
	 */
	@Column(name = "PAYMENT", length = 64 )
	private String payment;
	
	/**
	 * 经销商订单编号
	 */
	@Column(name = "ORDER_STATUES", length = 64 )
	private String orderStautes;
	
	/**
	 * 经销商订单编号
	 */
	@Column(name = "PAY_STATUES", length = 64 )
	private String payStautes;

	/**
	 * 是否发过短信
	 */
	@Column(name = "HAVE_SENGED_SMS", length = 64 )
	private String haveSendedSMS;
	
	public final static String YES = "yes";
	public final static String NO = "no";
	
	/**
	 * 保险金额
	 */
	@Column(name = "INSURANCE_PRICE")
	private Integer insurancePrice;
	
	/**
	 * 是否购买保险
	 * 1：购买
	 * 0：未购买
	 */
	@Column(name = "IS_BUY_INSURANCE")
	private Integer isBuyInsurance;
	
	
	public Integer getInsurancePrice() {
		return insurancePrice;
	}

	public void setInsurancePrice(Integer insurancePrice) {
		this.insurancePrice = insurancePrice;
	}

	public Integer getIsBuyInsurance() {
		return isBuyInsurance;
	}

	public void setIsBuyInsurance(Integer isBuyInsurance) {
		this.isBuyInsurance = isBuyInsurance;
	}

	
	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}
	
	
//	public SalePolicySnapshot getAdultSalePolicySnapshot() {
//		if (adultSalePolicySnapshot == null)
//			adultSalePolicySnapshot = new SalePolicySnapshot();
//		return adultSalePolicySnapshot;
//	}
//
//	public void setAdultSalePolicySnapshot(SalePolicySnapshot adultSalePolicySnapshot) {
//		this.adultSalePolicySnapshot = adultSalePolicySnapshot;
//	}

	public Set<LineOrderTraveler> getTravelers() {
		return travelers;
	}

	public void setTravelers(Set<LineOrderTraveler> travelers) {
		this.travelers = travelers;
	}



	public List<LineOrderTraveler> getTravelerList() {
		return travelerList;
	}

	public void setTravelerList(List<LineOrderTraveler> travelerList) {
		this.travelerList = travelerList;
	}

	public LineOrderStatus getStatus() {
		if (status == null)
			status = new LineOrderStatus();
		return status;
	}

	public void setStatus(LineOrderStatus status) {
		this.status = status;
	}

//	public SalePolicySnapshot getChildSalePolicySnapshot() {
//		if (childSalePolicySnapshot == null)
//			childSalePolicySnapshot = new SalePolicySnapshot();
//		return childSalePolicySnapshot;
//	}
//
//	public void setChildSalePolicySnapshot(SalePolicySnapshot childSalePolicySnapshot) {
//		this.childSalePolicySnapshot = childSalePolicySnapshot;
//	}
	
	public LineOrderBaseInfo getBaseInfo() {
		if (baseInfo == null)
			baseInfo = new LineOrderBaseInfo();
		return baseInfo;
	}

	public void setBaseInfo(LineOrderBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public LineOrderLinkInfo getLinkInfo() {
		if (linkInfo == null)
			linkInfo = new LineOrderLinkInfo();
		return linkInfo;
	}

	public void setLinkInfo(LineOrderLinkInfo linkInfo) {
		this.linkInfo = linkInfo;
	}

	public LineOrderUser getLineOrderUser() {
		return lineOrderUser;
	}

	public void setLineOrderUser(LineOrderUser lineOrderUser) {
		this.lineOrderUser = lineOrderUser;
	}

	public LineSnapshot getLineSnapshot() {
		return lineSnapshot;
	}

	public void setLineSnapshot(LineSnapshot lineSnapshot) {
		this.lineSnapshot = lineSnapshot;
	}

	
	
	public String getOrderStautes() {
		return orderStautes;
	}

	public void setOrderStautes(String orderStautes) {
		this.orderStautes = orderStautes;
	}

	public String getPayStautes() {
		return payStautes;
	}

	public void setPayStautes(String payStautes) {
		this.payStautes = payStautes;
	}

	
	public String getHaveSendedSMS() {
		return haveSendedSMS;
	}

	public void setHaveSendedSMS(String haveSendedSMS) {
		this.haveSendedSMS = haveSendedSMS;
	}

	/**
	 * 
	 * @功能说明：创建线路订单
	 * @返回：void
	 * @修改者：
	 * @修改日期：
	 * @修改说明：
	 * @创建者 zhuxy
	 * @创建时间：2015年2月28日
	 */
	public void create(CreateLineOrderCommand command) {
		//id
		this.setId(UUIDGenerator.getUUID());
			//基本信息
			LineOrderBaseInfo baseInfo = new LineOrderBaseInfo();
			baseInfo.setDealerOrderNo(command.getBaseInfo().getDealerOrderNo());
			baseInfo.setAdultNo(command.getBaseInfo().getAdultNo());
			baseInfo.setChildNo(command.getBaseInfo().getChildNo());
			baseInfo.setAdultUnitPrice(command.getBaseInfo().getAdultUnitPrice());
			baseInfo.setChildUnitPrice(command.getBaseInfo().getChildUnitPrice());
			baseInfo.setSalePrice(command.getBaseInfo().getSalePrice());
			baseInfo.setBargainMoney(command.getBaseInfo().getBargainMoney());
			/*baseInfo.setSupplierAdultUnitPrice(command.getBaseInfo().getSupplierAdultUnitPrice());
			baseInfo.setSupplierUnitChildPrice(command.getBaseInfo().getSupplierUnitChildPrice());
			baseInfo.setSupplierPrice(command.getBaseInfo().getSupplierPrice());*/
			baseInfo.setCreateDate(new Date());
			baseInfo.setTravelDate(command.getBaseInfo().getTravelDate());
		this.setBaseInfo(baseInfo);
		
			//联系人信息
			LineOrderLinkInfo linkInfo = new LineOrderLinkInfo();
			linkInfo.setLinkName(command.getLinkInfo().getLinkName());
			linkInfo.setLinkMobile(command.getLinkInfo().getLinkMobile());
			linkInfo.setEmail(command.getLinkInfo().getEmail());
		this.setLinkInfo(linkInfo);
		
			//下单人信息
			LineOrderUser orderUser = new LineOrderUser();
			orderUser.setLoginName(command.getLineOrderUser().getLoginName());
			orderUser.setMobile(command.getLineOrderUser().getMobile());
			orderUser.setUserId(command.getLineOrderUser().getUserId());
	    this.setLineOrderUser(orderUser);
		

			//订单状态
	         status = new LineOrderStatus();
	         status.createOrderStatus();
		this.setStatus(status);
		this.setInsurancePrice(command.getInsurancePrice());
		this.setIsBuyInsurance(command.getIsBuyInsurance());
		
		
	}
}

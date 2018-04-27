package hsl.domain.model.xl.order;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;
import hsl.domain.model.xl.Line;
import hsl.domain.model.xl.salepolicy.SalePolicySnapshot;
import hsl.pojo.command.line.HslCreateLineOrderCommand;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import hsl.pojo.command.line.UpdateLineOrderPayInfoCommand;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.alibaba.fastjson.JSON;
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
@Table(name = M.TABLE_PREFIX_HSL_XL + "LINE_ORDER")
public class LineOrder extends BaseModel {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 联系人信息
	 */
	@Embedded
	private LineOrderLinkInfo linkInfo;

	/**
	 * 订单基本信息
	 */
	@Embedded
	private LineOrderBaseInfo baseInfo;
	/**
	 * 线路支付信息
	 */
	@Embedded
	private LineOrderPayInfo lineOrderPayInfo;
	/**
	 * 游客信息列表set
	 * */
	@OneToMany(mappedBy="lineOrder",cascade={CascadeType.ALL},fetch = FetchType.LAZY)
	private Set<LineOrderTraveler> travelers;
	/**
	 * 游客信息列表list
	 */
	@Transient
	private List<LineOrderTraveler> travelerList;
	
	/**
	 * 成人类销售政策快照
	 * 销售政策分为 0成人儿童都适用 1成人适用 2儿童适用 三类
	 * 该值为下单当前有效的0和1类政策中优先度最高的
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ADULT_SALE_POLICY_SNAPSHOT_ID")
	private SalePolicySnapshot adultSalePolicySnapshot;
	
	/**
	 * 儿童类销售政策快照
	 * 销售政策分为 0成人儿童都适用 1成人适用 2儿童适用 三类
	 * 该值为下单当前有效的0和2类政策中优先度最高的
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CHILD_SALE_POLICY_SNAPSHOT_ID")
	private SalePolicySnapshot childSalePolicySnapshot;
	
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
	public SalePolicySnapshot getAdultSalePolicySnapshot() {
		if (adultSalePolicySnapshot == null)
			adultSalePolicySnapshot = new SalePolicySnapshot();
		return adultSalePolicySnapshot;
	}

	public void setAdultSalePolicySnapshot(SalePolicySnapshot adultSalePolicySnapshot) {
		this.adultSalePolicySnapshot = adultSalePolicySnapshot;
	}

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

	public SalePolicySnapshot getChildSalePolicySnapshot() {
		if (childSalePolicySnapshot == null)
			childSalePolicySnapshot = new SalePolicySnapshot();
		return childSalePolicySnapshot;
	}

	public void setChildSalePolicySnapshot(SalePolicySnapshot childSalePolicySnapshot) {
		this.childSalePolicySnapshot = childSalePolicySnapshot;
	}
	
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

	public LineOrderPayInfo getLineOrderPayInfo() {
		if (lineOrderPayInfo == null)
			lineOrderPayInfo = new LineOrderPayInfo();
		return lineOrderPayInfo;
	}

	public void setLineOrderPayInfo(LineOrderPayInfo lineOrderPayInfo) {
		this.lineOrderPayInfo = lineOrderPayInfo;
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
	public void create(HslCreateLineOrderCommand command) {
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
			baseInfo.setSupplierAdultUnitPrice(command.getBaseInfo().getSupplierAdultUnitPrice());
			baseInfo.setSupplierUnitChildPrice(command.getBaseInfo().getSupplierUnitChildPrice());
			baseInfo.setSupplierPrice(command.getBaseInfo().getSupplierPrice());
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
		
	}
	/**
	 * 是否需要全款支付
	 *
	 * @author zhurz
	 * @return 是否需要全款支付
	 */
	public boolean needPayAll() {

		Line lineSnapshot = JSON.parseObject(getLineSnapshot().getAllInfoLineJSON(), Line.class);

		int payTotalDay = lineSnapshot.getPayInfo().getPayTotalDaysBeforeStart();
		Date payTotalDate = DateUtils.addDays(getBaseInfo().getCreateDate(), payTotalDay);
		Date travelDate = getBaseInfo().getTravelDate();

		if (DateUtils.truncatedCompareTo(payTotalDate, travelDate, Calendar.DATE) >= 0) {
			return true;
		} else if (getBaseInfo().getBargainMoney().equals(getBaseInfo().getSalePrice())) {
			return true;
		}

		return false;
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
			for (LineOrderTraveler traveler : getTravelers()) {
				if (Integer.valueOf(status).equals(traveler.getLineOrderStatus().getPayStatus()))
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
			for (LineOrderTraveler traveler : getTravelers()) {
				if (Integer.valueOf(status).equals(traveler.getLineOrderStatus().getOrderStatus()))
					return true;
			}
		}
		return false;
	}
	/**
	 * @方法功能说明:更新线路订单的支付信息
	 * @修改者名字：chenxy
	 * @修改时间 2015-11-18 14-41-37
	 * @修改内容：
	 * @param
	 * @return
	 * @throws
	 */
	public void updateLineOrderPayInfo(UpdateLineOrderPayInfoCommand command){
		lineOrderPayInfo=getLineOrderPayInfo();
		/**（如果存在支付订金和支付尾款的时候，支付交易号用&分割）*/
		lineOrderPayInfo.setBuyerEmail(StringUtils.isNotBlank(lineOrderPayInfo.getBuyerEmail()) ? lineOrderPayInfo.getBuyerEmail() + "&" + command.getBuyerEmail() : command.getBuyerEmail());
		lineOrderPayInfo.setPayTradeNo(StringUtils.isNotBlank(lineOrderPayInfo.getPayTradeNo()) ? lineOrderPayInfo.getPayTradeNo() + "&" + command.getPayTradeNo() : command.getPayTradeNo());
		lineOrderPayInfo.setCashPrice(lineOrderPayInfo.getCashPrice()!=null?lineOrderPayInfo.getCashPrice() + command.getCashPrice():command.getCashPrice());
	}

}

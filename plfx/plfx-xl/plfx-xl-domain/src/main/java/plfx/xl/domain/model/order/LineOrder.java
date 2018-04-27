package plfx.xl.domain.model.order;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import plfx.xl.domain.model.M;
import plfx.xl.domain.model.line.LineSnapshot;
import plfx.xl.domain.model.salepolicy.SalePolicySnapshot;
import plfx.xl.pojo.command.order.CreateLineOrderCommand;
//import slfx.xl.domain.model.line.LineSnapshot;
//import slfx.xl.domain.model.salepolicy.SalePolicySnapshot;

/**
 * 
 * @类功能说明：线路订单
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年11月26日下午2:24:28
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX + "LINE_ORDER")
public class LineOrder extends BaseModel {
	
	/**
	 * 新增线路订单
	 * @param command
	 */
	public void createLineOrder(CreateLineOrderCommand command,
			SalePolicySnapshot adultSalePolicySnapshot,
			SalePolicySnapshot childSalePolicySnapshot,
			LineSnapshot lineSnapshot){
		//id
		this.setId(UUIDGenerator.getUUID());
		
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
			baseInfo.setOrderNo(command.getLineDealerID() + command.getBaseInfo().getDealerOrderNo());
		this.setBaseInfo(baseInfo);
		
			LineOrderLinkInfo linkInfo = new LineOrderLinkInfo();
			linkInfo.setLinkName(command.getLinkInfo().getLinkName());
			linkInfo.setLinkMobile(command.getLinkInfo().getLinkMobile());
		this.setLinkInfo(linkInfo);

		this.setAdultSalePolicySnapshot(adultSalePolicySnapshot);
		
		this.setChildSalePolicySnapshot(childSalePolicySnapshot);
		
			/*LineSnapshot lineSnapshot = new LineSnapshot();
			lineSnapshot.setId(UUIDGenerator.getUUID());
			lineSnapshot.setLine(line);
			lineSnapshot.setLineName(line.getBaseInfo().getName());
			lineSnapshot.setType(line.getBaseInfo().getType());
			lineSnapshot.setStarting(line.getBaseInfo().getStarting());
			lineSnapshot.setAllInfoLineJSON(JSON.toJSONString(line));
		this.setLineSnapshot(lineSnapshot);*/
		this.setLineSnapshot(lineSnapshot);
	}

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
	@OneToMany(fetch=FetchType.LAZY, mappedBy="lineOrder" ,cascade={CascadeType.ALL})
	private Set<LineOrderTraveler> travelers;
	 
	
	/**
	 * 游客信息列表list
	 
	@Transient
	private List<LineOrderTraveler> travelerList;
	*/
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
	
	

	public LineOrderBaseInfo getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(LineOrderBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public LineOrderLinkInfo getLinkInfo() {
		return linkInfo;
	}

	public void setLinkInfo(LineOrderLinkInfo linkInfo) {
		this.linkInfo = linkInfo;
	}
	
	public Set<LineOrderTraveler> getTravelers() {
//		Hibernate.initialize(travelers);
		return travelers;
	}

	public void setTravelers(Set<LineOrderTraveler> travelers) {
		this.travelers = travelers;
	}

	/*public List<LineOrderTraveler> getTravelerList() {
		return travelerList;
	}

	public void setTravelerList(List<LineOrderTraveler> travelerList) {
		this.travelerList = travelerList;
	}*/

	public SalePolicySnapshot getAdultSalePolicySnapshot() {
		return adultSalePolicySnapshot;
	}

	public void setAdultSalePolicySnapshot(
			SalePolicySnapshot adultSalePolicySnapshot) {
		this.adultSalePolicySnapshot = adultSalePolicySnapshot;
	}

	public SalePolicySnapshot getChildSalePolicySnapshot() {
		return childSalePolicySnapshot;
	}

	public void setChildSalePolicySnapshot(
			SalePolicySnapshot childSalePolicySnapshot) {
		this.childSalePolicySnapshot = childSalePolicySnapshot;
	}

	public LineSnapshot getLineSnapshot() {
		return lineSnapshot;
	}

	public void setLineSnapshot(LineSnapshot lineSnapshot) {
		this.lineSnapshot = lineSnapshot;
	}

}
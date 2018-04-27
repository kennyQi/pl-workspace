package hg.dzpw.pojo.command.platform.salepolicy;

import hg.dzpw.pojo.common.DZPWPlatformBaseCommand;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @类功能说明：创建销售策略
 * @类修改者：
 * @修改日期：2014-11-12上午10:43:37
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzx
 * @创建时间：2014-11-12上午10:43:37
 */
@Deprecated
public class PlatformModifySalePolicyCommand extends DZPWPlatformBaseCommand {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 政策编号
	 */
	private String id;
	
	/**
	 * 政策名称
	 */
	private String name;
	
	/**
	 * 适用联票
	 */
	private List<String> ticketPoliciyIds;
	
	/**
	 * 适用经销商
	 */
	private List<String> dealerIds;
	
	/**
	 * 有效开始时间
	 */
	private Date startDate;

	/**
	 * 有效结束时间
	 */
	private Date endDate;
	
	/**
	 * 是否上调
	 */
	private Boolean rise;
	
	/**
	 * 计算单位：1、元 2、百分比
	 */
	private int unit;
	
	/**
	 * 调整数目
	 */
	private Double amount;
	
	/**
	 * 政策描述
	 */
	private String description;
	
	public Boolean getRise() {
		return rise;
	}
	public void setRise(Boolean rise) {
		this.rise = rise;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}
	public List<String> getTicketPoliciyIds() {
		return ticketPoliciyIds;
	}
	public void setTicketPoliciyIds(List<String> ticketPoliciyIds) {
		this.ticketPoliciyIds = (null == ticketPoliciyIds || ticketPoliciyIds.size() < 1)?null:ticketPoliciyIds;
	}
	public void setTicketPoliciyIdStr(String ticketPoliciyIdStr) {
		if(null != ticketPoliciyIdStr && !"".equals(ticketPoliciyIdStr.trim()))
			this.ticketPoliciyIds = Arrays.asList(ticketPoliciyIdStr.split(","));
	}
	public List<String> getDealerIds() {
		return dealerIds;
	}
	public void setDealerIds(List<String> dealerIds) {
		this.dealerIds = (null == dealerIds || dealerIds.size() < 1)?null:dealerIds;
	}
	public void setDealerIdStr(String dealerIdStr) {
		if(null != dealerIdStr && !"".equals(dealerIdStr.trim()))
			this.dealerIds = Arrays.asList(dealerIdStr.split(","));
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
//		if(null != startDate)
//			startDate=  DateUtils.truncate(startDate,Calendar.DAY_OF_MONTH);
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
//		if(null != endDate)
//			endDate = DateUtils.truncate(endDate,Calendar.DAY_OF_MONTH);
		this.endDate = endDate;
	}
	public int getUnit() {
		return unit;
	}
	public void setUnit(int unit) {
		this.unit = unit;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}
}
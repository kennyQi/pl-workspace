package hg.dzpw.app.pojo.qo;

import hg.common.component.BaseQo;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

/**
 * @类功能说明: 联票QO
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014-11-11 下午5:10:11
 * @版本：V1.0
 */
public class TicketQo extends BaseQo {
	private static final long serialVersionUID = 1L;

	/**
	 * 票号
	 */
	private String ticketNo;
	private boolean ticketNoLike;

	/**
	 * 初次入园时间
	 */
	private Date firstTimeUseDate;

	/**
	 * 有效期开始时间
	 */
	private Date useDateStart;

	/**
	 * 有效期结束时间
	 */
	private Date useDateEnd;
	
	/**
	 * 有效期结束时间等于
	 */
	private Date eqUseDateEnd;

	/**
	 * 门票政策
	 */
	private TicketPolicyQo policyQo;

	/**
	 * 游客
	 */
	private TouristQo tourQo;
	
	/**
	 * ticket快照
	 */
	private TicketPolicySnapshotQo ticketPolicySnapshotQo;

	/**
	 * 实际价格<br/>
	 * 比例=单票基准价/套票基准价 <br/>
	 * 套票实价=套票基准价+调价政策 <br/>
	 * 单票实价=套票实价*比例<br/>
	 */
	private Double price;

	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo == null ? null : ticketNo.trim();
	}
	public Date getFirstTimeUseDate() {
		return firstTimeUseDate;
	}
	public void setFirstTimeUseDate(Date firstTimeUseDate) {
		this.firstTimeUseDate = firstTimeUseDate;
	}
	public Date getUseDateStart() {
		return useDateStart;
	}
	public void setUseDateStart(Date useDateStart) {
		if(null != useDateStart)
			useDateStart=  DateUtils.truncate(useDateStart,Calendar.DAY_OF_MONTH);
		this.useDateStart = useDateStart;
	}
	public Date getUseDateEnd() {
		return useDateEnd;
	}
	public void setUseDateEnd(Date useDateEnd) {
		if(null != useDateEnd)
			useDateEnd=  DateUtils.addSeconds(DateUtils.ceiling(useDateEnd,Calendar.DAY_OF_MONTH),-1);
		this.useDateEnd = useDateEnd;
	}
	public TicketPolicyQo getPolicyQo() {
		return policyQo;
	}
	public void setPolicyQo(TicketPolicyQo policyQo) {
		this.policyQo = policyQo;
	}
	public TouristQo getTourQo() {
		return tourQo;
	}
	public void setTourQo(TouristQo tourQo) {
		this.tourQo = tourQo;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public boolean isTicketNoLike() {
		return ticketNoLike;
	}
	public void setTicketNoLike(boolean ticketNoLike) {
		this.ticketNoLike = ticketNoLike;
	}
	public TicketPolicySnapshotQo getTicketPolicySnapshotQo() {
		return ticketPolicySnapshotQo;
	}
	public void setTicketPolicySnapshotQo(
			TicketPolicySnapshotQo ticketPolicySnapshotQo) {
		this.ticketPolicySnapshotQo = ticketPolicySnapshotQo;
	}
	public Date getEqUseDateEnd() {
		return eqUseDateEnd;
	}
	public void setEqUseDateEnd(Date eqUseDateEnd) {
		this.eqUseDateEnd = eqUseDateEnd;
	}
}
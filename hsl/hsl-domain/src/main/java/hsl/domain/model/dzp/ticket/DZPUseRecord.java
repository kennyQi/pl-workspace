package hsl.domain.model.dzp.ticket;

import hg.common.component.BaseModel;
import hsl.domain.model.M;

import javax.persistence.*;
import java.util.Date;

/**
 * 电子票使用记录
 *
 * @author zhurz
 * @since 1.8
 */
@Entity
@SuppressWarnings("serial")
@Table(name = M.TABLE_PREFIX_HSL_DZP + "USE_RECORD")
public class DZPUseRecord extends BaseModel {
	
	/**
	 * 门票票号（冗余）
	 */
	@Column(name = "TICKET_NO", length = 64)
	private String ticketNo;

	/**
	 * 门票使用时间
	 */
	@Column(name = "USE_DATE", columnDefinition = M.DATE_COLUM)
	private Date useDate;

	/**
	 * 所属门票
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GROUP_TICKET_ID")
	private DZPGroupTicket fromGroupTicket;

	/**
	 * 所属单票
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SINGLE_TICKET_ID")
	private DZPSingleTicket fromSingleTicket;

	/**
	 * 所属景区（冗余）
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCENIC_SPOT_ID")
	private DZPSingleTicket fromScenicSpot;

	/**
	 * 景区名称（冗余）
	 */
	@Column(name = "SCENIC_NAME", length = 128)
	private String scenicName;

	/**
	 * 入园核销方式
	 * <pre>
	 * 1、扫描身份证
	 * 2、填写证件号
	 * 3、扫描二维码
	 * </pre>
	 */
	@Column(name = "CHECK_TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer checkType;

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public Date getUseDate() {
		return useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}

	public DZPGroupTicket getFromGroupTicket() {
		return fromGroupTicket;
	}

	public void setFromGroupTicket(DZPGroupTicket fromGroupTicket) {
		this.fromGroupTicket = fromGroupTicket;
	}

	public DZPSingleTicket getFromSingleTicket() {
		return fromSingleTicket;
	}

	public void setFromSingleTicket(DZPSingleTicket fromSingleTicket) {
		this.fromSingleTicket = fromSingleTicket;
	}

	public DZPSingleTicket getFromScenicSpot() {
		return fromScenicSpot;
	}

	public void setFromScenicSpot(DZPSingleTicket fromScenicSpot) {
		this.fromScenicSpot = fromScenicSpot;
	}

	public String getScenicName() {
		return scenicName;
	}

	public void setScenicName(String scenicName) {
		this.scenicName = scenicName;
	}

	public Integer getCheckType() {
		return checkType;
	}

	public void setCheckType(Integer checkType) {
		this.checkType = checkType;
	}
}

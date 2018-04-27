package hg.dzpw.domain.model.ticket;

import hg.common.component.BaseModel;
import hg.dzpw.domain.model.M;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @类功能说明：入园记录
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2014-11-10下午2:23:41
 * @版本：V1.0
 */
@Entity
@Table(name = M.TABLE_PREFIX + "USE_RECORD")
public class UseRecord extends BaseModel {
	private static final long serialVersionUID = 1L;
	/**
	 * 门票使用时间
	 */
	@Column(name = "USE_DATE", columnDefinition = M.DATE_COLUM)
	private Date useDate;
	/**
	 * 单票标识
	 */
	@Column(name = "SINGLE_TICKET_ID", length = 64)
	private String singleTicketId;
	/**
	 * 套票标识
	 */
	@Column(name = "GROUP_TICKET_ID", length = 64)
	private String groupTicketId;
	/**
	 * 景区标识
	 */
	@Column(name = "SCENIC_SPOT_ID", length = 64)
	private String scenicSpotId;
	/**
	 * 入园核销方式 1、扫描身份证 2、填写证件号 3、扫描二维码
	 */
	@Column(name = "CHECK_TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private int checkType;


	public Date getUseDate() {
		return useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}

	public String getSingleTicketId() {
		return singleTicketId;
	}

	public void setSingleTicketId(String singleTicketId) {
		this.singleTicketId = singleTicketId;
	}

	public String getGroupTicketId() {
		return groupTicketId;
	}

	public void setGroupTicketId(String groupTicketId) {
		this.groupTicketId = groupTicketId;
	}

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}

	public int getCheckType() {
		return checkType;
	}

	public void setCheckType(int checkType) {
		this.checkType = checkType;
	}

}
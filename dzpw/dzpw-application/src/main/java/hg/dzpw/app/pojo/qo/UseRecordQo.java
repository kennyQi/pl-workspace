package hg.dzpw.app.pojo.qo;

import java.util.Date;

import hg.common.component.BaseQo;

/**
 * @类功能说明：入园记录QO
 * @类修改者：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2014-11-27下午3:57:11
 * @版本：V1.0
 */
@SuppressWarnings("serial")
public class UseRecordQo extends BaseQo{
	
	/**
	 * 所属单票
	 */
	private String singleTicketId;
	
	/**
	 * 所属门票
	 */
	private String groupTicketId;
	
	/**
	 * 查询开始时间
	 */
	private Date useDateStart;
	
	/**
	 * 查询结束时间
	 */
	private Date useDateEnd;

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

	public Date getUseDateStart() {
		return useDateStart;
	}

	public void setUseDateStart(Date useDateStart) {
		this.useDateStart = useDateStart;
	}

	public Date getUseDateEnd() {
		return useDateEnd;
	}

	public void setUseDateEnd(Date useDateEnd) {
		this.useDateEnd = useDateEnd;
	}
	
}
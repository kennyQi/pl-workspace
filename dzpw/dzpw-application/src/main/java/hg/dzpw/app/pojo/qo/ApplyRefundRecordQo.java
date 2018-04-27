package hg.dzpw.app.pojo.qo;

import java.util.Date;

import hg.common.annotation.QOAttr;
import hg.common.annotation.QOAttrType;
import hg.common.component.BaseQo;
/**
 * 
 * @类功能说明：退款申请记录查询对象
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：guotx
 * @创建时间：2016-3-17下午2:20:26
 * @版本：
 */
public class ApplyRefundRecordQo extends BaseQo {

	private static final long serialVersionUID = 1L;

	private GroupTicketQo groupTicketQo ;

	private TicketOrderQo ticketOrderQo;

	@QOAttr(name="recordDate",type=QOAttrType.GE)
	private Date recordDateBegin;

	@QOAttr(name="recordDate",type=QOAttrType.LE)
	private Date recordDateEnd;

	public Date getRecordDateBegin() {
		return recordDateBegin;
	}

	public void setRecordDateBegin(Date recordDateBegin) {
		this.recordDateBegin = recordDateBegin;
	}

	public Date getRecordDateEnd() {
		return recordDateEnd;
	}

	public void setRecordDateEnd(Date recordDateEnd) {
		this.recordDateEnd = recordDateEnd;
	}

	public GroupTicketQo getGroupTicketQo() {
		return groupTicketQo;
	}

	public void setGroupTicketQo(GroupTicketQo groupTicketQo) {
		this.groupTicketQo = groupTicketQo;
	}

	public TicketOrderQo getTicketOrderQo() {
		return ticketOrderQo;
	}

	public void setTicketOrderQo(TicketOrderQo ticketOrderQo) {
		this.ticketOrderQo = ticketOrderQo;
	}

}

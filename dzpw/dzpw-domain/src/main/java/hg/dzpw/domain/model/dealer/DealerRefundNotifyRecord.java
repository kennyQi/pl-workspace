package hg.dzpw.domain.model.dealer;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @类功能说明：暂存到redis中的经销商退款通知信息对象，不存到数据库
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：guotx
 * @创建时间：2016-3-24上午9:48:10
 * @版本：
 */
public class DealerRefundNotifyRecord implements Serializable {
	    /** 
	     * 
	     * @since Ver 1.1 
	     */ 
	    
	private static final long serialVersionUID = 8876047834915940896L;
	
	/** 需要通知的经销商key */
	private String dealerKey;
	/** 退款成功的票号 */
	private String ticketNo;
	/** 下次通知开始时间，只要过了该时间就发送 */
	private Date notifyDate;
	/** 已经通知的次数，初始为0，最大为2，通知三次，最后一次通知后即删除该记录 */
	private Integer hasNotifyNum;

	public String getDealerKey() {
		return dealerKey;
	}

	public void setDealerKey(String dealerKey) {
		this.dealerKey = dealerKey;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public Date getNotifyDate() {
		return notifyDate;
	}

	public void setNotifyDate(Date notifyDate) {
		this.notifyDate = notifyDate;
	}

	public Integer getHasNotifyNum() {
		return hasNotifyNum;
	}

	public void setHasNotifyNum(Integer hasNotifyNum) {
		this.hasNotifyNum = hasNotifyNum;
	}

}

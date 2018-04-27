package hg.dzpw.dealer.client.dto.order;

import hg.dzpw.dealer.client.common.EmbeddDTO;

import java.util.Date;

/**
 * @类功能说明：订单基本信息
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：zzx
 * @创建时间：2014-11-10下午3:02:03
 * @版本：V1.0
 */
@SuppressWarnings("serial")
public class TicketOrderBaseInfoDTO extends EmbeddDTO {
	
	/**
	 * 门票数量
	 */
	private int ticketNo;

	/**
	 * 下单时间
	 */
	private Date createDate;
	
	/**
	 * 经销商订单编号
	 */
	private String dealerOrderId;
	/**
	 * 经销商名称
	 */
	private String dealerName;
	
	/**
	 * 经销商Id
	 */
	private String dealerId;
	/**
	 * 联系人
	 */
	private String linkMan;
	
	/**
	 * 联系电话
	 */
	private String telephone;

	public int getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(int ticketNo) {
		this.ticketNo = ticketNo;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

}
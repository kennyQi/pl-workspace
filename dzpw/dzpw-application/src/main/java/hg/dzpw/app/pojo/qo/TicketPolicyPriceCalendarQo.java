package hg.dzpw.app.pojo.qo;

import hg.common.component.BaseQo;

/**
 * @类功能说明：门票价格日历查询对象
 * @类修改者：
 * @修改日期：2015-3-5下午2:07:45
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-3-5下午2:07:45
 */
public class TicketPolicyPriceCalendarQo extends BaseQo {
	private static final long serialVersionUID = 1L;

	/**
	 * 所属门票政策（价格日历只和单票政策关联）
	 */
	private TicketPolicyQo ticketPolicyQo;

	/**
	 * 是否为门票基准价，与经销商订的价格都为false
	 */
	private Boolean standardPrice;

	/**
	 * 对应经销商
	 */
	private DealerQo dealerQo;
	
	/**
	 * 是否查询经销商
	 */
	private Boolean dealerFetch = false;

	public TicketPolicyQo getTicketPolicyQo() {
		return ticketPolicyQo;
	}

	public void setTicketPolicyQo(TicketPolicyQo ticketPolicyQo) {
		this.ticketPolicyQo = ticketPolicyQo;
	}

	public Boolean getStandardPrice() {
		return standardPrice;
	}

	public void setStandardPrice(Boolean standardPrice) {
		this.standardPrice = standardPrice;
	}

	public DealerQo getDealerQo() {
		return dealerQo;
	}

	public void setDealerQo(DealerQo dealerQo) {
		this.dealerQo = dealerQo;
	}

	public Boolean getDealerFetch() {
		return dealerFetch;
	}

	public void setDealerFetch(Boolean dealerFetch) {
		this.dealerFetch = dealerFetch;
	}

}

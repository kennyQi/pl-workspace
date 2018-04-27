package hg.dzpw.dealer.client.api.v1.request;

import hg.dzpw.dealer.client.common.BaseClientQO;
import hg.dzpw.dealer.client.common.api.DealerApiAction;

/**
 * @类功能说明：入园记录查询
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2015-5-25上午10:10:44
 * @版本：V1.0
 */
@SuppressWarnings("serial")
@DealerApiAction(DealerApiAction.QueryUseRecord)
public class UseRecordQO extends BaseClientQO{
	
	
	/**
	 * 电子票务票号
	 */
	private String ticketNo;

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}
	
}

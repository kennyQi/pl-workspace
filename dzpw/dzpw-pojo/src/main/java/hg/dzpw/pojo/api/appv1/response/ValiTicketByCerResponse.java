package hg.dzpw.pojo.api.appv1.response;

import hg.dzpw.pojo.api.appv1.base.ApiResponse;
import hg.dzpw.pojo.api.appv1.dto.TicketDto;

import java.util.List;

/**
 * @类功能说明： 身份证件验票结果
 * @类修改者：
 * @修改日期：2014-11-18下午3:37:20
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-18下午3:37:20
 */
public class ValiTicketByCerResponse extends ApiResponse {
	private static final long serialVersionUID = 1L;
	
	/** -1：查不到购票记录(票号不存在) */
	public final static String RESULT_NOT_FOUND = "-1";
	/** -2：门票不可用(状态异常等情况) */
	public final static String RESULT_TICKET_NOT_USEABLE = "-2";
	/** -3：门票有效期错误  
	 *  此时返回有效期格式  2015.09.02-2015.10.10 */
	public final static String RESULT_TICKET_OVERDUE = "-3";
	/** -4：超过景区当天入园次数限制 */
	public final static String RESULT_SCENIC_SPOT_CHECK_IN_TIMES = "-4";
	/** -5：非法票号 */
	public final static String RESULT_TICKET_NO_FOMART_ERROR = "-5";
	

	private List<TicketDto> tickets ;

	public List<TicketDto> getTickets() {
		return tickets;
	}
	public void setTickets(List<TicketDto> tickets) {
		this.tickets = tickets;
	}
}
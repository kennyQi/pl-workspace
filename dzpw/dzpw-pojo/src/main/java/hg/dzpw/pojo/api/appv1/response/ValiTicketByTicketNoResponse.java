package hg.dzpw.pojo.api.appv1.response;

import hg.dzpw.pojo.api.appv1.base.ApiResponse;
import hg.dzpw.pojo.api.appv1.dto.TicketDto;


/**
 * @类功能说明： 票号验票结果
 * @类修改者：
 * @修改日期：2014-11-18下午3:44:17
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-18下午3:44:17
 */
public class ValiTicketByTicketNoResponse extends ApiResponse {
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
	
	
//	/** -2：门票已使用：已经超过可游玩的日期 */
//	public final static String RESULT_SINGLE_TICKET_OUTOFDATE = "-2";
//	/** -3：联票有效期错误：未到使用期和过期 */
//	public final static String RESULT_GROUP_TICKET_OUTOFDATE = "-3";
//	/** 单票超出可游玩次数 */
//	public final static String RESULT_SINGLE_TICKET_OUTOF_TOTAL_USETIME = "-4";
//	/** 超出当天可游玩次数*/
//	public final static String RESULT_SINGLE_TICKET_OUTOF_CURRENTDAY_USETIME = "-5";
	
	/**
	 * 查询结果
	 */
	private TicketDto ticket;

	public ValiTicketByTicketNoResponse() {
	}

	public ValiTicketByTicketNoResponse(String result, String message) {
		super(result, message);
	}
	
	public ValiTicketByTicketNoResponse(String result, String message, TicketDto ticket) {
		super(result, message);
		this.ticket = ticket;
	}
	
	/**
	 * @方法功能说明：其他异常
	 * @修改者名字：zhurz
	 * @修改时间：2014-12-4上午10:27:56
	 * @参数：@param msg
	 * @参数：@return
	 * @return:ApiResponse
	 */
	public static ValiTicketByTicketNoResponse error(String msg) {
		return new ValiTicketByTicketNoResponse(RESULT_ERROR, msg);
	}

	/**
	 * @方法功能说明：-1：查不到购票记录(全部)
	 * @修改者名字：zhurz
	 * @修改时间：2014-12-9下午3:39:33
	 * @参数：@param ticketNo
	 * @参数：@return
	 * @return:ValiTicketByTicketNoResponse
	 */
	public static ValiTicketByTicketNoResponse notFound() {
		return new ValiTicketByTicketNoResponse(RESULT_NOT_FOUND, "查不到该票记录");
	}
	
	
	/**
	 * @方法功能说明：-2： 超过景区当天入园次数限制
	 * @修改者名字：yangkang
	 * @修改时间：2015-12-24下午1:55:12
	 * @参数：@return
	 * @return:ValiTicketByTicketNoResponse
	 */
	public static ValiTicketByTicketNoResponse checkInTimes(){
		return new ValiTicketByTicketNoResponse(RESULT_SCENIC_SPOT_CHECK_IN_TIMES, "超过景区当天入园次数限制");
	}
	
	
	

	/**
	 * @方法功能说明：-4:门票不可用
	 * @修改者名字：yangkang
	 * @修改时间：2015-12-23下午5:12:17
	 * @参数：@return
	 * @return:ValiTicketByTicketNoResponse
	 */
	public static ValiTicketByTicketNoResponse notUseable() {
		return new ValiTicketByTicketNoResponse(RESULT_TICKET_NOT_USEABLE, "该票不可用或未关联游客信息");
	}
	
	/**
	 * @方法功能说明：-5：门票有效期错误
	 * @修改者名字：yangkang
	 * @修改时间：2015-12-24下午1:21:10
	 * @参数：@param msg返回有效期范围
	 * @参数：@return
	 * @return:ValiTicketByTicketNoResponse
	 */
	public static ValiTicketByTicketNoResponse overDue(String msg) {
		return new ValiTicketByTicketNoResponse(RESULT_TICKET_OVERDUE, msg);
	}
	
	
	public TicketDto getTicket() {
		return ticket;
	}

	public void setTicket(TicketDto ticket) {
		this.ticket = ticket;
	}

}
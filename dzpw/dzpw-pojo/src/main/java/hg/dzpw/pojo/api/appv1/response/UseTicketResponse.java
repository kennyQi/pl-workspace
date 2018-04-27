package hg.dzpw.pojo.api.appv1.response;

import hg.dzpw.pojo.api.appv1.base.ApiResponse;

/**
 * @类功能说明：确认核销门票结果
 * @类修改者：
 * @修改日期：2014-11-18下午4:07:55
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-18下午4:07:55
 */
public class UseTicketResponse extends ApiResponse {
	private static final long serialVersionUID = 1L;

	/** -1：查不到购票记录 */
	public final static String RESULT_NOT_FOUND = "-1";
	/** -2门票不可用 **/
	public final static String RESULT_TICKET_NOTUSEABLE = "-2";
	/** -3：未到使用期和过期 */
	public final static String RESULT_TICKET_OUTOFDATE = "-3";
	/** 单票单日入园限制 */
	public final static String RESULT_TICKET_DAYTIME_LIMIT = "-4";
	/** 单票入园总次数限制 */
	public final static String RESULT_SINGLE_TICKET_TOTAL_LIMIT = "-6";
	
	
}
package hg.dzpw.pojo.api.appv1.request;

import hg.dzpw.pojo.api.appv1.base.ApiRequestBody;

/**
 * @类功能说明：确认核销门票（当开启人工核查身份证件时，用该接口确认核销门票。）
 * @类修改者：
 * @修改日期：2014-11-18下午3:23:24
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-18下午3:23:24
 */
public class UseTicketRequestBody extends ApiRequestBody {
	private static final long serialVersionUID = 1L;

	/**
	 * 票号
	 */
	private String ticketNo;
	
	private String checkWay;
	
	/**
	 * 景区ID
	 */
	private String scenicSpotId;

	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo == null ? null : ticketNo.trim();
	}
	public String getCheckWay() {
		return checkWay;
	}
	public void setCheckWay(String checkWay) {
		this.checkWay = checkWay;
	}
	public String getScenicSpotId() {
		return scenicSpotId;
	}
	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}
}
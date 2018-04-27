package hg.dzpw.pojo.api.appv1.request;

import hg.dzpw.pojo.api.appv1.base.ApiRequestBody;

/**
 * @类功能说明：票号验票（根据票号验票，票号可以由二维码解码得到，如果未开启人工核查证件，则直接核销唯一可用门票，否则仅返回门票信息。）
 * @类修改者：
 * @修改日期：2014-11-18下午3:21:14
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-18下午3:21:14
 */
public class ValiTicketByTicketNoRequestBody extends ApiRequestBody {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 票号
	 */
	private String ticketNo;
	
	/**
	 * 景区ID
	 */
	private String scenicSpotId;
	
	public String getScenicSpotId() {
		return scenicSpotId;
	}
	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}
	public String getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo == null ? null : ticketNo.trim();
	}
}
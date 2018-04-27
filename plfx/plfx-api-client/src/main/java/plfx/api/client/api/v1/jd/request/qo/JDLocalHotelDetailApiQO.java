package plfx.api.client.api.v1.jd.request.qo;

import plfx.api.client.base.slfx.ApiPayload;
/****
 * 
 * @类功能说明：本地酒店查询QO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年7月20日下午3:45:24
 * @版本：V1.0
 *
 */
public class JDLocalHotelDetailApiQO extends ApiPayload{
	
	/****
	 * 酒店ID
	 */
	private String hotelId;

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}
	
	

}

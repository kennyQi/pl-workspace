package plfx.api.client.api.v1.jd.response;

import plfx.api.client.base.slfx.ApiResponse;
import plfx.jd.pojo.dto.plfx.hotel.YLHotelDTO;
/*****
 * 
 * @类功能说明：本地酒店返回Response
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年7月20日下午4:02:51
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class JDLocalHotelDetailResponse extends ApiResponse {

	/***
	 * 本地酒店dto
	 */
	private YLHotelDTO ylHotelDTO;

	public YLHotelDTO getYlHotelDTO() {
		return ylHotelDTO;
	}

	public void setYlHotelDTO(YLHotelDTO ylHotelDTO) {
		this.ylHotelDTO = ylHotelDTO;
	}
	
	
}

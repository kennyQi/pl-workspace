package plfx.api.client.api.v1.jd.response;

import plfx.api.client.base.slfx.ApiResponse;
import plfx.jd.pojo.dto.ylclient.hotel.HotelListResultDTO;

/**
 * 
 * @类功能说明：机票出票结果RESPONSE
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年8月1日下午4:42:42
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class JDHotelListResponse extends ApiResponse {

	private HotelListResultDTO hotelListResultDTO;

	public final static Integer LINKER_MISSING = -1001001; // 缺少联系人信息


	public HotelListResultDTO getHotelListResultDTO() {
		return hotelListResultDTO;
	}


	public void setHotelListResultDTO(HotelListResultDTO hotelListResultDTO) {
		this.hotelListResultDTO = hotelListResultDTO;
	}


	public static Integer getLinkerMissing() {
		return LINKER_MISSING;
	}

}

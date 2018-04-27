package zzpl.api.client.response.jp;

import java.util.List;

import zzpl.api.client.base.ApiResponse;
import zzpl.api.client.dto.jp.GJFlightListDTO;

/***
 * 
 * @类功能说明：查询航班RESPONSE
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年6月23日上午10:36:35
 * @版本：V1.0
 * 
 */
public class GJFlightResponse extends ApiResponse {

	/**
	 * 航班列表
	 */
	private List<GJFlightListDTO> flightGNListDTOs;

	public List<GJFlightListDTO> getFlightGNListDTOs() {
		return flightGNListDTOs;
	}

	public void setFlightGNListDTOs(List<GJFlightListDTO> flightGNListDTOs) {
		this.flightGNListDTOs = flightGNListDTOs;
	}

	
}

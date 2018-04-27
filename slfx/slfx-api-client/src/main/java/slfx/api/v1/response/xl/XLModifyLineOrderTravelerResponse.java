package slfx.api.v1.response.xl;

import slfx.api.base.ApiResponse;
import slfx.xl.pojo.dto.order.LineOrderDTO;

/**
 * 
 * @类功能说明：api接口修改线路订单游玩人RESPONSE
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年4月15日上午8:58:02
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class XLModifyLineOrderTravelerResponse extends ApiResponse{

	private LineOrderDTO lineOrderDTO;

	public LineOrderDTO getLineOrderDTO() {
		return lineOrderDTO;
	}

	public void setLineOrderDTO(LineOrderDTO lineOrderDTO) {
		this.lineOrderDTO = lineOrderDTO;
	}
}

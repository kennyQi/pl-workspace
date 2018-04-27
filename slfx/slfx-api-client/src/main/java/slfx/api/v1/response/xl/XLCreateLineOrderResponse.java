package slfx.api.v1.response.xl;

import slfx.api.base.ApiResponse;
import slfx.xl.pojo.dto.order.LineOrderDTO;

/**
 * 
 * @类功能说明：api接口创建线路订单RESPONSE
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月22日下午4:44:50
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class XLCreateLineOrderResponse extends ApiResponse {
	
	/**
	 * 创建订单订单成功后，返回信息
	 */
	private LineOrderDTO lineOrderDTO;

	public LineOrderDTO getLineOrderDTO() {
		return lineOrderDTO;
	}

	public void setLineOrderDTO(LineOrderDTO lineOrderDTO) {
		this.lineOrderDTO = lineOrderDTO;
	}

}

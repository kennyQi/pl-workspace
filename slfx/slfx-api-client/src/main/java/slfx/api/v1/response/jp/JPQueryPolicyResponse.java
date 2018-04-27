package slfx.api.v1.response.jp;

import slfx.api.base.ApiResponse;
import slfx.jp.pojo.dto.flight.SlfxFlightPolicyDTO;

/**
 * 
 * @类功能说明：下单前查政策RESPONSE
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年8月1日下午4:45:11
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class JPQueryPolicyResponse extends ApiResponse {

	/**
	 * 政策信息
	 */
	private SlfxFlightPolicyDTO slfxFlightPolicyDTO;

	public SlfxFlightPolicyDTO getSlfxFlightPolicyDTO() {
		return slfxFlightPolicyDTO;
	}

	public void setSlfxFlightPolicyDTO(SlfxFlightPolicyDTO slfxFlightPolicyDTO) {
		this.slfxFlightPolicyDTO = slfxFlightPolicyDTO;
	}

	
}

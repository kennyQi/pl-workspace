package plfx.api.client.api.v1.jd.response;

import plfx.api.client.base.slfx.ApiResponse;
import plfx.jd.pojo.dto.ylclient.order.ValidateCreditCardNoResultDTO;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江票量云数据科技股份有限公司
 * @部门：技术部
 * @作者：renfeng
 * @创建时间：2015年7月14日下午5:39:04
 * @版本：V1.1
 *
 */
@SuppressWarnings("serial")
public class JDValidateCreditCardResponse extends ApiResponse {
	private ValidateCreditCardNoResultDTO resultDto;

	public ValidateCreditCardNoResultDTO getResultDto() {
		return resultDto;
	}

	public void setResultDto(ValidateCreditCardNoResultDTO resultDto) {
		this.resultDto = resultDto;
	}
	
	
	
	
}

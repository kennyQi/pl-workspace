package plfx.yl.ylclient.yl.dto;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江票量云数据科技股份有限公司
 * @部门：技术部
 * @作者：renfeng
 * @创建时间：2015年7月17日上午8:57:59
 * @版本：V1.1
 *
 */
public class ValidateCreditCardNoResultDTO extends BaseResultDTO{
	
	@JSONField(name="Result")
	private CheckCreditCardNoDTO result;

	public CheckCreditCardNoDTO getResult() {
		return result;
	}

	public void setResult(CheckCreditCardNoDTO result) {
		this.result = result;
	}
	
	
	
	
}

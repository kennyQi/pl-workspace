package plfx.jp.pojo.dto.sms;

import plfx.jp.pojo.dto.BaseJpDTO;

/**
 * 
 * @类功能说明：短信DTO
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年12月25日下午2:29:56
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class SmsDTO extends BaseJpDTO{
	/**
	 * 返回代码
	 * 01：缺少用户名密码
	 * 02：用户名密码错误
	 * 03：非法请求
	 * succ：请求成功
	 */
	private String resultCode;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
}

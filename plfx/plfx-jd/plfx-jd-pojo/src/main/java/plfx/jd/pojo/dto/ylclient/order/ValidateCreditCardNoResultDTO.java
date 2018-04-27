package plfx.jd.pojo.dto.ylclient.order;

import java.io.Serializable;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江票量云数据科技股份有限公司
 * @部门：技术部
 * @作者：renfeng
 * @创建时间：2015年7月14日下午5:19:05
 * @版本：V1.1
 *
 */
@SuppressWarnings("serial")
public class ValidateCreditCardNoResultDTO implements Serializable{
	/**
	 * 是否有效
	 */
	private boolean isValid;
	
	/**
	 * 是否需要提供CVV验证码
	 */
	private boolean isNeedVerifyCode;


	public boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(boolean isValid) {
		this.isValid = isValid;
	}

	public boolean getIsNeedVerifyCode() {
		return isNeedVerifyCode;
	}

	public void setIsNeedVerifyCode(boolean isNeedVerifyCode) {
		this.isNeedVerifyCode = isNeedVerifyCode;
	}
		
		
	
	
}

package hsl.pojo.dto.hotel.order;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CheckCreditCardNoDTO implements Serializable{
	
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

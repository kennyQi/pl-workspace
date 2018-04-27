package plfx.yl.ylclient.yl.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.alibaba.fastjson.annotation.JSONField;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValidateCreditCardNoResult", propOrder = {
    "isValid",
    "isNeedVerifyCode"
})
public class CheckCreditCardNoDTO {
	
	/**
	 * 是否有效
	 */
	@JSONField(name = "IsValid")
	private boolean isValid;
	
	/**
	 * 是否需要提供CVV验证码
	 */
	@JSONField(name = "IsNeedVerifyCode")
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

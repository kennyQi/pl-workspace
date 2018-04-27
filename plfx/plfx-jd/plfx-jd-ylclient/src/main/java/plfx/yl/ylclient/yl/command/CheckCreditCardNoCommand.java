package plfx.yl.ylclient.yl.command;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.alibaba.fastjson.annotation.JSONField;
@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CheckCreditCardNoCondition", propOrder = {
    "creditCardNo"
})
public class CheckCreditCardNoCommand implements Serializable{
	/**
	 * 信用卡卡号
	 */
	 @JSONField(name = "CreditCardNo")
	private String creditCardNo;

	public String getCreditCardNo() {
		return creditCardNo;
	}

	public void setCreditCardNo(String creditCardNo) {
		this.creditCardNo = creditCardNo;
	}
	  
	  
	  
}

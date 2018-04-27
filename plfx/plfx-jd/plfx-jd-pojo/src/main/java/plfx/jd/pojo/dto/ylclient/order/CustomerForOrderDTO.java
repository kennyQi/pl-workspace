package plfx.jd.pojo.dto.ylclient.order;

import com.alibaba.fastjson.annotation.JSONField;

public class CustomerForOrderDTO extends CustomerDTO {

	/**
	 * 酒店确认号
	 */
	protected String confirmationNumber;

	public String getConfirmationNumber() {
		return confirmationNumber;
	}

	public void setConfirmationNumber(String value) {
		this.confirmationNumber = value;
	}

}

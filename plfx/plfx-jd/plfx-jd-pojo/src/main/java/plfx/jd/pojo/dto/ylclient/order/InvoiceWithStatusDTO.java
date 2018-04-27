//

package plfx.jd.pojo.dto.ylclient.order;

import java.io.Serializable;

public class InvoiceWithStatusDTO extends InvoiceDTO implements Serializable {

	/**
	 * 发票状态
	 */
	protected boolean status;
	/**
	 * 邮寄状态
	 */
	protected boolean deliveryStatus;

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean value) {
		this.status = value;
	}

	public boolean isDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(boolean value) {
		this.deliveryStatus = value;
	}

}

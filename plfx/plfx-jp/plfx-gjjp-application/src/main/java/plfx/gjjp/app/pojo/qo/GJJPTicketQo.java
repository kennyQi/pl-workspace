package plfx.gjjp.app.pojo.qo;

import hg.common.annotation.QOAttr;
import hg.common.annotation.QOAttrType;
import hg.common.annotation.QOConfig;
import hg.common.component.BaseQo;

@SuppressWarnings("serial")
@QOConfig(daoBeanId = "gjjpTicketDao")
public class GJJPTicketQo extends BaseQo {

	/**
	 * 机票票号
	 */
	@QOAttr(name = "ticketNo")
	private String ticketNo;

	/**
	 * 对应乘客
	 */
	@QOAttr(name = "passenger", type = QOAttrType.LEFT_JOIN)
	private GJPassengerQo passengerQo;

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public GJPassengerQo getPassengerQo() {
		return passengerQo;
	}

	public void setPassengerQo(GJPassengerQo passengerQo) {
		this.passengerQo = passengerQo;
	}

}

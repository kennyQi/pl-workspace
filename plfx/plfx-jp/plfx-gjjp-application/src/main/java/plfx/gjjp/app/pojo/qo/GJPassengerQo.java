package plfx.gjjp.app.pojo.qo;

import hg.common.annotation.QOAttr;
import hg.common.annotation.QOAttrType;
import hg.common.annotation.QOConfig;
import hg.common.component.BaseQo;
import plfx.gjjp.domain.common.GJJPConstants;

@SuppressWarnings("serial")
@QOConfig(daoBeanId = "gjPassengerDao")
public class GJPassengerQo extends BaseQo {

	/**
	 * 初始化所有延迟加载对象
	 */
	private Boolean initAll;

	/**
	 * 国际机票订单
	 */
	@QOAttr(name = "jpOrder", type = QOAttrType.LEFT_JOIN)
	private GJJPOrderQo jpOrderQo;

	/**
	 * 乘客姓名
	 */
	@QOAttr(name = "baseInfo.passengerName", ifTrueUseLike = "passengerNameLike")
	private String passengerName;
	private boolean passengerNameLike;

	/**
	 * 供应商机票状态
	 * 
	 * @see GJJPConstants#SUPPLIER_TICKET_STATUS_MAP
	 */
	@QOAttr(name = "status.supplierCurrentValue")
	private Integer supplierTicketStatus;

	/**
	 * 平台机票状态
	 * 
	 * @see GJJPConstants#TICKET_STATUS_MAP
	 */
	@QOAttr(name = "status.currentValue")
	private Integer ticketStatus;

	public Boolean getInitAll() {
		return initAll;
	}

	public void setInitAll(Boolean initAll) {
		this.initAll = initAll;
	}

	public GJJPOrderQo getJpOrderQo() {
		return jpOrderQo;
	}

	public void setJpOrderQo(GJJPOrderQo jpOrderQo) {
		this.jpOrderQo = jpOrderQo;
	}

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public boolean isPassengerNameLike() {
		return passengerNameLike;
	}

	public void setPassengerNameLike(boolean passengerNameLike) {
		this.passengerNameLike = passengerNameLike;
	}

	public Integer getSupplierTicketStatus() {
		return supplierTicketStatus;
	}

	public void setSupplierTicketStatus(Integer supplierTicketStatus) {
		this.supplierTicketStatus = supplierTicketStatus;
	}

	public Integer getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(Integer ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

}

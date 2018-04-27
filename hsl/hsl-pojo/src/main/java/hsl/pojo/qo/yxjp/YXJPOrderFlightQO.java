package hsl.pojo.qo.yxjp;

import hg.common.annotation.QOAttr;
import hg.common.annotation.QOAttrType;
import hg.common.annotation.QOConfig;
import hg.common.component.BaseQo;

import java.util.Date;

/**
 * @author zhurz
 * @since 1.7
 */
@SuppressWarnings("serial")
@QOConfig(daoBeanId = "yxjpOrderFlightDAO")
public class YXJPOrderFlightQO extends BaseQo {

	/**
	 * 始发地三字码（例：SHA）
	 */
	@QOAttr(name = "baseInfo.orgCity")
	private String orgCity;

	/**
	 * 目的地三字码（例：CAN）
	 */
	@QOAttr(name = "baseInfo.dstCity")
	private String dstCity;

	/**
	 * 航空公司代码（例：CA）
	 */
	@QOAttr(name = "baseInfo.airComp")
	private String airComp;

	/**
	 * 航空公司名称（例：中国国际航空公司）
	 */
	@QOAttr(name = "baseInfo.airCompanyName", type = QOAttrType.LIKE_ANYWHERE)
	private String airCompanyName;

	/**
	 * 航班号（例：CA1837）
	 */
	@QOAttr(name = "baseInfo.flightNo")
	private String flightNo;

	/**
	 * 出发时间
	 */
	@QOAttr(name = "baseInfo.startTime", type = QOAttrType.GE)
	private Date startTimeBegin;
	@QOAttr(name = "baseInfo.startTime", type = QOAttrType.LE)
	private Date startTimeEnd;

	/**
	 * 舱位代码（例：M）
	 */
	@QOAttr(name = "baseInfo.cabinCode")
	private String cabinCode;

	/**
	 * 仓位类型
	 * 0：普通；1：特殊
	 */
	@QOAttr(name = "baseInfo.cabinType")
	private Integer cabinType;

	/**
	 * 票号类型
	 * 1--B2B，2--BSP
	 */
	@QOAttr(name = "policyInfo.tickType")
	private Integer tickType;

	/**
	 * 易行政策id号
	 */
	@QOAttr(name = "policyInfo.plcid")
	private String plcid;

	/**
	 * 备注【模糊匹配】
	 */
	@QOAttr(name = "policyInfo.memo", type = QOAttrType.LIKE_ANYWHERE)
	private String memo;

	public String getOrgCity() {
		return orgCity;
	}

	public void setOrgCity(String orgCity) {
		this.orgCity = orgCity;
	}

	public String getDstCity() {
		return dstCity;
	}

	public void setDstCity(String dstCity) {
		this.dstCity = dstCity;
	}

	public String getAirComp() {
		return airComp;
	}

	public void setAirComp(String airComp) {
		this.airComp = airComp;
	}

	public String getAirCompanyName() {
		return airCompanyName;
	}

	public void setAirCompanyName(String airCompanyName) {
		this.airCompanyName = airCompanyName;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public Date getStartTimeBegin() {
		return startTimeBegin;
	}

	public void setStartTimeBegin(Date startTimeBegin) {
		this.startTimeBegin = startTimeBegin;
	}

	public Date getStartTimeEnd() {
		return startTimeEnd;
	}

	public void setStartTimeEnd(Date startTimeEnd) {
		this.startTimeEnd = startTimeEnd;
	}

	public String getCabinCode() {
		return cabinCode;
	}

	public void setCabinCode(String cabinCode) {
		this.cabinCode = cabinCode;
	}

	public Integer getCabinType() {
		return cabinType;
	}

	public void setCabinType(Integer cabinType) {
		this.cabinType = cabinType;
	}

	public Integer getTickType() {
		return tickType;
	}

	public void setTickType(Integer tickType) {
		this.tickType = tickType;
	}

	public String getPlcid() {
		return plcid;
	}

	public void setPlcid(String plcid) {
		this.plcid = plcid;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}

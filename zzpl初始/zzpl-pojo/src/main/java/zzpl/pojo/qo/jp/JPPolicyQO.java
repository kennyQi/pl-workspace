package zzpl.pojo.qo.jp;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class JPPolicyQO extends BaseQo {

	/**
	 * PNR编码
	 */
	private String pnr;

	/**
	 * Pnr文本信息
	 */
	private String pnrText;

	/**
	 * 出票类型[Yeego](默认) [B2B] [BSP]
	 */
	private String ticketType;

	/**
	 * 订座系统 [CA]:中航信-航空公司系统(ICS) [1E]:中航信-代理人系统(CRS)
	 */
	private String gds;

	/**
	 * 指定比价平台，可以多个，以|分割(默认：系统根据规则优选6家平台)
	 */
	private String plats;

	/**
	 * 是否过滤供应商非工作时间的政策: [1]:过滤 [0或null]:不过滤如果供应商的工作时间是21点，你在21点后再查询政策，这个政策是被过滤掉的
	 */
	private String isFilterWorkOutPD;

	/**
	 * 是否需要快捷采购政策 [1]:需要 [0]:不需要
	 */
	private Integer isNeedVIPPolicy;

	/**
	 * Pat文本
	 */
	private String pataText;

	/**
	 * 预定OFFICE （PNR是由哪个OFFICE号预订的）
	 */
	private String bookingOffice;

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public String getPnrText() {
		return pnrText;
	}

	public void setPnrText(String pnrText) {
		this.pnrText = pnrText;
	}

	public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}

	public String getGds() {
		return gds;
	}

	public void setGds(String gds) {
		this.gds = gds;
	}

	public String getPlats() {
		return plats;
	}

	public void setPlats(String plats) {
		this.plats = plats;
	}

	public String getIsFilterWorkOutPD() {
		return isFilterWorkOutPD;
	}

	public void setIsFilterWorkOutPD(String isFilterWorkOutPD) {
		this.isFilterWorkOutPD = isFilterWorkOutPD;
	}

	public Integer getIsNeedVIPPolicy() {
		return isNeedVIPPolicy;
	}

	public void setIsNeedVIPPolicy(Integer isNeedVIPPolicy) {
		this.isNeedVIPPolicy = isNeedVIPPolicy;
	}

	public String getPataText() {
		return pataText;
	}

	public void setPataText(String pataText) {
		this.pataText = pataText;
	}

	public String getBookingOffice() {
		return bookingOffice;
	}

	public void setBookingOffice(String bookingOffice) {
		this.bookingOffice = bookingOffice;
	}

}

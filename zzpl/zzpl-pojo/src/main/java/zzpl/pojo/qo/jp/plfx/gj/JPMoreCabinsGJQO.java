package zzpl.pojo.qo.jp.plfx.gj;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class JPMoreCabinsGJQO extends BaseQo{
	/**
	 * 航班舱位组合token
	 */
	private String flightCabinGroupToken;

	public String getFlightCabinGroupToken() {
		return flightCabinGroupToken;
	}

	public void setFlightCabinGroupToken(String flightCabinGroupToken) {
		this.flightCabinGroupToken = flightCabinGroupToken;
	}

}
package zzpl.api.client.request.jp;

import zzpl.api.client.base.ApiPayload;

import com.alibaba.fastjson.JSON;

@SuppressWarnings("serial")
public class GJCabinQO extends ApiPayload{
	/**
	 * 航班ID
	 */
	private String flightID;

	public String getFlightID() {
		return flightID;
	}

	public void setFlightID(String flightID) {
		this.flightID = flightID;
	}
	
	public static void main(String[] args) {
		GJCabinQO gnCabinQO = new GJCabinQO();
		gnCabinQO.setFlightID("1");
		System.out.println(JSON.toJSON(gnCabinQO));
	}
}

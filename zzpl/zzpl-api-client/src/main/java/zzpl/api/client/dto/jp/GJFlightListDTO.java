package zzpl.api.client.dto.jp;

import java.util.List;
import java.util.Map;

public class GJFlightListDTO {

	/**
	 * 航班ID
	 */
	private String flightID;

	/**
	 * 去程航班
	 */
	private List<GJFlightDTO> takeoffFlights;

	/**
	 * 返程航班
	 */
	private List<GJFlightDTO> backFlights;

	/**
	 * 票面价
	 */
	private String ibePrice;

	/**
	 * 价格信息
	 * 
	 * <乘客类型, 价格信息> 乘客类型:成人ADT,儿童CNN，婴儿INF
	 */
	private Map<String, PriceInfo> priceInfoMap;

	/**
	 * @类功能说明：价格信息
	 * @类修改者：
	 * @修改日期：2015-7-8下午5:17:27
	 * @修改说明：
	 * @公司名称：浙江汇购科技有限公司
	 * @作者：zhurz
	 * @创建时间：2015-7-8下午5:17:27
	 */
	public static class PriceInfo {
		/**
		 * 公布运价
		 */
		private Double totalPrice;
		/**
		 * 税金
		 */
		private Double totalTax;

		public Double getTotalPrice() {
			return totalPrice;
		}

		public void setTotalPrice(Double totalPrice) {
			this.totalPrice = totalPrice;
		}

		public Double getTotalTax() {
			return totalTax;
		}

		public void setTotalTax(Double totalTax) {
			this.totalTax = totalTax;
		}

	}

	public String getFlightID() {
		return flightID;
	}

	public void setFlightID(String flightID) {
		this.flightID = flightID;
	}

	public List<GJFlightDTO> getTakeoffFlights() {
		return takeoffFlights;
	}

	public void setTakeoffFlights(List<GJFlightDTO> takeoffFlights) {
		this.takeoffFlights = takeoffFlights;
	}

	public List<GJFlightDTO> getBackFlights() {
		return backFlights;
	}

	public void setBackFlights(List<GJFlightDTO> backFlights) {
		this.backFlights = backFlights;
	}

	public String getIbePrice() {
		return ibePrice;
	}

	public void setIbePrice(String ibePrice) {
		this.ibePrice = ibePrice;
	}

	public Map<String, PriceInfo> getPriceInfoMap() {
		return priceInfoMap;
	}

	public void setPriceInfoMap(Map<String, PriceInfo> priceInfoMap) {
		this.priceInfoMap = priceInfoMap;
	}

}

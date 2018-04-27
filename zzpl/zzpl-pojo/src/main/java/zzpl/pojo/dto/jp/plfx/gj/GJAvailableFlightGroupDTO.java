package zzpl.pojo.dto.jp.plfx.gj;

import java.util.List;
import java.util.Map;

import zzpl.pojo.dto.BaseDTO;

@SuppressWarnings("serial")
public class GJAvailableFlightGroupDTO extends BaseDTO{
	/**
	 * 航班舱位组合token
	 */
	private String flightCabinGroupToken;

	/**
	 * 退改签规则
	 */
	private String airRules;

	/**
	 * 去程总用时(单位分钟)
	 */
	private Integer takeoffTotalDuration;

	/**
	 * 去程航班
	 */
	private List<GJFlightCabinDTO> takeoffFlights;

	/**
	 * 回程总用时(单位分钟)
	 */
	private Integer backTotalDuration;

	/**
	 * 回程航班
	 */
	private List<GJFlightCabinDTO> backFlights;

	/**
	 * 价格信息
	 * 
	 * <乘客类型, 价格信息> 乘客类型:成人ADT,儿童CNN，婴儿INF
	 */
	private Map<String, PriceInfo> priceInfoMap;

	public String getFlightCabinGroupToken() {
		return flightCabinGroupToken;
	}

	public void setFlightCabinGroupToken(String flightCabinGroupToken) {
		this.flightCabinGroupToken = flightCabinGroupToken;
	}

	public String getAirRules() {
		return airRules;
	}

	public void setAirRules(String airRules) {
		this.airRules = airRules;
	}

	public Integer getTakeoffTotalDuration() {
		return takeoffTotalDuration;
	}

	public void setTakeoffTotalDuration(Integer takeoffTotalDuration) {
		this.takeoffTotalDuration = takeoffTotalDuration;
	}

	public List<GJFlightCabinDTO> getTakeoffFlights() {
		return takeoffFlights;
	}

	public void setTakeoffFlights(List<GJFlightCabinDTO> takeoffFlights) {
		this.takeoffFlights = takeoffFlights;
	}

	public Integer getBackTotalDuration() {
		return backTotalDuration;
	}

	public void setBackTotalDuration(Integer backTotalDuration) {
		this.backTotalDuration = backTotalDuration;
	}

	public List<GJFlightCabinDTO> getBackFlights() {
		return backFlights;
	}

	public void setBackFlights(List<GJFlightCabinDTO> backFlights) {
		this.backFlights = backFlights;
	}

	public Map<String, PriceInfo> getPriceInfoMap() {
		return priceInfoMap;
	}

	public void setPriceInfoMap(Map<String, PriceInfo> priceInfoMap) {
		this.priceInfoMap = priceInfoMap;
	}

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
}

package plfx.api.client.api.v1.gj.dto;

import java.util.List;
import java.util.Map;

/**
 * @类功能说明：可用的航班组合
 * @类修改者：
 * @修改日期：2015-7-8下午4:50:54
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-8下午4:50:54
 */
public class GJAvailableFlightGroupDTO {

	/**
	 * 航班舱位组合token
	 */
	private String flightCabinGroupToken;

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
		 * 乘客类型 
		 * 成人 ADT,儿童 CNN，婴儿 INF
		 */
		private String passType;
		/**
		 * 公布运价
		 */
		private Double totalPrice;
		/**
		 * 税金
		 */
		private Double totalTax;

		public String getPassType() {
			return passType;
		}

		public void setPassType(String passType) {
			this.passType = passType;
		}

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

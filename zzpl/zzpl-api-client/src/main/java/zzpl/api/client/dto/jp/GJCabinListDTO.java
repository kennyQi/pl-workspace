package zzpl.api.client.dto.jp;

import java.util.List;
import java.util.Map;

public class GJCabinListDTO {

	/**
	 * 舱位加密字符串
	 */
	private String encryptString;

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

	/**
	 * 去程舱位信息
	 */
	private List<GJCabinInfoDTO> takeoffCabinInfo;

	/**
	 * 返程舱位信息
	 */
	private List<GJCabinInfoDTO> backCabinInfo;

	public String getEncryptString() {
		return encryptString;
	}

	public void setEncryptString(String encryptString) {
		this.encryptString = encryptString;
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

	public List<GJCabinInfoDTO> getTakeoffCabinInfo() {
		return takeoffCabinInfo;
	}

	public void setTakeoffCabinInfo(List<GJCabinInfoDTO> takeoffCabinInfo) {
		this.takeoffCabinInfo = takeoffCabinInfo;
	}

	public List<GJCabinInfoDTO> getBackCabinInfo() {
		return backCabinInfo;
	}

	public void setBackCabinInfo(List<GJCabinInfoDTO> backCabinInfo) {
		this.backCabinInfo = backCabinInfo;
	}

}

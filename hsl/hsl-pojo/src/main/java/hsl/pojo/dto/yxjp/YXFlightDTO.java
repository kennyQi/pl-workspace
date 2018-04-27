package hsl.pojo.dto.yxjp;

import com.alibaba.fastjson.JSON;
import hsl.pojo.util.DesUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 易行航班信息DTO
 *
 * @author zhurz
 * @since 1.7
 */
@SuppressWarnings("serial")
public class YXFlightDTO implements Serializable {

	/**
	 * 默认的加密解密KEY
	 */
	private final static String CRYPT_KEY_DEFAULT = "5f17370e3dfe404dbb01e5ac3efa444d";

	/**
	 * 航班基础信息
	 */
	private BaseInfo baseInfo;
	/**
	 * 航班舱位信息
	 */
	private CabinInfo cabinInfo;
	/**
	 * 航班政策信息
	 */
	private PolicyInfo policyInfo;

	public BaseInfo getBaseInfo() {
		return baseInfo;
	}

	public void setBaseInfo(BaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}

	public CabinInfo getCabinInfo() {
		return cabinInfo;
	}

	public void setCabinInfo(CabinInfo cabinInfo) {
		this.cabinInfo = cabinInfo;
	}

	public PolicyInfo getPolicyInfo() {
		return policyInfo;
	}

	public void setPolicyInfo(PolicyInfo policyInfo) {
		this.policyInfo = policyInfo;
	}

	/**
	 * 航班基础信息
	 */
	public static class BaseInfo {
		/**
		 * 始发地
		 */
		private String orgCity;
		/**
		 * 始发地,名称
		 */
		private String orgCityName;
		/**
		 * 始发地航站楼
		 */
		private String departTerm;
		/**
		 * 航空公司名称
		 * 中国国际航空公司
		 */
		private String airCompanyName;
		/**
		 * 航空公司名称 简称
		 * 国航
		 */
		private String airCompanyShotName;
		/**
		 * 目的地
		 */
		private String dstCity;
		/**
		 * 目的地,名称
		 */
		private String dstCityName;
		/**
		 * 目的地航站楼
		 */
		private String arrivalTerm;
		/**
		 * 航空公司
		 */
		private String airComp;
		/**
		 * 航班
		 */
		private String flightno;
		/**
		 * 机型
		 */
		private String planeType;
		/**
		 * 出发时间
		 */
		private Date startTime;
		/**
		 * 到达时间
		 */
		private Date endTime;
		/**
		 * 经停次数
		 */
		private String stopNumber;
		/**
		 * 餐食代码
		 */
		private String mealCode;

		public String getOrgCity() {
			return orgCity;
		}

		public void setOrgCity(String orgCity) {
			this.orgCity = orgCity;
		}

		public String getOrgCityName() {
			return orgCityName;
		}

		public void setOrgCityName(String orgCityName) {
			this.orgCityName = orgCityName;
		}

		public String getDepartTerm() {
			return departTerm;
		}

		public void setDepartTerm(String departTerm) {
			this.departTerm = departTerm;
		}

		public String getAirCompanyName() {
			return airCompanyName;
		}

		public void setAirCompanyName(String airCompanyName) {
			this.airCompanyName = airCompanyName;
		}

		public String getAirCompanyShotName() {
			return airCompanyShotName;
		}

		public void setAirCompanyShotName(String airCompanyShotName) {
			this.airCompanyShotName = airCompanyShotName;
		}

		public String getDstCity() {
			return dstCity;
		}

		public void setDstCity(String dstCity) {
			this.dstCity = dstCity;
		}

		public String getDstCityName() {
			return dstCityName;
		}

		public void setDstCityName(String dstCityName) {
			this.dstCityName = dstCityName;
		}

		public String getArrivalTerm() {
			return arrivalTerm;
		}

		public void setArrivalTerm(String arrivalTerm) {
			this.arrivalTerm = arrivalTerm;
		}

		public String getAirComp() {
			return airComp;
		}

		public void setAirComp(String airComp) {
			this.airComp = airComp;
		}

		public String getFlightno() {
			return flightno;
		}

		public void setFlightno(String flightno) {
			this.flightno = flightno;
		}

		public String getPlaneType() {
			return planeType;
		}

		public void setPlaneType(String planeType) {
			this.planeType = planeType;
		}

		public Date getStartTime() {
			return startTime;
		}

		public void setStartTime(Date startTime) {
			this.startTime = startTime;
		}

		public Date getEndTime() {
			return endTime;
		}

		public void setEndTime(Date endTime) {
			this.endTime = endTime;
		}

		public String getStopNumber() {
			return stopNumber;
		}

		public void setStopNumber(String stopNumber) {
			this.stopNumber = stopNumber;
		}

		public String getMealCode() {
			return mealCode;
		}

		public void setMealCode(String mealCode) {
			this.mealCode = mealCode;
		}
	}

	/**
	 * 航班舱位信息
	 */
	public static class CabinInfo {

		/**
		 * 舱位
		 */
		private String cabinCode;
		/**
		 * 舱位类型
		 */
		private String cabinType;
		/**
		 * 舱位折扣
		 */
		private String cabinDiscount;
		/**
		 * 舱位名称
		 */
		private String cabinName;
		/**
		 * 剩余座位数
		 */
		private String cabinSales;
		/**
		 * 票面价
		 */
		private String ibePrice;
		/**
		 * 加密字符串
		 */
		private String encryptString;

		public String getCabinCode() {
			return cabinCode;
		}

		public void setCabinCode(String cabinCode) {
			this.cabinCode = cabinCode;
		}

		public String getCabinType() {
			return cabinType;
		}

		public void setCabinType(String cabinType) {
			this.cabinType = cabinType;
		}

		public String getCabinDiscount() {
			return cabinDiscount;
		}

		public void setCabinDiscount(String cabinDiscount) {
			this.cabinDiscount = cabinDiscount;
		}

		public String getCabinName() {
			return cabinName;
		}

		public void setCabinName(String cabinName) {
			this.cabinName = cabinName;
		}

		public String getCabinSales() {
			return cabinSales;
		}

		public void setCabinSales(String cabinSales) {
			this.cabinSales = cabinSales;
		}

		public String getIbePrice() {
			return ibePrice;
		}

		public void setIbePrice(String ibePrice) {
			this.ibePrice = ibePrice;
		}

		public String getEncryptString() {
			return encryptString;
		}

		public void setEncryptString(String encryptString) {
			this.encryptString = encryptString;
		}
	}

	/**
	 * 航班政策信息
	 */
	public static class PolicyInfo {

		/**
		 * 机场建设费
		 */
		private Double buildFee;

		/**
		 * 燃油税
		 */
		private Double oilFee;

		/**
		 * 供应商周一至周五工作时间
		 * 格式“HH:mm-HH:mm”,24小时制
		 * 如：“08:00-24:00”
		 */
		private String workTime;

		/**
		 * 供应商周六、周日工作时间
		 * 格式“HH:mm-HH:mm”,24小时制
		 * 如：“08:00-24:00”
		 */
		private String restWorkTime;

		/**
		 * 供应商周一至周五退废票时间
		 * 格式“HH:mm-HH:mm”,24小时制
		 * 如：“08:00-24:00”
		 */
		private String workReturnTime;

		/**
		 * 供应商周六、周日退废票时间
		 * 格式“HH:mm-HH:mm”,24小时制
		 * 如：“08:00-24:00”
		 */
		private String restReturnTime;

		/**
		 * 出票速度
		 * 格式：HH分钟mm秒
		 */
		private String outTime;

		/**
		 * 票号类型
		 * 1--B2B，2--BSP
		 */
		private Integer tickType;

		/**
		 * 自愿退票保证退款
		 * 自愿退票保证1/3/5/7个工作日内退款
		 */
		private String refund;

		/**
		 * 当日作废保证退款
		 * 当日作废保证24点之前退款
		 */
		private String invalid;

		/**
		 * 10分钟外NO位或差错保证出票
		 */
		private String indemnity;

		/**
		 * 加密串
		 */
		private String encryptString;

		/**
		 * 票面价
		 */
		private Double ibePrice;

		/**
		 * 政策id号
		 */
		private int plcid;

		/**
		 * 备注
		 */
		private String memo;

		/**
		 * 给供应商的单人支付总价(包括机建燃油)
		 */
		private Double singleTotalPrice;

		/**
		 * 单人支付总价(包括机建燃油)
		 * singleTotalPrice + 价格政策　＋　手续费
		 */
		private Double singlePlatTotalPrice;

		public Double getBuildFee() {
			return buildFee;
		}

		public void setBuildFee(Double buildFee) {
			this.buildFee = buildFee;
		}

		public Double getOilFee() {
			return oilFee;
		}

		public void setOilFee(Double oilFee) {
			this.oilFee = oilFee;
		}

		public String getWorkTime() {
			return workTime;
		}

		public void setWorkTime(String workTime) {
			this.workTime = workTime;
		}

		public String getRestWorkTime() {
			return restWorkTime;
		}

		public void setRestWorkTime(String restWorkTime) {
			this.restWorkTime = restWorkTime;
		}

		public String getWorkReturnTime() {
			return workReturnTime;
		}

		public void setWorkReturnTime(String workReturnTime) {
			this.workReturnTime = workReturnTime;
		}

		public String getRestReturnTime() {
			return restReturnTime;
		}

		public void setRestReturnTime(String restReturnTime) {
			this.restReturnTime = restReturnTime;
		}

		public String getOutTime() {
			return outTime;
		}

		public void setOutTime(String outTime) {
			this.outTime = outTime;
		}

		public Integer getTickType() {
			return tickType;
		}

		public void setTickType(Integer tickType) {
			this.tickType = tickType;
		}

		public String getRefund() {
			return refund;
		}

		public void setRefund(String refund) {
			this.refund = refund;
		}

		public String getInvalid() {
			return invalid;
		}

		public void setInvalid(String invalid) {
			this.invalid = invalid;
		}

		public String getIndemnity() {
			return indemnity;
		}

		public void setIndemnity(String indemnity) {
			this.indemnity = indemnity;
		}

		public String getEncryptString() {
			return encryptString;
		}

		public void setEncryptString(String encryptString) {
			this.encryptString = encryptString;
		}

		public Double getIbePrice() {
			return ibePrice;
		}

		public void setIbePrice(Double ibePrice) {
			this.ibePrice = ibePrice;
		}

		public int getPlcid() {
			return plcid;
		}

		public void setPlcid(int plcid) {
			this.plcid = plcid;
		}

		public String getMemo() {
			return memo;
		}

		public void setMemo(String memo) {
			this.memo = memo;
		}

		public Double getSingleTotalPrice() {
			return singleTotalPrice;
		}

		public void setSingleTotalPrice(Double singleTotalPrice) {
			this.singleTotalPrice = singleTotalPrice;
		}

		public Double getSinglePlatTotalPrice() {
			return singlePlatTotalPrice;
		}

		public void setSinglePlatTotalPrice(Double singlePlatTotalPrice) {
			this.singlePlatTotalPrice = singlePlatTotalPrice;
		}
	}

	/**
	 * 将加密字符串解密为对象
	 *
	 * @param encryptValue 加密字符串
	 * @return
	 */
	public static YXFlightDTO valueOfEncryptString(String encryptString) {
		try {
			String json = DesUtils.decrypt(encryptString, CRYPT_KEY_DEFAULT);
			return JSON.parseObject(json, YXFlightDTO.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 转为加密字符串
	 *
	 * @return
	 */
	public String toEncryptString() {
		try {
			return DesUtils.encrypt(JSON.toJSONString(this), CRYPT_KEY_DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static void main(String[] args) {
		YXFlightDTO dto = new YXFlightDTO();
		dto.setBaseInfo(new BaseInfo());
		dto.setCabinInfo(new CabinInfo());
		dto.getBaseInfo().setDstCity("上海");
		dto.getCabinInfo().setCabinCode("A");
		String encryptString;
		System.out.println("加密前：");
		System.out.println(encryptString = dto.toEncryptString());
		System.out.println("解密后：");
		System.out.println(JSON.toJSONString(valueOfEncryptString(encryptString), true));
	}
}

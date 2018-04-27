package zzpl.pojo.dto.jp.plfx.gn;

import zzpl.pojo.dto.BaseDTO;

/***
 * 
 * @类功能说明：下单前查政策RESPONSE
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年6月23日上午10:35:24
 * @版本：V1.0
 * 
 */
@SuppressWarnings("serial")
public class JPQueryHighPolicyGNDTO extends BaseDTO {

	/***
	 * 表示该次操作是否成功 T:成功F：失败
	 */
	private String is_success;

	/***
	 * 机场建设费
	 */
	private Double buildFee;

	/***
	 * 燃油税
	 */
	private Double oilFee;

	/**
	 * 供应商周一至周五工作时间 格式“HH:mm-HH:mm”,24小时制 如：“08:00-24:00”
	 */
	private String workTime;

	/**
	 * 供应商周六、周日工作时间 格式“HH:mm-HH:mm”,24小时制 如：“08:00-24:00”
	 */
	private String restWorkTime;

	/**
	 * 供应商周一至周五退废票时间 格式“HH:mm-HH:mm”,24小时制 如：“08:00-24:00”
	 */
	private String workReturnTime;

	/**
	 * 供应商周六、周日退废票时间 格式“HH:mm-HH:mm”,24小时制 如：“08:00-24:00”
	 */
	private String restReturnTime;

	/**
	 * 出票速度 格式：HH分钟mm秒
	 */
	private String outTime;

	/**
	 * 票号类型 1--B2B，2--BSP
	 */
	private Integer tickType;

	/**
	 * 自愿退票保证退款 自愿退票保证1/3/5/7个工作日内退款
	 */
	private String refund;

	/**
	 * 当日作废保证退款 当日作废保证24点之前退款
	 */
	private String invalid;

	/**
	 * 10分钟外NO位或差错保证出票
	 */
	private String indemnity;

	/***
	 * 价格政策信息
	 */
	private PriceInfoGNDTO pricesGNDTO;

	/***
	 * 错误信息 格式：错误代码^错误信息
	 */
	private String error;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public PriceInfoGNDTO getPricesGNDTO() {
		return pricesGNDTO;
	}

	public void setPricesGNDTO(PriceInfoGNDTO pricesGNDTO) {
		this.pricesGNDTO = pricesGNDTO;
	}

	public String getIs_success() {
		return is_success;
	}

	public void setIs_success(String is_success) {
		this.is_success = is_success;
	}

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

}

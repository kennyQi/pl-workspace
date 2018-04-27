package hsl.domain.model.yxjp;

import hsl.domain.model.M;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 易行机票政策信息
 *
 * @author zhurz
 * @since 1.7
 */
@Embeddable
@SuppressWarnings("serial")
public class YXJPOrderFlightPolicyInfo implements Serializable {

	/**
	 * 票面价
	 */
	@Column(name = "IBE_PRICE", columnDefinition = M.MONEY_COLUM)
	private Double ibePrice = 0d;

	/**
	 * 机场建设费
	 */
	@Column(name = "BUILD_FEE", columnDefinition = M.MONEY_COLUM)
	private Double buildFee = 0d;

	/**
	 * 燃油税
	 */
	@Column(name = "OIL_FEE", columnDefinition = M.MONEY_COLUM)
	private Double oilFee = 0d;

	/**
	 * 加密串（下单给供应商用）
	 */
	@Column(name = "ENCRYPT_STRING", columnDefinition = M.TEXT_COLUM)
	private String encryptString;

	/**
	 * 供应商周一至周五工作时间
	 * 格式“HH:mm-HH:mm”,24小时制
	 * 如：“08:00-24:00”
	 */
	@Column(name = "WORK_TIME", length = 16)
	private String workTime;

	/**
	 * 供应商周六、周日工作时间
	 * 格式“HH:mm-HH:mm”,24小时制
	 * 如：“08:00-24:00”
	 */
	@Column(name = "REST_WORK_TIME", length = 16)
	private String restWorkTime;

	/**
	 * 供应商周一至周五退废票时间
	 * 格式“HH:mm-HH:mm”,24小时制
	 * 如：“08:00-24:00”
	 */
	@Column(name = "WORK_RETURN_TIME", length = 16)
	private String workReturnTime;

	/**
	 * 供应商周六、周日退废票时间
	 * 格式“HH:mm-HH:mm”,24小时制
	 * 如：“08:00-24:00”
	 */
	@Column(name = "REST_RETURN_TIME", length = 16)
	private String restReturnTime;

	/**
	 * 票号类型
	 * 1--B2B，2--BSP
	 */
	@Column(name = "TICK_TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer tickType;

	/**
	 * 自愿退票保证退款
	 * 自愿退票保证1/3/5/7个工作日内退款
	 */

	@Column(name = "REFUND", length = 128)
	private String refund;

	/**
	 * 当日作废保证退款
	 * 当日作废保证24点之前退款
	 */

	@Column(name = "INVALID", length = 128)
	private String invalid;

	/**
	 * 10分钟外NO位或差错保证出票
	 */
	@Column(name = "INDEMNITY", length = 128)
	private String indemnity;

	/**
	 * 易行政策id号
	 */
	@Column(name = "PLCID", length = 32)
	private String plcid;

	/**
	 * 备注
	 */
	@Column(name = "MEMO", length = 512)
	private String memo;

	/**
	 * 给供应商的单价（包括机建燃油）
	 */
	@Column(name = "SUPPLIER_PRICE", columnDefinition = M.MONEY_COLUM)
	private Double supplierPrice = 0d;

	/**
	 * 经销商支付平台的价格=给供应商的单人支付总价+价格政策+手续费
	 */
	@Column(name = "PAY_PRICE", columnDefinition = M.MONEY_COLUM)
	private Double payPrice = 0d;

	public Double getIbePrice() {
		return ibePrice;
	}

	public void setIbePrice(Double ibePrice) {
		this.ibePrice = ibePrice;
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

	public String getEncryptString() {
		return encryptString;
	}

	public void setEncryptString(String encryptString) {
		this.encryptString = encryptString;
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

	public Double getSupplierPrice() {
		return supplierPrice;
	}

	public void setSupplierPrice(Double supplierPrice) {
		this.supplierPrice = supplierPrice;
	}

	public Double getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(Double payPrice) {
		this.payPrice = payPrice;
	}
}

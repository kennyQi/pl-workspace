package plfx.yeexing.pojo.dto.flight;



import plfx.jp.pojo.dto.BaseJpDTO;


/****
 * 
 * @类功能说明：价格政策信息DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年6月16日下午5:14:29
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class YeeXingPriceInfoDTO extends BaseJpDTO{
	/** 票面价  */
	private Double ibePrice;

	/** 票面价(不推荐使用) */
	private Double ibePricec;

	/** 政策id号  */
	private int plcid;

	/**  特殊高政策   0否1是*/
	private Double isSphigh;

	/** 政策返点    3.0表示返3个点 */
	private Double disc;

	/** 红包    5.0表示返点之外再减5元*/
	private Double extReward;

	/** 单张票价 */
	private Double tickPrice ;

	/**  单张利润 */
	private Double profits;

	/** 供应商周一至周五工作时间   格式“HH:mm-HH:mm”,24小时制如：“08:00-24:00”*/
	private String workTime;

	/** 供应商周六、周日工作时间   格式“HH:mm-HH:mm”,24小时制如：“08:00-24:00”*/
	private String restWorkTime ;

	/** 供应商周一至周五退废票时间   格式“HH:mm-HH:mm”,24小时制如：“08:00-24:00”*/
	private String workReturnTime;

	/** 供应商周六、周日退废票时间  格式“HH:mm-HH:mm”,24小时制如：“08:00-24:00”*/
	private String restReturnTime ;

	/** 出票速度 格式：HH分钟mm秒 */
	private String outTime;

	/** 票号类型   1--B2B，2--BSP */
	private String tickType;

	/** 是否更改pnr  */
	private String changePnr ;

	/** 备注  */
	private String memo ;

	/** 政策返点(不推荐使用)  */
	private Double commPayPrice;

	/**加密串*/
	private String encryptString;

	/**自愿退票保证退款    自愿退票保证1/3/5/7个工作日内退款*/
	private String refund ;

	/**  当日作废保证退款     当日作废保证24点之前退款*/
	private String invalid;

	/**  政策调整保证出票     政策调整保证出票 */
	private String credit;

	/** 10分钟外NO位或差错保证出票      10分钟外NO位或差错出票*/
	private String indemnity;

	/** 支持的支付方式    1-支付宝 2-汇付 6-IPS  7-德付通    如都支持，则为1267*/
	private String payType;
	
	public Double getIbePrice() {
		return ibePrice;
	}

	public void setIbePrice(Double ibePrice) {
		this.ibePrice = ibePrice;
	}

	public Double getIbePricec() {
		return ibePricec;
	}

	public void setIbePricec(Double ibePricec) {
		this.ibePricec = ibePricec;
	}

	public int getPlcid() {
		return plcid;
	}

	public void setPlcid(int plcid) {
		this.plcid = plcid;
	}

	public Double getIsSphigh() {
		return isSphigh;
	}

	public void setIsSphigh(Double isSphigh) {
		this.isSphigh = isSphigh;
	}

	public Double getDisc() {
		return disc;
	}

	public void setDisc(Double disc) {
		this.disc = disc;
	}

	public Double getExtReward() {
		return extReward;
	}

	public void setExtReward(Double extReward) {
		this.extReward = extReward;
	}

	public Double getTickPrice() {
		return tickPrice;
	}

	public void setTickPrice(Double tickPrice) {
		this.tickPrice = tickPrice;
	}

	public Double getProfits() {
		return profits;
	}

	public void setProfits(Double profits) {
		this.profits = profits;
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

	public String getTickType() {
		return tickType;
	}

	public void setTickType(String tickType) {
		this.tickType = tickType;
	}

	public String getChangePnr() {
		return changePnr;
	}

	public void setChangePnr(String changePnr) {
		this.changePnr = changePnr;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Double getCommPayPrice() {
		return commPayPrice;
	}

	public void setCommPayPrice(Double commPayPrice) {
		this.commPayPrice = commPayPrice;
	}

	public String getEncryptString() {
		return encryptString;
	}

	public void setEncryptString(String encryptString) {
		this.encryptString = encryptString;
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

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getIndemnity() {
		return indemnity;
	}

	public void setIndemnity(String indemnity) {
		this.indemnity = indemnity;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	
	
}

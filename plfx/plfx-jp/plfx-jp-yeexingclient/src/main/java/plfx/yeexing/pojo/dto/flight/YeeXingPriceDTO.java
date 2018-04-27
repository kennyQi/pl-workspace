package plfx.yeexing.pojo.dto.flight;

import plfx.jp.pojo.dto.BaseJpDTO;

/****
 * 
 * @类功能说明：生成订单的政策信息DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年6月18日下午3:48:06
 * @版本：V1.0
 * 
 */
@SuppressWarnings("serial")
public class YeeXingPriceDTO extends BaseJpDTO {
	/**
	 * 婴儿价格 有婴儿的时候会有(目前暂不支持婴儿)
	 */
	private Double infPrice;
	/**
	 * 票号类型 1--B2B，2--BSP
	 */
	private int tickType;
	/**
	 * 是否更改pnr 0否1是
	 */
	private String changePnr;
	/**
	 * 支持的支付方式 1-支付宝 2-汇付 6-IPS 7-德付通如都支持，则为1267
	 */
	private Integer payType;
	/**
	 * 票面价
	 */
	private Double ibePrice;
	/**
	 * 机场建设费
	 */
	private Double buildFee;
	/**
	 * 燃油税
	 */
	private Double oilFee;
	/**
	 * 政策id号
	 */
	private int plcid;
	/**
	 * 特殊高政策
	 */
	private String isSphigh;
	/**
	 * 政策返点
	 */
	private Double disc;
	/**
	 * 红包
	 */
	private Double extReward;
	/**
	 * 单张票价
	 */
	private Double tickPrice;
	/**
	 * 单张利润
	 */
	private Double profits;
	/**
	 * 供应商周六、周日工作时间
	 */
	private String restWorkTime;
	/**
	 * 供应商周一至周五工作时间
	 */
	private String workTime;
	/**
	 * 出票速度
	 */
	private String outTime;
	/**
	 * 备注
	 */
	private String memo;
	/**
	 * 订单总支付价格(支付给供应商的价格)
	 */
	private Double totalPrice;

	/**
	 * 平台加价政策金额(针对单张票)
	 */
	private Double platPolicyPirce;
	
	/**
	 * 平台计算政策价格后需要支付的总价，totalPrice基础上算的价格 
	 * totalPrice + platPolicyPirce + serviceCharge(每张票都要付手续费和平台加价价格)
	 */
	private Double platTotalPrice;
	
	/**
	 * 手续费
	 * 手续费=（票面价+税款）*0.008（支付宝千分之8手续费）
	 */
	private Double serviceCharge;

	public Double getInfPrice() {
		return infPrice;
	}

	public void setInfPrice(Double infPrice) {
		this.infPrice = infPrice;
	}

	public int getTickType() {
		return tickType;
	}

	public void setTickType(int tickType) {
		this.tickType = tickType;
	}

	public String getChangePnr() {
		return changePnr;
	}

	public Double getPlatTotalPrice() {
		return platTotalPrice;
	}

	public void setPlatTotalPrice(Double platTotalPrice) {
		this.platTotalPrice = platTotalPrice;
	}

	public void setChangePnr(String changePnr) {
		this.changePnr = changePnr;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

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

	public int getPlcid() {
		return plcid;
	}

	public void setPlcid(int plcid) {
		this.plcid = plcid;
	}

	public String getIsSphigh() {
		return isSphigh;
	}

	public void setIsSphigh(String isSphigh) {
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

	public String getRestWorkTime() {
		return restWorkTime;
	}

	public void setRestWorkTime(String restWorkTime) {
		this.restWorkTime = restWorkTime;
	}

	public String getWorkTime() {
		return workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}

	public String getOutTime() {
		return outTime;
	}

	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getPlatPolicyPirce() {
		return platPolicyPirce;
	}

	public void setPlatPolicyPirce(Double platPolicyPirce) {
		this.platPolicyPirce = platPolicyPirce;
	}

	public Double getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(Double serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
}

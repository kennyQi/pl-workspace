package slfx.jp.qo.api;

import java.util.Date;

import hg.common.component.BaseQo;

/**
 * 
 * @类功能说明：航班查询QO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午5:15:27
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class WebFlightQO extends BaseQo {
	
	/**
	 * 出发机场的三字代码(例如：PEK 北京)
	 */
	private String from;
	
	/**
	 * 目地机场三字代码
	 */
	private String arrive;
	
	/**
	 * 航班日期 (例如：2008-03-16)
	 */
	private Date date;
	
	/**
	 * 航空公司两字代码 (例如：CA)
	 */
	private String carrier;
	
	/**
	 * 起飞时间范围开始 (例如：0000)
	 */
	private String time;
	
	/**
	 * [N] :直达无经停（默认）
	 * [D] :直达 
	 * [A] :所有航班(包括中转)
	 */
	private String stopType;
	
	/**
	 * 是否返回共享航班：
	 * [0]:不包含共享航班 
	 * [1]:含共享航班
	 */
	private String cmdShare;
	
	/**
	 * 系统保留
	 */
	private String option;
	
	/**
	 * 最大返回结果， 0：表示返回全部查询结果
	 */
	private String maxNum;
	
	/**
	 * 用户来源，目前影响到数据缓存模式 
	 * [0]:默认，缓存模式以系统设置为准 
	 * [1]:B2B，航班数据不缓存 
	 * [2]:呼叫中心，航班数据不缓存 
	 * [3]:B2C，使用缓存航班数据
	 */
	private String userSource;
	
	/**
	 * 期望缓存时间(秒)，默认0
	 */
	private Integer expectedTimes;
	
	/**
	 * 可容忍航班缓存时间(秒)，默认0
	 */
	private Integer beTolerateTimes;
	
	/**
	 * 航班内容，数据规范参考”附录二” 传入航班内容后,
	 * 将按航班内容匹配运价,而不再做航班查询 数据为压缩格式,
	 * 压缩格式：deflate
	 */
	private String avContent;
	
	/**
	 * 是否返回一周最低价 
	 * [N]:不返回(默认)
	 * [Y]:返回 
	 */
	private String useWeekPrice;
	
	/**
	 * 是否按城市查询 
	 * [Y]:(默认)按城市查询,例如PEK查询同时返回PEK/NAY出港航班; 
	 * [N]:按机场查询,例如PEK仅返回PEK出港航班
	 */
	private String isCity;
	
	/**
	 * 产品类型 
	 * [C]:(默认)普通产品 
	 * [R]:包含折上折、K位等产品
	 */
	private String prdType;
	
	/**
	 * 航司大客户编码,多个以”,”分隔(用于匹配大客户产品，仅当PrdType=”R”时有效)
	 */
	private String carrierCustomerNos;
	
	/**
	 * 查询航班，是否同时查询政策
	 * [N]:不查询政策(默认) 
	 * [Y]:查询政策
	 */
	private String policy;
	
	/**
	 * 政策供应商(仅当Policy=”Y”时有效)
	 */
	private String policySP;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getArrive() {
		return arrive;
	}

	public void setArrive(String arrive) {
		this.arrive = arrive;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getStopType() {
		return stopType;
	}

	public void setStopType(String stopType) {
		this.stopType = stopType;
	}

	public String getCmdShare() {
		return cmdShare;
	}

	public void setCmdShare(String cmdShare) {
		this.cmdShare = cmdShare;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(String maxNum) {
		this.maxNum = maxNum;
	}

	public String getUserSource() {
		return userSource;
	}

	public void setUserSource(String userSource) {
		this.userSource = userSource;
	}

	public Integer getExpectedTimes() {
		return expectedTimes;
	}

	public void setExpectedTimes(Integer expectedTimes) {
		this.expectedTimes = expectedTimes;
	}

	public Integer getBeTolerateTimes() {
		return beTolerateTimes;
	}

	public void setBeTolerateTimes(Integer beTolerateTimes) {
		this.beTolerateTimes = beTolerateTimes;
	}

	public String getAvContent() {
		return avContent;
	}

	public void setAvContent(String avContent) {
		this.avContent = avContent;
	}

	public String getUseWeekPrice() {
		return useWeekPrice;
	}

	public void setUseWeekPrice(String useWeekPrice) {
		this.useWeekPrice = useWeekPrice;
	}

	public String getIsCity() {
		return isCity;
	}

	public void setIsCity(String isCity) {
		this.isCity = isCity;
	}

	public String getPrdType() {
		return prdType;
	}

	public void setPrdType(String prdType) {
		this.prdType = prdType;
	}

	public String getCarrierCustomerNos() {
		return carrierCustomerNos;
	}

	public void setCarrierCustomerNos(String carrierCustomerNos) {
		this.carrierCustomerNos = carrierCustomerNos;
	}

	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}

	public String getPolicySP() {
		return policySP;
	}

	public void setPolicySP(String policySP) {
		this.policySP = policySP;
	}
	
}

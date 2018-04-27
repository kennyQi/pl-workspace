package slfx.jp.qo.client;

/**
 * 
 * @类功能说明：易购航程查询QO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午5:17:02
 * @版本：V1.0
 *
 */
public class QueryWebFlightsQO {
	/**
	 * 出发机场三字码  必填
	 */
	private String from;
	
	/**
	 * 目的机场三字码  必填
	 */
	private String arrive;
	
	/**
	 * 航班日期 (格式：2008-03-16) 必填
	 */
	private String dateStr;
	
	/**
	 * 航空公司两字代码 (例如：CA)  非必填
	 */
	private String carrier = "";
	
	/**
	 * 起飞时间范围开始 (例如：0000) 长度4  必填
	 */
	private String timeStr = "0000";
	
	/**
	 * N:直达无经停（默认）   D：直达包含经停    A：所有航班（包括中转）  必填
	 */
	private String stopType = "D";
	
	/**
	 * 是否返回共享航班   0：不包含共享航班   1：含共享航班    必填
	 */
	private String cmdShare = "0";
	
	/**
	 * 系统保留    非必填  不用设
	 */
	private String option;
	
	/**
	 * 查询航班，是否同时查询政策   N默认不查询   Y查询     非必填   不用设
	 */
	private String policy = "N";

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

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getTimeStr() {
		return timeStr;
	}

	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
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

	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}
	
}

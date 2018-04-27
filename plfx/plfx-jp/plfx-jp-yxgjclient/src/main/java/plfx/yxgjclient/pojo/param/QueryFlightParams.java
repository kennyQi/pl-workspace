package plfx.yxgjclient.pojo.param;

/**
 * 航班查询参数
 * @author guotx
 * 2015-07-15
 */
public class QueryFlightParams extends BaseParam{
	
	/**
	 * 始发地
	 */
	private String orgCity;
	/**
	 * 目的地
	 */
	private String dstCity;
	/**
	 * 起飞日期
	 */
	private String orgDate;
	/**
	 * 回程起飞日期
	 */
	private String dstDate;
	/**
	 * 航空公司
	 */
	private String airComp;
	/**
	 * 是否直达，0直达，1全部（默认）
	 */
	private String isDirect;
	/**
	 * 乘客类型
	 */
	private String passType;
	/**
	 * 是否按照城市三字码查询航班
	 */
	private String airportOnly;
	/**
	 * 中转机场
	 */
	private String midAirline;
	public String getOrgCity() {
		return orgCity;
	}
	public String getDstCity() {
		return dstCity;
	}
	public String getOrgDate() {
		return orgDate;
	}
	public String getDstDate() {
		return dstDate;
	}
	public String getAirComp() {
		return airComp;
	}
	public String getIsDirect() {
		return isDirect;
	}
	public String getPassType() {
		return passType;
	}
	public String getAirportOnly() {
		return airportOnly;
	}
	public String getMidAirline() {
		return midAirline;
	}
	public void setOrgCity(String orgCity) {
		this.orgCity = orgCity;
	}
	public void setDstCity(String dstCity) {
		this.dstCity = dstCity;
	}
	public void setOrgDate(String orgDate) {
		this.orgDate = orgDate;
	}
	public void setDstDate(String dstDate) {
		this.dstDate = dstDate;
	}
	public void setAirComp(String airComp) {
		this.airComp = airComp;
	}
	public void setIsDirect(String isDirect) {
		this.isDirect = isDirect;
	}
	public void setPassType(String passType) {
		this.passType = passType;
	}
	public void setAirportOnly(String airportOnly) {
		this.airportOnly = airportOnly;
	}
	public void setMidAirline(String midAirline) {
		this.midAirline = midAirline;
	}
}

package plfx.yxgjclient.pojo.param;
/**
 * 规则请求参数
 * @author guotx
 * 2015-07-14
 */
public class QueryAirRulesParams extends BaseParam{
	/**
	 * 出发机场三字码
	 */
	private String orgCity;
	/**
	 * 到达机场三字码
	 */
	private String dstCity;
	/**
	 * 起飞时间
	 * yyyy-MM-dd HH:mm
	 */
	private String startTime;
	/**
	 * 发布运价航司
	 */
	private String filingAirline;
	/**
	 * 运价基础
	 */
	private String fareReference;
	/**
	 * 查询规则参数
	 */
	private String ref1;
	/**
	 * 查询规则参数
	 */
	private String ref2;
	public String getOrgCity() {
		return orgCity;
	}
	public void setOrgCity(String orgCity) {
		this.orgCity = orgCity;
	}
	public String getDstCity() {
		return dstCity;
	}
	public void setDstCity(String dstCity) {
		this.dstCity = dstCity;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getFilingAirline() {
		return filingAirline;
	}
	public void setFilingAirline(String filingAirline) {
		this.filingAirline = filingAirline;
	}
	public String getFareReference() {
		return fareReference;
	}
	public void setFareReference(String fareReference) {
		this.fareReference = fareReference;
	}
	public String getRef1() {
		return ref1;
	}
	public void setRef1(String ref1) {
		this.ref1 = ref1;
	}
	public String getRef2() {
		return ref2;
	}
	public void setRef2(String ref2) {
		this.ref2 = ref2;
	}
	
}

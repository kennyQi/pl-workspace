package plfx.mp.tcclient.tc.dto.jd;


/**
 * 景点明细
 * @author zhangqy
 *
 */
public class PriceCalendarDto extends JdDto{
	/**
	 * 价格id
	 */
	private Integer policyId;
	/**
	 * 价格id
	 */
	private String startDate;
	/**
	 * 查询结束日期
	 */
	private String endDate;
	/**
	 * 是否显示详情(0:不显示,1显示)
	 */
	private Integer showDetail;
	
	public PriceCalendarDto() {
		super();
		this.setUnlist(true);
		this.setParam("slfx.mp.tcclient.tc.pojo.ParamPriceCalendar");
		this.setResult("slfx.mp.tcclient.tc.pojo.ResultPriceCalendar");
		this.setServiceName("GetPriceCalendar");
	}
	public Integer getShowDetail() {
		return showDetail;
	}
	public void setShowDetail(Integer showDetail) {
		this.showDetail = showDetail;
	}
	public Integer getPolicyId() {
		return policyId;
	}
	public void setPolicyId(Integer policyId) {
		this.policyId = policyId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	
	
}

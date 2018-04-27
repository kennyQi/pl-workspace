package hsl.pojo.dto.hotel.base;
/**
 * @类功能说明：送礼活动
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年3月26日上午10:44:06
 * @版本：V1.0
 * 
 */
public class BaseGiftRuleDTO extends BaseRuleDTO {
	private static final long serialVersionUID = -1685517346478908000L;
	/**
	 * 开始时间
	 */
	protected java.util.Date startDate;
	/**
	 * 结束时间
	 */
	protected java.util.Date endDate;
	/**
	 * 日期类型
	 */
	protected String dateType;
	/**
	 * 星期设置
	 */
	protected String weekSet;
	/**
	 * 活动内容
	 */
	protected String giftContent;
	/**
	 * 送礼类型
	 */
	protected String giftTypes;
	/**
	 * 小时数
	 */
	protected int hourNumber;
	/**
	 * 小时数的类型
	 */
	protected String hourType;
	/**
	 * 送礼方式
	 */
	protected String wayOfGiving;
	/**
	 * 其他的送礼具体方式
	 */
	protected String wayOfGivingOther;

	public java.util.Date getStartDate() {
		return startDate;
	}

	public void setStartDate(java.util.Date startDate) {
		this.startDate = startDate;
	}

	public java.util.Date getEndDate() {
		return endDate;
	}

	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public String getWeekSet() {
		return weekSet;
	}

	public void setWeekSet(String weekSet) {
		this.weekSet = weekSet;
	}

	public String getGiftContent() {
		return giftContent;
	}

	public void setGiftContent(String giftContent) {
		this.giftContent = giftContent;
	}

	public String getGiftTypes() {
		return giftTypes;
	}

	public void setGiftTypes(String giftTypes) {
		this.giftTypes = giftTypes;
	}

	public int getHourNumber() {
		return hourNumber;
	}

	public void setHourNumber(int hourNumber) {
		this.hourNumber = hourNumber;
	}

	public String getHourType() {
		return hourType;
	}

	public void setHourType(String hourType) {
		this.hourType = hourType;
	}

	public String getWayOfGiving() {
		return wayOfGiving;
	}

	public void setWayOfGiving(String wayOfGiving) {
		this.wayOfGiving = wayOfGiving;
	}

	public String getWayOfGivingOther() {
		return wayOfGivingOther;
	}

	public void setWayOfGivingOther(String wayOfGivingOther) {
		this.wayOfGivingOther = wayOfGivingOther;
	}

}

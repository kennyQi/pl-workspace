package hsl.pojo.qo.notice;
import hg.common.component.BaseQo;
public class HslNoticeQO extends BaseQo{
	private static final long serialVersionUID = 1L;
	/**
	 * 标题
	 */
	private String title;
	private String beginTime;
	private String endTime;
	private String cutOffTime;
	/**
	 * 审核状态
	 */
	private Integer checkedStatus;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getCutOffTime() {
		return cutOffTime;
	}
	public void setCutOffTime(String cutOffTime) {
		this.cutOffTime = cutOffTime;
	}
	public Integer getCheckedStatus() {
		return checkedStatus;
	}
	public void setCheckedStatus(Integer checkedStatus) {
		this.checkedStatus = checkedStatus;
	}
}

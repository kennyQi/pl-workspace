package hsl.pojo.command;
import hg.common.component.BaseCommand;
/**
 * @类功能说明：修改公告Command
 * @类修改者：
 * @修改日期：2014年12月15日下午1:55:12
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年12月15日下午1:55:12
 *
 */
@SuppressWarnings("serial")
public class UpdateNoticeCommand extends BaseCommand {
	/**
	 * 公告ID
	 */
	private String id;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 截止时间
	 */
	private String cutOffTime;
	/**
	 * 审核状态
	 */
	private int checkedStatus;
	/**
	 * 详情
	 */
	private String details;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCutOffTime() {
		return cutOffTime;
	}
	public void setCutOffTime(String cutOffTime) {
		this.cutOffTime = cutOffTime;
	}
	public int getCheckedStatus() {
		return checkedStatus;
	}
	public void setCheckedStatus(int checkedStatus) {
		this.checkedStatus = checkedStatus;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
}

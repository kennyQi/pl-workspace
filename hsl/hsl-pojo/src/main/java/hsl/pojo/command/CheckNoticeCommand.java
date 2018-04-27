package hsl.pojo.command;
/**
 * @类功能说明：审核公告command
 * @类修改者：
 * @修改日期：2014年12月18日下午5:24:10
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年12月18日下午5:24:10
 *
 */
public class CheckNoticeCommand {
	/**
	 * 公告ID
	 */
	private String id;
	private int checkedStatus;
	/**
	 * 审核说明
	 */
	private String chcekDeclare;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getChcekDeclare() {
		return chcekDeclare;
	}
	public void setChcekDeclare(String chcekDeclare) {
		this.chcekDeclare = chcekDeclare;
	}
	public int getCheckedStatus() {
		return checkedStatus;
	}
	public void setCheckedStatus(int checkedStatus) {
		this.checkedStatus = checkedStatus;
	}
}

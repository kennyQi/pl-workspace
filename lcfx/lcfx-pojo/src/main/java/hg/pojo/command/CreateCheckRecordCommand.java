package hg.pojo.command;

import java.util.Date;

@SuppressWarnings("serial")
public class CreateCheckRecordCommand extends JxcCommand {
	/**
	 * 所属对象的id
	 */
	private String belongTo;

	/**
	 * 状态
	 */
	private int status;

	/**
	 * 审核人名字
	 */
	private String checkUser;

	/**
	 * 审核日期
	 */
	private Date checkDate;

	/**
	 * 审核内容
	 */
	private String checkOpinion;

	public String getBelongTo() {
		return belongTo;
	}

	public void setBelongTo(String belongTo) {
		this.belongTo = belongTo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCheckUser() {
		return checkUser;
	}

	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public String getCheckOpinion() {
		return checkOpinion;
	}

	public void setCheckOpinion(String checkOpinion) {
		this.checkOpinion = checkOpinion;
	}
}

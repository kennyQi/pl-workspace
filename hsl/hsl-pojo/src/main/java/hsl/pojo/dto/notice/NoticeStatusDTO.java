package hsl.pojo.dto.notice;

public class NoticeStatusDTO {
	/**
	 * 是否审核
	 */
	private int checkedStatus;
	/**
	 * 审核说明
	 */
	private String chcekDeclare;
	public int getCheckedStatus() {
		return checkedStatus;
	}
	public void setCheckedStatus(int checkedStatus) {
		this.checkedStatus = checkedStatus;
	}
	public String getChcekDeclare() {
		return chcekDeclare;
	}
	public void setChcekDeclare(String chcekDeclare) {
		this.chcekDeclare = chcekDeclare;
	}

}
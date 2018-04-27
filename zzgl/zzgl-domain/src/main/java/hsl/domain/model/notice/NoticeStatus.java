package hsl.domain.model.notice;
import hg.system.model.M;
import javax.persistence.Column;
import javax.persistence.Embeddable;
@Embeddable
public class NoticeStatus {
	/**
	 * 未审核
	 */
	public final static int CHECKEDSTATUS_NOCHECK=1;
	/**
	 * 审核通过
	 */
	public final static int CHECKEDSTATUS_PASSCHECK=2;
	/**
	 * 审核 拒绝
	 */
	public final static int CHECKEDSTATUS_REFUSECHECK=3;
	/**
	 * 审核状态
	 */
	@Column(name="CHECKEDSTATUS",columnDefinition=M.TYPE_NUM_COLUM)
	private Integer checkedStatus;
	/**
	 * 审核说明
	 */
	@Column(name="CHCEKDECLARE", columnDefinition = M.TEXT_COLUM)
	private String chcekDeclare;
	public Integer getCheckedStatus() {
		return checkedStatus;
	}
	public void setCheckedStatus(Integer checkedStatus) {
		this.checkedStatus = checkedStatus;
	}
	public String getChcekDeclare() {
		return chcekDeclare;
	}
	public void setChcekDeclare(String chcekDeclare) {
		this.chcekDeclare = chcekDeclare;
	}

}
package jxc.domain.model.system;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hg.pojo.command.CreateCheckRecordCommand;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import jxc.domain.model.M;

@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_JXC_SYETEM + "CHECK_RECORD")
public class CheckRecord extends BaseModel {

	/**
	 * 所属对象的id
	 */
	@Column(name = "BELONG_TO", length = 64)
	private String belongTo;

	/**
	 * 状态
	 */
	@Column(name = "STATUS", columnDefinition = M.TYPE_NUM_COLUM)
	private int status;

	/**
	 * 审核人名字
	 */
	@Column(name = "CHECK_USER", length = 32)
	private String checkUser;

	/**
	 * 审核日期
	 */
	@Column(name = "CREATE_TIME", columnDefinition = M.DATE_COLUM)
	private Date checkDate;

	/**
	 * 审核内容
	 */
	@Column(name = "CHECK_OPINION", length = 255)
	private String checkOpinion;

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

	public String getBelongTo() {
		return belongTo;
	}

	public void setBelongTo(String belongTo) {
		this.belongTo = belongTo;
	}

	public void createCheckRecord(CreateCheckRecordCommand command) {
		setId(UUIDGenerator.getUUID());

		setBelongTo(command.getBelongTo());
		setCheckDate(command.getCreateDate());
		setCheckOpinion(command.getCheckOpinion());
		setCheckUser(command.getCheckUser());
		
		setStatus(command.getStatus());
	}
}

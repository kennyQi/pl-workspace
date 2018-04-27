package lxs.domain.model.line;

import hg.common.component.BaseModel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lxs.domain.model.M;

@Entity
@Table(name = M.TABLE_PREFIX_XL + "LINE_SUBJECT")
@SuppressWarnings("serial")
public class LineSubject extends BaseModel {
	/**
	 * 主题ID
	 */
	@Column(name = "SUBJECT_ID", length = 64)
	private String subjectID;

	/**
	 * 线路ID 当线路删除或者下架时应删除关联信息
	 */
	@Column(name = "LINE_ID", length = 64)
	private String lineID;

	/**
	 * 时间
	 */
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;

	public String getSubjectID() {
		return subjectID;
	}

	public void setSubjectID(String subjectID) {
		this.subjectID = subjectID;
	}

	public String getLineID() {
		return lineID;
	}

	public void setLineID(String lineID) {
		this.lineID = lineID;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}

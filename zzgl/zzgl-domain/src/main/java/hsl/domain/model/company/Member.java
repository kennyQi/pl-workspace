package hsl.domain.model.company;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import hg.common.component.BaseModel;
import hsl.domain.model.M;
/**
 * 
 * @类功能说明：部门员工通讯录成员
 * @类修改者：
 * @修改日期：2014年9月16日下午4:22:43
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年9月16日下午4:22:43
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name=M.TABLE_PREFIX_HSL_COMPANY+"MEMBER")
public class Member extends BaseModel{
	/*
	 * 成员名称
	 */
	@Column(name="NAME" ,length=64)
	private String name;
	/*
	 * 职务
	 */
	@Column(name="JOB" ,length=64)
	private String job;
	/*
	 * 手机号
	 */
	@Column(name="MOBILEPHONE" ,length=20)
	private String mobilePhone;
	/*
	 * 身份证ID
	 */
	@Column(name="CERTIFICATEID" ,length=30)
	private String certificateID;
	/*
	 * 标识该成员是否已离职	0为在职，1为离职
	 */
	@Column(name="IDDEL" ,length=4)
	private int isDel;
	/*
	 * 部门外键
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "DEPARTMENT_ID")
	private Department department;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getCertificateID() {
		return certificateID;
	}
	public void setCertificateID(String certificateID) {
		this.certificateID = certificateID;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public int getIsDel() {
		return isDel;
	}
	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}
}

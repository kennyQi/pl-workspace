package hsl.domain.model.sign;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import hg.common.component.BaseModel;
import hsl.domain.model.M;

/**
 * 
 * @类功能说明：
 * @类修改者：hgg
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：hgg
 * @创建时间：2015年9月17日下午2:27:14
 *
 */
@Entity
@Table(name=M.TABLE_PREFIX_HSL_USER+"SIGN")
public class Sign extends BaseModel{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 姓名
	 */
	@Column(name="NAME", length = 64)
	private String  name;
	
	/**
	 * 手机
	 */
	@Column(name="MOBILE", length = 64)
	private String  mobile;
	
	/**
	 * 部门
	 */
	@Column(name="DEPARTMENT", length = 64)
	private String  department;
	
	/**
	 * 职务
	 */
	@Column(name="JOB", length = 64)
	private String  job;
	
	/**
	 * 活动名称
	 */
	@Column(name="ACTIVITYNAME", length = 64)
	private String  activityName;
	
	/**
	 * 是否已经签到
	 */
	@Type(type = "yes_no")
	@Column(name="SIGN")
	private boolean signed ;
	
	/**
	 * 签到时间
	 */
	@Column(name="SIGNTIME", columnDefinition=M.DATE_COLUM)
	private Date    signTime;
	
	/**
	 * 身份证
	 */
	@Column(name="sID", length = 64)
	private String  sID;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public boolean isSigned() {
		return signed;
	}

	public void setSigned(boolean signed) {
		this.signed = signed;
	}

	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	public String getsID() {
		return sID;
	}

	public void setsID(String sID) {
		this.sID = sID;
	}

}

package hsl.pojo.qo;

import hg.common.component.BaseQo;

/**
 * 
 * @类功能说明：签到查询Qo
 * @类修改者：hgg
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：hgg
 * @创建时间：2015年9月17日下午2:52:39
 *
 */
public class SignQo extends BaseQo{

	private static final long serialVersionUID = 1260228379067272913L;

	/**
	 * 姓名
	 */
	private String  name;
	
	/**
	 * 手机
	 */
	private String  mobile;
	
	/**
	 * 部门
	 */
	private String  department;
	
	/**
	 * 职务
	 */
	private String  job;
	
	/**
	 * 是否已经签到
	 */
	private Boolean signed;
	
	/**
	 * 身份证
	 */
	private String  sID;
	
	/**
	 * 活动名称
	 */
	private String  activityName;

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

	public Boolean getSigned() {
		return signed;
	}

	public void setSigned(Boolean signed) {
		this.signed = signed;
	}

	public String getsID() {
		return sID;
	}

	public void setsID(String sID) {
		this.sID = sID;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	
}

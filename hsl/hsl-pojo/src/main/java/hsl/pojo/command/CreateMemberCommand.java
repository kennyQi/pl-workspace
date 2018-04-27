package hsl.pojo.command;
/**
 * 创建成员COMD
 * @author zhuxy
 *
 */
public class CreateMemberCommand {
	/**
	 * 成员名称
	 */
	private String name;
	/**
	 * 职务
	 */
	private String job;
	/**
	 * 手机号
	 */
	private String mobilePhone;
	/**
	 * 身份证ID
	 */
	private String certificateID;
	/**
	 * 标识该成员是否已离职
	 */
	private int isDel;
	/**
	 * 所属部门ID
	 */
	private String deptId;
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
	public int getIsDel() {
		return isDel;
	}
	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	
}

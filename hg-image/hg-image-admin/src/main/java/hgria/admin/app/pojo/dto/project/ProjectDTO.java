package hgria.admin.app.pojo.dto.project;

import hg.system.dto.auth.AuthStaffDTO;
import hgria.admin.app.pojo.dto.BaseDTO;

/**
 * @类功能说明：工程_DTO
 * @类修改者：zzb
 * @修改日期：2014年11月28日下午4:27:39
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年11月28日下午4:27:39
 */
@SuppressWarnings("serial")
public class ProjectDTO extends BaseDTO {

	/**
	 * 工程名称
	 */
	private String name;

	/**
	 * 工程环境
	 */
	private String envName;
	
	/**
	 * 工程描述
	 */
	private String desc;

	/**
	 * 工程和人员关联
	 */
	private AuthStaffDTO staff;

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public AuthStaffDTO getStaff() {
		return staff;
	}

	public void setStaff(AuthStaffDTO staff) {
		this.staff = staff;
	}

	public String getEnvName() {
		return envName;
	}

	public void setEnvName(String envName) {
		this.envName = envName;
	}

}
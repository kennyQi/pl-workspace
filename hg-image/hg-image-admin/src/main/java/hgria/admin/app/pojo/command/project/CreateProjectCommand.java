package hgria.admin.app.pojo.command.project;

import hgria.admin.app.pojo.command.BaseCommand;

/**
 * @类功能说明：创建工程_command
 * @类修改者：zzb
 * @修改日期：2014年11月28日下午4:52:59
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年11月28日下午4:52:59
 */
@SuppressWarnings("serial")
public class CreateProjectCommand extends BaseCommand {

	/**
	 * 工程id
	 */
	private String projectId;

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
	 * 工程对应操作员
	 */
	private String staffId;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

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

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getEnvName() {
		return envName;
	}

	public void setEnvName(String envName) {
		this.envName = envName;
	}

}

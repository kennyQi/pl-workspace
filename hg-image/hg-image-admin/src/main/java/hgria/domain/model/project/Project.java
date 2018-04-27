package hgria.domain.model.project;

import hg.common.component.BaseModel;
import hg.system.model.staff.Staff;
import hgria.admin.app.pojo.command.project.CreateProjectCommand;
import hgria.admin.app.pojo.command.project.ModifyProjectCommand;
import hgria.admin.app.pojo.exception.IMAGEException;
import hgria.domain.model.SystemBaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

/**
 * @类功能说明：工程表
 * @类修改者：zzb
 * @修改日期：2014年11月28日下午3:34:41
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年11月28日下午3:34:41
 */
@Entity
@Table(name = SystemBaseModel.TABLE_PREFIX + "PROJECT")
public class Project extends BaseModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 工程名称
	 */
	@Column(name = "PROJECT_NAME", length = 128)
	private String name;
	
	/**
	 * 工程环境
	 */
	@Column(name = "PROJEC_ENV_NAME", length = 128)
	private String envName;
	
	/**
	 * 工程描述
	 */
	@Column(name = "PROJECT_DESC", length = 128)
	private String desc;
	
	/**
	 * 工程和人员关联
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STAFF_ID")
	private Staff staff;
	
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public String getEnvName() {
		return envName;
	}

	public void setEnvName(String envName) {
		this.envName = envName;
	}
	
	public void createProject(CreateProjectCommand command, Staff staff) throws IMAGEException {
		
		// 1. 检查
		String requiredName = "";
		if (StringUtils.isBlank(command.getProjectId()))
			requiredName = "工程id";
		else if (StringUtils.isBlank(command.getName()))
			requiredName = "工程名称";
		else if (StringUtils.isBlank(command.getStaffId()) || staff == null)
			requiredName = "工程管理员";

		if (requiredName.length() > 0)
			throw new IMAGEException(
					IMAGEException.PROJECT_CREATE_NOT_REQUIRED,
					requiredName + "不能为空");

		// 2. 设置属性
		setId(command.getProjectId());
		setName(command.getName());
		setEnvName(command.getEnvName());
		setDesc(command.getDesc());
		setStaff(staff);
	}

	public void modifyProject(ModifyProjectCommand command) throws IMAGEException {
		
		// 1. 检查
		String requiredName = "";
		if (StringUtils.isBlank(command.getProjectId()))
			requiredName = "工程id";
		else if (StringUtils.isBlank(command.getName()))
			requiredName = "工程名称";
		else if (StringUtils.isBlank(command.getStaffId()) || staff == null)
			requiredName = "工程管理员";
	
		if (requiredName.length() > 0)
			throw new IMAGEException(
					IMAGEException.PROJECT_CREATE_NOT_REQUIRED,
					requiredName + "不能为空");
	
		// 2. 设置属性
		setId(command.getProjectId());
		setName(command.getName());
		setEnvName(command.getEnvName());
		setDesc(command.getDesc());
		setStaff(staff);
	}

}

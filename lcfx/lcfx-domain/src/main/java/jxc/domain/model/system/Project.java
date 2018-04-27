package jxc.domain.model.system;

import java.util.Date;

import hg.common.util.UUIDGenerator;
import hg.pojo.command.CreateProjectCommand;
import hg.pojo.command.ModifyProjectCommand;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.M;

/**
 * 项目表
 * 
 * @author liujz
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_JXC_SYETEM + "PROJECT")
public class Project extends JxcBaseModel {
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;
	/**
	 * 项目名称
	 */
	@Column(name = "PROJECT_NAME", length = 50)
	private String name;

	/**
	 * 密钥
	 */
	@Column(name = "PROJECT_KEY", length = 50)
	private String key;

	/**
	 * 推送接口地址
	 */
	@Column(name = "PROJECT_POST_URL", length = 100)
	private String postUrl;

	/**
	 * 项目模式
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PROJECT_MODE_ID")
	private ProjectMode projectMode;

	/**
	 * 项目模式名称
	 */
	@Column(name = "PROJECT_MODE_NAME", length = 20)
	private String projectModeName;

	/**
	 * 项目类型
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PROJECT_TYPE_ID")
	private ProjectType projectType;

	/**
	 * 项目类型名称
	 */
	@Column(name = "PROJECT_TYPE_NAME", length = 20)
	private String projectTypeName;

	/**
	 * 运营模式
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "OPERATION_FORM_ID")
	private OperationForm operationForm;

	/**
	 * 运营模式名称
	 */
	@Column(name = "OPERATION_FORM_NAME", length = 20)
	private String operationFormName;

	/**
	 * 项目负责人
	 */
	@Column(name = "PROJECT_LEADER", length = 10)
	private String projectLeader;

	/**
	 * 联系电话
	 */
	@Column(name = "PROJECT_LINK_PHONE", length = 20)
	private String linkPhone;

	/**
	 * 联系邮箱
	 */
	@Column(name = "PROJECT_LINK_EMAIL", length = 100)
	private String linkEmail;

	/**
	 * 备注
	 */
	@Column(name = "PROJECT_REMARK", length = 255)
	private String remark;

	/**
	 * 是否开启接口
	 */
	@Embedded
	private ProjectStatus status;

	/**
	 * 商品库参数
	 */
	@Column(name = "PDB_NAME", length = 64)
	private String pdbName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getPostUrl() {
		return postUrl;
	}

	public void setPostUrl(String postUrl) {
		this.postUrl = postUrl;
	}

	public ProjectMode getProjectMode() {
		return projectMode;
	}

	public void setProjectMode(ProjectMode projectMode) {
		this.projectMode = projectMode;
	}

	public ProjectType getProjectType() {
		return projectType;
	}

	public void setProjectType(ProjectType projectType) {
		this.projectType = projectType;
	}

	public OperationForm getOperationForm() {
		return operationForm;
	}

	public void setOperationForm(OperationForm operationForm) {
		this.operationForm = operationForm;
	}

	public String getProjectLeader() {
		return projectLeader;
	}

	public void setProjectLeader(String projectLeader) {
		this.projectLeader = projectLeader;
	}

	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	public String getLinkEmail() {
		return linkEmail;
	}

	public void setLinkEmail(String linkEmail) {
		this.linkEmail = linkEmail;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public ProjectStatus getStatus() {
		return status;
	}

	public void setStatus(ProjectStatus status) {
		this.status = status;
	}

	public String getProjectModeName() {
		return projectModeName;
	}

	public void setProjectModeName(String projectModeName) {
		this.projectModeName = projectModeName;
	}

	public String getProjectTypeName() {
		return projectTypeName;
	}

	public void setProjectTypeName(String projectTypeName) {
		this.projectTypeName = projectTypeName;
	}

	public String getOperationFormName() {
		return operationFormName;
	}

	public void setOperationFormName(String operationFormName) {
		this.operationFormName = operationFormName;
	}

	public void createProject(CreateProjectCommand command, ProjectType type, ProjectMode model, OperationForm form) {
		status = new ProjectStatus();
		status.setUsing(command.getUsing());
		setId(UUIDGenerator.getUUID());
		setKey(command.getKey());
		setLinkEmail(command.getLinkEmail());
		setLinkPhone(command.getLinkPhone());
		setName(command.getName());
		setOperationForm(form);
		setPostUrl(command.getPostUrl());
		setProjectLeader(command.getProjectLeader());
		setOperationFormName(form.getName());
		setProjectModeName(model.getName());
		setProjectTypeName(type.getName());
		setProjectMode(model);
		setProjectType(type);
		setRemark(command.getRemark());
		setStatus(status);

		setCreateDate(new Date());
		setStatusRemoved(false);

		setPdbName(command.getPdbName());

	}

	public void modifyProject(ModifyProjectCommand command, ProjectType type, ProjectMode model, OperationForm form) {

		status.setUsing(command.getUsing());
		setId(command.getProjectId());
		setKey(command.getKey());
		setLinkEmail(command.getLinkEmail());
		setLinkPhone(command.getLinkPhone());
		setName(command.getName());
		setOperationForm(form);
		setPostUrl(command.getPostUrl());
		setProjectLeader(command.getProjectLeader());
		setOperationFormName(form.getName());
		setProjectModeName(model.getName());
		setProjectTypeName(type.getName());
		setProjectMode(model);
		setProjectType(type);
		setRemark(command.getRemark());
		setStatus(status);

		setCreateDate(new Date());

		setPdbName(command.getPdbName());
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getPdbName() {
		return pdbName;
	}

	public void setPdbName(String pdbName) {
		this.pdbName = pdbName;
	}

}

package hg.framework.common.base;

import hg.framework.common.util.UUIDGenerator;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class BaseSagaEvent extends BaseObject implements Serializable {

	private String id;

	/**
	 * 事件所属流程id
	 */
	private String sagaId;

	/**
	 * model名加方法名，如ProjectUser.create
	 */
	private String name;

	/**
	 * 参数json串 CreateProjectUserCommand command 当有多个参数时，记录为json1,json2...
	 */
	private String params;

	/**
	 * 事件文字描述
	 */
	private String describe;

	/**
	 * 创建时间
	 */
	private Date createDate;

	/**
	 * 来源项目
	 */
	private String projectId;

	/**
	 * 来源环境
	 */
	private String envId;

	public BaseSagaEvent() {
		super();
	}

	public BaseSagaEvent(String name, String params, String describe,
						 String projectId, String envId) {
		super();
		this.name = name;
		this.params = params;
		this.describe = describe;
		this.projectId = projectId;
		this.envId = envId;
		this.createDate = new Date();
		this.id = UUIDGenerator.getUUID();
	}

	public String getSagaId() {
		return sagaId;
	}

	public void setSagaId(String sagaId) {
		this.sagaId = sagaId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getEnvId() {
		return envId;
	}

	public void setEnvId(String envId) {
		this.envId = envId;
	}

}

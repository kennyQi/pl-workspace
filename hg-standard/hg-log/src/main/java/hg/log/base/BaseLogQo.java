package hg.log.base;

import java.util.Date;

public class BaseLogQo {

	/**
	 * ID
	 */
	private String id;

	/**
	 * 项目id
	 */
	private String projectId;

	/**
	 * 环境id
	 */
	private String envId;

	/**
	 * 创建时间
	 */
	private Boolean createDateDesc;
	private Date createDateBegin;
	private Date createDateEnd;

	/**
	 * 搜索关键字
	 */
	private String[] tags;

	/**
	 * 需要查询的字段
	 */
	private String[] include;

	/**
	 * 需要排除的字段（当不为空时，忽略需要查询的字段）
	 */
	private String[] exclude;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Boolean getCreateDateDesc() {
		return createDateDesc;
	}

	public void setCreateDateDesc(Boolean createDateDesc) {
		this.createDateDesc = createDateDesc;
	}

	public Date getCreateDateBegin() {
		return createDateBegin;
	}

	public void setCreateDateBegin(Date createDateBegin) {
		this.createDateBegin = createDateBegin;
	}

	public Date getCreateDateEnd() {
		return createDateEnd;
	}

	public void setCreateDateEnd(Date createDateEnd) {
		this.createDateEnd = createDateEnd;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public String[] getInclude() {
		return include;
	}

	public void setInclude(String[] include) {
		this.include = include;
	}

	public String[] getExclude() {
		return exclude;
	}

	public void setExclude(String[] exclude) {
		this.exclude = exclude;
	}

}

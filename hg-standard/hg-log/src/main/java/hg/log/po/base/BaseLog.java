package hg.log.po.base;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class BaseLog {

	@Id
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
	@Indexed(direction = IndexDirection.DESCENDING)
	private Date createDate;

	/**
	 * 搜索关键字(AND条件)
	 */
	private String[] tags;

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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String[] getTags() {
		return tags;
	}
	public void setTags(String[] tags) {
		this.tags = tags;
	}
}
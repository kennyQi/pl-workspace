package hg.service.image.domain.model.base;

import hg.common.component.BaseModel;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @类功能说明：图片服务父类Model
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：
 * @创建时间：2014年9月1日下午2:44:35
 */
@MappedSuperclass
public class ImageBaseModel extends BaseModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 工程ID
	 */
	@Column(name = "PROJECT_ID", length = 64)
	private String projectId;

	/**
	 * 环境名称
	 */
	@Column(name = "ENV_NAME", length = 64)
	private String envName;

	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId == null ? null : projectId.trim();
	}
	public String getEnvName() {
		return envName;
	}
	public void setEnvName(String envName) {
		this.envName = envName == null ? null : envName.trim();
	}
}
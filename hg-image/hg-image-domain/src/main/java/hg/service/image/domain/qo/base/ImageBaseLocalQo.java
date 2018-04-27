package hg.service.image.domain.qo.base;

import hg.common.component.BaseQo;

/**
 * @类功能说明：图片服务父类Qo
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：
 * @创建时间：2014年9月1日下午2:44:35
 */
public class ImageBaseLocalQo extends BaseQo {
	private static final long serialVersionUID = 1L;

	/**
	 * 工程ID
	 */
	private String projectId;

	/**
	 * 环境名称
	 */
	private String envName;
	
	public ImageBaseLocalQo(){		
	}
	public ImageBaseLocalQo(String projectId, String envName) {
		super();
		this.setProjectId(projectId);
		this.setEnvName(envName);
	}
	public ImageBaseLocalQo(String id) {
		super();
		super.setId(id);
	}

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
package hg.service.image.pojo.qo;

import java.io.Serializable;
import java.util.List;

/**
 * @类功能说明：图片服务基础_qo
 * @类修改者：zzb
 * @修改日期：2014年12月1日下午2:48:49
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年12月1日下午2:48:49
 */
public class ImageBaseQo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 别名
	 */
	private String alias;
	/**
	 * 需要保留的字段
	 */
	private String[] projectionPropertys;
	// ------------------延迟加载条件------------------
	// ------------------排序条件------------------
	// ------------------属性条件------------------
	/**
	 * 实体ID
	 */
	private String id;
	/**
	 * 实体ID集合
	 */
	private List<String> ids;
	// ------------------不包含的属性条件------------------
	/**
	 * 不包含的ID集合
	 */
	private String[] idNotIn;

	// ------------------状态类条件------------------

	// 分页条件
	private Integer pageNo;
	private Integer pageSize;
	
	/**
	 * 工程ID
	 */
	private String projectId;

	/**
	 * 环境名称
	 */
	private String envName;

	public ImageBaseQo(){}
	public ImageBaseQo(String projectId, String envName) {
		super();
		this.setProjectId(projectId);
		this.setEnvName(envName);
	}
	public ImageBaseQo(String id) {
		super();
		this.setId(id);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}
	public String[] getIdNotIn() {
		return idNotIn;
	}
	public void setIdNotIn(String[] idNotIn) {
		this.idNotIn = idNotIn;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String[] getProjectionPropertys() {
		return projectionPropertys;
	}
	public void setProjectionPropertys(String[] projectionPropertys) {
		this.projectionPropertys = projectionPropertys;
	}
	public List<String> getIds() {
		return ids;
	}
	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
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
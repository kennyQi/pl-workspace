package hg.system.dto.album;

import hg.system.dto.EmbeddDTO;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年9月1日下午2:44:35
 */
@SuppressWarnings("serial")
public class AlbumDTO extends EmbeddDTO {

	/**
	 * 相册id
	 */
	private String id;
	
	/**
	 * 相册标题
	 */
	private String title;

	/**
	 * 相册备注
	 */
	private String remark;

	/**
	 * 所有者id
	 */
	private String ownerId;

	/**
	 * 例 景区联盟：JQLM
	 */
	private String projectId;

	/**
	 * 例 联盟主站相册:001 景点专属相册：002
	 */
	private Integer useType;

	/**
	 * 上级节点
	 */
	private AlbumDTO parent;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Integer getUseType() {
		return useType;
	}

	public void setUseType(Integer useType) {
		this.useType = useType;
	}

	public AlbumDTO getParent() {
		return parent;
	}

	public void setParent(AlbumDTO parent) {
		this.parent = parent;
	}

}

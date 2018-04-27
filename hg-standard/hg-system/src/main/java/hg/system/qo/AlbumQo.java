package hg.system.qo;

import hg.common.model.qo.DwzBasePaginQo;

/**
 * 
 * @类功能说明：相册查询类
 * @类修改者：zzb
 * @修改日期：2014年9月1日下午4:25:10
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年9月1日下午4:25:10
 * 
 */
public class AlbumQo extends DwzBasePaginQo {
	private static final long serialVersionUID = 1L;

	/**
	 * 相册标题
	 */
	private String title;
	private boolean titleLike;

	/**
	 * 上级节点id
	 */
	private AlbumQo parent;

	/**
	 * 是否是根节点
	 */
	private boolean isRoot = false;

	/**
	 * 左侧(上级)节点id(不参与检索)
	 */
	private String leftId;

	/**
	 * 例 景区联盟：JQLM
	 */
	private String projectId;
	private boolean projectIdLike;

	/**
	 * 例 联盟主站相册:001 景点专属相册：002
	 */
	private Integer useType;

	/**
	 * 所有者id
	 */
	private String ownerId;

	/**
	 * 所有者下属ID
	 */
	private String ownerItemId;

	public String getLeftId() {
		return leftId;
	}

	public void setLeftId(String leftId) {
		this.leftId = leftId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isTitleLike() {
		return titleLike;
	}

	public void setTitleLike(boolean titleLike) {
		this.titleLike = titleLike;
	}

	public AlbumQo getParent() {
		return parent;
	}

	public void setParent(AlbumQo parent) {
		this.parent = parent;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public boolean isProjectIdLike() {
		return projectIdLike;
	}

	public void setProjectIdLike(boolean projectIdLike) {
		this.projectIdLike = projectIdLike;
	}

	public Integer getUseType() {
		return useType;
	}

	public void setUseType(Integer useType) {
		this.useType = useType;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerItemId() {
		return ownerItemId;
	}

	public void setOwnerItemId(String ownerItemId) {
		this.ownerItemId = ownerItemId;
	}

	public boolean isRoot() {
		return isRoot;
	}

	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}

}

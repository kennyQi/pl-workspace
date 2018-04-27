package hg.system.command.album;

import hg.common.component.BaseCommand;

/**
 * 
 * @类功能说明：相册 添加 command
 * @类修改者：zzb
 * @修改日期：2014年9月1日下午6:07:50
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年9月1日下午6:07:50
 *
 */
@SuppressWarnings("serial")
public class AlbumCreateCommand extends BaseCommand {

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
	 * 所有者下属ID
	 */
	private String ownerItemId;

	/**
	 * 例 景区联盟：JQLM
	 */
	private String projectId;

	/**
	 * 例 联盟主站相册:001 景点专属相册：002
	 */
	private Integer useType;
	
	/**
	 * 上级节点id
	 */
	private String parentId;

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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getOwnerItemId() {
		return ownerItemId;
	}

	public void setOwnerItemId(String ownerItemId) {
		this.ownerItemId = ownerItemId;
	}

}

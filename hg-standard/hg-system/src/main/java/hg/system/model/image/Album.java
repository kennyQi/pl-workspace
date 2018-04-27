package hg.system.model.image;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hg.system.command.album.AlbumCreateCommand;
import hg.system.command.album.AlbumModifyCommand;
import hg.system.model.M;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年9月1日下午2:44:35
 * 
 */
@Entity
@Table(name = M.TABLE_PREFIX_SYS + "ALBUM")
@SuppressWarnings("serial")
public class Album extends BaseModel {

	/**
	 * 相册标题
	 */
	@Column(name = "TITLE", length = 512)
	private String title;

	/**
	 * 相册备注
	 */
	@Column(name = "REMARK", columnDefinition = M.TEXT_COLUM)
	private String remark;

	/**
	 * 所有者id
	 */
	@Column(name = "OWNER_ID", length = 60)
	private String ownerId;

	/**
	 * 所有者下属ID
	 */
	@Column(name = "OWNER_ITEM_ID", length = 60)
	private String ownerItemId;

	/**
	 * 例 景区联盟：JQLM
	 */
	@Column(name = "PROJECT_ID", length = 60)
	private String projectId;

	/**
	 * 例 联盟主站相册:001 景点专属相册：002
	 */
	@Column(name = "USE_TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer useType;

	/**
	 * 上级节点
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_ID")
	private Album parent;

	/**
	 * 
	 * @方法功能说明：创建相册
	 * @修改者名字：zzb
	 * @修改时间：2014年9月2日上午10:06:26
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@param albumPar
	 * @return:void
	 * @throws
	 */
	public void albumCreate(AlbumCreateCommand command, Album albumPar) {
		setId(UUIDGenerator.getUUID());
		setTitle(command.getTitle());
		setRemark(command.getRemark());
		setProjectId(command.getProjectId());
		setUseType(command.getUseType());
		setOwnerId(command.getOwnerId());
		setOwnerItemId(command.getOwnerItemId());
		setParent(albumPar);
	}

	/**
	 * 
	 * @方法功能说明：保存相册
	 * @修改者名字：zzb
	 * @修改时间：2014年9月2日上午11:59:05
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@param albumPar
	 * @return:void
	 * @throws
	 */
	public void albumtModify(AlbumModifyCommand command, Album albumPar) {

		setTitle(command.getTitle());
		setRemark(command.getRemark());
		setProjectId(command.getProjectId());
		setUseType(command.getUseType());
		setParent(albumPar);

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

	public Album getParent() {
		return parent;
	}

	public void setParent(Album parent) {
		this.parent = parent;
	}

	public String getOwnerItemId() {
		return ownerItemId;
	}

	public void setOwnerItemId(String ownerItemId) {
		this.ownerItemId = ownerItemId;
	}

}

package hg.system.model.image;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hg.system.command.image.ImageCreateCommand;
import hg.system.command.image.ImageCreateTempCommand;
import hg.system.command.image.ImageModifyCommand;
import hg.system.model.M;

import java.util.Date;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @类功能说明：一个Image代表一张内容相同的图片，以及它的一组大小不同的文件集合
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年8月15日下午2:03:26
 */
@Entity
@Table(name = M.TABLE_PREFIX_SYS + "IMAGE")
@SuppressWarnings("serial")
public class Image extends BaseModel {

	/**
	 * 图片标题
	 */
	@Column(name = "TITLE", length = 512)
	private String title;

	/**
	 * 上传的原图大小(单位byte)
	 */
	@Column(name = "SRC_FILE_SIZE", columnDefinition = M.NUM_COLUM)
	private Integer sourceFileSize = 0;

	/**
	 * 不同规格图片集合
	 */
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "IMAGE_ID")
	@MapKey(name = "key")
	Map<String, ImageSpec> specImageMap;

	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;

	/**
	 * 图片备注说明
	 */
	@Column(name = "REMARK", columnDefinition = M.TEXT_COLUM)
	private String remark;

	/**
	 * 归属者
	 */
	@Column(name = "OWNER_ID", length = 60)
	private String ownerId;

	/**
	 * 相册
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ALBUM_ID")
	private Album album;

	/**
	 * 
	 * @方法功能说明：图片_添加属性
	 * @修改者名字：zzb
	 * @修改时间：2014年9月3日上午10:39:38
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@param image
	 * @return:void
	 * @throws
	 */
	public void imageCreate(ImageCreateCommand command, Album album) {

		setId(UUIDGenerator.getUUID());
		setTitle(command.getTitle());
		setCreateDate(new Date());
		setRemark(command.getRemark());
		setAlbum(album);

	}

	/**
	 * 
	 * @方法功能说明：创建存储在临时相册下面的图片
	 * @修改者名字：zzb
	 * @修改时间：2014年9月25日下午5:53:43
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@param album2
	 * @return:void
	 * @throws
	 */
	public void imageCreateTemp(ImageCreateTempCommand command, Album album) {

		setId(UUIDGenerator.getUUID());
		setTitle(command.getTitle());
		setCreateDate(new Date());
		setAlbum(album);

	}

	/**
	 * 
	 * @方法功能说明：
	 * @修改者名字：zzb
	 * @修改时间：2014年9月3日上午11:05:30
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@param album2
	 * @return:void
	 * @throws
	 */
	public void imageModify(ImageModifyCommand command, Album albumNew) {

		setTitle(command.getTitle());
		setRemark(command.getRemark());
		setAlbum(albumNew);

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public Map<String, ImageSpec> getSpecImageMap() {
		return specImageMap;
	}

	public void setSpecImageMap(Map<String, ImageSpec> specImageMap) {
		this.specImageMap = specImageMap;
	}

	public Integer getSourceFileSize() {
		return sourceFileSize;
	}

	public void setSourceFileSize(Integer sourceFileSize) {
		this.sourceFileSize = sourceFileSize;
	}

}
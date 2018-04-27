package hg.service.image.domain.model;

import hg.common.util.UUIDGenerator;
import hg.service.image.command.image.CreateImageCommand;
import hg.service.image.command.image.CreateTempImageCommand;
import hg.service.image.command.image.ModifyImageCommand;
import hg.service.image.domain.model.base.ImageBaseModel;
import hg.service.image.domain.model.base.M;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @类功能说明：图片——一个Image代表一张内容相同的图片，以及它的一组大小不同的文件集合
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年8月15日下午2:03:26
 */
@Entity(name = "imgImage")
@Table(name = M.TABLE_PREFIX + "IMAGE")
public class Image extends ImageBaseModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 所属相册
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ALBUM_ID")
	private Album album;

	/**
	 * 图片标题
	 */
	@Column(name = "TITLE", length = 512)
	private String title;

	/**
	 * 原图
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEFAULT_IMAGE_SPEC_ID")
	private ImageSpec defaultImageSpec;

	/**
	 * 图片名
	 */
	@Column(name = "NAME", length = 1024)
	private String name;

	/**
	 * 不同图片规格集合
	 */
	@OneToMany(mappedBy = "image")
	@MapKey(name = "key")
	private Map<String, ImageSpec> imageSpecMap;

	/**
	 * 图片备注说明
	 */
	@Column(name = "REMARK", columnDefinition = M.TEXT_COLUM)
	private String remark;

	/**
	 * 图片原始名称(加后缀名)
	 */
	@Column(name = "FILENAME", length = 64)
	private String fileName;

	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;

	/**
	 * 图片用途
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USE_TYPE_ID")
	private ImageUseType useType;

	/**
	 * 回收状态(0.未删除；1.已删除；2.附属删除)
	 */
	private Integer refuse;

	public final static Integer REFUSE_STATUS_NO = 0; // 未删除图片
	public final static Integer REFUSE_STATUS_YES = 1; // 回收站图片
	public final static Integer REFUSE_STATUS_CLEAR = 2; // 彻底删除待清理图片

	public Image() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @方法功能说明：创建图片指令
	 * @修改者名字：chenys
	 * @修改时间：2014年11月4日 下午2:50:39
	 * @修改内容：
	 * @param command
	 * @param fileSize
	 * @param fileMd5
	 * @param specMap
	 */
	public Image(CreateImageCommand command, Album album, ImageUseType useType,
			ImageSpec defaultImageSpec, List<ImageSpec> imageSpecs) {

		setId(UUIDGenerator.getUUID());

		setProjectId(command.getFromProjectId());
		setEnvName(command.getFromProjectEnvName());
		setTitle(command.getTitle());
		setFileName(command.getFileName());
		setRemark(command.getRemark());
		setCreateDate(new Date());// 服务器时间

		setAlbum(album);
		setDefaultImageSpec(defaultImageSpec);
		
		// 图片状态
		setRefuse(REFUSE_STATUS_NO);
		// 图片用途
		setUseType(useType);
		
		setImageSpecMap(new HashMap<String, ImageSpec>());

		// 如果有图片规格，则设置
		if (imageSpecs != null && imageSpecs.size() > 0) {
			for (ImageSpec imageSpec : imageSpecs) {
				imageSpecMap.put(imageSpec.getKey(), imageSpec);
			}
		}

		/*Map<String, ImageSpec> curImageSpecMap = Collections.synchronizedMap(new HashMap<String, ImageSpec>());

		// 如果有图片规格，则设置
		for (ImageSpec imageSpec : imageSpecs) {
			curImageSpecMap.put(imageSpec.getKey(), imageSpec);
		}
		setImageSpecMap(curImageSpecMap);*/
		
	}

	public Image(CreateTempImageCommand command, Album album,
			ImageUseType tempImageUseType, ImageSpec defaultImageSpec) {
		setId(UUIDGenerator.getUUID());

		setProjectId(command.getFromProjectId());
		setEnvName(command.getFromProjectEnvName());
		setTitle(command.getTitle());
		setCreateDate(new Date());// 服务器时间

		setAlbum(album);
		setDefaultImageSpec(defaultImageSpec);
		
		// 图片状态
		setRefuse(REFUSE_STATUS_NO);
		// 图片用途
		setUseType(tempImageUseType);
	}

	/**
	 * @方法功能说明：修改图片指令
	 * @修改者名字：chenys
	 * @修改时间：2014年11月4日 下午2:35:20
	 * @修改内容：
	 * @param command
	 */
	public void modify(ModifyImageCommand command, Album album) {
		setProjectId(command.getFromProjectId());
		setEnvName(command.getFromProjectEnvName());
		setTitle(command.getTitle());
		setFileName(command.getFileName());
		setRemark(command.getRemark());

		if (album != null) {
			setAlbum(album);
		}

		// 图片状态
		setRefuse(REFUSE_STATUS_NO);

	}

	/**
	 * @方法功能说明: 回收
	 */
	public void remove() {
		setRefuse(REFUSE_STATUS_YES);
	}

	/**
	 * @方法功能说明: 删除
	 */
	public void delete() {
		setRefuse(REFUSE_STATUS_CLEAR);
	}

	/**
	 * @方法功能说明: 还原
	 */
	public void restore() {
		setRefuse(REFUSE_STATUS_NO);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Map<String, ImageSpec> getImageSpecMap() {
		return imageSpecMap;
	}

	public void setImageSpecMap(Map<String, ImageSpec> imageSpecMap) {
		this.imageSpecMap = imageSpecMap;
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
		this.remark = remark == null ? null : remark.trim();
	}

	public ImageUseType getUseType() {
		return useType;
	}

	public void setUseType(ImageUseType useType) {
		this.useType = useType;
	}

	public ImageSpec getDefaultImageSpec() {
		return defaultImageSpec;
	}

	public void setDefaultImageSpec(ImageSpec defaultImageSpec) {
		this.defaultImageSpec = defaultImageSpec;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRefuse() {
		return refuse;
	}

	public void setRefuse(Integer refuse) {
		this.refuse = refuse;
	}

}
package hg.service.image.pojo.dto;

import hg.service.image.base.BaseDTO;

import java.util.Date;
import java.util.Map;

/**
 * @类功能说明：图片DTO——一个Image代表一张内容相同的图片，以及它的一组大小不同的文件集合
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014年8月15日下午2:03:26
 */
public class ImageDTO extends BaseDTO {
	private static final long serialVersionUID = 1L;

	/**
	 * 图片标题
	 */
	private String title;

	/**
	 * 上传的原图大小(单位byte)
	 */
	private int sourceFileSize;

	/**
	 * 图片原始名称(加后缀名)
	 */
	private String fileName;

	/**
	 * 不同图片规格集合
	 */
	private Map<String, ImageSpecDTO> specImageMap;

	/**
	 * 图片备注说明
	 */
	private String remark;

	/**
	 * 创建时间
	 */
	private Date createDate;

	/**
	 * 对应ID
	 */
	private String fromId;

	/**
	 * 所有者id
	 */
	private String ownerId;

	/**
	 * 用途
	 */
	private ImageUseTypeDTO useType;

	/**
	 * 是否回收(回收站)
	 */
	private int isRefuse;

	/**
	 * 层级路径
	 */
	private String waypath;

	/**
	 * 扩展参数
	 */
	private ExtArgsDTO extArgs;

	/**
	 * 所属相册
	 */
	private AlbumDTO album;

	public ExtArgsDTO getExtArgs() {
		return extArgs;
	}

	public void setExtArgs(ExtArgsDTO extArgs) {
		this.extArgs = extArgs;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getSourceFileSize() {
		return sourceFileSize;
	}

	public void setSourceFileSize(int sourceFileSize) {
		this.sourceFileSize = sourceFileSize;
	}

	public Map<String, ImageSpecDTO> getSpecImageMap() {
		return specImageMap;
	}

	public void setSpecImageMap(Map<String, ImageSpecDTO> specImageMap) {
		this.specImageMap = specImageMap;
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

	public ImageUseTypeDTO getUseType() {
		return useType;
	}

	public void setUseType(ImageUseTypeDTO useType) {
		this.useType = useType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getWaypath() {
		return waypath;
	}

	public void setWaypath(String waypath) {
		this.waypath = waypath;
	}

	public int getIsRefuse() {
		return isRefuse;
	}

	public void setIsRefuse(int isRefuse) {
		if (isRefuse > 1)
			isRefuse = 1;
		this.isRefuse = isRefuse;
	}

	public AlbumDTO getAlbum() {
		return album;
	}

	public void setAlbum(AlbumDTO album) {
		this.album = album;
	}

}
package hg.service.image.domain.qo;

import java.util.Date;

import hg.service.image.domain.qo.base.ImageBaseLocalQo;

/**
 * @类功能说明：创建图片(同时创建默认 ImageSpec)，根据规格集合剪裁图片。
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenys
 * @创建时间：2014-10-30上午11:17:56
 */
public class ImageLocalQo extends ImageBaseLocalQo {

	private static final long serialVersionUID = 1L;

	/**
	 * 所属相册
	 */
	private AlbumLocalQo albumQo;

	/**
	 * 图片标题
	 */
	private String title;
	private boolean titleLike;

	/**
	 * 创建时间
	 */
	private Date createBeginDate;
	private Date createEndDate;

	/**
	 * 创建时间排序(>0 asc <0 desc)
	 */
	private int createDateSort;

	/**
	 * 图片规格Key
	 */
	private String imageSpecKey;

	/**
	 * 所有者id
	 */
	private String ownerId;

	/**
	 * 对应ID
	 */
	private String fromId;

	/**
	 * 相册用途
	 */
	private ImageUseTypeLocalQo useType;

	/**
	 * 是否回收(回收站)
	 */
	private Integer isRefuse = 0;

	/**
	 * 图片路径(路径结尾带斜杠)
	 */
	private String waypath;
	private boolean waypathLike;

	/**
	 * 图片全名(相册路径+图片标题)
	 */
	private String fullName;
	private boolean fullNameLike;

	/**
	 * 是否立即加载相册信息
	 */
	private boolean fetchAlbum = false;
	/**
	 * 是否立即加载图片用途
	 */
	private boolean fetchUseType = false;

	public ImageLocalQo() {
	}

	public ImageLocalQo(String projectId, String envName) {
		super(projectId, envName);
	}

	public ImageLocalQo(String id) {
		super(id);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public boolean isWaypathLike() {
		return waypathLike;
	}

	public void setWaypathLike(boolean waypathLike) {
		this.waypathLike = waypathLike;
	}

	public ImageUseTypeLocalQo getUseType() {
		return useType;
	}

	public void setUseType(ImageUseTypeLocalQo useType) {
		this.useType = useType;
	}

	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId == null ? null : fromId.trim();
	}

	public String getImageSpecKey() {
		return imageSpecKey;
	}

	public void setImageSpecKey(String imageSpecKey) {
		this.imageSpecKey = imageSpecKey == null ? null : imageSpecKey.trim();
	}

	public boolean isTitleLike() {
		return titleLike;
	}

	public void setTitleLike(boolean titleLike) {
		this.titleLike = titleLike;
	}

	public Date getCreateBeginDate() {
		return createBeginDate;
	}

	public void setCreateBeginDate(Date createBeginDate) {
		this.createBeginDate = createBeginDate;
	}

	public Date getCreateEndDate() {
		return createEndDate;
	}

	public void setCreateEndDate(Date createEndDate) {
		this.createEndDate = createEndDate;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId == null ? null : ownerId.trim();
	}

	public String getWaypath() {
		return waypath;
	}

	public void setWaypath(String waypath) {
		if (null != waypath && waypath.lastIndexOf("/") == waypath.length() - 1)
			this.waypath = waypath.trim();
	}

	public Integer getIsRefuse() {
		return isRefuse;
	}

	public void setIsRefuse(Integer isRefuse) {
		if (null != isRefuse && isRefuse > 1)
			isRefuse = 1;
		this.isRefuse = isRefuse;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName == null ? null : fullName.trim();
	}

	public boolean isFullNameLike() {
		return fullNameLike;
	}

	public void setFullNameLike(boolean fullNameLike) {
		this.fullNameLike = fullNameLike;
	}

	public int getCreateDateSort() {
		return createDateSort;
	}

	public void setCreateDateSort(int createDateSort) {
		this.createDateSort = createDateSort;
	}

	public AlbumLocalQo getAlbumQo() {
		return albumQo;
	}

	public void setAlbumQo(AlbumLocalQo albumQo) {
		this.albumQo = albumQo;
	}

	public boolean isFetchAlbum() {
		return fetchAlbum;
	}

	public void setFetchAlbum(boolean fetchAlbum) {
		this.fetchAlbum = fetchAlbum;
	}

	public boolean isFetchUseType() {
		return fetchUseType;
	}

	public void setFetchUseType(boolean fetchUseType) {
		this.fetchUseType = fetchUseType;
	}

}
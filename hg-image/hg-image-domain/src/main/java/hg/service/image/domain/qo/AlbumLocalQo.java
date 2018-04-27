package hg.service.image.domain.qo;

import java.util.Date;
import hg.service.image.domain.qo.base.ImageBaseLocalQo;

/**
 * @类功能说明：创建相册
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenys
 * @创建时间：2014-10-30下午3:40:45
 */
public class AlbumLocalQo extends ImageBaseLocalQo {
	private static final long serialVersionUID = 1L;

	/**
	 * 相册标题
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
	 * 所有者id
	 */
	private String ownerId;

	/**
	 * 对应ID
	 */
	private String fromId;

	/**
	 * 图片用途
	 */
	private ImageUseTypeLocalQo useType;

	/**
	 * 是否回收(回收站)
	 */
	private Integer isRefuse = 0;

	/**
	 * 相册路径(路径结尾带斜杠)
	 */
	private String path;
	private boolean pathLike;

	/**
	 * 相册全名(相册路径+相册名)
	 */
	private String name;
	private boolean nameLike;

	public AlbumLocalQo() {
	}

	public AlbumLocalQo(String projectId, String envName) {
		super(projectId, envName);
	}

	public AlbumLocalQo(String id) {
		super(id);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId == null ? null : ownerId.trim();
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

	public boolean isTitleLike() {
		return titleLike;
	}

	public void setTitleLike(boolean titleLike) {
		this.titleLike = titleLike;
	}

	public Integer getIsRefuse() {
		return isRefuse;
	}

	public void setIsRefuse(Integer isRefuse) {
		if (null != isRefuse && isRefuse > 1)
			isRefuse = 1;
		this.isRefuse = isRefuse;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isPathLike() {
		return pathLike;
	}

	public void setPathLike(boolean pathLike) {
		this.pathLike = pathLike;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isNameLike() {
		return nameLike;
	}

	public void setNameLike(boolean nameLike) {
		this.nameLike = nameLike;
	}

	public int getCreateDateSort() {
		return createDateSort;
	}

	public void setCreateDateSort(int createDateSort) {
		this.createDateSort = createDateSort;
	}
}
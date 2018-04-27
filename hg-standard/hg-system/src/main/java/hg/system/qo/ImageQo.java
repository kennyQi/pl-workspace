package hg.system.qo;

import hg.common.model.qo.DwzBasePaginQo;

/**
 * 
 * @类功能说明：图片_查询类
 * @类修改者：zzb
 * @修改日期：2014年9月3日上午8:59:06
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年9月3日上午8:59:06
 * 
 */
@SuppressWarnings("serial")
public class ImageQo extends DwzBasePaginQo {

	/**
	 * 图片标题
	 */
	private String title;
	private boolean titleLike;

	/**
	 * 创建时间
	 */
	private String createDateBegin;
	private String createDateEnd;

	/**
	 * 归属者
	 */
	private String ownerId;

	/**
	 * 相册qo
	 */
	private AlbumQo albumQo;

	/**
	 * 左侧相册id
	 */
	private String leftId;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public AlbumQo getAlbumQo() {
		return albumQo;
	}

	public void setAlbumQo(AlbumQo albumQo) {
		this.albumQo = albumQo;
	}

	public String getLeftId() {
		return leftId;
	}

	public void setLeftId(String leftId) {
		this.leftId = leftId;
	}

	public boolean isTitleLike() {
		return titleLike;
	}

	public void setTitleLike(boolean titleLike) {
		this.titleLike = titleLike;
	}

	public String getCreateDateBegin() {
		return createDateBegin;
	}

	public void setCreateDateBegin(String createDateBegin) {
		this.createDateBegin = createDateBegin;
	}

	public String getCreateDateEnd() {
		return createDateEnd;
	}

	public void setCreateDateEnd(String createDateEnd) {
		this.createDateEnd = createDateEnd;
	}


}
package lxs.pojo.dto.app;

import java.util.Date;

import javax.persistence.Column;

import lxs.pojo.BaseDTO;


@SuppressWarnings("serial")
public class CarouselDTO extends BaseDTO {

	private String lineid;

	private String linename;

	/**s
	 * 首页
	 */
	public static final Integer HOMGPAGE = 1;
	/**
	 * 线路
	 */
	public static final Integer LINE = 2;
	/**
	 * 门票
	 */
	public static final Integer MENPIAO = 3;
	/**
	 * 活动
	 */
	public static final Integer PROMOTION = 4;
	/**
	 * 约伴
	 */
	public static final Integer FRIENDS = 5;
	/**
	 * 链接
	 */
	public static final Integer LINK = 6;

	/**
	 * 轮播启用
	 */
	public static final Integer ON = 1;

	/**
	 * 轮播禁用
	 */
	public static final Integer OFF = 2;

	/**
	 * 轮播图级别（用于菜单展示） 1：首页 2：线路 3：门票
	 */
	private Integer carouselLevel;

	/**
	 * 图片url（不包含域名,域名从CC里拉取）
	 */
	private String imageURL;

	/**
	 * 轮播图类别（用于新增时类别选择） 2：线路 3：门票 4：活动 5：约伴 6：链接
	 */
	private Integer carouselType;

	/**
	 * 轮播图指向地址
	 */
	@Column(name = "CAROUSEL_ACTION", length = 512)
	private String carouselAction;

	/**
	 * 备注
	 */
	private String note;

	/**
	 * 状态 1：启用 2：禁用
	 */
	private Integer status;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 添加时间
	 */
	private Date createDate;

	public void carouselON() {
		status = ON;
	}

	public void carouselOFF() {
		status = OFF;
	}

	public Integer getCarouselLevel() {
		return carouselLevel;
	}

	public void setCarouselLevel(Integer carouselLevel) {
		this.carouselLevel = carouselLevel;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public Integer getCarouselType() {
		return carouselType;
	}

	public void setCarouselType(Integer carouselType) {
		this.carouselType = carouselType;
	}

	public String getCarouselAction() {
		return carouselAction;
	}

	public void setCarouselAction(String carouselAction) {
		this.carouselAction = carouselAction;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getLineid() {
		return lineid;
	}

	public void setLineid(String lineid) {
		this.lineid = lineid;
	}

	public String getLinename() {
		return linename;
	}

	public void setLinename(String linename) {
		this.linename = linename;
	}

}

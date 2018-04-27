package lxs.domain.model.app;

import hg.common.component.BaseModel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @类功能说明：启动页与引导页
 * @类修改者：
 * @修改日期：2015年9月17日上午11:17:48
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年9月17日上午11:17:48
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_APP + "LINK_PAGE")
public class LinkPage extends BaseModel {

	/**
	 * 启动图
	 */
	public static final Integer START_PAGE = 1;
	/**
	 * 引导图
	 */
	public static final Integer LINK_PAGE = 2;
	
//	/**
//	 * 提示更新
//	 */
//	public static final Integer NECESSARY_UPDATE = 1;
//	/**
//	 * 提示无需更新
//	 */
//	public static final Integer UNNECESSARY_UPDATE = 0;
	
	/**
	 * 图片地址
	 */
	@Column(name = "IMAGE_URL", length = 512)
	private String imageURL;
	
	@Column(name = "sort")
	private int sort;

	/**
	 * 图片类型 1：启动图 2：引导图
	 */
	@Column(name = "IMAGE_TYPE", columnDefinition = M.NUM_COLUM)
	private Integer imageType;

//	/**
//	 * 是否更新标志位 0：提示无需更新 1：提示更新
//	 */
//	@Column(name = "IMAGE_UPDATE_STATUS", columnDefinition = M.NUM_COLUM)
//	private Integer imageUpdateStatus;

	/**
	 * 添加时间
	 */
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public Integer getImageType() {
		return imageType;
	}

	public void setImageType(Integer imageType) {
		this.imageType = imageType;
	}

//	public Integer getImageUpdateStatus() {
//		return imageUpdateStatus;
//	}
//
//	public void setImageUpdateStatus(Integer imageUpdateStatus) {
//		this.imageUpdateStatus = imageUpdateStatus;
//	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

}

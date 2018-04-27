package lxs.domain.model.app;

import hg.common.component.BaseModel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @类功能说明：首页推荐
 * @类修改者：
 * @修改日期：2015年9月14日上午10:08:42
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年9月14日上午10:08:42
 */
@SuppressWarnings("serial")
@Entity
@Table(name=M.TABLE_PREFIX_APP+"RECOMMEND")
public class Recommend extends BaseModel{

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
	 * 启用活动
	 */
	public static final Integer ON = 1;
	
	/**
	 * 禁用活动
	 */
	public static final Integer OFF = 2;
	
	/**
	 * 推荐标题
	 */
	@Column(name = "RECOMMEND_NAME", length=512)
	private String recommendName;
	
	/**
	 * 图片url（不包含域名,域名从CC里拉取）
	 */
	@Column(name = "IMAGE_URL", length = 512)
	private String imageURL;
	
	/**
	 * 轮播图类别（用于新增时类别选择）
	 * 2：线路
	 * 3：门票
	 * 4：活动
	 * 5：约伴
	 * 6：链接
	 */
	@Column(name = "RECOMMEND_TYPE", columnDefinition = M.NUM_COLUM)
	private Integer recommendType;
	
	/**
	 * 推荐指向地址
	 */
	@Column(name = "RECOMMEND_ACTION", length = 512)
	private String recommendAction;
	
	/**
	 * 简介说明
	 */
	@Column(name = "NOTE", columnDefinition = M.TEXT_COLUM)
	private String note;
	
	/**
	 * 状态
	 * 1：启用
	 * 2：禁用
	 */
	@Column(name = "STATUS", columnDefinition = M.NUM_COLUM)
	private Integer status;
	
	/**
	 * 排序
	 */
	@Column(name = "SORT", columnDefinition = M.NUM_COLUM)
	private Integer sort;
	
	/**
	 * 添加时间
	 */
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;

	public void carouselON(){
		status=ON;
	}

	public void carouselOFF(){
		status=OFF;
	}

	public String getRecommendName() {
		return recommendName;
	}

	public void setRecommendName(String recommendName) {
		this.recommendName = recommendName;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public Integer getRecommendType() {
		return recommendType;
	}

	public void setRecommendType(Integer recommendType) {
		this.recommendType = recommendType;
	}

	public String getRecommendAction() {
		return recommendAction;
	}

	public void setRecommendAction(String recommendAction) {
		this.recommendAction = recommendAction;
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
	
	
}

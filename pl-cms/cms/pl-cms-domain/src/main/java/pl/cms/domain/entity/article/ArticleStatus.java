package pl.cms.domain.entity.article;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import pl.cms.domain.entity.M;

/**
 * 文章状态
 * @author yuxx
 *
 */
@Embeddable
public class ArticleStatus {

	/**
	 * 当前状态值
	 */
	@Column(name="STATUS", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer currentValue;
	
	public final static Integer VALUE_HIDE = 1;	//	隐藏
	public final static Integer VALUE_SHOW = 2;	//	显示
	
	/**
	 * 浏览数
	 */
	@Column(name="VIEW_NO", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer viewNo;

	/**
	 * 评论数
	 */
	@Column(name="COMMENT_NO", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer commentNo;
	
	/**
	 * 支持次数
	 */
	@Column(name = "STAT_SUPPORT_COUNT", columnDefinition = M.NUM_COLUM)
	private Integer supportCount;

	/**
	 * 反对次数
	 */
	@Column(name = "STAT_OPPOSE_COUNT", columnDefinition = M.NUM_COLUM)
	private Integer opposeCount;
	
	public Integer getSupportCount() {
		return supportCount;
	}

	public void setSupportCount(Integer supportCount) {
		this.supportCount = supportCount;
	}

	public Integer getOpposeCount() {
		return opposeCount;
	}

	public void setOpposeCount(Integer opposeCount) {
		this.opposeCount = opposeCount;
	}
	
	public Integer getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(Integer currentValue) {
		this.currentValue = currentValue;
	}

	public Integer getViewNo() {
		return viewNo;
	}

	public void setViewNo(Integer viewNo) {
		this.viewNo = viewNo;
	}

	public Integer getCommentNo() {
		return commentNo;
	}

	public void setCommentNo(Integer commentNo) {
		this.commentNo = commentNo;
	}

}

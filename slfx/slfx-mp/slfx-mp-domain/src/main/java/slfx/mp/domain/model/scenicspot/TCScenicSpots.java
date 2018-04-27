package slfx.mp.domain.model.scenicspot;

import hg.common.component.BaseModel;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import slfx.mp.domain.model.M;
import slfx.mp.domain.model.supplierpolicy.TCPolicyNotice;

import com.alibaba.fastjson.JSON;

/**
 * 同程景区信息
 * 
 * @author Administrator
 */
@Entity
@Table(name = M.TABLE_PREFIX + "TC_SCENIC_SPOTS")
public class TCScenicSpots extends BaseModel {
	private static final long serialVersionUID = 1L;

	/**
	 * 政策须知
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private TCPolicyNotice tcPolicyNotice;
	
	/**
	 * 主题列表
	 */
	@Transient
	private List<TCScenicSpotsTheme> themes;

	/**
	 * 景区主题ID集合，逗号分隔
	 */
	@Column(name = "THEME_IDS", length = 256)
	private String themesIds;

	/**
	 * 景区主题列表JSON
	 */
	@Column(name = "THEMES_JSON", length = 4000)
	private String themesJson;

	/**
	 * 对应平台景区
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCENIC_SPOT_ID")
	private ScenicSpot scenicSpot;
	/**
	 * 点评数
	 */
	@Column(name = "COMMENT_COUNT", columnDefinition = M.NUM_COLUM)
	private Integer commentCount;
	/**
	 * 问答数
	 */
	@Column(name = "QUESTION_COUNT", columnDefinition = M.NUM_COLUM)
	private Integer questionCount;
	/**
	 * 博客数量
	 */
	@Column(name = "BLOG_COUNT", columnDefinition = M.NUM_COLUM)
	private Integer blogCount;
	/**
	 * 浏览次数
	 */
	@Column(name = "VIEW_COUNT", columnDefinition = M.NUM_COLUM)
	private Integer viewCount;
	/**
	 * 是否可预订
	 */
	@Type(type = "yes_no")
	@Column(name = "BOOK_FLAG")
	private Boolean bookFlag;
	/**
	 * 最低价
	 */
	@Column(name = "AMOUNT_ADVICE", columnDefinition = M.MONEY_COLUM)
	private Double amountAdvice;
	/**
	 * 是否需要证件号
	 */
	@Type(type = "yes_no")
	@Column(name = "IF_USE_CARD")
	private Boolean ifUseCard;
	/**
	 * 支付类型
	 */
	@Column(name = "PAY_MODE", length = 64)
	private String payMode;

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getQuestionCount() {
		return questionCount;
	}

	public void setQuestionCount(Integer questionCount) {
		this.questionCount = questionCount;
	}

	public Integer getBlogCount() {
		return blogCount;
	}

	public void setBlogCount(Integer blogCount) {
		this.blogCount = blogCount;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public Boolean getBookFlag() {
		return bookFlag;
	}

	public void setBookFlag(Boolean bookFlag) {
		this.bookFlag = bookFlag;
	}

	public Double getAmountAdvice() {
		return amountAdvice;
	}

	public void setAmountAdvice(Double amountAdvice) {
		this.amountAdvice = amountAdvice;
	}

	public Boolean getIfUseCard() {
		return ifUseCard;
	}

	public void setIfUseCard(Boolean ifUseCard) {
		this.ifUseCard = ifUseCard;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public ScenicSpot getScenicSpot() {
		return super.getProperty(scenicSpot, ScenicSpot.class);
	}

	public void setScenicSpot(ScenicSpot scenicSpot) {
		this.scenicSpot = scenicSpot;
	}

	public TCPolicyNotice getTcPolicyNotice() {
		return super.getProperty(tcPolicyNotice, TCPolicyNotice.class);
	}

	public void setTcPolicyNotice(TCPolicyNotice tcPolicyNotice) {
		this.tcPolicyNotice = tcPolicyNotice;
	}

	public List<TCScenicSpotsTheme> getThemes() {
		if (themes == null && themesJson != null)
			try {
				themes = JSON.parseArray(themesJson, TCScenicSpotsTheme.class);
			} catch (Exception e) { }
		if (themes == null)
			return new ArrayList<TCScenicSpotsTheme>();
		return themes;
	}

	public void setThemes(List<TCScenicSpotsTheme> themes) {
		this.themes = themes;
	}

	public String getThemesIds() {
		if (themesIds == null) {
			StringBuilder sb = new StringBuilder();
			for (TCScenicSpotsTheme theme : getThemes())
				sb.append(",").append(theme.getThemeId());
			if (sb.length() > 0)
				sb.append(",");
			themesIds = sb.toString();
		}
		return themesIds;
	}
	
	public void setThemesIds(String themesIds) {
		this.themesIds = themesIds;
	}

	public String getThemesJson() {
		if (themesJson == null && themes != null)
			themesJson = JSON.toJSONString(themes);
		return themesJson;
	}

	public void setThemesJson(String themesJson) {
		this.themesJson = themesJson;
	}

}
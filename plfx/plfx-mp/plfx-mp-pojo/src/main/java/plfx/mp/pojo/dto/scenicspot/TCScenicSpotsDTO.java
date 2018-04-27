package plfx.mp.pojo.dto.scenicspot;

import java.util.List;

import plfx.mp.pojo.dto.BaseMpDTO;
import plfx.mp.pojo.dto.supplierpolicy.TCPolicyNoticeDTO;

/**
 * 同程景区信息
 * 
 * @author Administrator
 */
public class TCScenicSpotsDTO extends BaseMpDTO {
	private static final long serialVersionUID = 1L;

	/**
	 * 点评数
	 */
	private Integer commentCount;
	/**
	 * 问答数
	 */
	private Integer questionCount;
	/**
	 * 博客数量
	 */
	private Integer blogCount;
	/**
	 * 浏览次数
	 */
	private Integer viewCount;
	/**
	 * 是否可预订
	 */
	private Boolean bookFlag;
	/**
	 * 最低价
	 */
	private Double amountAdvice;
	/**
	 * 是否需要证件号
	 */
	private Boolean ifUseCard;

	/**
	 * 政策须知
	 */
	private TCPolicyNoticeDTO tcPolicyNotice;

	/**
	 * 支付类型
	 */
	private String payMode;
	
	/**
	 * 主题列表
	 */
	private List<TCScenicSpotsThemeDTO> themes;

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

	public TCPolicyNoticeDTO getTcPolicyNotice() {
		return tcPolicyNotice;
	}

	public void setTcPolicyNotice(TCPolicyNoticeDTO tcPolicyNotice) {
		this.tcPolicyNotice = tcPolicyNotice;
	}

	public List<TCScenicSpotsThemeDTO> getThemes() {
		return themes;
	}

	public void setThemes(List<TCScenicSpotsThemeDTO> themes) {
		this.themes = themes;
	}

}
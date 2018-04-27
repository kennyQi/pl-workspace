package pl.cms.pojo.qo;
import hg.common.component.BaseQo;
@SuppressWarnings("serial")
public class ContributionQO extends BaseQo {
	/**
	 * 稿件ID
	 */
	private String contributionId;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 作者
	 */
	private String author;
	/**
	 * 审核状态
	 */
	private Integer checkStatus;
	/**
	 * 标题 是否模糊查询
	 */
	private Boolean titleLike;
	/**
	 * 创建开始时间
	 */
	private String beginTime;
	/**
	 * 创建结束时间
	 */
	private String endTime;
		
	private Integer topSize;
	//上一篇或下一篇
	private String pertOrNext;
	//排序字段
	private String orderField;
	//排序方式
	private String orderMode;
//	private Boolean fetchChannel;
	public String getContributionId() {
		return contributionId;
	}
	public void setContributionId(String contributionId) {
		this.contributionId = contributionId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Integer getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}
	public Boolean getTitleLike() {
		return titleLike;
	}
	public void setTitleLike(Boolean titleLike) {
		this.titleLike = titleLike;
	}
	public Integer getTopSize() {
		return topSize;
	}
	public void setTopSize(Integer topSize) {
		this.topSize = topSize;
	}
	public String getPertOrNext() {
		return pertOrNext;
	}
	public void setPertOrNext(String pertOrNext) {
		this.pertOrNext = pertOrNext;
	}
	public String getOrderField() {
		return orderField;
	}
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}
	public String getOrderMode() {
		return orderMode;
	}
	public void setOrderMode(String orderMode) {
		this.orderMode = orderMode;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
//	public Boolean getFetchChannel() {
//		return fetchChannel;
//	}
//	public void setFetchChannel(Boolean fetchChannel) {
//		this.fetchChannel = fetchChannel;
//	}
}

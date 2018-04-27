package lxs.pojo.qo.app;

import java.util.Date;

import hg.common.component.BaseQo;

@SuppressWarnings("serial")
public class RecommendQO extends BaseQo {

	/**
	 * 推荐ID
	 */
	private String recommendID;
	/**
	 * 推荐标题
	 */
	private String recommendName;

	private String recommendAction;

	/**
	 * 状态 1：启用 2：禁用
	 */
	private Integer status;

	/**
	 * 添加时间
	 */
	private Date inCreateDate;
	private String start;

	/**
	 * 添加时间
	 */
	private Date toCreateDate;
	private String end;

	public RecommendQO() {
	}

	public RecommendQO(Date inCreateDate, Date toCreateDate) {
		this.inCreateDate = inCreateDate;
		this.toCreateDate = toCreateDate;
	}

	public String getRecommendID() {
		return recommendID;
	}

	public void setRecommendID(String recommendID) {
		this.recommendID = recommendID;
	}

	public String getRecommendName() {
		return recommendName;
	}

	public void setRecommendName(String recommendName) {
		this.recommendName = recommendName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getToCreateDate() {
		return toCreateDate;
	}

	public void setToCreateDate(Date toCreateDate) {
		this.toCreateDate = toCreateDate;
	}

	public Date getInCreateDate() {
		return inCreateDate;
	}

	public void setInCreateDate(Date inCreateDate) {
		this.inCreateDate = inCreateDate;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getRecommendAction() {
		return recommendAction;
	}

	public void setRecommendAction(String recommendAction) {
		this.recommendAction = recommendAction;
	}

}

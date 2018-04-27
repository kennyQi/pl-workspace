package plfx.mp.tcclient.tc.domain;

import java.util.List;

public class PriceQueryScenery {
	/**
	 * 景区id
	 */
	private Integer sceneryId;
	
	/**
	 * 价格列表
	 */
	private List<Policy> policy;
	/**
	 * 购票须知列表
	 */
	private List<Notice> notice;
	/**
	 * 提前预订列表
	 */
	private Ahead ahead;
	public Integer getSceneryId() {
		return sceneryId;
	}
	public void setSceneryId(Integer sceneryId) {
		this.sceneryId = sceneryId;
	}
	public List<Policy> getPolicy() {
		return policy;
	}
	public void setPolicy(List<Policy> policy) {
		this.policy = policy;
	}
	public List<Notice> getNotice() {
		return notice;
	}
	public void setNotice(List<Notice> notice) {
		this.notice = notice;
	}
	public Ahead getAhead() {
		return ahead;
	}
	public void setAhead(Ahead ahead) {
		this.ahead = ahead;
	}
}

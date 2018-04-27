package plfx.jd.domain.model.hotel;

import javax.persistence.Column;

@SuppressWarnings("serial")
public class YLReview{
	/***
	 * 点评总数
	 */
	@Column(name = "COUNT")
	private String count;
	/**
	 * 好评数
	 */
	@Column(name = "GOOD")
	private String good;
	/**
	 * 差评数
	 */
	@Column(name = "POOR")
	private String poor;
	/**
	 * 评分
	 */
	@Column(name = "SCORE")
	private String score;

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getGood() {
		return good;
	}

	public void setGood(String good) {
		this.good = good;
	}

	public String getPoor() {
		return poor;
	}

	public void setPoor(String poor) {
		this.poor = poor;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
	
	
}

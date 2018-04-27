package plfx.jd.pojo.dto.plfx.hotel;

import hg.common.component.BaseModel;





@SuppressWarnings("serial")
public class YLReviewDTO extends BaseModel{
	/***
	 * 点评总数
	 */

	private String count;
	/**
	 * 好评数
	 */

	private String good;
	/**
	 * 差评数
	 */

	private String poor;
	/**
	 * 评分
	 */

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

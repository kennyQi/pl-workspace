package plfx.jd.domain.model.hotel;

import java.util.Date;

import javax.persistence.Column;
@SuppressWarnings("serial")
public class YLHelpfulTips{
	
	/***
	 * 开始日期
	 */
	@Column(name = "STARTDATE")
	public Date startDate;
	/***
	 * 结束日期
	 */
	@Column(name = "ENDDATE")
	public Date endDate;
	/***
	 * 描述
	 */
	@Column(name = "HELPFULTIPSDESCRPTION",columnDefinition ="TEXT")
	public String  helpfulTipsDescrption;
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getHelpfulTipsDescrption() {
		return helpfulTipsDescrption;
	}
	public void setHelpfulTipsDescrption(String helpfulTipsDescrption) {
		this.helpfulTipsDescrption = helpfulTipsDescrption;
	}
}

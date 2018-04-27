package plfx.jd.pojo.dto.plfx.hotel;

import java.util.Date;

import plfx.jd.pojo.dto.ylclient.order.BaseJDDTO;
@SuppressWarnings("serial")
public class YLHelpfulTipsDTO extends BaseJDDTO{
	
	/***
	 * 开始日期
	 */

	public Date startDate;
	/***
	 * 结束日期
	 */

	public Date endDate;
	/***
	 * 描述
	 */

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

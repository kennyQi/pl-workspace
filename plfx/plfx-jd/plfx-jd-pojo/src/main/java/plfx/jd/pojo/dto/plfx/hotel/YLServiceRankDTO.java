package plfx.jd.pojo.dto.plfx.hotel;

public class YLServiceRankDTO{
	
	/***
	 * 酒店服务总评分
	 */

	public String summaryScore;
	/**
	 * 及时确认率
	 */

	public String instantConfirmScore;
	/***
	 * 预订成功率
	 */
	
	public String bookingSuccessScore;
	/***
	 * 用户投诉率
	 */

	public String complaintScore;
	/***
	 * 服务总评分与同城的水平的对比
	 */

	public String summaryRate;
	/***
	 * 及时确认率与同城的水平的对比
	 */

	public String instantConfirmRate;
	/***
	 * 预订成功率
	 */

	public String bookingSuccessRate;
	/***
	 * 预订成功率与同城的水平的对比
	 */

	public String complaintRate;
	
	public String getSummaryScore() {
		return summaryScore;
	}
	public void setSummaryScore(String summaryScore) {
		this.summaryScore = summaryScore;
	}
	public String getInstantConfirmScore() {
		return instantConfirmScore;
	}
	public void setInstantConfirmScore(String instantConfirmScore) {
		this.instantConfirmScore = instantConfirmScore;
	}
	public String getBookingSuccessScore() {
		return bookingSuccessScore;
	}
	public void setBookingSuccessScore(String bookingSuccessScore) {
		this.bookingSuccessScore = bookingSuccessScore;
	}
	public String getComplaintScore() {
		return complaintScore;
	}
	public void setComplaintScore(String complaintScore) {
		this.complaintScore = complaintScore;
	}
	public String getSummaryRate() {
		return summaryRate;
	}
	public void setSummaryRate(String summaryRate) {
		this.summaryRate = summaryRate;
	}
	public String getInstantConfirmRate() {
		return instantConfirmRate;
	}
	public void setInstantConfirmRate(String instantConfirmRate) {
		this.instantConfirmRate = instantConfirmRate;
	}
	public String getBookingSuccessRate() {
		return bookingSuccessRate;
	}
	public void setBookingSuccessRate(String bookingSuccessRate) {
		this.bookingSuccessRate = bookingSuccessRate;
	}
	public String getComplaintRate() {
		return complaintRate;
	}
	public void setComplaintRate(String complaintRate) {
		this.complaintRate = complaintRate;
	}
	
	
}

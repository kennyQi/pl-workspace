package plfx.jd.domain.model.hotel;

import javax.persistence.Column;
@SuppressWarnings("serial")
public class YLServiceRank{
	
	/***
	 * 酒店服务总评分
	 */
	@Column(name = "SUMMARY_SCORE")
	public String summaryScore;
	/**
	 * 及时确认率
	 */
	@Column(name = "HINSTANT_CONFIRMSCORE")
	public String instantConfirmScore;
	/***
	 * 预订成功率
	 */
	@Column(name = "BOOKING_SUCCESS_SCORE")
	public String bookingSuccessScore;
	/***
	 * 用户投诉率
	 */
	@Column(name = "COMPLAIN_SCORE")
	public String complaintScore;
	/***
	 * 服务总评分与同城的水平的对比
	 */
	@Column(name = "SUMMARY_RATE")
	public String summaryRate;
	/***
	 * 及时确认率与同城的水平的对比
	 */
	@Column(name = "INSTANT_CONFIRMRATE")
	public String instantConfirmRate;
	/***
	 * 预订成功率
	 */
	@Column(name = "BOOKING_SUCCES_SRATE")
	public String bookingSuccessRate;
	/***
	 * 预订成功率与同城的水平的对比
	 */
	@Column(name = "COMPLAINT_RATE")
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

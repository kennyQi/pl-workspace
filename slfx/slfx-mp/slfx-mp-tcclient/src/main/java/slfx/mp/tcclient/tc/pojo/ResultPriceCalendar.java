package slfx.mp.tcclient.tc.pojo;

import java.util.List;

import slfx.mp.tcclient.tc.domain.Ahead;
import slfx.mp.tcclient.tc.domain.Calendar;
import slfx.mp.tcclient.tc.domain.Notice;

/**
 * 用于调用同城获取价格日历接口返回结果
 * @author zhangqy
 *
 */
public class ResultPriceCalendar extends Result {
	/**
	 * 景区Id
	 */
	private Integer sceneryId;
	/**
	 * 景区名称
	 */
	private String sceneryName;
	/**
	 * 支付方式
	 */
	private Integer pMode;
	/**
	 * 	是否使用二代身份证
	 */
	private String useCard;
	/**
	 * 是否使用二代身份证
	 */
	private String realName;
	/**
	 * 门票说明
	 */
	private String remark;
	/**
	 * 预订须知列表
	 */
	private List<Notice> notice;
	/**
	 * 每日价格列表
	 */
	private List<Calendar> calendar;
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
	public String getSceneryName() {
		return sceneryName;
	}
	public void setSceneryName(String sceneryName) {
		this.sceneryName = sceneryName;
	}
	public Integer getPMode() {
		return pMode;
	}
	public void setPMode(Integer pMode) {
		this.pMode = pMode;
	}
	public String getUseCard() {
		return useCard;
	}
	public void setUseCard(String useCard) {
		this.useCard = useCard;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<Notice> getNotice() {
		return notice;
	}
	public void setNotice(List<Notice> notice) {
		this.notice = notice;
	}
	
	public List<Calendar> getCalendar() {
		return calendar;
	}
	
	public void setCalendar(List<Calendar> calendar) {
		this.calendar = calendar;
	}
	public Ahead getAhead() {
		return ahead;
	}
	public void setAhead(Ahead ahead) {
		this.ahead = ahead;
	}
	
	
}	

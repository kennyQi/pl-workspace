package hsl.pojo.command.line;

import hg.common.component.BaseCommand;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;

/**
 * @类功能说明：创建线路订单（H5）
 * @类修改者：
 * @修改日期：2015-10-8下午4:35:02
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-10-8下午4:35:02
 */
@SuppressWarnings("serial")
public class HslH5CreateLineOrderCommand extends BaseCommand {

	// -------------------------- 基本信息 --------------------------

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 线路ID
	 */
	private String lineId;

	// -------------------------- 价格日历选择 --------------------------

	/**
	 * 发团日期
	 */
	private Date travelDate;

	/**
	 * 成人人数
	 */
	private Integer adultNo = 1;

	/**
	 * 儿童人数
	 */
	private Integer childNo = 0;

	// -------------------------- 游玩人 --------------------------

	/**
	 * 游客ID
	 */
	private List<String> travelerIds;

	// -------------------------- 联系人 --------------------------

	/**
	 * 联系人姓名
	 */
	private String linkName;

	/**
	 * 联系人手机号
	 */
	private String linkMobile;

	/**
	 * 联系人邮箱
	 */
	private String linkEmail;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public Date getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(Date travelDate) {
		if (travelDate != null)
			this.travelDate = DateUtils.truncate(travelDate, Calendar.DATE);
		this.travelDate = travelDate;
	}

	public Integer getAdultNo() {
		return adultNo;
	}

	public void setAdultNo(Integer adultNo) {
		this.adultNo = adultNo;
	}

	public Integer getChildNo() {
		return childNo;
	}

	public void setChildNo(Integer childNo) {
		this.childNo = childNo;
	}

	public List<String> getTravelerIds() {
		if (travelerIds == null)
			travelerIds = new ArrayList<String>();
		return travelerIds;
	}

	public void setTravelerIds(List<String> travelerIds) {
		this.travelerIds = travelerIds;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getLinkMobile() {
		return linkMobile;
	}

	public void setLinkMobile(String linkMobile) {
		this.linkMobile = linkMobile;
	}

	public String getLinkEmail() {
		return linkEmail;
	}

	public void setLinkEmail(String linkEmail) {
		this.linkEmail = linkEmail;
	}

}

package hg.dzpw.dealer.client.dto.policy;

import hg.dzpw.dealer.client.common.EmbeddDTO;

import java.util.Date;

/**
 * @类功能说明:
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014-11-10 下午4:16:54
 * @版本：V1.0
 */
@SuppressWarnings("serial")
public class TicketPolicyUseConditionDTO extends EmbeddDTO {

	/** 门票使用条件：固定有效期 */
	public final static Integer VALID_USE_DATE_TYPE_FIXED = 1;
	/** 门票使用条件：有效天数 */
	public final static Integer VALID_USE_DATE_TYPE_DAYS = 2;

	/**
	 * 有效天数(独立单票可入园天数 or 联票自出票后的有效天数)
	 */
	private Integer validDays;
	
	/**
	 * 单票单天可入园次数
	 */
	private Integer validTimesPerDay;
	
	/**
	 * 单票可入园总次数
	 */
	private Integer validTimesTotal;
	
	/**
	 * 是否套票使用条件优先
	 */
	private Boolean groupPolicyFirst = true;

	/**
	 * 使用的验证类型
	 */
	private Integer validUseDateType;

	
	public Integer getValidDays() {
		return validDays;
	}

	public void setValidDays(Integer validDays) {
		this.validDays = validDays;
	}

	public Integer getValidUseDateType() {
		return validUseDateType;
	}

	public void setValidUseDateType(Integer validUseDateType) {
		this.validUseDateType = validUseDateType;
	}

	public Integer getValidTimesPerDay() {
		return validTimesPerDay;
	}

	public void setValidTimesPerDay(Integer validTimesPerDay) {
		this.validTimesPerDay = validTimesPerDay;
	}

	public Integer getValidTimesTotal() {
		return validTimesTotal;
	}

	public void setValidTimesTotal(Integer validTimesTotal) {
		this.validTimesTotal = validTimesTotal;
	}

	public Boolean getGroupPolicyFirst() {
		return groupPolicyFirst;
	}

	public void setGroupPolicyFirst(Boolean groupPolicyFirst) {
		this.groupPolicyFirst = groupPolicyFirst;
	}

}
package hsl.domain.model.dzp.policy;

import hsl.domain.model.M;
import hsl.pojo.util.HSLConstants;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 使用条件
 *
 * @author zhurz
 * @since 1.8
 */
@Embeddable
@SuppressWarnings("serial")
public class DZPTicketPolicyUseCondition implements HSLConstants.DZPTicketPolicyValidUseDateType, Serializable {

	/**
	 * 有效天数(独立单票可入园天数 or 联票自出票后的有效天数)
	 */
	@Column(name = "VALID_DAYS", columnDefinition = M.NUM_COLUM)
	private Integer validDays;
	
	/**
	 * 单票单天可入园次数
	 */
	@Column(name = "VALID_TIMES_PER_DAY", columnDefinition = M.NUM_COLUM)
	private Integer validTimesPerDay;
	
	/**
	 * 单票可入园总次数
	 */
	@Column(name = "VALID_TIMES_TOTAL", columnDefinition = M.NUM_COLUM)
	private Integer validTimesTotal;
	
	/**
	 * 是否套票使用条件优先
	 */
	@Type(type = "yes_no")
	@Column(name = "GROUP_POLICY_FIRST")
	private Boolean groupPolicyFirst = true;

	/**
	 * 使用的验证类型
	 *
	 * @see HSLConstants.DZPTicketPolicyValidUseDateType
	 */
	@Column(name = "VALID_USE_DATE_TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer validUseDateType;

	public Integer getValidDays() {
		return validDays;
	}

	public void setValidDays(Integer validDays) {
		this.validDays = validDays;
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

	public Integer getValidUseDateType() {
		return validUseDateType;
	}

	public void setValidUseDateType(Integer validUseDateType) {
		this.validUseDateType = validUseDateType;
	}
}
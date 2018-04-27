package hg.dzpw.domain.model.policy;

import hg.dzpw.domain.model.M;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.hibernate.annotations.Type;

/**
 * @类功能说明:
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014-11-10 下午4:16:54
 * @版本：V1.0
 */
@Embeddable
public class TicketPolicyUseCondition implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/** 门票使用条件：固定有效期 */
	public final static Integer VALID_USE_DATE_TYPE_FIXED = 1;
	/** 门票使用条件：有效天数 */
	public final static Integer VALID_USE_DATE_TYPE_DAYS = 2;

	/**
	 * 固定有效期开始时间(只有联票的validUseDateType=1时有效)
	 */
	@Column(name = "FIXED_USEDATE_START", columnDefinition = M.DATE_COLUM)
	private Date fixedUseDateStart;

	/**
	 * 固定有效期结束时间(只有联票的validUseDateType=1时有效)
	 */
	@Column(name = "FIXED_USEDATE_END", columnDefinition = M.DATE_COLUM)
	private Date fixedUseDateEnd;

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
	 */
	@Column(name = "VALID_USEDATE_TYPE", columnDefinition = M.NUM_COLUM)
	private Integer validUseDateType;

	public Date getFixedUseDateStart() {
		return fixedUseDateStart;
	}

	public void setFixedUseDateStart(Date fixedUseDateStart) {
		this.fixedUseDateStart = fixedUseDateStart;
	}

	public Date getFixedUseDateEnd() {
		return fixedUseDateEnd;
	}

	public void setFixedUseDateEnd(Date fixedUseDateEnd) {
		this.fixedUseDateEnd = fixedUseDateEnd;
	}

	public Integer getValidDays() {
		if (validDays == null)
			validDays = 1;
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
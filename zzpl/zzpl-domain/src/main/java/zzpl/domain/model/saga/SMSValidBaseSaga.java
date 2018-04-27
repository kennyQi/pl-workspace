package zzpl.domain.model.saga;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import zzpl.domain.model.M;
import hg.common.component.BaseEvent;
import hg.common.component.BaseSaga;

@MappedSuperclass
@SuppressWarnings("serial")
public abstract class SMSValidBaseSaga extends BaseSaga {

	/**
	 * 发送的验证码
	 */
	@Column(name = "VALID_CODE", length = 16)
	private String validCode;

	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;

	/**
	 * 短信验证码失效时间
	 */
	@Column(name = "SMS_INVALID_DATE", columnDefinition = M.DATE_COLUM)
	private Date smsInvalidDate;

	/**
	 * 最多接受几次短信验证
	 */
	@Column(name = "SMS_VALID_MAX_TIMES", length = 4)
	private Integer smsValidMaxTimes;

	/**
	 * 已经进行了几次短信验证
	 */
	@Column(name = "SMS_VALID_TIMES", length = 4)
	private Integer smsValidTimes;

	/**
	 * 流程结束时间
	 */
	@Column(name = "FINISH_DATE", columnDefinition = M.DATE_COLUM)
	private Date finishDate;

	/**
	 * 短信最后发送时间
	 */
	@Column(name = "LAST_SEND_SMS_DATE", columnDefinition = M.DATE_COLUM)
	private Date lastSendSMSDate;

	@Override
	public boolean checkFinish() {
		// 如果验证次数达到上限，或者短信已过有效期，视为流程结束
		if (getSmsValidTimes().intValue() == getSmsValidMaxTimes().intValue()
				|| getSmsInvalidDate().before(new Date())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public abstract void handle(BaseEvent event);

	public String generateRandemCode(Integer no) {
		String validCode = "";
		for (int i = 0; i < no; i++) {
			validCode += Math.round(Math.random() * 9);
		}
		return validCode;
	}

	public void resetInvalidDate(int second) {
		// 30分钟后过期
		Calendar c = Calendar.getInstance();
		c.add(Calendar.SECOND, second);

		setSmsInvalidDate(c.getTime());
		setLastSendSMSDate(new Date());
		
		System.out.println("已重发短信，短信验证码是:" + getValidCode());
	}

	public String getValidCode() {
		return validCode;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getSmsInvalidDate() {
		return smsInvalidDate;
	}

	public void setSmsInvalidDate(Date smsInvalidDate) {
		this.smsInvalidDate = smsInvalidDate;
	}

	public Integer getSmsValidMaxTimes() {
		return smsValidMaxTimes;
	}

	public void setSmsValidMaxTimes(Integer smsValidMaxTimes) {
		this.smsValidMaxTimes = smsValidMaxTimes;
	}

	public Integer getSmsValidTimes() {
		return smsValidTimes;
	}

	public void setSmsValidTimes(Integer smsValidTimes) {
		this.smsValidTimes = smsValidTimes;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public Date getLastSendSMSDate() {
		return lastSendSMSDate;
	}

	public void setLastSendSMSDate(Date lastSendSMSDate) {
		this.lastSendSMSDate = lastSendSMSDate;
	}

}

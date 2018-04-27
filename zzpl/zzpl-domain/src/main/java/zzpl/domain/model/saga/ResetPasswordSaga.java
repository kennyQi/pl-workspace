package zzpl.domain.model.saga;

import hg.common.component.BaseEvent;
import hg.common.util.UUIDGenerator;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import zzpl.domain.model.M;
import zzpl.domain.model.event.SMSValidCodeCorrectEvent;
import zzpl.domain.model.event.SMSValidCodePastDueEvent;
import zzpl.domain.model.event.SMSValidCodeWrongEvent;
import zzpl.pojo.command.saga.ResetPasswordCommand;

@Entity
@Table(name = M.TABLE_PREFIX_ZZPL_SAGA + "RESET_PASSWORD")
@SuppressWarnings("serial")
public class ResetPasswordSaga extends SMSValidBaseSaga {

	/**
	 * 登录用户ID
	 */
	@Column(name = "USER_ID", length = 512)
	private String userID;

	/**
	 * 注册的手机
	 */
	@Column(name = "MOBILE", length = 16)
	private String mobile;

	public final static Integer STATUS_RESET_PASSWORD = 1; // 手机号未验证

	public ResetPasswordSaga() {
		super();
	}

	/**
	 * 
	 * @类名：ResetPasswordSaga.java
	 * @描述：向某个手机发送了短信验证码，开启一个新注册流程
	 * @@param mobile
	 * @@param validCode
	 */
	public ResetPasswordSaga(ResetPasswordCommand command) {
		setId(UUIDGenerator.getUUID());
		setMobile(command.getMobile());

		// 生成验证码
		String validCode = generateRandemCode(6);
		System.out.println("===========短信验证码是:" + validCode + "==========");
		setValidCode(validCode);

		// 最多验三次
		setSmsValidMaxTimes(3);
		setSmsValidTimes(0);
		setCreateDate(new Date());

		// 30分钟后过期
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, 30);

		setSmsInvalidDate(c.getTime());

		setCurrentStatus(ResetPasswordSaga.STATUS_RESET_PASSWORD);
	}

	private void success() {
		setFinish(true);
		setFinishDate(new Date());
		setSuccess(true);
	}

	private void ValidCodePastDue() {
		// 如果注册流程结束时间为空，当前时间为结束时间
		setFinish(true);
		if (getFinishDate() == null) {
			setFinishDate(new Date());
		}
		setSuccess(false);
	}

	private void ValidCodeWrong() {
		// 如果短信最大验证次数还没达到，加1次
		if (getSmsValidMaxTimes() > getSmsValidTimes()) {
			setSmsValidTimes(getSmsValidTimes() + 1);
			// 如果达到最大验证次数，注册流程结束
			if (getSmsValidMaxTimes() == getSmsValidTimes()) {
				setFinish(true);
				if (getFinishDate() == null) {
					setFinishDate(new Date());
				}
			}
		}
		setSuccess(false);
	}

	@Override
	public void handle(BaseEvent event) {
		if (event instanceof SMSValidCodeCorrectEvent) {
			// 验证成功
			success();

		} else if (event instanceof SMSValidCodePastDueEvent) {
			// 发生验证码已过期事件
			ValidCodePastDue();

		} else if (event instanceof SMSValidCodeWrongEvent) {
			// 发生输错验证码事件
			ValidCodeWrong();

		}
	}

	public void sendSMS() {
		setLastSendSMSDate(new Date());
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

}

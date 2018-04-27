package lxs.domain.model.user.saga;

import hg.common.component.BaseEvent;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lxs.domain.model.M;
import lxs.domain.model.user.event.register.UserActivedEvent;
import lxs.domain.model.user.event.sms.SMSValidCodeCorrectEvent;
import lxs.domain.model.user.event.sms.SMSValidCodePastDueEvent;
import lxs.domain.model.user.event.sms.SMSValidCodeWrongEvent;
import lxs.pojo.command.user.GetSMSValidCodeCommand;

import org.hibernate.annotations.DynamicUpdate;

/**
 * 
 * @类功能说明：用户注册流程 1、每发送一个新的短信验证码视为一个新的注册流程开始 2、用户在输入正确的短信验证码以后，会创建一个未激活用户实体
 *               3、用户在输入密码后，会被激活
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2015年1月23日上午9:44:05
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_SAGA + "REGISTER")
@DynamicUpdate
public class RegisterSaga extends SMSValidBaseSaga {

	/**
	 * 注册的手机
	 */
	@Column(name = "MOBILE", length = 16)
	private String mobile;

	/**
	 * 流程ID
	 */
	@Column(name = "SAGAID", length = 36)
	private String sagaid;
	
	public final static Integer STATUS_MOBILE_UNCHECK = 1; // 手机号未验证
	public final static Integer STATUS_MOBILE_CHECKED = 2; // 手机号已验证，未激活
	public final static Integer STATUS_ACTIVED = 3; // 已填写密码激活

	public RegisterSaga() {
		super();
	}

	/**
	 * 
	 * @类名：UserRegisterSaga.java
	 * @描述：向某个手机发送了短信验证码，开启一个新注册流程
	 * @@param mobile
	 * @@param validCode
	 */
	public RegisterSaga(GetSMSValidCodeCommand command) {
		setId(command.getToken());
		setMobile(command.getMobile());
		setSagaid(UUID.randomUUID().toString());

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

		setCurrentStatus(RegisterSaga.STATUS_MOBILE_UNCHECK);
	}

	/**
	 * 从业务上检查是否已完成流程
	 */
	@Override
	public boolean checkFinish() {
		return super.checkFinish();
	}

	@Override
	public void handle(BaseEvent event) {
		if (event instanceof UserActivedEvent) {

			// 发生用户已激活事件
			userActived();

		} else if (event instanceof SMSValidCodeCorrectEvent) {

			// 发生用户验证码校验成功事件
			userValidCodeCorrect();

		} else if (event instanceof SMSValidCodePastDueEvent) {

			// 发生验证码已过期事件
			userValidCodePastDue();

		} else if (event instanceof SMSValidCodeWrongEvent) {

			// 发生输错验证码事件
			userValidCodeWrong();

		}
	}

	private void userValidCodeCorrect() {

		setFinish(false);
		setFinishDate(null);
		setSuccess(false);

		if (getCurrentStatus() == RegisterSaga.STATUS_MOBILE_UNCHECK
				.intValue()) {
			setCurrentStatus(RegisterSaga.STATUS_MOBILE_CHECKED);
		}

	}

	private void userActived() {
		setFinish(true);
		setFinishDate(new Date());
		setSuccess(true);

		if (getCurrentStatus() == RegisterSaga.STATUS_MOBILE_CHECKED
				.intValue()) {
			setCurrentStatus(RegisterSaga.STATUS_ACTIVED);
		}
	}

	private void userValidCodeWrong() {
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

	private void userValidCodePastDue() {
		// 如果注册流程结束时间为空，当前时间为结束时间
		setFinish(true);
		if (getFinishDate() == null) {
			setFinishDate(new Date());
		}
		setSuccess(false);
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
	
	public String getSagaid() {
		return sagaid;
	}

	public void setSagaid(String sagaid) {
		this.sagaid = sagaid;
	}

	public static void main(String[] args) {
		System.out.println(Math.round(Math.random() * 10));
	}

}

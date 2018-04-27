package hsl.pojo.dto.event;

import java.util.Date;

public class SMSValidateRecordDTO {

	/**
	 * 发送时间
	 */
	private Date sendDate;

	/**
	 * 允许输入次数
	 */
	private Integer validTimes;

	/**
	 * 已校验次数
	 */
	private Integer currentTimes;

	/**
	 * 超时时间，分钟
	 */
	private Integer overtime;

	/**
	 * 1 待验证 2 已失败结束 3 已成功结束
	 */
	private Integer status;

	/**
	 * 业务场景 1 注册时验证 2 找回密码验证
	 */
	private Integer scene;

	/**
	 * 验证码值
	 */
	private String value;
	
	/**
	 * 接受验证码的手机号
	 */
	private String mobile;
	
	
	public Integer getCurrentTimes() {
		return currentTimes;
	}

	public void setCurrentTimes(Integer currentTimes) {
		this.currentTimes = currentTimes;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Integer getValidTimes() {
		return validTimes;
	}

	public void setValidTimes(Integer validTimes) {
		this.validTimes = validTimes;
	}

	public Integer getOvertime() {
		return overtime;
	}

	public void setOvertime(Integer overtime) {
		this.overtime = overtime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getScene() {
		return scene;
	}

	public void setScene(Integer scene) {
		this.scene = scene;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}

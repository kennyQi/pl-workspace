package hsl.domain.model.event;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;
import hsl.pojo.command.SendValidCodeCommand;
import hsl.pojo.exception.UserException;
import java.util.Date;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name=M.TABLE_PREFIX_HSL_USER+"SMSVALIDATECODE")
public class SMSValidateRecord extends BaseModel{

	/**
	 * 发送时间
	 */
	@Column(name="SEND_DATE",columnDefinition=M.DATE_COLUM)
	private Date sendDate;

	/**
	 * 允许输入次数
	 */
	@Column(name="VALID_TIMES",columnDefinition=M.NUM_COLUM)
	private Integer validTimes;

	/**
	 * 已校验次数
	 */
	@Column(name="CURRENT_TIMES",columnDefinition=M.NUM_COLUM)
	private Integer currentTimes;

	/**
	 * 超时时间，分钟
	 */
	@Column(name="OVER_TIME",columnDefinition=M.NUM_COLUM)
	private Integer overtime;

	/**
	 * 1 待验证 2 已失败结束 3 已成功结束
	 */
	@Column(name="STATUS",columnDefinition=M.NUM_COLUM)
	private Integer status;

	/**
	 * 业务场景 1 注册时验证 2 找回密码验证
	 */
	@Column(name="SCENE",columnDefinition=M.NUM_COLUM)
	private Integer scene;

	/**
	 * 验证码值
	 */
	@Column(name="VALUE",length=64)
	private String value;
	
	/**
	 * 接受验证码的手机号
	 */
	@Column(name="MOBILE",length=11)
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
	
	/**
	 * 创建验证码信息
	 * @param command
	 */
	public void createSMSValidateRecord(SendValidCodeCommand command){
		setMobile(command.getMobile());
		setCurrentTimes(0);
		setOvertime(10);
		setScene(command.getScene());
		setSendDate(new Date());
		setStatus(1);
		Random random=new Random();
		setValue(""+(int)(random.nextDouble()*10000000/10));
		setValidTimes(3);
		setId(UUIDGenerator.getUUID());
	}
	
	public void checkSMSCode(String value) throws UserException{
		
		// 计算验证时间与发送时间差
		Long time = new Date().getTime() - this.getSendDate().getTime();
		// 验证时间与发送时间相差的分钟数
		Long mins = time / 1000 / 60;
		
		//判断验证状态时是否需要验证
		if (getStatus() == 1||getStatus() == 3) {//前端验证码不需要使用一次后过期
			// 判断验证码是否过期（时间过期或者验证次数已达最大值）
			if (mins > this.getOvertime().longValue() 	||  this.getValidTimes() == 0) {
				setStatus(2);
				throw new UserException(UserException.VALIDCODE_OVERTIME,
						"短信验证码过期");
			}
			// 判断验证码是否正确
			else if (!this.getValue().equals(value)) {
				setCurrentTimes(this.getCurrentTimes() + 1);
				setValidTimes(this.getValidTimes() - 1);
				throw new UserException(UserException.RESULT_VALICODE_WRONG,
						"短信验证码错误");
			}
			//验证成功
			else{
				setCurrentTimes(this.getCurrentTimes() + 1);
				setValidTimes(this.getValidTimes() - 1);
				setStatus(3);
			}
		}else{
			throw new UserException(UserException.VALIDCODE_OVERTIME,
					"短信验证码过期");
		}
	}

}

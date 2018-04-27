package hg.fx.command.smsCodeRecord;

import java.util.Date;
import hg.framework.common.base.BaseSPICommand;

/**
 * @类功能说明：doto
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江汇购科技有限公司
 * @部门： 技术部
 * @作者： zhouwei
 * @创建时间： 2016年6月1日 下午7:25:44
 * @版本： V1.0
 */
public class CreateSmsCodeRecordCommand extends BaseSPICommand {

	private static final long serialVersionUID = 1L;

	/**
	 * 接收手机号
	 * */
	private String mobile;

	/**
	 * 验证码
	 * */
	private String code;

	/**
	 * 创建时间
	 */
	private Date createDate;

	/**
	 * 失效时间
	 */
	private Date invalidDate;

	/**
	 * type=1,注册类型 
	 * type=2,忘记密码类型
	 */
	private Integer type;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}

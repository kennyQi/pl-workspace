package hg.fx.spi.qo;

import java.util.Date;

import hg.framework.common.base.BaseSPIQO;

/**
 * @类功能说明：doto
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江汇购科技有限公司
 * @部门： 技术部
 * @作者： zhouwei
 * @创建时间： 2016年6月1日 下午7:09:20
 * @版本： V1.0
 */
public class SmsCodeRecordSQO extends BaseSPIQO {

	private static final long serialVersionUID = 1L;

	/**
	 * 接收手机号
	 * */
	private String mobile;

	/**
	 * 是否已使用 Y--已使用 N--未使用
	 */
	private Boolean used = false;

	/**
	 * type=1,注册类型 type=2,忘记密码类型
	 */
	private Integer type;

	/**
	 * 开始时间
	 */
	private Date beginTime;

	/**
	 * 结束时间
	 */
	private Date endTime;

	/**
	 * 验证码
	 */
	private String code;;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Boolean getUsed() {
		return used;
	}

	public void setUsed(Boolean used) {
		this.used = used;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}

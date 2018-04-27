package hg.fx.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import hg.demo.member.common.domain.model.def.O;
import hg.framework.common.base.BaseStringIdModel;

/**
 * @类功能说明：短信验证码发送记录
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2016-5-31下午7:19:00
 * @版本：V1.0
 * 
 */
@Entity
@Table(name = O.FX_TABLE_PREFIX + "SMS_CODE_RECORD")
public class SmsCodeRecord extends BaseStringIdModel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 注册验证码类型
	 */
	public static final Integer TYPE_REGISTER = 1;
	/**
	 * 注册审核状态通知类型
	 */
	public static final Integer TYPE_REGISTER_NOTIFY_STATUS = 2;
	/**
	 * 里程预警通知类型
	 */
	public static final Integer TYPE_LC_WARN = 3;
	/**
	 * 系统消息通知类型
	 */
	public static final Integer TYPE_NEWS_NOTIFY = 4;
	/**
	 * 忘记密码类型
	 */
	public static final Integer TYPE_FORGET_PASSWORD = 5;
	/**
	 * 修改手机号码类型
	 */
	public static final Integer TYPE_MODIFY_PHONE = 6;

	/**
	 * 接收手机号
	 * */
	@Column(name = "MOBILE", length = 16)
	private String mobile;

	/**
	 * 验证码
	 * */
	@Column(name = "CODE", length = 16)
	private String code;

	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_DATE", columnDefinition = O.DATE_COLUM)
	private Date createDate;

	/**
	 * 失效时间
	 */
	@Column(name = "INVALID_DATE", columnDefinition = O.DATE_COLUM)
	private Date invalidDate;

	/**
	 * 是否已使用 
	 * Y--已使用 
	 * N--未使用
	 */
	@Type(type = "yes_no")
	@Column(name = "USED")
	private Boolean used = false;

	/**
	 * type=1,注册验证码类型
	 * type=2,注册审核状态通知类型
	 * type=3,积分预警通知类型
	 * type=4,系统消息通知类型
	 * type=5,忘记密码类型
	 * type=6,修改手机号码类型
	 */
	@Column(name = "TYPE", columnDefinition = O.TYPE_NUM_COLUM)
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

}

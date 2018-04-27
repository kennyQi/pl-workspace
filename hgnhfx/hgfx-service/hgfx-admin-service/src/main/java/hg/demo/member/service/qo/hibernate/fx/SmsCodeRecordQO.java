package hg.demo.member.service.qo.hibernate.fx;

import java.util.Date;

import hg.framework.service.component.annotations.QOAttr;
import hg.framework.service.component.annotations.QOAttrType;
import hg.framework.service.component.annotations.QOConfig;
import hg.framework.service.component.base.hibernate.BaseHibernateQO;
import hg.fx.spi.qo.SmsCodeRecordSQO;

/**
 * @类功能说明：doto
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江汇购科技有限公司
 * @部门： 技术部
 * @作者： zhouwei
 * @创建时间： 2016年6月1日 下午7:14:44
 * @版本： V1.0
 */
@QOConfig(daoBeanId = "smsCodeRecordDAO")
public class SmsCodeRecordQO extends BaseHibernateQO<String> {

	private static final long serialVersionUID = 1L;

	// 排序值
	private static final Integer ORDER_DESC = -1;

	/**
	 * 接收手机号
	 * */
	@QOAttr(name = "mobile", type = QOAttrType.EQ)
	private String mobile;

	/**
	 * 是否已使用 Y--已使用 N--未使用
	 */
	@QOAttr(name = "used", type = QOAttrType.EQ)
	private Boolean used = false;

	/**
	 * type=1,注册类型 type=2,忘记密码类型
	 */
	@QOAttr(name = "type", type = QOAttrType.EQ)
	private Integer type;

	/**
	 * 倒序
	 */
	@QOAttr(name = "createDate", type = QOAttrType.ORDER)
	private Integer orderDesc;

	/**
	 * 开始时间
	 */
	@QOAttr(name = "createDate", type = QOAttrType.GE)
	private Date beginTime;

	/**
	 * 结束时间
	 */
	@QOAttr(name = "createDate", type = QOAttrType.LE)
	private Date endTime;

	/**
	 * 验证码
	 */
	@QOAttr(name = "code", type = QOAttrType.EQ)
	private String code;

	public static SmsCodeRecordQO build(SmsCodeRecordSQO sqo) {
		SmsCodeRecordQO qo = new SmsCodeRecordQO();
		qo.setMobile(sqo.getMobile());
		qo.setType(sqo.getType());
		qo.setUsed(sqo.getUsed());
		if (sqo.getCode() != null) {
			qo.setCode(sqo.getCode());
		}
		qo.setOrderDesc(ORDER_DESC);
		qo.setBeginTime(sqo.getBeginTime());
		qo.setEndTime(sqo.getEndTime());
		return qo;
	}

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

	public Integer getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(Integer orderDesc) {
		this.orderDesc = orderDesc;
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

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
 * @作者： xinglj
 * @创建时间： 2016年6月1日 下午7:14:44
 * @版本： V1.0
 */
@QOConfig(daoBeanId = "warnSmsRecordDAO")
public class WarnSmsRecordQO extends BaseHibernateQO<String> {

	private static final long serialVersionUID = 1L;

	// 排序值
	private static final Integer ORDER_DESC = -1;

	/**
	 * 接收手机号
	 * */
	@QOAttr(name = "mobile", type = QOAttrType.EQ)
	private String mobile;
 
	/**
	 * 倒序
	 */
	@QOAttr(name = "createDate", type = QOAttrType.ORDER)
	private Integer orderDesc;
 
 

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
 

	public Integer getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(Integer orderDesc) {
		this.orderDesc = orderDesc;
	}
 

}

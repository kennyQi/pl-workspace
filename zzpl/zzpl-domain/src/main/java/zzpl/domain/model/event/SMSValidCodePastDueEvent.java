package zzpl.domain.model.event;

import hg.common.component.BaseEvent;
import hg.common.util.SysProperties;

/**
 * 
 * @类功能说明：用户验证码过期事件
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2015年1月23日上午10:11:05
 * 
 */
@SuppressWarnings("serial")
public class SMSValidCodePastDueEvent extends BaseEvent {

	private String mobile;

	public SMSValidCodePastDueEvent(String name, String params, String describe) {
		super(name, params, describe, SysProperties.getInstance().get(
				"projectId"), SysProperties.getInstance().get("envId"));
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}

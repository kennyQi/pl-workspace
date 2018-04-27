package hsl.pojo.command.sign;

import hg.common.component.BaseCommand;

/**
 * 
 * @类功能说明：删除活动签到Command
 * @类修改者：hgg
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：hgg
 * @创建时间：2015年9月17日下午3:41:56
 *
 */
public class DeleteActivitySignCommand extends BaseCommand{

	private static final long serialVersionUID = -1966240822231333343L;

	/**
	 * 活动签到ID
	 */
	private String signId;

	public String getSignId() {
		return signId;
	}

	public void setSignId(String signId) {
		this.signId = signId;
	}
	
}

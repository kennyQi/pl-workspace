package plfx.jp.command.pay.balances;

import hg.common.component.BaseCommand;

/**
 * 
 * @类功能说明：删除支付宝余额COMMAND
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年12月30日上午9:53:52
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class DeletePayBalancesCommand extends BaseCommand{
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}

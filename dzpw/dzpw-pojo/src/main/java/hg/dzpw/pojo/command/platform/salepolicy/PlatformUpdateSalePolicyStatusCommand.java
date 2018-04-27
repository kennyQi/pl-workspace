package hg.dzpw.pojo.command.platform.salepolicy;

import hg.dzpw.pojo.common.DZPWPlatformBaseCommand;

/**
 * @类功能说明： 更新价格策略状态
 * @类修改者：
 * @修改日期：2014-11-19上午10:43:37
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzx
 * @创建时间：2014-11-19上午10:43:37
 */
@Deprecated
public class PlatformUpdateSalePolicyStatusCommand extends DZPWPlatformBaseCommand {
	private static final long serialVersionUID = 1L;

	private String salePolicyId;

	public String getSalePolicyId() {
		return salePolicyId;
	}
	public void setSalePolicyId(String salePolicyId) {
		this.salePolicyId = salePolicyId;
	}
}
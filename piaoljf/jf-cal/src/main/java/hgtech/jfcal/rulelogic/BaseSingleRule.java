/**
 * @文件名称：BaseSingleRule.java
 * @类路径：hgtech.jfcal.rulelogic
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014-9-3下午4:59:56
 */
package hgtech.jfcal.rulelogic;

import hgtech.jfcal.model.Rule;

/**
 * @类功能说明：一个规则的基类
 * @类修改者：
 * @修改日期：2014-9-3下午4:59:56
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-3下午4:59:56
 *
 */
public abstract class BaseSingleRule implements SingleRule{
	public Rule rule;
	/* (non-Javadoc)
	 * @see hgtech.jfcal.rulelogic.SingleRule#setRule(hgtech.jfcal.model.Rule)
	 */
	@Override
	public void setRule(Rule r) {
		rule=r;
	}

}

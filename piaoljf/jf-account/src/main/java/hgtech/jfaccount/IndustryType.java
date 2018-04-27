/**
 * @文件名称：IndustryType.java
 * @类路径：hgtech.jfaccount
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014-9-5下午2:39:25
 */
package hgtech.jfaccount;

import hgtech.jf.tree.WithChildren;

import java.util.LinkedList;

/**
 *  行业类别
 *  @deprecated use <code>TradeType</code>
 * @类修改者：
 * @修改日期：2014-9-5下午2:39:25
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-5下午2:39:25
 *
 */
public class IndustryType implements WithChildren<IndustryType>{
	/**
	 * 编码，唯一约束
	 */
	public String code;
	public String name;
	/**
	 * 上级类别
	 */
	public IndustryType upperType;
	public LinkedList<WithChildren<IndustryType>> sublist=new LinkedList<WithChildren<IndustryType>>();
	/* (non-Javadoc)
	 * @see hgtech.jfaccount.WithChildren#getSubList()
	 */
	@Override
	public LinkedList<WithChildren<IndustryType>> getSubList() {
		return sublist;
	}
	/* (non-Javadoc)
	 * @see hgtech.jfaccount.WithChildren#getMe()
	 */
	@Override
	public IndustryType getMe() {
		return this;
	}
}

/**
 * @文件名称：VisitTreeUp.java
 * @类路径：hgtech.jf.tree
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014-9-15下午2:35:00
 */
package hgtech.jf.tree;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2014-9-15下午2:35:00
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-15下午2:35:00
 *
 */
public interface VisitTreeUp {
	/**
	 * 找到上级
	 * @方法功能说明：
	 * @修改者名字：xinglj
	 * @修改时间：2014-9-15下午2:35:51
	 * @修改内容：
	 * @参数：@return
	 * @return:Object
	 * @throws
	 */
	public WithChildren findUpper(WithChildren o,int level);
}

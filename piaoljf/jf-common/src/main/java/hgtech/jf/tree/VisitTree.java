/**
 * @文件名称：VisitTree.java
 * @类路径：hgtech.jf.tree
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014-9-15上午11:09:02
 */
package hgtech.jf.tree;

/**
 * 访问到一个元素（叶子、树枝都有）时候的事件
 * @类功能说明：
 * @类修改者：
 * @修改日期：2014-9-15上午10:57:39
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-15上午10:57:39
 *
 */
public interface VisitTree{
	/**
	 * 
	 * @方法功能说明：
	 * @修改者名字：xinglj
	 * @修改时间：2014-9-15下午2:12:32
	 * @修改内容：
	 * @参数：@param element 访问到的那个元素
	 * @参数：@param level 递归访问的层级
	 * @return:void
	 * @throws
	 */
	public void  elementVisisted(Object element,int level);
}
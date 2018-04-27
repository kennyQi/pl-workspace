/**
 * @文件名称：TreeView.java
 * @类路径：hgtech.jfaccount
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014-9-12上午10:28:18
 */
package hgtech.jf.tree;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * @类功能说明：有子代的结构
 * @类修改者：
 * @修改日期：2014-9-12上午10:28:18
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-12上午10:28:18
 *
 */
public interface WithChildren<T> extends Serializable{

	/**
	 * @return the subList
	 */
	public abstract LinkedList<WithChildren<T>> getSubList();

	public T getMe();
}
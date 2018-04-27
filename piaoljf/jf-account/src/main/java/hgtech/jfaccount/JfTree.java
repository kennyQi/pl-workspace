/**
 * @文件名称：JfAccountView.java
 * @类路径：hgtech.jfaccount
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014-9-10下午5:36:35
 */
package hgtech.jfaccount;

import hgtech.jf.tree.WithChildren;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @deprecated use WithChildren
 * @类功能说明： 树状视图
 * @逻辑主键 acct, subAcct
 * @类修改者：
 * @修改日期：2014-9-10下午5:36:35
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-10下午5:36:35
 *
 */
public class JfTree<T> {
	public T element;
	public LinkedList<JfTree> subAcct=new LinkedList<JfTree>();
	/**
	 * 层级，从0开始
	 */
	public int level;
	
	/*
	 * 树的简单缩进表示
	 */
	public String toString() {
		StringBuffer sb=new StringBuffer();
		String tab="";
		for(int i=0;i<level;i++)
			tab +="\t";
		
		sb.append(tab+ element.toString()+"\n");
		for(JfTree<T> a:subAcct)
			sb.append(a.toString());
		return sb.toString();
	}
	
	/**
	 * 
	 * @方法功能说明：
	 * @修改者名字：xinglj
	 * @修改时间：2014-9-12上午11:02:31
	 * @修改内容：
	 * @参数：@param view
	 * @return:void
	 * @throws
	 */
	public void from(WithChildren<T> view){
		this.element=view.getMe();
		for(WithChildren<T> t:view.getSubList()){
			JfTree e=new JfTree<T>();
			e.from(t);
			this.subAcct.add(e);
		}
	}
 
 

	/**
	 * @方法功能说明：
	 * @修改者名字：xinglj
	 * @修改时间：2014年10月8日上午11:18:32
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */

	
}

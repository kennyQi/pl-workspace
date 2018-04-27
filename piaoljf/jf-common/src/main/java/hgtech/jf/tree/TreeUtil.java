/**
 * @文件名称：Util.java
 * @类路径：hgtech.jfaccount
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014-9-12上午9:01:10
 */
package hgtech.jf.tree;

import java.util.LinkedList;

/**账户树的访问工具
 * @类功能说明：
 * @类修改者：
 * @修改日期：2014-9-12上午9:01:10
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-12上午9:01:10
 *
 */
public class TreeUtil {

	/*
	 * 树的简单缩进表示
	 */
	public static String toTreeString(WithChildren element, int level) {
		final StringBuffer sb=new StringBuffer();
		VisitTree visit=new VisitTree() {
			
			@Override
			public void elementVisisted(Object element, int level) {
				String tab="";
				for(int i=0;i<level;i++)
					tab +="\t";		
				sb.append(tab+ element+"\n");
			}
		};
		downTree(element, 0, visit);
		return sb.toString();
	}
	
	/*
	 * 树的遍历 自上向下
	 */
	public static void  downTree(WithChildren element, int level,VisitTree visit) {
		
		visit.elementVisisted( element , level);
		LinkedList<WithChildren> subList = (element).getSubList();
		level++;
		for( WithChildren e:subList)
			downTree(e, level, visit);
	}
	
	/**
	 * @return 从一个节点向上树的遍历  自下而上
	 * @方法功能说明：级联更新上级的账户积分值
	 * @修改者名字：xinglj
	 * @修改时间：2014-9-12上午9:24:12
	 * @修改内容：
	 * @参数：@param a
	 * @return 顶级上级账户
	 * @throws
	 */
	public static WithChildren upTree(WithChildren node,int level,VisitTreeUp visit) {
		WithChildren the=node;
		while(true){
			WithChildren upperAcc=visit.findUpper(the,level);
			level++;
			if(upperAcc!=null){
				if(!upperAcc.getSubList().contains(the))
					upperAcc.getSubList().add(the);
				the=upperAcc;
			}else
				break;
		}
		return the;
	}
	
	public static void main(String[] args) {
	//	test_toTreeString();
	}

	/**
	 * @方法功能说明：
	 * @修改者名字：xinglj
	 * @修改时间：2014-9-12上午11:19:57
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	/*private static void test_toTreeString() {
		SetupApplicationContextDemo票量网.init();
		JfAccount tree=new JfAccount();
		tree.uk.type=SetupApplicationContextDemo票量网.旅游账户类型;
		tree.jf=100;
		
		
		JfAccount grow=new JfAccount();
		grow.jf=20;
		grow.uk.type=SetupApplicationContextDemo票量网.成长账户类型;
		JfAccount consume=new JfAccount();
		consume. jf=80;
		consume.uk.type=SetupApplicationContextDemo票量网.门票账户类型 ;
		
		tree.subList.add(grow);
		tree.subList.add(consume);
		System.out.println(TreeUtil.toTreeString(tree,0));
	}*/
}

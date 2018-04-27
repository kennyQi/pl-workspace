/**
 * @文件名称：JfAccountView.java
 * @类路径：hgtech.jfaccount
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014-9-10下午5:36:35
 */
package hgtech.jfaccount;

import hgtech.jf.tree.TreeUtil;
import hgtech.jf.tree.VisitTree;
import hgtech.jf.tree.VisitTreeUp;
import hgtech.jf.tree.WithChildren;
import hgtech.jfaccount.dao.JfAccountHome_Mem;
import hgtech.jfaccount.dao.JfAccountTypeDao;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * @类功能说明：账户的树状视图
 * @逻辑主键 acct, subAcct
 * @类修改者：
 * @修改日期：2014-9-10下午5:36:35
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-10下午5:36:35
 *
 */
public class JfAccountView {
	public JfAccountTypeDao acctypeHome;
	public JfAccountHome_Mem accHome;
	public  WithChildren<JfAccount> topAccount;
	
	/**
	 * 根据节点账户，生成账户树。
	 * 方法：根据节点账户的账户类型的上级依次向上找。
	 * @方法功能说明：
	 * @修改者名字：xinglj
	 * @修改时间：2014-9-15上午9:35:32
	 * @修改内容：
	 * @参数：@param accounts
	 * @return:void
	 * @throws
	 */
	public  void genAccountTree(Collection<JfAccount> accounts){
		//check 
		if(acctypeHome==null || accHome==null)
			throw new RuntimeException("");
		
		
		Set<JfAccount> topAccounts=new HashSet<JfAccount>();
		for(JfAccount a:accounts){
			topAccounts.add(updateFatherAccount(a));
		}
		//顶级账户应只有一个
		if(topAccounts.size()!=1){
			System.err.println(topAccounts);
			throw new RuntimeException("顶级账户应只有一个");
		}else{
			topAccount=topAccounts.iterator().next();
			
		}
		
	}

	/**
	 * @方法功能说明：级联更新上级的账户积分值
	 * @修改者名字：xinglj
	 * @修改时间：2014-9-12上午9:24:12
	 * @修改内容：
	 * @参数：@param a
	 * @return 顶级上级账户
	 * @throws
	 */
	JfAccount updateFatherAccount(final JfAccount a) {
		
		return (JfAccount) TreeUtil.upTree(a, 0, new VisitTreeUp() {

			@Override
			public WithChildren findUpper(WithChildren o, int level) {
				JfAccount the=(JfAccount) o;
				if(the.uk.type.upper!=null){
					JfAccount upperAcc=accHome.getOrNew(new JfAccountUK( the.uk.user, the.uk.type.upper));
					upperAcc.jf+=a.jf;
					return upperAcc;
				}else
				{	 
					return null;
				}
			}
			
		});
		
		//这是原先直接的代码，保留到2014.11.1
		/*JfAccount the=a;
		while(the.type.upper!=null){
			JfAccount upperAcc=accHome.getOrNew(the.user, the.type.upper);
			upperAcc.jf+=a.jf;
			if(!upperAcc.subList.contains(the))
				upperAcc.subList.add(the);
			the=upperAcc;
		}
		return the;*/
	}
	
}

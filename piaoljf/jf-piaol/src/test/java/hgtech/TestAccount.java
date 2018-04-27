/**
 * @文件名称：TestAccount.java
 * @类路径：hgtech
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014-9-15下午3:55:57
 */
package hgtech;

import hgtech.jf.JfChangeApply;
import hgtech.jf.entity.StringUK;
import hgtech.jf.entity.dao.BaseEntityFileDao;
import hgtech.jf.entity.dao.EntityDao;
import hgtech.jf.piaol.SetupApplicationContextDemo票量网;
import hgtech.jf.tree.TreeUtil;
import hgtech.jfaccount.AbstractAdjustBean;
import hgtech.jfaccount.JfAccount;
import hgtech.jfaccount.JfAccountType;
import hgtech.jfaccount.JfAccountTypeUK;
import hgtech.jfaccount.JfAccountUK;
import hgtech.jfaccount.JfAccountView;
import hgtech.jfaccount.JfFlow;
import hgtech.jfaccount.JfUser;
import hgtech.jfaccount.dao.JfAccountHome_Mem;
import hgtech.jfaccount.service.AccountService;
import hgtech.jfcal.model.CalResult;
import hgtech.jfcal.model.Rule;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2014-9-15下午3:55:57
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-15下午3:55:57
 *
 */
public class TestAccount {
	
	@Test
	public void testUK(){
		SetupApplicationContextDemo票量网.init();
		Map  m=new HashMap ();
		m.put(new JfAccountTypeUK(SetupApplicationContextDemo票量网.sysDomain, SetupApplicationContextDemo票量网.ct成长).getkey(), "dsfs");
		System.out.println(m.keySet().iterator().next().equals(new JfAccountTypeUK(SetupApplicationContextDemo票量网.sysDomain, SetupApplicationContextDemo票量网.ct成长).getkey()));
		boolean con = m.containsKey(new JfAccountTypeUK(SetupApplicationContextDemo票量网.sysDomain, SetupApplicationContextDemo票量网.ct成长).getkey());
		System.out.println("containskey "+con);
		Assert.assertTrue(con);
//		m.put(new JfAccountTypeUK(SetupDemo票量网.sysDomain, SetupDemo票量网.ct成长), "dsfs");
//		System.out.println(m.size());
//		Assert.assertEquals(m.size(), 1);
	}
	
	//演示 账户的树状视图
	@Test
	public  void testTreeAccount( ) {
		SetupApplicationContextDemo票量网.init();
		JfAccountHome_Mem accHome=new JfAccountHome_Mem();
		
		JfUser user1=new JfUser();
		user1.code= new StringUK("A130101197301130917");
		user1.name="邢立军";
		
		JfAccount acc1=accHome.newEntity(new JfAccountUK(user1, SetupApplicationContextDemo票量网.成长账户类型));
		acc1.jf=10000;
		
		JfAccount acc2 =accHome.newEntity(new JfAccountUK(user1,SetupApplicationContextDemo票量网.门票账户类型));
		acc2.jf=100;
		
		JfAccount acc3=accHome.newEntity(new JfAccountUK(user1,SetupApplicationContextDemo票量网.huijb成长账户类型));
		acc3.jf=1;
		
		JfAccountView view = new JfAccountView();
		view.acctypeHome=SetupApplicationContextDemo票量网.acctTypeHome;
		view.accHome=accHome;
		view.genAccountTree(Arrays.asList(acc1,acc2,acc3));
		System.out.println(TreeUtil.toTreeString(view.topAccount,0));
	}
	
	@Test
	public void test_toTreeString() {
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
	}
	
	//累积积分
	//@Test
	/*public void testCommitJf(){
		SetupApplicationContextDemo票量网.init();
		AccountService acManage = new AccountService();
		acManage.accountDao =new JfAccountHome_Mem();
		acManage.userDao=new BaseEntityFileDao<StringUK, JfUser>(JfUser.class);
		acManage.flowDao=new BaseEntityFileDao<StringUK, JfFlow>(JfFlow.class){
			int flow=0;
			public void saveEntity(JfFlow en) {
				en.flowId=new StringUK( ++flow+"");
				super.saveEntity(en);
			};
		};

		

		Rule rule=new Rule();
		rule.accountType=SetupApplicationContextDemo票量网.成长账户类型;
		CalResult result =new CalResult();
		result.out_jf=100;
		result.out_resultCode="Y";
		result.in_userCode=new String("tom");
		result.in_rule=rule;

		acManage.commit(result, 3);
		result.out_jf=-100;
		acManage.commit(result, 3);
		
		final JfAccountType 成长账户类型 = SetupApplicationContextDemo票量网.成长账户类型;
		AbstractAdjustBean adjust =new AbstractAdjustBean() {
			@Override
			protected Object getAcctTypebyId(String accountTypeId2) {
				return   成长账户类型;
			}
		};
		adjust.bean.jf=100;
		adjust.bean.userCode=new String("tom");
		adjust.bean. remark="兑奖";
		adjust. bean.batchNo="201411";
		acManage.adjust(adjust);
		
		System.out.println(acManage.userDao.getEntities().values());
		System.out.println(acManage.accountDao.getEntities().values());
		System.out.println(acManage.flowDao.getEntities().values());


	}*/
}

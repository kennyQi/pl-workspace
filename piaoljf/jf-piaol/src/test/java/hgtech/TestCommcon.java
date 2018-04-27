/**
 * @文件名称：TestCommcon.java
 * @类路径：hgtech
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年10月8日上午11:18:50
 */
package hgtech;

import java.io.File;

import hgtech.jf.entity.dao.SaveObjectUtilJsonImp;
import hgtech.jf.piaol.SetupApplicationContextDemo票量网;
import hgtech.jf.piaol.SetupSpiApplicationContext;
import hgtech.jf.tree.TreeUtil;
import hgtech.jf.tree.WithChildren;
import hgtech.jfaccount.JfAccountType;
import hgtech.jfaccount.SetupAccountContext;
import hgtech.jfaccount.dao.JfAccountTypeDao;

import org.junit.Assert;
import org.junit.Test;


/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2014年10月8日上午11:18:50
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月8日上午11:18:50
 *
 */
public class TestCommcon {
	/*@Test
	public   void testTree() {
		SetupApplicationContext票量网.init();
		JfTree<JfAccount> tree=new JfTree<JfAccount>();
		tree.level=0;
		tree.element=new JfAccount();
		tree.element.uk.type=SetupApplicationContext票量网.票量网总账户类型;
		tree.element.jf=100;
		
		
		JfTree<JfAccount> grow=new JfTree();
		grow.level=1;
		grow.element=new JfAccount();
		grow.element.jf=20;
		grow.element.uk.type=SetupApplicationContext票量网.成长账户类型;
		JfTree<JfAccount> consume=new JfTree();
		consume.level=1;
		consume.element=new JfAccount();
		consume.element.jf=80;
		consume.element.uk.type=SetupApplicationContext票量网.消费账户类型;
		
		tree.subAcct.add(grow);
		tree.subAcct.add(consume);
		System.out.println(tree);
	}*/
	
	@Test
	public void testSaveObject(){WithChildren a;
		SetupSpiApplicationContext.init();
		JfAccountTypeDao typeDao =new SetupSpiApplicationContext. JfAccountTypeMemDao();
		JfAccountType acctT =SetupAccountContext.topAcctType;
		System.out.println(acctT.getSubList().get(0).getMe().getName());
		SaveObjectUtilJsonImp.save(new File("d:/test.json"), acctT);
		
		JfAccountType  reado=(JfAccountType) SaveObjectUtilJsonImp.read(new File("d:/test.json"), JfAccountType.class);
		System.out.println("==============");
		System.out.println(reado);
		Assert.assertTrue(reado.readUK().equals(acctT.readUK()));
		
		
	}
}

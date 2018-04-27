/**
 * @文件名称：DomainSetup.java
 * @类路径：hgtech.jfaccount
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014-9-5下午2:48:11
 */
package hgtech.jf.piaol;

import java.util.List;

import hgtech.jf.JfProperty;
import hgtech.jf.tree.TreeUtil;
import hgtech.jf.tree.WithChildren;
import hgtech.jfaccount.JfAccountTypeUK;
import hgtech.jfaccount.JfTradeType;
import hgtech.jfaccount.TradeType;
import hgtech.jfaccount.Domain;
import hgtech.jfaccount.IndustryType;
import hgtech.jfaccount.JfAccountType;
import hgtech.jfaccount.dao.JfAccountTypeDao;
import hgtech.jfcal.model.CalModel;

/**
 * @类功能说明：票量网积分的硬编码配置类
 * @类修改者：
 * @修改日期：2014-9-5下午2:48:11
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-5下午2:48:11
 *
 */
public class SetupPiaolApplicationContextHardCoded {

	/**
	 * 这个内部积分系统的单位
	 */
	public static Domain sysDomain;
	//消费类型：成长值、消费
	private static TradeType ctGrow, ctConsume;
	/**
	 * 行业类别
	 */
	public static TradeType   ctTravel;
	/**
	 * 成长积分类型
	 */
	public static JfAccountType accTypeGrow=new JfAccountType();
	/**
	 * 消费积分类型
	 */
	public static JfAccountType accTypeConsume=new JfAccountType();
	/**
	 * 行业顶级积分类型
	 */
	private static JfAccountType accTypeTravel;
	/**
	 * 
	 */
	public static JfAccountType topAcctType;
	
	public static JfAccountTypeDao acctTypeHome =new SetupSpiApplicationContext. JfAccountTypeMemDao();
	
	public static CalModel calModel=new CalModel();
	
	private static  boolean init=false;
	public static void init(){
		if(init)
			return;
		
		//积分交易类型
		JfTradeType.init();
		
//		消费形态
		
		//travel
		ctGrow=new TradeType();
		ctGrow.code="grow";
		ctGrow.name="成长值";
		
		
		ctConsume=new TradeType();
		ctConsume.code="consume";
		ctConsume.name="票豆";
		
		ctTravel =new TradeType();
		ctTravel.code="e-ticket";
		ctTravel.name="商旅业";
		
	 	
		ctConsume.upperType=ctTravel;
		ctTravel.getSubList().add(ctConsume);
		
		ctGrow.upperType=ctTravel;
		ctTravel.getSubList().add(ctGrow);
		
//		机构
		sysDomain =new Domain();
		sysDomain.code="piaol";
		sysDomain.name="票量网";
		sysDomain.jfRate=1.0f;
		sysDomain.type=ctTravel;
		sysDomain.ip=JfProperty.getProperties().getProperty("clientip");
		
		topAcctType=acctTypeHome.genAccountTypeTree(acctTypeHome, sysDomain,sysDomain.type);
		topAcctType.setName("票量积分");
		
		accTypeGrow=acctTypeHome.get(new JfAccountTypeUK( sysDomain, ctGrow));
		accTypeGrow.setName("成长值");
		accTypeConsume=acctTypeHome.get(new JfAccountTypeUK(sysDomain, ctConsume));
		accTypeConsume.setName("票豆");
		accTypeTravel=acctTypeHome.get(new JfAccountTypeUK(sysDomain, ctTravel));

		System.out.println("all account types:\n"+acctTypeHome.getEntities());
		System.out.println("trade:\n"+TreeUtil.toTreeString(ctTravel, 0));
		System.out.println("domain:\n"+TreeUtil.toTreeString(sysDomain, 0));  
		System.out.println("accounttype:\n"+ TreeUtil.toTreeString(topAcctType, 0));
		
		// rule init
//		calModel.project.ruleSet.add(r);
		
		init=true;
	}
	
	public static JfAccountType findType(String code){
		List<WithChildren<JfAccountType>> list=accTypeTravel.getSubList();
		for(WithChildren<JfAccountType> t:list){
			JfAccountType account=(JfAccountType)t;
			if(account.getCode().equals(code))
				return (JfAccountType)account;
		}
		return null;
	}
	
	public static void main(String[] args) {
		SetupSpiApplicationContext.init();
	}
	
}

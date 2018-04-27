/**
 * @文件名称：DomainSetup.java
 * @类路径：hgtech.jfaccount
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014-9-5下午2:48:11
 */
package hgtech.jf.piaol;

import hgtech.jf.tree.TreeUtil;
import hgtech.jfaccount.JfAccountTypeUK;
import hgtech.jfaccount.JfTradeType;
import hgtech.jfaccount.TradeType;
import hgtech.jfaccount.Domain;
import hgtech.jfaccount.IndustryType;
import hgtech.jfaccount.JfAccountType;
import hgtech.jfaccount.dao.JfAccountTypeDao;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2014-9-5下午2:48:11
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-5下午2:48:11
 *
 */
public class SetupApplicationContextDemo票量网 {

	/**
	 * 这个内部积分系统的单位
	 */
	public static Domain sysDomain, domain汇金宝, domain汇购;
	public static TradeType ct成长, ct门票消费, ct旅游, ct商旅;

	public static JfAccountType 成长账户类型=new JfAccountType();
	public static JfAccountType huijb成长账户类型=new JfAccountType();
	public static JfAccountType 门票账户类型=new JfAccountType();
	public static JfAccountType 旅游账户类型;
	
	public static JfAccountTypeDao acctTypeHome =new SetupSpiApplicationContext. JfAccountTypeMemDao();

	private static  boolean init=false;
	public static void init(){
		if(init)
			return;
		
		//积分交易类型
		JfTradeType.init();
		
//		消费形态
		
		//travel
		ct成长=new TradeType();
		ct成长.code="grow";
		ct成长.name="成长";
		
		ct门票消费=new TradeType();
		ct门票消费.code="ticket";
		ct门票消费.name="门票消费";
		
		ct旅游=new TradeType();
		ct旅游.code="travel";
		ct旅游.name="旅游消费";
		
		ct商旅 =new TradeType();
		ct商旅.code="商旅业";
		ct商旅.name="商旅业";
		
		ct门票消费.upperType=ct旅游;	//上级消费类型
		ct成长.upperType=ct旅游;
		ct旅游.getSubList().add(ct成长);
		ct旅游.getSubList().add(ct门票消费);
		
		ct旅游.upperType=ct商旅;
		ct商旅.getSubList().add(ct旅游);
		
		//ctBank
		TradeType ctBank=new TradeType();
		ctBank.code="bank";
		ctBank.name="金融业";
		
		TradeType ctcCard=new TradeType();
		ctcCard.code="credit";
		ctcCard.name="信用卡消费";
		
		ctcCard.upperType=ctBank;
		ctBank.getSubList().add(ctcCard);
		
		//ct互联网金融
		TradeType ctWWWBank=new TradeType();
		ctWWWBank.code="wwwbank";
		ctWWWBank.name="互联网金融";
		
		TradeType ctHuijinb=new TradeType();
		ctHuijinb.code="huijb";
		ctHuijinb.name="金元宝买卖";
		
		TradeType cthuijinbGrow=new TradeType();
		cthuijinbGrow.code="grow";
		cthuijinbGrow.name="成长";
		
		ctHuijinb.upperType=ctWWWBank;
		cthuijinbGrow.upperType=ctWWWBank;
		ctWWWBank.getSubList().add(ctHuijinb);
		ctWWWBank.getSubList().add(cthuijinbGrow);
		
		//ct集团类
		TradeType ctGroup=new TradeType();
		ctGroup.code="group";
		ctGroup.name="集团";
		
		ct商旅.upperType=ctGroup;
		ctGroup.getSubList().add(ct商旅);
		
//		机构
		domain汇购=new Domain();
		domain汇购.code="huig";
		domain汇购.name="汇购平台";
		domain汇购.jfRate=1.0f;
		domain汇购.type=ctGroup;
		
		sysDomain =new Domain();
		sysDomain.code="piaol";
		sysDomain.name="票量网";
		sysDomain.jfRate=1.0f;
		sysDomain.type=ct商旅;
		sysDomain.upperDomain=domain汇购; //上级机构
		
		domain汇金宝=new Domain();
		domain汇金宝.code="huijb";
		domain汇金宝.name="汇金宝";
		domain汇金宝.jfRate=1.0f;
		domain汇金宝.type=ctWWWBank;
		domain汇金宝.upperDomain=domain汇购;//上级机构
		
		Domain domainbank1=new Domain();
		domainbank1.code="bank1";
		domainbank1.name="某银行";
		domainbank1.type=ctBank;
		domainbank1.upperDomain=domain汇购;
				
		
		domain汇购.getSubList().add(sysDomain);//下级
		domain汇购.getSubList().add(domain汇金宝);//下级
		domain汇购.getSubList().add(domainbank1);
		
		JfAccountType topAcctType;
		topAcctType=JfAccountTypeDao.genAccountTypeTree(acctTypeHome, domain汇购,domain汇购.type);

		成长账户类型=acctTypeHome.get(new JfAccountTypeUK( sysDomain, ct成长));
		旅游账户类型=acctTypeHome.get(new JfAccountTypeUK(sysDomain, ct旅游));
		门票账户类型=acctTypeHome.get(new JfAccountTypeUK(sysDomain, ct门票消费));
		huijb成长账户类型=acctTypeHome.get(new JfAccountTypeUK(domain汇金宝, ct成长));

		System.out.println("all account types:\n"+acctTypeHome.getEntities());
		System.out.println("domain:\n"+TreeUtil.toTreeString(domain汇购, 0));  
		System.out.println("tradetype:\n"+TreeUtil.toTreeString(ctGroup, 0));  
		System.out.println("accounttype:\n"+ TreeUtil.toTreeString(topAcctType, 0));
		
		init=true;
	}
	
	public static void main(String[] args) {
		SetupApplicationContextDemo票量网.init();
	}
	
}

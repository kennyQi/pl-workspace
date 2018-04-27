/**
 
 * @类路径：hgtech.jfaccount
 * @描述： 
 * @作者：xinglj
 * @时间：2014-9-5下午2:48:11
 */
package hgtech.jf.piaol;

import hgtech.jf.JfProperty;
import hgtech.jf.entity.dao.BaseEntityMemDao;
import hgtech.jf.tree.TreeUtil;
import hgtech.jfaccount.Domain;
import hgtech.jfaccount.JfAccountType;
import hgtech.jfaccount.JfAccountTypeUK;
import hgtech.jfaccount.JfTradeType;
import hgtech.jfaccount.SetupAccountContext;
import hgtech.jfaccount.TradeType;
import hgtech.jfaccount.dao.JfAccountTypeDao;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @类功能说明：xx网积分的编码配置类
 * @类修改者：
 * @修改日期：2014-9-5下午2:48:11
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-5下午2:48:11
 *
 */
public class SetupSpiApplicationContext extends SetupAccountContext {

	public static final class JfAccountTypeMemDao extends JfAccountTypeDao {
		private HashMap<Serializable, JfAccountType> map=new LinkedHashMap< Serializable, JfAccountType>();

		@Override
		protected void writeObject(Serializable obj) {
			map = (HashMap<Serializable, JfAccountType>) obj;
		}

		@Override
		protected Object readObject() {
			return map;
		}
	
	}

	public static void init(){
		
		if(init)
			return;
		
		//积分交易类型
		JfTradeType.init();
		
		
		sysDomain=new Domain();
		jfNameTop=new TradeType();
		
		setAcctTypeMemDao();
		
		SAXReader sax=new SAXReader();
    	try {
    		URL url = SetupSpiApplicationContext.class.getResource("/SetupSpiApplicationContext.xml");
    		Document document= sax.read(url);//sax.read(new File("SetupPiaolApplicationContext.xml"));
			Element element=document.getRootElement();
			//从配置文件中得到行业
			Element tradeElement=element.element("trades").element("trade");
			jfNameTop.setCode(tradeElement.elementTextTrim("code"));
			jfNameTop.setName(tradeElement.elementTextTrim("name"));
			
			//从配置文件中读取交易类型
			List<TradeType> tradeTypeList=new ArrayList<TradeType>();
			Element tradeTypeElement=element.element("tradeTypes");
			for(Iterator it=tradeTypeElement.elementIterator();it.hasNext();){
 				Element element2=(Element)it.next();
				TradeType tradeType=new TradeType();
				tradeType.setCode(element2.elementTextTrim("code"));
				tradeType.setName(element2.elementTextTrim("name"));
				//String tradeCode=element2.elementTextTrim("trade");
				tradeType.setUpperType(jfNameTop);
				tradeTypeList.add(tradeType);
				jfNameTop.getSublist().add(tradeType);
			}
			
			//从配置文件中得到机构
			Element domainElement=element.element("domains").element("sysdomain");
			sysDomain.setCode(domainElement.elementTextTrim("code"));
			sysDomain.setName(domainElement.elementText("name"));
			if(!"".equals(domainElement.elementText("jfRate"))){
				sysDomain.setJfRate(Float.parseFloat(domainElement.elementTextTrim("jfRate")));
			}
			sysDomain.setIp(JfProperty.getProperties().getProperty("clientip"));
			sysDomain.setPassK(JfProperty.getProperties().getProperty("passK"));
			sysDomain.setIsDetectionSignature(Boolean.parseBoolean(JfProperty.getProperties().getProperty("isDetectionSignature")));
			sysDomain.setType(jfNameTop);
			
			//从配置文件中读取账户类型
			topAcctType=JfAccountTypeDao.genAccountTypeTree(acctTypeDao, sysDomain,sysDomain.type);
			System.out.println(acctTypeDao.getEntities() +" press key to continue..");
			
			topAcctType.setName("汇积分");
			Element accTypeElement=element.element("accountTypes");
			for(Iterator it=accTypeElement.elementIterator();it.hasNext();){
				JfAccountType accType=new JfAccountType();
				Element element3=(Element)it.next();
				//System.out.println(element3.elementTextTrim("name"));
				String tradeTypeCode=element3.elementTextTrim("type");
				//System.out.println(tradeTypeCode);
				for(TradeType tradeType:tradeTypeList){
					if(tradeType.getCode().equals(tradeTypeCode)){
						accType=acctTypeDao.get(new JfAccountTypeUK( sysDomain, tradeType));
					}
				}
				accType.setName(element3.elementTextTrim("name"));
				//acctTypeDao.flush();
			}
			
			//topAcctType =acctTypeDao.get(new JfAccountTypeUK(sysDomain, tradeTypeTop));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
    	
		
		
		System.out.println("trade:\n"+TreeUtil.toTreeString(jfNameTop, 0));
		System.out.println("domain:\n"+TreeUtil.toTreeString(sysDomain, 0));
		System.out.println(topAcctType.getCode()+"   "+topAcctType.getName());
		System.out.println("accounttype:\n"+ TreeUtil.toTreeString(topAcctType, 0));
		
		init=true;
	}

	/**
	 * 商户端的账户类型不变的，使用内存方式
	 */
	protected static void setAcctTypeMemDao() {
		acctTypeDao = new JfAccountTypeMemDao();
	}
	
	public static void main(String[] args) {
		SetupSpiApplicationContext.init();
	}
	
}

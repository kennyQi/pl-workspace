/**
 * @SetupApplicationContext.java Create on 2015年1月19日下午2:01:49
 * Copyright (c) 2012 by www.hg365.com。
 */
package hgtech.jfaccount;

import hgtech.jf.JfProperty;
import hgtech.jf.entity.dao.BaseEntityMemDao;
import hgtech.jf.tree.WithChildren;
import hgtech.jfaccount.dao.JfAccountTypeDao;
import hgtech.jfaccount.dao.JfNameFileDao;
import hgtech.jfaccount.dao.TradeTypeFileDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @类功能说明：账户模块的上下文
 * @类修改者：
 * @修改日期：2015年1月19日下午2:01:49
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=xinglj@hgtech365.com>xinglj</a>
 * @创建时间：2015年1月19日下午2:01:49
 * @version：
 */
public class SetupAccountContext {

	/**
	 * 这个内部积分系统的单位
	 */
	public static Domain sysDomain=new Domain();
	public static Domain hjfDomain = new Domain();
	public static JfName hjfJfName=new JfName("consume","汇积分");
	public static String JFNAME_GROW="grow";
	public static String JFNAME_LICHENG="licheng";
	/**
	 * 本系统的顶级行业类别
	 */
	public static TradeType jfNameTop;

	/**
	 *	 * 本系统顶级账户类型
	 */
	public static JfAccountType topAcctType;
	/**
	 * hui jf 
	 */
	public static JfAccountType accTypeHjf;
	/**
	 * 外部商户的账户类型dao. 这个dao 的数据是threadlocal的
	 */
	public static JfAccountTypeDao  acctTypeDao /*= new JfAccountTypeFileDao()*/;
	/**
	 * 行业dao
	 */
	public static TradeTypeFileDao tradeTypeDao/* = new TradeTypeFileDao()*/;
	
	public static JfNameFileDao jfNameFileDao /*= new JfNameFileDao()*/;
	/**
	 * 所有外部的积分类型
	 */
	//public static List<JfAccountType> externalAccType = new ArrayList<>();
	/**
	 * 积分名称列表
	 * 
	 */
	public static List<JfName> jfNames/*=new ArrayList<>()*/;
	
	/**
	 * 外部积分机构
	 */
	public static Map<String,Domain> jfDomains=new HashMap<String,Domain>(); 
	protected static boolean init = false;

	public static String currentLoginJfUser;

	//系统固化数据初始化
	static {
//		sysDomain=new Domain();
		sysDomain.code=JfProperty.getProperties().getProperty(JfProperty.K_SYS_DOMAIN_CODE,"hjf");
		sysDomain.name=JfProperty.getProperties().getProperty(JfProperty.K_SYS_DOMAIN_NAME,"积分联盟");
		hjfDomain .code="hjf";
		hjfDomain.name="积分联盟";
		
		JfAccountTypeUK hjfAccountTypeUK = new JfAccountTypeUK(hjfDomain, hjfJfName);
	    accTypeHjf=new JfAccountType();
	    accTypeHjf.uk=hjfAccountTypeUK;
	    accTypeHjf.setName("汇积分");		
	}
	/**
	 * 
	 * 得到积分类型（包含系统本身）<br>
	 * 修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * 修改时间：2015年3月26日下午2:32:31
	 * version：
	 * 修改内容：
	 * 参数：@param code
	 * 参数：@return
	 * return:JfAccountType
	 * throws
	 */
	public static JfAccountType findType(String code) {
	    	if(code.equals(accTypeHjf.getCode()))
	    	    return accTypeHjf;
			else {
				JfAccountType jfAccountType = acctTypeDao.get(code);
				if(jfAccountType!=null)
					return jfAccountType;
				else {
					JfAccountType findType = findType(code, topAcctType);
					if(findType==null)
						throw new RuntimeException("没找到对应代码的账户类型:"+code);
					return findType;
				}
			}
	}
	
	/**
	 * 本机构或互通机构
	 * @param domainCode
	 * @return
	 */
	public static Domain findDomain(String domainCode){
		Domain domain ;
		if(isUnion()) {
			if(domainCode.equals(hjfDomain.getCode()))
				return hjfDomain;
			domain = jfDomains.get(domainCode);
			return domain;
		}	else
			return sysDomain;
	}
	
	/**
	 * 返回本商户系统内部的账户类型
	 * @param code
	 * @return
	 */
	public static JfAccountType findSystemType(String code){
		return findType(code, topAcctType);
	}
	
	
	/**
	 * 适用于商户端
	 * @param code
	 * @param top
	 * @return
	 */
	private static JfAccountType findType(String code, JfAccountType top){
		for(WithChildren<JfAccountType> o:top.getSubList()){
			if (o.getMe().getCode().equals(code))
				return o.getMe();
			else {
				JfAccountType findType = findType(code, o.getMe());
				if(findType!=null)
					return findType;
			}
		}
		return null;
	}

	/**
	 * 
	 * @方法功能说明：
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年3月16日上午10:50:00
	 * @version：
	 * @修改内容：
	 * @参数：@param code
	 * @参数：@return
	 * @return:JfName or null
	 * @throws
	 */
	public static JfName findJfname(String code){
	    for(JfName n:jfNames){
		if(n.code.equalsIgnoreCase(code))
		    return n;
	    }
	    return null;
	}
	
	/**
	 * @类名：SetupApplicationContext.java Created on 2015年1月19日下午2:01:49
	 * 
	 * @Copyright (c) 2012 by www.hg365.com。
	 */
	public SetupAccountContext() {
		super();
	}
	/**
	 * 生成本机构的账户类型
	 */
	public static void refreshTopAccountType() {
		BaseEntityMemDao<JfAccountTypeUK, JfAccountType> tempDao = new BaseEntityMemDao<JfAccountTypeUK, JfAccountType>(JfAccountType.class);
		topAcctType=JfAccountTypeDao.genAccountTypeTree(tempDao, sysDomain,sysDomain.type);
		 
		System.out.println(tempDao.getEntities() +" \npress key to continue..");
	}

	/**
	 * 是否是中心端（积分联盟）
	 * @return
	 */
	public static boolean isUnion(){
		return JfProperty.getProperties().containsKey(JfProperty.K_SYS_DOMAIN_CODE) 
				&& JfProperty.getProperties().get(JfProperty.K_SYS_DOMAIN_CODE).equals("hjf");
	}
}
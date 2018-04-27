/**
 * @文件名称：JfAccountTypeHome.java
 * @类路径：hgtech.jfaccount.home
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014-9-11下午1:58:33
 */
package hgtech.jfaccount.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import hg.common.page.Pagination;
import hgtech.jf.JfProperty;
import hgtech.jf.entity.dao.BaseEntityFileDao;
import hgtech.jf.entity.dao.BaseEntityPersistenceDao;
import hgtech.jf.entity.dao.EntityDao;
import hgtech.jf.tree.WithChildren;
import hgtech.jfaccount.Domain;
import hgtech.jfaccount.JfAccountType;
import hgtech.jfaccount.JfAccountTypeUK;
import hgtech.jfaccount.JfName;
import hgtech.jfaccount.SetupAccountContext;
import hgtech.jfaccount.TradeType;

/**
 * @类功能说明：积分类型的文件形式的dao
 * @类修改者：
 * @修改日期：2014-9-11下午1:58:33
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-11下午1:58:33
 *
 */
public abstract class JfAccountTypeDao extends BaseEntityPersistenceDao<JfAccountTypeUK, JfAccountType>{
	
	/**
	 * @类名：JfAccountTypeHome_Mem.java
	 * @描述：
	 * @@param entityClass
	 */
	public JfAccountTypeDao( ) {
		//super(JfAccountType.class);
		//objectPath=JfProperty.getProperties().getProperty("jfPath");		
		dupLogicKey ="相同的渠道名称和积分名称记录已存在，请重新选择";
	}

	/* (non-Javadoc)
	 * @see hgtech.entity.EntityHome_Mem#newEntity()
	 */
	@Override
	public JfAccountType newInstance() {
		return new JfAccountType();
	}
	
	/**
	 * 
	 * @方法功能说明：
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年3月23日上午11:07:30
	 * @version：
	 * @修改内容：
	 * @参数：@param dom
	 * @参数：@return 积分机构默认的积分类型, may null
	 * @return:JfAccountType
	 * @throws
	 */
	public JfAccountType getDomainDefault(Domain dom){
	    for(JfAccountType ty: getEntityList())
		if(ty.uk.domain.code.equals(dom.code))
		    return ty;
	    return null;
	}
	
	    /**
     * 
     * @方法功能说明：设置domain的可转出入属性
     * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
     * @修改时间：2015年3月23日下午3:45:14
     * @version：
     * @修改内容：
     * @参数：@param dom
     * @return:void
     * @throws
     */
    public void setDomainInOut(Domain dom) {
	for (JfAccountType ty : getEntityList())
	    if (ty.uk.domain.code.equals(dom.code)) {
		if (ty.canIn && !dom.canIn)
		    dom.canIn = true;
		if (ty.canOut && !dom.canOut)
		    dom.canOut = true;
		if (dom.canOut && dom.canOut)
		    break;
	    }
    }

    /**
     * 
     * @方法功能说明：渠道的可能积分名称
     * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
     * @修改时间：2015年3月23日下午5:00:42
     * @version：
     * @修改内容：
     * @参数：@param dom
     * @参数：@return
     * @return:List
     * @throws
     */
    public List getJfNames(String dom) {
	List<JfName> list =  new ArrayList<>();
	for (JfAccountType ty : getEntityList())
	    if (ty.uk.domain.code.equals(dom)) {
		if(!list.contains(ty.uk.consumeType))
		    list.add((JfName) ty.uk.consumeType);
	    }
	return list;
    }
    
    /**
     * 
     * @方法功能说明：渠道的可能积分名称
     * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
     * @修改时间：2015年3月23日下午5:00:42
     * @version：
     * @修改内容：
     * @参数：@param dom
     * @参数：@return
     * @return:List
     * @throws
     */
    public List<JfAccountType> getJfAccounttype(String dom) {
	List<JfAccountType> list =  new ArrayList<>();
	for (JfAccountType ty : getEntityList())
	    if (ty.uk.domain.code.equals(dom)) {
		if(!list.contains(ty.uk.consumeType))
		    list.add( ty);
	    }
	return list;
    }
    
	  /**
	 * @return 
	     * @方法功能说明：
	     * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	     * @修改时间：2015年3月20日下午5:26:02
	     * @version：
	     * @修改内容：
	     * @参数：@param pagination
	     * @参数：@param qo
	     * @return:void
	     * @throws
	     */
	    public List<JfAccountType> queryPage( String  domain, String name, String jfName) {
		SetupAccountContext.acctTypeDao.refresh();
		Collection<JfAccountType> ruleTypeList = SetupAccountContext.acctTypeDao.getEntities().values();
		List<JfAccountType> list = new ArrayList<JfAccountType>();
		
		
		for (JfAccountType t : ruleTypeList){
		    boolean add=false;
		    if (!t.getCode().equals(SetupAccountContext.accTypeHjf.getCode())){
			// 过滤渠道
			if(domain!=null && domain.length()>0){
			    add = domain.equalsIgnoreCase(t.uk.domain.code);
			}else{
			    add =true;
			}
			    
			//过滤积分类型名称
			if(name !=null &&name.length()>0){
			    add = add && t.getName().indexOf(name)>-1;
			}
			
			//过滤积分名称
			if(jfName!=null && jfName.length()>0){
			    add = add && t.uk.consumeType.code.equals(jfName);
			}
		    }
		    if (add) list.add(t);
		}
		return (list);
	    }
	    
   
	/**
	 * 该 机构和交易类型 本身以及其各自下级组成生成账户类型（账户科目）
	 * 组合顺序：1）非节点机构，每个机构生成一个账户类型，2）节点结构对应每个交易类型生成一个账户类型
	 * @方法功能说明：
	 * @修改者名字：xinglj
	 * @修改时间：2014-9-14上午9:27:47
	 * @修改内容：
	 * @参数：@param domain
	 * @参数：@param consume
	 * @return:void
	 * @throws
	 */
	public static JfAccountType genAccountTypeTree(EntityDao<JfAccountTypeUK, JfAccountType> acctTypedao,Domain domain,TradeType trade){
		JfAccountType accttype = acctTypedao.getOrNew(new JfAccountTypeUK( domain, trade));
		if(accttype.uk.getkey().equals(SetupAccountContext.accTypeHjf.uk.getkey()))
			accttype = SetupAccountContext.accTypeHjf;
		if(accttype.getName()==null )
			accttype.setName(/*domain.getName()+*/trade.getName());
//		acctTypedao.flush();//没有flush就不会保存的
		
		if(domain.getSubList().size()==0){
			//为具体机构生成各种消费类型的账户类型
			for(WithChildren<TradeType> con:trade.getSubList() ){
				JfAccountType tradeAcctType =genAccountTypeTree(acctTypedao,(Domain)domain, (TradeType)con);
				accttype.getSubList().add(tradeAcctType);
				tradeAcctType.upper=accttype;
				
			}
		}else{
			//为每个子机构生成一个对于的账户类型
			for(WithChildren<Domain> dom:domain.getSubList()){
				JfAccountType domainAcctType = genAccountTypeTree(acctTypedao,(Domain)dom,((Domain)dom).type);
				accttype.getSubList().add(domainAcctType);
				domainAcctType.upper=accttype;
			}			
		}
		return accttype;
	}

}

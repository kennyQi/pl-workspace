/**
 * @文件名称：JfSystemServiceImpl.java
 * @类路径：hgtech.jfadmin.service.imp
 * @描述：TODO
 * @作者：pengel
 * @时间：2014年10月28日上午9:55:50
 */
package hgtech.jfadmin.service.imp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;









import hgtech.jf.piaol.SetupSpiApplicationContext;
import hgtech.jf.tree.TreeUtil;
import hgtech.jf.tree.WithChildren;
import hgtech.jfaccount.Domain;
import hgtech.jfaccount.JfAccountType;
import hgtech.jfaccount.SetupAccountContext;
import hgtech.jfaccount.TradeType;
import hgtech.jfadmin.service.JfSystemService;

/**
 * @类功能说明：系统管理Service实现类
 * @类修改者：
 * @修改日期：2014年10月28日上午9:55:50
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：pengel
 * @创建时间：2014年10月28日上午9:55:50
 *
 */
@Service("jfSystemService")
public class JfSystemServiceImpl implements JfSystemService {

	/* (non-Javadoc)
	 * @see hgtech.jfadmin.service.JfSystemService#getSystemList()
	 */
	/**
	 * 机构查看实现
	 */
	@Override
	public List<Domain> getSystemList() {
		
        SetupSpiApplicationContext .init();
		//System.out.println("domain:\n"+TreeUtil.toTreeString(SetupApplicationContextDemo票量网.domain汇购, 0)); 
		WithChildren<Domain> dom=SetupAccountContext.sysDomain.getMe();
        List<Domain> acclist=new ArrayList<Domain>();
        acclist=getTree(acclist, dom);
        
        return acclist;
	}

	/**
	 * 交易类型查看实现
	 */
	@Override
	public List<TradeType> getTradeList() {
		
		
		SetupSpiApplicationContext .init();
		List<TradeType> tradeList=new ArrayList<TradeType>();
		WithChildren<TradeType> w=SetupAccountContext.jfNameTop.getMe();
		TradeType trade=w.getMe();
		tradeList=getTree(tradeList, trade);
		
		return tradeList;
	}
	

/*
    public List<TradeType> getTrade(List<TradeType> tradeList, TradeType trade){
    	tradeList.add(trade);
    	List<WithChildren<TradeType>> list=trade.getSubList();
    	if(list.size()!=0){
    		for(WithChildren<TradeType> t:list){
    			trade=t.getMe();
    			tradeList=getTrade(tradeList,trade);
    		}
    	}
    	
    	return tradeList;
    }*/

    private <T> List<T> getTree(List<T> list, WithChildren<T> t){
    	list.add(t.getMe());
    	//WithChildren<T> w=(WithChildren<T>) t;
    	List<WithChildren<T>> l=t.getSubList();
    	if(l.size()!=0){
    		for(WithChildren<T> wi:l){
    			list=getTree(list, wi);
    		}
    	}
    	
    	return list;
    }
    /**
     * 账户类型查看实现
     */
	@Override
	public List<JfAccountType> getAccountList() {
		
		SetupSpiApplicationContext .init();
		//System.out.println("domain:\n"+TreeUtil.toTreeString(SetupApplicationContextDemo票量网.domain汇购, 0)); 
		WithChildren<JfAccountType> ac=SetupAccountContext.topAcctType.getMe();
        List<JfAccountType> acclist=new ArrayList<JfAccountType>();
        acclist=getTree(acclist, ac);
		
		return acclist;
	}


}

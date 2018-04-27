package hg.fx.spi;

import hg.framework.common.base.BaseServiceProviderInterface;
import hg.framework.common.model.Pagination;
import hg.fx.domain.fixedprice.FixedPriceSet;
import hg.fx.spi.qo.FixedPriceSetSQO;

public interface FixedPriceSetSPI extends BaseServiceProviderInterface{
	
	/**
	 * 
	 * @方法功能说明：审核列表查询
	 * @修改者名字：cangs
	 * @修改时间：2016年7月26日上午11:22:38
	 * @修改内容：
	 * @参数：@return
	 * @return:Pagination<FixedPriceSet>
	 * @throws
	 */
	Pagination<FixedPriceSet> queryPagination(FixedPriceSetSQO sqo);
	
	/**
	 * 
	 * @方法功能说明：审核
	 * @修改者名字：cangs
	 * @修改时间：2016年7月26日上午11:24:11
	 * @修改内容：
	 * @参数：@param fixedPriceSetID
	 * @参数：@param result
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	boolean shenhe(String fixedPriceSetID,String checkUserID,boolean result);
	/**
	 * 
	 * @方法功能说明：列表页分页查找
	 * @修改者名字：cangs
	 * @修改时间：2016年7月25日下午2:26:03
	 * @修改内容：
	 * @参数：@param sqo
	 * @参数：@return
	 * @return:Pagination<FixedPriceSet>
	 * @throws
	 */
	Pagination<FixedPriceSet> queryPage(FixedPriceSetSQO sqo);
	
	/**
	 * 
	 * @方法功能说明：保存
	 * @修改者名字：cangs
	 * @修改时间：2016年7月25日下午8:34:42
	 * @修改内容：
	 * @参数：@param fixedPriceSet
	 * @return:void
	 * @throws
	 */
	void saveFixedPriceSet(FixedPriceSet fixedPriceSet,String authUserID,String datetype);
	
	/**
	 * 
	 * @方法功能说明：
	 * @修改者名字：cangs
	 * @修改时间：2016年7月26日上午9:32:20
	 * @修改内容：
	 * @参数：@param fixedPriceSetID
	 * @参数：@return
	 * @return:FixedPriceSet
	 * @throws
	 */
	FixedPriceSet queryByID(String fixedPriceSetID);
	
	
	void setFixedPriceIntervalID(String fixedPriceIntervalID,int theNextMonth);
}

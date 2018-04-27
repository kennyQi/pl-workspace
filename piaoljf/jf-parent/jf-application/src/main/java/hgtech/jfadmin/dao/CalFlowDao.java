/**
 * @文件名称：CalFlowDao.java
 * @类路径：hgtech.jfadmin.dao
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年10月30日上午11:26:21
 */
package hgtech.jfadmin.dao;

import hg.common.page.Pagination;
import hgtech.jfadmin.dto.CalLogDto;
import hgtech.jfadmin.hibernate.CalFlowHiberEntity;
import hgtech.jfadmin.hibernate.RuleHiberEntity;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @类功能说明：计算流水dao
 * @类修改者：
 * @修改日期：2014年10月30日上午11:26:21
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月30日上午11:26:21
 *
 */
public interface CalFlowDao {
	/**
	 * @方法功能说明：
	 * @修改者名字：xinglj
	 * @修改时间：2014年10月22日上午9:42:54
	 * @修改内容：
	 * @参数：@param pagination
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	Pagination findPagination(Pagination pagination);
	Pagination findPaginationActivityStat(Pagination pagination);
	/**
	 * @方法功能说明：
	 * @修改者名字：<a href=pengel@hgtech365.com>pengel</a>
	 * @修改时间：2014年12月22日上午9:27:50
	 * @version：
	 * @修改内容：
	 * @参数：@param l
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public void save (Collection<CalFlowHiberEntity> l);
	
	public void delete(CalFlowHiberEntity ruleEntity) ;
	
	public void update(CalFlowHiberEntity ruleEntity);
	
	public CalFlowHiberEntity get(String cal_id);
	/**
	 * 
	 * @方法功能说明：新开数据库连接保存
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年1月28日下午3:28:13
	 * @version：
	 * @修改内容：
	 * @参数：@param l
	 * @return:void
	 * @throws
	 */
	public abstract void save2(Collection<CalFlowHiberEntity> l);
	/**
	 * @方法功能说明：交易日志
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年1月28日下午4:15:38
	 * @version：
	 * @修改内容：
	 * @参数：@param pagination
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	public Pagination findTradeFlowPagination(Pagination pagination);
	
	
	public abstract List<CalFlowHiberEntity> findList(CalLogDto dto);
	
	public List<CalFlowHiberEntity> findChoujiangList(CalLogDto dto);
	
}

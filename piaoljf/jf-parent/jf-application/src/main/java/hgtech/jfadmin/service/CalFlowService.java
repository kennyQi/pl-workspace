/**
 * @文件名称：RuleService.java
 * @类路径：hgtech.jfadmin.service
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年10月22日上午9:27:32
 */
package hgtech.jfadmin.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hg.common.page.Pagination;
import hgtech.jfadmin.dto.CalLogDto;

/**
 * @类功能说明：积分流水servi
 * @类修改者：
 * @修改日期：2014年10月31日 
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xy
 * @创建时间：2014年10月31日 
 *
 */
public interface CalFlowService {
	/** 获取分页 */
	public abstract Pagination findPagination(Pagination pagination);

	/**
	 * @方法功能说明：交易日志的分页
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年1月28日下午4:16:57
	 * @version：
	 * @修改内容：
	 * @参数：@param pagination
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	Pagination findTradeFlowPagination(Pagination pagination);
	Pagination findPaginationActivityStat(Pagination pagination);

	HSSFWorkbook export(CalLogDto dto);

}

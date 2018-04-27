/**
 * @文件名称：AccountService.java
 * @类路径：hgtech.jfadmin.service
 * @描述：TODO
 * @作者：pengel
 * @时间：2014年11月4日上午10:14:56
 */
package hgtech.jfadmin.service;

import java.util.List;

import hg.common.page.Pagination;
import hgtech.jfaccount.JfAccount;

/**
 * @类功能说明：账户查询service、
 * 
 * @类修改者：
 * @修改日期：2014年11月4日上午10:14:56
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：pengel
 * @创建时间：2014年11月4日上午10:14:56
 *
 */
public interface AccountQueryService {
	/**
	 * @方法功能说明：账户分页查询
	 * @修改者名字：pengel
	 * @修改时间：2014年11月7日下午2:37:35
	 * @修改内容：
	 * @参数：@param pagination
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	Pagination findPagination(Pagination pagination);
	/**
	 * @方法功能说明：用户积分查询
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年2月5日下午1:31:50
	 * @version：
	 * @修改内容：
	 * @参数：@param userCode
	 * @参数：@return
	 * @return:List<JfAccount>
	 * @throws
	 */
	List<JfAccount> querybyCode(String userCode);
	/**
	 * 
	 * @param userCode
	 * @param acctType
	 * @return not null,
	 */
	JfAccount querybyCode(String userCode, String acctType);
	

}

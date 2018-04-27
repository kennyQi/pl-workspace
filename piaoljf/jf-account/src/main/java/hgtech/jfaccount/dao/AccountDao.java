/**
 * @文件名称：AccountDao.java
 * @类路径：hgtech.jfadmin.dao
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年10月31日下午3:34:25
 */
package hgtech.jfaccount.dao;

import java.util.List;

import hg.common.page.Pagination;
import hgtech.jf.entity.dao.EntityDao;
import hgtech.jfaccount.JfAccount;
import hgtech.jfaccount.JfAccountType;
import hgtech.jfaccount.JfAccountUK;

/**
 * @类功能说明：积分账户dao
 * @类修改者：
 * @修改日期：2014年10月31日下午3:34:25
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月31日下午3:34:25
 *
 */
public interface AccountDao extends EntityDao<JfAccountUK, JfAccount>{
	
	/**
	 * @方法功能说明：积分分页查询
	 * @修改者名字：pengel
	 * @修改时间：2014年11月7日下午2:34:39
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
	
	public boolean hasAny(JfAccountType type);

	JfAccount querybyCode(String userCode, String acctType);

}

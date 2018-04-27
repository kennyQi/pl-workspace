/**
 * @文件名称：ISmallDataDao.java
 * @类路径：hgtech.jfaddmin.dao.imp
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年10月13日下午5:01:58
 */
package hgtech.jfadmin.dao.imp;

import hg.common.page.Pagination;

/**
 * @类功能说明：数据dao，小数据量的表
 * @类修改者：
 * @修改日期：2014年10月13日下午5:01:58
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月13日下午5:01:58
 *
 */
public interface ISmallDataDao {

	/**
	 * 
	 * @方法功能说明：得到一个分页数据
	 * @修改者名字：xinglj
	 * @修改时间：2014年11月4日下午1:47:49
	 * @修改内容：
	 * @参数：@param pageNo 页号
	 * @参数：@param pageSize 一页大小
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	public abstract Pagination findAllwithPage(int pageNo, int pageSize);

}
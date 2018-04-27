/**
 * @文件名称：UseDao.java
 * @类路径：hgtech.jfaccount.dao
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年11月26日下午4:42:22
 */
package hgtech.jfaccount.dao;

import java.util.List;

import hgtech.jfaccount.JfUseDetail;

/**
 * @类功能说明：积分使用dao
 * @类修改者：
 * @修改日期：2014年11月26日下午4:42:22
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年11月26日下午4:42:22
 *
 */
public interface UseDao {

	/**
	 * @方法功能说明：save db
	 * @修改者名字：xinglj
	 * @修改时间：2014年11月26日下午4:54:43
	 * @修改内容：
	 * @参数：@param useDetail
	 * @return:void
	 * @throws
	 */
	void save(JfUseDetail useDetail);

	/**
	 * @方法功能说明：返回某次消耗明细的积分扣减明细
	 * @修改者名字：xinglj
	 * @修改时间：2014年11月27日下午4:23:48
	 * @修改内容：
	 * @参数：@param 消耗明细id
	 * @参数：@return
	 * @return:List<JfUseDetail>
	 * @throws
	 */
	List<JfUseDetail> getUseList(String useFlowId);

}

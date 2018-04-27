/**
 * @文件名称：Entity.java
 * @类路径：hgtech.jfaccount
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014-9-16上午9:06:51
 */
package hgtech.jf.entity;

import java.io.Serializable;

/**
 * @类功能说明：带主键类型的实体
 * @类修改者：
 * @修改日期：2014-9-16上午9:06:51
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-16上午9:06:51
 *
 */
public interface Entity<UKType> extends Serializable{
	/**
	 * set/get容易被认为是bean属性，改为read/put
	 * @方法功能说明：
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年1月8日下午4:49:33
	 * @version：
	 * @修改内容：
	 * @参数：@return
	 * @return:UKType
	 * @throws
	 */
	public   UKType readUK();
	public void putUK(UKType uk);
	/**
	 * @return 
	 * 
	 * @方法功能说明：同步逻辑主键部分的临时变量和持久化变量
	 * @修改者名字：xinglj
	 * @修改时间：2014年11月27日下午3:44:55
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public void syncUK();
//	public Entity<UKType> newInstance();
}
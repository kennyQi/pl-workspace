/**
 * @文件名称：EntityHome.java
 * @类路径：hgtech.jf.entity.home
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014-9-24下午3:47:24
 */
package hgtech.jf.entity.dao;

import hgtech.jf.entity.Entity;
import hgtech.jf.entity.EntityUK;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2014-9-24下午3:47:24
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-24下午3:47:24
 *
 * @param <EnUk>
 * @param <En>
 */
public interface EntityDao<EnUk extends EntityUK, En extends Entity<EnUk>> {

	/**
	 * 获取所有实体，慎用！
	 * @方法功能说明：
	 * @修改者名字：xinglj
	 * @修改时间：2014年12月1日下午3:25:17
	 * @修改内容：
	 * @参数：@return
	 * @return:HashMap<Serializable,En>
	 * @throws
	 */
	public abstract HashMap<Serializable, En> getEntities();

	/**
	 * @param parameterObject 按逻辑主键获取对象
	 * 
	 * @方法功能说明：
	 * @修改者名字：xinglj
	 * @修改时间：2014-9-11下午2:00:55
	 * @修改内容：
	 * @参数：@param domain
	 * @参数：@param consume
	 * @参数：@return
	 * @return:Entity or null
	 * @throws
	 */
	public abstract En get(EnUk uk);
	/**
	 * 
	 * @方法功能说明：按形式主键获取对象，如sequence号
	 * @修改者名字：xinglj
	 * @修改时间：2014年11月26日下午2:56:07
	 * @修改内容：
	 * @参数：@param id
	 * @参数：@return
	 * @return:En
	 * @throws
	 */
	public abstract En get(Serializable getkey);	
	
	/**
	 * 按逻辑主键新造一个实体
	 * @方法功能说明：
	 * @修改者名字：xinglj
	 * @修改时间：2014年12月1日下午2:10:06
	 * @修改内容：
	 * @参数：@param uk
	 * @参数：@return
	 * @return:En
	 * @throws
	 */
	public abstract En newEntity(EnUk uk);
	/**
	 * 
	 * @方法功能说明：按逻辑主键获取一个实体，如果不存在新造一个
	 * @修改者名字：xinglj
	 * @修改时间：2014年12月1日下午3:25:52
	 * @修改内容：
	 * @参数：@param uk
	 * @参数：@return
	 * @return:En
	 * @throws
	 */
	public abstract En getOrNew(EnUk uk);
	
	/**
	 * 
	 * @方法功能说明：insert or update
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年3月6日下午2:45:26
	 * @version：
	 * @修改内容：
	 * @参数：@param en
	 * @return:void
	 * @throws
	 */
	public void saveEntity(En en);
	/**
	 * 
	 * @方法功能说明：写硬盘
	 * @修改者名字：xinglj
	 * @修改时间：2014年10月15日下午4:56:43
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public void flush();
	/**
	 * 从硬盘读，刷新
	 * @方法功能说明：
	 * @修改者名字：xinglj
	 * @修改时间：2014年10月15日下午4:57:02
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public void refresh();

	/**
	 * @方法功能说明：按逻辑主键删除对象
	 * @修改者名字：xinglj
	 * @修改时间：2014年10月27日下午4:57:14
	 * @修改内容：
	 * @参数：@param uk
	 * @return:void
	 * @throws
	 */
	void delete(EnUk uk);

	/**
	 * @方法功能说明：
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年3月6日下午2:44:48
	 * @version：
	 * @修改内容：
	 * @参数：@param en
	 * @return:void
	 * @throws
	 */
	void insertEntity(En en);

	/**
	 * @方法功能说明：
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年3月6日下午2:44:58
	 * @version：
	 * @修改内容：
	 * @参数：@param en
	 * @return:void
	 * @throws
	 */
	void updateEntity(En en);

	



}
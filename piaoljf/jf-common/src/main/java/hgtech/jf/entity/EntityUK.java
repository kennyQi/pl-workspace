/**
 * @文件名称：EntityUK.java
 * @类路径：hgtech.entity
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014-9-16上午10:55:05
 */
package hgtech.jf.entity;

import java.io.Serializable;

/**
 * @类功能说明：主键类型
 * @类修改者：
 * @修改日期：2014-9-16上午10:55:05
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-16上午10:55:05
 *
 */
public interface EntityUK extends Serializable {
	/**
	 * 应该为基本类型,java.lang.*,String,Intger等
	 * @方法功能说明：
	 * @修改者名字：xinglj
	 * @修改时间：2014-9-16上午10:56:36
	 * @修改内容：
	 * @参数：@return
	 * @return:Serializable
	 * @throws
	 */
	public Serializable getkey();
}

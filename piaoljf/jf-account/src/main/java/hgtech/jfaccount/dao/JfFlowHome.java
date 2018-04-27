/**
 * @文件名称：JfFlowHome.java
 * @类路径：hgtech.jfaccount
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014-9-24上午11:28:31
 */
package hgtech.jfaccount.dao;

import hgtech.jf.entity.StringUK;
import hgtech.jf.entity.dao.BaseEntityFileDao;
import hgtech.jfaccount.JfFlow;

/**
 * @类功能说明：FlowDao的内存实现
 * @类修改者：
 * @修改日期：2014-9-24上午11:28:31
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-24上午11:28:31
 *
 */
public class JfFlowHome extends BaseEntityFileDao<StringUK, JfFlow> {

	/**
	 * @类名：JfFlowHome.java
	 * @描述： 
	 * @@param entityClass
	 */
	public JfFlowHome( ) {
		super(JfFlow.class);
	}

	/* (non-Javadoc)
	 * @see hgtech.jf.entity.home.BaseEntityHome_Mem#newInstance()
	 */
	@Override
	public JfFlow newInstance() {
		return new JfFlow();
	}

}

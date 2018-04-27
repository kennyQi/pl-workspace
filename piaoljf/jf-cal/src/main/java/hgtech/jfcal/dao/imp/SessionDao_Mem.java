/**
 * @文件名称：SimpleSessionDao.java
 * @类路径：hgtech.jfcal.dao
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年10月24日下午12:35:15
 */
package hgtech.jfcal.dao.imp;

import hgtech.jfcal.dao.RuledSession;
import hgtech.jfcal.dao.SessionDao;

/**
 * @类功能说明：内存方式的 dao
 * @类修改者：
 * @修改日期：2014年10月24日下午12:35:15
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月24日下午12:35:15
 *
 */
public class SessionDao_Mem implements SessionDao{

	/* (non-Javadoc)
	 * @see hgtech.jfcal.dao.SessionDao#getRuleSession(java.lang.String)
	 */
	@Override
	public RuledSession getRuleSession(String rule) {
		return new RuledSession_Mem(rule);
	}

}

/**
 * @文件名称：SessionDao.java
 * @类路径：hgtech.jfcal.dao
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年10月24日上午11:33:43
 */
package hgtech.jfcal.dao;

import hgtech.jfcal.model.Session;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2014年10月24日上午11:33:43
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月24日上午11:33:43
 *
 */
public interface SessionDao {
	public RuledSession getRuleSession(String rule );
}

/**
 * @文件名称：SessionAction_Mem.java
 * @类路径：hgtech.jfcal.home
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014-9-22下午2:19:29
 */
package hgtech.jfcal.dao.imp;

import java.util.HashMap;
import java.util.Map;

import hgtech.jf.entity.StringUK;
import hgtech.jf.entity.dao.BaseEntityFileDao;
import hgtech.jfcal.dao.RuledSession;
import hgtech.jfcal.model.DataRow;
import hgtech.jfcal.model.Rule;
import hgtech.jfcal.model.Session;

/**
 * @类功能说明：内存方式的 dao
 * @类修改者：
 * @修改日期：2014-9-22下午2:19:29
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-22下午2:19:29
 *
 */
public class RuledSession_Mem  extends BaseEntityFileDao<StringUK, Session> implements RuledSession{
	/**
	 * @类名：SessionHome_Mem.java
	 * @@param rule2
	 */
	public RuledSession_Mem(String rule2) {
		super(Rule.class);
		rule=rule2;
	}

	Map<String,DataRow> map=new HashMap();
	public String rule;

	/* (non-Javadoc)
	 * @see hgtech.jfcal.model.SessionAction#init()
	 */
	public void init() {
		
	}

	/* (non-Javadoc)
	 * @see hgtech.jfcal.model.SessionAction#clear()
	 */
	@Override
	public void clear() {
		map.clear();		
	}



	/* (non-Javadoc)
	 * @see hgtech.jfcal.model.SessionAction#getRuleCode()
	 */
	@Override
	public String getRuleCode() {
		return rule;
	}

	/* (non-Javadoc)
	 * @see hgtech.jfcal.dao.RuledSession#get(java.lang.String)
	 */
	@Override
	public DataRow get(String user) {
		return map.get(user);
	}

	/* (non-Javadoc)
	 * @see hgtech.jfcal.dao.RuledSession#put(java.lang.String, hgtech.jfcal.model.Session)
	 */
	@Override
	public void put(String user, DataRow row) {
		map.put(user, row);
	}

}

package hgtech.jfcal.dao;

import hgtech.jf.entity.StringUK;
import hgtech.jf.entity.dao.EntityDao;
import hgtech.jfcal.model.DataRow;
import hgtech.jfcal.model.Session;

/**
 * @类功能说明：一个规则的session单元。
 * @类修改者：
 * @修改日期：2014-9-22下午2:40:13
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-22下午2:40:13
 *
 */
public interface RuledSession /*extends EntityDao<StringUK, Session>*/{
	public String getRuleCode();
//	public void init();
	public void clear();
	/**
	 * 
	 * @方法功能说明：
	 * @修改者名字：xinglj
	 * @修改时间：2014-9-22下午2:53:20
	 * @修改内容：
	 * @参数：@param user
	 * @参数：@return
	 * @return:DataRow, null if not exists
	 * @throws 
	 */
	public DataRow get(String user);
	public void put(String user,DataRow row);
}

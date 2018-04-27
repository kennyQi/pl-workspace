/**
 * @文件名称：JfAccountHome.java
 * @类路径：hgtech.jfaccount.home
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014-9-11下午1:58:33
 */
package hgtech.jfaccount.dao;

import java.util.HashMap;

import hgtech.jf.entity.Entity;
import hgtech.jf.entity.dao.BaseEntityFileDao;
import hgtech.jfaccount.JfAccountUK;
import hgtech.jfaccount.TradeType;
import hgtech.jfaccount.Domain;
import hgtech.jfaccount.JfAccount;
import hgtech.jfaccount.JfAccountType;
import hgtech.jfaccount.JfUser;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2014-9-11下午1:58:33
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-11下午1:58:33
 *
 */
public class JfAccountHome_Mem extends BaseEntityFileDao<JfAccountUK, JfAccount>{

	/**
	 * @类名：JfAccountHome_Mem.java
	 * @描述：TODO
	 * @@param entityClass
	 */
	public JfAccountHome_Mem() {
		super(JfAccount.class);
	}

	/* (non-Javadoc)
	 * @see hgtech.entity.EntityHome_Mem#newEntity()
	 */
	@Override
	public JfAccount newInstance() {
		return new JfAccount();
	}
	
	
}

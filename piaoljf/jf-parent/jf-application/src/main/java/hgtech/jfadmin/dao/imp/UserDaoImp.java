/**
 * @文件名称：UserDaoImp.java
 * @类路径：hgtech.jfadmin.dao.imp
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年10月31日上午10:14:08
 */
package hgtech.jfadmin.dao.imp;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import hgtech.jf.entity.Entity;
import hgtech.jf.entity.StringUK;
import hgtech.jf.entity.dao.EntityDao;
import hgtech.jfaccount.JfUser;
import hgtech.jfadmin.dao.UserDao;

/**
 * @类功能说明：积分用户 dao
 * @类修改者：
 * @修改日期：2014年10月31日上午10:14:08
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月31日上午10:14:08
 *
 */
@SuppressWarnings("rawtypes")
@Repository("jfuserDao")
public class UserDaoImp extends BaseEntityHiberDao<StringUK, JfUser> implements UserDao{
	public UserDaoImp(){
		super(JfUser.class);
	}

	@Override
	public HashMap<Serializable, JfUser> getEntities() {
		throw new RuntimeException("大表不可使用getEntities！");
	}
 

	/* (non-Javadoc)
	 * @see hgtech.jfadmin.dao.imp.BaseEntityHiberDao#beforeSaveEntity(hgtech.jf.entity.Entity)
	 */
	@Override
	protected void beforeSaveEntity(JfUser en) {
		
	}



	/* (non-Javadoc)
	 * @see hgtech.jfadmin.dao.imp.BaseEntityHiberDao#afterHiberGet(hgtech.jf.entity.Entity)
	 */
	protected void afterHiberGet(JfUser object) {
		
	}

	/**
	 * 获取单个用户等级
	 * @param name
	 * @return
	 */
	public JfUser getUserLevel(String name) {
		String hql = "from JfUser o where o.code = ?";
		JfUser user = (JfUser) getSession().createQuery(hql).setString(0, name).uniqueResult();
		return user;
	}

	/**
	 * 获取所有用户等级
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<JfUser> queryAllUserLevel() {
		long now = System.currentTimeMillis();
		long yesterday = now - 24 * 60 * 60 *1000;
		String hql = "from JfUser where gradeUpdateDate > ? and gradeUpdateDate < ?";
		return getSession().createQuery(hql).setLong(0, yesterday).setLong(1, now).list();
	}

}

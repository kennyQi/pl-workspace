/**
 * @文件名称：EntityHiberDao.java
 * @类路径：hgtech.jfadmin.dao.imp
 * @描述：hibernate 实现的 EntityDao
 * @作者：xinglj
 * @时间：2014年10月31日上午9:18:32
 */
package hgtech.jfadmin.dao.imp;

import hg.common.component.hibernate.HibernateSimpleDao;
import hgtech.jf.entity.Entity;
import hgtech.jf.entity.EntityUK;
import hgtech.jf.entity.dao.EntityDao;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @类功能说明：hibernate 实现的 EntityDao
 * @类修改者：适用于逻辑主键(EntityUk)是单字段的情况.对应多字段请覆盖getHiberObject（UK）方法
 * @修改日期：2014年10月31日上午9:18:32
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月31日上午9:18:32
 *
 */
public abstract class BaseEntityHiberDao <EnUk extends EntityUK,En extends Entity<EnUk> >  extends HibernateSimpleDao implements EntityDao<EnUk, En> {
	//使用hibernate映射的类
	public Class hiberClass;
	//汇购的Entity类
	public Class entityClass;
	
	/**
	 * 
	 * @类名：BaseEntityHiberDao.java
	 * @描述：
	 * @@param clazz,必须同时是hgtech.jf.Entity ,又有hibernate标记
	 */
	public BaseEntityHiberDao(Class clazz){
		hiberClass=clazz;
		entityClass=clazz;
	}
	
	/* (non-Javadoc)
	 * @see hgtech.jf.entity.dao.EntityDao#getEntities()
	 */
	@Override
	public HashMap<Serializable, En> getEntities() {
		throw new RuntimeException("not yet!");
	}

	/* (non-Javadoc)
	 * @see hgtech.jf.entity.dao.EntityDao#get(hgtech.jf.entity.EntityUK)
	 */
	@Override
	public En get(EnUk uk) {
		En object = getHiberObject(uk);
		if(object!=null){
			object.putUK(uk);
//			afterHiberGet(object);
			object.syncUK();
		}
		En o = object;
		return  o;//(En) convertHiber2Entity(o);
	}

	/**
	 * @方法功能说明：
	 * @修改者名字：xinglj
	 * @修改时间：2014年11月1日下午1:38:28
	 * @修改内容：
	 * @参数：@param uk
	 * @参数：@return
	 * @return:En
	 * @throws
	 */
	protected En getHiberObject(EnUk uk) {
	
		@SuppressWarnings("unchecked")
		En object = ((En) getSession().get(getHiberClass(), uk));
		return object;
	}

	@Override
	public En get(Serializable id){
		@SuppressWarnings("unchecked")
		En object = (En) getSession().get(hiberClass, id);
		object.syncUK();
		return object;
		
	}
	
	/**
	 * @方法功能说明：从数据库读出实体后,子类可同步成员变量（如临时和持久化变量）
	 * @修改者名字：xinglj
	 * @修改时间：2014年11月1日下午1:06:25
	 * @修改内容：
	 * @参数：@param object
	 * @return:void
	 * @throws
	 */
//	protected abstract void afterHiberGet(En object) ;

	/**
	 * @方法功能说明：在实体保存之前，子类可以在这里设置主键等
	 * @修改者名字：xinglj
	 * @修改时间：2014年10月31日下午4:51:40
	 * @修改内容：
	 * @参数：@param hiberOjb
	 * @return:void
	 * @throws
	 */
	protected abstract void beforeSaveEntity( En en) ;

	/* (non-Javadoc)
	 * @see hgtech.jf.entity.dao.EntityDao#newEntity(hgtech.jf.entity.EntityUK)
	 */
	@Override
	public En newEntity(EnUk uk) {
		En acc=newInstance();
		acc.putUK(uk);
		return acc;
	}

	/* (non-Javadoc)
	 * @see hgtech.jf.entity.dao.EntityDao#getOrNew(hgtech.jf.entity.EntityUK)
	 */
	@Override
	public En getOrNew(EnUk uk) {
		En en=get(uk);
		if(en==null)
			return newEntity(uk);
		else
			return en;
	}

	/* (non-Javadoc)
	 * @see hgtech.jf.entity.dao.EntityDao#saveEntity(hgtech.jf.entity.Entity)
	 */
	@Override
	public void saveEntity(En en) {
//		Object hiberOjb =en;// convertEntity2Hiber(en);
		beforeSaveEntity( en /*,hiberOjb*/);
		en.syncUK();
		
		getSession().saveOrUpdate( en);// 
		
	}

	@Override
	public void insertEntity(En en) {
//		Object hiberOjb =en;// convertEntity2Hiber(en);
		beforeSaveEntity( en /*,hiberOjb*/);
		en.syncUK();
		
		getSession().save( en); 
		
	}	
	@Override
	public void updateEntity(En en) {
//		Object hiberOjb =en;// convertEntity2Hiber(en);
		beforeSaveEntity( en /*,hiberOjb*/);
		en.syncUK();
		
		getSession().update( en);//
		
	}


	/* (non-Javadoc)
	 * @see hgtech.jf.entity.dao.EntityDao#flush()
	 */
	@Override
	public void flush() {
		getSession().flush();
		
	}

	/* (non-Javadoc)
	 * @see hgtech.jf.entity.dao.EntityDao#refresh()
	 */
	@Override
	public void refresh() {
		throw new RuntimeException("not supportted!");
		
	}

	/* (non-Javadoc)
	 * @see hgtech.jf.entity.dao.EntityDao#delete(hgtech.jf.entity.EntityUK)
	 */
	@Override
	public void delete(EnUk uk) {
		getSession().delete( get(uk));
	}

	/**
	 * @return the clazz
	 */
	public Class getHiberClass() {
		return hiberClass;
	}

	/**
	 * @param clazz the clazz to set
	 */
	public void setHiberClass(Class clazz) {
		this.hiberClass = clazz;
	}
	
	public   En newInstance(){
		try {
			return (En) (entityClass.newInstance());
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

 
	
}

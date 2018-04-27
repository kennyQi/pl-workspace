package hgtech.jf.entity.dao;

import hg.common.page.Pagination;
import hgtech.jf.entity.Entity;
import hgtech.jf.entity.EntityUK;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 小数据使用对象序列化存储的dao.注意：数据是threadloca
 * @author xinglj
 *
 * @param <EnUk>
 * @param <En>
 */
public abstract class BaseEntityPersistenceDao<EnUk extends EntityUK, En extends Entity<EnUk>> implements EntityDao<EnUk, En> {
	Log log =LogFactory.getLog(BaseEntityPersistenceDao.class);
	/**
	 * @FieldsTIPDUPKEY:TODO
	 */
	public static final String TIPDUPKEY = "重复的逻辑主键：";
	/**
	 * 重复主键的提示信息
	 */
	public String dupLogicKey;
	/**
	 * threadlocall的map
	 */
	ThreadLocal<HashMap<Serializable, En>> tlocal = new ThreadLocal<HashMap<Serializable, En>>(){
		    protected java.util.HashMap<Serializable,En> initialValue() {
			return new LinkedHashMap< Serializable, En>();
		    };
		    
		};
	ThreadLocal<Boolean> tlocalRefreshed = new ThreadLocal<Boolean>(){
		protected Boolean initialValue() {return false;};
	};	
	protected Class entityClass;

	public static Pagination getPage(Pagination page, List  collection) {
	    int totalpage = collection.size()%page.getPageSize()==0? collection.size()/page.getPageSize():collection.size()/page.getPageSize()+1;
	    int from = (page.getPageNo()-1)* page.getPageSize();
	    
	    int to = page.getPageNo() * page.getPageSize();
	    if(to>collection.size())
		to = collection.size();
	    
	    List  list = new ArrayList<>();
	    for(int i=from;i<to;i++) {
		list.add( collection.get(i));
	    }
	    page.setList(list);
	    page.setTotalCount(collection.size());
	    return page;
	}

	/**
	 * 所有读取都会经过这里，自动刷新
	 */
	@Override
	public HashMap<Serializable, En> getEntities() {
			//从数据库中更新到threadlocall
			if(!tlocalRefreshed.get()){
				refresh();
				
			}
		    HashMap<Serializable, En> map = tlocal.get();
		    /*log.warn("map instance id:"+tlocal);
		    log.warn("row size:"+map.size());
		    log.warn("keySet:"+map.keySet());*/
			return map;
		}

	public List<En> getEntityList() {
	    List l= new ArrayList<>();
	    l.addAll(getEntities().values());
	    return l;
	}

	@Override
	public En get(EnUk uk) {
		Serializable getkey = uk.getkey();
		return get(getkey);
	}

	/**
	 * @方法功能说明：按主键key找找
	 * @修改者名字：xinglj
	 * @修改时间：2014年10月8日上午10:51:06
	 * @修改内容：
	 * @参数：@param getkey 
	 * @参数：@return
	 * @return:En
	 * @throws
	 */
	@Override
	public En get(Serializable getkey) {
		    //get为从内存加载，不能refresh
	//		if(map.size()==0)
	//		refresh();
			return getEntities().get(getkey);
		}

	@Override
	public void delete(EnUk uk) {
		refresh();
		Serializable getkey = uk.getkey();
		getEntities().remove(getkey);
	}

	@Override
	public En newEntity(EnUk uk) {
		En acc=newInstance();
		acc.putUK(uk);
		getEntities().put(uk.getkey() , acc);
		//System.out.println("account key:\n"+map.keySet());
		return acc;
	}

	@Override
	public void saveEntity(En en) {
		getEntities().put(en.readUK().getkey(), en);
	}

	@Override
	public void flush() {
			Serializable obj = tlocal.get();
			writeObject(obj);
	//		SaveObjectUtilJsonImp.save(getObjectFile(), map);
		}

	protected abstract void writeObject(Serializable obj) ;

	@Override
	public void refresh() {
			log.warn("refresh data...");
			tlocal.get().clear();
			Object read = readObject();
	//		Object read = SaveObjectUtilJsonImp.read(objectFile, map.getClass());
			if(read!=null)
			    tlocal.get().putAll((Map<? extends Serializable, ? extends En>) read);
			tlocalRefreshed.set(true);			
		}

	
	protected abstract  Object readObject() ;

	public En newInstance() {
		try {
			return (En) (entityClass).newInstance();
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public En getOrNew(EnUk uk) {
		En  get=get(uk);
		if(get==null)
			get=newEntity(uk);
		return get;
	}

	@Override
	public void insertEntity(En en) {
	    if(get(en.readUK())!=null)
		throw new RuntimeException( dupLogicKey==null? TIPDUPKEY+en.readUK():dupLogicKey);
	    else
		saveEntity(en);
	    
	}

	@Override
	public void updateEntity(En en) {
	    saveEntity(en);
	    
	}

	public BaseEntityPersistenceDao() {
		super();
	}

}
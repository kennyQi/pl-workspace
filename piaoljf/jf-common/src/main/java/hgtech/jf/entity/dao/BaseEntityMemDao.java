/**
 * @文件名称：EntityHome.java
 * @类路径：hgtech.Entity.home
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014-9-11下午1:58:33
 */
package hgtech.jf.entity.dao;

import hg.common.page.Pagination;
import hgtech.jf.entity.Entity;
import hgtech.jf.entity.EntityUK;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.helpers.ThreadLocalMap;
import org.springframework.beans.factory.annotation.Autowired;

import com.hazelcast.partition.client.GetPartitionsRequest;

/**
 * @类功能说明：内存中的dao. 
 * @类修改者：
 * @修改日期：2014-9-11下午1:58:33
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-11下午1:58:33
 *
 */
public  class BaseEntityMemDao<EnUk extends EntityUK,En extends Entity<EnUk> > implements EntityDao<EnUk, En> {
	/**
     * @FieldsTIPDUPKEY:TODO
     */
    public static final String TIPDUPKEY = "重复的逻辑主键：";
    	/**
    	 * 重复主键的提示信息
    	 */
    	public
    	String dupLogicKey;
	private HashMap<Serializable, En> map=new LinkedHashMap< Serializable, En>();

	private Class entityClass;
	 
	/**
	 * @类名：BaseEntityHome_Mem.java
	 * @@param entityClass
	 */
	public BaseEntityMemDao(Class entityClass) {
		super();
		this.entityClass = entityClass;
		
	}

	/* (non-Javadoc)
	 * @see hgtech.jf.entity.home.EntityHome#getEntities()
	 */
	@Override
	public HashMap<Serializable, En> getEntities(){
	    //refresh();
	    	if(map.size()==0)
	    	    refresh();
		return map;
//		return map;
	}
	
	public List<En> getEntityList(){
	    List l= new ArrayList<>();
	    l.addAll(getEntities().values());
	    return l;
	}
	
	/* (non-Javadoc)
	 * @see hgtech.jf.entity.home.EntityHome#get(EnUk)
	 */
	@Override
	public En get(EnUk uk){
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
	public void delete(EnUk uk){
		Serializable getkey = uk.getkey();
		getEntities().remove(getkey);
	}
	
	/* (non-Javadoc)
	 * @see hgtech.jf.entity.home.EntityHome#newEntity(EnUk)
	 */
	@Override
	public En newEntity(EnUk uk){
		En acc=newInstance();
		acc.putUK(uk);
		getEntities().put(uk.getkey() , acc);
		//System.out.println("account key:\n"+map.keySet());
		return acc;
	}
	@Override
	public void saveEntity(En en) {
		getEntities().put(en.readUK().getkey(), en);
	};
	
	@Override
	public void flush(){
	}


	
	@Override
	public void refresh(){
  	}

 
	
	public   En newInstance(){
		try {
			return (En) (entityClass).newInstance();
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}


	/* (non-Javadoc)
	 * @see hgtech.jf.entity.home.EntityHome#getOrNew(EnUk)
	 */
	@Override
	public En getOrNew(EnUk uk){
		En  get=get(uk);
		if(get==null)
			get=newEntity(uk);
		return get;
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
	    List collection = new ArrayList<>();
	    for(int i=0;i<98;i++)
		collection.add(i+"");
	    
	    Pagination page = new Pagination(10,10,collection.size());
	    System.out.println(BaseEntityMemDao.getPage(page, collection).getList());
	
	}

	/* (non-Javadoc)
	 * @see hgtech.jf.entity.dao.EntityDao#insertEntity(hgtech.jf.entity.Entity)
	 */
	@Override
	public void insertEntity(En en) {
	    if(get(en.readUK())!=null)
		throw new RuntimeException( dupLogicKey==null? TIPDUPKEY+en.readUK():dupLogicKey);
	    else
		saveEntity(en);
	    
	}

	/* (non-Javadoc)
	 * @see hgtech.jf.entity.dao.EntityDao#updateEntity(hgtech.jf.entity.Entity)
	 */
	@Override
	public void updateEntity(En en) {
	    saveEntity(en);
	    
	}

	public static Pagination  getPage(Pagination page, List  collection){
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
	

}

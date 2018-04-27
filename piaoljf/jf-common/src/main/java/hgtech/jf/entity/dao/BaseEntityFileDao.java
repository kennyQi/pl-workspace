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
import hgtech.jf.entity.JsonUtil;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.helpers.ThreadLocalMap;
import org.springframework.beans.factory.annotation.Autowired;

import com.hazelcast.partition.client.GetPartitionsRequest;

/**
 * @类功能说明：小数据使用对象序列化存储的dao.注意：数据是threadlocal
 * @类修改者：
 * @修改日期：2014-9-11下午1:58:33
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-11下午1:58:33
 *
 */
public  class BaseEntityFileDao<EnUk extends EntityUK,En extends Entity<EnUk> > extends BaseEntityPersistenceDao<EnUk, En>  {
	/**
	 * 对象持久化的路径
	 */
	public String objectPath;
	
	
	/**
	 * @类名：BaseEntityHome_Mem.java
	 * @@param entityClass
	 */
	public BaseEntityFileDao(Class entityClass) {
		super();
		this.entityClass = entityClass;
		
	}

	/**
	 * @方法功能说明：从磁盘读出java对象
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年4月1日上午9:42:04
	 * @version：
	 * @修改内容：
	 * @参数：@return
	 * @return:Object
	 * @throws
	 */
	protected Object readObject() {
	    Object read = SaveObjectUtil.read(getObjectFile());
//		Object read = SaveObjectUtilJsonImp.read(getObjectFile(), entityClass);
	    return read;
	}
	/**
	 * @方法功能说明：从磁盘写入java对象
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年4月1日上午9:48:41
	 * @version：
	 * @修改内容：
	 * @参数：@param obj
	 * @return:void
	 * @throws
	 */
	protected void writeObject(Serializable obj) {
	    SaveObjectUtil.save(getObjectFile(),obj);
//		SaveObjectUtilJsonImp.save(getObjectFile(), obj);
	}	

	/**
	 * @方法功能说明：存储对象的文件名
	 * @修改者名字：xinglj
	 * @修改时间：2014年10月14日下午5:17:42
	 * @修改内容：
	 * @参数：@return 
	 * @return:File or null
	 * @throws
	 */
	 protected File getObjectFile() {
	     if(objectPath==null || objectPath.length()==0)
	     {
//		 throw new RuntimeException("请配置 objectPath!一般是jf.properties中的属性");
		 System.err.println("请配置 objectPath!一般是jf.properties中的属性");
		 return null;
	     }
		return new File(objectPath,entityClass.getName()+".jdb");
	}
	 
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
	    List collection = new ArrayList<>();
	    for(int i=0;i<98;i++)
		collection.add(i+"");
	    
	    Pagination page = new Pagination(10,10,collection.size());
	    System.out.println(BaseEntityFileDao.getPage(page, collection).getList());
	
	}
	

}

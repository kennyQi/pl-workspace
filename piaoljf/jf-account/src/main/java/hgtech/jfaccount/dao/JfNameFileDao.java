/**
 * @文件名称：JfAccountTypeHome.java
 * @类路径：hgtech.jfaccount.home
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014-9-11下午1:58:33
 */
package hgtech.jfaccount.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hg.common.page.Pagination;
import hgtech.jf.JfProperty;
import hgtech.jf.entity.StringUK;
import hgtech.jf.entity.dao.BaseEntityFileDao;
import hgtech.jf.tree.WithChildren;
import hgtech.jfaccount.Domain;
import hgtech.jfaccount.JfAccountType;
import hgtech.jfaccount.JfAccountTypeUK;
import hgtech.jfaccount.JfName;

/**
 * @类功能说明：jfname 文件形式的dao
 * @类修改者：
 * @修改日期：2014-9-11下午1:58:33
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-11下午1:58:33
 *
 */
public class JfNameFileDao extends BaseEntityFileDao<StringUK, JfName>{
	
	/**
	 * @类名：JfAccountTypeHome_Mem.java
	 * @描述：
	 * @@param entityClass
	 */
	public JfNameFileDao( ) {
		super(JfName.class);
		objectPath=JfProperty.getProperties().getProperty("jfPath");		
	}

	/* (non-Javadoc)
	 * @see hgtech.entity.EntityHome_Mem#newEntity()
	 */
	@Override
	public JfName newInstance() {
		return new JfName();
	}

	/**
	 * @方法功能说明：
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年3月20日下午12:27:10
	 * @version：
	 * @修改内容：
	 * @参数：@param pagination
	 * @参数：@return
	 * @return:Object
	 * @throws
	 */
	public Pagination queryPagination(Pagination pagination) {
	    refresh();
	   JfName qo= (JfName) pagination.getCondition();
	   List<JfName> all = getEntityList();
	   List<JfName> filters = new ArrayList<>();
	   for(JfName n:all){
	       if(qo.name!=null && qo.name.length()>0){
		   if( n.name.contains(qo.name))
		       filters.add(n);
	       }else
		    filters.add(n);
	   }
	   
	   //按添加顺序倒叙排序
	   Collections.reverse(filters);
	   
	   return getPage(pagination, filters);
	   
	}

 
 
}

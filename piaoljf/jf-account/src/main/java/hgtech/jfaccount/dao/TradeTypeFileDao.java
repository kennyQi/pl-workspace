/**
 * @文件名称：JfAccountTypeHome.java
 * @类路径：hgtech.jfaccount.home
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014-9-11下午1:58:33
 */
package hgtech.jfaccount.dao;

import java.util.ArrayList;
import java.util.List;

import hgtech.jf.JfProperty;
import hgtech.jf.entity.StringUK;
import hgtech.jf.entity.dao.BaseEntityFileDao;
import hgtech.jf.tree.WithChildren;
import hgtech.jfaccount.Domain;
import hgtech.jfaccount.JfAccountType;
import hgtech.jfaccount.JfAccountTypeUK;
import hgtech.jfaccount.TradeType;

/**
 * @类功能说明：行业类型的文件形式的dao
 * @类修改者：
 * @修改日期：2014-9-11下午1:58:33
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-11下午1:58:33
 *
 */
public class TradeTypeFileDao extends BaseEntityFileDao<StringUK, TradeType>{
	
	/**
	 * @类名：JfAccountTypeHome_Mem.java
	 * @描述：
	 * @@param entityClass
	 */
	public TradeTypeFileDao( ) {
		super(TradeType.class);
		objectPath=JfProperty.getProperties().getProperty("jfPath");	
		dupLogicKey="分类编号已存在，请重新输入";
	}

	/* (non-Javadoc)
	 * @see hgtech.entity.EntityHome_Mem#newEntity()
	 */
	@Override
	public TradeType newInstance() {
		return new TradeType();
	}

	/**
	 * @方法功能说明：是否叶子
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年3月19日下午2:24:01
	 * @version：
	 * @修改内容：
	 * @参数：@param id
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean isEndCategory(String id) {
	    
	    boolean isend =true;
	    List<TradeType> sub = getSubList(id);
	    return sub.size()==0;
	}

	/**
	 * @方法功能说明：
	 * @修改者名字：<a href=xinglj@hgtech365.com>xinglj</a>
	 * @修改时间：2015年3月20日上午9:42:19
	 * @version：
	 * @修改内容：
	 * @参数：@param id
	 * @参数：@return
	 * @return:List<TradeType>
	 * @throws
	 */
	public List<TradeType> getSubList(String id) {
	    List<TradeType> sub=new ArrayList<>(); 
	    for(TradeType t: getEntityList()){
		if(t.upperType!=null && t.upperType.code.equals(id))
		{
		    sub.add(t);
		}
	    }
	    return sub;
	}

}

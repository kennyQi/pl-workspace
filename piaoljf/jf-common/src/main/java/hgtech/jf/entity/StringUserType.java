/**
 * @文件名称：SimpleUserType.java
 * @类路径：hgtech.jf.entity
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年11月27日下午8:19:32
 */
package hgtech.jf.entity;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

/**
 * @类功能说明：String类型的自定义hibernate 字段类型
 * @类修改者：
 * @修改日期：2014年11月27日下午8:19:32
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年11月27日下午8:19:32
 *
 */
public abstract class StringUserType implements UserType{
	/**
	 * 
	 * @方法功能说明：将数据库字段转为对象
	 * @修改者名字：xinglj
	 * @修改时间：2014年11月27日下午8:39:10
	 * @修改内容：
	 * @参数：@param dbString
	 * @参数：@return
	 * @return:Object
	 * @throws
	 */
	public abstract   Object toObject(String dbString) ;
	/**
	 * 将对象转为数据库的字段
	 * @方法功能说明：
	 * @修改者名字：xinglj
	 * @修改时间：2014年11月27日下午8:39:29
	 * @修改内容：
	 * @参数：@param obj
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public abstract String todbString(Object obj);
	/**
	 * @类名：SimpleUserType.java
	 * @描述： 
	 * @
	 */
	public StringUserType() {
		super();
	}

	public Object assemble(Serializable arg0, Object arg1) throws HibernateException {
		throw new RuntimeException("not implement yet!");
	}
	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#deepCopy(java.lang.Object)
	 */

	public Object deepCopy(Object arg0) throws HibernateException {
		return arg0;
//		throw new RuntimeException("not implement yet!");
	}
	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#disassemble(java.lang.Object)
	 */

	public Serializable disassemble(Object arg0) throws HibernateException {
		throw new RuntimeException("not implement yet!");
	}
	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#equals(java.lang.Object, java.lang.Object)
	 */

	public boolean equals(Object arg0, Object arg1) throws HibernateException {
	    	
	    	if(arg0!=null )
	    	    return arg0.equals(arg1);
	    	else{
	    		//then arg0==null
	    		return arg1==null;
	    	}
	    		
	}
	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#hashCode(java.lang.Object)
	 */

	public int hashCode(Object arg0) throws HibernateException {
		return arg0.hashCode();
	}
	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#isMutable()
	 */

	public boolean isMutable() {
		return false;
	}
	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#nullSafeGet(java.sql.ResultSet, java.lang.String[], org.hibernate.engine.spi.SessionImplementor, java.lang.Object)
	 */

	public Object replace(Object arg0, Object arg1, Object arg2)
			throws HibernateException {
				throw new RuntimeException("not implement yet!");
			}
			/* (non-Javadoc)
			 * @see org.hibernate.usertype.UserType#returnedClass()
			 */

	public abstract Class returnedClass();
	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#sqlTypes()
	 */

	public int[] sqlTypes() {
		return new int[]{Types.VARCHAR};
	}
	public Object nullSafeGet(ResultSet arg0, String[] arg1, SessionImplementor arg2,
			Object arg3) throws HibernateException, SQLException {
				String dbString = arg0.getString(arg1[0]);
				return toObject(dbString);
			}
	public void nullSafeSet(PreparedStatement arg0, Object arg1, int arg2,
			SessionImplementor arg3) throws HibernateException, SQLException {
				String dbString = todbString(arg1);
				arg0.setString(arg2, dbString);
				
			}

}
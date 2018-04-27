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
public abstract class AbstractUserType implements UserType{
	/**
	 * @类名：SimpleUserType.java
	 * @描述： 
	 * @
	 */
	public AbstractUserType() {
		super();
	}

	public Object assemble(Serializable arg0, Object arg1) throws HibernateException {
		throw new RuntimeException("not implement yet!");
	}
	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#deepCopy(java.lang.Object)
	 */

	public Object deepCopy(Object arg0) throws HibernateException {
		throw new RuntimeException("not implement yet!");
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
		return arg0.equals(arg1);
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

	public abstract int[] sqlTypes() ;
	/**
	 * Retrieve an instance of the mapped class from a JDBC resultset. Implementors
	 * should handle possibility of null values.
	 *
	 *
	 * @param rs a JDBC result set
	 * @param names the column names
	 * @param session
	 *@param owner the containing entity  @return Object
	 * @throws HibernateException
	 * @throws SQLException
	 */
	public abstract Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException;

	/**
	 * Write an instance of the mapped class to a prepared statement. Implementors
	 * should handle possibility of null values. A multi-column type should be written
	 * to parameters starting from <tt>index</tt>.
	 *
	 *
	 * @param st a JDBC prepared statement
	 * @param value the object to write
	 * @param index statement parameter index
	 * @param session
	 * @throws HibernateException
	 * @throws SQLException
	 */
	public abstract void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException;


}
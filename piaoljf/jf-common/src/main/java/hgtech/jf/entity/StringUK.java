/**
 * @文件名称：StringUK.java
 * @类路径：hgtech.jf.entity
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014-9-24上午11:35:03
 */
package hgtech.jf.entity;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

/**
 * @类功能说明：string类型的主键
 * @类修改者：
 * @修改日期：2014-9-24上午11:35:03
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-24上午11:35:03
 *
 */
public   class StringUK extends StringUserType implements EntityUK,UserType{
	String s;

	/**
	 * @return the s
	 */
	public String getS() {
		return s;
	}
	/**
	 * @param s the s to set
	 */
	public void setS(String s) {
		this.s = s;
	}
	/**
	 * @类名：StringUK.java
	 * @@param s
	 */
	public StringUK(String s) {
		super();
		this.s = s;
	}
	
	public StringUK(){
		
	}
	/* (non-Javadoc)
	 * @see hgtech.jf.entity.EntityUK#getkey()
	 */
	@Override
	public Serializable getkey() {
		return s;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return s;
	}
	/* (non-Javadoc)
	 * @see hgtech.jf.entity.StringUserType#toObject(java.lang.String)
	 */
	@Override
	public Object toObject(String dbString) {
		return new StringUK(dbString);
	}
	/* (non-Javadoc)
	 * @see hgtech.jf.entity.StringUserType#todbString(java.lang.Object)
	 */
	@Override
	public String todbString(Object obj) {
		if(obj instanceof String)
			return (String) obj;
		else
		return ((StringUK)obj).s;
	}
	/* (non-Javadoc)
	 * @see hgtech.jf.entity.StringUserType#returnedClass()
	 */
	@Override
	public Class returnedClass() {
		return this.getClass();
	}
	
/*	 (non-Javadoc)
	* @see hgtech.jf.entity.StringUserType#equals(java.lang.Object, java.lang.Object)
	
	@Override
	public boolean equals(Object arg0, Object arg1) throws HibernateException {
        	if(arg0 instanceof StringUK && arg1 instanceof StringUK){
        	   return arg0.toString().equals(arg1.toString());
        	}else
        	    return super.equals(arg0, arg1);
	}*/
}

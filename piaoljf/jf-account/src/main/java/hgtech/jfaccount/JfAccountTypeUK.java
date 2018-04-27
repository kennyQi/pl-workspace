/**
 * @文件名称：JfAccountTypeUK.java
 * @类路径：hgtech.jfaccount
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014-9-16上午11:10:54
 */
package hgtech.jfaccount;

import hgtech.jf.entity.EntityUK;
import hgtech.jf.entity.StringUserType;

import java.io.Serializable;

import org.hibernate.usertype.UserType;


/**
 * @修改日期：2014-9-16上午11 :10:54
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-16上午11 :10:54
 */
public class JfAccountTypeUK extends StringUserType implements EntityUK,UserType, Serializable{
    	
	/**
     * @FieldsserialVersionUID:TODO
     */
    private static final long serialVersionUID = -1916984528760213512L;
	/**
	 * 积分机构
	 */
	public Domain domain;
	/**
	 * @类名：JfAccountTypeUK.java
	 * @描述：账户类型uk 
	 * @@param domain
	 * @@param consumeType
	 */
	public JfAccountTypeUK(Domain domain, TradeType consumeType) {
		super();
		this.domain = domain;
		this.consumeType = consumeType;
	}

	/**
	 * @FieldsconsumeType:交易类型
	 */
	public TradeType consumeType;

	/**
	 * @类名：JfAccountTypeUK.java
	 * @描述： 
	 * @
	 */
	public JfAccountTypeUK() {
	}

	/* (non-Javadoc)
	 * @see hgtech.entity.EntityUK#getkey()
	 */
	public String getkey() {
		if(domain.code==null&&consumeType.code==null){
			return null;
		}
		return domain.code+"__"+consumeType.code;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return getkey().equals(((JfAccountTypeUK)obj).getkey());
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getkey();
	}

	/* (non-Javadoc)
	 * @see hgtech.jf.entity.StringUserType#toObject(java.lang.String)
	 */
	@Override
	public Object toObject(String dbString) {
		return SetupAccountContext.findType(dbString);

	}

	/* (non-Javadoc)
	 * @see hgtech.jf.entity.StringUserType#todbString(java.lang.Object)
	 */
	@Override
	public String todbString(Object obj) {

		return ((JfAccountTypeUK)obj).getkey();
	}

	/* (non-Javadoc)
	 * @see hgtech.jf.entity.StringUserType#returnedClass()
	 */
	@Override
	public Class returnedClass() {
		return this.getClass();
	}

	/**
	 * @return the domain
	 */
	public Domain getDomain() {
	    return domain;
	}

	/**
	 * @return the consumeType
	 */
	public TradeType getConsumeType() {
	    return consumeType;
	}
}
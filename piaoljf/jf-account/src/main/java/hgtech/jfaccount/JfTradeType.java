/**
 * @文件名称：JfTradeType.java
 * @类路径：hgtech.jfaccount
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014-9-24上午10:14:49
 */
package hgtech.jfaccount;

import hgtech.jf.entity.StringUserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

/**
 * @类功能说明：积分账户的交易类型
 * @类修改者：
 * @修改日期：2014-9-24上午10:14:49
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-24上午10:14:49
 *
 */
public class JfTradeType extends TradeType implements UserType{
	StringUserType userType=new StringUserType() {
		
		@Override
		public String todbString(Object obj) {
			if(obj==null)
				return null;
			else
				return ((JfTradeType)obj).getCode();
		}
		
		@Override
		public Object toObject(String dbString) {
			return JfTradeType.findType(dbString);
		}

		@Override
		public Class returnedClass() {
			return this.getClass();
		}
	};
	public static  JfTradeType commit, adjust,expire,in,out,exchange,charge,adjed,ARRIVED,undo;
	public static ArrayList<TradeType> all=new ArrayList<>(); 
	public static void init(){
		if(commit==null){
			commit=new JfTradeType();
			commit.code="CAL";
			commit.name="活动奖励";
			
			adjust=new JfTradeType();
			adjust.code="ADJ";
			adjust.name="调整";
			
			adjed=new JfTradeType();
			adjed.code="ADJED";
			adjed.name="积分明细调整";
			
			exchange=new JfTradeType();
			exchange.code="EX";
			exchange.name="消费";

			charge=new JfTradeType();
			charge.code="CZ";
			charge.name="充值";
			
			in=new JfTradeType();
			in.code="IN";
			in.name="转入";
			
			out=new JfTradeType();
			out.code="OUT";
			out.name="转出";
			
			expire=new JfTradeType();
			expire.code="EXP";
			expire.name="过期";
			
			ARRIVED = new JfTradeType();
			ARRIVED.code="ARRIVED";
			ARRIVED.name="生效";

			undo = new JfTradeType();
			undo.code = "UNDO";
			undo.name = "撤销";
			undo.clientName = "退回";
			
			all.add(commit);
			all.add(exchange);
			all.add(charge);
			all.add(expire);
			all.add(adjust);
			all.add(adjed);
			all.add(in);
			all.add(out);
			all.add(ARRIVED);
			all.add(undo);
		}
		
	}
	public static JfTradeType findType(String code){
		for(TradeType t:all)
			if(t.code.equals(code))
				return (JfTradeType)t;
		return null;
	}
	/* (non-Javadoc)
	 * @see hgtech.jfaccount.TradeType#toString()
	 */
	@Override
	public String toString() {
		return name;
	}
	/**
	 * @param dbString
	 * @return
	 * @see hgtech.jf.entity.StringUserType#toObject(java.lang.String)
	 */
	public Object toObject(String dbString) {
		return userType.toObject(dbString);
	}
	/**
	 * @param obj
	 * @return
	 * @see hgtech.jf.entity.StringUserType#todbString(java.lang.Object)
	 */
	public String todbString(Object obj) {
		return userType.todbString(obj);
	}
	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 * @throws HibernateException
	 * @see hgtech.jf.entity.StringUserType#assemble(java.io.Serializable, java.lang.Object)
	 */
	public Object assemble(Serializable arg0, Object arg1)
			throws HibernateException {
		return userType.assemble(arg0, arg1);
	}
	/**
	 * @param arg0
	 * @return
	 * @throws HibernateException
	 * @see hgtech.jf.entity.StringUserType#deepCopy(java.lang.Object)
	 */
	public Object deepCopy(Object arg0) throws HibernateException {
		return userType.deepCopy(arg0);
	}
	/**
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return userType.hashCode();
	}
	/**
	 * @param arg0
	 * @return
	 * @throws HibernateException
	 * @see hgtech.jf.entity.StringUserType#disassemble(java.lang.Object)
	 */
	public Serializable disassemble(Object arg0) throws HibernateException {
		return userType.disassemble(arg0);
	}
	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 * @throws HibernateException
	 * @see hgtech.jf.entity.StringUserType#equals(java.lang.Object, java.lang.Object)
	 */
	public boolean equals(Object arg0, Object arg1) throws HibernateException {
		return userType.equals(arg0, arg1);
	}
	/**
	 * @param arg0
	 * @return
	 * @throws HibernateException
	 * @see hgtech.jf.entity.StringUserType#hashCode(java.lang.Object)
	 */
	public int hashCode(Object arg0) throws HibernateException {
		return userType.hashCode(arg0);
	}
	/**
	 * @return
	 * @see hgtech.jf.entity.StringUserType#isMutable()
	 */
	public boolean isMutable() {
		return userType.isMutable();
	}
	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @return
	 * @throws HibernateException
	 * @see hgtech.jf.entity.StringUserType#replace(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	public Object replace(Object arg0, Object arg1, Object arg2)
			throws HibernateException {
		return userType.replace(arg0, arg1, arg2);
	}
	/**
	 * @return
	 * @see hgtech.jf.entity.StringUserType#returnedClass()
	 */
	public Class returnedClass() {
		return userType.returnedClass();
	}
	/**
	 * @return
	 * @see hgtech.jf.entity.StringUserType#sqlTypes()
	 */
	public int[] sqlTypes() {
		return userType.sqlTypes();
	}
	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @return
	 * @throws HibernateException
	 * @throws SQLException
	 * @see hgtech.jf.entity.StringUserType#nullSafeGet(java.sql.ResultSet, java.lang.String[], org.hibernate.engine.spi.SessionImplementor, java.lang.Object)
	 */
	public Object nullSafeGet(ResultSet arg0, String[] arg1,
			SessionImplementor arg2, Object arg3) throws HibernateException,
			SQLException {
		return userType.nullSafeGet(arg0, arg1, arg2, arg3);
	}
	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @throws HibernateException
	 * @throws SQLException
	 * @see hgtech.jf.entity.StringUserType#nullSafeSet(java.sql.PreparedStatement, java.lang.Object, int, org.hibernate.engine.spi.SessionImplementor)
	 */
	public void nullSafeSet(PreparedStatement arg0, Object arg1, int arg2,
			SessionImplementor arg3) throws HibernateException, SQLException {
		userType.nullSafeSet(arg0, arg1, arg2, arg3);
	}
	/**
	 * @param obj
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if(obj==null)
			return userType.equals(obj);
		
		return (((JfTradeType)obj).userType).equals(userType);
	}
}

/**
 * @文件名称：Entity.java
 * @类路径：hgtech.jfaccount
 * @描述：账户逻辑主键类
 * @作者：xinglj
 * @时间：2014-9-16上午8:59:09
 */
package hgtech.jfaccount;

import java.io.Serializable;

import hgtech.jf.entity.EntityUK;

/**
 * @修改日期：2014-9-16上午8 :59:09
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-16上午8 :59:09
 */
public class JfAccountUK implements EntityUK {
	/**
	 * 积分用户
	 */
	public JfUser user;
	/**
	 * @类名：JfAccountUK.java
	 * @描述：积分账户的逻辑主键
	 * @@param user
	 * @@param type
	 */
	public JfAccountUK(JfUser user, JfAccountType type) {
		super();
		this.user = user;
		this.type = type;
	}

	/**
	 * 积分账户类型
	 */
	public JfAccountType type;                                  


	public JfAccountUK() {
	}

 
	public String getUK() {
		if(user!=null)
		return user.readUK()+"__"+type.readUK();
		else
			return "?__?";
	}
	/* (non-Javadoc)
	 * @see hgtech.entity.EntityUK#getkey()
	 */
	public Serializable getkey() {
		return getUK();
	} 
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
	return getUK();
	}


	public JfUser getUser() {
		return user;
	}


	public void setUser(JfUser user) {
		this.user = user;
	}


	public JfAccountType getType() {
		return type;
	}


	public void setType(JfAccountType type) {
		this.type = type;
	}
	
	
}
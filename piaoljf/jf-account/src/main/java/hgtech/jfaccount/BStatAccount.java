/**
 * @文件名称：JfAccount.java
 * @类路径：hgtech.jfaccount
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014-9-5下午3:09:18
 */
package hgtech.jfaccount;

import hgtech.jf.entity.Entity;
import hgtech.jf.entity.StringUK;
import hgtech.jf.tree.WithChildren;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Columns;

/**
	以积分提供商一个积分类型为主键的积分汇总账户，<br>
	
 * @类修改者：
 * @修改日期：2014-9-5下午3:09:18
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-5下午3:09:18
 *
 */
//@javax.persistence.Entity
//@Table(name="JF_ACCOUNT")
public class BStatAccount implements WithChildren {
	/**
	 * 对应的积分类型
	 */
    	public JfAccountType accountType;
    	/**
    	 * 汇积分
    	 */
	@Column(name="amount")
	public long jf;
	/**
	 * 手续费，单位为转出积分类型
	 */
	public long fee;
	/**
	 * 在途积分
	 */
	@Column(name="amount_todo")
	public long jfTODO;

	/**
	 * 对方积分，可能是转出也可以是转入
	 */
	@Transient
	public long objJf;
	 
	
	@Column(name="update_time")
	public Date lastUpdate;
	
	/**
	 * 下级账户
	 */
	@Transient
	public LinkedList<WithChildren> subList=new LinkedList<WithChildren>() ; 
	
	/* (non-Javadoc)
	 * @see hgtech.jfaccount.TreeView#getSubList()
	 */
	@Override
	public LinkedList<WithChildren> getSubList() {
		return subList;
	}
 
	/* (non-Javadoc)
	 * @see hgtech.jfaccount.WithChildren#getMe()
	 */
	@Override
	public Object getMe() {
		return this;
	}
 
	/**
	 * @return the jf
	 */
	public long getJf() {
		return jf;
	}

	/**
	 * @param jf the jf to set
	 */
	public void setJf(long jf) {
		this.jf = jf;
	}

	/**
	 * @return the lastUpdate
	 */
	public Date getLastUpdate() {
		return lastUpdate;
	}

	/**
	 * @param lastUpdate the lastUpdate to set
	 */
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	/**
	 * @param subList the subList to set
	 */
	public void setSubList(LinkedList<WithChildren> subList) {
		this.subList = subList;
	}
	 

	/**
	 * @return the jfTODO
	 */
	public long getJfTODO() {
		return jfTODO;
	}

	/**
	 * @param jfTODO the jfTODO to set
	 */
	public void setJfTODO(long jfTODO) {
		this.jfTODO = jfTODO;
	}

	/**
	 * @return the jfIn
	 */
	public long getObjJf() {
		return objJf;
	}

	/**
	 * @param jfIn the jfIn to set
	 */
	public void setObjJf(long jfIn) {
		this.objJf = jfIn;
	}

 

	/**
	 * @return the accountType
	 */
	public JfAccountType getAccountType() {
	    return accountType;
	}

	/**
	 * @param accountType the accountType to set
	 */
	public void setAccountType(JfAccountType accountType) {
	    this.accountType = accountType;
	}

	/**
	 * @return the fee
	 */
	public long getFee() {
	    return fee;
	}

	/**
	 * @param fee the fee to set
	 */
	public void setFee(long fee) {
	    this.fee = fee;
	}
}

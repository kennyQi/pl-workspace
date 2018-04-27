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
import javax.persistence.Version;

import org.hibernate.annotations.Columns;

/**
	积分账户，<br>
	积分用户和账户类型联合为唯一性
 * @类修改者：
 * @修改日期：2014-9-5下午3:09:18
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-5下午3:09:18
 *
 */
@javax.persistence.Entity
@Table(name="JF_ACCOUNT")
public class JfAccount implements WithChildren, Entity<JfAccountUK>{
	@Id
	@Column(name="id")
	public String id;
	
	@Column(name="user")
	public String user;
	
	@Column(name="acct_type")
	public String acct_type;
	
	@Transient
	public JfAccountUK uk ;
	
	@Column(name="amount")
	public long jf;
	
	/**
	 * 在途积分
	 */
	@Column(name="amount_todo")
	public long jfTODO;

	@Transient
	public long jfIn;
	@Transient
	public long jfOut;
	
	@Column(name="update_time")
	public Date lastUpdate;
	
	/**
	 * 版本号，每次修改+1.spring jpa太强大了！只有此处标注，自动检查该自动，并每次更新累加
	 */
	@Column(name="version", columnDefinition="int(11)  NOT NULL DEFAULT 0")
	@Version
	public int version;
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
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return (user ) +" " +acct_type +jf+"分";
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		JfAccount accobj=(JfAccount) obj;
		return uk.user.equals(accobj.uk.user) && uk.type.equals(accobj.uk.type);
	}

	/* (non-Javadoc)
	 * @see hgtech.jfaccount.WithChildren#getMe()
	 */
	@Override
	public Object getMe() {
		return this;
	}
	
	/* (non-Javadoc)
	 * @see hgtech.jfaccount.Entity#getUK()
	 */
	@Override
	public JfAccountUK readUK() {
		return uk;
	}
	

	/* (non-Javadoc)
	 * @see hgtech.entity.Entity#setUK(java.lang.Object)
	 */
	@Override
	public void putUK(JfAccountUK uk) {
		this.uk=uk;
	}

	/* (non-Javadoc)
	 * @see hgtech.jf.entity.Entity#syncTransentPersistence()
	 */
	@Override
	public void syncUK() {
		if( uk!=null){
			 user= uk.user.code.getS();
			 acct_type= uk.type.getCode();
		}else{
		 uk=new JfAccountUK();
		 uk.type= SetupAccountContext.findType(getAcct_type()); 
		 uk.user=new JfUser();
		 uk.user.code=new StringUK( user);
		  
		}

	}

	/**
	 * @return the uk
	 */
	public JfAccountUK getUk() {
		return uk;
	}

	/**
	 * @param uk the uk to set
	 */
	public void setUk(JfAccountUK uk) {
		this.uk = uk;
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
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getAcct_type() {
		return acct_type;
	}

	public void setAcct_type(String acct_type) {
		this.acct_type = acct_type;
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
	public long getJfIn() {
		return jfIn;
	}

	/**
	 * @param jfIn the jfIn to set
	 */
	public void setJfIn(long jfIn) {
		this.jfIn = jfIn;
	}

	/**
	 * @return the jfOut
	 */
	public long getJfOut() {
		return jfOut;
	}

	/**
	 * @param jfOut the jfOut to set
	 */
	public void setJfOut(long jfOut) {
		this.jfOut = jfOut;
	}

	/**
	 * @return the version
	 */
	public int getVersion() {
	    return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(int version) {
	    this.version = version;
	}
}

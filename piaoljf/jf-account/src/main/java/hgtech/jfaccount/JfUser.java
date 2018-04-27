/**
 * @文件名称：JfUser.java
 * @类路径：hgtech.jfaccount
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014-9-5下午3:09:08
 */
package hgtech.jfaccount;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import hgtech.jf.entity.Entity;
import hgtech.jf.entity.StringUK;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2014-9-5下午3:09:08
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014-9-5下午3:09:08
 *
 */
@javax.persistence.Entity
@Table(name="JF_USER")
public class JfUser implements Entity<StringUK>  {
	/**
	 * 积分用户的编码，唯一性
	 */
	@Id
	@Column(name="code")
	public StringUK code;
	@Column(name="name")
	public String name;
	
	/**
	 * 决定使用可以使用 带有效期积分
	 */
	@Column(name="is_real")
	public Boolean isReal;
	
	public Boolean getIsReal() {
		return isReal;
	}
	public void setIsReal(Boolean isReal) {
		this.isReal = isReal;
	}
	/**
	 * 等级
	 */
	@Column(length=50)
	public String gradeCode;
	
	@Transient
	public Grade gradeObj;
	@Transient
	public Grade oldGradeObj;
	
	/**
	 * 原来等级
	 */
	@Column(length=50)
	public String oldGradeCode;
	
	/**
	 * 等级修改日期
	 */
	@Column(length=8)
	public String gradeUpdateDate;

	/**
	 * 等级失效日
	 */
	@Column(length=8)
	public String gradeInvalidDate;
	
	/* (non-Javadoc)
	 * @see hgtech.jf.entity.EntityUK#getkey()
	 */
	public Serializable getkey() {
		return code;
	}
	/* (non-Javadoc)
	 * @see hgtech.jf.entity.Entity#getUK()
	 */
	@Override
	public StringUK readUK() {
		return code;
	}
 
	/* (non-Javadoc)
	 * @see hgtech.jf.entity.Entity#setUK(java.lang.Object)
	 */
	@Override
	public void putUK(StringUK uk) {
		code=uk;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return readUK().getS();
	}
	/* (non-Javadoc)
	 * @see hgtech.jf.entity.Entity#syncTransentPersistence()
	 */
	@Override
	public void syncUK() {
		
	}
	public StringUK getCode() {
		return code;
	}
	public void setCode(StringUK code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	 
	public String getGradeInvalidDate() {
		return gradeInvalidDate;
	}
	public void setGradeInvalidDate(String gradeInvalidDate) {
		this.gradeInvalidDate = gradeInvalidDate;
	}
	public String getGradeCode() {
		return gradeCode;
	}
	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}
	public Grade getGradeObj() {
		return gradeObj;
	}
	public void setGradeObj(Grade gradeObj) {
		this.gradeObj = gradeObj;
	}
	public Grade getOldGradeObj() {
		return oldGradeObj;
	}
	public void setOldGradeObj(Grade oldGradeObj) {
		this.oldGradeObj = oldGradeObj;
	}
	public String getOldGradeCode() {
		return oldGradeCode;
	}
	public void setOldGradeCode(String oldGradeCode) {
		this.oldGradeCode = oldGradeCode;
	}
	
	
}

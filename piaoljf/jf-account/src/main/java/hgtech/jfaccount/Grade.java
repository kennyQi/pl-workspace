package hgtech.jfaccount;

import hg.common.component.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 会员等级条件
 * @author xinglj
 *
 */
@Entity
@Table(name="jf_grade")
public class Grade extends BaseModel{
	/**
	 * 
	 */
	@Column(unique=true)
	private String code;
	@Column
	private String name;
	/**
	 * 要到达的积分（含）
	 */
	@Column
	private int jf;
	
	/**
	 * 积分顶，不含
	 */
	@Transient
	private int toJf = Integer.MAX_VALUE;
	
	
	/**
	 * 依据的积分类型。成长值、消费值等
	 */
	@Column
	private String accountType;
	
	@Transient
	private JfAccountType accountTypeObj;
	
	public int getToJf() {
		return toJf;
	}
	public void setToJf(int toJf) {
		this.toJf = toJf;
	}
	
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	/**
	 * 等级有效期（年）
	 */
	@Column
	private int validYear;
	/**
	 * 失效后扣减的积分
	 */
	@Column
	private int invalidJf;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getJf() {
		return jf;
	}
	public void setJf(int jf) {
		this.jf = jf;
	}

	public int getValidYear() {
		return validYear;
	}
	public void setValidYear(int validYear) {
		this.validYear = validYear;
	}
	public int getInvalidJf() {
		return invalidJf;
	}
	public void setInvalidJf(int invalidJf) {
		this.invalidJf = invalidJf;
	}
	public JfAccountType getAccountTypeObj() {
		return accountTypeObj;
	}
	public void setAccountTypeObj(JfAccountType accountTypeObj) {
		this.accountTypeObj = accountTypeObj;
	}
	
	
}

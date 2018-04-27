package jxc.domain.model.supplier;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import jxc.domain.model.M;

/**
 * 供应商基本信息
 * @author liujz
 *
 */
@Embeddable
public class SupplierBaseInfo {

	/**
	 * 供应商名称
	 */
	@Column(name="SUPPLIER_NAME",length=255)
	private String name;

	
	/**
	 * 供应商类型
	 */
	@Column(name="SUPPLIER_TYPE",columnDefinition=M.NUM_COLUM)
	private Integer type;
	
	/**
	 * 开户银行
	 */
	@Column(name="SUPPLIER_BANK",length=50)
	private String bank;
	
	/**
	 * 银行帐号
	 */
	@Column(name="SUPPLIER_BANK_ACCOUNT",length=20)
	private String account;
	

	/**
	 * 公司网站
	 */
	@Column(name="SUPPLIER_URL",length=255)
	private String URL;
	
	/**
	 * 法人名字
	 */
	@Column(name="SUPPLIER_LEGAL_PERSON",length=10)
	private  String legalPerson;
	
	/**
	 * 税务号
	 */
	@Column(name="SUPPLIER_TAX",length=20)
	private String tax;

	/**
	 * 注册资本
	 */
	@Column(name="SUPPLIER_REGISTERED_CAPITAL",columnDefinition=M.DOUBLE_COLUM)
	private Double registeredCapital;
	
	
	/**
	 * 成立时间
	 */
	@Column(name="SUPPLIER_ESTABISH_DATE",columnDefinition=M.DATE_COLUM)
	private Date establishDate;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getEstablishDate() {
		return establishDate;
	}

	public void setEstablishDate(Date establishDate) {
		this.establishDate = establishDate;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public Double getRegisteredCapital() {
		return registeredCapital;
	}

	public void setRegisteredCapital(Double registeredCapital) {
		this.registeredCapital = registeredCapital;
	}

}

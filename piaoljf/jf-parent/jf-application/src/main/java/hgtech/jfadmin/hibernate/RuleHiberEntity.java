/**
 * @文件名称：RuleHiberEntity.java
 * @类路径：hgtech.jfadmin.hiberentity
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年10月22日上午9:54:36
 */
package hgtech.jfadmin.hibernate;

import hgtech.jfaccount.JfAccountType;
import hgtech.jfadmin.util.RuleUtil;
import hgtech.jfcal.dao.RuledSession;
import hgtech.jfcal.model.Project;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2014年10月22日上午9:54:36
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月22日上午9:54:36
 * 
 */
@Entity
@Table(name = "CAL_RULE")
public class RuleHiberEntity implements Serializable {
	
	@Id
	@Column(name = "code", length = 30)
	public String code;
	
	@Column(name = "name", length = 100)
	public String name;
	
	@Column(name = "template_name", length = 30)
	public String logicClass;
	
	@Column(name = "props", length = 1024)
	public String props;
	
	@Transient
	public Map<String, String> parameters = new HashMap<String, String>();
	
	@Transient
	public RuledSession session;
	
	@Transient
	public Project project;
	
	@Column(name = "account_type")
	public String accountType;
	
	@Column(name = "start_date")
	public Date startDate;
	
	@Column(name = "end_date")
	public Date endDate;
	
	@Column(name = "rule_status")
	public String status;

	@Column(name="jf_valid_year")
	public Integer jfValidYear;
	
	@Column(name="jf_end_year")
	public Date jfEndYear;
	
	/**
	 * 规则是否过期
	 */
	@Transient
	public String isLimit;
	@Transient
	public JfAccountType jfAccountType;
 
	/**
	 * 规则模板显示名
	 */
	@Transient
	public String templateName;
	
	
	public JfAccountType getJfAccountType() {
		return jfAccountType;
	}

	public void setJfAccountType(JfAccountType jfAccountType) {
		this.jfAccountType = jfAccountType;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getIsLimit() {
		return isLimit;
	}

	public void setIsLimit(String isLimit) {
		this.isLimit = isLimit;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the logicClass
	 */
	public String getLogicClass() {
		return logicClass;
	}

	/**
	 * @param logicClass
	 *            the logicClass to set
	 */
	public void setLogicClass(String logicClass) {
		this.logicClass = logicClass;
	}

 
	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	
	
	public String getProps() {
		return props;
	}

	public Map getPropMap(){
		return RuleUtil.getParameters(props);
	}
	
	public void setProps(String props) {
		this.props = props;
	}

	public static String STATUS_Y = "Y";


	public Integer getJfValidYear() {
		if(jfValidYear==null){
			jfValidYear=0;
		}
		return jfValidYear;
	}

	public void setJfValidYear(Integer jfValidYear) {
		this.jfValidYear = jfValidYear;
	}

	public Date getJfEndYear() {
		return jfEndYear;
	}

	public void setJfEndYear(Date jfEndYear) {
		this.jfEndYear = jfEndYear;
	}

	
	
}

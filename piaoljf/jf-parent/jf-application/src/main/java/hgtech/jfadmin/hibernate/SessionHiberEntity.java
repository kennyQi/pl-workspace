/**
 * @文件名称：SessionHiberEntity.java
 * @类路径：hgtech.jfadmin.hibernate
 * @描述：TODO
 * @作者：xinglj
 * @时间：2014年10月24日上午10:32:06
 */
package hgtech.jfadmin.hibernate;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @类功能说明：中间数据(session)
 * @类修改者：
 * @修改日期：2014年10月24日上午10:32:06
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月24日上午10:32:06
 *
 */
@Entity
@Table(name="CAL_SESSION")
public class SessionHiberEntity implements Serializable{
	@Id
	@Column(name="rule_code")
	String ruleCode;
	@Id
	@Column(name="user_code")
	String userCode;
	/**
	 * json format
	 */
	@Column(name="props")
	String props;
	/**
	 * @return the ruleCode
	 */
	public String getRuleCode() {
		return ruleCode;
	}
	/**
	 * @param ruleCode the ruleCode to set
	 */
	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}
	/**
	 * @return the userCode
	 */
	public String getUserCode() {
		return userCode;
	}
	/**
	 * @param userCode the userCode to set
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	/**
	 * @return the props
	 */
	public String getProps() {
		return props;
	}
	/**
	 * @param props the props to set
	 */
	public void setProps(String props) {
		this.props = props;
	}
	
	
}

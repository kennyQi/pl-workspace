package hg.system.model.backlog;

import hg.system.model.M;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * 
 *@类功能说明：待办事项基本信息
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年9月17日下午3:43:26
 *
 */
@Embeddable
public class BacklogInfo {
	
	/**
	 * 类型
	 */
	@Column(name = "TYPE", length = 32)
	private String type;
	
	/**
	 * 名称
	 */
	@Column(name = "NAME", length = 96)
	private String name;
	
	/**
	 * 各平台待办事项描述,json格式字符串
	 */
	@Column(name = "DESCRIPTION", length = 1024)
	private String description;
	
	/**
	 * 限制执行次数
	 */
	@Column(name = "EXECUTE_NUM", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer executeNum;
	
	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;
	
	
	/** 支付平台支付宝支付待办事项 **/
	public static String BACKLOG_TYPE_PAYMENT_ALIPAY = "001";
	/** 支付平台汇金宝支付待办事项 **/
	public static String BACKLOG_TYPE_PAYMENT_HJB = "002";
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getExecuteNum() {
		return executeNum;
	}

	public void setExecuteNum(Integer executeNum) {
		this.executeNum = executeNum;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	
	
	
	

}

package hg.system.qo;

import hg.common.component.BaseQo;

/**
 * 
 * 
 *@类功能说明：待办事项Qo
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年9月16日下午3:38:06
 *
 */
@SuppressWarnings("serial")
public class BacklogQo extends BaseQo{

	/**
	 * 类型
	 */
	private String type;
	
	/**
	 * 是否按从小到大时间排序
	 */
	private Boolean createDateAsc;
	
	/**
	 * 待办事项是否执行成功
	 */
	private Boolean success;
	
	/**
	 * 待办事项描述
	 */
	private String description;
	private Boolean descriptionLike = true;
	
	/**
	 * 执行次数小于限制执行次数的待办事项
	 */
	private Boolean executeCountValid = true;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getCreateDateAsc() {
		return createDateAsc;
	}

	public void setCreateDateAsc(Boolean createDateAsc) {
		this.createDateAsc = createDateAsc;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getDescriptionLike() {
		return descriptionLike;
	}

	public void setDescriptionLike(Boolean descriptionLike) {
		this.descriptionLike = descriptionLike;
	}

	public Boolean getExecuteCountValid() {
		return executeCountValid;
	}

	public void setExecuteCountValid(Boolean executeCountValid) {
		this.executeCountValid = executeCountValid;
	}

	
	
	
	
	
}

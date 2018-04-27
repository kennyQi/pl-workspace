package hg.system.command.backlog;

import hg.common.component.BaseCommand;

/**
 * 
 * 
 *@类功能说明：创建待办事项command
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年9月16日下午4:03:54
 *
 */
@SuppressWarnings("serial")
public class CreateBacklogCommand extends BaseCommand {
	
	/**
	 * 待办事项类型
	 */
	private String type;
	
	/**
	 * 待办事项名称
	 */
	private String name;
	
	/**
	 * 待办事项描述
	 */
	private String description;
	
	/**
	 * 限制执行次数
	 */
	private Integer executeNum;
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getExecuteNum() {
		return executeNum;
	}

	public void setExecuteNum(Integer executeNum) {
		this.executeNum = executeNum;
	}
	
	

}

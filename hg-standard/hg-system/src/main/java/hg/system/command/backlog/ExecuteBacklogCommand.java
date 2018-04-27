package hg.system.command.backlog;


import hg.common.component.BaseCommand;

/**
 * 
 * 
 *@类功能说明：执行待办事项command
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年9月17日下午4:42:40
 *
 */
@SuppressWarnings("serial")
public class ExecuteBacklogCommand extends BaseCommand{
	
	/**
	 * 待办事项ID
	 */
	private String backlogId;
	
	/**
	 * 是否执行成功
	 */
	private Boolean success;
	

	public String getBacklogId() {
		return backlogId;
	}

	public void setBacklogId(String backlogId) {
		this.backlogId = backlogId;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	

	

	
	
	
	

}

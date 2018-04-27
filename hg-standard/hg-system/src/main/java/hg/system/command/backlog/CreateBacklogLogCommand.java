package hg.system.command.backlog;



public class CreateBacklogLogCommand {
	
	/**
	 * 待办事项
	 */
	private String BacklogId;
	
	/**
	 * 操作是否成功
	 */
	private Boolean success;
	
	/**
	 * 待办事项执行次数
	 */
	private Integer operateNum;
	
	/**
	 * 操作备注
	 */
	private String operateContent;
	

	public String getBacklogId() {
		return BacklogId;
	}


	public void setBacklogId(String backlogId) {
		BacklogId = backlogId;
	}


	public Boolean getSuccess() {
		return success;
	}


	public void setSuccess(Boolean success) {
		this.success = success;
	}


	public Integer getOperateNum() {
		return operateNum;
	}


	public void setOperateNum(Integer operateNum) {
		this.operateNum = operateNum;
	}


	public String getOperateContent() {
		return operateContent;
	}


	public void setOperateContent(String operateContent) {
		this.operateContent = operateContent;
	}

	
	

}

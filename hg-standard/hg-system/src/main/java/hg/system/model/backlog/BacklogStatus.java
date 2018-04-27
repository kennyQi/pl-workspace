package hg.system.model.backlog;

import hg.system.model.M;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * 
 *@类功能说明：待办事项状态信息
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年9月17日下午3:43:43
 *
 */
@Embeddable
public class BacklogStatus {
	
	/**
	 * 待办事项是否执行成功
	 */
	@Column(name = "SUCCESS")
	private Boolean success;
	
	/**
	 * 待办事项执行次数
	 */
    @Column(name = "EXECUTE_COUNT", columnDefinition = M.NUM_COLUM)
	private Integer executeCount;
    
   
	public Integer getExecuteCount() {
		return executeCount;
	}

	public void setExecuteCount(Integer executeCount) {
		this.executeCount = executeCount;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}
	
	

}

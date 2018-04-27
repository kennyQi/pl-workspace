package hg.system.model.backlog;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hg.system.command.backlog.CreateBacklogCommand;
import hg.system.command.backlog.ExecuteBacklogCommand;
import hg.system.model.M;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.alibaba.fastjson.JSONObject;


/**
 * 
 * 
 *@类功能说明：待办事项
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年9月16日下午3:32:21
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = M.TABLE_PREFIX_SYS + "BACKLOG")
public class Backlog extends BaseModel{

	/**
	 * 待办事项基本信息
	 */
	private BacklogInfo backlogInfo;
	
	/**
	 * 待办事项状态信息
	 */
	private BacklogStatus backlogStatus;
	
	
	public void create(CreateBacklogCommand command){
		
		setId(UUIDGenerator.getUUID());
		
		backlogInfo = new BacklogInfo();
		backlogInfo.setType(command.getType());
		backlogInfo.setName(command.getName());
		backlogInfo.setDescription(command.getDescription());
		backlogInfo.setExecuteNum(command.getExecuteNum());
		backlogInfo.setCreateDate(new Date());
		
		backlogStatus = new BacklogStatus();
		backlogStatus.setSuccess(false);
		backlogStatus.setExecuteCount(0);
		
		
	}
	
	public void execute(ExecuteBacklogCommand command){
		
		backlogStatus.setSuccess(command.getSuccess());
		//每执行一次，执行次数加1
		backlogStatus.setExecuteCount(backlogStatus.getExecuteCount()+1);
	}
	
	//解析描述信息中 key对应的value
	public Object parseDescription(String key){
		JSONObject jsonObj= JSONObject.parseObject(getBacklogInfo().getDescription());
		return jsonObj.get(key);
	}
	

	public BacklogInfo getBacklogInfo() {
		return backlogInfo;
	}

	public void setBacklogInfo(BacklogInfo backlogInfo) {
		this.backlogInfo = backlogInfo;
	}

	public BacklogStatus getBacklogStatus() {
		return backlogStatus;
	}

	public void setBacklogStatus(BacklogStatus backlogStatus) {
		this.backlogStatus = backlogStatus;
	}
	
	
	
	
	
}

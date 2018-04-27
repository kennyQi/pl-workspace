package slfx.xl.pojo.command.dealer;

import hg.common.component.BaseCommand;

/**
 * 
 * @类功能说明：启用禁用线路经销商command
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tuhualiang
 * @创建时间：2014年12月10日下午2:41:13
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class AuditLineDealerCommand extends BaseCommand{
	/**
	 * ID
	 */
	private String id;
	/**
	 * 批量禁用启用id字符串
	 */
	private String[] ids;
	/**
	 * 禁用启用开关
	 */
	private String flag;
	/**
	 * 
	 */
	private String status;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String[] getIds() {
		return ids;
	}
	public void setIds(String[] ids) {
		this.ids = ids;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}

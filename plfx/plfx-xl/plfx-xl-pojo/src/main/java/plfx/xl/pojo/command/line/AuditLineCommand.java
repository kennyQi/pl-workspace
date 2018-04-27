package plfx.xl.pojo.command.line;

import hg.common.component.BaseCommand;


/**
 * @类功能说明：审核线路基本信息Command
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：liusong
 * @创建时间：2014年12月17日上午10:47:30
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class AuditLineCommand extends BaseCommand{
	
	/**
	 * ID
	 */
	private String id;
	
	/**
	 * 审核状态
	 */
	private Integer status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}

package hg.fx.command.smsCodeRecord;

import hg.framework.common.base.BaseSPICommand;

/**
 * @类功能说明：doto
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江汇购科技有限公司
 * @部门： 技术部
 * @作者： zhouwei
 * @创建时间： 2016年6月1日 下午7:25:44
 * @版本： V1.0
 */
public class ModifySmsCodeRecordCommand extends BaseSPICommand {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 * */
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}

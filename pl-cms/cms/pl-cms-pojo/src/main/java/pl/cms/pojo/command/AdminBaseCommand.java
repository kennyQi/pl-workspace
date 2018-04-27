package pl.cms.pojo.command;

import hg.common.component.BaseCommand;

/**
 * 
 * @类功能说明：来自管理后台的命令基类
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2015年3月10日下午2:24:27
 * 
 */
@SuppressWarnings("serial")
public class AdminBaseCommand extends BaseCommand {

	/**
	 * 后台用户id
	 */
	private String adminStaffId;

	public String getAdminStaffId() {
		return adminStaffId;
	}

	public void setAdminStaffId(String adminStaffId) {
		this.adminStaffId = adminStaffId;
	}

}

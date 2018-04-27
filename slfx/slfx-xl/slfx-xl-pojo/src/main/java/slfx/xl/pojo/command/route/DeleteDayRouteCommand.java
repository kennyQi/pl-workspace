package slfx.xl.pojo.command.route;

import hg.common.component.BaseCommand;

/**
 * 
 * @类功能说明:删除一日行程
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：luoyun
 * @创建时间：2014年12月16日下午3:11:08
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class DeleteDayRouteCommand extends BaseCommand{

	/**
	 * 每日行程ID
	 */
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}

package slfx.xl.pojo.command.line;

import hg.common.component.BaseCommand;


/**
 * @类功能说明：下架线路基本信息Command
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
public class UnderCarriageLineCommand extends BaseCommand{
	
	/**
	 * ID
	 */
	private String lineID;

	public String getLineID() {
		return lineID;
	}

	public void setLineID(String lineID) {
		this.lineID = lineID;
	}
	

	
}

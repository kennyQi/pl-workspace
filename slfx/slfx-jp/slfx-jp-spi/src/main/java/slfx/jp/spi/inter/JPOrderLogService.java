package slfx.jp.spi.inter;

import slfx.jp.command.admin.orderLog.CreateJPOrderLogCommand;
import slfx.jp.pojo.dto.order.JPOrderLogDTO;
import slfx.jp.qo.admin.JPOrderLogQO;

/**
 * @类功能说明：机票订单操作日志外层Service接口
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：liusong
 * @创建时间：2014年12月22日上午10:20:54
 * @版本：V1.0
 */
public interface JPOrderLogService extends BaseJpSpiService<JPOrderLogDTO, JPOrderLogQO> {

	/**
	 * 
	 * @方法功能说明：创建机票订单日志
	 * @修改者名字：caizhenghao
	 * @修改时间：2015年1月21日上午11:20:40
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean create(CreateJPOrderLogCommand command);

}
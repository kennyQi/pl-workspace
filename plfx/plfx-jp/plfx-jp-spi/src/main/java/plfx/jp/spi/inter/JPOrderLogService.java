package plfx.jp.spi.inter;

import plfx.jp.command.admin.jpOrderLog.CreateJPOrderLogCommand;
import plfx.yeexing.pojo.dto.order.JPOrderLogDTO;
import plfx.yeexing.qo.admin.JPOrderLogQO;


/****
 * 
 * @类功能说明：机票订单操作日志外层Service接口
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年7月9日下午2:56:33
 * @版本：V1.0
 *
 */
public interface JPOrderLogService extends BaseJpSpiService<JPOrderLogDTO, JPOrderLogQO> {


	/****
	 * 
	 * @方法功能说明：创建机票订单日志
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年7月9日下午2:56:45
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean create(CreateJPOrderLogCommand command);

}
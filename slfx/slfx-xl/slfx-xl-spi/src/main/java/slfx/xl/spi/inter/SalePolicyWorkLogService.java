package slfx.xl.spi.inter;

import slfx.xl.pojo.command.salepolicy.CreateSalePolicyWorkLogCommand;
import slfx.xl.pojo.dto.log.SalePolicyWorkLogDTO;
import slfx.xl.pojo.exception.SlfxXlException;
import slfx.xl.pojo.qo.SalePolicyWorkLogQO;

/**
 * @类功能说明：价格政策操作日志外层Service接口
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：liusong
 * @创建时间：2014年12月22日上午10:20:54
 * @版本：V1.0
 */
public interface SalePolicyWorkLogService extends BaseXlSpiService<SalePolicyWorkLogDTO, SalePolicyWorkLogQO> {

	/**
	 * @方法功能说明：创建价格政策日志
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-24下午3:15:11
	 * @param command
	 * @throws SlfxXlException
	 */
	public boolean create(CreateSalePolicyWorkLogCommand command);
}
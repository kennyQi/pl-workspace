package slfx.xl.app.service.spi;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import slfx.xl.app.component.base.BaseXlSpiServiceImpl;
import slfx.xl.app.service.local.SalePolicyWorkLogLocalService;
import slfx.xl.pojo.command.salepolicy.CreateSalePolicyWorkLogCommand;
import slfx.xl.pojo.dto.log.SalePolicyWorkLogDTO;
import slfx.xl.pojo.exception.SlfxXlException;
import slfx.xl.pojo.qo.SalePolicyWorkLogQO;
import slfx.xl.spi.inter.SalePolicyWorkLogService;

/**
 * @类功能说明：价格政策SERVICE实现
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：liusong
 * @创建时间：2014年7月31日下午4:47:08
 * @版本：V1.0
 */
@Service("salePolicyWorkLogService")
public class SalePolicyWorkLogServiceImpl extends BaseXlSpiServiceImpl<SalePolicyWorkLogDTO, SalePolicyWorkLogQO, SalePolicyWorkLogLocalService> 
	implements SalePolicyWorkLogService {

	@Resource
	private  SalePolicyWorkLogLocalService  workLogSer;
	
	@Override
	protected SalePolicyWorkLogLocalService getService() {
		return workLogSer;
	}
	@Override
	protected Class<SalePolicyWorkLogDTO> getDTOClass() {
		return SalePolicyWorkLogDTO.class;
	}
	
	/**
	 * @方法功能说明：创建价格政策日志
	 * @修改者名字：chenyanshou
	 * @修改时间：2014-12-24下午3:15:11
	 * @param command
	 * @throws SlfxXlException
	 */
	public boolean create(CreateSalePolicyWorkLogCommand command){
		return workLogSer.create(command);
	}
}
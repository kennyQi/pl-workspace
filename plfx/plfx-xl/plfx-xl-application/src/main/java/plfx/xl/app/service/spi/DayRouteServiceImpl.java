package plfx.xl.app.service.spi;

import hg.system.common.util.EntityConvertUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plfx.xl.app.component.base.BaseXlSpiServiceImpl;
import plfx.xl.app.service.local.DayRouteLocalService;
import plfx.xl.app.service.local.LineLocalService;
import plfx.xl.domain.model.line.DayRoute;
import plfx.xl.pojo.command.line.XLUpdateLineMessageApiCommand;
import plfx.xl.pojo.command.route.CreateDayRouteCommand;
import plfx.xl.pojo.command.route.DeleteDayRouteCommand;
import plfx.xl.pojo.command.route.ModifyDayRouteCommand;
import plfx.xl.pojo.dto.route.DayRouteDTO;
import plfx.xl.pojo.exception.SlfxXlException;
import plfx.xl.pojo.qo.DayRouteQO;
import plfx.xl.pojo.system.LineMessageConstants;
import plfx.xl.spi.inter.DayRouteService;
import plfx.xl.spi.inter.LineService;

/**
 * 
 * @类功能说明：一日行程Service
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：luoyun
 * @创建时间：2014年12月15日下午2:57:12
 * @版本：V1.0
 *
 */
@Service("dayRouteService")
public class DayRouteServiceImpl extends BaseXlSpiServiceImpl<DayRouteDTO, DayRouteQO, DayRouteLocalService> 
																									implements DayRouteService {
	@Autowired
	private DayRouteLocalService dayRouteLocalService;
	@Autowired
	private LineLocalService lineLocalService;
	@Autowired
	private LineService lineService;
	
	@Override
	protected DayRouteLocalService getService() {
		return dayRouteLocalService;
	}

	@Override
	protected Class<DayRouteDTO> getDTOClass() {
		return DayRouteDTO.class;
	}
	@Override
	public DayRouteDTO createDayRoute(CreateDayRouteCommand command)throws SlfxXlException{
		DayRoute dayRoute =  dayRouteLocalService.createDayRoute(command);
		DayRouteDTO dayRouteDTO = new DayRouteDTO();
		if(dayRoute != null){
			dayRouteDTO = EntityConvertUtils.convertEntityToDto(dayRoute, DayRouteDTO.class);
		}
		XLUpdateLineMessageApiCommand apiCommand = new XLUpdateLineMessageApiCommand();
		apiCommand.setLineId(command.getLineID());
		apiCommand.setStatus(LineMessageConstants.AUDIT_DOWN);
		lineService.sendLineUpdateMessage(apiCommand);
		return dayRouteDTO;
	}

	@Override
	public Boolean modifyDayRoute(ModifyDayRouteCommand command) {
		Boolean flag = dayRouteLocalService.modifyDayRoute(command);
		if(flag){
		XLUpdateLineMessageApiCommand apiCommand = new XLUpdateLineMessageApiCommand();
		apiCommand.setLineId(command.getLineID());
		apiCommand.setStatus(LineMessageConstants.AUDIT_DOWN);
		lineService.sendLineUpdateMessage(apiCommand);
		}
		return flag;
	}

	@Override
	public Boolean deleteDayRoute(DeleteDayRouteCommand command) {
		
		DayRoute dayRoute = this.dayRouteLocalService.get(command.getId());
		XLUpdateLineMessageApiCommand apiCommand = new XLUpdateLineMessageApiCommand();
		apiCommand.setLineId(dayRoute.getLine().getId());
		apiCommand.setStatus(LineMessageConstants.AUDIT_DOWN);
		Boolean flag = dayRouteLocalService.deleteDayRoute(command);
		if(flag){
			lineService.sendLineUpdateMessage(apiCommand);
		}
		return flag;
	}


	

	
	

}

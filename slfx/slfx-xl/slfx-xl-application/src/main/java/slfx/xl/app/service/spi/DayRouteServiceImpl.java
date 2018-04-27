package slfx.xl.app.service.spi;

import hg.system.common.util.EntityConvertUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import slfx.xl.app.component.base.BaseXlSpiServiceImpl;
import slfx.xl.app.service.local.DayRouteLocalService;
import slfx.xl.app.service.local.LineLocalService;
import slfx.xl.domain.model.line.DayRoute;
import slfx.xl.pojo.command.line.XLUpdateLineMessageApiCommand;
import slfx.xl.pojo.command.route.CreateDayRouteCommand;
import slfx.xl.pojo.command.route.DeleteDayRouteCommand;
import slfx.xl.pojo.command.route.ModifyDayRouteCommand;
import slfx.xl.pojo.dto.route.DayRouteDTO;
import slfx.xl.pojo.exception.SlfxXlException;
import slfx.xl.pojo.qo.DayRouteQO;
import slfx.xl.pojo.system.LineMessageConstants;
import slfx.xl.spi.inter.DayRouteService;
import slfx.xl.spi.inter.LineService;

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

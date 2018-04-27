package slfx.xl.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.log.po.domainevent.DomainEvent;
import hg.log.repository.DomainEventRepository;
import hg.log.util.HgLogger;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import slfx.xl.app.dao.DayRouteDAO;
import slfx.xl.app.dao.LineDAO;
import slfx.xl.app.dao.LineImageDAO;
import slfx.xl.app.dao.LineSnapshotDAO;
import slfx.xl.domain.model.line.DayRoute;
import slfx.xl.domain.model.line.Line;
import slfx.xl.domain.model.line.LineImage;
import slfx.xl.domain.model.line.LineSnapshot;
import slfx.xl.pojo.command.route.CreateDayRouteCommand;
import slfx.xl.pojo.command.route.DeleteDayRouteCommand;
import slfx.xl.pojo.command.route.ModifyDayRouteCommand;
import slfx.xl.pojo.exception.SlfxXlException;
import slfx.xl.pojo.qo.DayRouteQO;
import slfx.xl.pojo.qo.LineImageQO;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：一日行程Service
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：luoyun
 * @创建时间：2014年12月15日下午2:57:51
 * @版本：V1.0
 *
 */
@Transactional
@Service
public class DayRouteLocalService extends BaseServiceImpl<DayRoute, DayRouteQO, DayRouteDAO>{

	@Autowired
	private DayRouteDAO dayRouteDAO;
	@Autowired
	private LineDAO lineDAO;
	@Autowired
	private DomainEventRepository domainEventRepository;
	@Autowired
	private LineSnapshotDAO lineSnapshotDAO;
	
	@Autowired
	private LineImageDAO lineImageDAO;
	
	
	@Override
	protected DayRouteDAO getDao() {
		return dayRouteDAO;
	}

	/**
	 * 
	 * @方法功能说明：新增一日行程
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月16日下午2:54:09
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:String 一日行程保存之后的ID
	 * @throws
	 */
	public DayRoute createDayRoute(CreateDayRouteCommand command) throws SlfxXlException{
		Line line = lineDAO.get(command.getLineID());
		if(line != null){
			
			Integer routeDays = line.getRouteInfo().getRouteDays();
			//添加的一日行程的所属的天数大于线路行程信息中设定的行程天数
			if(routeDays < command.getDays()){
				throw new SlfxXlException(SlfxXlException.CREATE_DAYROUTE_DAY_NOT_NOTVALID,"天数大于行程天数");
			}
			
			DayRoute dayRoute = new DayRoute();
			dayRoute.createDayRoute(command);
			dayRouteDAO.save(dayRoute);
			
			DomainEvent event = new DomainEvent("slfx.xl.domain.model.line.DayRoute","createDayRoute",JSON.toJSONString(command));
			domainEventRepository.save(event);
			
			//将线路状态改为初始状态未审核
			line.initLineStatus();
			lineDAO.update(line);
			DomainEvent lineEvent = new DomainEvent("slfx.xl.domain.model.line.Line","initLineStatus",JSON.toJSONString(line));
			domainEventRepository.save(lineEvent);
			
			//保存线路快照
			LineSnapshot lineSnapshot = new LineSnapshot();
			lineSnapshot.create(line);
			lineSnapshotDAO.save(lineSnapshot);
			DomainEvent lineSnapshotEvent = new DomainEvent("slfx.xl.domain.model.line.LineSnapshot","createLineSnapshot",JSON.toJSONString(lineSnapshot));
			domainEventRepository.save(lineSnapshotEvent);
			
			
			return dayRoute;
		}else{
			throw new SlfxXlException(SlfxXlException.RESULT_LINE_NOT_FOUND,"线路不存在");
		}
	}
	
	/**
	 * 
	 * @方法功能说明：修改一日行程信息
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月16日下午3:13:49
	 * @修改内容：
	 * @参数：@param command
	 * @return:Boolean
	 * @throws
	 */
	public Boolean modifyDayRoute(ModifyDayRouteCommand command){
		try{
			Line line = lineDAO.get(command.getLineID());
			if(line != null){
				
				DayRoute dayRoute = get(command.getId());
				dayRoute.modifyDayRoute(command);
				dayRouteDAO.update(dayRoute);
				DomainEvent event = new DomainEvent("slfx.xl.domain.model.line.DayRoute","modifyDayRoute",JSON.toJSONString(command));
				domainEventRepository.save(event);
				
				//将线路状态改为初始状态未审核
				line.initLineStatus();
				lineDAO.update(line);
				DomainEvent lineEvent = new DomainEvent("slfx.xl.domain.model.line.Line","initLineStatus",JSON.toJSONString(line));
				domainEventRepository.save(lineEvent);
				
				//保存线路快照
				LineSnapshot lineSnapshot = new LineSnapshot();
				lineSnapshot.create(line);
				lineSnapshotDAO.save(lineSnapshot);
				DomainEvent lineSnapshotEvent = new DomainEvent("slfx.xl.domain.model.line.LineSnapshot","createLineSnapshot",JSON.toJSONString(lineSnapshot));
				domainEventRepository.save(lineSnapshotEvent);
				
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			HgLogger.getInstance().error("luoyun", "DayRouteLocalService->modifyDayRoute->exception:" + HgLogger.getStackTrace(e));
			return false;
		}
	}
	
	/**
	 * 
	 * @方法功能说明：删除一日行程
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月16日下午3:16:29
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:Boolean
	 * @throws
	 */
	public Boolean deleteDayRoute(DeleteDayRouteCommand command){
		try{
			
			DayRoute dayRoute = get(command.getId());
			Line line = lineDAO.get(dayRoute.getLine().getId());
			if(line != null){
				//删除行程,先删除行程中的图片(外键关联),否则删除不成功
				LineImageQO qo=new LineImageQO();
				qo.setDayRouteId(command.getId());
			    List<LineImage> imageList=lineImageDAO.queryList(qo);
			    for(LineImage image :imageList){
			    	 lineImageDAO.delete(image);
			    }
				dayRouteDAO.delete(dayRoute);
				DomainEvent event = new DomainEvent("slfx.xl.domain.model.line.DayRoute","deleteDayRoute",JSON.toJSONString(command));
				domainEventRepository.save(event);
				
				//将线路状态改为初始状态未审核
				line.initLineStatus();
				lineDAO.update(line);
				DomainEvent lineEvent = new DomainEvent("slfx.xl.domain.model.line.Line","initLineStatus",JSON.toJSONString(line));
				domainEventRepository.save(lineEvent);
				
				//保存线路快照
				LineSnapshot lineSnapshot = new LineSnapshot();
				lineSnapshot.create(line);
				lineSnapshotDAO.save(lineSnapshot);
				DomainEvent lineSnapshotEvent = new DomainEvent("slfx.xl.domain.model.line.LineSnapshot","createLineSnapshot",JSON.toJSONString(lineSnapshot));
				domainEventRepository.save(lineSnapshotEvent);
				
				return true;
				
			}else{
				return false;
			}
			
		}catch(Exception e){
			HgLogger.getInstance().error("luoyun", "DayRouteLocalService->deleteDayRoute->exception:" + HgLogger.getStackTrace(e));
			return false;
		}
	}
	
}

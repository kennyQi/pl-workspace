package plfx.xl.app.service.spi;

import hg.log.util.HgLogger;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plfx.xl.app.common.util.EntityConvertUtils;
import plfx.xl.app.component.base.BaseXlSpiServiceImpl;
import plfx.xl.app.service.local.LineDealerLocalService;
import plfx.xl.app.service.local.LineLocalService;
import plfx.xl.app.service.local.LineSnapshotLocalService;
import plfx.xl.domain.model.line.Line;
import plfx.xl.domain.model.line.LineSnapshot;
import plfx.xl.pojo.command.line.AuditLineCommand;
import plfx.xl.pojo.command.line.CopyLineCommand;
import plfx.xl.pojo.command.line.CreateLineCommand;
import plfx.xl.pojo.command.line.GroundingLineCommand;
import plfx.xl.pojo.command.line.ModifyLineCommand;
import plfx.xl.pojo.command.line.ModifyLineMinPriceCommand;
import plfx.xl.pojo.command.line.UnderCarriageLineCommand;
import plfx.xl.pojo.command.line.XLUpdateLineMessageApiCommand;
import plfx.xl.pojo.command.route.ModifyLineRouteInfoCommand;
import plfx.xl.pojo.dto.line.LineDTO;
import plfx.xl.pojo.message.SynchronizationLineMessage;
import plfx.xl.pojo.qo.LineQO;
import plfx.xl.pojo.qo.LineSnapshotQO;
import plfx.xl.pojo.system.LineConstants;
import plfx.xl.pojo.system.LineMessageConstants;
import plfx.xl.spi.inter.LineService;
import plfx.xl.spi.inter.LineSnapshotService;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：平台订单SERVICE实现
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：renfeng
 * @创建时间：2014年7月31日下午4:47:08
 * @版本：V1.0
 * 
 */
@Service("lineService")
public class LineServiceImpl extends
		BaseXlSpiServiceImpl<LineDTO, LineQO, LineLocalService> implements
		LineService {

	@Resource
	private LineLocalService lineLocalService;

	@Resource
	private LineDealerLocalService lineDealerLocalService;
	
	@Autowired
	private LineSnapshotLocalService lineSnapshotLocalService;
	@Autowired
	private LineSnapshotService lineSnapshotService;
	@Autowired
	private RabbitTemplate template;

	@Override
	public Boolean createLine(CreateLineCommand command) {
		return lineLocalService.createLine(command);
	}

	@Override
	public Boolean modifyLineRouteInfo(ModifyLineRouteInfoCommand command) {
		Boolean flag = lineLocalService.modifyLineRouteInfo(command);
		if(flag){
			XLUpdateLineMessageApiCommand apiCommand = new XLUpdateLineMessageApiCommand();
			apiCommand.setLineId(command.getLineID());
			apiCommand.setStatus(LineMessageConstants.AUDIT_DOWN);
			sendLineUpdateMessage(apiCommand);
		}
		return flag;
	}

	public Boolean modifyLine(ModifyLineCommand command) {
		Boolean flag = lineLocalService.modifyLine(command);
		if(flag){
			XLUpdateLineMessageApiCommand apiCommand = new XLUpdateLineMessageApiCommand();
			apiCommand.setLineId(command.getLineID());
			apiCommand.setStatus(LineMessageConstants.AUDIT_DOWN);
			sendLineUpdateMessage(apiCommand);
		}
		return flag;
	}

	@Override
	public boolean auditLine(AuditLineCommand command) {
		Boolean flag = lineLocalService.auditLine(command);
//		if (flag) {
//			if (command.getStatus() != null) {
//				if (command.getStatus() == 4) {
//					XLUpdateLineMessageApiCommand apiCommand = new XLUpdateLineMessageApiCommand();
//					apiCommand.setLineId(command.getId());
//					apiCommand
//							.setStatus(LineMessageConstants.AUDIT_UP);
//					sendLineUpdateMessage(apiCommand);
//				} else if (command.getStatus() == 5) {
//					XLUpdateLineMessageApiCommand apiCommand = new XLUpdateLineMessageApiCommand();
//					apiCommand.setLineId(command.getId());
//					apiCommand
//							.setStatus(LineMessageConstants.AUDIT_DOWN);
//					sendLineUpdateMessage(apiCommand);
//				}
//			}
//		}
		return flag;
	}

	@Override
	public boolean groundingLine(GroundingLineCommand command) {
		boolean bool = lineLocalService.groundingLine(command);
		//更新线路快照
		LineDTO lineDTO = new LineDTO();
		lineDTO.setId(command.getLineID());
		lineSnapshotService.createLineSnapshot(lineDTO);
		HgLogger.getInstance().info("yuqz", "LineServiceImpl->groundingLine->上架线路更新线路快照完成");
		XLUpdateLineMessageApiCommand apiCommand = new XLUpdateLineMessageApiCommand();
		apiCommand.setLineId(command.getLineID());
		apiCommand.setStatus(LineMessageConstants.AUDIT_UP);
		sendLineUpdateMessage(apiCommand);
		
		return bool;
	}

	@Override
	public boolean underCarriageLine(UnderCarriageLineCommand command) {
		boolean bool = lineLocalService.underCarriageLine(command);
		XLUpdateLineMessageApiCommand apiCommand = new XLUpdateLineMessageApiCommand();
		apiCommand.setLineId(command.getLineID());
		apiCommand.setStatus(LineMessageConstants.AUDIT_DOWN);
		sendLineUpdateMessage(apiCommand);
		return bool;
	}

	@Override
	public boolean copyLine(CopyLineCommand command) {
		return lineLocalService.copyLine(command);
	}

	/*---------------------admin使用上面，shop使用下面----------------------*/

	@Override
	public List<LineDTO> shopQueryLineList(LineQO qo) {
		if (qo != null) {
			qo.setStatus(LineConstants.AUDIT_UP);
		}
		List<Line> lines = lineLocalService.shopQueryList(qo);
		HgLogger.getInstance().info("yuqz", "LineServiceImpl->shopQueryLineList->lines：" + JSON.toJSONString(lines));
		List<LineDTO> lineList = null;
		try {
			lineList = EntityConvertUtils.convertEntityToDtoList(lines,
					getDTOClass());
			HgLogger.getInstance().info("yuqz", "LineServiceImpl->shopQueryLineList->lineList：" + JSON.toJSONString(lineList));
		} catch (Exception e) {
			HgLogger.getInstance().error(
					"tandeng",
					"LineServiceImpl->[线路entity转换dto异常]:"
							+ HgLogger.getStackTrace(e));
		}
		for (LineDTO lineDTO : lineList) {
			LineSnapshotQO snapshotQO = new LineSnapshotQO();
			snapshotQO.setLineID(lineDTO.getId());
			snapshotQO.setIsNew(true);
			HgLogger.getInstance().info("yuqz", "LineServiceImpl->shopQueryLineList->查询最新快照snapshotQO：" + JSON.toJSONString(snapshotQO));
			LineSnapshot lineSnapshot = lineSnapshotLocalService.queryUnique(snapshotQO);
			HgLogger.getInstance().info("yuqz", "LineServiceImpl->shopQueryLineList->查询最新快照snapshotQO结束lineSnapshot：" + JSON.toJSONString(lineSnapshot));
			if(lineSnapshot != null){
				lineDTO.setLineSnapshotId(lineSnapshot.getId());
			}
		}

		return lineList;
	}

	public Integer shopQueryLineTotalCount(LineQO qo) {
		if (qo != null) {
			qo.setStatus(LineConstants.AUDIT_UP);
			qo.setPageNo(null);
		}
		Integer count = lineLocalService.queryCount(qo);
		return count;
	}

	@Override
	protected LineLocalService getService() {
		return lineLocalService;
	}

	@Override
	protected Class<LineDTO> getDTOClass() {
		return LineDTO.class;
	}

	@Override
	public void sendLineUpdateMessage(XLUpdateLineMessageApiCommand apiCommand) {
		
		SynchronizationLineMessage baseAmqpMessage=new SynchronizationLineMessage();
		
		baseAmqpMessage.setContent(apiCommand);
		baseAmqpMessage.setType(Integer.parseInt(LineMessageConstants.UPDATE_DATE_SALE_PRICE));
		baseAmqpMessage.setSendDate(new Date());
		baseAmqpMessage.setArgs(null);
		try{
//			template.convertAndSend("plfx.synchronizationLine",baseAmqpMessage);
			template.convertAndSend("plfx.xl.synchronizationLine",baseAmqpMessage);
			
			HgLogger.getInstance().info("yuqz","LineServiceImpl->sendLineUpdateMessage-成功:"+ JSON.toJSONString(baseAmqpMessage));
		}catch(Exception e){
			HgLogger.getInstance().error("yuqz","LineServiceImpl->sendLineUpdateMessage-失败:"+ JSON.toJSONString(baseAmqpMessage));
		}
		
	}

	@Override
	public void modifyLineMinPrice(ModifyLineMinPriceCommand modifyLineMinPriceCommand) {
		try{
			lineLocalService.modifyLineMinPrice(modifyLineMinPriceCommand);
//			sendModifyLineMinPriceMessage(modifyLineMinPriceCommand);
		}catch(Exception e){
			HgLogger.getInstance().error("yuqz","LineServiceImpl->modifyLineMinPrice-更新团期最低价格失败");
		}
		
	}

	/**
	 * @方法功能说明：发送修改线路团期最低价格通知
	 * @修改者名字：yuqz
	 * @修改时间：2015年5月5日下午5:47:14
	 * @修改内容：
	 * @参数：
	 * @return:
	 * @throws
	 */
	@Override
	public void sendModifyLineMinPriceMessage(ModifyLineMinPriceCommand modifyLineMinPriceCommand) {
		SynchronizationLineMessage baseAmqpMessage=new SynchronizationLineMessage();
		XLUpdateLineMessageApiCommand apiCommand = new XLUpdateLineMessageApiCommand();
		apiCommand.setLineId(modifyLineMinPriceCommand.getLineId());
		apiCommand.setStatus(LineMessageConstants.UPDATE_DATE_SALE_MINPRICE);
		baseAmqpMessage.setContent(apiCommand);
		baseAmqpMessage.setType(Integer.parseInt(LineMessageConstants.UPDATE_DATE_SALE_MINPRICE));//发送修改线路団期最低价格通知
		baseAmqpMessage.setSendDate(new Date());
		baseAmqpMessage.setArgs(null);
		try{
			template.convertAndSend("plfx.xl.modifyLineMinPriceMessage",baseAmqpMessage);			
			
			HgLogger.getInstance().info("tandeng","LineServiceImpl->sendLineUpdateMessage-成功:"+ JSON.toJSONString(baseAmqpMessage));
		}catch(Exception e){
			HgLogger.getInstance().error("tandeng","LineServiceImpl->sendLineUpdateMessage-失败:"+ JSON.toJSONString(baseAmqpMessage));
		}
	}

	

}

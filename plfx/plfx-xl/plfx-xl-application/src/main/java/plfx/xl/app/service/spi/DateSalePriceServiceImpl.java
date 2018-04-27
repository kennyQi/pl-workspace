package plfx.xl.app.service.spi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import plfx.xl.app.component.base.BaseXlSpiServiceImpl;
import plfx.xl.app.service.local.DateSalePriceLocalService;
import plfx.xl.domain.model.line.DateSalePrice;
import plfx.xl.pojo.command.price.BatchModifyDateSalePriceCommand;
import plfx.xl.pojo.command.price.CreateDateSalePriceCommand;
import plfx.xl.pojo.command.price.ModifyDateSalePriceCommand;
import plfx.xl.pojo.dto.line.LineDTO;
import plfx.xl.pojo.dto.price.DateSalePriceDTO;
import plfx.xl.pojo.qo.DateSalePriceQO;
import plfx.xl.pojo.qo.LineQO;
import plfx.xl.spi.inter.DateSalePriceService;
import plfx.xl.spi.inter.LineService;
import plfx.xl.spi.inter.LineSnapshotService;

/**
 * 
 * @类功能说明：价格日历Service实现类
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：luoyun
 * @创建时间：2014年12月17日下午4:45:58
 * @版本：V1.0
 *
 */
@Service("xlDateSalePriceService")
public class DateSalePriceServiceImpl extends BaseXlSpiServiceImpl<DateSalePriceDTO, DateSalePriceQO, DateSalePriceLocalService> 
											implements DateSalePriceService{

	@Autowired
	@Qualifier("dateSalePriceLocalService_xl")
	private DateSalePriceLocalService dateSalePriceLocalService;
	@Autowired
	private LineService lineService;
	@Autowired
	private LineSnapshotService lineSnapshotService;
	@Override
	protected DateSalePriceLocalService getService() {
		return dateSalePriceLocalService;
	}

	@Override
	protected Class<DateSalePriceDTO> getDTOClass() {
		return DateSalePriceDTO.class;
	}

	@Override
	public Boolean modifyDateSalePrice(ModifyDateSalePriceCommand command) {
		Boolean flag = dateSalePriceLocalService.modifyDateSalePrice(command);
		if(flag){
			DateSalePrice dateSalePrice = dateSalePriceLocalService.get(command.getId());
			if(dateSalePrice != null && dateSalePrice.getLine().getStatusInfo().getStatus() != 4){
				LineDTO lineDTO = new LineDTO();
				lineDTO.setId(dateSalePrice.getLine().getId());
				lineSnapshotService.createLineSnapshot(lineDTO);
				//发送消息
//				XLUpdateLineMessageApiCommand apiCommand = new XLUpdateLineMessageApiCommand();
//				apiCommand.setLineId(dateSalePrice.getLine().getId());
//				apiCommand.setStatus(LineMessageConstants.UPDATE_DATE_SALE_PRICE);
//				lineService.sendLineUpdateMessage(apiCommand);
			}
			
		}
		return flag;
	}

	@Override
	public Boolean batchModifyDateSalePrice(
			BatchModifyDateSalePriceCommand command) {
		Boolean flag = dateSalePriceLocalService.batchModifyDateSalePrice(command);
		if(flag){
			LineQO qo = new LineQO();
			qo.setId(command.getLineID());
			LineDTO line = lineService.queryUnique(qo);
			if(line != null && (line.getStatusInfo().getStatus() == 4 || line.getStatusInfo().getStatus() == 5)){
				LineDTO lineDTO = new LineDTO();
				lineDTO.setId(command.getLineID());
				lineSnapshotService.createLineSnapshot(lineDTO);
//				XLUpdateLineMessageApiCommand apiCommand = new XLUpdateLineMessageApiCommand();
//				apiCommand.setLineId(command.getLineID());
//				apiCommand.setStatus(LineMessageConstants.UPDATE_DATE_SALE_PRICE);
//				lineService.sendLineUpdateMessage(apiCommand);
			}
		}
		return flag;
	}

	@Override
	public Boolean createDateSalePrice(CreateDateSalePriceCommand command) {
		Boolean flag = dateSalePriceLocalService.createDateSalePrice(command);
		if(flag){
			LineQO qo = new LineQO();
			qo.setId(command.getLineID());
			LineDTO line = lineService.queryUnique(qo);
			if(line != null && line.getStatusInfo().getStatus() != 4){
				LineDTO lineDTO = new LineDTO();
				lineDTO.setId(command.getLineID());
				lineSnapshotService.createLineSnapshot(lineDTO);
//				XLUpdateLineMessageApiCommand apiCommand = new XLUpdateLineMessageApiCommand();
//				apiCommand.setLineId(command.getLineID());
//				apiCommand.setStatus(LineMessageConstants.UPDATE_DATE_SALE_PRICE);
//				lineService.sendLineUpdateMessage(apiCommand);
			}
		}
		return flag;
	}
	
	public Double countDailyAverPrice(DateSalePriceQO dateSalePriceQO){
		return dateSalePriceLocalService.countDailyAverPrice(dateSalePriceQO);
	}

	@Override
	public void deleteDateSalePrice(String id) {
		dateSalePriceLocalService.deleteById(id);
	}

}

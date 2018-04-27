package slfx.xl.app.task;

import hg.common.util.DateUtil;
import hg.log.util.HgLogger;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import slfx.xl.pojo.command.line.ModifyLineMinPriceCommand;
import slfx.xl.pojo.dto.line.LineDTO;
import slfx.xl.pojo.dto.price.DateSalePriceDTO;
import slfx.xl.pojo.qo.DateSalePriceQO;
import slfx.xl.pojo.qo.LineQO;
import slfx.xl.spi.inter.DateSalePriceService;
import slfx.xl.spi.inter.LineService;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：定时更新团期最低价格
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年4月14日下午5:08:11
 * @版本：V1.0
 *
 */
public class UpdateXlMinPriceTask {
	
	@Autowired
	private LineService lineService;
	
	@Autowired
	private DateSalePriceService xlDateSalePriceService;
	
	public void run(){
		long t1 = System.currentTimeMillis();
		HgLogger.getInstance().info("yuqz", "UpdateXlMinPriceTask->定时更新团期最低价格开始>>>>>>t1:" + t1);
		LineQO lineQO = new LineQO();
		List<LineDTO> lineDTOList = lineService.queryList(lineQO);
		long t2 = System.currentTimeMillis();
		HgLogger.getInstance().info("yuqz", "UpdateXlMinPriceTask->定时更新团期最低价格>>>>>>t2:" + t2);
		HgLogger.getInstance().info("yuqz", "UpdateXlMinPriceTask->定时更新团期最低价格:lineDTOList:" + JSON.toJSONString(lineDTOList));
		for(LineDTO lineDTO : lineDTOList){
			HgLogger.getInstance().info("yuqz", "UpdateXlMinPriceTask->定时更新团期最低价格:lineDTO:" + JSON.toJSONString(lineDTO));
			DateSalePriceQO dateSalePriceQO_null = new DateSalePriceQO();
			dateSalePriceQO_null.setLineID(lineDTO.getId());
			dateSalePriceQO_null.setSaleDateAsc(true);
			dateSalePriceQO_null.setBeginDate(DateUtil.formatDate(new Date()));
			List<DateSalePriceDTO> dateSalePriceList = xlDateSalePriceService.queryList(dateSalePriceQO_null);
			HgLogger.getInstance().info("yuqz", "UpdateXlMinPriceTask->定时更新团期最低价格:dateSalePriceList:" + JSON.toJSONString(dateSalePriceList));
			double minPrice = 0;
			for(DateSalePriceDTO dto : dateSalePriceList){
				if(minPrice == 0 && dto.getAdultPrice() != 0){
					minPrice = dto.getAdultPrice();
				}
				if(minPrice > dto.getAdultPrice()){
					minPrice = dto.getAdultPrice();
				}
			}
			if(null == lineDTO.getMinPrice() || lineDTO.getMinPrice() != minPrice){
				ModifyLineMinPriceCommand modifyLineMinPriceCommand = new ModifyLineMinPriceCommand();
				modifyLineMinPriceCommand.setLineId(lineDTO.getId());
				modifyLineMinPriceCommand.setMinPrice(minPrice);
				HgLogger.getInstance().info("yuqz", "UpdateXlMinPriceTask->定时更新团期最低价格:modifyLineMinPriceCommand:" + JSON.toJSONString(modifyLineMinPriceCommand));
				lineService.modifyLineMinPrice(modifyLineMinPriceCommand);
				//更新团期最低价格发送通知
				lineService.sendModifyLineMinPriceMessage(modifyLineMinPriceCommand);
				HgLogger.getInstance().info("yuqz", "UpdateXlMinPriceTask->定时更新团期最低价格结束:线路id:" + lineDTO.getId());
			}
		}
		long t3 = System.currentTimeMillis();
		HgLogger.getInstance().info("yuqz", "UpdateXlMinPriceTask->定时更新团期最低价格结束花费时间>>>>>>:" + (t3 - t1));
	}
}

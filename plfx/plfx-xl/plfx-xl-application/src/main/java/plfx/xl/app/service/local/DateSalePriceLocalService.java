package plfx.xl.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.log.po.domainevent.DomainEvent;
import hg.log.repository.DomainEventRepository;
import hg.log.util.HgLogger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import plfx.xl.app.dao.DateSalePriceDAO;
import plfx.xl.app.dao.LineDAO;
import plfx.xl.domain.model.line.DateSalePrice;
import plfx.xl.domain.model.line.Line;
import plfx.xl.pojo.command.price.BatchModifyDateSalePriceCommand;
import plfx.xl.pojo.command.price.CreateDateSalePriceCommand;
import plfx.xl.pojo.command.price.ModifyDateSalePriceCommand;
import plfx.xl.pojo.qo.DateSalePriceQO;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：价格日历LocalService
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：luoyun
 * @创建时间：2014年12月17日下午4:38:22
 * @版本：V1.0
 *
 */
@Service("dateSalePriceLocalService_xl")
@Transactional
public class DateSalePriceLocalService extends BaseServiceImpl<DateSalePrice, DateSalePriceQO, DateSalePriceDAO>{

	@Autowired
	private DateSalePriceDAO dateSalePriceDAO;
	@Autowired
	private DomainEventRepository domainEventRepository;
	@Autowired
	private LineDAO lineDAO;
	
	@Override
	protected DateSalePriceDAO getDao() {
		return dateSalePriceDAO;
	}
	
	/**
	 * 
	 * @方法功能说明：修改单天团期
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月18日下午1:59:29
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public Boolean modifyDateSalePrice(ModifyDateSalePriceCommand command){
		
		try{
			
			DateSalePrice dateSalePrice = get(command.getId());
			if(dateSalePrice != null){
				dateSalePrice.modifyDateSalePrice(command);
				dateSalePriceDAO.update(dateSalePrice);
				
				DomainEvent event = new DomainEvent("slfx.xl.domain.model.line.DateSalePrice","modifyDateSalePrice",JSON.toJSONString(command));
				domainEventRepository.save(event);
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			HgLogger.getInstance().error("luoyun", "DateSalePriceLocalService->modifyDateSalePrice->exception:" + HgLogger.getStackTrace(e));
			return false;
		}
		
	}
	
	/**
	 * 
	 * @方法功能说明：批量修改多天团期
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月18日下午5:17:36
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public Boolean batchModifyDateSalePrice(BatchModifyDateSalePriceCommand command){
		
		try{
			
			Line line = lineDAO.get(command.getLineID());
			if(line == null){
				HgLogger.getInstance().error("luoyun", "DateSalePriceLocalService->batchModifyDateSalePrice->exception:线路 " 
						+ command.getLineID() + "不存在");
				return false;
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date firstDay = format.parse(command.getBeginDate());
			Date lastDay = format.parse(command.getEndDate());
			Calendar calendar = Calendar.getInstance();
			
			//新增团期列表
			List<DateSalePrice> saveList = new ArrayList<DateSalePrice>();
			//更新团期列表
			List<DateSalePrice> updateList = new ArrayList<DateSalePrice>(); 
			
			Date currentDate = firstDay;
			calendar.setTime(currentDate);
			while(currentDate.before(lastDay) || currentDate.equals(lastDay)){
				
				//日期符合要求的星期几则进行处理
				String DayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) + "";
				if(command.getWeekDay().contains(DayOfWeek) ){
					
					String currentDateStr = format.format(currentDate);
					DateSalePriceQO dateSalePriceQO = new DateSalePriceQO();
					dateSalePriceQO.setLineID(command.getLineID());
					dateSalePriceQO.setSaleDate(currentDateStr);
					DateSalePrice dateSalePrice = dateSalePriceDAO.queryUnique(dateSalePriceQO);
					//不存在则新增
					if(dateSalePrice == null){
						dateSalePrice = new DateSalePrice();
						CreateDateSalePriceCommand createComamnd = new CreateDateSalePriceCommand();
						createComamnd.setLineID(command.getLineID());
						createComamnd.setAdultPrice(command.getAdultPrice());
						createComamnd.setChildPrice(command.getChildPrice());
						createComamnd.setNumber(command.getNumber());
						createComamnd.setSaleDate(currentDateStr);
						dateSalePrice.createDateSalePrice(createComamnd);
						dateSalePrice.setLine(line);
						saveList.add(dateSalePrice);
					}else{ //存在则更新
						dateSalePrice.batchModifyDateSalePrice(command);
						updateList.add(dateSalePrice);
					}
					
				}
				calendar.add(Calendar.DATE,1);
				currentDate =  calendar.getTime();
				
			}
			
			dateSalePriceDAO.saveList(saveList);
			dateSalePriceDAO.updateList(updateList);
			
			DomainEvent event = new DomainEvent("slfx.xl.domain.model.line.DateSalePrice","batchModifyDateSalePrice",JSON.toJSONString(command));
			domainEventRepository.save(event);
			return true;
		}catch(Exception e){
			HgLogger.getInstance().error("luoyun", "DateSalePriceLocalService->batchModifyDateSalePrice->exception:" + HgLogger.getStackTrace(e));
			return false;
		}
		
		
	}
	
	/**
	 * 
	 * @方法功能说明：新增单天团期
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月19日下午4:53:06
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public Boolean createDateSalePrice(CreateDateSalePriceCommand command){
		
		try{
			Line line = lineDAO.get(command.getLineID());
			if(line != null){
				
				DateSalePrice dateSalePrice = new DateSalePrice();
				dateSalePrice.setLine(line);
				dateSalePrice.createDateSalePrice(command);
				dateSalePriceDAO.save(dateSalePrice);
				
				DomainEvent event = new DomainEvent("slfx.xl.domain.model.line.DateSalePrice","createDateSalePrice",JSON.toJSONString(command));
				domainEventRepository.save(event);
				return true;
			}else{
				return false;
			}
			
		}catch(Exception e){
			HgLogger.getInstance().error("luoyun", "DateSalePriceLocalService->createDateSalePrice->exception:" + HgLogger.getStackTrace(e));
			return false;
		}
	}
	
	/**
	 * 
	 * @方法功能说明：计算线路某天的平均价格
	 * @修改者名字：luoyun
	 * @修改时间：2015年1月5日下午5:16:38
	 * @修改内容：
	 * @参数：@param dateSalePriceQO
	 * @参数：@return
	 * @return:Double
	 * @throws
	 */
	public Double countDailyAverPrice(DateSalePriceQO dateSalePriceQO){
		DateSalePrice dateSalePrice = dateSalePriceDAO.queryUnique(dateSalePriceQO);
		Double averPrice = 0.0;
		if(dateSalePrice != null){
			Double tempAdultPrice = dateSalePrice.getAdultPrice() == null?0.00d:dateSalePrice.getAdultPrice();
			Double tempChildPrice = dateSalePrice.getChildPrice() == null?0.00d:dateSalePrice.getChildPrice();
			averPrice = (tempAdultPrice+tempChildPrice)/2;
		}
		return averPrice;
		
	}

}

package plfx.admin.controller.traveline;

import hg.common.util.DateUtil;
import hg.common.util.DwzJsonResultUtil;
import hg.log.util.HgLogger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import plfx.xl.pojo.command.line.ModifyLineMinPriceCommand;
import plfx.xl.pojo.command.price.BatchModifyDateSalePriceCommand;
import plfx.xl.pojo.command.price.CreateDateSalePriceCommand;
import plfx.xl.pojo.command.price.ModifyDateSalePriceCommand;
import plfx.xl.pojo.dto.line.LineDTO;
import plfx.xl.pojo.dto.price.DateSalePriceDTO;
import plfx.xl.pojo.dto.price.PriceDTO;
import plfx.xl.pojo.qo.DateSalePriceQO;
import plfx.xl.pojo.qo.LineQO;
import plfx.xl.spi.inter.DateSalePriceService;
import plfx.xl.spi.inter.LineService;
import plfx.xl.spi.inter.LineSupplierService;

import com.alibaba.fastjson.JSON;

/**
 *@类功能说明：线路团期维护controller
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年12月10日上午10:15:00
 *
 */
@Controller
@RequestMapping("/traveline/price")
public class DateSalePriceController {

	@Autowired
	private DateSalePriceService xlDateSalePriceService;
	@Autowired
	private LineSupplierService lineSupplierService;
	@Autowired
	private LineService lineService;
	
	/**
	 * 跳转到团期维护页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String toPriceList(HttpServletRequest request,HttpServletResponse response,Model model,
			@RequestParam(value="id",required=false) String lineID){
		model.addAttribute("lineID", lineID);
		return "/traveline/price/price_list.html";
	}
	
	
	/**
	 * @方法功能说明：跳转到团期日历页面
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月18日上午10:09:41
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/datesaleprice")
	public String toDateSalePrice(HttpServletRequest request,HttpServletResponse response,Model model,
			@RequestParam(value="lineID",required=false) String lineID){
		model.addAttribute("lineID", lineID);
		return "/traveline/price/datesaleprice.html";
	}
	
	/**
	 * 查询线路团期列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/query")
	@ResponseBody
	public String queryDateSalePriceList(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="lineID",required=false) String lineID,
			@RequestParam(value="month",required=false) Integer month,
			@RequestParam(value="year",required=false) Integer year){
		
		try{
			
			List<PriceDTO> priceDTOList = new ArrayList<PriceDTO>();
			
			//年月不存在 则默认显示本月的团期数据
			Calendar calendar = Calendar.getInstance();
			if(year != 0){
				calendar.set(Calendar.YEAR, year);
			}
			if(month != -1){
				calendar.set(Calendar.MONTH, month);
			}
			
			//获取当月的第一天
			calendar.set(Calendar.DAY_OF_MONTH,1);
			Date firstDay  = calendar.getTime();
			//获取当月的最后一天
			calendar.add(Calendar.MONDAY,1);
			calendar.add(Calendar.DAY_OF_MONTH,-1);
			Date lastDay =  calendar.getTime();
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
			Date currentDate = firstDay;
			calendar.setTime(currentDate);
			while(currentDate.before(lastDay) || currentDate.equals(lastDay)){
				DateSalePriceQO dateSalePriceQO = new DateSalePriceQO();
				dateSalePriceQO.setLineID(lineID);
				String currentDateStr = format.format(currentDate);
				dateSalePriceQO.setSaleDate(currentDateStr);
				
				DateSalePriceDTO dateSalePriceDTO = xlDateSalePriceService.queryUnique(dateSalePriceQO);
				PriceDTO priceDTO = new PriceDTO();
				priceDTO.setSaleDate(currentDateStr);
				if(dateSalePriceDTO != null){
					priceDTO.setId(dateSalePriceDTO.getId());
					priceDTO.setAdultPrice(dateSalePriceDTO.getAdultPrice());
					priceDTO.setChildPrice(dateSalePriceDTO.getChildPrice());
					priceDTO.setLineID(dateSalePriceDTO.getLine().getId());
					priceDTO.setNumber(dateSalePriceDTO.getNumber());
				}else{ //不存在则价格人数显示0
					priceDTO.setAdultPrice(0.00);
					priceDTO.setChildPrice(0.00);
					priceDTO.setNumber(0);
				}
				priceDTOList.add(priceDTO);
				calendar.add(Calendar.DATE,1);
				currentDate =  calendar.getTime();
			}
			
			return JSON.toJSONString(priceDTOList);
			
		}catch(Exception e){
			HgLogger.getInstance().error("luoyun", "旅游线路管理-团期维护-查询团期失败" + HgLogger.getStackTrace(e));
			return "";
		}
		
	}
	
	
	/**
	 * 
	 * @方法功能说明：跳转到修改单个团期信息页面
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月18日上午11:07:41
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param dateSalePriceID
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/toedit")
	public String toEditDateSalePrice(HttpServletRequest request,HttpServletResponse response,Model model,
			@RequestParam(value="id",required=false) String dateSalePriceID){
		
		try{
			DateSalePriceQO dateSalePriceQO = new DateSalePriceQO();
			dateSalePriceQO.setDateSalePriceID(dateSalePriceID);
			DateSalePriceDTO dateSalePriceDTO = xlDateSalePriceService.queryUnique(dateSalePriceQO);
			model.addAttribute("priceDTO", dateSalePriceDTO);
		}catch(Exception e){
			HgLogger.getInstance().error("luoyun", "旅游线路管理-团期维护-跳转到修改单个价格页面失败" + HgLogger.getStackTrace(e));
		}
		
		return "/traveline/price/price_edit.html";
		
	}
	
	
	/**
	 * 
	 * @方法功能说明：修改单个团期信息
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月18日下午2:05:48
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public String editDateSalePrice(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute ModifyDateSalePriceCommand command){
		
		try{
			String message = "";
			String statusCode = "";
			//根据价格日历中的一天的id获取lineId
			DateSalePriceQO dateSalePriceQO = new DateSalePriceQO();
			dateSalePriceQO.setId(command.getId());
			DateSalePriceDTO dateSalePriceDTO = xlDateSalePriceService.queryUnique(dateSalePriceQO);
			LineQO lineQO = new LineQO();
			lineQO.setId(dateSalePriceDTO.getLine().getId());
			LineDTO lineDTO = lineService.queryUnique(lineQO);
			if(null != lineDTO){
				if(null != lineDTO.getStatusInfo() && lineDTO.getStatusInfo().getStatus() == 4){
					message = "新增失败，上架的线路不允许修改団期";
					return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_300, message,
							DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "price");
				}
				Boolean result = xlDateSalePriceService.modifyDateSalePrice(command);
				
				if(result){
					message = "修改成功";
					statusCode = DwzJsonResultUtil.STATUS_CODE_200;
				}else{
					message = "修改失败";
					statusCode = DwzJsonResultUtil.STATUS_CODE_300;
					HgLogger.getInstance().error("luoyun", "旅游线路管理-团期维护-修改单天团期失败" + JSON.toJSONString(command));
				}
				DateSalePriceQO dateSalePriceQO_null = new DateSalePriceQO();
				dateSalePriceQO_null.setLineID(dateSalePriceDTO.getLine().getId());
				dateSalePriceQO_null.setSaleDateAsc(true);
				dateSalePriceQO_null.setBeginDate(DateUtil.formatDate(new Date()));
				List<DateSalePriceDTO> dateSalePriceList = xlDateSalePriceService.queryList(dateSalePriceQO_null);
				double minPrice = 0;
				for(DateSalePriceDTO dto : dateSalePriceList){
					if(minPrice == 0 && dto.getAdultPrice() != 0){
						minPrice = dto.getAdultPrice();
					}
					if(minPrice > dto.getAdultPrice()){
						minPrice = dto.getAdultPrice();
					}
				}
				//如果现有的团期最低价格和之后的不相等就改动
				if(null == lineDTO.getMinPrice() || lineDTO.getMinPrice() != minPrice){
					ModifyLineMinPriceCommand modifyLineMinPriceCommand = new ModifyLineMinPriceCommand();
					modifyLineMinPriceCommand.setLineId(dateSalePriceDTO.getLine().getId());
					modifyLineMinPriceCommand.setMinPrice(minPrice);
					HgLogger.getInstance().error("yuqz", "旅游线路管理-团期维护-新增单天团期-修改団期最低价格" + JSON.toJSONString(modifyLineMinPriceCommand));
					try{
						lineService.modifyLineMinPrice(modifyLineMinPriceCommand);
					}catch(Exception e){
						e.printStackTrace();
						HgLogger.getInstance().error("yuqz", "旅游线路管理-团期维护-修改団期最低价失败" + HgLogger.getStackTrace(e));
						return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_300, "修改団期最低价失败",
								DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "price");
					}
				}
				return DwzJsonResultUtil.createJsonString(statusCode, message,DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "price");
			}else{
				HgLogger.getInstance().info("yuqz", "旅游线路管理-团期维护-修改单天团期失败->线路不存在");
				return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_300, "修改失败",
						DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "price");
			}
		}catch(Exception e){
			HgLogger.getInstance().error("luoyun", "旅游线路管理-团期维护-修改单天团期失败" + HgLogger.getStackTrace(e));
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_300, "修改失败",
					DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "price");
		}
		
	}
	
	/**
	 * 
	 * @方法功能说明：批量修改团期
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月18日下午5:27:11
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/batchedit")
	@ResponseBody
	public String batchEditDateSalePrice(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute BatchModifyDateSalePriceCommand command){
		
		try{
			String message = "";
			String statusCode = "";
			LineQO lineQO = new LineQO();
			lineQO.setId(command.getLineID());
			LineDTO lineDTO = lineService.queryUnique(lineQO);
			if(null != lineDTO){
				if(null != lineDTO.getStatusInfo() && lineDTO.getStatusInfo().getStatus() == 4){
					message = "新增失败，上架的线路不允许修改団期";
					return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_300, message,
							DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "price");
				}
			Boolean result = xlDateSalePriceService.batchModifyDateSalePrice(command);
			
			if(result){
				message = "修改成功";
				statusCode = DwzJsonResultUtil.STATUS_CODE_200;
			}else{
				message = "修改失败";
				statusCode = DwzJsonResultUtil.STATUS_CODE_300;
				HgLogger.getInstance().error("luoyun", "旅游线路管理-团期维护-批量修改团期失败" + JSON.toJSONString(command));
			}

			DateSalePriceQO dateSalePriceQO_null = new DateSalePriceQO();
			dateSalePriceQO_null.setLineID(command.getLineID());
			dateSalePriceQO_null.setSaleDateAsc(true);
			dateSalePriceQO_null.setBeginDate(DateUtil.formatDate(new Date()));
			List<DateSalePriceDTO> dateSalePriceList = xlDateSalePriceService.queryList(dateSalePriceQO_null);
			double minPrice = 0;
			for(DateSalePriceDTO dto : dateSalePriceList){
				if(minPrice == 0 && dto.getAdultPrice() != 0){
					minPrice = dto.getAdultPrice();
				}
				if(minPrice > dto.getAdultPrice()){
					minPrice = dto.getAdultPrice();
				}
			}
			//如果现有的团期最低价格和之后的不相等就改动
			if(null == lineDTO.getMinPrice() || lineDTO.getMinPrice() != minPrice){
				ModifyLineMinPriceCommand modifyLineMinPriceCommand = new ModifyLineMinPriceCommand();
				modifyLineMinPriceCommand.setLineId(command.getLineID());
				modifyLineMinPriceCommand.setMinPrice(minPrice);
				HgLogger.getInstance().error("yuqz", "旅游线路管理-团期维护-新增单天团期-修改団期最低价格" + JSON.toJSONString(modifyLineMinPriceCommand));
				try{
					lineService.modifyLineMinPrice(modifyLineMinPriceCommand);
				}catch(Exception e){
					e.printStackTrace();
					HgLogger.getInstance().error("yuqz", "旅游线路管理-团期维护-修改団期最低价失败" + HgLogger.getStackTrace(e));
					return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_300, "修改団期最低价失败",
							DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "price");
				}
			}
			return DwzJsonResultUtil.createJsonString(statusCode, message,DwzJsonResultUtil.FLUSH_FORWARD, "price");
			}else{
				HgLogger.getInstance().info("yaosanfeng", "旅游线路管理-团期维护-修改单天团期失败->线路不存在");
				return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_300, "修改失败",
						DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "price");
			}
		}catch(Exception e){
			HgLogger.getInstance().error("luoyun", "旅游线路管理-团期维护-批量修改团期失败" + HgLogger.getStackTrace(e));
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_300, "修改失败",
					DwzJsonResultUtil.FLUSH_FORWARD, "price");
		}
		
		
	}
	
	/**
	 * 
	 * @方法功能说明：跳转到新增团期页面
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月18日上午11:07:41
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param dateSalePriceID
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/toadd")
	public String toAddDateSalePrice(HttpServletRequest request,HttpServletResponse response,Model model,
			@RequestParam(value="id",required=false) String lineID,
			@RequestParam(value="saleDate",required=false) String saleDate){
		
		try{
			LineQO lineQO = new LineQO();
			lineQO.setId(lineID);
			LineDTO lineDTO = lineService.queryUnique(lineQO);
			model.addAttribute("lineDTO", lineDTO);
			model.addAttribute("saleDate", saleDate);
			
		}catch(Exception e){
			HgLogger.getInstance().error("luoyun", "旅游线路管理-团期维护-跳转到新增团期页面失败" + HgLogger.getStackTrace(e));
		}
		
		return "/traveline/price/price_add.html";
		
	}
	
	/**
	 * 
	 * @方法功能说明：新增单天团期
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月19日下午5:06:53
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/add")
	@ResponseBody
	public String addDateSalePrice(HttpServletRequest request,HttpServletResponse response,Model model,
			@ModelAttribute CreateDateSalePriceCommand command){
		try{
			String message = "";
			String statusCode = "";
			LineQO lineQO = new LineQO();
			lineQO.setId(command.getLineID());
			LineDTO lineDTO = lineService.queryUnique(lineQO);
			HgLogger.getInstance().info("yuqz", "旅游线路管理-团期维护-新增单天团期-线路状态:" + lineDTO.getStatusInfo().getStatus());
			if(lineDTO.getStatusInfo().getStatus() == 4){
				message = "新增失败，上架的线路不允许修改団期";
				return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_300, message,
						DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "price");
			}
			Boolean result = xlDateSalePriceService.createDateSalePrice(command);
			if(result){
				message = "新增成功";
				statusCode = DwzJsonResultUtil.STATUS_CODE_200;
			}else{
				message = "新增失败";
				statusCode = DwzJsonResultUtil.STATUS_CODE_300;
				HgLogger.getInstance().error("luoyun", "旅游线路管理-团期维护-新增单天团期失败" + JSON.toJSONString(command));
			}
			DateSalePriceQO dateSalePriceQO = new DateSalePriceQO();
			dateSalePriceQO.setLineID(command.getLineID());
			dateSalePriceQO.setSaleDateAsc(true);
			dateSalePriceQO.setBeginDate(DateUtil.formatDate(new Date()));
			List<DateSalePriceDTO> dateSalePriceList = xlDateSalePriceService.queryList(dateSalePriceQO);
			double minPrice = 0;
			for(DateSalePriceDTO dto : dateSalePriceList){
				if(minPrice == 0 && dto.getAdultPrice() != 0){
					minPrice = dto.getAdultPrice();
				}
				if(minPrice > dto.getAdultPrice()){
					minPrice = dto.getAdultPrice();
				}
			}
			//如果现有的团期最低价格和之后的不相等就改动
			if(null == lineDTO.getMinPrice() || lineDTO.getMinPrice() != minPrice){
				ModifyLineMinPriceCommand modifyLineMinPriceCommand = new ModifyLineMinPriceCommand();
				modifyLineMinPriceCommand.setLineId(lineDTO.getId());
				modifyLineMinPriceCommand.setMinPrice(minPrice);
				HgLogger.getInstance().error("yuqz", "旅游线路管理-团期维护-新增单天团期-修改団期最低价格" + JSON.toJSONString(modifyLineMinPriceCommand));
				try{
					lineService.modifyLineMinPrice(modifyLineMinPriceCommand);
				}catch(Exception e){
					e.printStackTrace();
					HgLogger.getInstance().error("yuqz", "旅游线路管理-团期维护-修改団期最低价失败" + HgLogger.getStackTrace(e));
					return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_300, "修改団期最低价失败",
							DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "price");
				}
			}
			return DwzJsonResultUtil.createJsonString(statusCode, message,DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "price");
		
		}catch(Exception e){
			e.printStackTrace();
			HgLogger.getInstance().error("luoyun", "旅游线路管理-团期维护-新增单天团期失败" + HgLogger.getStackTrace(e));
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_300, "新增失败",
					DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "price");
		}
		
	}
	
	/**
	 * 
	 * @方法功能说明：删除单天团期
	 * @修改者名字：yuqz
	 * @修改时间：2015年5月15日上午11:00:03
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param id
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String deleteDateSalePrice(HttpServletRequest request,HttpServletResponse response,Model model,
			@RequestParam String priceId){
		Map<String ,Object> map=new HashMap<String ,Object>();
		DateSalePriceDTO dateSalePriceDTO = new DateSalePriceDTO();
		map.put("status", "1");
		map.put("message", "修改团期成功");
		if(StringUtils.isBlank(priceId)){
			map.put("status", "0");
			map.put("message", "修改团期失败");
			return JSON.toJSONString(map);
		}
		HgLogger.getInstance().info("yuqz", "DateSalePriceController->deleteDateSalePrice->团期id:" + priceId);
		try{
			DateSalePriceQO dateSalePriceQO = new DateSalePriceQO();
			dateSalePriceQO.setDateSalePriceID(priceId);
			dateSalePriceDTO = xlDateSalePriceService.queryUnique(dateSalePriceQO);
			
			if(null == dateSalePriceDTO){
				map.put("status", "0");
				map.put("message", "团期不存在");
				return JSON.toJSONString(map);
			}
			
			LineQO lineQO = new LineQO();
			lineQO.setId(dateSalePriceDTO.getLine().getId());
			LineDTO lineDTO = lineService.queryUnique(lineQO);
			if(null != lineDTO.getStatusInfo() && lineDTO.getStatusInfo().getStatus() == 4){
				map.put("status", "0");
				map.put("message", "上架线路不允许删除团期");
				return JSON.toJSONString(map);
			}
			
			xlDateSalePriceService.deleteDateSalePrice(priceId);
			
			DateSalePriceQO dateSalePriceQO_null = new DateSalePriceQO();
			dateSalePriceQO_null.setLineID(dateSalePriceDTO.getLine().getId());
			dateSalePriceQO_null.setSaleDateAsc(true);
			dateSalePriceQO_null.setBeginDate(DateUtil.formatDate(new Date()));
			List<DateSalePriceDTO> dateSalePriceList = xlDateSalePriceService.queryList(dateSalePriceQO_null);
			double minPrice = 0;
			for(DateSalePriceDTO dto : dateSalePriceList){
				if(minPrice == 0 && dto.getAdultPrice() != 0){
					minPrice = dto.getAdultPrice();
				}
				if(minPrice > dto.getAdultPrice()){
					minPrice = dto.getAdultPrice();
				}
			}
			//如果现有的团期最低价格和之后的不相等就改动
			if(null == lineDTO.getMinPrice() || lineDTO.getMinPrice() != minPrice){
				ModifyLineMinPriceCommand modifyLineMinPriceCommand = new ModifyLineMinPriceCommand();
				modifyLineMinPriceCommand.setLineId(dateSalePriceDTO.getLine().getId());
				modifyLineMinPriceCommand.setMinPrice(minPrice);
				HgLogger.getInstance().error("yuqz", "旅游线路管理-团期维护-删除单天团期-修改団期最低价格" + JSON.toJSONString(modifyLineMinPriceCommand));
				try{
					lineService.modifyLineMinPrice(modifyLineMinPriceCommand);
				}catch(Exception e){
					e.printStackTrace();
					HgLogger.getInstance().error("yuqz", "旅游线路管理-团期维护-修改団期最低价失败" + HgLogger.getStackTrace(e));
					return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_300, "修改団期最低价失败",
							DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "price");
				}
			}
			
		}catch(Exception e){
			HgLogger.getInstance().error("yuqz", "DateSalePriceController->deleteDateSalePrice->删除团期失败：" + HgLogger.getStackTrace(e));
			map.put("status", "0");
			map.put("message", "修改团期失败");
			return JSON.toJSONString(map);
		}
//		XLUpdateLineMessageApiCommand apiCommand = new XLUpdateLineMessageApiCommand();
//		apiCommand.setLineId(dateSalePriceDTO.getLine().getId());
//		apiCommand.setStatus(LineMessageConstants.UPDATE_DATE_SALE_PRICE);
//		lineService.sendLineUpdateMessage(apiCommand);
		return JSON.toJSONString(map);
	}
	
}

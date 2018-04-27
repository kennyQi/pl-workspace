package hsl.admin.controller.lineSalesPlan;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;
import hg.system.model.meta.City;
import hg.system.qo.CityQo;
import hg.system.service.CityService;
import hsl.admin.controller.BaseController;
import hsl.pojo.command.lineSalesPlan.CreateLineSalesPlanCommand;
import hsl.pojo.command.lineSalesPlan.ModifyLineSalesPlanCommand;
import hsl.pojo.command.lineSalesPlan.UpdateLineSalesPlanStatusCommand;
import hsl.pojo.command.lineSalesPlan.order.UpdateLSPOrderStatusCommand;
import hsl.pojo.dto.line.DateSalePriceDTO;
import hsl.pojo.dto.line.LineDTO;
import hsl.pojo.dto.lineSalesPlan.LineSalesPlanConstant;
import hsl.pojo.dto.lineSalesPlan.LineSalesPlanDTO;
import hsl.pojo.dto.lineSalesPlan.order.LSPOrderDTO;
import hsl.pojo.qo.line.HslLineQO;
import hsl.pojo.qo.lineSalesPlan.LineSalesPlanQO;
import hsl.pojo.qo.lineSalesPlan.order.LSPOrderQO;
import hsl.spi.inter.line.HslLineService;
import hsl.spi.inter.lineSalesPlan.LineSalesPlanService;
import hsl.spi.inter.lineSalesPlan.order.LSPOrderService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @类功能：人气秒杀or拼团游控制器
 * @作者： zhaows
 * @创建时间：2015/11/30
 */
@Controller
@RequestMapping(value="/lineSalesPlan")
public class LineSalesPlanController extends BaseController{
	@Autowired
	private HslLineService hslLineService;
	@Autowired
	private LineSalesPlanService lineSalesPlanService;
	@Autowired
	private LSPOrderService lsPOrderService;
	@Resource
	private CityService cityService;
	/**
	 * @方法功能说明：查询线路活动列表
	 * @修改者名字：zhaows
	 * @修改时间：2015年11月30日
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/lineSalesList")
	public String selectLineSales(HttpServletRequest request, Model model,
								  @RequestParam(value="pageNum",required=false) Integer pageNo,
								  @RequestParam(value="numPerPage",required=false) Integer pageSize,
								  LineSalesPlanQO lineSalesPlanQO){
		try{
			Pagination pagination=new Pagination();
			pageNo=(pageNo==null?1:pageNo);
			pageSize=(pageSize==null?20:pageSize);
			pagination.setPageNo(pageNo);
			pagination.setPageSize(pageSize);
			pagination.setCondition(lineSalesPlanQO);
			pagination=lineSalesPlanService.queryPagination(pagination);
			HgLogger.getInstance().info("zhaows", "lineSalesList-->查询线路活动列表pagination" + JSON.toJSONString(pagination));
			model.addAttribute(pagination);
			model.addAttribute("lspStatus",getOrderStatus(3));
		}catch (Exception e){
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows", "lineSalesList-->查询线路活动列表失败:" + HgLogger.getStackTrace(e));
		}
		return "lineSalesPlan/list_lineSalesPlan.html";
	}
	/**
	 * @方法功能说明：添加线路活动列表
	 * @修改者名字：zhaows
	 * @修改时间：2015年12月01日
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/addLineSales",method = RequestMethod.GET)
	public String addLineSales(HttpServletRequest request,Model model){
		return "lineSalesPlan/add_lineSalesPlan.html";
	}
	/**
	 * @方法功能说明：修改线路活动
	 * @修改者名字：zhaows
	 * @修改时间：2015年12月01日
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/updateLineSales",method = RequestMethod.GET)
	public String updateLineSales(HttpServletRequest request,Model model){
		try{
			String planId=request.getParameter("planId");
			String type=request.getParameter("type");//type 1修改 2详情
			if(StringUtils.isNotBlank(planId)) {
				LineSalesPlanQO lineSalesPlanQO = new LineSalesPlanQO();
				lineSalesPlanQO.setId(planId);
				lineSalesPlanQO.setFetchLine(true);
				HgLogger.getInstance().info("zhaows", "updateLineSales-->查询线路活动UniqueQOGET" + JSON.toJSONString(lineSalesPlanQO));
				LineSalesPlanDTO dto = lineSalesPlanService.queryUnique(lineSalesPlanQO);
				HgLogger.getInstance().info("zhaows", "updateLineSales-->查询线路活动UniquedtoGET" + JSON.toJSONString(dto));
				model.addAttribute("dto", dto);
				String fileUploadPath=SysProperties.getInstance().get("fileUploadPath");
				model.addAttribute("fileUploadPath", fileUploadPath);
				model.addAttribute("type", type);

			}
		}catch (Exception e){
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows", "updateLineSales-->查询线路活动UniquedtoGET失败:" + HgLogger.getStackTrace(e));
		}

		return "lineSalesPlan/update_lineSalesPlan.html";
	}
	/**
	 * @方法功能说明：修改线路活动
	 * @修改者名字：zhaows
	 * @修改时间：2015年12月01日
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/updateLineSales",method = RequestMethod.POST)
	public String updateLineSales(HttpServletRequest request,ModifyLineSalesPlanCommand command){
		try{
			HgLogger.getInstance().info("zhaows", "updateLineSales-->修改线路活动command" + JSON.toJSONString(command));
			lineSalesPlanService.modifyLineSalesPlan(command);
		}catch (Exception e){
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows", "updateLineSales-->修改线路活动失败:" + HgLogger.getStackTrace(e));
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "修改失败:" + e.getMessage(), null, null);
		}

		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "修改成功",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "lineSalesList");
	}
	/**
	 * @方法功能说明：添加线路活动
	 * @修改者名字：zhaows
	 * @修改时间：2015年12月03日
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/addLineSales",method = RequestMethod.POST)
	public String addLineSales(HttpServletRequest request, Model model, CreateLineSalesPlanCommand command){
		try{
			JSONObject jsonObject=JSON.parseObject(command.getImageUri());
			if(StringUtils.isNotBlank(jsonObject.get("uri").toString())){
				command.setImageUri(jsonObject.get("uri").toString());
			}
			HgLogger.getInstance().info("zhaows", "addLineSales-->添加线路活动command" + JSON.toJSONString(command));
			lineSalesPlanService.addLineSalesPlan(command);
		}catch (Exception e){
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows", "addLineSales-->添加线路活动失败:" + HgLogger.getStackTrace(e));
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "添加失败:" + e.getMessage(), null, null);
		}
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "添加成功","openNew", "lineSalesList");
	}

	/**
	 * @方法功能说明：查询线路名称
	 * @修改者名字：zhaows
	 * @修改时间：2015年12月02日
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/selectLineName")
	public String selectLineName(HttpServletRequest request,Model model){
		try{
			HslLineQO hslLineQO=new HslLineQO();
			List<LineDTO> lineDtoList=hslLineService.queryList(hslLineQO);
			HgLogger.getInstance().info("zhaows", "selectLineName-->查询线路名称lineDtoList" + JSON.toJSONString(lineDtoList));
			model.addAttribute("lineDtoList", lineDtoList);
		}catch (Exception e){
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows", "selectLineName-->查询线路名称失败:" + HgLogger.getStackTrace(e));
		}
		return "lineSalesPlan/select_lineName.html";
	}
	/**
	 * @方法功能说明：根据线路日期查询线路价格
	 * @修改者名字：zhaows
	 * @修改时间：2015年12月02日
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/selectLinePrice")
	@ResponseBody
	public String selectLinePrice(HttpServletRequest request,Model model){
		Map<String,Object> map=new HashMap<String,Object>();
		try{
			double linePrice=0.00;
			int peopleAmount=0;
			int number=0;
			double adultPrice=0.00;
			String expect="1";//1标识无团期 2有团期
			String lineId=request.getParameter("lineId");
			String travelDate=request.getParameter("travelDate");
			Date date=new Date();
			SimpleDateFormat sdf=DateUtil.DATE_TIME_FORMAT4();
			map.put("linePrice", linePrice);
			map.put("peopleAmount", peopleAmount);
			HslLineQO hslLineQO=new HslLineQO();
			hslLineQO.setId(lineId);
			hslLineQO.setGetDateSalePrice(true);
			HgLogger.getInstance().info("zhaows", "selectLinePrice-->根据线路日期查询线路价格hslLineQO" + JSON.toJSONString(hslLineQO));
			LineDTO lineDto=hslLineService.queryUnique(hslLineQO);
			HgLogger.getInstance().info("zhaows", "selectLinePrice-->根据线路日期查询线路价格lineDto" + JSON.toJSONString(lineDto));
			if(lineDto!=null&&lineDto.getDateSalePriceList()!=null&&StringUtils.isNotBlank(travelDate)) {
				List<DateSalePriceDTO> dateSalePriceList = lineDto.getDateSalePriceList();
				for(DateSalePriceDTO dateSalePrice:dateSalePriceList){
					String saleDate=DateUtil.formatDate(dateSalePrice.getSaleDate());
					travelDate=travelDate.substring(0,10);
					boolean aa= DateUtil.checkDate(saleDate, travelDate);
					if(DateUtil.checkDate(saleDate, travelDate)
							&&dateSalePrice.getAdultPrice()!=null
							&&dateSalePrice.getNumber()!=null){
						adultPrice=dateSalePrice.getAdultPrice();
						number=dateSalePrice.getNumber();
						expect="2";
						break;
					}
				}
			}
			map.put("linePrice",adultPrice);//取成人价格
			map.put("peopleAmount",number);//取成人人数
			map.put("expect",expect);//是否有团期
		}catch (Exception e){
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows", "selectLinePrice-->根据线路日期查询线路价格失败" + HgLogger.getStackTrace(e));
		}
		return JSON.toJSONString(map);
	}
	/**
	 * @方法功能说明：显示或隐藏活动和审核
	 * @修改者名字：zhaows
	 * @修改时间：2015年12月02日
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/showOrHide")
	@ResponseBody
	public String showOrHide(HttpServletRequest request,Model model){
		try{
			String showStatus=request.getParameter("showStatus");//显示状态
			String status=request.getParameter("status");//活动状态
			String planId=request.getParameter("planId");
			UpdateLineSalesPlanStatusCommand command=new UpdateLineSalesPlanStatusCommand();
			if(StringUtils.isNotBlank(planId)){
				command.setPlanId(planId);
				if(StringUtils.isNotBlank(showStatus)){
					command.setShowStatus(Integer.parseInt(showStatus));
				}
				if(StringUtils.isNotBlank(status)){
					command.setStatus(Integer.parseInt(status));
				}
				HgLogger.getInstance().info("zhaows", "showOrHide-->显示或隐藏活动和审核" + JSON.toJSONString(command));
				lineSalesPlanService.updateLineSalesPlanStatus(command);
				return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "修改成功",null,"");
			}
		}catch (Exception e){
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows", "showOrHide-->显示或隐藏活动和审核失败" + HgLogger.getStackTrace(e));
		}
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "修改失败", null, "");
	}
	/**
	 * @方法功能说明：删除线路活动
	 * @修改者名字：zhaows
	 * @修改时间：2015年12月02日
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/deleteLineSalesPlan")
	@ResponseBody
	public String deleteLineSalesPlan(HttpServletRequest request,Model model){
		try{
			String planId=request.getParameter("planId");
			HgLogger.getInstance().info("zhaows", "deleteLineSalesPlan-->删除线路活动planId" + planId);
			lineSalesPlanService.deleteLineSalesPlan(planId);
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "删除成功",null,"");
		}catch (Exception e){
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows", "deleteLineSalesPlan-->删除线路活动planId失败" + HgLogger.getStackTrace(e));
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "删除失败", null, "");
		}
	}
	/**
	 * @方法功能说明：查询线路活动订单列表
	 * @修改者名字：zhaows
	 * @修改时间：2015年11月30日
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/lineSalesOrderList")
	public String selectLineSalesPlanOrder(
			HttpServletRequest request, Model model,
			@RequestParam(value="pageNum",required=false) Integer pageNo,
			@RequestParam(value="numPerPage",required=false) Integer pageSize,
			LSPOrderQO lsPOrderQO){
		try{
			Pagination pagination=new Pagination();
			pageNo=(pageNo==null?1:pageNo);
			pageSize=(pageSize==null?20:pageSize);
			pagination.setPageNo(pageNo);
			pagination.setPageSize(pageSize);
			pagination.setCondition(lsPOrderQO);
			pagination=lsPOrderService.queryPagination(pagination);
			HgLogger.getInstance().info("zhaows", "lineSalesOrderList-->查询线路活动列表pagination" + JSON.toJSONString(pagination));
			model.addAttribute("cityMap", getCityMap());
			model.addAttribute("orderStatusMap",getOrderStatus(1));
			model.addAttribute("orderPayStatusMap", getOrderStatus(2));
			model.addAttribute("pagination", pagination);
		}catch (Exception e){
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows", "lineSalesOrderList-->查询线路活动列表失败:" + HgLogger.getStackTrace(e));
		}
		return "lineSalesPlan/list_lineSalesPlanOrder.html";
	}
	/**
	 * @方法功能说明：查询线路活动订单详情
	 * @修改者名字：zhaows
	 * @修改时间：2015年11月30日
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/detailSalesOrder")
	public String detailSalesOrder(HttpServletRequest request, Model model,LSPOrderQO lsPOrderQO){
		try{
			HgLogger.getInstance().info("zhaows", "detailSalesOrder-->查询线路活动订单详情lsPOrderQO" + JSON.toJSONString(lsPOrderQO));
			LSPOrderDTO lspOrderDTO=lsPOrderService.queryUnique(lsPOrderQO);
			HgLogger.getInstance().info("zhaows", "detailSalesOrder-->查询线路活动订单详情lspOrderDTO" + JSON.toJSONString(lspOrderDTO));
			model.addAttribute("lspOrderDTO", lspOrderDTO);
			model.addAttribute("cityMap", getCityMap());
			model.addAttribute("orderStatusMap",getOrderStatus(1));
			model.addAttribute("orderPayStatusMap", getOrderStatus(2));
		}catch (Exception e){
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows", "detailSalesOrder-->查询线路活动订单详情失败:" + HgLogger.getStackTrace(e));
		}
		return "lineSalesPlan/detail_lineSalesOrder.html";
	}
	/**
	 * @方法功能说明：修改线路活动订单
	 * @修改者名字：zhaows
	 * @修改时间：2015年11月30日
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/updateSalesOrder")
	@ResponseBody
	public String updateSalesOrder(HttpServletRequest request, Model model){
		try {
			String planOrderId=request.getParameter("id");
			if(StringUtils.isBlank(planOrderId)){
				throw new Exception("订单ID为空");
			}
			UpdateLSPOrderStatusCommand command=new UpdateLSPOrderStatusCommand();
			command.setOrderId(planOrderId);
			command.setStatus(LineSalesPlanConstant.LSP_ORDER_STATUS_CANCEL);
			HgLogger.getInstance().error("zhaows", "updateSalesOrder-->修改线路活动订单command:" + JSON.toJSONString(command));
			lsPOrderService.updateLSPOrderStatus(command);
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "修改成功",null,"");
		}catch (Exception e){
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows", "updateSalesOrder-->修改线路活动订单失败:" + HgLogger.getStackTrace(e));
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "修改失败", null, "");
		}
	}

	/**
	 * @throws
	 * @方法功能说明：查询城市
	 * @修改者名字：zhaows
	 * @修改时间：2015年12月8日上午8:30:20
	 * @修改内容：
	 */

	public HashMap<String, String> getCityMap() {
		HashMap<String, String> cityMap = new HashMap<String, String>();
		CityQo cityQo = new CityQo();
		List<City> cityList = cityService.queryList(cityQo);
		if (cityList != null && cityList.size() > 0) {
			for (City city : cityList) {
				if (StringUtils.isNotBlank(city.getCode())&&StringUtils.isNotBlank(city.getName())){
					cityMap.put(city.getCode(), city.getName());
				}
			}
		}
		return cityMap;
	}
	public   HashMap<String, String>  getOrderStatus(Integer type) {
		if(type==1) {
			HashMap<String, String> orderStatus = new HashMap<String, String>();
			HashMap<Integer, String> order_Status = LineSalesPlanConstant.LSP_ORDER_STATUS;
			Iterator<Map.Entry<Integer, String>> iterator = order_Status.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<Integer, String> entry = iterator.next();
				orderStatus.put(entry.getKey().toString(), entry.getValue());
			}
			return orderStatus;
		}else if(type==2){
			HashMap<String, String> orderPayStatus = new HashMap<String, String>();
			HashMap<Integer, String> order_Pay_Status = LineSalesPlanConstant.LSP_ORDER_PAY_STATUS;
			Iterator<Map.Entry<Integer, String>> iteratorPay = order_Pay_Status.entrySet().iterator();
			while (iteratorPay.hasNext()) {
				Map.Entry<Integer, String> entryPay = iteratorPay.next();
				orderPayStatus.put(entryPay.getKey().toString(), entryPay.getValue());
			}
			return orderPayStatus;
		}else{
			HashMap<String,String> lsp_status= new HashMap<String,String>();
			HashMap<Integer, String> order_Pay_Status = LineSalesPlanConstant.LSP_STATUS;
			Iterator<Map.Entry<Integer, String>> iteratorPay = order_Pay_Status.entrySet().iterator();
			while (iteratorPay.hasNext()) {
				Map.Entry<Integer, String> entryPay = iteratorPay.next();
				lsp_status.put(entryPay.getKey().toString(), entryPay.getValue());
			}
			return lsp_status;
		}

	}
}

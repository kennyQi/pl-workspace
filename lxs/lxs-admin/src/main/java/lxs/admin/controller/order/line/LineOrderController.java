package lxs.admin.controller.order.line;

import hg.common.page.Pagination;
import hg.common.util.BeanMapperUtils;
import hg.common.util.DwzJsonResultUtil;
import hg.log.util.HgLogger;
import hg.system.model.meta.City;
import hg.system.qo.CityQo;
import hg.system.service.CityService;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lxs.admin.controller.BaseController;
import lxs.app.service.line.LineOrderLocalService;
import lxs.domain.model.line.LineOrder;
import lxs.domain.model.line.LineOrderBaseInfo;
import lxs.domain.model.line.LineOrderTraveler;
import lxs.domain.model.line.LineSnapshot;
import lxs.pojo.command.line.CancleLineOrderCommand;
import lxs.pojo.dto.line.XLOrderStatusConstant;
import lxs.pojo.qo.line.LineOrderQO;
import lxs.pojo.util.line.LineOrderConstants;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @类功能说明：订单controller
 * @类修改者：
 * @修改日期：2015年5月21日下午12:30:51
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年5月21日下午12:30:51
 */
@Controller
@RequestMapping(value="/lineOrder")
public class LineOrderController extends BaseController{

	@Autowired 
	private LineOrderLocalService lineOrderLocalService;
	@Autowired
	private CityService cityService;
	
	/**
	 * 
	 * @方法功能说明：订单列表
	 * @修改者名字：cangs
	 * @修改时间：2015年5月21日下午12:30:40
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param lineOrderQO
	 * @参数：@param pageNo
	 * @参数：@param pageSize
	 * @参数：@param beginTimeStr
	 * @参数：@param endTimeStr
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/lineOrderList")
	public String queryLineOrderList(HttpServletRequest request, Model model,
			@ModelAttribute LineOrderQO lineOrderQO,
			@RequestParam(value="pageNum",required=false) Integer pageNo, 
			@RequestParam(value = "numPerPage", required = false) Integer pageSize,
			@RequestParam(value = "beginTimeStr", required = false) String beginTimeStr,
			@RequestParam(value = "endTimeStr", required = false) String endTimeStr
			) {
		
		// 添加分页参数
		Pagination pagination = new Pagination();
		pageNo = (pageNo == null) ? new Integer(1) : pageNo;
		pageSize = (pageSize == null) ? new Integer(20) : pageSize;
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		if(StringUtils.isNotBlank(lineOrderQO.getStartTime()) && StringUtils.isBlank(lineOrderQO.getEndTime())){
			lineOrderQO.setEndTime(lineOrderQO.getStartTime());
		}
		if(StringUtils.isBlank(lineOrderQO.getStartTime()) && StringUtils.isNotBlank(lineOrderQO.getEndTime())){
			lineOrderQO.setStartTime(lineOrderQO.getEndTime());
		}
				
		pagination.setCondition(lineOrderQO);
		
		pagination = lineOrderLocalService.queryPagination(pagination);
		List<LineOrder> lineOrders =(List <LineOrder>)pagination.getList();
		int count=0;
		for(LineOrder lineOrder:lineOrders){
			String destinationCity = "";
			LineSnapshot lineSnapshot=new LineSnapshot();
			lineSnapshot=lineOrder.getLineSnapshot();
			LineSnapshot lineSnapshot2=new LineSnapshot();
			lineSnapshot2=BeanMapperUtils.map(lineSnapshot, LineSnapshot.class);
			if(lineOrder.getLineSnapshot().getStarting()!=null&&StringUtils.isNotBlank(lineOrder.getLineSnapshot().getStarting())){
				String[] strings=lineSnapshot.getStarting().split(",");
				for(int i=0;i<strings.length;i++){
					destinationCity=destinationCity+cityService.get(strings[i]).getName();
					if(i+1<strings.length){
						destinationCity=destinationCity+",";
					}
				}
			}
			lineSnapshot2.setStarting(destinationCity);
			lineOrders.get(count).setLineSnapshot(lineSnapshot2);
			LineOrderBaseInfo lineOrderBaseInfo = lineOrders.get(count).getBaseInfo();
			if(lineOrders.get(count).getInsurancePrice()!=null){
				lineOrderBaseInfo.setSalePrice(lineOrderBaseInfo.getSalePrice()+lineOrders.get(count).getInsurancePrice());
			}
			lineOrders.get(count).setBaseInfo(lineOrderBaseInfo);
			count++;
		}
		
		
		
		//订单状态
		model.addAttribute("statusMap", XLOrderStatusConstant.SLFX_XLORDER_STATUS_MAP); 
		//订单支付状态
		model.addAttribute("payStatusMap", XLOrderStatusConstant.SLFX_XLORDER_PAY_STATUS_MAP); 
		
		model.addAttribute("lineOrderQO", lineOrderQO);
		model.addAttribute("pagination", pagination);

		
		
		return "/order/orderlist.html";
	}
	
	
	
	@RequestMapping(value = "/detail")
	public String lineOrderDetail(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@RequestParam(value = "id") String lineOrderId) {
		LineOrderQO qo = new LineOrderQO();
		qo.setId(lineOrderId);
		LineOrder lineOrderDTO = lineOrderLocalService.queryUnique(qo);
		//把意外险信息更新到dto中
		LineOrderBaseInfo lineOrderBaseInfo = lineOrderDTO.getBaseInfo();
		if(lineOrderDTO.getInsurancePrice()!=null){
			lineOrderBaseInfo.setSalePrice(lineOrderBaseInfo.getSalePrice()+lineOrderDTO.getInsurancePrice());
		}
		lineOrderDTO.setBaseInfo(lineOrderBaseInfo);
		
		CityQo cityQo = new CityQo();
		List<City> cityList = cityService.queryList(cityQo);
		
		model.addAttribute("lineOrderDTO", lineOrderDTO);
		model.addAttribute("citylist", cityList);
		//订单状态
		model.addAttribute("statusMap", XLOrderStatusConstant.SLFX_XLORDER_STATUS_MAP); 
		//订单支付状态
		model.addAttribute("payStatusMap", XLOrderStatusConstant.SLFX_XLORDER_PAY_STATUS_MAP); 
		//游客类别
		model.addAttribute("TTAVELER_TYPE_MAP",LineOrderConstants.TTAVELER_TYPE_MAP);
		//证件类型
		model.addAttribute("TTAVELER_ID_TYPE_MAP",LineOrderConstants.TTAVELER_ID_TYPE_MAP);

		return "/order/orderdetail.html";
	}
	
	/***
	 * 
	 * @方法功能说明：跳转取消订单页
	 * @修改者名字：cangs
	 * @修改时间：2015年5月21日下午12:32:20
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param lineOrderID
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/cancle")
	public String toCancleLineOrder(HttpServletRequest request,HttpServletResponse response, Model model,
			@RequestParam(value = "id") String lineOrderID){
		
		LineOrderQO lineOrderQO = new LineOrderQO();
		lineOrderQO.setId(lineOrderID);
		LineOrder lineOrder=lineOrderLocalService.queryUnique(lineOrderQO);
		Set<LineOrderTraveler> travellerList = lineOrder.getTravelers();
		Iterator<LineOrderTraveler> iterator = travellerList.iterator();
		while(iterator.hasNext()){
			LineOrderTraveler traveller = iterator.next();
			//订单已取消的游客不显示在取消列表里
			if(StringUtils.equals(traveller.getLineOrderStatus().getOrderStatus().toString(),XLOrderStatusConstant.SLFX_ORDER_CANCEL)){ 
				iterator.remove();
			}
		}
		model.addAttribute("lineOrderDTO", lineOrder);
		model.addAttribute("id", lineOrderID);
		model.addAttribute("travellerList", travellerList);
		return "/order/ordercancle.html";
	}

	/**
	 * 
	 * @方法功能说明：取消订单
	 * @修改者名字：cangs
	 * @修改时间：2015年5月21日下午12:31:46
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param lineOrderID
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/cancleOrder")
	@ResponseBody
	public String cancleLineOrder(HttpServletRequest request,HttpServletResponse response, Model model,
			@RequestParam(value = "id") String lineOrderID){
		
		String[] lineOrderTravelers=request.getParameterValues("lineOrderTravelers");
		try{
			CancleLineOrderCommand  command= new CancleLineOrderCommand();
			command.setLineOrderID(lineOrderID);
			command.setTravelerIDs(lineOrderTravelers);
			
			lineOrderLocalService.cancelLineOrder(command);
			
			
		}catch(Exception e){
			HgLogger.getInstance().info("lxs_dev", "【cancleLineOrder】"+"异常，"+HgLogger.getStackTrace(e));
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "取消失败",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "lineOrderList");
		}
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "取消成功",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "lineOrderList");
	}
		
}
	
	
	
	
	

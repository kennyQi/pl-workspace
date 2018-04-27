package slfx.admin.controller.traveline;

import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.system.model.meta.City;
import hg.system.model.meta.Province;
import hg.system.qo.CityQo;
import hg.system.qo.ProvinceQo;
import hg.system.service.CityService;
import hg.system.service.ProvinceService;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import slfx.admin.controller.BaseController;
import slfx.xl.pojo.dto.finance.LineFinanceDTO;
import slfx.xl.pojo.dto.order.LineOrderDTO;
import slfx.xl.pojo.dto.order.LineOrderTravelerDTO;
import slfx.xl.pojo.qo.LineFinanceQO;
import slfx.xl.pojo.qo.LineOrderQO;
import slfx.xl.pojo.system.LineConstants;
import slfx.xl.pojo.system.XLOrderStatusConstant;
import slfx.xl.spi.inter.LineFinanceService;

/**
 * @类功能说明：财务管理Controller
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月22日下午4:40:29
 * @版本：V1.0
 * 
 */
@Controller
@RequestMapping("/traveline/finance")
public class LineFinanceController extends BaseController {

	@Autowired
	private LineFinanceService lineFinanceService;
	
	@Autowired
	private ProvinceService provinceService;
	
	@Autowired
	private CityService cityService;

	/**
	 * 
	 * @方法功能说明：财务管理列表查询
	 * @修改者名字：tandeng
	 * @修改时间：2014年12月25日下午5:24:57
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param lineQO
	 * @参数：@param pageNo
	 * @参数：@param pageSize
	 * @参数：@param beginTimeStr
	 * @参数：@param endTimeStr
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/list")
	public String queryLineList(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@ModelAttribute LineFinanceQO lineFinanceQO,
			@RequestParam(value = "pageNum", required = false) Integer pageNo,
			@RequestParam(value = "numPerPage", required = false) Integer pageSize,
			@RequestParam(value = "beginTimeStr", required = false) String beginTimeStr,
			@RequestParam(value = "endTimeStr", required = false) String endTimeStr) {

		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNo == null ? new Integer(1) : pageNo);
		pagination.setPageSize(pageSize == null ? new Integer(20) : pageSize);

		if (lineFinanceQO == null) {
			lineFinanceQO = new LineFinanceQO();
		}

		if (null != beginTimeStr && !"".equals(beginTimeStr)) {
			lineFinanceQO.setCreateDateFrom(DateUtil.dateStr2BeginDate(beginTimeStr));
		}
		if (null != endTimeStr && !"".equals(endTimeStr)) {
			lineFinanceQO.setCreateDateTo(DateUtil.dateStr2EndDate(endTimeStr));
		}
		if (StringUtils.isBlank(lineFinanceQO.getStartingDepart())) {
			lineFinanceQO.setStartingProvinceID(null);
			lineFinanceQO.setStartingCityID(null);
		}
		
		if (StringUtils.isBlank(lineFinanceQO.getDomesticLine())) {
			lineFinanceQO.setCityOfType(null);
			lineFinanceQO.setProvinceOfType(null);
		}
		pagination.setCondition(lineFinanceQO);
		pagination = lineFinanceService.queryPagination(pagination);

		CityQo cityQo = new CityQo();
		List<City> cityList = cityService.queryList(cityQo);
				
		//查询所有省信息
		ProvinceQo province = new ProvinceQo();
		List<Province> provinceList = provinceService.queryList(province);
		//有出发城市查询条件则查询该城市所在省份的所有城市
		if(StringUtils.isNotBlank(lineFinanceQO.getStartingCityID())){
			CityQo startCityQo = new CityQo();
			startCityQo.setProvinceCode(lineFinanceQO.getStartingProvinceID());
			List<City> startCityList = cityService.queryList(startCityQo);
			model.addAttribute("startCityList", startCityList);
		}
						
		//有线路类型城市编号查询条件则查询该城市所在省份的所有城市
		if(StringUtils.isNotBlank(lineFinanceQO.getCityOfType())){
			CityQo typeCityQo = new CityQo();
			typeCityQo.setProvinceCode(lineFinanceQO.getProvinceOfType());
			List<City> typeCityList = cityService.queryList(typeCityQo);
			model.addAttribute("typeCityList", typeCityList);
		}

		model.addAttribute("pagination", pagination);
		model.addAttribute("lineFinanceQO", lineFinanceQO);
		model.addAttribute("cityList", cityList);
		model.addAttribute("provinceList",provinceList);
		//订单状态
		model.addAttribute("statusMap", XLOrderStatusConstant.SLFX_XLORDER_STATUS_MAP); 
		//订单支付状态
		model.addAttribute("payStatusMap", XLOrderStatusConstant.SLFX_XLORDER_PAY_STATUS_MAP); 
		//线路类型
		model.addAttribute("typeMap", LineConstants.typeMap); 

		return "/traveline/finance/finance_list.html";
	}
	
	/**
	 * 
	 * @方法功能说明：跳转人工结算操作界面
	 * @修改者名字：yaosanfeng
	 * @修改时间：2014年12月23日下午2:12:53
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/toAccount")
	public String accountLineOrder(HttpServletRequest request,HttpServletResponse response, Model model,
			@RequestParam(value = "id") String lineOrderID){
		
		LineFinanceQO lineFinanceQO = new LineFinanceQO();
		lineFinanceQO.setId(lineOrderID);
		LineFinanceDTO lineFinanceDTO = lineFinanceService.queryUnique(lineFinanceQO);
		Set<LineOrderTravelerDTO> travellerList = lineFinanceDTO.getTravelers();
		Iterator<LineOrderTravelerDTO> iterator = travellerList.iterator();
		while(iterator.hasNext()){
			LineOrderTravelerDTO traveller = iterator.next();
			//订单已取消的游客不显示在取消列表里
			if(XLOrderStatusConstant.SLFX_ORDER_CANCEL.equals(traveller.getXlOrderStatus().getStatus()  + "")){ 
				iterator.remove();
			}
		}
		model.addAttribute("lineFinanceDTO", lineFinanceDTO);
		model.addAttribute("id", lineOrderID);
		model.addAttribute("travellerList", travellerList);
		return "/traveline/finance/finance_account.html";
	}
	
}
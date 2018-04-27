package slfx.admin.controller.viewspot;

import hg.common.page.Pagination;
import hg.common.util.BeanMapperUtils;
import hg.common.util.DateUtil;
import hg.log.util.HgLogger;
import hg.system.model.meta.Area;
import hg.system.model.meta.City;
import hg.system.model.meta.Province;
import hg.system.qo.AreaQo;
import hg.system.qo.CityQo;
import hg.system.qo.ProvinceQo;
import hg.system.service.AreaService;
import hg.system.service.CityService;
import hg.system.service.ProvinceService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
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

import slfx.admin.controller.BaseController;
import slfx.mp.pojo.dto.order.MPOrderDTO;
import slfx.mp.qo.DWZPlatdormOrderQO;
import slfx.mp.qo.PlatformOrderQO;
import slfx.mp.spi.common.MpEnumConstants;
import slfx.mp.spi.inter.PlatformOrderService;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping(value = "/viewspot/finance")
public class FinanceController extends BaseController {

	@Resource
	private PlatformOrderService platformOrderService;
	@Resource
	private ProvinceService provinceService;
	@Resource
	private CityService cityService;
	@Resource
	private AreaService areaService;
	@Autowired
	private HgLogger hgLogger;
	@RequestMapping(value = "/list")
	public String queryFinanceOrderList(
			HttpServletRequest request,
			Model model,
			@ModelAttribute DWZPlatdormOrderQO dwzPlatformOrderQO,
			@RequestParam(value = "pageNum", required = false) Integer pageNo,
			@RequestParam(value = "numPerPage", required = false) Integer pageSize) {
		hgLogger.info("wuyg", "admin开始查询景点订单列表");
		// 添加分页参数
		Pagination pagination = new Pagination();
		pageNo = pageNo == null ? new Integer(1) : pageNo;
		pageSize = pageSize == null ? new Integer(20) : pageSize;
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		// 添加查询条件
		PlatformOrderQO platformOrderQO = BeanMapperUtils.map(
				dwzPlatformOrderQO, PlatformOrderQO.class);
		// 判断注册时间查询条件是否被选择
		if (StringUtils.isBlank(dwzPlatformOrderQO.getCreateDateBegin())
				&& StringUtils.isBlank(dwzPlatformOrderQO.getCreateDateEnd())) {
			platformOrderQO.setCreateDateFrom(null);
			platformOrderQO.setCreateDateTo(null);
		} else {

			if (StringUtils.isNotBlank(dwzPlatformOrderQO.getCreateDateBegin())
					&& StringUtils.isNotBlank(dwzPlatformOrderQO
							.getCreateDateEnd())) {

				platformOrderQO.setCreateDateFrom(DateUtil
						.dateStr2BeginDate(dwzPlatformOrderQO
								.getCreateDateBegin()));
				platformOrderQO
						.setCreateDateTo(DateUtil
								.dateStr2EndDate(dwzPlatformOrderQO
										.getCreateDateEnd()));
			} else if (StringUtils.isNotBlank(dwzPlatformOrderQO
					.getCreateDateBegin())
					&& StringUtils.isBlank(dwzPlatformOrderQO
							.getCreateDateEnd())) {
				platformOrderQO.setCreateDateFrom(DateUtil
						.dateStr2BeginDate(dwzPlatformOrderQO
								.getCreateDateBegin()));
				platformOrderQO.setCreateDateTo(DateUtil
						.dateStr2EndDate(dwzPlatformOrderQO
								.getCreateDateBegin()));
			}

		}
		pagination.setCondition(platformOrderQO);
		// 分页查询
		pagination = platformOrderService.queryPagination(pagination);
		model.addAttribute("statusMap",
				MpEnumConstants.OrderStatusEnum.orderStatusMap);
		model.addAttribute("pagination", pagination);
		model.addAttribute("dwzPlatformOrderQO", dwzPlatformOrderQO);

		return "/viewspot/finance/finance_order_list.html";
	}

	/**
	 * 财务报表
	 */
	@RequestMapping(value = "/report")
	public String queryReportList(
			HttpServletRequest request,
			Model model,
			@ModelAttribute DWZPlatdormOrderQO dwzPlatformOrderQO,
			@RequestParam(value = "pageNum", required = false) Integer pageNo,
			@RequestParam(value = "numPerPage", required = false) Integer pageSize) {

		Pagination pagination = new Pagination();
		pageNo = pageNo == null ? new Integer(1) : pageNo;
		pageSize = pageSize == null ? new Integer(20) : pageSize;
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);

		// 添加查询条件
		PlatformOrderQO platformOrderQO = BeanMapperUtils.map(
				dwzPlatformOrderQO, PlatformOrderQO.class);
		// 判断注册时间查询条件是否被选择
		if (StringUtils.isBlank(dwzPlatformOrderQO.getCreateDateBegin())
				&& StringUtils.isBlank(dwzPlatformOrderQO.getCreateDateEnd())) {
			platformOrderQO.setCreateDateFrom(null);
			platformOrderQO.setCreateDateTo(null);
		} else {
			if (StringUtils.isNotBlank(dwzPlatformOrderQO.getCreateDateBegin())
					&& StringUtils.isNotBlank(dwzPlatformOrderQO.getCreateDateEnd())) {

				platformOrderQO.setCreateDateFrom(DateUtil.dateStr2BeginDate(dwzPlatformOrderQO.getCreateDateBegin()));
				platformOrderQO.setCreateDateTo(DateUtil.dateStr2EndDate(dwzPlatformOrderQO.getCreateDateEnd()));
			} else if (StringUtils.isNotBlank(dwzPlatformOrderQO.getCreateDateBegin())
					&& StringUtils.isBlank(dwzPlatformOrderQO.getCreateDateEnd())) {
				platformOrderQO.setCreateDateFrom(DateUtil.dateStr2BeginDate(dwzPlatformOrderQO.getCreateDateBegin()));
				platformOrderQO.setCreateDateTo(DateUtil.dateStr2EndDate(dwzPlatformOrderQO.getCreateDateBegin()));
			}
		}
		// 添加查询条件
		pagination.setCondition(platformOrderQO);
		// 分页查询
		pagination = platformOrderService.queryPagination(pagination);

		// 查询所有省
		ProvinceQo provinceQo = new ProvinceQo();
		List<Province> provinceList = provinceService.queryList(provinceQo);
		
		// 查询市
		CityQo cityQo = new CityQo();
		if(StringUtils.isNotBlank(dwzPlatformOrderQO.getProvince())){
			cityQo.setProvinceCode(dwzPlatformOrderQO.getProvince());
		}
		List<City> cityList = cityService.queryList(cityQo);
		
		
		// 查询区
		AreaQo areaQo = new AreaQo();
		if(StringUtils.isNotBlank(dwzPlatformOrderQO.getCity())){
			areaQo.setCityCode(dwzPlatformOrderQO.getCity());
		}
		List<Area> areaList = areaService.queryList(areaQo);

		model.addAttribute("provinceList", provinceList);
		model.addAttribute("cityList", cityList);
		model.addAttribute("areaList", areaList);
		model.addAttribute("statusMap",
				MpEnumConstants.OrderStatusEnum.orderStatusMap);
		model.addAttribute("pagination", pagination);
		model.addAttribute("dwzPlatformOrderQO", dwzPlatformOrderQO);

		return "/viewspot/finance/finance_report.html";
	}

	@RequestMapping(value = "/export.csv")
	@ResponseBody
	public String exportReport(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute DWZPlatdormOrderQO dwzPlatformOrderQO) {
		hgLogger.info("wuyg", "admin开始导出报表");
		// 添加查询条件
		PlatformOrderQO platformOrderQO = BeanMapperUtils.map(
				dwzPlatformOrderQO, PlatformOrderQO.class);
		// 判断注册时间查询条件是否被选择
		if (StringUtils.isBlank(dwzPlatformOrderQO.getCreateDateBegin())
				&& StringUtils.isBlank(dwzPlatformOrderQO.getCreateDateEnd())) {
			platformOrderQO.setCreateDateFrom(null);
			platformOrderQO.setCreateDateTo(null);
		} else {
			if (StringUtils.isNotBlank(dwzPlatformOrderQO.getCreateDateBegin())
					&& StringUtils.isNotBlank(dwzPlatformOrderQO.getCreateDateEnd())) {

				platformOrderQO.setCreateDateFrom(DateUtil.dateStr2BeginDate(dwzPlatformOrderQO.getCreateDateBegin()));
				platformOrderQO.setCreateDateTo(DateUtil.dateStr2EndDate(dwzPlatformOrderQO.getCreateDateEnd()));
			} else if (StringUtils.isNotBlank(dwzPlatformOrderQO.getCreateDateBegin())
					&& StringUtils.isBlank(dwzPlatformOrderQO.getCreateDateEnd())) {
				platformOrderQO.setCreateDateFrom(DateUtil.dateStr2BeginDate(dwzPlatformOrderQO.getCreateDateBegin()));
				platformOrderQO.setCreateDateTo(DateUtil.dateStr2EndDate(dwzPlatformOrderQO.getCreateDateBegin()));
			}
		}
			// 按照条件查询出所有的信息
			List<MPOrderDTO> mpOrderlist = platformOrderService
					.queryList(platformOrderQO);

			response.reset();
			response.setContentType("text/csv");

			try {

				ServletOutputStream out = response.getOutputStream();
				String title = "序号,订单来源,平台订单号,供应商订单号,经销商订单号,景点名称,省,市,订单状态,下单时间";
				// String field = "";

				out.write(title.getBytes("GB2312"));
				out.write("\r\n".getBytes("GB2312"));

				StringBuilder outs = new StringBuilder();
				int i = 1;

				for (MPOrderDTO mpOrderDTO : mpOrderlist) {
					outs.append(i + ",");
					outs.append("汇商旅,");
					outs.append("'" + mpOrderDTO.getPlatformOrderCode() + "',");
					outs.append(mpOrderDTO.getSupplierOrderCode() + ",");
					outs.append(mpOrderDTO.getDealerOrderCode() + ",");
					outs.append(mpOrderDTO.getSupplierPolicySnapshot()
							.getScenicSpotSnapshot().getTcScenicSpotsName()
							+ ",");
					outs.append(mpOrderDTO.getSupplierPolicySnapshot()
							.getScenicSpotSnapshot().getProvinceName()
							+ ",");
					outs.append(mpOrderDTO.getSupplierPolicySnapshot()
							.getScenicSpotSnapshot().getCityName()
							+ ",");
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					if (mpOrderDTO.getStatus().getCancel()){
							outs.append("已取消,");
					} else if (mpOrderDTO.getStatus().getOutOfDate()) {
							outs.append("已过期,");
					} else if (mpOrderDTO.getStatus().getPrepared()) {
							outs.append("已预定待消费,");
					} else {
						outs.append("已使用,");
					}
					outs.append(sdf.format(mpOrderDTO.getCreateDate()));
					outs.append("\n");
					i++;
				}

				out.write(outs.toString().getBytes("GB2312"));
				out.flush();
				response.getOutputStream().close();

				HgLogger.getInstance().info("tuhualiang", "机票分销平台-景点管理-财务管理-财务报表-下载财务报表成功");
			} catch (IOException e) {
				HgLogger.getInstance().error("tuhualiang", "机票分销平台-景点管理-财务管理-财务报表-下载财务报表失败"+HgLogger.getStackTrace(e));
			}

		return "";
	}
	
	@RequestMapping(value="/searchCity")
	@ResponseBody
	public String searchCity(HttpServletRequest request,Model model,
			@RequestParam(value = "province", required = false) String province){
		// 查询市
		CityQo cityQo = new CityQo();
		cityQo.setProvinceCode(province);
		List<City> cityList = cityService.queryList(cityQo);
		return JSON.toJSONString(cityList);
	}
	
	@RequestMapping(value="/searchArea")
	@ResponseBody
	public String searchArea(HttpServletRequest request,Model model,
			@RequestParam(value = "city", required = false) String city){
		// 查询区
		AreaQo areaQo = new AreaQo();
		areaQo.setCityCode(city);
		List<Area> areaList = areaService.queryList(areaQo);
		return JSON.toJSONString(areaList);
	}

}
package plfx.admin.controller.gjjp;

import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.log.util.HgLogger;

import java.util.Iterator;
import java.util.List;
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
import plfx.admin.controller.BaseController;
import plfx.gjjp.app.pojo.qo.GJJPOrderQo;
import plfx.gjjp.app.service.local.DataTransService;
import plfx.gjjp.app.service.local.GJJPOrderLocalService;
import plfx.gjjp.domain.common.GJJPConstants;
import plfx.gjjp.domain.model.GJFlightCabin;
import plfx.gjjp.domain.model.GJJPOrder;
import plfx.jp.app.service.local.AirlineCompanyLocalService;
import plfx.jp.app.service.local.CountryLocalService;
import plfx.jp.domain.model.Country;
import plfx.jp.pojo.dto.supplier.SupplierDTO;
import plfx.jp.qo.CountryQo;
import plfx.jp.qo.admin.supplier.SupplierQO;
import plfx.jp.spi.inter.CityAirCodeService;
import plfx.jp.spi.inter.supplier.SupplierService;
import plfx.yeexing.pojo.dto.order.JPOrderStatusConstant;
/**
 * 
 * @类功能说明：国际机票财务管理
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：guotx
 * @创建时间：2015年7月30日 16:23:56
 * @版本：V1.0
 *
 */
@Controller
@RequestMapping(value="/gjjp/finance")
public class GJFinanceController extends BaseController {

	@Autowired
	private GJJPOrderLocalService gjjpOrderLocalService;
	@Autowired
	private CityAirCodeService cityAirCodeService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private DataTransService dataTransService;
	@Autowired
	private CountryLocalService countryLocalService;
	@Autowired
	private AirlineCompanyLocalService airlineCompanyLocalService;
	@Autowired
	private SupplierService jpSupplierService;
	
	/**
	 * 国际机票财务报表
	 * @param request
	 * @param model
	 * @param gjjpOrderQo
	 * @param pageNo
	 * @param pageSize
	 * @param beginTimeStr
	 * @param endTimeStr
	 * @return
	 */
	@RequestMapping(value = "/report")
	public String queryOrderList(HttpServletRequest request, Model model,
			@ModelAttribute GJJPOrderQo gjjpOrderQo,
			@RequestParam(value = "isException",required = false)Integer isException,
			@RequestParam(value="pageNum",required=false) Integer pageNo, 
			@RequestParam(value = "numPerPage", required = false) Integer pageSize,
			@RequestParam(value = "beginTimeStr", required = false) String beginTimeStr,
			@RequestParam(value = "endTimeStr", required = false) String endTimeStr) {

		if (null != beginTimeStr && !"".equals(beginTimeStr)) {
			gjjpOrderQo.setCreateDateBegin(DateUtil.dateStr2BeginDate(beginTimeStr));
		}
		if (null != endTimeStr && !"".equals(endTimeStr)) {
			gjjpOrderQo.setCreateDateEnd(DateUtil.dateStr2EndDate(endTimeStr));
		}
		//设置是否为异常订单
		if (isException!=null) {
			gjjpOrderQo.setExceptionOrder(isException==0?false:true);
		}
		gjjpOrderQo.setInitAll(true);
		//国家代码
		CountryQo countryQo=new CountryQo();
		List<Country> countries=countryLocalService.queryList(countryQo);
//		List<AirlineCompany> airlineCompanys=airlineCompanyLocalService.queryList(new AirlineCompanyQo());
		// 查询出所有供应商
		List<SupplierDTO> supplierDtoList = jpSupplierService
				.queryList(new SupplierQO());
		// 添加分页参数
		Pagination pagination = new Pagination();
		pageNo = pageNo == null ? new Integer(1) : pageNo;
		pageSize = pageSize == null ? new Integer(20) : pageSize;
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		// 添加查询条件
		pagination.setCondition(gjjpOrderQo);

		pagination = gjjpOrderLocalService.queryPagination(pagination);
		model.addAttribute("pagination", pagination);
		model.addAttribute("orderStatusMap", dataTransService.getIntMap(GJJPConstants.ORDER_STATUS_MAP));
		//支付状态
		model.addAttribute("orderPayStatusMap", dataTransService.getIntMap(GJJPConstants.ORDER_PAY_STATUS_MAP));
		model.addAttribute("countries", countries);
		model.addAttribute("supplierDtoList", supplierDtoList);
//		model.addAttribute("airlineCompanys", JsonUtil.parseObject(airlineCompanys, true));
		model.addAttribute("gjjpOrderQo", gjjpOrderQo);
		model.addAttribute("dataTransService",dataTransService);
		model.addAttribute("EXCEPTION_TYPE", JPOrderStatusConstant.EXCEPTION_TYPE);
		//是否为异常订单查询条件
		model.addAttribute("isException",isException);
		return "/airticket/financial/gj_financial_report.html";
	}
	
	/**
	 * 
	 * @方法功能说明：导出订单excle表
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月2日上午9:34:40
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param platformOrderQO
	 * @参数：@param pageNo
	 * @参数：@param pageSize
	 * @参数：@param beginTimeStr
	 * @参数：@param endTimeStr
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/export.csv")
	@ResponseBody
	public String export(HttpServletRequest request, HttpServletResponse response, Model model,
			@ModelAttribute GJJPOrderQo gjjpOrderQo,
			@RequestParam(value="pageNo",required=false) Integer pageNo, 
			@RequestParam(value = "numPerPage", required = false) Integer pageSize,
			@RequestParam(value = "beginTimeStr", required = false) String beginTimeStr,
			@RequestParam(value = "endTimeStr", required = false) String endTimeStr) {

		if(StringUtils.isNotBlank(beginTimeStr)){
			gjjpOrderQo.setCreateDateBegin(DateUtil.dateStr2BeginDate(beginTimeStr));
		}
		if(StringUtils.isNotBlank(endTimeStr)){
			gjjpOrderQo.setCreateDateEnd(DateUtil.dateStr2EndDate(endTimeStr));
		}

		gjjpOrderQo.setInitAll(true);
		try {
			// 添加分页参数
			Pagination pagination = new Pagination();
			pagination.setPageNo(1);
			pagination.setPageSize(10000);
			// 添加查询条件
			pagination.setCondition(gjjpOrderQo);
			pagination = gjjpOrderLocalService.queryPagination(pagination);
			@SuppressWarnings("unchecked")
			List<GJJPOrder> orderList = (List<GJJPOrder>) pagination.getList();
			response.reset();
			response.setContentType("text/csv");
			
			ServletOutputStream out = response.getOutputStream();
			String title = "序号,订单类别,平台订单号,经销商订单号,供应商订单号,航空公司,出发地,目的地,支付金额,支付供应商,返点/佣金,${0}订单状态,支付状态,下单时间";
			String field = "";
//			if (null != platformOrderQO&& JPOrderStatusConstant.EXCEPTION_TYPE.equals(platformOrderQO.getType())) {
//				field = "调整金额,";
//			}
			title = title.replace("${0}", field);
			out.write(title.getBytes("GB2312"));
			out.write("\r\n".getBytes("GB2312"));
			
			StringBuilder outs = new StringBuilder();
			int i = 1;
			double totalPayAmount=0;
//			double totalPlatPolicyPirce=0;
//			double totalAdjust=0;
			
			for (GJJPOrder gjjpOrder : orderList) {
				outs.append(i+",");
				String orderType = null;
				if (gjjpOrder.getExceptionInfo()==null) {
					orderType="普通订单";
				}else{
					orderType= gjjpOrder.getExceptionInfo().getExceptionOrder()?"异常订单":"普通订单";
				}
				outs.append(orderType+",");
				outs.append(gjjpOrder.getId()+",");
				outs.append(gjjpOrder.getBaseInfo().getDealerOrderId()+",");
				outs.append(gjjpOrder.getBaseInfo().getSupplierOrderId()+",");
				List<GJFlightCabin> backFlights = gjjpOrder.getSegmentInfo().getBackFlights();
				String currentAirlineCode="";
				if (backFlights!=null && backFlights.size()>0) {
					for (Iterator<GJFlightCabin> iterator = backFlights.iterator(); iterator
							.hasNext();) {
						GJFlightCabin gjFlightCabin = (GJFlightCabin) iterator
								.next();
						if (gjFlightCabin.getCarriageAirline()!=null && !gjFlightCabin.getCarriageAirline().equals(currentAirlineCode)) {
							currentAirlineCode=gjFlightCabin.getCarriageAirline();
							outs.append(dataTransService.getAirLineNameByCode(currentAirlineCode));
						}
					}
					outs.append(",");
				}else {
					outs.append(",");
				}
				//出发地、目的地
				outs.append(dataTransService.getAirportName(gjjpOrder.getSegmentInfo().getOrgCity())+",");
				outs.append(dataTransService.getAirportName(gjjpOrder.getSegmentInfo().getDstCity())+",");
				outs.append(gjjpOrder.getPayInfo().getTotalPrice()+",");
				outs.append(gjjpOrder.getPayInfo().getSupplierTotalPrice()+",");
				outs.append(gjjpOrder.getPayInfo().getSupplierDisc()+"/"+gjjpOrder.getPayInfo().getCommAmount()+",");
//				if (null != platformOrderQO && JPOrderStatusConstant.EXCEPTION_TYPE.equals(platformOrderQO.getType())) {
//					double adjust  = jpOrderDTO.getAdjust() == null ? 0 : jpOrderDTO.getAdjust();
//					outs.append(adjust+",");
//				}
				//支付宝手续费
				//outs.append("手续费,");
				String orderStatus =GJJPConstants.ORDER_STATUS_MAP.get(gjjpOrder.getStatus().getCurrentValue());
				outs.append(orderStatus+",");
				outs.append(GJJPConstants.ORDER_PAY_STATUS_MAP.get(gjjpOrder.getPayInfo().getStatus())+",");
				outs.append(DateUtil.formatDateTime(gjjpOrder.getBaseInfo().getCreateDate(), "yyyy-MM-dd HH:mm:ss")+",");
				outs.append("\n");
				i++;
			}
			outs.append("合计,,,,,,,,");
			outs.append(totalPayAmount);
			outs.append(",");
			
			out.write(outs.toString().getBytes("GB2312"));
			out.flush();
			response.getOutputStream().close();
			
			HgLogger.getInstance().info("guotx", "机票分销平台-国际机票管理-财务管理-导出-下载财务报表成功");
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("guotx", "机票分销平台-国际机票管理-财务管理-导出-下载财务报表失败"+HgLogger.getStackTrace(e));
		}
		return "";
	}
	
	
	
	
}

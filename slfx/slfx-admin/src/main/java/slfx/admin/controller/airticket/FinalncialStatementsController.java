package slfx.admin.controller.airticket;

import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.log.util.HgLogger;

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

import slfx.admin.controller.BaseController;
import slfx.jp.pojo.dto.flight.CityAirCodeDTO;
import slfx.jp.pojo.dto.flight.SlfxFlightDTO;
import slfx.jp.pojo.dto.order.JPOrderDTO;
import slfx.jp.pojo.dto.order.JPOrderStatusConstant;
import slfx.jp.qo.admin.PlatformOrderQO;
import slfx.jp.spi.inter.CityAirCodeService;
import slfx.jp.spi.inter.JPPlatformOrderService;

import com.alibaba.dubbo.common.json.JSON;
/**
 * 
 * @类功能说明： 财务报表
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tuhualiang
 * @创建时间：2014年8月12日下午3:15:20
 * @版本：V1.0
 *
 */
@Controller
@RequestMapping(value="/airtkt/finalncial/statements")
public class FinalncialStatementsController extends BaseController {

	@Autowired
	private JPPlatformOrderService jpPlatformOrderService;
	@Autowired
	private CityAirCodeService cityAirCodeService;
	
	@RequestMapping(value = "/list")
	public String queryOrderList(HttpServletRequest request, Model model,
			@ModelAttribute PlatformOrderQO platformOrderQO,
			@RequestParam(value="pageNum",required=false) Integer pageNo, 
			@RequestParam(value = "numPerPage", required = false) Integer pageSize,
			@RequestParam(value = "beginTimeStr", required = false) String beginTimeStr,
			@RequestParam(value = "endTimeStr", required = false) String endTimeStr) {

		if(StringUtils.isNotBlank(beginTimeStr)){
			platformOrderQO.setCreateDateFrom(DateUtil.dateStr2BeginDate(beginTimeStr));
		}
		if(StringUtils.isNotBlank(endTimeStr)){
			platformOrderQO.setCreateDateTo(DateUtil.dateStr2EndDate(endTimeStr));
		}
		
		if(StringUtils.isNotBlank(platformOrderQO.getLinkerName())){
			platformOrderQO.setLoginName(platformOrderQO.getLinkerName());
		}
		
		if (null !=platformOrderQO && StringUtils.isBlank(platformOrderQO.getType())) {
			platformOrderQO.setType(JPOrderStatusConstant.COMMON_TYPE);
		}
		
		if (null != platformOrderQO && platformOrderQO.getTempStatus() != null) {
			platformOrderQO.setStatus(platformOrderQO.getTempStatus());
		}
		
		platformOrderQO.setCreateDateAsc(false);//按创建时间倒序排序
		platformOrderQO.setIsFilterCancel(true); //过滤掉cancel状态的订单
		platformOrderQO.setJustDisPaySucc(true); //过滤掉cancel状态的订单
		
		//航空城市代码
		List<CityAirCodeDTO> cities=this.cityAirCodeService.queryCityAirCodeList();
		
		// 添加分页参数
		Pagination pagination = new Pagination();
		pageNo = pageNo == null ? new Integer(1) : pageNo;
		pageSize = pageSize == null ? new Integer(20) : pageSize;
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		// 添加查询条件
		pagination.setCondition(platformOrderQO);

		pagination = jpPlatformOrderService.queryPagination(pagination);
		model.addAttribute("pagination", pagination);
		model.addAttribute("statusList", JPOrderStatusConstant.SLFX_JPORDER_STATUS_LIST);
		model.addAttribute("orderPayStatusList", JPOrderStatusConstant.SLFX_JPORDER_PAY_STATUS_LIST);
		model.addAttribute("cities", cities);
		model.addAttribute("STATUS_MAP", JPOrderStatusConstant.JPORDER_STATUS_MAP);
		model.addAttribute("platformOrderQO", platformOrderQO);
		model.addAttribute("TYPE_MAP", JPOrderStatusConstant.JPORDER_TYPE_MAP);
		model.addAttribute("EXCEPTION_TYPE", JPOrderStatusConstant.EXCEPTION_TYPE);
		HgLogger.getInstance().info("tuhualiang", "机票分销平台-机票管理-财务管理-查询财务报表列表成功");
		return "/airticket/financial_statements/statements_list.html";
	}
	
	
	/**
	 * 导出订单excle表
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/fileName.csv")
	@ResponseBody
	public String export(HttpServletRequest request, HttpServletResponse response, Model model,
			@ModelAttribute PlatformOrderQO platformOrderQO,
			@RequestParam(value="pageNo",required=false) Integer pageNo, 
			@RequestParam(value = "numPerPage", required = false) Integer pageSize,
			@RequestParam(value = "beginTimeStr", required = false) String beginTimeStr,
			@RequestParam(value = "endTimeStr", required = false) String endTimeStr) {

		if(StringUtils.isNotBlank(beginTimeStr)){
			platformOrderQO.setCreateDateFrom(DateUtil.dateStr2BeginDate(beginTimeStr));
		}
		if(StringUtils.isNotBlank(endTimeStr)){
			platformOrderQO.setCreateDateTo(DateUtil.dateStr2EndDate(endTimeStr));
		}

		if(StringUtils.isNotBlank(platformOrderQO.getLinkerName())){
			platformOrderQO.setLoginName(platformOrderQO.getLinkerName().trim());
		}
		
		if (null !=platformOrderQO && StringUtils.isBlank(platformOrderQO.getType())) {
			platformOrderQO.setType(JPOrderStatusConstant.COMMON_TYPE);
		}
		
		platformOrderQO.setCreateDateAsc(false);//按创建时间倒序排序
		platformOrderQO.setIsFilterCancel(true); //过滤掉cancel状态的订单
		platformOrderQO.setJustDisPaySucc(true); //过滤掉cancel状态的订单
		
		try {
			// 添加分页参数
			Pagination pagination = new Pagination();
			pagination.setPageNo(1);
			pagination.setPageSize(10000);
			// 添加查询条件
			pagination.setCondition(platformOrderQO);
			pagination = jpPlatformOrderService.queryPagination(pagination);
			@SuppressWarnings("unchecked")
			List<JPOrderDTO> orderDtoList = (List<JPOrderDTO>) pagination.getList();
			response.reset();
			response.setContentType("text/csv");
			
			ServletOutputStream out = response.getOutputStream();
			String title = "序号,订单类别,平台订单号,经销商订单号,供应商订单号,航空公司,出发地,目的地,支付金额,返点/佣金,${0}订单状态,下单时间";
			String field = "";
			if (null != platformOrderQO&& JPOrderStatusConstant.EXCEPTION_TYPE.equals(platformOrderQO.getType())) {
				field = "调整金额,";
			}
			title = title.replace("${0}", field);
			out.write(title.getBytes("GB2312"));
			out.write("\r\n".getBytes("GB2312"));
			
			StringBuilder outs = new StringBuilder();
			int i = 1;
			double totalPayAmount=0;
//			double totalPlatPolicyPirce=0;
			double totalAdjust=0;
			
			for (JPOrderDTO jpOrderDTO : orderDtoList) {
				outs.append(i+",");
				String orderType = JPOrderStatusConstant.JPORDER_TYPE_MAP.get(jpOrderDTO.getType()+"");
				outs.append(orderType+",");
				outs.append(jpOrderDTO.getOrderNo()+",");
				outs.append(jpOrderDTO.getDealerOrderCode()+",");
				outs.append("\"\t"+jpOrderDTO.getSupplierOrderNo()+"\",");
				String flightSnapshotJSON = jpOrderDTO.getFlightSnapshotJSON();
				if (StringUtils.isNotBlank(flightSnapshotJSON)) {
					SlfxFlightDTO flightDto = JSON.parse(flightSnapshotJSON,SlfxFlightDTO.class);
					outs.append(flightDto.getAirCompName());
				}
				outs.append(",");
				outs.append(jpOrderDTO.getStartCityName()+",");
				outs.append(jpOrderDTO.getEndCityName()+",");
				outs.append(jpOrderDTO.getUserPayAmount()+",");
				outs.append(jpOrderDTO.getCommRate()+"/"+jpOrderDTO.getCommAmount()+",");
				if (null != platformOrderQO && JPOrderStatusConstant.EXCEPTION_TYPE.equals(platformOrderQO.getType())) {
					double adjust  = jpOrderDTO.getAdjust() == null ? 0 : jpOrderDTO.getAdjust();
					outs.append(adjust+",");
				}
				String orderStatus =JPOrderStatusConstant.JPORDER_STATUS_MAP.get(jpOrderDTO.getOrderStatus().getStatus()+"");
				outs.append(orderStatus+",");
				outs.append(DateUtil.formatDateTime(jpOrderDTO.getCreateDate(), "yyyy-MM-dd HH:mm:ss")+",");
				outs.append("\n");
				i++;
				
				if (null != jpOrderDTO.getUserPayAmount()) {
					totalPayAmount=totalPayAmount + jpOrderDTO.getUserPayAmount();
				}
				
//				if (null != jpOrderDTO.getPlatPolicyPirce()) {
//					totalPlatPolicyPirce=totalPlatPolicyPirce + jpOrderDTO.getPlatPolicyPirce();
//				}
				
				if (null != platformOrderQO && JPOrderStatusConstant.EXCEPTION_TYPE.equals(platformOrderQO
								.getType())) {
					if (null != jpOrderDTO.getAdjust()) {
						totalAdjust=totalAdjust + jpOrderDTO.getAdjust();
					}
				}
			}
			outs.append("合计,,,,,,,,");
			outs.append(totalPayAmount);
			outs.append(",");
//			outs.append(totalPlatPolicyPirce);
//			outs.append(",");
			if (null != platformOrderQO && JPOrderStatusConstant.EXCEPTION_TYPE.equals(platformOrderQO.getType())) {
				outs.append(totalAdjust);
				outs.append(",");
			}
			
			out.write(outs.toString().getBytes("GB2312"));
			out.flush();
			response.getOutputStream().close();
			
			HgLogger.getInstance().info("tuhualiang", "机票分销平台-机票管理-财务管理-导出-下载财务报表成功");
		} catch (Exception e) {
			HgLogger.getInstance().error("tuhualiang", "机票分销平台-机票管理-财务管理-导出-下载财务报表失败"+HgLogger.getStackTrace(e));
		}
		return "";
	}
	
}

package plfx.admin.controller.gnjp;

import hg.common.page.Pagination;
import hg.common.util.DateUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import plfx.admin.controller.BaseController;
import plfx.jp.pojo.dto.supplier.SupplierDTO;
import plfx.jp.pojo.system.SupplierConstants;
import plfx.jp.qo.admin.supplier.SupplierQO;
import plfx.jp.spi.inter.JPOrderLogService;
import plfx.jp.spi.inter.JPPlatformOrderService;
import plfx.jp.spi.inter.supplier.SupplierService;
import plfx.yeexing.pojo.dto.order.JPOrderDTO;
import plfx.yeexing.pojo.dto.order.JPOrderLogDTO;
import plfx.yeexing.pojo.dto.order.JPOrderStatusConstant;
import plfx.yeexing.qo.admin.JPOrderLogQO;
import plfx.yeexing.qo.admin.PassengerQO;
import plfx.yeexing.qo.admin.PlatformOrderQO;


/******
 * 
 * @类功能说明：供应商平台支出退款
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年8月11日下午2:57:26
 * @版本：V1.0
 *
 */
@Controller
public class SupplierIncomRefundController extends BaseController{
	
	@Autowired
	private JPPlatformOrderService jpPlatformOrderService;
	
	@Autowired
	SupplierService jpSupplierService;
	
	@Autowired
	private JPOrderLogService jPOrderLogService;
	
	@RequestMapping("/airtkt/gnjp/supplier/income_refund/list")
	public ModelAndView list(HttpServletRequest request, 
							@RequestParam(value="pageNum", required = false)Integer pageNum,
							@RequestParam(value="numPerPage", required = false)Integer pageSize,
							@RequestParam(value="suppShortName", required=false)String suppShortName,
							@RequestParam(value="linkerName", required=false)String linkerName,
							@RequestParam(value="orderNo", required=false)String orderNo,
							@RequestParam(value="createDateFrom", required=false)String createDateFrom,
							@RequestParam(value="createDateTo", required=false)String createDateTo,
							@RequestParam(value="yeeXingOrderId", required=false)String yeeXingOrderId,
							@RequestParam(value="pnr", required=false)String pnr,
							@RequestParam(value="passengerName", required=false)String passengerName){
		
		
		Pagination pagination = new Pagination();
		PlatformOrderQO qo = new PlatformOrderQO();
		//只显示有支付流水号的订单
		qo.setIsPay(true);
		//供应商名称
		if(StringUtils.isNotBlank(suppShortName)){
			qo.setSuppShortName(suppShortName);
		}
		//下单人
		if(StringUtils.isNotBlank(linkerName)){
			qo.setLoginName(linkerName);
		}
		//平台订单号
		if(StringUtils.isNotBlank(orderNo)){
			qo.setOrderNo(orderNo);
		}
		//pnr
		if(StringUtils.isNotBlank(pnr)){
			qo.setPnr(pnr);
		}
		//下单开始时间
		if(StringUtils.isNotBlank(createDateFrom)){
			qo.setCreateDateFrom(DateUtil.dateStr2BeginDate(createDateFrom));
		}
		//下单结束时间
		if(StringUtils.isNotBlank(createDateTo)){
			qo.setCreateDateTo(DateUtil.dateStr2EndDate(createDateTo));
		}
		//乘客姓名
		if(StringUtils.isNotBlank(passengerName)){
			PassengerQO passengerQo = new PassengerQO(); 
			passengerQo.setName(passengerName);
			qo.setPassengerQO(passengerQo);
		}
		//易行订单号
		if(StringUtils.isNotBlank(yeeXingOrderId)){
			qo.setYeeXingOrderId(yeeXingOrderId);
		}
		//按创建时间倒序排序
		qo.setCreateDateAsc(false);
		
		pagination.setCondition(qo);
		
		if(pageNum!=null){
			pagination.setPageNo(pageNum);
		}
		if(pageSize!=null){
			pagination.setPageSize(pageSize);
		}
		
		List<SupplierDTO> supplierDtoList = jpSupplierService.queryList(new SupplierQO());
		
		pagination = jpPlatformOrderService.queryPagination(pagination);

		ModelAndView mav = new ModelAndView("/airticket/supplier_income_refund/gn_list.html");
		mav.addObject("pagination", pagination);
		mav.addObject("suppShortName", suppShortName);
		mav.addObject("linkerName", linkerName);
		mav.addObject("orderNo", orderNo);
		mav.addObject("pnr", pnr);
		mav.addObject("createDateFrom", createDateFrom);
		mav.addObject("createDateTo", createDateTo);
		mav.addObject("yeeXingOrderId", yeeXingOrderId);
		mav.addObject("passengerName", passengerName);
		mav.addObject("suppMap", SupplierConstants.getSuppmap());
		mav.addObject("supplierDtoList", supplierDtoList);
		
		//证件类型
		mav.addObject("DOCUMENT_TYPE_MAP", JPOrderStatusConstant.DOCUMENT_TYPE_MAP);
		//乘客类型
		mav.addObject("PASSENGER_TYPE_MAP", JPOrderStatusConstant.PASSENGER_TYPE_MAP);
		return mav;
	}
	
	
	@RequestMapping("/airtkt/gnjp/supplier/income_refund/detail")
	public ModelAndView orderDetail(
			@ModelAttribute PlatformOrderQO platformOrderQO){
		ModelAndView mav = new ModelAndView("/airticket/supplier_income_refund/gn_detail.html");

		JPOrderDTO jpOrderDTO = jpPlatformOrderService.queryUnique(platformOrderQO);
		mav.addObject("detail", jpOrderDTO);
		//订单状态
		mav.addObject("STATUS_MAP", JPOrderStatusConstant.PLFX_JPORDER_STATUS_MAP);
		//订单支付状态
		mav.addObject("PAY_STATUS_MAP", JPOrderStatusConstant.PLFX_JPORDER_PAY_STATUS_MAP);
		//查询条件
		mav.addObject("platformOrderQO", platformOrderQO);
		//异常/普通订单
		mav.addObject("TYPE_MAP", JPOrderStatusConstant.JPORDER_TYPE_MAP);
	    //日志查询
		if(jpOrderDTO != null && StringUtils.isNotBlank(jpOrderDTO.getId())){
			JPOrderLogQO qo = new JPOrderLogQO();
			qo.setOrderId(jpOrderDTO.getId());
			List<JPOrderLogDTO> logs = jPOrderLogService.queryList(qo);
			//保存日志
			mav.addObject("LOGS", logs);
			
		}
		//证件类型
		mav.addObject("DOCUMENT_TYPE_MAP", JPOrderStatusConstant.DOCUMENT_TYPE_MAP);
		return mav;
	}
	
	
}

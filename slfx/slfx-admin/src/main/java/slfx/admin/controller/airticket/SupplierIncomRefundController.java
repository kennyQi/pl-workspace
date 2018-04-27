package slfx.admin.controller.airticket;

import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.log.util.HgLogger;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import slfx.admin.controller.BaseController;
import slfx.jp.pojo.dto.order.JPOrderDTO;
import slfx.jp.pojo.dto.order.JPOrderStatusConstant;
import slfx.jp.pojo.dto.order.PlatformOrderDetailDTO;
import slfx.jp.pojo.dto.supplier.SupplierDTO;
import slfx.jp.qo.admin.PassengerQO;
import slfx.jp.qo.admin.PlatformOrderQO;
import slfx.jp.qo.admin.supplier.SupplierQO;
import slfx.jp.spi.inter.JPPlatformOrderService;
import slfx.jp.spi.inter.supplier.SupplierService;

/**
 * @类功能说明：供应商平台支出退款controller
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2014-8-4下午3:57:29
 * @版本：V1.0
 *
 */
@Controller
public class SupplierIncomRefundController extends BaseController{
	
	@Autowired
	private JPPlatformOrderService jpPlatformOrderService;
	
	@Autowired
	SupplierService jpSupplierService;
	
	
	@RequestMapping("/airtkt/supplier/income_refund/list")
	public ModelAndView list(HttpServletRequest request, 
							@RequestParam(value="pageNum", required = false)Integer pageNum,
							@RequestParam(value="numPerPage", required = false)Integer pageSize,
							@RequestParam(value="suppShortName", required=false)String suppShortName,
							@RequestParam(value="linkerName", required=false)String linkerName,
							@RequestParam(value="orderNo", required=false)String orderNo,
							@RequestParam(value="createDateFrom", required=false)String createDateFrom,
							@RequestParam(value="createDateTo", required=false)String createDateTo,
							@RequestParam(value="ygOrderNo", required=false)String ygOrderNo,
							@RequestParam(value="pnr", required=false)String pnr,
							@RequestParam(value="passengerName", required=false)String passengerName){
		
		
		Pagination pagination = new Pagination();
		PlatformOrderQO qo = new PlatformOrderQO();
		//只显示有支付流水号的订单
		qo.setIsPay(true);
		
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if(StringUtils.isNotBlank(suppShortName)){
			qo.setSuppShortName(suppShortName);
		}
		
		if(StringUtils.isNotBlank(linkerName)){
//			qo.setUserId(linkerName);//下单联系人
			qo.setLoginName(linkerName);
		}
		
		if(StringUtils.isNotBlank(orderNo)){
			qo.setOrderNo(orderNo);//平台订单号
		}
		
		if(StringUtils.isNotBlank(pnr)){
			qo.setPnr(pnr);
		}
		
		if(StringUtils.isNotBlank(createDateFrom)){
			qo.setCreateDateFrom(DateUtil.dateStr2BeginDate(createDateFrom));
		}
		if(StringUtils.isNotBlank(createDateTo)){
			qo.setCreateDateTo(DateUtil.dateStr2EndDate(createDateTo));
		}
		
		if(StringUtils.isNotBlank(passengerName)){
			PassengerQO passengerQo = new PassengerQO(); //乘客姓名
			passengerQo.setName(passengerName);
			qo.setPassengerQO(passengerQo);
		}
		
		if(StringUtils.isNotBlank(ygOrderNo)){
			qo.setYgOrderNo(ygOrderNo);//易购订单号
		}
		qo.setCreateDateAsc(false);//按创建时间倒序排序
		
		pagination.setCondition(qo);
		
		if(pageNum!=null){
			pagination.setPageNo(pageNum);
		}
		if(pageSize!=null){
			pagination.setPageSize(pageSize);
		}
		
		List<SupplierDTO> supplierDtoList = jpSupplierService.queryList(new SupplierQO());
		
		pagination = jpPlatformOrderService.queryPagination(pagination);
		for(int i=0;i<pagination.getList().size();i++){
			JPOrderDTO tmpDto=(JPOrderDTO)pagination.getList().get(i);
			tmpDto.setUserPayAmount(Math.abs(tmpDto.getUserPayAmount()));
			tmpDto.setDisparity(Math.abs(tmpDto.getDisparity()));
		}
		ModelAndView mav = new ModelAndView("/airticket/supplier_income_refund/list.html");
		mav.addObject("pagination", pagination);
		mav.addObject("suppShortName", suppShortName);
		mav.addObject("linkerName", linkerName);
		mav.addObject("orderNo", orderNo);
		mav.addObject("pnr", pnr);
		mav.addObject("createDateFrom", createDateFrom);
		mav.addObject("createDateTo", createDateTo);
		mav.addObject("ygOrderNo", ygOrderNo);
		mav.addObject("passengerName", passengerName);
//		mav.addObject("suppMap", SupplierConstants.getSuppmap());
		mav.addObject("supplierDtoList", supplierDtoList);
		return mav;
	}
	
	
	@RequestMapping("/airtkt/supplier/income_refund/detail")
	public ModelAndView orderDetail(
		@RequestParam(value="orderNo", required=false)String orderNo,
		@RequestParam(value="supplierOrderNo", required=false)String supplierOrderNo){
		ModelAndView mav = new ModelAndView("/airticket/supplier_income_refund/detail.html");
		if(StringUtils.isBlank(orderNo)){
			return mav;
		}
		/*PlatformOrderDetailDTO dto = null;
		
		if(StringUtils.isNotBlank(orderNo)){
			PlatformOrderQO qo = new PlatformOrderQO();
			qo.setOrderNo(orderNo);
			dto = jpPlatformOrderService.queryOrderDetail(qo);
		}*/
		PlatformOrderQO qo = new PlatformOrderQO();
		qo.setOrderNo(orderNo);
		qo.setSupplierOrderNo(supplierOrderNo);
		JPOrderDTO jpOrder = jpPlatformOrderService.queryUnique(qo);
		PlatformOrderDetailDTO dto = new PlatformOrderDetailDTO();
		dto.setOrder(jpOrder);
		
		mav.addObject("detail", dto);
		try {
			mav.addObject("STATUS_MAP", JPOrderStatusConstant.SLFX_JPORDER_STATUS_MAP);
		} catch (Exception e) {
			HgLogger.getInstance().error("tuhualiang", "SupplierIncomRefundController->orderDetail->exception:" + HgLogger.getStackTrace(e));
		}
		
		return mav;
	}
	
	
}

package plfx.admin.controller.gjjp;

import hg.common.page.Pagination;
import hg.common.util.DateUtil;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import plfx.gjjp.app.pojo.qo.GJJPOrderQo;
import plfx.gjjp.app.service.local.DataTransService;
import plfx.gjjp.app.service.local.GJJPOrderLocalService;
import plfx.gjjp.domain.common.GJJPConstants;
import plfx.gjjp.domain.model.GJJPOrder;
import plfx.jp.pojo.dto.dealer.DealerDTO;
import plfx.jp.qo.admin.dealer.DealerQO;
import plfx.jp.spi.inter.dealer.DealerService;

/**
 * @类功能说明：用户收入，退款明细
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：guotx
 * @创建时间：2015年7月25日上午16:18:32
 * @版本：V1.0
 * 
 */
@Controller
public class GJCustomerIncomeRefundController {

	@Autowired
	private GJJPOrderLocalService gjjpOrderLocalService;
	
	@Autowired
	private DataTransService dataTransService;
	
	@Autowired
	private DealerService dealerService;

	@RequestMapping("/airtkt/gj_customer/income_refund/list")
	public ModelAndView list(
			@ModelAttribute GJJPOrderQo gjjpOrderQo,
			@RequestParam(value = "pageNum", required = false) Integer pageNum,
			@RequestParam(value = "numPerPage", required = false) Integer pageSize,
			@RequestParam(value = "beginTimeStr", required = false) String beginTimeStr,
			@RequestParam(value = "endTimeStr", required = false) String endTimeStr) {
		
		if (null != beginTimeStr && !"".equals(beginTimeStr)) {
			gjjpOrderQo.setCreateDateBegin(DateUtil.dateStr2BeginDate(beginTimeStr));
		}
		if (null != endTimeStr && !"".equals(endTimeStr)) {
			gjjpOrderQo.setCreateDateEnd(DateUtil.dateStr2EndDate(endTimeStr));
		}
		Pagination pagination = new Pagination();
		gjjpOrderQo.setInitAll(true);
		pagination.setCondition(gjjpOrderQo);

		if (pageNum != null) {
			pagination.setPageNo(pageNum);
		}
		if (pageSize != null) {
			pagination.setPageSize(pageSize);
		}

		pagination = gjjpOrderLocalService.queryPagination(pagination);
		// 经销商列表
		List<DealerDTO> dealerList = dealerService.queryList(new DealerQO());

		ModelAndView mav = new ModelAndView(
				"/airticket/customer_income_refund/gj_list.html");
		mav.addObject("pagination", pagination);
		mav.addObject("gjjpOrderQo", gjjpOrderQo);
		mav.addObject("dealerList", dealerList);
		mav.addObject("dataTransService",dataTransService);
		return mav;
	}

	/**
	 * 订单明细
	 */
	@RequestMapping("/airtkt/gj_customer/income_refund/detail")
	public ModelAndView detail(
			@RequestParam(value = "orderNo", required = false) String orderNo,
			@RequestParam(value = "supplierOrderNo", required = false) String supplierOrderNo) {

		ModelAndView mav = new ModelAndView(
				"/airticket/customer_income_refund/gj_detail.html");
		GJJPOrderQo qo = new GJJPOrderQo();
		qo.setId(orderNo);
		qo.setInitAll(true);
		//qo.setSupplierOrderId(supplierOrderNo);
		GJJPOrder gjjpOrder = gjjpOrderLocalService.queryUnique(qo);
		mav.addObject("gjjpOrder", gjjpOrder);
		mav.addObject("ORDER_STATUS",GJJPConstants.ORDER_STATUS_MAP);
		mav.addObject("dataTransService",dataTransService);
		return mav;
	}

	@RequestMapping("/airtkt/gj_customer/income_refund/cancel_jporder/page")
	public ModelAndView cancelJPOrderPage(
			@RequestParam(value = "orderNo", required = false) String orderNo) {

		ModelAndView mav = new ModelAndView(
				"/airticket/customer_income_refund/cancel.html");
		mav.addObject("orderNo", orderNo);
		return mav;
	}

}

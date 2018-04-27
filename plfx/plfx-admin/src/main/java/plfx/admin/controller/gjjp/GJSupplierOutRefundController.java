package plfx.admin.controller.gjjp;

import java.util.List;

import hg.common.page.Pagination;
import hg.common.util.DateUtil;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import plfx.gjjp.app.pojo.qo.GJJPOrderLogQo;
import plfx.gjjp.app.pojo.qo.GJJPOrderQo;
import plfx.gjjp.app.service.local.DataTransService;
import plfx.gjjp.app.service.local.GJJPOrderLocalService;
import plfx.gjjp.app.service.local.GJJPOrderLogLocalService;
import plfx.gjjp.domain.common.GJJPConstants;
import plfx.gjjp.domain.model.GJJPOrder;
import plfx.gjjp.domain.model.GJJPOrderLog;
import plfx.jp.pojo.dto.supplier.SupplierDTO;
import plfx.jp.qo.admin.supplier.SupplierQO;
import plfx.jp.spi.inter.supplier.SupplierService;

/**
 * 
 * @类功能说明：供应商平台支出/退款
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：guotx
 * @创建时间：2015年7月25日 11:31:39
 * @版本：V1.0
 * 
 */
@Controller
@RequestMapping("/airtkt/gjjp/supplier_outcome_refund")
public class GJSupplierOutRefundController {
	@Autowired
	private GJJPOrderLocalService gjjpOrderLocalService;
	@Autowired
	private SupplierService jpSupplierService;
	@Autowired
	private GJJPOrderLogLocalService gjjpOrderLogService;
	@Autowired
	private DataTransService cacheManagerService;

	/**
	 * 供应商平台支出退款明细
	 * 
	 * @param request
	 * @param model
	 * @param gjjpOrderQo
	 * @param pageNo
	 * @param pageSize
	 * @param beginTimeStr
	 * @param endTimeStr
	 * @return
	 */
	@RequestMapping(value="/list")
	public String list(
			HttpServletRequest request,
			Model model,
			@ModelAttribute GJJPOrderQo gjjpOrderQo,
			@RequestParam(value="pageNum",required=false) Integer pageNo, 
			@RequestParam(value = "numPerPage", required = false) Integer pageSize,
			@RequestParam(value = "beginTimeStr", required = false) String beginTimeStr,
			@RequestParam(value = "endTimeStr", required = false) String endTimeStr) {

		if (null != beginTimeStr && !"".equals(beginTimeStr)) {
			gjjpOrderQo.setCreateDateBegin(DateUtil
					.dateStr2BeginDate(beginTimeStr));
		}
		if (null != endTimeStr && !"".equals(endTimeStr)) {
			gjjpOrderQo.setCreateDateEnd(DateUtil.dateStr2EndDate(endTimeStr));
		}
		// 查询出所有供应商
		List<SupplierDTO> supplierDtoList = jpSupplierService
				.queryList(new SupplierQO());
		Pagination pagination = new Pagination();
		pageNo = pageNo == null ? new Integer(1) : pageNo;
		pageSize = pageSize == null ? new Integer(20) : pageSize;
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		// 初始化全部数据
		gjjpOrderQo.setInitAll(true);
		// 添加查询条件
		pagination.setCondition(gjjpOrderQo);
		pagination = gjjpOrderLocalService.queryPagination(pagination);

		model.addAttribute("pagination", pagination);

		model.addAttribute("supplierDtoList", supplierDtoList);

		model.addAttribute("gjjpOrderQo", gjjpOrderQo);
		model.addAttribute("airPortManager",cacheManagerService);
		return "/airticket/supplier_income_refund/gj_list.html";
	}

	@RequestMapping("/detail")
	public ModelAndView orderDetail(
			@RequestParam(value = "orderNo", required = false) String orderNo) {
		ModelAndView mav = new ModelAndView(
				"/airticket/supplier_income_refund/gj_detail.html");
		if (StringUtils.isBlank(orderNo)) {
			return mav;
		}
		GJJPOrderQo qo = new GJJPOrderQo();
		qo.setId(orderNo);
		qo.setInitAll(true);
		GJJPOrder gjjpOrder = gjjpOrderLocalService.queryUnique(qo);
		mav.addObject("gjjpOrder", gjjpOrder);
		//证件类型
		mav.addObject("ID_TYPE_MAP", cacheManagerService.getIntMap(GJJPConstants.IDTYPE_MAP));
		mav.addObject("airPortManager",cacheManagerService);
		//订单状态
		mav.addObject("orderStatusMap", cacheManagerService.getIntMap(GJJPConstants.ORDER_STATUS_MAP));
		GJJPOrderLogQo logQo = new GJJPOrderLogQo();
		logQo.setJpOrderQo(qo);
		List<GJJPOrderLog> logs = gjjpOrderLogService.queryList(logQo);
		//保存日志
		mav.addObject("LOGS", logs);
		return mav;
	}
}

package hgfx.web.controller.orderList;

import hg.framework.common.model.LimitQuery;
import hg.framework.common.model.Pagination;
import hg.fx.domain.Distributor;
import hg.fx.domain.DistributorUser;
import hg.fx.domain.MileOrder;
import hg.fx.domain.ProductInUse;
import hg.fx.enums.MileOrderOrderWayEnum;
import hg.fx.spi.MileOrderSPI;
import hg.fx.spi.ProductInUseSPI;
import hg.fx.spi.ProductSPI;
import hg.fx.spi.qo.MileOrderSQO;
import hg.fx.spi.qo.ProductInUseSQO;
import hg.fx.util.DateUtil;
import hg.fx.util.MileOrderServiceUtil;
import hgfx.web.controller.sys.BaseController;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;

/**
 * 订单列表controller
 * 
 * @author zqq
 * @date 2016-6-6上午10:05:53
 * @since
 */
@Controller
@RequestMapping(value = "/orderManage")
public class OrderListController extends BaseController {

	@Autowired
	public MileOrderSPI mileOrderService;
	@Autowired
	public ProductSPI productSPIService;
	@Autowired
	private ProductInUseSPI productInUseSPI;

	/**
	 * 订单列表查询
	 * 
	 * @author zqq
	 * @since hgfx-web
	 * @date 2016-6-6 上午10:08:41
	 * @return
	 */
	@RequestMapping(value = "/orderList")
	public String orderList(
			Model model,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "numPerPage", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "orderBy", defaultValue = "date") String orderBy,
			@RequestParam(value = "orderIndex", defaultValue = "-1") Integer orderIndex,
			HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute MileOrderSQO orderSQO) {
		//用户商品查询
		ProductInUseSQO sqo = new ProductInUseSQO();
		DistributorUser distributorUser = getSessionUserInfo(request.getSession());
		sqo.setDistributorId(distributorUser.getDistributor().getId());
		List<ProductInUse> productInUses = productInUseSPI.queryList(sqo);
		
		model.addAttribute("proList", productInUses);
		
		LimitQuery limitQuery = new LimitQuery();
		limitQuery.setPageNo(pageNum);
		limitQuery.setPageSize(pageSize);
		orderSQO.setLimit(limitQuery);
		// 获得当前商户帐号信息
		DistributorUser user = getUser(request.getSession());
		Distributor distri = user.getDistributor();
		// 查询本商户订单信息
		orderSQO.setDistributorId(distri.getId());
		//如果是第一次进入，则默认为查询当天
		if(orderSQO.getStrImportDate()==null){
			orderSQO.setStrImportDate(DateUtil.formatDate2(new Date()));
			orderSQO.setEndImportDate(DateUtil.formatDate2(new Date()));
		}
		// 设置排序
		if (orderBy.equals("date")) {
			if (orderIndex == -1) {
				orderSQO.setOrderWay(MileOrderOrderWayEnum.ORDER_BY_CREATEDATE_DESC);
			} else {
				orderSQO.setOrderWay(MileOrderOrderWayEnum.ORDER_BY_CREATEDATE_AES);
			}
		} else {
			if (orderIndex == -1) {
				orderSQO.setOrderWay(MileOrderOrderWayEnum.ORDER_BY_NUM_DESC);
			} else {
				orderSQO.setOrderWay(MileOrderOrderWayEnum.ORDER_BY_NUM_AES);
			}
		}
		//关联查询所有数据
		//orderSQO.setJoin(true);
		Pagination<MileOrder> pagination = mileOrderService
				.queryPagination(orderSQO);
		model.addAttribute("pagination", pagination);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("mileOrderSqo", orderSQO);
		model.addAttribute("orderBy", orderBy);
		model.addAttribute("orderIndex", orderIndex);
		double totalPageNum = 1;
		if (pagination.getTotalCount() > pageSize) {
			totalPageNum = Math.ceil(Double.parseDouble(Integer
					.toString(pagination.getTotalCount()))
					/ Double.parseDouble(Integer.toString(pageSize)));
		}
		model.addAttribute("totalPageNum", totalPageNum);
		return "/order/order-manage-list.html";
	}

	/**
	 * 导出订单列表
	 * 
	 * @author zqq
	 * @since hgfx-web
	 * @date 2016-6-2 下午5:07:11
	 * @param request
	 * @param model
	 * @param mileOrderSQO
	 * @param response
	 */
	@RequestMapping(value = "/export")
	public void export(
			HttpServletRequest request,
			Model model,
			@ModelAttribute MileOrderSQO mileOrderSQO,
			HttpServletResponse response,
			@RequestParam(value = "orderBy", defaultValue = "date") String orderBy,
			@RequestParam(value = "orderIndex", defaultValue = "-1") Integer orderIndex) {
		// 获得当前商户帐号信息
		DistributorUser user = getUser(request.getSession());
		Distributor distri = user.getDistributor();
		// 查询本商户订单信息
		mileOrderSQO.setDistributorId(distri.getId());
		// 设置排序
		if (orderBy.equals("date")) {
			if (orderIndex == -1) {
				mileOrderSQO
						.setOrderWay(MileOrderOrderWayEnum.ORDER_BY_CREATEDATE_DESC);
			} else {
				mileOrderSQO
						.setOrderWay(MileOrderOrderWayEnum.ORDER_BY_CREATEDATE_AES);
			}
		} else {
			if (orderIndex == -1) {
				mileOrderSQO
						.setOrderWay(MileOrderOrderWayEnum.ORDER_BY_NUM_DESC);
			} else {
				mileOrderSQO
						.setOrderWay(MileOrderOrderWayEnum.ORDER_BY_NUM_AES);
			}
		}
		//关联查询所有数据
		//mileOrderSQO.setJoin(true);
		List<MileOrder> list = mileOrderService.queryList(mileOrderSQO);
		HSSFWorkbook excel = MileOrderServiceUtil.exportOrder3Excel(list);
		try {
			String path = this.getClass().getResource("/").toString()
					.replace("file:", "");
			int end = path.length() - "WEB-INF/classes/".length();
			path = path.substring(0, end) + File.separator + "excel";
			String fileName = path + File.separator + "订单列表"
					+ DateUtil.formatDateTime3(new Date()) + ".xls";
			outputExcel(excel, response, path, fileName);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

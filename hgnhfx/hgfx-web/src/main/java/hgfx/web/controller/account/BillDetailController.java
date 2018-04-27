package hgfx.web.controller.account;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import hg.framework.common.model.LimitQuery;
import hg.framework.common.model.Pagination;
import hg.fx.domain.DistributorUser;
import hg.fx.domain.ProductInUse;
import hg.fx.domain.ReserveRecord;
import hg.fx.spi.ProductInUseSPI;
import hg.fx.spi.ReserveRecordSPI;
import hg.fx.spi.qo.ProductInUseSQO;
import hg.fx.spi.qo.ReserveRecordSQO;
import hgfx.web.controller.sys.BaseController;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 个人管理--账户管理--账单明细
 * 
 * @author 杨康
 * @date 2016-06-06
 * */
@Controller
public class BillDetailController extends BaseController {

	@Autowired
	private ReserveRecordSPI reserveRecordService;

	@Autowired
	private ProductInUseSPI productInUseService;

	private SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private SimpleDateFormat dateToStr = new SimpleDateFormat("yyyy-MM-dd");

	@RequestMapping("/bill/list")
	public ModelAndView billList(
			HttpServletRequest request,
			@RequestParam(value = "prodName", required = false) String prodName,
			@RequestParam(value = "pageNum", required = false) Integer pageNum,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "tradeNo", required = false) String tradeNo,
			@RequestParam(value = "status", required = false) Integer status,
			@RequestParam(value = "tradeDateStart", required = false) String tradeDateStart,
			@RequestParam(value = "tradeDateEnd", required = false) String tradeDateEnd) {

		ModelAndView mav = new ModelAndView("/billdetail/bill-list.html");
		// 查询当前用户
		DistributorUser user = getSessionUserInfo(request.getSession());

		// 查询当前用户所属商户的使用商品
		ProductInUseSQO pis = new ProductInUseSQO();
		pis.setDistributorId(user.getDistributor().getId());
		List<ProductInUse> pl = productInUseService.queryList(pis);
		mav.addObject("pInUse", pl);

		ReserveRecordSQO sqo = new ReserveRecordSQO();
		LimitQuery limitQuery = new LimitQuery();

		sqo.setDistributorID(user.getDistributor().getId());

		// 查询商品名称
		if (StringUtils.isNotBlank(prodName))
			sqo.setProdName(prodName.trim());

		// 查询交易号
		if (StringUtils.isNotBlank(tradeNo))
			sqo.setTradeNo(tradeNo.trim());

		// 查询状态
		if (status != null)
			sqo.setStatusArray(new Integer[]{status});
		else
			// 查询记录为一下三种值
			sqo.setStatusArray(new Integer[] {
				ReserveRecord.RECORD_STATUS_CHONGZHI_SUCC,
				ReserveRecord.RECORD_STATUS_KOUKUAN_SUCC,
				ReserveRecord.RECORD_STATUS_REFUND_SUCC });

		String today = dateToStr.format(new Date());

		// 查询交易时间
		if (StringUtils.isNotBlank(tradeDateStart)) {
			try {
				sqo.setTradeDateStart(sd.parse(tradeDateStart + " 00:00:00"));
			} catch (ParseException e) {
				sqo.setTradeDateStart(null);
			}
		} else {
			try {
				sqo.setTradeDateStart(sd.parse(today + " 00:00:00"));
			} catch (ParseException e) {
				sqo.setTradeDateStart(null);
			}
		}

		if (StringUtils.isNotBlank(tradeDateEnd)) {
			try {
				sqo.setTradeDateEnd(sd.parse(tradeDateEnd + " 23:59:59"));
			} catch (ParseException e) {
				sqo.setTradeDateEnd(null);
			}
		} else {
			try {
				sqo.setTradeDateEnd(sd.parse(today + " 23:59:59"));
			} catch (ParseException e) {
				sqo.setTradeDateEnd(null);
			}
		}

		if (pageNum != null) {
			limitQuery.setPageNo(pageNum);
		} else {
			pageNum = 1;
			limitQuery.setPageNo(pageNum);
		}

		if (pageSize != null) {
			limitQuery.setPageSize(pageSize);
		} else {
			pageSize = 13;
			limitQuery.setPageSize(pageSize);
		}

		sqo.setLimit(limitQuery);

		Pagination<ReserveRecord> pagination = reserveRecordService
				.queryReserveRecordPagination(sqo);

		mav.addObject("pagination", pagination);
		mav.addObject("prodName", prodName);
		mav.addObject("tradeNo", tradeNo);
		mav.addObject("tradeDateStart",
				tradeDateStart == null ? sd.format(new Date()).substring(0, 10)
						: tradeDateStart);
		mav.addObject("tradeDateEnd",
				tradeDateEnd == null ? sd.format(new Date()).substring(0, 10)
						: tradeDateEnd);
		mav.addObject("status", status);
		mav.addObject("pageNum", pageNum);
		double totalPageNum = 1;
		if (pagination.getTotalCount() > pageSize) {
			totalPageNum = Math.ceil(Double.parseDouble(Integer
					.toString(pagination.getTotalCount()))
					/ Double.parseDouble(Integer.toString(pageSize)));
		}
		mav.addObject("totalPageNum", totalPageNum);
		return mav;
	}

}

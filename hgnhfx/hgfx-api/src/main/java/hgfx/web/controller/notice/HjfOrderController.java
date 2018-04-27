
package hgfx.web.controller.notice;

import hg.fx.domain.MileOrder;
import hg.fx.spi.DistributorSPI;
import hg.fx.spi.MileOrderSPI;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 




import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 接受汇积分的订单已发送到南航，订单处理成败与否的通知
 * @author gaoce,xinglj
 *
 */
@Controller
@RequestMapping("/hjfOrder")
public class HjfOrderController   {
	@Autowired
	MileOrderSPI mileOrderService;
	@Autowired
	DistributorSPI distributorService;

	final static String navTabRel = "mileOrder";
	@ResponseBody
	@RequestMapping("/notice")
	public String noticeMileOrder(Model model, HttpServletRequest request, @ModelAttribute MileOrder mileOrder) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String csairName=request.getParameter("csairName");
		String payDate=request.getParameter("payDate");
		String toCsairDate=request.getParameter("toCsairDate");
		try {
			mileOrder.setCsairName(URLDecoder.decode(mileOrder.getCsairName(),"utf-8"));
			mileOrder.setPayDate(sdf.parse(payDate));
			mileOrder.setToCsairDate(sdf.parse(toCsairDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
//		return mileOrderService.saveHjfMileOrder(mileOrder); 保存汇积分订单到 分销系统中，以便可以查到汇积分的订单信息。
		//目前不保存汇积分的订单
		return "1";
	}
	@ResponseBody
	@RequestMapping("/success")
	public String MileOrdersuccess(Model model, HttpServletRequest request) {
		String orderCode=request.getParameter("orderCode");
		String csairInfo=request.getParameter("csairInfo");
//		return mileOrderService.updateReturnMileOrder(orderCode, csairInfo, MileOrder.STATUS_CSAIR_SUCCEED);
		//更新汇积分订单状态。目前不保存汇积分的订单,所以不需要接受通知
		return "1";
	}
	@ResponseBody
	@RequestMapping("/fail")
	public String MileOrderfail(Model model, HttpServletRequest request) {
		String orderCode=request.getParameter("orderCode");
		String csairInfo=request.getParameter("csairInfo");
		String info="";
		try {
			info=URLDecoder.decode(csairInfo,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
//		return mileOrderService.updateReturnMileOrder(orderCode, info, MileOrder.STATUS_CSAIR_ERROR);
		//更新汇积分订单状态。目前不保存汇积分的订单,所以不需要接受通知
		return "1";
	}


}

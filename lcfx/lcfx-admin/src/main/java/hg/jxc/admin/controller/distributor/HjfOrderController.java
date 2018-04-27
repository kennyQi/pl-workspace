
package hg.jxc.admin.controller.distributor;

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

import jxc.app.service.distributor.DistributorService;
import jxc.app.service.distributor.MileOrderService;
import jxc.domain.model.distributor.Distributor;
import jxc.domain.model.distributor.MileOrder;
import jxc.domain.util.CodeUtil;
import jxc.domain.util.Tools;

import hg.common.model.qo.DwzPaginQo;
import hg.common.page.Pagination;
import hg.common.util.ClassPathTool;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.ExcelUtils;
import hg.common.util.UUIDGenerator;
import hg.jxc.admin.common.CommandUtil;
import hg.jxc.admin.common.JsonResultUtil;
import hg.jxc.admin.controller.BaseController;
import hg.pojo.command.mileOrder.CheckMileOrderCommand;
import hg.pojo.command.mileOrder.CreateMileOrderCommand;
import hg.pojo.command.mileOrder.ImportMileOrderCommand;
import hg.pojo.command.mileOrder.ModifyMileOrderCommand;
import hg.pojo.command.mileOrder.RemoveMileOrderCommand;
import hg.pojo.qo.DistributorQo;
import hg.pojo.qo.MileOrderQo;
import hg.system.model.auth.AuthUser;

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

@Controller
@RequestMapping("/hjfOrder")
public class HjfOrderController extends BaseController {
	@Autowired
	MileOrderService mileOrderService;
	@Autowired
	DistributorService distributorService;

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
		return mileOrderService.saveHjfMileOrder(mileOrder);
	}
	@ResponseBody
	@RequestMapping("/success")
	public String MileOrdersuccess(Model model, HttpServletRequest request) {
		String orderCode=request.getParameter("orderCode");
		String csairInfo=request.getParameter("csairInfo");
		return mileOrderService.updateReturnMileOrder(orderCode, csairInfo, MileOrder.STATUS_CSAIR_SUCCEED);
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
		return mileOrderService.updateReturnMileOrder(orderCode, info, MileOrder.STATUS_CSAIR_ERROR);
	}


}

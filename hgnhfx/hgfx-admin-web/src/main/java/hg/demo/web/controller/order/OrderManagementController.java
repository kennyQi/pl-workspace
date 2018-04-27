package hg.demo.web.controller.order;

import hg.demo.web.common.UserInfo;
import hg.demo.web.controller.BaseController;
import hg.framework.common.model.LimitQuery;
import hg.framework.common.model.Pagination;
import hg.fx.command.mileOrder.CheckMileOrderCommand;
import hg.fx.command.mileOrder.ConfirmMileOrderCommand;
import hg.fx.domain.Channel;
import hg.fx.domain.Distributor;
import hg.fx.domain.MileOrder;
import hg.fx.domain.OperationLog;
import hg.fx.domain.Product;
import hg.fx.enums.MileOrderOrderWayEnum;
import hg.fx.spi.ChannelSPI;
import hg.fx.spi.DistributorSPI;
import hg.fx.spi.MileOrderSPI;
import hg.fx.spi.ProductSPI;
import hg.fx.spi.qo.ChannelSQO;
import hg.fx.spi.qo.DistributorSQO;
import hg.fx.spi.qo.MileOrderSQO;
import hg.fx.spi.qo.ProductSQO;
import hg.fx.util.DateUtil;
import hg.fx.util.MileOrderServiceUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author zqq
 * @date 2016-5-31下午3:17:32
 * @since
 */
@Controller
@RequestMapping(value = "/orderManagement")
public class OrderManagementController extends BaseController{
	@Autowired
	public MileOrderSPI mileOrderService;
	@Autowired
	public ChannelSPI channelSPIService;
	@Autowired
	public ProductSPI productSPIService;
	@Autowired
	public DistributorSPI distributorService;

	/**
	 * 订单列表页
	 * 
	 * @author zqq
	 * @since hgfx-admin-web
	 * @date 2016-6-1 下午2:11:47
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/orderList")
	public String toOrderList(
			HttpSession httpSession,
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@ModelAttribute MileOrderSQO mileOrderSQO,
			@RequestParam(value = "pageNum", defaultValue = "1") String currpage,
			@RequestParam(value = "numPerPage", defaultValue = "20") String pagesize) {
		// 设置分页条件
		Pagination<MileOrder> pagination = new Pagination<MileOrder>();
		mileOrderSQO.setLimit(new LimitQuery());
		mileOrderSQO.getLimit().setPageNo(Integer.parseInt(currpage));
		mileOrderSQO.getLimit().setPageSize(Integer.parseInt(pagesize));
		// 渠道商查询
		ChannelSQO channelSQO = new ChannelSQO();
		List<Channel> channelList = channelSPIService.queryList(channelSQO);
		// 商品查询
		ProductSQO proSQO = new ProductSQO();
		List<Product> proList = new ArrayList<Product>();
		// 如果选择了渠道使用渠道id查询商品
		if (StringUtils.isNotBlank(mileOrderSQO.getChannelId())) {
			proSQO.setChannelID(mileOrderSQO.getChannelId());
			proList = productSPIService.queryProductList(proSQO);
		} else if (channelList.size() > 0) {// 没有选择渠道 查询全部
			//proSQO.setChannelID(channelList.get(0).getId());
			proList = productSPIService.queryProductList(proSQO);
			//mileOrderSQO.setChannelId(channelList.get(0).getId());
		} else {// 渠道为空 商品返回为空
			proList = new ArrayList<Product>();
		}
		// 商户查询
		DistributorSQO distriSQO = new DistributorSQO();
		//distriSQO.setDiscountType(DistributorSQO.DISCOUNT_TYPE_FIXED_PRICE);
		List<Distributor> distriList = distributorService.queryList(distriSQO);
		// 订单列表查询的都是非待审核订单 即status！=1 不管type是1还是2
		mileOrderSQO.setNonStatus(MileOrder.STATUS_NO_CHECK);
		//如果是点击导航进入 所有条件为null，此时将查询时间设置成当前时间
		if(mileOrderSQO.getStrImportDate()==null){
			mileOrderSQO.setStrImportDate(DateUtil.formatDate2(new Date()));
			mileOrderSQO.setEndImportDate(DateUtil.formatDate2(new Date()));
		}
		//关联查询所有数据
		//mileOrderSQO.setJoin(true);
		pagination = mileOrderService.queryPagination(mileOrderSQO);
		model.addAttribute("pagination", pagination);
		model.addAttribute("proList", proList);
		model.addAttribute("channelList", channelList);
		model.addAttribute("distriList", distriList);
		model.addAttribute("mileOrderSqo", mileOrderSQO);
		return "/order/list.ftl";
	}

	/**
	 * 返回渠道级联的商户
	 * 
	 * @author zqq
	 * @since hgfx-admin-web
	 * @date 2016-6-2 下午2:44:48
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/proJsonList")
	public String proJsonList(HttpServletRequest request) {
		String channelId = request.getParameter("value");
		// 商品查询
		ProductSQO proSQO = new ProductSQO();
		List<Product> proList = new ArrayList<Product>();
		// 如果选择了渠道使用渠道id查询商品
		proSQO.setChannelID(channelId);
		proList = productSPIService.queryProductList(proSQO);
		// 转化成json数组
		StringBuffer sb = new StringBuffer();
		sb.append("[[\"\",\"全部\"],");
		for (Product item : proList) {
			sb.append("[");
			sb.append("\"" + item.getId() + "\",");
			sb.append("\"" + item.getProdName() + "\"");
			sb.append("],");
		}
		// 将最后一个,替换成]
		sb.replace(sb.length() - 1, sb.length(), "]");
		String resStr = sb.toString();
		return resStr;
	}

	@RequestMapping(value = "/pendingAuditList")
	public String toPendingAuditList(
			HttpSession httpSession,
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@ModelAttribute MileOrderSQO mileOrderSQO,
			@RequestParam(value = "pageNum", defaultValue = "1") String currpage,
			@RequestParam(value = "numPerPage", defaultValue = "20") String pagesize) {
		// 设置分页条件
		Pagination<MileOrder> pagination = new Pagination<MileOrder>();
		mileOrderSQO.setLimit(new LimitQuery());
		mileOrderSQO.getLimit().setPageNo(Integer.parseInt(currpage));
		mileOrderSQO.getLimit().setPageSize(Integer.parseInt(pagesize));
		// 渠道商查询
		ChannelSQO channelSQO = new ChannelSQO();
		List<Channel> channelList = channelSPIService.queryList(channelSQO);
		// 商品查询
		ProductSQO proSQO = new ProductSQO();
		List<Product> proList = new ArrayList<Product>();
		// 如果选择了渠道使用渠道id查询商品
		if (StringUtils.isNotBlank(mileOrderSQO.getChannelId())) {
			proSQO.setChannelID(mileOrderSQO.getChannelId());
			proList = productSPIService.queryProductList(proSQO);
		} else if (channelList.size() > 0) {// 没有选择渠道 查询全部
			//proSQO.setChannelID(channelList.get(0).getId());
			proList = productSPIService.queryProductList(proSQO);
			//mileOrderSQO.setChannelId(channelList.get(0).getId());
		} else {// 渠道为空 商品返回为空
			proList = new ArrayList<Product>();
		}
		// 商户查询
		DistributorSQO distriSQO = new DistributorSQO();
		List<Distributor> distriList = distributorService.queryList(distriSQO);
		//待审核订单列表查询的是待审核订单，即status=1 不管type是1还是2
		mileOrderSQO.setStatus(MileOrder.STATUS_NO_CHECK);
		//如果是点击导航进入 所有条件为null，此时将查询时间设置成当前时间
		if(mileOrderSQO.getStrImportDate()==null){
			mileOrderSQO.setStrImportDate(DateUtil.formatDate2(new Date()));
			mileOrderSQO.setEndImportDate(DateUtil.formatDate2(new Date()));
		}
		//关联查询所有数据
		mileOrderSQO.setJoin(true);
		pagination = mileOrderService.queryPagination(mileOrderSQO);
		model.addAttribute("pagination", pagination);
		model.addAttribute("proList", proList);
		model.addAttribute("channelList", channelList);
		model.addAttribute("distriList", distriList);
		model.addAttribute("mileOrderSqo", mileOrderSQO);
		return "/order/pendingAuditList.ftl";
	}
	/**
	 * 订单确认页面
	 * @author zqq
	 * @since hgfx-admin-web
	 * @date 2016-6-28 上午9:45:05 
	 * @param httpSession
	 * @param request
	 * @param response
	 * @param model
	 * @param mileOrderSQO
	 * @param currpage
	 * @param pagesize
	 * @return
	 */
	@RequestMapping(value = "/toCheckOrderList")
	public String toCheckOrderList(
			HttpSession httpSession,
			HttpServletRequest request,
			HttpServletResponse response,
			Model model,
			@ModelAttribute MileOrderSQO mileOrderSQO,
			@RequestParam(value = "pageNum", defaultValue = "1") String currpage,
			@RequestParam(value = "numPerPage", defaultValue = "20") String pagesize,
			@RequestParam(value = "orderWayNum", defaultValue = "0") String orderWayNum) {
		// 设置分页条件
		Pagination<MileOrder> pagination = new Pagination<MileOrder>();
		mileOrderSQO.setLimit(new LimitQuery());
		mileOrderSQO.getLimit().setPageNo(Integer.parseInt(currpage));
		mileOrderSQO.getLimit().setPageSize(Integer.parseInt(pagesize));
		// 渠道商查询
		ChannelSQO channelSQO = new ChannelSQO();
		List<Channel> channelList = channelSPIService.queryList(channelSQO);
		// 商品查询
		ProductSQO proSQO = new ProductSQO();
		List<Product> proList = new ArrayList<Product>();
		// 如果选择了渠道使用渠道id查询商品
		if (StringUtils.isNotBlank(mileOrderSQO.getChannelId())) {
			proSQO.setChannelID(mileOrderSQO.getChannelId());
			proList = productSPIService.queryProductList(proSQO);
		} else if (channelList.size() > 0) {// 没有选择渠道 查询全部
			//proSQO.setChannelID(channelList.get(0).getId());
			proList = productSPIService.queryProductList(proSQO);
			//mileOrderSQO.setChannelId(channelList.get(0).getId());
		} else {// 渠道为空 商品返回为空
			proList = new ArrayList<Product>();
		}
		// 商户查询
		DistributorSQO distriSQO = new DistributorSQO();
		List<Distributor> distriList = distributorService.queryList(distriSQO);
		//待确认的列表
		mileOrderSQO.setStatus(MileOrder.STATUS_CHECK_PASS);
		//排序条件
		if("1".equals(orderWayNum)){
			mileOrderSQO.setOrderWay(MileOrderOrderWayEnum.ORDER_BY_NUM_DESC);
		}else if("2".equals(orderWayNum)){
			mileOrderSQO.setOrderWay(MileOrderOrderWayEnum.ORDER_BY_NUM_AES);
		}else{
			mileOrderSQO.setOrderWay(MileOrderOrderWayEnum.ORDER_BY_CREATEDATE_DESC);
		}
		//如果是点击导航进入 所有条件为null，此时将查询时间设置成当前时间
		if(mileOrderSQO.getStrImportDate()==null){
			mileOrderSQO.setStrImportDate(DateUtil.formatDate2(new Date()));
			mileOrderSQO.setEndImportDate(DateUtil.formatDate2(new Date()));
		}
		//关联查询所有数据
		pagination = mileOrderService.queryPagination(mileOrderSQO);
		model.addAttribute("pagination", pagination);
		model.addAttribute("proList", proList);
		model.addAttribute("channelList", channelList);
		model.addAttribute("distriList", distriList);
		model.addAttribute("mileOrderSqo", mileOrderSQO);
		model.addAttribute("orderWayNum", orderWayNum);
		return "/order/toCheckOrderList.ftl";
	}
	/**
	 * 导出订单列表
	 * @author zqq
	 * @since hgfx-admin-web
	 * @date 2016-6-2 下午5:07:11 
	 * @param request
	 * @param model
	 * @param mileOrderSQO
	 * @param response
	 */
	@RequestMapping(value = "/export")
	public void export(HttpServletRequest request, Model model, @ModelAttribute MileOrderSQO mileOrderSQO,HttpServletResponse response) {
		//待审核订单列表查询的是待审核订单，即status=1 不管type是1还是2
		mileOrderSQO.setNonStatus(MileOrder.STATUS_NO_CHECK);
		//关联查询所有数据
		//mileOrderSQO.setJoin(true);
		List<MileOrder> list = mileOrderService.queryList(mileOrderSQO);
		HSSFWorkbook excel = MileOrderServiceUtil.exportOrder2Excel(list);
		try {
			String path = this.getClass().getResource("/").toString().replace("file:", "");
			int end = path.length() - "WEB-INF/classes/".length();  
		    path = path.substring(0, end)+File.separator+"excel";
		    String fileName = path+ File.separator+"订单列表"+
					DateUtil.formatDateTime3(new Date())+".xls";
			outputExcel(excel, response,path,fileName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 跳转至审核通过页面
	 * @author zqq
	 * @since hgfx-admin-web
	 * @date 2016-6-2 下午6:23:26 
	 * @param request
	 * @param model
	 * @param mileOrderSQO
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/toCheck")
	public String toChke(HttpServletRequest request, Model model, @ModelAttribute MileOrderSQO mileOrderSQO,HttpServletResponse response
			,@ModelAttribute CheckMileOrderCommand cmd) {
		model.addAttribute("id", mileOrderSQO.getId());
		if(cmd.getIds()!=null&&cmd.getIds().size()!=0){
			request.getSession().setAttribute("auditIds", cmd.getIds());
		}
		return "/order/checkOrder.ftl";
	}
	/**
	 * 审核通过一个
	 * @author zqq
	 * @since hgfx-admin-web
	 * @date 2016-6-2 下午6:23:26 
	 * @param request
	 * @param model
	 * @param mileOrderSQO
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/passOne")
	public Map<String, Object> pass(HttpServletRequest request, Model model, @ModelAttribute CheckMileOrderCommand cmd,HttpServletResponse response) {
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			UserInfo userInfo = getUserInfo(request.getSession());
			cmd.setCheckPersonId(userInfo.getUserId());
			cmd.setAduitPerson(userInfo.getDisplayName());
			String content;
			if(StringUtils.isNotBlank(cmd.getId())){
				// 将选中的id放入数组中(service中只会处理List)
				List<String> orderIdList = new  ArrayList<String>();
				orderIdList.add(cmd.getId());
				cmd.setIds(orderIdList);
				content = "异常订单审核通过操作,\t订单id("+cmd.getId()+")";
				saveLog(content, request,OperationLog.OPERATION_TYPE_CHECK_EXCEPTION_ORDER);
			}else{
				cmd.setIds((List<String>)request.getSession().getAttribute("auditIds"));
				request.getSession().removeAttribute("auditIds");
				content = "异常订单审核批量通过操作,\t订单ids"+cmd.getIdsString();
				saveLog(content, request,OperationLog.OPERATION_TYPE_CHECK_EXCEPTION_ORDER);
			}
			//提交功过审核,审核通过以后 status为审核通过 type不变 依然为1异常订单
			mileOrderService.batchCheck(cmd, true);
			//添加操作日志
			map.put("statusCode", 200);
			map.put("callbackType","closeCurrent");
			map.put("message", "操作成功");
		} catch (Exception e) {
			//添加操作日志
			if(StringUtils.isNotBlank(cmd.getId())){
	        	String content = "异常订单审核通过操作,\t订单id("+cmd.getId()+")"+
	        			"操作失败，异常："+e.getMessage();
	        	saveLog(content, request,OperationLog.OPERATION_TYPE_CHECK_EXCEPTION_ORDER);
			}else{
				String content = "异常订单审核批量通过操作,\t订单ids"+cmd.getIdsString()+
	        			"操作失败，异常："+e.getMessage();
	        	saveLog(content, request,OperationLog.OPERATION_TYPE_CHECK_EXCEPTION_ORDER);
			}
			map.put("statusCode", 300);
			map.put("callbackType","closeCurrent");
			map.put("message", "操作失败");
			e.printStackTrace();
		}
		return  map;
	}
	/**
	 * 审核批量通过(废弃)
	 * @author zqq
	 * @since hgfx-admin-web
	 * @date 2016-6-2 下午6:23:26 
	 * @param request
	 * @param model
	 * @param mileOrderSQO
	 * @param response
	 * @return
	 */
	/* @ResponseBody
	@RequestMapping(value = "/passMany")
	public Map<String, Object> passMany(HttpServletRequest request, Model model, @ModelAttribute CheckMileOrderCommand cmd,HttpServletResponse response) {
		 Map<String, Object> map=new HashMap<String, Object>();
			try {
				UserInfo userInfo = getUserInfo(request.getSession());
				cmd.setCheckPersonId(userInfo.getUserId());
				//提交功过审核,审核通过以后 status为审核通过 type不变 依然为1异常订单
				mileOrderService.batchCheck(cmd, true);
				//添加操作日志
	        	String content = "异常订单审核批量通过操作,\t订单ids"+cmd.getIdsString();
	        	saveLog(content, request,OperationLog.OPERATION_TYPE_CHECK_EXCEPTION_ORDER);
				map.put("statusCode", 200);
				map.put("callbackType",null);
				map.put("message", "操作成功");
			} catch (Exception e) {
				//添加操作日志
	        	String content = "异常订单审核批量通过操作,\t订单ids"+cmd.getIdsString()+
	        			"操作失败，异常："+e.getMessage();
	        	saveLog(content, request,OperationLog.OPERATION_TYPE_CHECK_EXCEPTION_ORDER);
				map.put("statusCode", 300);
				map.put("callbackType","closeCurrent");
				map.put("message", "操作失败");
				e.printStackTrace();
			}
			return  map;
	}*/
	/**
	 * 审核拒绝一个
	 * @author zqq
	 * @since hgfx-admin-web
	 * @date 2016-6-2 下午6:23:26 
	 * @param request
	 * @param model
	 * @param mileOrderSQO
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/refuseOne")
	public Map<String, Object> refuseOne(HttpServletRequest request, Model model, @ModelAttribute CheckMileOrderCommand cmd,HttpServletResponse response) {
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			UserInfo userInfo = getUserInfo(request.getSession());
			cmd.setCheckPersonId(userInfo.getUserId());
			//将选中的id放入数组中(service中只会处理List)
			List<String> orderIdList = new  ArrayList<String>();
			orderIdList.add(cmd.getId());
			cmd.setIds(orderIdList);
			//提交拒绝审核， status为拒绝 type不变 依然为1异常订单
			mileOrderService.batchCheck(cmd, false);
			//添加操作日志
        	String content = "异常订单审核拒绝操作,\t订单id("+cmd.getId()+")";
        	saveLog(content, request,OperationLog.OPERATION_TYPE_CHECK_EXCEPTION_ORDER);
			map.put("statusCode", 200);
			map.put("callbackType",null);
			map.put("message", "操作成功");
		} catch (Exception e) {
			//添加操作日志
        	String content = "异常订单审核拒绝操作,\t订单id("+cmd.getId()+")"+
        			"操作失败，异常："+e.getMessage();
        	saveLog(content, request,OperationLog.OPERATION_TYPE_CHECK_EXCEPTION_ORDER);
			map.put("statusCode", 300);
			map.put("callbackType",null);
			map.put("message", "操作失败");
			e.printStackTrace();
		}
		return  map;
	}
	/**
	 * 审核批量拒绝
	 * @author zqq
	 * @since hgfx-admin-web
	 * @date 2016-6-2 下午6:23:26 
	 * @param request
	 * @param model
	 * @param mileOrderSQO
	 * @param response
	 * @return
	 */
	 @ResponseBody
	@RequestMapping(value = "/refuseMany")
	public Map<String, Object>  refuseMany(HttpServletRequest request, Model model, @ModelAttribute CheckMileOrderCommand cmd,HttpServletResponse response) {
		 Map<String, Object> map=new HashMap<String, Object>();
			try {
				UserInfo userInfo = getUserInfo(request.getSession());
				cmd.setCheckPersonId(userInfo.getUserId());
				//提交拒绝审核， status为拒绝 type不变 依然为1异常订单
				mileOrderService.batchCheck(cmd, false);
				//添加操作日志
	        	String content = "异常订单审核批量拒绝操作,\t订单ids"+cmd.getIdsString();
	        	saveLog(content, request,OperationLog.OPERATION_TYPE_CHECK_EXCEPTION_ORDER);
				map.put("statusCode", 200);
				map.put("callbackType",null);
				map.put("message", "操作成功");
			} catch (Exception e) {
				//添加操作日志
	        	String content = "异常订单审核批量拒绝操作,\t订单ids"+cmd.getIdsString()+
	        			"操作失败，异常："+e.getMessage();
	        	saveLog(content, request,OperationLog.OPERATION_TYPE_CHECK_EXCEPTION_ORDER);
				map.put("statusCode", 300);
				map.put("callbackType","closeCurrent");
				map.put("message", "操作失败");
				e.printStackTrace();
			}
			return  map;
	}
	 
	 /**
		 * 跳转至订单确认拒绝页面
		 * @author zqq
		 * @since hgfx-admin-web
		 * @date 2016-6-2 下午6:23:26 
		 * @param request
		 * @param model
		 * @param mileOrderSQO
		 * @param response
		 * @return
		 */
		@RequestMapping(value = "/toRefuse")
		public String toRefuse(HttpServletRequest request, Model model,HttpServletResponse response, 
				@ModelAttribute CheckMileOrderCommand cmd) {
			model.addAttribute("id", cmd.getId());
			if(cmd.getIds()!=null&&cmd.getIds().size()!=0){
				request.getSession().setAttribute("checkIds", cmd.getIds());
			}
			return "/order/refuseOrder.ftl";
		}
	 
	 /**
		 * 订单确认拒绝
		 * @author zqq
		 * @since hgfx-admin-web
		 * @date 2016年6月28日 10:21:42
		 * @param request
		 * @param model
		 * @param mileOrderSQO
		 * @param response
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value = "/checkRefuse")
		public Map<String, Object> checkRefuse(HttpServletRequest request, Model model, @ModelAttribute ConfirmMileOrderCommand cmd,HttpServletResponse response) {
			Map<String, Object> map=new HashMap<String, Object>();
			try {
				UserInfo userInfo = getUserInfo(request.getSession());
				cmd.setConfirmPerson(userInfo.getDisplayName());
				//如果是拒绝单个
				if(!com.alibaba.dubbo.common.utils.StringUtils.isBlank(cmd.getId())){
					//将选中的id放入数组中(service中只会处理List)
					List<String> orderIdList = new  ArrayList<String>();
					orderIdList.add(cmd.getId());
					cmd.setIds(orderIdList);
				}else{
					cmd.setIds((List<String>)request.getSession().getAttribute("checkIds"));
					request.getSession().removeAttribute("checkIds");
				}
				//提交拒绝审核
				mileOrderService.batchConfirm(cmd, false);
				//添加操作日志
				map.put("statusCode", 200);
				map.put("callbackType","closeCurrent");
				map.put("message", "操作成功");
			} catch (Exception e) {
				//添加操作日志
				map.put("statusCode", 300);
				map.put("callbackType","closeCurrent");
				map.put("message", "操作失败");
				e.printStackTrace();
			}
			return  map;
		}
	
		 /**
			 * 订单确认通过
			 * @author zqq
			 * @since hgfx-admin-web
			 * @date 2016年6月28日 10:21:42
			 * @param request
			 * @param model
			 * @param mileOrderSQO
			 * @param response
			 * @return
			 */
			@ResponseBody
			@RequestMapping(value = "/checkPass")
			public Map<String, Object> checkPass(HttpServletRequest request, Model model, @ModelAttribute ConfirmMileOrderCommand cmd,HttpServletResponse response) {
				Map<String, Object> map=new HashMap<String, Object>();
				try {
					UserInfo userInfo = getUserInfo(request.getSession());
					cmd.setConfirmPerson(userInfo.getDisplayName());
					//如果是拒绝单个
					if(StringUtils.isNotBlank(cmd.getId())){
						//将选中的id放入数组中(service中只会处理List)
						List<String> orderIdList = new  ArrayList<String>();
						orderIdList.add(cmd.getId());
						cmd.setIds(orderIdList);
					}
					//提交拒绝审核
					mileOrderService.batchConfirm(cmd, true);
					//添加操作日志
					map.put("statusCode", 200);
					map.put("callbackType",null);
					map.put("message", "操作成功");
				} catch (Exception e) {
					//添加操作日志
					map.put("statusCode", 300);
					map.put("callbackType",null);
					map.put("message", "操作失败");
					e.printStackTrace();
				}
				return  map;
			}

	 /**
	  * excel导出
	  * @author zqq
	  * @since hgfx-admin-web
	  * @date 2016-6-3 上午9:50:48 
	  * @param wb
	  * @param response
	  * @throws IOException
	  */
	 public void outputExcel(HSSFWorkbook wb, HttpServletResponse response,String path,String fileName) throws IOException {
			InputStream inStream = null;
			try {
				// 创建指定位置目录
			    File f = new File(path);
			    if(!f.exists()){
			    	f.mkdir();
			    }
			    //将文件写在本地
				FileOutputStream fout = new FileOutputStream(fileName);

				wb.write(fout);
				fout.close();
				// 将文件输入并下载 读到流中
				inStream = new FileInputStream(fileName); // 文件的存放路径
				// 设置输出的格式
				String name = "订单列表"+ DateUtil.formatDate1(new Date()) ;
				response.reset();
				String isoName = parseGBK(name);
				response.setContentType("application/x-msdownload MSEXCEL");
				response.setHeader("Content-Disposition", "attachment;filename=\""
						+ isoName+ ".xls" + "\"");
				// 循环取出流中的数据
				byte[] b = new byte[100];
				int len;
				while ((len = inStream.read(b)) > 0) {
					response.getOutputStream().write(b, 0, len);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	// 将GBK字符转化为ISO码
		public static String parseGBK(String sIn) {
			if (sIn == null || sIn.equals(""))
				return sIn;
			try {
				return new String(sIn.getBytes("GBK"), "ISO-8859-1");
			} catch (UnsupportedEncodingException usex) {
				return sIn;
			}
		}
}

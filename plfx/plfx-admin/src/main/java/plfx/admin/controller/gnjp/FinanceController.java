package plfx.admin.controller.gnjp;

import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.JsonUtil;
import hg.common.util.SysProperties;
import hg.common.util.file.FdfsFileInfo;
import hg.common.util.file.FdfsFileUtil;
import hg.log.util.HgLogger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import plfx.admin.controller.BaseController;
import plfx.jp.pojo.dto.flight.CityAirCodeDTO;
import plfx.jp.pojo.dto.supplier.SupplierDTO;
import plfx.jp.qo.admin.supplier.SupplierQO;
import plfx.jp.spi.common.JpEnumConstants;
import plfx.jp.spi.inter.CityAirCodeService;
import plfx.jp.spi.inter.JPPlatformOrderService;
import plfx.jp.spi.inter.supplier.SupplierService;
import plfx.yeexing.pojo.command.order.JPOrderCommand;
import plfx.yeexing.pojo.dto.order.JPOrderDTO;
import plfx.yeexing.pojo.dto.order.JPOrderStatusConstant;
import plfx.yeexing.qo.admin.PlatformOrderQO;
/**
 * 
 * @类功能说明：财务管理
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年7月1日下午5:29:56
 * @版本：V1.0
 *
 */
@Controller
@RequestMapping(value="/jp/finance")
public class FinanceController extends BaseController {

	@Autowired
	private JPPlatformOrderService jpPlatformOrderService;
	@Autowired
	private CityAirCodeService cityAirCodeService;
	@Autowired
	private SupplierService supplierService;
	
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
		
		if (null !=platformOrderQO && StringUtils.isBlank(platformOrderQO.getType())) {
			platformOrderQO.setType(JPOrderStatusConstant.COMMON_TYPE);
		}
		
		if (null != platformOrderQO && platformOrderQO.getTempStatus() != null) {
			platformOrderQO.setStatus(platformOrderQO.getTempStatus());
		}
		
		platformOrderQO.setCreateDateAsc(false);//按创建时间倒序排序
		platformOrderQO.setFilterCancel(true); //过滤掉cancel状态的订单
		platformOrderQO.setJustDisPaySucc(true); //过滤掉cancel状态的订单
		platformOrderQO.setRefundType("A");//过滤记录订单
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
		model.addAttribute("statusList", JPOrderStatusConstant.PLFX_JPORDER_STATUS_LIST);
		model.addAttribute("orderPayStatusList", JPOrderStatusConstant.PLFX_JPORDER_PAY_STATUS_LIST);
		model.addAttribute("cities", cities);
		model.addAttribute("STATUS_MAP", JPOrderStatusConstant.JPORDER_STATUS_MAP);
		model.addAttribute("platformOrderQO", platformOrderQO);
		model.addAttribute("TYPE_MAP", JPOrderStatusConstant.JPORDER_TYPE_MAP);
		model.addAttribute("EXCEPTION_TYPE", JPOrderStatusConstant.EXCEPTION_TYPE);
		HgLogger.getInstance().info("yaosanfeng", "机票分销平台-机票管理-财务管理-查询财务报表列表成功");
		return "/airticket/financial_statements/statements_list.html";
	}
	
	/**
	 * 
	 * @方法功能说明：导出订单excle表
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月2日上午9:34:40
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param platformOrderQO
	 * @参数：@param pageNo
	 * @参数：@param pageSize
	 * @参数：@param beginTimeStr
	 * @参数：@param endTimeStr
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/export.csv")
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

		if(StringUtils.isNotBlank(platformOrderQO.getLoginName())){
			platformOrderQO.setLoginName(platformOrderQO.getLoginName().trim());
		}
		
		if (null !=platformOrderQO && StringUtils.isBlank(platformOrderQO.getType())) {
			platformOrderQO.setType(JPOrderStatusConstant.COMMON_TYPE);
		}
		
		platformOrderQO.setCreateDateAsc(false);//按创建时间倒序排序
		platformOrderQO.setFilterCancel(true); //过滤掉cancel状态的订单
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
//			String title = "序号,订单类别,平台订单号,经销商订单号,供应商订单号,航空公司,出发地,目的地,支付金额,返点/佣金,${0}订单状态,下单时间";
			String title = "序号,订单类别,平台订单号,经销商订单号,供应商订单号,航空公司,出发地,目的地,订单总金额,支付供应商金额,返点/佣金,支付宝手续费,${0}订单状态,支付状态,下单时间";
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
				outs.append("\"\t"+jpOrderDTO.getYeeXingOrderId()+"\",");
				outs.append("\"\t"+jpOrderDTO.getAirCompName()+"\",");
				outs.append(jpOrderDTO.getStartCityName()+",");
				outs.append(jpOrderDTO.getEndCityName()+",");
				outs.append(jpOrderDTO.getUserPayAmount()+",");
				outs.append(jpOrderDTO.getTotalPrice()+",");
				outs.append(jpOrderDTO.getDisc()+"/"+jpOrderDTO.getCommAmount()+",");
				if(jpOrderDTO.getProcedures() != null){
					outs.append(jpOrderDTO.getProcedures()+",");
				}else{
					outs.append("0.00,");
				}
				
				if (null != platformOrderQO && JPOrderStatusConstant.EXCEPTION_TYPE.equals(platformOrderQO.getType())) {
					double adjust  = jpOrderDTO.getAdjust() == null ? 0 : jpOrderDTO.getAdjust();
					outs.append(adjust+",");
				}
				String orderStatus =JPOrderStatusConstant.PLFX_JPORDER_STATUS_MAP.get(jpOrderDTO.getOrderStatus().getStatus()+"");
				String orderPayStatus =JPOrderStatusConstant.PLFX_JPORDER_PAY_STATUS_MAP.get(jpOrderDTO.getOrderStatus().getPayStatus()+"");
				outs.append(orderStatus+",");
				outs.append(orderPayStatus+",");
				outs.append(jpOrderDTO.getCreateTime()+",");
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
			
			HgLogger.getInstance().info("yuqz", "机票分销平台-机票管理-财务管理-导出-下载财务报表成功");
		} catch (Exception e) {
			HgLogger.getInstance().error("yuqz", "机票分销平台-机票管理-财务管理-导出-下载财务报表失败"+HgLogger.getStackTrace(e));
		}
		return "";
	}
	
	/**
	 * 
	 * @方法功能说明：异常订单列表
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月2日上午10:33:21
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param platformOrderQO
	 * @参数：@param pageNo
	 * @参数：@param pageSize
	 * @参数：@param beginTimeStr
	 * @参数：@param endTimeStr
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/orderList")
	public String orderList(HttpServletRequest request, Model model,
			@ModelAttribute PlatformOrderQO platformOrderQO,
			@RequestParam(value="pageNum",required=false) Integer pageNo, 
			@RequestParam(value="numPerPage",required=false) Integer pageSize,
			@RequestParam(value="beginTimeStr",required=false) String beginTimeStr,
			@RequestParam(value="endTimeStr",required=false) String endTimeStr){
		
		HgLogger.getInstance().info("yaosanfeng","机票分销平台-机票管理-财务管理- 异常订单订单列表条件查询开始");
		//查询异常订单条件
		platformOrderQO.setType(JpEnumConstants.OrderErrorType.TYPE_ERROR);
		//航空城市代码
		List<CityAirCodeDTO> cities=this.cityAirCodeService.queryCityAirCodeList();
		//供应商列表
		List<SupplierDTO> supplierList=this.supplierService.getSupplierList(new SupplierQO());
		
		if (null != beginTimeStr && !"".equals(beginTimeStr)) {
			platformOrderQO.setCreateDateFrom(DateUtil.dateStr2BeginDate(beginTimeStr));
		}
		if (null != endTimeStr && !"".equals(endTimeStr)) {
			platformOrderQO.setCreateDateTo(DateUtil.dateStr2EndDate(endTimeStr));
		}
		
		if (null != platformOrderQO && platformOrderQO.getTempStatus() != null) {
			platformOrderQO.setStatus(platformOrderQO.getTempStatus());
		}
		platformOrderQO.setCreateDateAsc(false);//按创建时间倒序排序
		
		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNo==null?1:pageNo);
		pagination.setPageSize(pageSize==null?20:pageSize);
		//添加页面查询条件
		pagination.setCondition(platformOrderQO);
		
		//根据查询条件查询订单列表
		pagination=this.jpPlatformOrderService.queryFJPOrderList(pagination);
		
		model.addAttribute("statusList", JPOrderStatusConstant.PLFX_JPORDER_STATUS_LIST);
		model.addAttribute("orderPayStatusList", JPOrderStatusConstant.PLFX_JPORDER_PAY_STATUS_LIST);
		model.addAttribute("cities", cities);
		model.addAttribute("supplierList", supplierList);
		
		model.addAttribute("pagination", pagination);
		model.addAttribute("platformOrderQO", platformOrderQO);
		
		model.addAttribute("beginTimeStr", beginTimeStr);
		model.addAttribute("endTimeStr", endTimeStr);
		//订单状态
		model.addAttribute("statusList", JPOrderStatusConstant.PLFX_JPORDER_STATUS_LIST);
		//支付状态
		model.addAttribute("orderPayStatusList", JPOrderStatusConstant.PLFX_JPORDER_PAY_STATUS_LIST);
        //证件类型
		model.addAttribute("DOCUMENT_TYPE_MAP", JPOrderStatusConstant.DOCUMENT_TYPE_MAP);
		//乘客类型
		model.addAttribute("PASSENGER_TYPE_MAP", JPOrderStatusConstant.PASSENGER_TYPE_MAP);
		HgLogger.getInstance().info("yaosanfeng","机票分销平台-机票管理-财务管理- 异常订单列表条件查询结束");
		return "/airticket/financial/gn_jp_exception_orderList.html";
	}
	
	/**
	 * 
	 * @方法功能说明：转向新增异常订单页面
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月2日下午1:47:41
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param qo
	 * @参数：@param pageNum
	 * @参数：@param numPerPage
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/toAddErrorOrder")
	public String toAddErrorOrder(HttpServletRequest request, Model model,
			@ModelAttribute PlatformOrderQO qo,
			@RequestParam(value="pageNum",required=false) Integer pageNum, 
			@RequestParam(value = "numPerPage", required = false) Integer numPerPage){
		//添加分页参数
		Pagination pagination = new Pagination();
		pageNum = pageNum == null ? new Integer(1) : pageNum;
		numPerPage = numPerPage == null ? new Integer(10) : numPerPage;
		pagination.setPageNo(pageNum);
		pagination.setPageSize(numPerPage);
		qo.setRefundType("A");//排除记录订单
		qo.setCreateDateAsc(false);//按创建时间倒序排序
		// 添加查询条件
		pagination.setCondition(qo);
		
		List<SupplierDTO> supplierList=this.supplierService.getSupplierList(new SupplierQO());
		pagination = jpPlatformOrderService.queryPagination(pagination);
		model.addAttribute("pagination", pagination);
		/*
		//供应商列表
		
		jpOrderQO.setType(JpEnumConstants.OrderErrorType.TYPE_FORMAL);
		List<JPOrderDTO> orderList=this.jpPlatformOrderService.queryErrorJPOrderList(jpOrderQO);
		
		model.addAttribute("orderList", orderList);
		*/
		model.addAttribute("jpOrderQO", qo);
		model.addAttribute("supplierList", supplierList);
		return "/airticket/financial/addErrorOrder.html";
	}
		
	@RequestMapping(value = "/toAdjustErrorOrder")
	public String toAdjustErrorOrder(HttpServletRequest request, Model model){
		String id=request.getParameter("id");
		String orderNo=request.getParameter("orderNo");
		model.addAttribute("id", id);
		model.addAttribute("orderNo", orderNo);
		
		return "/airticket/financial/adjustErrorOrder.html";
	}
	
	
	/**
	 * 
	 * @方法功能说明：上传异常订单凭证
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月2日下午1:47:48
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/upload")
	public String upload( HttpServletRequest request, HttpServletResponse response, Model model) {
		
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multi.getFile("voucherPictureFile");
		Map<String, String> result_map = new HashMap<String, String>();
		
		HgLogger.getInstance().info("yuqz", "机票分销平台-机票管理-财务管理-异常订单上传凭证开始");
		
		if(multipartFile != null){
			String imageName=multipartFile.getOriginalFilename();
			
			String imageType=imageName.substring(imageName.lastIndexOf(".")+1);
				FdfsFileInfo fileInfo=null;
				try {
					fileInfo = FdfsFileUtil.upload((FileInputStream)multipartFile.getInputStream(),imageType, null);
				} catch (IOException e) {
					HgLogger.getInstance().error("yuqz", "机票分销平台-机票管理-财务管理-异常订单上传凭证异常"+HgLogger.getStackTrace(e));
				}
				
				//fastdfs图片服务器地址:192.168.2.214
				String imageUrl="";
				if(fileInfo != null){
					imageUrl = SysProperties.getInstance().get("fileUploadPath")+fileInfo.getUri();
				}
				
				result_map.put("status", "success");
				result_map.put("imageUrl", imageUrl);
				result_map.put("imageName", imageName);
				
				HgLogger.getInstance().debug("yuqz", "机票分销平台-机票管理-财务管理-异常订单凭证图片上传成功");
		}else{
			result_map.put("status", "error");
			result_map.put("message", "上传异常");
			HgLogger.getInstance().error("yuqz", "机票分销平台-机票管理-财务管理-异常订单凭证图片上传失败");
		}
			
		HgLogger.getInstance().info("yuqz", "机票分销平台-机票管理-财务管理-异常订单上传凭证结束");
		return JsonUtil.parseObject(result_map, false);
	}
	
	
	/**
	 * 
	 * @方法功能说明：新增异常订单
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月2日下午1:47:57
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param id
	 * @参数：@param orderNo
	 * @参数：@param adjustAmount
	 * @参数：@param voucher
	 * @参数：@param adjustReason
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/addErrorOrder")
	@ResponseBody
	public String addErrorOrder(HttpServletRequest request, Model model,
			@RequestParam(value="id",required=true) String id,
			@RequestParam(value="orderNo",required=true) String orderNo,
			@RequestParam(value="adjustAmount",required=true) String adjustAmount,
			@RequestParam(value="voucherPicture",required=true) String voucher,
			@RequestParam(value="adjustReason",required=true) String adjustReason
			){
		
		try {
			
			if(StringUtils.isNotEmpty(id)){
				
				JPOrderCommand jpOrderCommand=new JPOrderCommand();
				jpOrderCommand.setId(id);
				jpOrderCommand.setOrderNo(orderNo);				
				
				double amount=0.0;
				if(StringUtils.isNotEmpty(adjustAmount)){
					amount=Double.parseDouble(adjustAmount);
				}
				jpOrderCommand.setAdjustAmount(amount);
				jpOrderCommand.setVoucherPicture(voucher);
				jpOrderCommand.setAdjustReason(adjustReason);
				
				this.jpPlatformOrderService.saveErrorJPOrder(jpOrderCommand);
			}
		} catch (Exception e) {
			HgLogger.getInstance().error("tuhualiang", "机票分销平台-机票管理-财务管理-异常订单新增失败"+HgLogger.getStackTrace(e));
			return DwzJsonResultUtil.createJsonString("300", "新增异常订单失败", "closeCurrent", "errorOrderList");
		}
		
		return DwzJsonResultUtil.createJsonString("200", "新增异常订单成功", "closeCurrent", "errorOrderList");
	}
	
}

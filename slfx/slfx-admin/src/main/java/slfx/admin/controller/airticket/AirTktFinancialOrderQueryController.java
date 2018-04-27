package slfx.admin.controller.airticket;

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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import slfx.admin.controller.BaseController;
import slfx.jp.command.admin.JPOrderCommand;
import slfx.jp.pojo.dto.flight.CityAirCodeDTO;
import slfx.jp.pojo.dto.order.JPOrderStatusConstant;
import slfx.jp.pojo.dto.supplier.SupplierDTO;
import slfx.jp.qo.admin.PlatformOrderQO;
import slfx.jp.qo.admin.supplier.SupplierQO;
import slfx.jp.spi.common.JpEnumConstants;
import slfx.jp.spi.inter.CityAirCodeService;
import slfx.jp.spi.inter.JPPlatformOrderService;
import slfx.jp.spi.inter.supplier.SupplierService;

import com.alibaba.dubbo.common.utils.StringUtils;
/**
 * 
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：财务管理 查看订单明细 异常订单
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：renfeng
 * @创建时间：2014年8月4日下午4:52:32
 * @版本：V1.0
 *
 */
@Controller
@RequestMapping(value="/airtkt/financial")
public class AirTktFinancialOrderQueryController extends BaseController {
	
	@Autowired
	private JPPlatformOrderService jpPlatformOrderService;

	@Autowired
	private SupplierService supplierService;
	@Autowired
	private CityAirCodeService cityAirCodeService;
	
	
	/**
	 * 
	 * @方法功能说明：
	 * @修改者名字：renfeng
	 * @修改时间：2014年8月5日下午2:45:48
	 * @修改内容：财务管理:订单列表(异常订单列表)
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param platformOrderQO
	 * @参数：@param pageNo
	 * @参数：@param pageSize
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/orderlist")
	public String queryAirTktOrderList(HttpServletRequest request, Model model,
			@ModelAttribute PlatformOrderQO platformOrderQO,
			@RequestParam(value="pageNum",required=false) Integer pageNo, 
			@RequestParam(value="numPerPage",required=false) Integer pageSize,
			@RequestParam(value="beginTimeStr",required=false) String beginTimeStr,
			@RequestParam(value="endTimeStr",required=false) String endTimeStr){
		
		HgLogger.getInstance().debug("tuhualiang","机票分销平台-机票管理-财务管理- 异常订单订单列表条件查询开始");
		
		//判断查询的是否是异常订单
		String page="/airticket/financial/orderList.html";
		String ordertype=request.getParameter("type");
		if(ordertype!=null&&ordertype.equals(JpEnumConstants.OrderErrorType.TYPE_ERROR)){
			platformOrderQO.setType(JpEnumConstants.OrderErrorType.TYPE_ERROR);
			page="/airticket/financial/errorOrderList.html";
		}else{
			platformOrderQO.setType(JpEnumConstants.OrderErrorType.TYPE_FORMAL);
		}
		
		
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
		
		model.addAttribute("statusList", JPOrderStatusConstant.SLFX_JPORDER_STATUS_LIST);
		model.addAttribute("orderPayStatusList", JPOrderStatusConstant.SLFX_JPORDER_PAY_STATUS_LIST);
		model.addAttribute("cities", cities);
		model.addAttribute("supplierList", supplierList);
		
		model.addAttribute("pagination", pagination);
		model.addAttribute("platformOrderQO", platformOrderQO);
		
		model.addAttribute("beginTimeStr", beginTimeStr);
		model.addAttribute("endTimeStr", endTimeStr);
		HgLogger.getInstance().debug("tuhualiang","机票分销平台-机票管理-财务管理- 异常订单列表条件查询结束");
		return page;
	}
	
	/**
	 * @方法功能说明：转向新增异常订单页面
	 * @修改者名字：renfeng
	 * @修改时间：2014年8月12日上午10:43:19
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/toAddErrorOrder")
	public String toAddErrorOrder(HttpServletRequest request, Model model,
			@ModelAttribute PlatformOrderQO qo,
			@RequestParam(value="pageNum",required=false) Integer pageNum, 
			@RequestParam(value = "numPerPage", required = false) Integer numPerPage){
		// 添加分页参数
		Pagination pagination = new Pagination();
		pageNum = pageNum == null ? new Integer(1) : pageNum;
		numPerPage = numPerPage == null ? new Integer(10) : numPerPage;
		pagination.setPageNo(pageNum);
		pagination.setPageSize(numPerPage);
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
	 * @修改者名字：renfeng
	 * @修改时间：2014年8月12日下午5:27:23
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
		
		HgLogger.getInstance().info("tuhualiang", "机票分销平台-机票管理-财务管理-异常订单上传凭证开始");
		
		if(multipartFile != null){
			String imageName=multipartFile.getOriginalFilename();
			
			String imageType=imageName.substring(imageName.lastIndexOf(".")+1);
				FdfsFileInfo fileInfo=null;
				try {
					fileInfo = FdfsFileUtil.upload((FileInputStream)multipartFile.getInputStream(),imageType, null);
				} catch (IOException e) {
					HgLogger.getInstance().error("tuhualiang", "机票分销平台-机票管理-财务管理-异常订单上传凭证异常"+HgLogger.getStackTrace(e));
				}
				
				//fastdfs图片服务器地址:192.168.2.214
				String imageUrl="";
				if(fileInfo != null){
					imageUrl = SysProperties.getInstance().get("fileUploadPath")+fileInfo.getUri();
				}
				
				result_map.put("status", "success");
				result_map.put("imageUrl", imageUrl);
				result_map.put("imageName", imageName);
				
				HgLogger.getInstance().debug("tuhualiang", "机票分销平台-机票管理-财务管理-异常订单凭证图片上传成功");
		}else{
			result_map.put("status", "error");
			result_map.put("message", "上传异常");
			HgLogger.getInstance().error("tuhualiang", "机票分销平台-机票管理-财务管理-异常订单凭证图片上传失败");
		}
			
		HgLogger.getInstance().info("tuhualiang", "机票分销平台-机票管理-财务管理-异常订单上传凭证结束");
		return JsonUtil.parseObject(result_map, false);
	}
	
	
	/**
	 * @方法功能说明：新增异常订单
	 * @修改者名字：renfeng
	 * @修改时间：2014年8月12日下午5:19:37
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param id
	 * @参数：@param cancelRemark
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
			@RequestParam(value="adjustReason",required=true) String adjustReason){
		
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

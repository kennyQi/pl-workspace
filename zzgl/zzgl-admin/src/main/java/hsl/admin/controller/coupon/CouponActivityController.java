package hsl.admin.controller.coupon;
import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.log.util.HgLogger;
import hsl.admin.common.CouponActivityParam;
import hsl.admin.controller.BaseController;
import hsl.pojo.command.coupon.BatchIssueCouponCommand;
import hsl.pojo.command.coupon.CancelCouponActivityCommand;
import hsl.pojo.command.coupon.CheckCouponActivityCommand;
import hsl.pojo.command.coupon.ModifyCouponActivityCommand;
import hsl.pojo.command.coupon.ModifyCouponActivitySendInfoCommand;
import hsl.pojo.dto.coupon.CouponActivityDTO;
import hsl.pojo.dto.coupon.CouponActivityEventDTO;
import hsl.pojo.exception.CouponActivityException;
import hsl.pojo.qo.coupon.HslCouponActivityEventQO;
import hsl.pojo.qo.coupon.HslCouponActivityQO;
import hsl.pojo.util.LogFormat;
import hsl.spi.inter.Coupon.CouponActivityService;
import hsl.spi.inter.user.UserService;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSON;
@Controller
@RequestMapping("/couponActivity")
public class CouponActivityController extends BaseController{
	@Autowired
	private CouponActivityService couponActivityService;
	@Autowired
	private UserService userService;
	/**
	 * 卡券活动管理页面
	 * @return
	 */
	@RequestMapping(value="/couponManage")
	public String couponManage(HslCouponActivityQO hslCouponActivityQO,
			@RequestParam(value="pageNum",required=false) Integer pageNo, 
			@RequestParam(value="numPerPage",required=false) Integer pageSize,
			Model model){
		
		//添加分页查询条件
		Pagination pagination=new Pagination();
		pagination.setCondition(hslCouponActivityQO);
		pageNo=(pageNo==null?1:pageNo);
		pageSize=(pageSize==null?20:pageSize);
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination.setCondition(hslCouponActivityQO);
		pagination = couponActivityService.queryPagination(pagination);
		model.addAttribute("pagination", pagination);
		model.addAttribute("qo", hslCouponActivityQO);
		model.addAttribute("couponTypeMap", CouponActivityParam.getCouponTypeMap());
		model.addAttribute("currentValueMap", CouponActivityParam.getCurrentValueMap());
		model.addAttribute("issueWayMap", CouponActivityParam.getIssueWayMap());
		model.addAttribute("conditionMap", CouponActivityParam.getConditionMap());
		return "coupon/coupon_manage.html";
	}
	
	/**
	 * 审核页面
	 * @return
	 */
	@RequestMapping(value="/examPage")
	public String examPage(@RequestParam(value="id",required=true) String id ,Model model){
		model.addAttribute("id", id);
		return "coupon/check.html";
	}
	
	/**
	 * 审核
	 * @return
	 */
	@RequestMapping(value="/examPermit")
	@ResponseBody
	public String examActivity(@RequestParam(value="id",required=true) String id ,
			@RequestParam(value="approve",required=true) int approve 
			){
		CheckCouponActivityCommand cmd = new CheckCouponActivityCommand();
		cmd.setCouponActivityId(id);
		if(approve==0){
			cmd.setApproved(true);
		}else{
			cmd.setApproved(false);
		}
		CouponActivityDTO couponActivityDTO;
		try {
			couponActivityDTO = couponActivityService.checkCouponActivity(cmd);
			HgLogger.getInstance().info("chenxy", "审核的卡券活动为："+JSON.toJSONString(couponActivityDTO));
		} catch (CouponActivityException e) {
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "审核失败:"+e.getMessage(),DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "couponManage");
		}
		if(approve==0){
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "审核通过", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "couponManage");
		}else{
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "审核未通过", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "couponManage");
		}
	}
	
	@RequestMapping(value="/cancelActivity",method = RequestMethod.GET)
	public String cancelActivity(@RequestParam(value="id",required=true) String id,Model model){
		model.addAttribute("id",id );
		return "coupon/activity_cancel.html";
	}
	
	/**
	 * 取消卡券活动
	 * @param cancelCouponActivityCommand
	 * @return
	 */
	@RequestMapping(value="/cancelActivity",method = RequestMethod.POST)
	@ResponseBody
	public String cancelActivity(CancelCouponActivityCommand cancelCouponActivityCommand){
		try {
			couponActivityService.cancelCouponActivity(cancelCouponActivityCommand);
		} catch (CouponActivityException e) {
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "取消失败","", "couponManage");
		}
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "取消成功",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "couponManage");
	}
	
	/**
	 * 显示活动取消原因
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/showReason")
	public String showReason(@RequestParam(value="id",required=true) String id ,Model model){
		HslCouponActivityEventQO qo = new HslCouponActivityEventQO();
		qo.setEventType(10);
		qo.setCouponActivityId(id);
		CouponActivityEventDTO couponActivityEventDTO = couponActivityService.getCancelRemark(qo);
		model.addAttribute("couponActivityEventDTO", couponActivityEventDTO);
		return "coupon/reason.html";
	}
	
	/**
	 * 添加卡券活动页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/addCoupon",method=RequestMethod.GET)
	public String addCoupon(@RequestParam(value="id",required=false) String id,Model model){
		CouponActivityDTO couponActivityDTO = null;
		if(!StringUtils.isBlank(id)){
			HslCouponActivityQO qo = new HslCouponActivityQO();
			qo.setId(id);
			couponActivityDTO = couponActivityService.queryUnique(qo);
		}
		if(couponActivityDTO!=null){
			model.addAttribute("status", 1);
			if(couponActivityDTO.getIssueConditionInfo()!=null&&couponActivityDTO.getIssueConditionInfo().getIssueNumber()>0){
				if(couponActivityDTO.getIssueConditionInfo().getIssueNumber()==Long.MAX_VALUE){
					model.addAttribute("nolimit", 1);
				}
			}
		}
		model.addAttribute("couponActivityDTO",couponActivityDTO );
		return "coupon/add_coupon.html";
	}
	
	/**
	 * 验证id是否存在
	 * @return
	 */
	@RequestMapping("/examCoupon")
	@ResponseBody
	public String examCoupon(@RequestParam(value="id",required=true) String id){
		CouponActivityDTO couponActivityDTO = null;
		if(!StringUtils.isBlank(id)){
			HslCouponActivityQO qo = new HslCouponActivityQO();
			qo.setId(id);
			couponActivityDTO = couponActivityService.queryUnique(qo);
		}
		if(couponActivityDTO==null){
			return "{\"result\":true}";
		}
		return "{\"result\":false}";
	}
	

	/**
	 * 处理添加卡券活动
	 * @param createCouponActivityCommand
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/addCoupon",method=RequestMethod.POST)
	@ResponseBody
	public String addCoupon(ModifyCouponActivityCommand modifyCouponActivityCommand,
			HttpServletRequest request,
			Model model){
		//默认的binder不能保存具体的不是datetime，需要单独做处理
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String issueBeginDate = request.getParameter("issueBeginDate");
		String issueEndDate = request.getParameter("issueEndDate");
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		try{
			if(!StringUtils.isBlank(issueBeginDate)){
				modifyCouponActivityCommand.setIssueBeginDate(dateFormat.parse(issueBeginDate));
			}
			if(!StringUtils.isBlank(issueEndDate)){
				modifyCouponActivityCommand.setIssueEndDate(dateFormat.parse(issueEndDate));
			}
			if(!StringUtils.isBlank(beginDate)){
				modifyCouponActivityCommand.setBeginDate(dateFormat.parse(beginDate));
			}
			if(!StringUtils.isBlank(endDate)){
				modifyCouponActivityCommand.setEndDate(dateFormat.parse(endDate));
			}
		}catch(ParseException e ){
			HgLogger.getInstance().error("zhuxy", "添加卡券活动————》日期转换失败");
		}
		
		
		//若是不限，则取最大值
		String nolimit = request.getParameter("nolimit");
		if(!StringUtils.isBlank(nolimit)&&"1".equals(nolimit)){
			modifyCouponActivityCommand.setIssueNumber(Long.MAX_VALUE);
		}
		
		//区分是添加还是修改
		String id = modifyCouponActivityCommand.getId();
		CouponActivityDTO couponActivityDTO = null ;
		if(!StringUtils.isBlank(id)){
			HslCouponActivityQO qo = new HslCouponActivityQO();
			qo.setId(id);
			couponActivityDTO = couponActivityService.queryUnique(qo);
		}
		if(couponActivityDTO==null){
			try {
				couponActivityService.saveCouponActivity(modifyCouponActivityCommand);
			}catch(Exception e){
				HgLogger.getInstance().error("zhaows","添加卡券活动失败-->addCoupon" + HgLogger.getStackTrace(e));
				e.printStackTrace();
				return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "添加失败",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "couponManage");
			}
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "添加成功","openNew", "couponManage");
		}else{
			try {
				couponActivityService.updateCouponActivity(modifyCouponActivityCommand);
			} catch (CouponActivityException e) {
				HgLogger.getInstance().error("zhaows","修改失败活动失败-->addCoupon" + HgLogger.getStackTrace(e));
				e.printStackTrace();
				return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "修改失败："+e.getMessage(),DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "couponManage");
			}
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "修改成功","openNew", "couponManage");
		}
	}
	
	/**
	 * 卡券活动详情页
	 */
	@RequestMapping("/showCoupon")
	public String couponDetail(@RequestParam(value="id",required=true) String id,Model model){
		CouponActivityDTO couponActivityDTO = new CouponActivityDTO();
		if(!StringUtils.isBlank(id)){
			HslCouponActivityQO qo = new HslCouponActivityQO();
			qo.setId(id);
			couponActivityDTO = couponActivityService.queryUnique(qo);
		}
		if(couponActivityDTO!=null){
			model.addAttribute("status", 1);
			if(couponActivityDTO.getIssueConditionInfo()!=null&&couponActivityDTO.getIssueConditionInfo().getIssueNumber()>0){
				if(couponActivityDTO.getIssueConditionInfo().getIssueNumber()==Long.MAX_VALUE){
					model.addAttribute("nolimit", 1);
				}
			}
		}
		model.addAttribute("couponActivityDTO",couponActivityDTO );
		model.addAttribute("couponTypeMap", CouponActivityParam.getCouponTypeMap());
		model.addAttribute("currentValueMap", CouponActivityParam.getCurrentValueMap());
		model.addAttribute("issueWayMap", CouponActivityParam.getIssueWayMap());
		model.addAttribute("conditionMap", CouponActivityParam.getConditionMap());
		return "coupon/show_coupon.html";
	}
	
	/**
	 * 转赠页面
	 * @return
	 */
	@RequestMapping(value="/conversedPage")
	public String conversed(@RequestParam(value="id",required=true)String id ,
			@RequestParam(value="sendAppendCouponIds",required=true)String sendAppendCouponIds,
			               @RequestParam(value="userCreateTime",required=true)String userCreateTime,Model model){
		model.addAttribute("id", id);
		model.addAttribute("sendAppendCouponIds", sendAppendCouponIds);
		model.addAttribute("userCreateTime", userCreateTime);
		return "coupon/conversed.html";
	}
	
	/**
	 * 转赠
	 * @return
	 */
	@RequestMapping(value="/conversed",method=RequestMethod.POST)
	@ResponseBody
	public String conversed(ModifyCouponActivitySendInfoCommand modifyCouponActivitySendInfoCommand,
			HttpServletRequest request,
			Model model){
		
		//默认的binder不能保存具体的不是datetime，需要单独做处理
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String userCreateTime = request.getParameter("userCreateTime");
		try{
			if(StringUtils.isNotBlank(userCreateTime)){
				modifyCouponActivitySendInfoCommand.setUserCreateTime(dateFormat.parse(userCreateTime));
			}
		}catch(ParseException e ){
			HgLogger.getInstance().error("renfeng", "卡券转赠设置————》日期转换失败");
		}
		
		
		try {
			couponActivityService.modifyCouponSendInfo(modifyCouponActivitySendInfoCommand);
		}catch(CouponActivityException e){
			HgLogger.getInstance().error("renfeng", "奖励的卡券活动不存在");
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, e.getMessage(),DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "couponManage");
		}catch(Exception e){
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "卡券转赠设置失败",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "couponManage");
		}
		return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "卡券转赠设置成功",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "couponManage");
		
	}
	
	
	/**
	 * 发放页面
	 * @return
	 */
	@RequestMapping(value="/sendWelfarePage")
	public String sendWelfare(@RequestParam(value="id",required=true) String id ,Model model){
		model.addAttribute("id", id);
		return "coupon/sendWelfare.html";
	}
	/**
	 * 导入用户列表
	 * @throws IOException 
	 * 
	 */
	@ResponseBody
	@RequestMapping("/sendWelfare/upload")
	public String uploadUsers(@RequestParam(value = "file", required = false) MultipartFile file, 
			HttpServletRequest request) throws Exception{
		Map<String,Object> model = new HashMap<String, Object>(3);
		HgLogger.getInstance().debug("renfeng", newHeader("个人用户手机号码载入开始"));
		//添加校验的正则表达式
		
		Pattern telp = Pattern.compile("^0?(13|15|17|18)[0-9]{9}$");
		int i = 1;
		int j = 0;
		InputStream  is=null;
		//获取文件输入流
		try {
			model.put("detial","");
			
			is = file.getInputStream();
			Workbook wb = Workbook.getWorkbook(is);
			//开始校验excel
			Sheet se = wb.getSheet(0);		
			String tel;
		
			List<String> tels=new ArrayList<String>();
			System.out.println("总记录数:"+se.getRows());
			HgLogger.getInstance().info("renfeng",LogFormat.log("发放卡券：个人用户手机号Excel导入<<总记录数:", se.getRows()-1+""));
			while(i<se.getRows()){
				if(!StringUtils.isBlank(se.getCell(0,i).getContents())){
					//获取手机号
					tel = se.getCell(0,i).getContents();
					Matcher tm =  telp.matcher(tel);
					if(tm.matches()){
											
						if(tels.contains(tel)){
							model.put("detial","手机号"+tel+"与前面的重复");
							HgLogger.getInstance().info("renfeng",LogFormat.log("发放卡券：个人用户手机号Excel导入<<手机号:", tel+"与前面的重复"));
							throw new Exception();
						}else{
							tels.add(tel);
						}
						
						
					}else{
						j=2;
						model.put("detial","手机号:"+tel+"格式错误");
						HgLogger.getInstance().info("renfeng",LogFormat.log("发放卡券：个人用户手机号Excel导入<<手机号:", tel+"格式错误"));
						throw new Exception();
					}
					
					
				}
				i++;
				
			}
			
			is.close();
			HgLogger.getInstance().info("renfeng",LogFormat.log("发放卡券：个人用户手机号Excel导入<<所有手机号：", JSON.toJSONString(tels)));
			model.put("tels", tels);
			model.put("msg", "success");
			
		} catch (Exception e1) {
			e1.printStackTrace();
			model.put("msg", "手机号码载入出错");
			//model.put("detial","手机号码载入出错，请检查excel版本及excel是否是按照模板来编写");
			model.put("row", i+1);
			model.put("col", j+1);
			HgLogger.getInstance().error("renfeng", LogFormat.log("发放卡券：用户手机号导入错误信息", HgLogger.getStackTrace(e1)));
			String json=JSON.toJSONString(model);
			System.out.println(json);
			return json;
		}finally{
			if(is!=null){
				is.close();
			}
			
		}
		HgLogger.getInstance().debug("renfeng", newHeader("发放卡券：个人用户手机号码载入结束"));
		String json=JSON.toJSONString(model);
		System.out.println(json);
		return json;
	}
	
	
	
	/**
	 * 下载excel
	 * @return
	 */
	@RequestMapping("/sendWelfare/download")
	public String download(HttpServletRequest request,HttpServletResponse response){
		HgLogger.getInstance().debug("renfeng", "开始下载用户手机号Excel。。。。。。");
				
		String fileName="userMobilesDemo.xls";
		    response.setContentType("text/html;charset=utf-8");   
	        try {
				request.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}   
	        BufferedInputStream bis = null;   
	        BufferedOutputStream bos = null;  

	        String downLoadPath = request.getSession().getServletContext().getRealPath(   
	                "/")    +"excel"+File.separator+fileName; 
	        		
	        HgLogger.getInstance().debug("renfeng", "下载Excel 文件路径："+downLoadPath);
	        try {   
	            long fileLength = new File(downLoadPath).length();   
	            response.setContentType("application/x-msdownload;");   
	            response.setHeader("Content-disposition", "attachment; filename="  
	                    + new String(fileName.getBytes("utf-8"), "ISO8859-1"));   
	            response.setHeader("Content-Length", String.valueOf(fileLength));   
	            bis = new BufferedInputStream(new FileInputStream(downLoadPath));   
	            bos = new BufferedOutputStream(response.getOutputStream());   
	            byte[] buff = new byte[2048];   
	            int bytesRead;   
	            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {   
	                bos.write(buff, 0, bytesRead);   
	            }   
	        } catch (Exception e) {   
	            e.printStackTrace();   
	        } finally {   
	           
					try {
						if (bis != null)
						    bis.close();
						if (bos != null)   
			                bos.close();   
					} catch (IOException e) {
						e.printStackTrace();
					}   
	            
	        }   
	        
	        HgLogger.getInstance().debug("renfeng", "下载结束。");
	        return null;   
	}
	
	/**
	 * 发送福利
	 * @return
	 */
	@RequestMapping(value="/sendWelfare",method=RequestMethod.POST)
	@ResponseBody
	public String sendWelfare(@RequestParam(value="id",required=true) String id ,
			@RequestParam(value="tels",required=true) String tels ,
			HttpServletRequest request){
		HgLogger.getInstance().info("renfeng",LogFormat.log("开始发放卡券。。。。。。。。",""));
		String msg="发放成功";
		List<String> tList=null;
		if(StringUtils.isNotBlank(tels)){
			String[] tel_arr=tels.split(",");
			tList=Arrays.asList(tel_arr);
		}
		
		BatchIssueCouponCommand issueCouponCmd=new BatchIssueCouponCommand();
		issueCouponCmd.setCouponId(id);
		issueCouponCmd.setMobiles(tList);
		
		try {
			
			String noSend=couponActivityService.batchIssueCoupons(issueCouponCmd);
			if(StringUtils.isNotBlank(noSend)){
				msg="以下手机号的用户没有发放成功！<br>"+noSend.substring(0, noSend.length()-1);
				HgLogger.getInstance().info("renfeng",LogFormat.log("发放卡券：个人用户手机号Excel导入<<",msg));
				return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_300, msg,DwzJsonResultUtil.STATUS_CODE_300, "couponManage");
			}else{
				
				HgLogger.getInstance().info("renfeng",LogFormat.log("卡券发放成功：",tels));
				return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, msg,DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "couponManage");
			}
		}catch(Exception e){
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "发放失败",DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "couponManage");
		}
			
		
		
	}
	
	
}

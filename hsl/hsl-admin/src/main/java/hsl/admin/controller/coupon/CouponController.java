package hsl.admin.controller.coupon;

import hg.common.page.Pagination;
import hg.common.util.BeanMapperUtils;
import hg.common.util.DwzJsonResultUtil;
import hg.log.util.HgLogger;
import hsl.admin.common.CouponActivityParam;
import hsl.admin.controller.BaseController;
import hsl.app.service.local.coupon.CouponTransferRecordLocalService;
import hsl.domain.model.coupon.CouponStatus;
import hsl.domain.model.coupon.CouponTransferRecord;
import hsl.pojo.command.coupon.CancelCouponCommand;
import hsl.pojo.dto.coupon.CouponDTO;
import hsl.pojo.qo.coupon.CouponTransferRecordQO;
import hsl.pojo.qo.coupon.HslCouponQO;
import hsl.spi.inter.Coupon.CouponService;
import java.text.ParseException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping(value="/coupon")
public class CouponController extends BaseController{

	@Autowired
	private  CouponService  couponService;
	@Autowired
	private CouponTransferRecordLocalService transferRecordLocalService;

	@RequestMapping(value="/transfer-records")
	public String couponTransferRecordList(Model model, @RequestParam String couponId) {
		CouponTransferRecordQO qo = new CouponTransferRecordQO();
		qo.setCouponId(couponId);
		List<CouponTransferRecord> records = transferRecordLocalService.queryList(qo);
		model.addAttribute("records", records);
		return "/coupon/coupon-transfer-records.html";
	}

	/**
	 * 卡券记录列表查询
	 * @param request
	 * @param model
	 * @param hslCouponQO
	 * @param pageNo
	 * @param pageSize
	 * @throws ParseException 
	 * */
	@RequestMapping(value="/list")
	public  String  couponList(HttpServletRequest request,Model model,
			@ModelAttribute  HslCouponQO  hslCouponQO,
			@RequestParam(value="pageNum",required=false) Integer pageNo, 
			@RequestParam(value="numPerPage",required=false) Integer pageSize){
		
		//添加分页查询条件
		Pagination pagination=new Pagination();
		
		//判断是否是模糊查询
		if(hslCouponQO!=null){
			hslCouponQO.setIdLike(true);
//			hslCouponQO.setCouponId(hslCouponQO.getId());
			hslCouponQO.setId(null);
			hslCouponQO.setLoginNameLike(true);
		}
		
		pagination.setCondition(hslCouponQO);
		pageNo=pageNo==null?new Integer(1):pageNo;
		pageSize=pageSize==null?new Integer(20):pageSize;
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		
		pagination =couponService.queryPagination(pagination);
		HgLogger.getInstance().info("liusong", "分页组件查询"+pagination);
		List<CouponDTO> couponList = BeanMapperUtils.getMapper().mapAsList(pagination.getList(),CouponDTO.class);
		
	
		pagination.setList(couponList);
		model.addAttribute("pagination", pagination);
		model.addAttribute("couponQO", hslCouponQO);
		model.addAttribute("issueWayMap", CouponActivityParam.getIssueWayMap());
		model.addAttribute("couponTypeMap", CouponActivityParam.getCouponTypeMap());
		return "coupon/coupon_list.html";
	}
	
	/**
	 * 单条卡券记录作废
	 * @param command
	 * @param request
	 * @param model
	 * */
	@RequestMapping(value="/cancel")
	@ResponseBody
	public  String  cancelCoupon(HttpServletRequest request, Model model,
			@ModelAttribute CancelCouponCommand command){
		
		//新建一个卡券command
		try{
			couponService.cancelCoupon(command);
		}catch(Exception e){
			HgLogger.getInstance().error("liusong", "单条卡券记录作废失败:" + HgLogger.getStackTrace(e));
			return DwzJsonResultUtil.createSimpleJsonString("500", "卡券作废失败");
		}
		return DwzJsonResultUtil.createSimpleJsonString("200", "卡券作废成功");
	}
	
	/**
	 * 卡券记录详情
	 * @param id
	 * @param model
	 * @param request
	 * */
	@RequestMapping(value="/detail")
	public  String couponDetail(HttpServletRequest request, Model model,
			@RequestParam(value="id",required=true) String id){
		HslCouponQO  hslCouponQO = new HslCouponQO();
		hslCouponQO.setId(id);
		
		if(hslCouponQO!=null){
			hslCouponQO.setIdLike(true);
		}
		
		try{
			CouponDTO couponDto = couponService.queryUnique(hslCouponQO);
			model.addAttribute("couponDto", couponDto);
		}catch(Exception e){
			e.printStackTrace();
			HgLogger.getInstance().error("liusong", "获取卡券记录详情失败:" + HgLogger.getStackTrace(e));
		}
		return "coupon/coupon_detail.html";
	}
	
	
	/**
	 * 批量作废卡券记录
	 * @param 
	 * @param model
	 * @param request
	 * @param command
	 * */
	@RequestMapping(value="/cancelAll")
	@ResponseBody
	public   String   cancelAllcoupon(HttpServletRequest request, Model model){
		CancelCouponCommand  couponCommand = new CancelCouponCommand();
		    
			try {
				String ids = request.getParameter("ids");
			    //System.out.println(ids);
			    String[] id = ids.split(",");
			    for(int i=0;i<id.length;i++){
			    	HslCouponQO  hslCouponQO  =  new  HslCouponQO();
			    	hslCouponQO.setId(id[i]);
			    	hslCouponQO.setIdLike(true);
			    	couponCommand.setCouponId(id[i]);
			    	CouponDTO couponDto = couponService.queryUnique(hslCouponQO);
			    	
			    	if(couponDto.getStatus().getCurrentValue()!=CouponStatus.TYPE_NOUSED){
			    		HgLogger.getInstance().error("liusong", "卡券记录批量作废失败!!");
			    		return DwzJsonResultUtil.createSimpleJsonString("500", "卡券作废失败,批量作废卡券时只能作废未使用状态的卡券！！！");
			    	}else{
			    		couponService.cancelCoupon(couponCommand);
			    	}
			    }
			} catch (Exception e) {
				HgLogger.getInstance().error("liusong", "卡券记录批量作废失败:" + HgLogger.getStackTrace(e));
				return DwzJsonResultUtil.createSimpleJsonString("500", "卡券作废失败");
			}
		return DwzJsonResultUtil.createSimpleJsonString("200", "卡券批量作废成功");
	}
}

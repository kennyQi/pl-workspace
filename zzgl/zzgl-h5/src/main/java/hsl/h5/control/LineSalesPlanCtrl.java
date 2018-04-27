package hsl.h5.control;

import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.common.util.SysProperties;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;
import hsl.domain.model.lineSalesPlan.LineSalesPlanBaseInfo;
import hsl.domain.model.lineSalesPlan.order.LSPOrderTraveler;
import hsl.h5.alipay.bean.AlipayLineOrderFormBuilder;
import hsl.h5.exception.HslapiException;
import hsl.pojo.command.CommonContact.CreateCommonContactCommand;
import hsl.pojo.command.CommonContact.UpdateCommonContactCommand;
import hsl.pojo.command.lineSalesPlan.order.CreateLSPOrderCommand;
import hsl.pojo.dto.commonContact.CommonContactDTO;
import hsl.pojo.dto.lineSalesPlan.LineSalesPlanConstant;
import hsl.pojo.dto.lineSalesPlan.LineSalesPlanDTO;
import hsl.pojo.dto.lineSalesPlan.order.LSPOrderDTO;
import hsl.pojo.dto.lineSalesPlan.order.LSPOrderTravelerDTO;
import hsl.pojo.dto.user.UserDTO;
import hsl.pojo.exception.CommonContactException;
import hsl.pojo.exception.LSPException;
import hsl.pojo.exception.ShowMessageException;
import hsl.pojo.qo.CommonContact.CommonContactQO;
import hsl.pojo.qo.lineSalesPlan.LineSalesPlanQO;
import hsl.pojo.qo.lineSalesPlan.order.LSPOrderQO;
import hsl.spi.inter.commonContact.CommonContactService;
import hsl.spi.inter.lineSalesPlan.LineSalesPlanService;
import hsl.spi.inter.lineSalesPlan.order.LSPOrderService;
import hsl.spi.inter.user.UserService;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import static  hsl.h5.alipay.ctl.AlipayLSPOrderCtrl.*;

@Controller
@RequestMapping(value="lineSalesPlan")
public class LineSalesPlanCtrl extends HslCtrl{
	
	private final String  devName = "renfeng";
	@Autowired
	private CommonContactService commonContactService;
	
	@Autowired
	private LineSalesPlanService lineSalesPlanService;
	
	@Autowired
	private LSPOrderService lSPOrderService;
	
	@Autowired
	private UserService userService;
	
	//订单每次下拉加载的记录数
	final   Integer  fectNum = 20;
	/**
	 * @方法功能说明：抢购活动列表
	 * @修改者名字：renfeng
	 * @修改时间：2015年11月30日下午4:40:31
	 * @修改内容：
	 * @参数：@return
	 * @return:ModelAndView
	 * @throws
	 */
	@RequestMapping("/buyActivitylist")
	public ModelAndView list(){
		
		HgLogger.getInstance().info(devName, "跳转抢购活动页面");
		ModelAndView mav = new ModelAndView("lineSalesPlan/buyActivityList");
		
		//秒杀活动查询
		LineSalesPlanQO secKillQo=new LineSalesPlanQO();
		
		//显示 未开始、进行中、已结束的活动
		Integer[] statusArray=new Integer[]{LineSalesPlanConstant.LSP_STATUS_NOBEGIN, 
				LineSalesPlanConstant.LSP_STATUS_SALESING,LineSalesPlanConstant.LSP_STATUS_END,
				LineSalesPlanConstant.LSP_STATUS_END_GROUP_FAIL,LineSalesPlanConstant.LSP_STATUS_END_GROUP_SUCC} ;
		
		secKillQo.setStatusArray(statusArray);
		secKillQo.setShowStatus(LineSalesPlanConstant.LSP_SHOW_STATUS_SHOW);
		secKillQo.setOrderByPriority(true);
		secKillQo.setFetchLine(true);
		secKillQo.setPlanType(LineSalesPlanBaseInfo.PLANTYPE_SECKILL);
		
		List<LineSalesPlanDTO> secKillList =this.lineSalesPlanService.queryList(secKillQo);
		HgLogger.getInstance().info(devName, "跳转抢购活动页面，获取秒杀活动列表："+JSON.toJSONString(secKillList));
		
		mav.addObject("secKillList", secKillList);
		
		//团购活动查询
		LineSalesPlanQO groupQo=new LineSalesPlanQO();
		groupQo.setStatusArray(statusArray);
		groupQo.setShowStatus(LineSalesPlanConstant.LSP_SHOW_STATUS_SHOW);
		groupQo.setOrderByPriority(true);
		groupQo.setFetchLine(true);
		groupQo.setPlanType(LineSalesPlanBaseInfo.PLANTYPE_GROUP);
		
		List<LineSalesPlanDTO> groupList=this.lineSalesPlanService.queryList(groupQo);
		HgLogger.getInstance().info(devName, "跳转抢购活动页面，获取团购活动列表："+JSON.toJSONString(groupList));
		mav.addObject("groupList", groupList);
		
		
		HgLogger.getInstance().info(devName, "跳转抢购活动页面");
		String image_host=SysProperties.getInstance().get("image_host");
		mav.addObject("image_host", image_host);
		mav.addObject("serviceTime", System.currentTimeMillis());
		return mav;
	}
	
	
	/**
	 * @方法功能说明：跳转活动详情页面
	 * @修改者名字：renfeng
	 * @修改时间：2015年11月30日下午4:40:08
	 * @修改内容：
	 * @参数：@return
	 * @return:ModelAndView
	 * @throws
	 */
	@RequestMapping("/buyingActivityDetail")
	public ModelAndView detail(LineSalesPlanQO qo){
		
		HgLogger.getInstance().info(devName, "跳转抢购活动详情页面");
		ModelAndView mav = new ModelAndView("lineSalesPlan/buyingActivityDetail");
		qo.setFetchLine(true);
		LineSalesPlanDTO planDto=this.lineSalesPlanService.queryUnique(qo);
		HgLogger.getInstance().info(devName, "跳转抢购活动页面，获取活动详情："+JSON.toJSONString(planDto));
		
		mav.addObject("planDto", planDto);
		mav.addObject("cityMap", getCityMap());
		String image_host=SysProperties.getInstance().get("image_host");
		mav.addObject("image_host", image_host);
		return mav;
	}
	
	
	/**
	 * @方法功能说明：跳转填写活动订单页面
	 * @修改者名字：renfeng
	 * @修改时间：2015年12月1日上午10:16:54
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@param hslH5CreateLineOrderCommand
	 * @参数：@param request
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/toFillOrders")
	public String lSPOrderPage(Model model,HttpServletRequest request,CreateLSPOrderCommand command){
		
		HgLogger.getInstance().info(devName, "跳转抢购活动填写订单页面");
		LineSalesPlanQO qo=new LineSalesPlanQO();
		qo.setId(command.getLspId());
		qo.setFetchLine(true);
		LineSalesPlanDTO planDto=this.lineSalesPlanService.queryUnique(qo);
		HgLogger.getInstance().info(devName, "跳转抢购活动填写订单页面，获取活动详情："+JSON.toJSONString(planDto));
		
		model.addAttribute("planDto", planDto);
		String url="lineSalesPlan/fillOrders";
		//检查活动人数和是否已过期
		String message="";
		int totalNum=planDto.getLineSalesPlanSalesInfo().getProvideNum();
		int solidNum=planDto.getLineSalesPlanSalesInfo().getSoldNum();
		if(solidNum>=totalNum){
			message="购买失败，剩余人数不足";
		}else if(new Date().after(planDto.getLineSalesPlanSalesInfo().getEndDate())){
			message="购买失败，活动已结束";
		}
		
		if(StringUtils.isNotBlank(message)){
			url= "lineSalesPlan/buyingActivityDetail";
			String image_host=SysProperties.getInstance().get("image_host");
			model.addAttribute("image_host", image_host);
			model.addAttribute("cityMap", getCityMap());
		}else{
			message="1";
			
			String userId ="";
			try {
				//从request中取得userId
			    userId = getUserId(request);
				
			} catch (HslapiException e) {
				HgLogger.getInstance().info(devName, "获取用户ID发送异常："+e.getMessage());
			}
			LSPOrderQO orderQo=new LSPOrderQO();
			
			//查询用户最近订单所用的联系人
			List<LSPOrderDTO> orders=this.lSPOrderService.queryList(orderQo);
			if(orders!=null&&orders.size()>0){
				LSPOrderDTO order=orders.get(0);
				command.setLinkName(order.getOrderLinkInfo().getLinkName());
				command.setLinkMobile(order.getOrderLinkInfo().getLinkMobile());
				command.setEmail(order.getOrderLinkInfo().getEmail());
			}
			command.setLspId(planDto.getId());
			command.setUserId(userId);
			command.setAdultNo(0);
			command.setTravelDate(planDto.getLineSalesPlanSalesInfo().getTravelDate());
			command.setAdultUnitPrice(planDto.getLineSalesPlanSalesInfo().getPlanPrice());
			command.setOrderType(planDto.getBaseInfo().getPlanType());
			//带回下单数据
			model.addAttribute("lspOrderCommand", command);
			
			
			//可添加的最多游客数
			int lastNum=this.getCanAddMaxTravelerNum(planDto.getLineSalesPlanSalesInfo().getProvideNum(),
					planDto.getLineSalesPlanSalesInfo().getSoldNum(),planDto.getBaseInfo().getPlanType());
			command.setLastNum(lastNum);
			
		}
		model.addAttribute("message", message);
		HgLogger.getInstance().info(devName, "跳转填写活动订单页面，message："+message+",url:"+url);
		
		return url;
	}
	
	/**
	 * @方法功能说明：可添加的最多游客数
	 * @修改者名字：renfeng
	 * @修改时间：2015年12月9日下午4:05:09
	 * @修改内容：
	 * @参数：@param provideNum
	 * @参数：@param soldNum
	 * @参数：@param planType
	 * @参数：@return
	 * @return:int
	 * @throws
	 */
	private int getCanAddMaxTravelerNum(int provideNum,int soldNum,int planType){
		
		int lastNum=provideNum-soldNum;
		int maxNum=0;
		
		//秒杀活动一个订单最多可添加两个游客，团购一个订单最多可添加5个游客
		if(LineSalesPlanBaseInfo.PLANTYPE_SECKILL.equals(planType)){
			maxNum=2;
		}else if(LineSalesPlanBaseInfo.PLANTYPE_GROUP.equals(planType)){
			maxNum=5;
		}
		if(lastNum>maxNum){
			lastNum=maxNum;
		}
		
		return lastNum;
	}
	
/**
 * @方法功能说明：跳转游客列表
 * @修改者名字：renfeng
 * @修改时间：2015年12月1日下午1:59:10
 * @修改内容：
 * @参数：@param model
 * @参数：@param request
 * @参数：@return
 * @return:String
 * @throws
 */
	@RequestMapping(value="/addTravelerPage")
	public String commonContactList(Model model,HttpServletRequest request,CreateLSPOrderCommand command){
		
		HgLogger.getInstance().info(devName, "抢购活动填写订单页面，跳转至当前用户常用联系人页面选择游客");
		//带回下单数据
		model.addAttribute("lspOrderCommand", command);
		
		List<CommonContactDTO> commonContacts = null;
		String userId ="";
		try {
			//从request中取得userId
		    userId = getUserId(request);
			
		} catch (HslapiException e) {
			HgLogger.getInstance().info(devName, "获取用户ID发送异常："+e.getMessage());
		}
		
		CommonContactQO commonContactQO=new CommonContactQO();
		commonContactQO.setUserId(userId);
		commonContactQO.setType(1);//成人
		
		//查询用户常用联系人
		if(StringUtils.isNotBlank(userId)){
			commonContacts = commonContactService.queryList(commonContactQO);
		}
		
		HgLogger.getInstance().info(devName, "抢购活动填写订单页面，跳转至当前用户常用联系人页面选择游客，获取常用联系人列表："+JSON.toJSONString(commonContacts));
		
		if(CollectionUtils.isNotEmpty(commonContacts)){//有游客列表
			model.addAttribute("travelers", commonContacts);
			return "lineSalesPlan/commonContactList";
			
		}else{//跳转到添加游客页面
			return "lineSalesPlan/addCommonContact";
		}
		
		
	}
	
	/**
	 * @方法功能说明：跳转至添加游客页面
	 * @修改者名字：renfeng
	 * @修改时间：2015年12月1日下午1:57:39
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/addCommonContact")
	public String addTraveler(Model model,CreateLSPOrderCommand command){
		HgLogger.getInstance().info(devName, "抢购活动填写订单页面,选择游客时跳转至添加用户常用联系人页面");
		model.addAttribute("lspOrderCommand", command);

		return "lineSalesPlan/addCommonContact";
	}
	
	/**
	 * @方法功能说明：跳转至编辑游客页面
	 * @修改者名字：renfeng
	 * @修改时间：2015年12月1日下午1:57:17
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@param travelerId
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/editCommonContact")
	public String editTraveler(Model model,String travelerId,CreateLSPOrderCommand command){
		
		HgLogger.getInstance().info(devName, "抢购活动填写订单页面,选择游客时跳转至编辑用户常用联系人页面");
		
		if(StringUtils.isNotBlank(travelerId)){
			
			CommonContactQO commonContactQO=new CommonContactQO();
			commonContactQO.setId(travelerId);
			
			CommonContactDTO commonContact=this.commonContactService.queryUnique(commonContactQO);
			//HgLogger.getInstance().info(devName, "抢购活动填写订单页面,选择游客时跳转至编辑用户常用联系人页面，获取要编辑的常用联系人信息："+JSON.toJSONString(commonContact));
			model.addAttribute("traveler", commonContact);
		}

		model.addAttribute("lspOrderCommand", command);

		return "lineSalesPlan/editCommonContact";
	}
	
	/**
	 * @方法功能说明：添加游客
	 * @修改者名字：renfeng
	 * @修改时间：2015年12月1日下午1:56:58
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@param commonContactCommand
	 * @参数：@param request
	 * @参数：@param out
	 * @return:void
	 * @throws
	 */
	@RequestMapping(value="/saveAddTraveler")
	public void saveAddTraveler(Model model,CreateCommonContactCommand commonContactCommand,HttpServletRequest request,
			CreateLSPOrderCommand command,PrintWriter out){
		
		HgLogger.getInstance().info(devName, "抢购活动填写订单页面,选择游客时保存新添加的用户常用联系人");
		Map<String,String> map=new HashMap<String,String>();
		map.put("status", "success");
		String message = "";
		try {
			
			String userId = getUserId(request);
			if(StringUtils.isNotBlank(userId)){
				HgLogger.getInstance().info(devName, "抢购活动填写订单页面,保存添加的常用联系人时，获取userId："+userId);
				commonContactCommand.setUserId(userId);
				CommonContactDTO contact=this.commonContactService.addCommonContact(commonContactCommand);
				HgLogger.getInstance().info(devName, "抢购活动填写订单页面,保存添加的常用联系人后，返回添加的常用联系人信息："+JSON.toJSONString(contact));
				
			}
			
		}  catch (CommonContactException e) {
			HgLogger.getInstance().error(devName, "添加常用联系人 发生异常："+e.getMessage());
			message = e.getMessage();
			map.put("status", "error");
		} catch (HslapiException e) {
			HgLogger.getInstance().error(devName, "获取用户ID 发生异常："+e.getMessage());
			message = e.getMessage();
			map.put("status", "error");
		}
		
		
		map.put("lspOrderCommand", JSON.toJSONString(command));
		map.put("message", message);
		
		out.print(JSON.toJSONString(map));
	}
	
	/**
	 * @方法功能说明：保存编辑游客
	 * @修改者名字：renfeng
	 * @修改时间：2015年12月1日下午1:56:39
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@param updateCommonContactCommand
	 * @参数：@param request
	 * @参数：@param out
	 * @return:void
	 * @throws
	 */
	@RequestMapping(value="/saveEditTraveler")
	public void saveEditPerson(Model model,UpdateCommonContactCommand updateCommonContactCommand,HttpServletRequest request,
			CreateLSPOrderCommand command,PrintWriter out){
			
		HgLogger.getInstance().info(devName, "抢购活动填写订单页面,选择游客时保存编辑后的用户常用联系人");
		
		Map<String,String> map=new HashMap<String,String>();
		map.put("status", "success");
		String message = "";
		
		try {
			String userId = getUserId(request);
			HgLogger.getInstance().info(devName, "抢购活动填写订单页面,保存编辑的常用联系人时，获取userId："+userId);
			if(StringUtils.isNotBlank(userId)){
				
				updateCommonContactCommand.setUserId(userId);
				CommonContactDTO contact=this.commonContactService.updateCommonContact(updateCommonContactCommand);
				HgLogger.getInstance().info(devName, "抢购活动填写订单页面,保存添加的常用联系人后，返回添加的常用联系人信息："+JSON.toJSONString(contact));
				
			}
			
		} catch (ShowMessageException e) {
			e.printStackTrace();
			HgLogger.getInstance().info(devName, "保存编辑游客异常:" + e.getMessage());
			message = e.getMessage();
			map.put("status", "error");
		} catch (HslapiException e) {
			HgLogger.getInstance().info(devName, "保存编辑游客异常:" + e.getMessage());
			message = e.getMessage();
			map.put("status", "error");
		} catch (CommonContactException e) {
			HgLogger.getInstance().info(devName, "保存编辑游客异常:" + e.getMessage());
			message = e.getMessage();
			map.put("status", "error");
			e.printStackTrace();
		}
		
		map.put("lspOrderCommand", JSON.toJSONString(command));
		map.put("message", message);
		
		out.print(JSON.toJSONString(map));
	}
	
	/**
	 * @方法功能说明：
	 * @修改者名字：renfeng
	 * @修改时间：2015年12月1日下午1:56:27
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/saveTraveler")
	public String saveTraveler(Model model,CreateLSPOrderCommand command){
		
		HgLogger.getInstance().info(devName, "抢购活动填写订单页面,选择游客后回到填写订单页面");
		
		LineSalesPlanQO qo=new LineSalesPlanQO();
		qo.setId(command.getLspId());
		qo.setFetchLine(true);
		LineSalesPlanDTO planDto=this.lineSalesPlanService.queryUnique(qo);
		HgLogger.getInstance().info(devName, "选择游客后回到填写订单页面，获取活动详情："+JSON.toJSONString(planDto));
		
		model.addAttribute("planDto", planDto);
		
		CommonContactQO commonContactQO=new CommonContactQO();
		List<CommonContactDTO> travelers = new ArrayList<CommonContactDTO>();
		
		List<String> ids = command.getTravelerIds();
		if(ids!=null){
			for (String id : ids) {
				commonContactQO.setId(id);
				CommonContactDTO commonContact=this.commonContactService.queryUnique(commonContactQO);
				travelers.add(commonContact);
			}
			//选择游客人数
			command.setAdultNo(ids.size());
		}
		
		
		//计算选择游客后支付总价
		command.setSalePrice(planDto.getLineSalesPlanSalesInfo().getPlanPrice()*command.getAdultNo());
		
		model.addAttribute("lspOrderCommand", command);
		model.addAttribute("travelers", travelers);
		
		
		
		return "lineSalesPlan/fillOrders";
	}
	
	/**
	 * @方法功能说明：创建活动订单,成功后跳转支付页面
	 * @修改者名字：renfeng
	 * @修改时间：2015年12月1日下午1:38:47
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@param request
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/createLSPOrder")
	public String createLSPOrder(Model model,HttpServletRequest request,CreateLSPOrderCommand command){
		
		HgLogger.getInstance().info(devName, "创建活动订单，command："+JSON.toJSONString(command));
		String message = "";
		String url ="lineSalesPlan/payOrder";
		
		//查询抢购线路
		LineSalesPlanQO qo=new LineSalesPlanQO();
		qo.setId(command.getLspId());
		qo.setFetchLine(true);
		LineSalesPlanDTO planDto=this.lineSalesPlanService.queryUnique(qo);
		HgLogger.getInstance().info(devName, "创建订单时，获取活动详情："+JSON.toJSONString(planDto));
		try {
			
			
			String userId = getUserId(request);
			command.setUserId(userId);
			
			//补充用户信息
			UserDTO user=this.getUserByUserId(userId);
			if(user!=null){
				command.setLoginName(user.getAuthInfo().getLoginName());
				command.setMobile(user.getContactInfo().getMobile());
			}	
			
			//设置线路活动信息
			command.setOrderType(planDto.getBaseInfo().getPlanType());
			command.setAdultUnitPrice(planDto.getLineSalesPlanSalesInfo().getPlanPrice());
			command.setTravelDate(planDto.getLineSalesPlanSalesInfo().getTravelDate());
			//儿童游客人数为0,单价为0.0
			command.setChildNo(0);
			command.setChildUnitPrice(0.0);
			//设置游客列表
			List<String> travelerIds=	command.getTravelerIds();
			List<LSPOrderTravelerDTO> travelerList=new ArrayList<LSPOrderTravelerDTO>();
			CommonContactQO commonContactQO=new CommonContactQO();
			List<CommonContactDTO> commonContactList=new ArrayList<CommonContactDTO>();
			for(String travelerId:travelerIds){
				commonContactQO.setId(travelerId);
				CommonContactDTO commonContact=this.commonContactService.queryUnique(commonContactQO);
				commonContactList.add(commonContact);
				LSPOrderTravelerDTO traveler =new LSPOrderTravelerDTO();
				/**直接赋值id,传值分销*/
				traveler.setId(UUIDGenerator.getUUID());
				traveler.setIdNo(commonContact.getCardNo());
				traveler.setIdType(commonContact.getCardType());
				traveler.setMobile(commonContact.getMobile());
				traveler.setName(commonContact.getName());
				traveler.setSingleSalePrice(command.getAdultUnitPrice());
				traveler.setType(LSPOrderTraveler.TYPE_ADULT);
				travelerList.add(traveler);
			}
			command.setTravelerList(travelerList);
			
			//查询该游客在该活动中，是否已下过单（一个活动，每个游客只能参与一次）
			String msg=this.checkTravelerAlreadyHasOrderOneActivity(command.getLspId(), commonContactList);
			HgLogger.getInstance().error(devName, "创建活动订单之前，检查游客在活动中是否已经下过单:"+message);
			if(StringUtils.isNotBlank(msg)){
				message=msg;
				model.addAttribute("travelers", commonContactList);
				model.addAttribute("planDto", planDto);
				model.addAttribute("lspOrderCommand", command);
				url = "lineSalesPlan/fillOrders";
			}else{
				
				//下单
				LSPOrderDTO orderDto=this.lSPOrderService.createLSPOrder(command);
				HgLogger.getInstance().info(devName, "创建活动订单成功，orderDto:"+JSON.toJSONString(orderDto));
				model.addAttribute("orderDto",orderDto);
			}
						
			
		} catch(LSPException e) {
			e.printStackTrace();
			HgLogger.getInstance().error(devName, "创建活动订单发生异常:"+HgLogger.getStackTrace(e));
			message=e.getMessage();
			model.addAttribute("planDto", planDto);
			url = "lineSalesPlan/fillOrders";
			//重置游客人数
			command.setAdultNo(0);
			model.addAttribute("lspOrderCommand", command);
		} catch (HslapiException e) {
			e.printStackTrace();
			HgLogger.getInstance().error(devName, "创建活动订单时，获取用户失败:"+HgLogger.getStackTrace(e));
			message=e.getMessage();
			model.addAttribute("planDto", planDto);
			url = "lineSalesPlan/fillOrders";
			//重置游客人数
			command.setAdultNo(0);
			model.addAttribute("lspOrderCommand", command);
		}
		
		model.addAttribute("message", message);
		
		return url;
		
	}
	
	/**
	 * @方法功能说明：//查询该游客在该活动中，是否已下过单（一个活动，每个游客只能参与一次）
	 * @修改者名字：renfeng
	 * @修改时间：2015年12月10日上午9:41:41
	 * @修改内容：
	 * @参数：@param planId
	 * @参数：@param travelers
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	private String checkTravelerAlreadyHasOrderOneActivity(String planId,List<CommonContactDTO> travelers){
		
		String msg="";
		LSPOrderQO orderQo=new LSPOrderQO();
		orderQo.setPlanId(planId);
		orderQo.setFetchTraveler(true);
		
		Integer[] orderStatusArray=new Integer[]{
				LineSalesPlanConstant.LSP_ORDER_STATUS_SUCCESS_NOT_RESERVE, 
				LineSalesPlanConstant.LSP_ORDER_STATUS_RESERVE_SUCCESS,
				LineSalesPlanConstant.LSP_ORDER_STATUS_SUCCESS_GROUP_ING,
				LineSalesPlanConstant.LSP_ORDER_STATUS_SUCCESS_GROUP_SUC} ;
		
		orderQo.setOrderStatusArray(orderStatusArray);
		
		for(CommonContactDTO traveler:travelers){
			orderQo.setTravelerIdNo(traveler.getCardNo());
			List<LSPOrderDTO> orderList=this.lSPOrderService.queryList(orderQo);
			if(orderList!=null&&orderList.size()>0){
				msg=traveler.getName()+" 已参与该活动";
				break;
			}
			
		}
		
		
		return msg;
	}
	/**
	 * @方法功能说明：订单支付
	 * @修改者名字：renfeng
	 * @修改时间：2015年12月2日下午3:57:03
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@param request
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/payLSPOrder")
	public String payLSPOrder(Model model,HttpServletRequest request){
		
		
		String message = "";
		String url = "";
		String alipayFormHtml="";
		LSPOrderDTO orderDto=null;
		
		String returnUrl = getWebAppPath(request);
		String lspOrderId=request.getParameter("lspId");
		HgLogger.getInstance().info(devName, "订单支付 ，订单ID："+lspOrderId);
		try {
			
			LSPOrderQO orderQo=new LSPOrderQO();
			orderQo.setId(lspOrderId);
		    orderDto=this.lSPOrderService.queryUnique(orderQo);
		    String userId=this.getUserId(request);
		    if(orderDto==null){
		    	message="订单不存在";
		    }else if(!orderDto.getOrderUser().getUserId().equals(userId)){
		    	message="操作有误，非当前用户订单";
		    	orderDto=null;
		    }else{
		    	 //支付之前，检查活动否已过期，订单是否有效
				 message=this.checkTravelerNumAndActivityDate(orderDto);
				
		    }
		    
		    
		    if(StringUtils.isNotBlank(message)){
				url= "lineSalesPlan/payOrder";
				model.addAttribute("orderDto",orderDto);
			}else{
				message="1";
				//组装跳转支付宝页面form
				AlipayLineOrderFormBuilder builder=new AlipayLineOrderFormBuilder(returnUrl);
				String subject = "“"+orderDto.getLineSalesPlan().getBaseInfo().getPlanName()  + "全款支付”" ;
				alipayFormHtml = builder.buildAlipayFormHtml(orderDto.getOrderBaseInfo().getDealerOrderNo(), 
						subject, orderDto.getOrderBaseInfo().getSalePrice(),SUFFIX);
				HgLogger.getInstance().info(devName, "订单支付 ，跳转支付宝页面form："+alipayFormHtml);
				model.addAttribute("payForm", alipayFormHtml);
				url="line/orderPayForm";
			}
		 
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().info(devName, "确认支付订单发生异常_原因:"+HgLogger.getStackTrace(e));
			message = e.getMessage();
			model.addAttribute("orderDto",orderDto);
			url="lineSalesPlan/payOrder";
			
		}
		
		model.addAttribute("message", message);
		
		return url;
		
	}
	/**
	 * @方法功能说明：支付前，检查活动人数是否满员，过期
	 * @修改者名字：renfeng
	 * @修改时间：2015年12月9日下午2:05:06
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	private String checkTravelerNumAndActivityDate(LSPOrderDTO orderDto){
		String message="";
		 int planType=orderDto.getLineSalesPlan().getBaseInfo().getPlanType();
		   
	    //秒杀活动，订单是否已取消（下单20分钟未支付，定时器取消该订单）,如果已取消，不允许支付
	    if(LineSalesPlanConstant.LSP_ORDER_STATUS_CANCEL.equals(orderDto.getOrderStatus().getOrderStatus())){
			message="支付失败，订单已取消，请重新购买";
			
	    }else if(LineSalesPlanBaseInfo.PLANTYPE_GROUP.equals(planType)){
	    	
	    	 int totalNum=orderDto.getLineSalesPlan().getLineSalesPlanSalesInfo().getProvideNum();
	    	 int solidNum=orderDto.getLineSalesPlan().getLineSalesPlanSalesInfo().getSoldNum();
			 int orderTtavelerNum=orderDto.getOrderBaseInfo().getAdultNo();
	    	 //团购活动，活动过期，不允许支付；
	    	 if(new Date().after(orderDto.getLineSalesPlan().getLineSalesPlanSalesInfo().getEndDate())){
				 message="支付失败，活动已结束";
			 }else if(solidNum+orderTtavelerNum>totalNum){
				 //订单人数+已售人数>活动人数，不允许支付
				 message="支付失败，活动人数已不足";
			 }
	    	
	    	 
	    }
		
		return message;
	}
	
	/**
	 * @方法功能说明：支付成功跳转页面
	 * @修改者名字：renfeng
	 * @修改时间：2015年12月1日下午4:53:57
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@param dealerOrderNo
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/orderPaySuccess")
	public String success(Model model,String dealerOrderNo){
		HgLogger.getInstance().info(devName, "跳转支付成功页面，订单ID："+dealerOrderNo);
		try{
			
			LSPOrderQO orderQo=new LSPOrderQO();
			orderQo.setDealerOrderNo(dealerOrderNo);
			LSPOrderDTO orderDto=this.lSPOrderService.queryUnique(orderQo);
			HgLogger.getInstance().info(devName,"支付成功，查询订单详情："+JSON.toJSONString(orderDto));
			model.addAttribute("orderDto", orderDto);
			
		}catch (Exception e){
			e.printStackTrace();
			HgLogger.getInstance().error(devName,"支付成功后，跳转支付成功页面出错："+e.getMessage());
		}
		
		return "lineSalesPlan/paySuccess";
	}
	
	/**
	 * @方法功能说明：线路抢购活动订单列表
	 * @修改者名字：renfeng
	 * @修改时间：2015年12月7日下午3:23:02
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@param request
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/lspOrderList")
	public String lspOrderList(Model model,HttpServletRequest request){
		
		
		return "lineSalesPlan/lspOrderList";
	}
	
	/**
	 * @方法功能说明：订单列表加载更多
	 * @修改者名字：renfeng
	 * @修改时间：2015年12月7日下午3:33:51
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@param queryNum
	 * @参数：@param request
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/loadMoreOrderList")
	@ResponseBody
	public String lspOrderList(Model model,Integer queryNum,HttpServletRequest request){
		
		HgLogger.getInstance().info(devName, "加载更多活动订单，页码："+queryNum);
		List<String>  strList = new ArrayList<String>();
		
		try {
			String userId = getUserId(request);
			if(StringUtils.isNotBlank(userId)){
				Pagination orderPage=new Pagination();
				orderPage.setPageNo(queryNum);
				orderPage.setPageSize(fectNum);
				
				LSPOrderQO orderQo=new LSPOrderQO();
				orderQo.setUserId(userId);
				orderPage.setCondition(orderQo);
				
				List<LSPOrderDTO> lspOrders = (List<LSPOrderDTO>) this.lSPOrderService.queryPagination(orderPage).getList();
				HgLogger.getInstance().info(devName, "加载更多活动订单，获取订单列表："+JSON.toJSONString(lspOrders));
				
				for (LSPOrderDTO lspOrder : lspOrders) {
					String str = lspOrder.getOrderBaseInfo().getDealerOrderNo()+","+lspOrder.getLineSalesPlan().getBaseInfo().getPlanName()+","
						+DateUtil.formatDateTime(lspOrder.getOrderBaseInfo().getCreateDate())+","+lspOrder.getOrderBaseInfo().getSalePrice()+","
						+lspOrder.getOrderStatus().getOrderStatus();
					strList.add(str);
				}
			}
			
		} catch (HslapiException e) {
			HgLogger.getInstance().error(devName, "下拉加载更多活动订单列表,发生异常："+e.getMessage());
		}
		
		if(CollectionUtils.isNotEmpty(strList)){
			return JSON.toJSONString(strList);
		}else{
			return "[]";
		}
	}
	
	/**
	 * @方法功能说明：订单详情页面
	 * @修改者名字：renfeng
	 * @修改时间：2015年12月1日下午4:56:18
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@param dealerOrderNo
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/orderDetail")
	public String lspOrderDetail(Model model,String dealerOrderNo,HttpServletRequest request){
		
		HgLogger.getInstance().error(devName,"H5查询抢购活动订单详,订单号：："+JSON.toJSONString(dealerOrderNo));
		if(StringUtils.isBlank(dealerOrderNo)){
			//抛异常页面
			return "lineSalesPlan/error";
		}
		String message="";
		try{
			//【1】查询抢购活动订单
			LSPOrderQO orderQo=new LSPOrderQO();
			orderQo.setDealerOrderNo(dealerOrderNo);
			LSPOrderDTO orderDto=this.lSPOrderService.queryUnique(orderQo);
			HgLogger.getInstance().info(devName,"抢购活动订单详情："+JSON.toJSONString(orderDto));
			
			
			String userId = getUserId(request);
			if(orderDto==null){
				message="订单不存在";
			}else if(!orderDto.getOrderUser().getUserId().equals(userId)){
				orderDto=null;
				message="非当前用户订单";
			}
			model.addAttribute("orderDto", orderDto);
			
		}catch (HslapiException e){
			e.printStackTrace();
			HgLogger.getInstance().error(devName,"H5查询抢购活动订单详情，获取当前用户信息出错");
		}
		
		model.addAttribute("activityStatusMap", LineSalesPlanConstant.LSP_STATUS);
		model.addAttribute("orderStatusMap", LineSalesPlanConstant.LSP_ORDER_STATUS);
		model.addAttribute("orderPayStatusMap", LineSalesPlanConstant.LSP_ORDER_PAY_STATUS);
		model.addAttribute("serviceTime", System.currentTimeMillis());
		
		model.addAttribute("message", message);
		
		return "lineSalesPlan/activityOrderDetail";
	}
	
	/**
	 * @方法功能说明：个人中心活动订单详情去支付页面
	 * @修改者名字：renfeng
	 * @修改时间：2015年12月7日下午4:27:48
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param orderId
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/detailToPayPage")
	public String orderDetailSubmit(HttpServletRequest request,Model model,String orderId,PrintWriter out){
		HgLogger.getInstance().error(devName,"H5活动订单详情跳转支付页面,订单ID："+JSON.toJSONString(orderId));
		LSPOrderQO orderQo=new LSPOrderQO();
		orderQo.setId(orderId);
		
		LSPOrderDTO orderDto=this.lSPOrderService.queryUnique(orderQo);
		HgLogger.getInstance().info(devName,"H5活动订单详情跳转支付页面,订单查询："+JSON.toJSONString(orderDto));
		
		
		String url="lineSalesPlan/payOrder";
		String message="";
		String userId="";
		try {
			userId = this.getUserId(request);
		} catch (HslapiException e) {
			e.printStackTrace();
		}
		if(orderDto==null){
		    message="订单不存在";
	    }else if(!orderDto.getOrderUser().getUserId().equals(userId)){
	    	message="操作有误，非当前用户订单";
	    	orderDto=null;
	    }else{
	    	//支付之前，检查活动否已过期，订单是否有效
			message=this.checkTravelerNumAndActivityDate(orderDto);
	    }
		
		//返回订单详情页面
		if(StringUtils.isNotBlank(message)){
			url= "lineSalesPlan/activityOrderDetail";
			model.addAttribute("activityStatusMap", LineSalesPlanConstant.LSP_STATUS);
			model.addAttribute("orderStatusMap", LineSalesPlanConstant.LSP_ORDER_STATUS);
			model.addAttribute("orderPayStatusMap", LineSalesPlanConstant.LSP_ORDER_PAY_STATUS);
			model.addAttribute("serviceTime", System.currentTimeMillis());
		}else{
			message="1";
		}
		
		
		model.addAttribute("orderDto", orderDto);
		model.addAttribute("message", message);
	
		return url;
	}
	
	
	
	
	/**
	 *  获取webapp路径
	 * @return
	 */
	private String getWebAppPath(HttpServletRequest request) {
		Boolean isRoot = Boolean.parseBoolean(SysProperties.getInstance().get("root"));
		String proj = request.getContextPath();
		String port = SysProperties.getInstance().get("port");
		String system = "http://" + SysProperties.getInstance().get("host") +
				("80".equals(port) ? "" : ":" + port);
		if (!isRoot) {
			system += proj;
		}
		return system;
	}
}

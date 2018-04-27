package lxs.api.action.order.line;

import hg.common.util.SMSUtils;
import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.user.ContactsDTO;
import lxs.api.v1.request.command.order.line.CreateLineOrderCommand;
import lxs.api.v1.response.order.line.CreateLineOrderResponse;
import lxs.app.service.app.OrderNoticeService;
import lxs.app.service.line.LineOrderLocalService;
import lxs.app.service.line.LineOrderTravelerService;
import lxs.app.service.line.LineService;
import lxs.app.service.user.LxsUserService;
import lxs.domain.model.app.OrderNotice;
import lxs.domain.model.line.Line;
import lxs.domain.model.line.LineOrder;
import lxs.domain.model.line.LineOrderTraveler;
import lxs.domain.model.user.LxsUser;
import lxs.pojo.dto.line.LineOrderBaseInfoDTO;
import lxs.pojo.dto.line.LineOrderDTO;
import lxs.pojo.dto.line.LineOrderLinkInfoDTO;
import lxs.pojo.dto.line.LineOrderTravelerDTO;
import lxs.pojo.dto.line.LineOrderUserDTO;
import lxs.pojo.exception.line.LineException;
import lxs.pojo.exception.line.LineOrderException;
import lxs.pojo.qo.app.OrderNoticeQO;
import lxs.pojo.qo.line.LineOrderQO;
import lxs.pojo.qo.line.LineOrderTravelerQO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：
 * @类修改者：
 * @修改日期：2015年4月24日下午4:11:35
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年4月24日下午4:11:35
 */
@Component("CreateLineOrderAction")
public class CreateLineOrderAction implements LxsAction {

	@Autowired
	private LxsUserService lxsUserService;
	@Autowired
	private LineOrderLocalService lineOrderLocalService;
	@Autowired
	private LineOrderTravelerService lineOrderTravelerService;
	@Autowired
	private SMSUtils smsUtils;
	@Autowired
	private LineService lineService;
	@Autowired
	private OrderNoticeService orderNoticeService;
	
	private static final String CONTACTS_TYPE_ADULT="adult";
	private static final String CONTACTS_TYPE_CHILD="child";
	private static final Integer CONTACTS_TYPE_ADULT_VAL=1;
	private static final Integer CONTACTS_TYPE_CHILD_VAL=2;
	private static final Integer CONTACTS_ID_TYPE=1;//身份证
	
	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev",  "【CreateLineOrderAction】"+"进入action");
		CreateLineOrderCommand apicCreateLineOrderCommand = JSON.parseObject(apiRequest.getBody().getPayload(), CreateLineOrderCommand.class);
		CreateLineOrderResponse createLineOrderResponse = new CreateLineOrderResponse();
		try {
			lxs.pojo.command.line.CreateLineOrderCommand createLineOrderCommand = new lxs.pojo.command.line.CreateLineOrderCommand();
			//-----------开始获得游客信息
			HgLogger.getInstance().info("lxs_dev", "【CreateLineOrderAction】"+"开始获得游客信息");
			List<LineOrderTravelerDTO> lineOrderTravelers = new ArrayList<LineOrderTravelerDTO>();
			if(apicCreateLineOrderCommand.getLineOrderDTO().getContactsList()==null){
				throw new LineOrderException(LineOrderException.RESULT_NO_TRAVELER, "游客信息为空");
			}
			for(ContactsDTO contact:apicCreateLineOrderCommand.getLineOrderDTO().getContactsList()){
				LineOrderTravelerDTO lineOrderTraveler = new LineOrderTravelerDTO();
				//设置用户类型
				if(StringUtils.endsWith(contact.getType(), CONTACTS_TYPE_ADULT)){
					lineOrderTraveler.setType(CONTACTS_TYPE_ADULT_VAL);
				}else if(StringUtils.endsWith(contact.getType(),CONTACTS_TYPE_CHILD)){
					lineOrderTraveler.setType(CONTACTS_TYPE_CHILD_VAL);
				}
				//身份证
				lineOrderTraveler.setIdType(CONTACTS_ID_TYPE);
				//设置游客姓名
				lineOrderTraveler.setName(contact.getContactsName());
				//设置游客手机
				lineOrderTraveler.setMobile(contact.getMobile());
				//设置游客身份证号
				lineOrderTraveler.setIdNo(contact.getContactsIdCardNO());
				lineOrderTravelers.add(lineOrderTraveler);
			}
			//-----------结束获得游客信息
			createLineOrderCommand.setTravelerList(lineOrderTravelers);
			//-----------开始获得登录人信息
			HgLogger.getInstance().info("lxs_dev", "【CreateLineOrderAction】"+"开始获得登录人信息");
			LxsUser lxsUser = lxsUserService.get(apicCreateLineOrderCommand.getLineOrderDTO().getUserID());
			if(lxsUser==null){
				throw new LineOrderException(LineOrderException.RESULT_NO_USER, "用户不能为空");
			}
			LineOrderUserDTO lineOrderUser = new LineOrderUserDTO();
			//设置登录名
			lineOrderUser.setLoginName(lxsUser.getAuthInfo().getLoginName());
			//设置电话
			lineOrderUser.setMobile(lxsUser.getContactInfo().getMobile());
			//设置身份证号
			lineOrderUser.setUserId(lxsUser.getId());
			//-----------结束获得登录人信息
			createLineOrderCommand.setLineOrderUser(lineOrderUser);
			//-----------开始设置订单基本信息
			HgLogger.getInstance().info("lxs_dev","【CreateLineOrderAction】"+ "开始获得订单基本信息");
			LineOrderBaseInfoDTO lineOrderBaseInfoDTO = new LineOrderBaseInfoDTO();
			//设置成人游客数量
			if(Integer.parseInt(apicCreateLineOrderCommand.getLineOrderDTO().getAdultNO())+Integer.parseInt(apicCreateLineOrderCommand.getLineOrderDTO().getChildNO())==0){
				throw new LineOrderException(LineOrderException.RESULT_NO_TRAVELER_NUMBER, "游客数量为空");
			}
			lineOrderBaseInfoDTO.setAdultNo(Integer.parseInt(apicCreateLineOrderCommand.getLineOrderDTO().getAdultNO()));
			//设置成人游客单价
			if(Double.parseDouble(apicCreateLineOrderCommand.getLineOrderDTO().getAdultUnitPrice())==0||apicCreateLineOrderCommand.getLineOrderDTO().getAdultUnitPrice()==null){				
				throw new LineOrderException(LineOrderException.RESULT_NO_ADULT_PRICE, "成人游客票价不能为空");
			}
			lineOrderBaseInfoDTO.setAdultUnitPrice(Double.valueOf(apicCreateLineOrderCommand.getLineOrderDTO().getAdultUnitPrice()));
			//设置儿童游客数量
			lineOrderBaseInfoDTO.setChildNo(Integer.parseInt(apicCreateLineOrderCommand.getLineOrderDTO().getChildNO()));
			if(Integer.parseInt(apicCreateLineOrderCommand.getLineOrderDTO().getChildNO())!=0){
				//设置儿童游客单价
				if(Double.parseDouble(apicCreateLineOrderCommand.getLineOrderDTO().getChildUnitPrice())==0||apicCreateLineOrderCommand.getLineOrderDTO().getChildUnitPrice()==null){				
					throw new LineOrderException(LineOrderException.RESULT_NO_CHILD_PRICE, "儿童游客票价不能为空");
				}
			}
			lineOrderBaseInfoDTO.setChildUnitPrice(Double.valueOf(apicCreateLineOrderCommand.getLineOrderDTO().getChildUnitPrice()));
			//设置定金
			if(apicCreateLineOrderCommand.getLineOrderDTO().getBargainMoney()==null){				
				throw new LineOrderException(LineOrderException.RESULT_NO_BARGAIN_MONEY, "定金为空");
			}
			lineOrderBaseInfoDTO.setBargainMoney(Double.valueOf(apicCreateLineOrderCommand.getLineOrderDTO().getBargainMoney()));
			//设置全款金额
			if(apicCreateLineOrderCommand.getLineOrderDTO().getSalePrice()==null){				
				throw new LineOrderException(LineOrderException.RESULT_NO_ALL_MONEY, "全款为空");
			}
			lineOrderBaseInfoDTO.setSalePrice(Double.valueOf(apicCreateLineOrderCommand.getLineOrderDTO().getSalePrice()));
			//设置旅行出发日期
			if(apicCreateLineOrderCommand.getLineOrderDTO().getTravelDate()==null){				
				throw new LineOrderException(LineOrderException.RESULT_NO_TRAVEL_DATE, "出发日期为空");
			}
			lineOrderBaseInfoDTO.setTravelDate(apicCreateLineOrderCommand.getLineOrderDTO().getTravelDate());
			//设置订单创建日期
			lineOrderBaseInfoDTO.setCreateDate(new Date());
			//-----------结束设置订单基本信息
			createLineOrderCommand.setBaseInfo(lineOrderBaseInfoDTO);
			//-----------开始设置订单联系人信息
			HgLogger.getInstance().info("lxs_dev", "【CreateLineOrderAction】"+"开始获得订单联系人信息");
			LineOrderLinkInfoDTO lineOrderLinkInfoDTO = new LineOrderLinkInfoDTO();
			//设置订单联系人姓名
			if(apicCreateLineOrderCommand.getLineOrderDTO().getLinkName()==null){				
				throw new LineOrderException(LineOrderException.RESULT_NO_LINK_NAME, "订单联系人姓名为空");
			}
			lineOrderLinkInfoDTO.setLinkName(apicCreateLineOrderCommand.getLineOrderDTO().getLinkName());
			//设置订单联系人电话
			if(apicCreateLineOrderCommand.getLineOrderDTO().getLinkMobile()==null){				
				throw new LineOrderException(LineOrderException.RESULT_NO_LINK_MOBILE, "订单联系人电话为空");
			}
			lineOrderLinkInfoDTO.setLinkMobile(apicCreateLineOrderCommand.getLineOrderDTO().getLinkMobile());
			//设置订单联系人邮箱
			if(apicCreateLineOrderCommand.getLineOrderDTO().getLinkEmail()==null){				
				throw new LineOrderException(LineOrderException.RESULT_NO_LINK_EMAIL, "订单联系人邮箱为空");
			}
			lineOrderLinkInfoDTO.setEmail(apicCreateLineOrderCommand.getLineOrderDTO().getLinkEmail());
			//-----------结束设置订单联系人信息
			createLineOrderCommand.setLinkInfo(lineOrderLinkInfoDTO);
			//设置订单来源
			createLineOrderCommand.setSource(1);
			//设置线路信息
			if(apicCreateLineOrderCommand.getLineOrderDTO().getLineID()==null){				
				throw new LineOrderException(LineOrderException.RESULT_NO_LINE, "线路为空");
			}
			createLineOrderCommand.setLineID(apicCreateLineOrderCommand.getLineOrderDTO().getLineID());
			createLineOrderCommand.setInsurancePrice(apicCreateLineOrderCommand.getLineOrderDTO().getInsurancePrice());
			createLineOrderCommand.setIsBuyInsurance(apicCreateLineOrderCommand.getLineOrderDTO().getIsBuyInsurance());
			try {
				HgLogger.getInstance().info("lxs_dev","【CreateLineOrderAction】"+ "开始创建订单");
				LineOrderDTO lineOrderDTO=lineOrderLocalService.createLineOrder(createLineOrderCommand,apicCreateLineOrderCommand.getLineOrderDTO().getPayment());
				if(lineOrderDTO==null){
					throw new LineException(-1, "分销创建订单失败");
				}
				createLineOrderResponse.setOrderID(lineOrderDTO.getId());
				createLineOrderResponse.setOrderNO(lineOrderDTO.getBaseInfo().getDealerOrderNo());
				LineOrderTravelerQO lineOrderTravelerQO = new LineOrderTravelerQO();
				lineOrderTravelerQO.setLineOrderId(lineOrderDTO.getId());
				List<LineOrderTraveler> lineOrderTravelers1= lineOrderTravelerService.queryList(lineOrderTravelerQO);
				List<ContactsDTO> contactsList = new ArrayList<ContactsDTO>();
				for(LineOrderTraveler lineOrderTraveler:lineOrderTravelers1){
					ContactsDTO contactsDTO= new ContactsDTO();
					contactsDTO.setContactsIdCardNO(lineOrderTraveler.getIdNo());
					contactsDTO.setContactsName(lineOrderTraveler.getName());
					contactsDTO.setMobile(lineOrderTraveler.getMobile());
					contactsDTO.setOrderStatus(lineOrderTraveler.getLineOrderStatus().getOrderStatus().toString());
					contactsDTO.setPayStatus(lineOrderTraveler.getLineOrderStatus().getPayStatus().toString());
					if(StringUtils.equals(CONTACTS_TYPE_ADULT_VAL.toString(), lineOrderTraveler.getType().toString())){
						contactsDTO.setType(CONTACTS_TYPE_ADULT);
					}
					if(StringUtils.equals(CONTACTS_TYPE_CHILD_VAL.toString(), lineOrderTraveler.getType().toString())){
						contactsDTO.setType(CONTACTS_TYPE_CHILD);
					}
					contactsList.add(contactsDTO);
				}
				createLineOrderResponse.setContactsList(contactsList);
				createLineOrderResponse.setSalePrice(lineOrderDTO.getBaseInfo().getSalePrice().toString());
				createLineOrderResponse.setBargainMoney(lineOrderDTO.getBaseInfo().getBargainMoney().toString());
				HgLogger.getInstance().info("lxs_dev", "【CreateLineOrderAction】"+"<----------------------------------------------------------->");
				HgLogger.getInstance().info("lxs_dev", "【CreateLineOrderAction】"+"订单创建成功，订单号："+lineOrderDTO.getBaseInfo().getDealerOrderNo()+"订单id："+lineOrderDTO.getId());
				HgLogger.getInstance().info("lxs_dev", "【CreateLineOrderAction】"+"<----------------------------------------------------------->");
				//发送短信
				final String dealerOrderNo = lineOrderDTO.getBaseInfo().getDealerOrderNo();
				final String lineID = apicCreateLineOrderCommand.getLineOrderDTO().getLineID();
				LineOrderQO lineOrderQO = new LineOrderQO();
				lineOrderQO.setDealerOrderNo(dealerOrderNo);
				HgLogger.getInstance().info("lxs_dev","【CreateLineOrderAction】"+"【短信通知】"+"订单号：" + dealerOrderNo);
				final LineOrder myLineOrder = lineOrderLocalService.queryUnique(lineOrderQO);
				final Line line = lineService.get(lineID);
				OrderNotice orderNotice = orderNoticeService.queryUnique(new OrderNoticeQO());
				String string ="";
				if(orderNotice!=null&&orderNotice.getPhonoNumber()!=null&&StringUtils.isNotBlank(orderNotice.getPhonoNumber())){
					string = orderNotice.getPhonoNumber();
				}
				final String adminPhone  = string; 
				new Thread(){
					public void run(){
						if(myLineOrder!=null&&myLineOrder.getLinkInfo()!=null&&myLineOrder.getLinkInfo().getLinkMobile()!=null){
							try {
								Date daydate=new Date();
								long payTotalDaysBeforeStart=myLineOrder.getLineSnapshot().getLine().getPayInfo().getPayTotalDaysBeforeStart()*24*60*60*1000;
								//出发时间
								long date=myLineOrder.getBaseInfo().getTravelDate().getTime();
								//定金or全额支付 1为支付定金2为全额支付
								if(line.getPayInfo().getDownPayment()==0&&daydate.getTime()+payTotalDaysBeforeStart<date){
									//定金为零
									String text ="";
									text = "【票量旅游】亲爱的用户，您的订单"+dealerOrderNo+"已经成功提交预约，客服将在2小时内与您进行电话确认，请您耐心等待。客服专线0571-28280815。";
									HgLogger.getInstance().info("lxs_dev","【CreateLineOrderAction】"+"【短信通知】"+"发送短信内容，电话"+myLineOrder.getLinkInfo().getLinkMobile());
									HgLogger.getInstance().info("lxs_dev","【CreateLineOrderAction】"+"【短信通知】"+"发送短信内容，内容"+text);
									smsUtils.sendSms(myLineOrder.getLinkInfo().getLinkMobile(),text);
									//--------给后台运营人员发短信-------
									if(adminPhone!=null&&StringUtils.isNotBlank(adminPhone)){
										text = "【票量旅游】随心游有订单"+dealerOrderNo+"生成，请及时登录后台查看，并在1小时内处理进行处理。";
										HgLogger.getInstance().info("lxs_dev","【CreateLineOrderAction】"+"【短信通知】"+"发送短信内容，电话"+adminPhone);
										HgLogger.getInstance().info("lxs_dev","【CreateLineOrderAction】"+"【短信通知】"+"发送短信内容，内容"+text);
										smsUtils.sendSms(adminPhone,text);
									}
									HgLogger.getInstance().info("lxs_dev","【CreateLineOrderAction】"+"【短信通知】"+"发送短信内容【成功】");
								}
							} catch (Exception e) {
								HgLogger.getInstance().info("lxs_dev","【CreateLineOrderAction】"+"【短信通知】"+"发生异常" +HgLogger.getStackTrace(e));
							}
						}
					}
				}.start();
			} catch (LineException e) {
				throw new LineOrderException(LineOrderException.RESULT_CREATE_ORDER_FAILED, "订单创建失败");
			}
			createLineOrderResponse.setResult(ApiResponse.RESULT_CODE_OK);
			createLineOrderResponse.setMessage("订单提交成功");
		} catch (LineOrderException e) {
			HgLogger.getInstance().info("lxs_dev", "【CreateLineOrderAction】"+"异常，"+HgLogger.getStackTrace(e));
			createLineOrderResponse.setResult(e.getCode());
			createLineOrderResponse.setMessage(e.getMessage());
		}
		HgLogger.getInstance().info("lxs_dev", "【CreateLineOrderAction】"+"创建订单结果"+JSON.toJSONString(createLineOrderResponse));
		return JSON.toJSONString(createLineOrderResponse);
	}

}

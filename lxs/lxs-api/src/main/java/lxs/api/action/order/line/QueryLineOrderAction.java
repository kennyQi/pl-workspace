package lxs.api.action.order.line;

import hg.common.page.Pagination;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.base.ApiResponse;
import lxs.api.v1.dto.order.line.LineOrderInfoDTO;
import lxs.api.v1.dto.order.line.LineOrderListDTO;
import lxs.api.v1.dto.user.ContactsDTO;
import lxs.api.v1.request.qo.order.line.LineOrderQO;
import lxs.api.v1.response.order.line.QueryLineOrderResponse;
import lxs.app.service.line.LineOrderLocalService;
import lxs.app.service.line.LineOrderTravelerService;
import lxs.domain.model.line.Line;
import lxs.domain.model.line.LineImage;
import lxs.domain.model.line.LineOrder;
import lxs.domain.model.line.LineOrderTraveler;
import lxs.pojo.dto.line.XLOrderStatusConstant;
import lxs.pojo.exception.line.LineOrderException;
import lxs.pojo.qo.line.LineOrderTravelerQO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
@Component("QueryLineOrderAction")
public class QueryLineOrderAction implements LxsAction {

	@Autowired
	private LineOrderLocalService lineOrderLocalService;
	@Autowired
	private LineOrderTravelerService lineOrderTravelerService;
	
	private static final String CONTACTS_TYPE_ADULT="adult";
	private static final String CONTACTS_TYPE_CHILD="child";
	private static final Integer CONTACTS_TYPE_ADULT_VAL=1;
	private static final Integer CONTACTS_TYPE_CHILD_VAL=2;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		HgLogger.getInstance().info("lxs_dev", "【CreateLineOrderAction】"+"进入action");
		LineOrderQO apilineOrderQO = JSON.parseObject(apiRequest.getBody().getPayload(), LineOrderQO.class);
		lxs.pojo.qo.line.LineOrderQO lineOrderQO = new lxs.pojo.qo.line.LineOrderQO();
		QueryLineOrderResponse queryLineOrderResponse= new QueryLineOrderResponse();
		try{
			if(apilineOrderQO.getLineOrderID()!=null){
				HgLogger.getInstance().info("lxs_dev", "【CreateLineOrderAction】"+"开始查询线路订单"+apilineOrderQO.getLineOrderID());
				lineOrderQO.setLineId(apilineOrderQO.getLineOrderID());
				LineOrder lineOrder=lineOrderLocalService.get(lineOrderQO.getLineId());
				HgLogger.getInstance().info("lxs_dev", "【CreateLineOrderAction】"+"线路订单查询成功");
				if(lineOrder==null){
					throw new LineOrderException(LineOrderException.RESULT_ORDER_NOT_FOUND, "线路订单不存在");
				}
				LineOrderInfoDTO lineOrderInfoDTO = new LineOrderInfoDTO();
				//获取订单ID
				lineOrderInfoDTO.setOrderID(lineOrder.getId());
				//获取订单号
				lineOrderInfoDTO.setOrderNO(lineOrder.getBaseInfo().getDealerOrderNo());
				//获取订单创建时间
				lineOrderInfoDTO.setCreateDate(lineOrder.getBaseInfo().getCreateDate().toString());
				//获取订单名称（线路名称）
				Line line=JSON.parseObject(lineOrder.getLineSnapshot().getAllInfoLineJSON(),Line.class);
				lineOrderInfoDTO.setLineName(line.getBaseInfo().getName());
				lineOrderInfoDTO.setLineID(line.getId());
				lineOrderInfoDTO.setLineNO(line.getBaseInfo().getNumber());
				//出行时间
				lineOrderInfoDTO.setTravelDate(lineOrder.getBaseInfo().getTravelDate().toString());
				//获取成人旅客数量
				lineOrderInfoDTO.setAdultNO(lineOrder.getBaseInfo().getAdultNo().toString());
				//获取儿童旅客数量
				lineOrderInfoDTO.setChildNO(lineOrder.getBaseInfo().getChildNo().toString());
				lineOrderInfoDTO.setAdultUnitPrice(lineOrder.getBaseInfo().getAdultUnitPrice());
				lineOrderInfoDTO.setChildUnitPrice(lineOrder.getBaseInfo().getChildUnitPrice());
				//获取应支付金额
				lineOrderInfoDTO.setBargainMoney(lineOrder.getBaseInfo().getBargainMoney().toString());
				lineOrderInfoDTO.setSalePrice(lineOrder.getBaseInfo().getSalePrice().toString());
				/**
				 * 需付全款提前天数
				 */
				lineOrderInfoDTO.setPayTotalDaysBeforeStart(line.getPayInfo().getPayTotalDaysBeforeStart());
				/**
				 * 最晚付款时间出发日期前
				 */
				lineOrderInfoDTO.setLastPayTotalDaysBeforeStart(line.getPayInfo().getLastPayTotalDaysBeforeStart());
				
				lineOrderInfoDTO.setDownPayment(line.getPayInfo().getDownPayment());
				
				lineOrderInfoDTO.setInsurancePrice(lineOrder.getInsurancePrice());
				
				lineOrderInfoDTO.setIsBuyInsurance(lineOrder.getIsBuyInsurance());
				/*//支付截止日
				long lastPayTotalDaysBeforeStart=line.getPayInfo().getLastPayTotalDaysBeforeStart()*24*60*60*1000;
				//当前时间（付款请求时间）
				Date now= new Date();
				//出发时间
				Date travelDate=lineOrder.getBaseInfo().getTravelDate();
				if(StringUtils.equals(lineOrder.getStatus().getOrderStatus().toString(),XLOrderStatusConstant.SHOP_CREATE_SUCCESS_NOT_RESERVE)&&StringUtils.equals(lineOrder.getStatus().getPayStatus().toString(),XLOrderStatusConstant.SHOP_WAIT_PAY_BARGAIN_MONEY)){
					HgLogger.getInstance().info("lxs_dev",  "订单无任何支付记录");
					//预定成功还没进行任何付款行为
					//支付定金截止日
					long payTotalDaysBeforeStart=line.getPayInfo().getPayTotalDaysBeforeStart()*24*60*60*1000;
					if(now.getTime()+payTotalDaysBeforeStart<travelDate.getTime()){
						HgLogger.getInstance().info("lxs_dev",  "获取定金金额");
						//支付定金
						lineOrderInfoDTO.setShouldPayMoney(Double.toString(lineOrder.getBaseInfo().getBargainMoney()));
					}else{
						//支付全款
						HgLogger.getInstance().info("lxs_dev",  "获取全款金额");
						lineOrderInfoDTO.setShouldPayMoney(Double.toString(lineOrder.getBaseInfo().getSalePrice()));
					}
				}else if(StringUtils.equals(lineOrder.getStatus().getOrderStatus().toString(),XLOrderStatusConstant.SHOP_CREATE_SUCCESS_LOCK_SEAT)&&StringUtils.equals(lineOrder.getStatus().getPayStatus().toString(), XLOrderStatusConstant.SHOP_WAIT_PAY_BALANCE_MONEY)){
					//预订成功并且已支付预付款
					HgLogger.getInstance().info("lxs_dev",  "获取尾款金额");
					lineOrderInfoDTO.setShouldPayMoney(Double.toString(lineOrder.getBaseInfo().getSalePrice()-lineOrder.getBaseInfo().getBargainMoney()));
				}else{
					lineOrderInfoDTO.setShouldPayMoney("等待经销商确认");
				}
				//获取支付类型
				if(StringUtils.equals(lineOrder.getStatus().getPayStatus().toString(),XLOrderStatusConstant.SHOP_WAIT_PAY_BARGAIN_MONEY)||StringUtils.equals(lineOrder.getStatus().getPayStatus().toString(),XLOrderStatusConstant.SHOP_WAIT_PAY_BALANCE_MONEY)){
					//待支付
					lineOrderInfoDTO.setPayType("1");
				}else{
					lineOrderInfoDTO.setPayType("0");
				}*/
				//获取首页图
				lineOrderInfoDTO.setPictureUri("#");
				if(line.getLineImageList()!=null){
					for(LineImage lineImage:line.getLineImageList()){
						if(lineImage.getUrlMapsJSON()!=null){
							Map lineMap = JSON.parseObject(lineImage.getUrlMapsJSON(), Map.class);
							if(lineMap.get(SysProperties.getInstance().get("slfxImageAPPDetailType"))==null){
								if(lineMap.get("default")!=null){
									lineOrderInfoDTO.setPictureUri(SysProperties.getInstance().get("slfxImageUrl")+lineMap.get("default"));
								}
							}else{
								lineOrderInfoDTO.setPictureUri(SysProperties.getInstance().get("slfxImageUrl")+lineMap.get(SysProperties.getInstance().get("slfxImageAPPDetailType")));
							}
							break;
						}
					}
				}
			/*	if(line.getFolderList()!=null){
					for(LinePictureFolder linePictureFolder:line.getFolderList()){
						if(linePictureFolder.isMatter()){
							if(linePictureFolder.getPictureList()!=null){
								for(LinePicture linePicture:linePictureFolder.getPictureList()){
									if(linePicture!=null){
										lineOrderInfoDTO.setPictureUri(linePicture.getSite());
										break;
									}
								}
							}
						}
					}
				}*/
				//获取联系人姓名
				lineOrderInfoDTO.setLinkName(lineOrder.getLinkInfo().getLinkName());
				//获取联系人电话
				lineOrderInfoDTO.setLinkMobile(lineOrder.getLinkInfo().getLinkMobile());
				//获取联系人邮箱
				lineOrderInfoDTO.setLinkEmail(lineOrder.getLinkInfo().getEmail());
				LineOrderTravelerQO lineOrderTravelerQO = new LineOrderTravelerQO();
				lineOrderTravelerQO.setLineOrderId(lineOrder.getId());
				List<LineOrderTraveler> lineOrderTravelers= lineOrderTravelerService.queryList(lineOrderTravelerQO);
				List<ContactsDTO> contactsList = new ArrayList<ContactsDTO>();
				for(LineOrderTraveler lineOrderTraveler:lineOrderTravelers){
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
				lineOrderInfoDTO.setContactsList(contactsList);
				queryLineOrderResponse.setLineOrderInfo(lineOrderInfoDTO);
				queryLineOrderResponse.setResult(ApiResponse.RESULT_CODE_OK);
				queryLineOrderResponse.setMessage("查询成功");
				HgLogger.getInstance().info("lxs_dev", "【CreateLineOrderAction】"+"线路订单查询结束");
			}else{
				HgLogger.getInstance().info("lxs_dev", "【CreateLineOrderAction】"+"开始线路订单列表");
				//获取用户
				if(apilineOrderQO.getUserId()==null){
					throw new LineOrderException(LineOrderException.RESULT_NO_USER, "用户不存在");
				}
				lineOrderQO.setUserId(apilineOrderQO.getUserId());
				if(apilineOrderQO.getPayType()!=null&&StringUtils.isNotBlank(apilineOrderQO.getPayType())){
					switch(Integer.parseInt(apilineOrderQO.getPayType())){
					case 1:
						//待支付
//						lineOrderQO.setOrderStatus(XLOrderStatusConstant.SHOP_CREATE_SUCCESS_NOT_RESERVE);
						List<Integer> list = new ArrayList<Integer>();
						list.add(Integer.parseInt(XLOrderStatusConstant.SHOP_WAIT_PAY_BARGAIN_MONEY));
						list.add(Integer.parseInt(XLOrderStatusConstant.SHOP_WAIT_PAY_BALANCE_MONEY));
						lineOrderQO.setPayStatusList(list);
						break;
					case 2:
						//处理中
						lineOrderQO.setOrderStatus(XLOrderStatusConstant.SHOP_CREATE_SUCCESS_NOT_RESERVE);
//						List<Integer> list2 = new ArrayList<Integer>();
//						list2.add(Integer.parseInt(XLOrderStatusConstant.SHOP_RECEIVE_BARGAIN_MONEY_WAIT_PAY_FX));
//						list2.add(Integer.parseInt(XLOrderStatusConstant.SHOP_PAY_SUCCESS));
//						lineOrderQO.setPayStatusList(list2);
						lineOrderQO.setPayStatus(XLOrderStatusConstant.SHOP_RECEIVE_BARGAIN_MONEY_WAIT_PAY_FX);
						break;
					case 3:
						//已完成
						lineOrderQO.setOrderStatus(XLOrderStatusConstant.SHOP_RESERVE_SUCCESS);
//						lineOrderQO.setPayStatus(XLOrderStatusConstant.SHOP_PAY_SUCCESS);
						break;
					}
					
				}
				Pagination pagination= new Pagination();
				pagination.setPageNo(Integer.parseInt(apilineOrderQO.getPageNO()));
				pagination.setPageSize(Integer.parseInt(apilineOrderQO.getPageSize()));
				pagination.setCondition(lineOrderQO);
				pagination= lineOrderLocalService.queryPagination(pagination);
				HgLogger.getInstance().info("lxs_dev","【CreateLineOrderAction】"+ "线路订单列表查询成功");
				List<LineOrderListDTO> lineOrderList= new ArrayList<LineOrderListDTO>();
				queryLineOrderResponse.setResult(ApiResponse.RESULT_CODE_OK);
				queryLineOrderResponse.setMessage("查询成功");
				if (Integer.parseInt(apilineOrderQO.getPageNO()) <=pagination.getTotalPage()) {
					for(LineOrder lineOrder:(List<LineOrder>)pagination.getList()){
						LineOrderListDTO lineOrderListDTO = new LineOrderListDTO();
						//获取订单ID
						lineOrderListDTO.setOrderID(lineOrder.getId());
						//获取订单号
						lineOrderListDTO.setOrderNO(lineOrder.getBaseInfo().getDealerOrderNo());
						//获取订单创建时间
						lineOrderListDTO.setCreateDate(lineOrder.getBaseInfo().getCreateDate().toString());
						//获取订单名称（线路名称）
						Line line=JSON.parseObject(lineOrder.getLineSnapshot().getAllInfoLineJSON(),Line.class);
						lineOrderListDTO.setLineName(line.getBaseInfo().getName());
						lineOrderListDTO.setLineID(line.getId());
						lineOrderListDTO.setLineNO(line.getBaseInfo().getNumber());
						//出行时间
						lineOrderListDTO.setTravelDate(lineOrder.getBaseInfo().getTravelDate().toString());
						//获取成人旅客数量
						lineOrderListDTO.setAdultNO(lineOrder.getBaseInfo().getAdultNo().toString());
						//获取儿童旅客数量
						lineOrderListDTO.setChildNO(lineOrder.getBaseInfo().getChildNo().toString());
						//获取应支付金额
						
						LineOrderTravelerQO lineOrderTravelerQO = new LineOrderTravelerQO();
						lineOrderTravelerQO.setLineOrderId(lineOrder.getId());
						List<LineOrderTraveler> lineOrderTravelers= lineOrderTravelerService.queryList(lineOrderTravelerQO);
						List<ContactsDTO> contactsList = new ArrayList<ContactsDTO>();
						List<Integer> orderStatus =new ArrayList<Integer>();
						List<Integer> payStatus = new ArrayList<Integer>();
						for(LineOrderTraveler lineOrderTraveler:lineOrderTravelers){
//						ContactsDTO contactsDTO= new ContactsDTO();
//						contactsDTO.setContactsIdCardNO(lineOrderTraveler.getIdNo());
//						contactsDTO.setContactsName(lineOrderTraveler.getName());
//						contactsDTO.setMobile(lineOrderTraveler.getMobile());
							orderStatus.add(lineOrderTraveler.getLineOrderStatus().getOrderStatus());
							payStatus.add(lineOrderTraveler.getLineOrderStatus().getPayStatus());
//						contactsDTO.setOrderStatus(lineOrderTraveler.getLineOrderStatus().getOrderStatus().toString());
//						contactsDTO.setPayStatus(lineOrderTraveler.getLineOrderStatus().getPayStatus().toString());
//						if(StringUtils.equals(CONTACTS_TYPE_ADULT_VAL.toString(), lineOrderTraveler.getType().toString())){
//							contactsDTO.setType(CONTACTS_TYPE_ADULT);
//						}
//						if(StringUtils.equals(CONTACTS_TYPE_CHILD_VAL.toString(), lineOrderTraveler.getType().toString())){
//							contactsDTO.setType(CONTACTS_TYPE_CHILD);
//						}
//						contactsList.add(contactsDTO);
						}
//					lineOrderListDTO.setContactsList(contactsList);
						if(Collections.max(orderStatus).equals(Integer.valueOf(XLOrderStatusConstant.SHOP_ORDER_CANCEL))){
							if(orderStatus.size()==1){
								//只有一位游客
								lineOrderListDTO.setOrderStatus(Collections.max(orderStatus));
							}else{
								//获取第二大状态
								int status=Collections.min(orderStatus);
								for(int i=0;i<orderStatus.size();i++){
									if(orderStatus.get(i)<Collections.max(orderStatus)&&orderStatus.get(i)>status){
										status=orderStatus.get(i);
									}
								}
								lineOrderListDTO.setOrderStatus(status);
							}
						}else{
							lineOrderListDTO.setOrderStatus(Collections.max(orderStatus));
						}
						if(Collections.max(payStatus).equals(Integer.valueOf(XLOrderStatusConstant.SHOP_WAIT_REFUND))){
							if(payStatus.size()==1){
								//只有一位游客
								lineOrderListDTO.setPayStatus(Collections.max(payStatus));
							}else{
								//获取第二大状态
								int status=Collections.min(payStatus);
								for(int i=0;i<payStatus.size();i++){
									if(payStatus.get(i)<Collections.max(payStatus)&&payStatus.get(i)>status){
										status=payStatus.get(i);
									}
								}
								lineOrderListDTO.setPayStatus(status);
							}
						}else{
							lineOrderListDTO.setPayStatus(Collections.max(payStatus));
						}
						lineOrderListDTO.setBargainMoney(lineOrder.getBaseInfo().getBargainMoney().toString());
						lineOrderListDTO.setSalePrice(lineOrder.getBaseInfo().getSalePrice().toString());
						
						lineOrderListDTO.setInsurancePrice(lineOrder.getInsurancePrice());
						
						lineOrderListDTO.setIsBuyInsurance(lineOrder.getIsBuyInsurance());
						/*//支付截止日
					long lastPayTotalDaysBeforeStart=line.getPayInfo().getLastPayTotalDaysBeforeStart()*24*60*60*1000;
					//当前时间（付款请求时间）
					Date now= new Date();
					//出发时间
					Date travelDate=lineOrder.getBaseInfo().getTravelDate();
					if(StringUtils.equals(lineOrder.getStatus().getOrderStatus().toString(),XLOrderStatusConstant.SHOP_CREATE_SUCCESS_NOT_RESERVE)&&StringUtils.equals(lineOrder.getStatus().getPayStatus().toString(),XLOrderStatusConstant.SHOP_WAIT_PAY_BARGAIN_MONEY)){
						HgLogger.getInstance().info("lxs_dev",  "订单无任何支付记录");
						//预定成功还没进行任何付款行为
						//支付定金截止日
						long payTotalDaysBeforeStart=line.getPayInfo().getPayTotalDaysBeforeStart()*24*60*60*1000;
						if(now.getTime()+payTotalDaysBeforeStart<travelDate.getTime()){
							HgLogger.getInstance().info("lxs_dev",  "获取定金金额");
							//支付定金
							lineOrderListDTO.setShouldPayMoney(Double.toString(lineOrder.getBaseInfo().getBargainMoney()));
						}else{
							//支付全款
							HgLogger.getInstance().info("lxs_dev",  "获取全款金额");
							lineOrderListDTO.setShouldPayMoney(Double.toString(lineOrder.getBaseInfo().getSalePrice()));
						}
					}else if(StringUtils.equals(lineOrder.getStatus().getOrderStatus().toString(),XLOrderStatusConstant.SHOP_CREATE_SUCCESS_LOCK_SEAT)&&StringUtils.equals(lineOrder.getStatus().getPayStatus().toString(), XLOrderStatusConstant.SHOP_WAIT_PAY_BALANCE_MONEY)){
						//预订成功并且已支付预付款
						HgLogger.getInstance().info("lxs_dev",  "获取尾款金额");
						lineOrderListDTO.setShouldPayMoney(Double.toString(lineOrder.getBaseInfo().getSalePrice()-lineOrder.getBaseInfo().getBargainMoney()));
					}else{
						lineOrderListDTO.setShouldPayMoney("等待经销商确认");
					}
					//获取支付类型
					if(StringUtils.equals(lineOrder.getStatus().getPayStatus().toString(),XLOrderStatusConstant.SHOP_WAIT_PAY_BARGAIN_MONEY)||StringUtils.equals(lineOrder.getStatus().getPayStatus().toString(),XLOrderStatusConstant.SHOP_WAIT_PAY_BALANCE_MONEY)){
						//待支付
						lineOrderListDTO.setPayType("1");
					}else{
						lineOrderListDTO.setPayType("0");
					}*/
						//获取首页图
						lineOrderListDTO.setPictureUri("#");
						if(line.getLineImageList()!=null){
							for(LineImage lineImage:line.getLineImageList()){
								if(lineImage.getUrlMapsJSON()!=null){
									Map lineMap = JSON.parseObject(lineImage.getUrlMapsJSON(), Map.class);
									if(lineMap.get(SysProperties.getInstance().get("slfxImageAPPListType"))==null){
										if(lineMap.get("default")!=null){
											lineOrderListDTO.setPictureUri(SysProperties.getInstance().get("slfxImageUrl")+lineMap.get("default"));
										}
									}else{
										lineOrderListDTO.setPictureUri(SysProperties.getInstance().get("slfxImageUrl")+lineMap.get(SysProperties.getInstance().get("slfxImageAPPListType")));
									}
									break;
								}
							}
						}
						/**
						 * 需付全款提前天数
						 */
						lineOrderListDTO.setPayTotalDaysBeforeStart(line.getPayInfo().getPayTotalDaysBeforeStart());
						/**
						 * 最晚付款时间出发日期前
						 */
						lineOrderListDTO.setLastPayTotalDaysBeforeStart(line.getPayInfo().getLastPayTotalDaysBeforeStart());
						lineOrderList.add(lineOrderListDTO);
					}
					queryLineOrderResponse.setLineOrderList(lineOrderList);
					queryLineOrderResponse.setIsLastPage("n");
				} else if (Integer.parseInt(apilineOrderQO.getPageNO()) >=pagination.getTotalPage()) {
					queryLineOrderResponse.setLineOrderList(lineOrderList);
					queryLineOrderResponse.setIsLastPage("y");
				}
				HgLogger.getInstance().info("lxs_dev", "【CreateLineOrderAction】"+"线路订单列表查询结束");
			}
		}catch(LineOrderException e){
			HgLogger.getInstance().info("lxs_dev", "【CreateLineOrderAction】"+"线路订单查询错误"+e.getMessage());
			queryLineOrderResponse.setResult(e.getCode());
			queryLineOrderResponse.setMessage(e.getMessage());
		}
		HgLogger.getInstance().info("lxs_dev", "【CreateLineOrderAction】"+"线路订单查询结果"+JSON.toJSONString(queryLineOrderResponse));
		return JSON.toJSONString(queryLineOrderResponse);
	}

}

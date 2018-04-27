package hg.dzpw.app.service.api.dealer;

import hg.common.component.RedisLock;
import hg.common.page.Pagination;
import hg.dzpw.app.common.adapter.DealerApiAdapter;
import hg.dzpw.app.component.manager.DealerCacheManager;
import hg.dzpw.app.pojo.qo.TicketPolicyQo;
import hg.dzpw.app.service.local.TicketOrderLocalService;
import hg.dzpw.app.service.local.TicketPolicyLocalService;
import hg.dzpw.dealer.client.api.v1.request.CloseTicketOrderCommand;
import hg.dzpw.dealer.client.api.v1.request.CreateTicketOrderCommand;
import hg.dzpw.dealer.client.api.v1.request.PayToTicketOrderCommand;
import hg.dzpw.dealer.client.api.v1.request.TicketOrderQO;
import hg.dzpw.dealer.client.api.v1.response.CloseTicketOrderResponse;
import hg.dzpw.dealer.client.api.v1.response.CreateTicketOrderResponse;
import hg.dzpw.dealer.client.api.v1.response.PayToTicketOrderResponse;
import hg.dzpw.dealer.client.api.v1.response.TicketOrderResponse;
import hg.dzpw.dealer.client.common.ApiRequest;
import hg.dzpw.dealer.client.common.ApiResponse;
import hg.dzpw.dealer.client.common.api.DealerApiAction;
import hg.dzpw.dealer.client.common.api.DealerApiService;
import hg.dzpw.dealer.client.common.exception.DZPWDealerApiException;
import hg.dzpw.dealer.client.dto.order.TicketOrderDTO;
import hg.dzpw.domain.model.order.TicketOrder;
import hg.dzpw.domain.model.policy.TicketPolicy;
import hg.dzpw.pojo.exception.DZPWException;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @类功能说明：经销商接口-门票订单查询
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014年12月8日 下午5:11:49
 */
@Service
public class TicketOrderDealerApiService implements DealerApiService {
	
	@Autowired
	private TicketOrderLocalService ticketOrderService;
	@Autowired
	private DealerCacheManager dealerCacheManager;
	@Autowired
	private TicketPolicyLocalService ticketPolicyService;

	/**
	 * @方法功能说明：查询门票订单
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-27下午3:41:48
	 * @参数：@param request
	 * @参数：@return
	 * @return:TicketOrderResponse
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@DealerApiAction(DealerApiAction.QueryTicketOrder)
	public TicketOrderResponse queryTicketOrder(ApiRequest<TicketOrderQO> request) {

		TicketOrderResponse response = new TicketOrderResponse();
		TicketOrderQO QO = request.getBody();

		try {

			// 查询对应的经销商ID
			String dealerId = DealerApiAdapter.getDealerId(request.getHeader().getDealerKey());

			if (StringUtils.isBlank(dealerId)) {
				response.setMessage("无效经销商代码");
				response.setResult(TicketOrderResponse.RESULT_DEALER_KEY_ERROR);
				return response;
			}
			//添加根据订单号批量查询
			String orderIds=QO.getOrderId();
			List<TicketOrder> ticketOrders=new ArrayList<TicketOrder>();
//			未指定订单编号
			if (StringUtils.isBlank(orderIds)) {
				Pagination pagination = ticketOrderService.queryPagination(DealerApiAdapter.convertPaginationCondition(QO));
				response.setPageNo(pagination.getPageNo());
				response.setPageSize(pagination.getPageSize());
				if(pagination.getTotalCount()>0)
					ticketOrders.addAll((List<TicketOrder>)pagination.getList());
			}else{
//				指定订单编号
				String ids[]=orderIds.split(",");
				
				for (int i = 0; i < ids.length; i++) {
					QO.setOrderId(ids[i]);
	
					Pagination pagination = ticketOrderService.queryPagination(DealerApiAdapter.convertPaginationCondition(QO));
					response.setPageNo(pagination.getPageNo());
					response.setPageSize(pagination.getPageSize());
					if(pagination.getTotalCount()>0)
						ticketOrders.addAll((List<TicketOrder>)pagination.getList());
				}
			}
			List<TicketOrderDTO> list = new ArrayList<TicketOrderDTO>();
			for (TicketOrder ticketOrder : ticketOrders)
				list.add(DealerApiAdapter.ticketOrder.convertDTO(ticketOrder, QO));

			response.setTicketOrders(list);
			response.setMessage("查询成功");
			response.setResult(TicketOrderResponse.RESULT_SUCCESS);
			
			response.setTotalCount(ticketOrders.size());

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("系统异常");
			response.setResult(TicketOrderResponse.RESULT_ERROR);
		}

		return response;
	}

	
	/**
	 * @方法功能说明：创建门票订单
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-24下午6:07:57
	 * @return:CreateTicketOrderResponse
	 */
	@Transactional(rollbackFor = Exception.class)
	@DealerApiAction(DealerApiAction.CreateTicketOrder)
	public CreateTicketOrderResponse createTicketOrder(ApiRequest<CreateTicketOrderCommand> request) {

		CreateTicketOrderCommand command = request.getBody();
		CreateTicketOrderResponse response = new CreateTicketOrderResponse();
		
		// 重复检查
		RedisLock lock = new RedisLock(request.getHeader().getDealerKey() + "_" + command.getDealerOrderId());
		boolean hasLock = false;
		
		try {

			String dealerId = dealerCacheManager.getDealerId(request.getHeader().getDealerKey());

			if (StringUtils.isBlank(dealerId)) {
				return (CreateTicketOrderResponse)getResponse("无效经销商代码", 
																CreateTicketOrderResponse.RESULT_DEALER_KEY_ERROR, 
																	response);
			}

			// 校验参数
			if (checkCreateTicketOrderCommand(command)!=null) {
				return checkCreateTicketOrderCommand(command);
			}

			if (!(hasLock = lock.tryLock()))
				throw new DZPWException(DZPWException.MESSAGE_ONLY, String.format("此订单[%s]正在处理中", command.getDealerOrderId()));


			TicketOrderDTO ticketOrderDTO = ticketOrderService.createTicketOrder(command, dealerId);
			response.setTicketOrder(ticketOrderDTO);
			response.setResult(CreateTicketOrderResponse.RESULT_SUCCESS);
			response.setMessage("下单成功");
			
		} catch (DZPWDealerApiException e) {
			
			e.printStackTrace();
			response.setResult(e.getCode());
			response.setMessage(e.getMessage());
		} catch (DZPWException e) {
			
			e.printStackTrace();
			response.setResult(CreateTicketOrderResponse.RESULT_ERROR);
			response.setMessage(e.getMessage());
		} catch (Exception e) {
			
			e.printStackTrace();
			response.setResult(CreateTicketOrderResponse.RESULT_ERROR);
			response.setMessage("下单失败");
		} finally {
			if (hasLock)
				lock.unlock();
		}
		return response;
	}
	

	/**
	 * @方法功能说明：支付订单
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-23下午6:05:53
	 * @return:CloseTicketOrderResponse
	 */
	@Transactional(rollbackFor = Exception.class)
	@DealerApiAction(DealerApiAction.PayToTicketOrder)
	public PayToTicketOrderResponse payToTicketOrder(ApiRequest<PayToTicketOrderCommand> request) {

		PayToTicketOrderCommand command = request.getBody();
		PayToTicketOrderResponse response = new PayToTicketOrderResponse();
		
		// 重复检查
		RedisLock lock = new RedisLock(request.getHeader().getDealerKey() + "_" + command.getDealerOrderId());
		boolean hasLock = false;

		try {

			String dealerId = DealerApiAdapter.getDealerId(request.getHeader().getDealerKey());

			if (StringUtils.isBlank(dealerId)) {
				return (PayToTicketOrderResponse)getResponse("无效经销商代码", 
																PayToTicketOrderResponse.RESULT_DEALER_KEY_ERROR, 
																	response);
			}
			
			if (!(hasLock = lock.tryLock()))
				throw new DZPWException(DZPWException.MESSAGE_ONLY, String.format("此订单[%s]正在处理中", command.getDealerOrderId()));

			DZPWDealerApiException ep = ticketOrderService.payToTicketOrder(command, dealerId);
			
			if (ep==null){
				response.setResult(PayToTicketOrderResponse.RESULT_SUCCESS);
				response.setMessage("出票成功");
			}else{
				response.setResult(ep.getCode());
				response.setMessage(ep.getMessage());
			}
		} catch (DZPWException e) {
			e.printStackTrace();
			response.setResult(PayToTicketOrderResponse.RESULT_PAY_ERROR);
			response.setMessage(e.getMessage());
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setResult(PayToTicketOrderResponse.RESULT_PAY_ERROR);
			response.setMessage("付款失败");
			
		} finally {
			if (hasLock)
				lock.unlock();
		}
		
		return response;
	}

	
	/**
	 * @方法功能说明：关闭订单
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-23下午6:05:53
	 * @return:CloseTicketOrderResponse
	 */
	@Transactional(rollbackFor = Exception.class)
	@DealerApiAction(DealerApiAction.CloseTicketOrder)
	public CloseTicketOrderResponse closeTicketOrder(ApiRequest<CloseTicketOrderCommand> request) {

		CloseTicketOrderCommand command = request.getBody();
		CloseTicketOrderResponse response = new CloseTicketOrderResponse();
		
		// 重复检查
		RedisLock lock = new RedisLock(request.getHeader().getDealerKey() + "_" + command.getDealerOrderId());
		boolean hasLock = false;

		try {

			String dealerId = DealerApiAdapter.getDealerId(request.getHeader().getDealerKey());

			if (StringUtils.isBlank(dealerId)) {
				return (CloseTicketOrderResponse)getResponse("无效经销商代码", CloseTicketOrderResponse.RESULT_DEALER_KEY_ERROR, response);
			}
			
			if (!(hasLock = lock.tryLock()))
				throw new DZPWException(DZPWException.MESSAGE_ONLY, String.format("此订单[%s]正在处理中", command.getDealerOrderId()));

			ticketOrderService.closeTicketOrder(command, dealerId);
			
			response.setResult(CloseTicketOrderResponse.RESULT_SUCCESS);
			response.setMessage("关闭成功");
			
		} catch (DZPWDealerApiException e) {
			e.printStackTrace();
			response.setResult(e.getCode());
			response.setMessage(e.getMessage());
		} catch (DZPWException e) {
			e.printStackTrace();
			response.setResult(CloseTicketOrderResponse.RESULT_FAIL);
			response.setMessage(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			response.setResult(CloseTicketOrderResponse.RESULT_FAIL);
			response.setMessage("关闭失败");
		} finally {
			if (hasLock)
				lock.unlock();
		}
		
		return response;
	}

	
	/**
	 * @方法功能说明：校验创建订单必填参数
	 * @修改者名字：yangkang
	 * @修改时间：2015-1-27下午2:02:27
	 * @return:CreateTicketOrderResponse
	 */
	private CreateTicketOrderResponse checkCreateTicketOrderCommand(CreateTicketOrderCommand command) {
		
		CreateTicketOrderResponse resoponse = new CreateTicketOrderResponse();
		String msg = null;

		if (StringUtils.isBlank(command.getDealerOrderId()))
			msg = "参数错误[缺少必填参数dealerOrderId]";

		if (StringUtils.isBlank(command.getTicketPolicyId()))
			msg = "参数错误[缺少必填参数ticketPolicyId]";

		if (command.getTourists() == null || command.getTourists().size() == 0)
			msg = "参数错误[缺少必填参数tourists]";
		
		if (StringUtils.isBlank(command.getBookManMobile()))
			msg = "参数错误[缺少必填参数bookManMobile]";
		
		if (StringUtils.isBlank(command.getBookMan()))
			msg = "参数错误[缺少必填参数bookMan]";
		
		TicketPolicy tp = ticketPolicyService.queryUnique(new TicketPolicyQo(command.getTicketPolicyId()));
		if (tp==null){
			return (CreateTicketOrderResponse)getResponse("门票政策不存在或被删除", CreateTicketOrderResponse.RESULT_TICKET_POLICY_NOT_EXISTS,
					  resoponse);
		}
		
		// 联票时检查退收益账户
		if (tp.getType() == TicketPolicy.TICKET_POLICY_TYPE_GROUP 
				&& StringUtils.isBlank(command.getBookManAliPayAccount()))
			msg = "参数错误[缺少必填参数bookManAliPayAccount]";
		
		if (command.getPrice() == null || command.getPrice() <= 0)
			msg = "参数错误[price]";
		
		if (command.getBuyNum() == null || command.getBuyNum()<=0)
			msg = "参数错误[buyNum]";
		
		if (command.getBuyNum() != command.getTourists().size())
			msg = "参数错误[购买数量与游客人数不一致]";
		
		if (command.getBuyNum()>10)
			msg = "参数错误[购买数量不能大于10]";
		
		if(msg!=null){
			return (CreateTicketOrderResponse)getResponse(msg, CreateTicketOrderResponse.RESULT_PARAM_ERROR,
					  									  resoponse);
		}

		return null;
	}

	
	@SuppressWarnings("unused")
	private CreateTicketOrderResponse checkOrderTotalPrice(String dealerId,
			CreateTicketOrderCommand command, CreateTicketOrderResponse response) {

		TicketPolicy ticketPolicy = this.ticketPolicyService
				.queryUnique(new TicketPolicyQo(command.getTicketPolicyId()));

		if (ticketPolicy == null) {
			response.setMessage("门票已被删除或被禁用");
			response.setResult(CreateTicketOrderResponse.RESULT_TICKET_POLICY_NOT_EXISTS);
			return response;
		}
		
		if (ticketPolicy.getSellInfo().getRemainingQty() < command.getTourists().size()) {
			response.setMessage("可售门票数不足");
			response.setResult(CreateTicketOrderResponse.RESULT_REMAINING_QTY_LOW);
			return response;
		}

		return response;
	}
	
	
	/**
	 * @方法功能说明：设置响应实体的消息内容、消息代码
	 * @修改者名字：yangkang
	 * @修改时间：2016-1-27下午2:34:42
	 * @return:CreateTicketOrderResponse
	 */
	private ApiResponse getResponse(String msg, String code, ApiResponse response){
		response.setMessage(msg);
		response.setResult(code);
		return response;
	}

}
package hsl.app.service.spi.line;

import com.alibaba.fastjson.JSON;
import hg.common.util.BeanMapperUtils;
import hg.log.util.HgLogger;
import hsl.app.component.base.BaseSpiServiceImpl;
import hsl.app.service.local.line.LineOrderLocalService;
import hsl.app.service.local.lineSalesPlan.LineSalesPlanLocalService;
import hsl.app.service.local.lineSalesPlan.order.LSPOrderLocalService;
import hsl.domain.model.lineSalesPlan.order.LSPOrder;
import hsl.domain.model.lineSalesPlan.order.LSPOrderBaseInfo;
import hsl.domain.model.lineSalesPlan.order.LSPOrderTraveler;
import hsl.domain.model.xl.order.LineOrder;
import hsl.domain.model.xl.order.LineOrderTraveler;
import hsl.pojo.command.line.CancelLineOrderCommand;
import hsl.pojo.command.line.HslCreateLineOrderCommand;
import hsl.pojo.command.line.UpdateLineOrderPayInfoCommand;
import hsl.pojo.command.line.UpdateLineOrderStatusCommand;
import hsl.pojo.command.lineSalesPlan.UpdateLSPSalesNumCommand;
import hsl.pojo.dto.line.order.LineOrderDTO;
import hsl.pojo.dto.line.order.LineOrderTravelerDTO;
import hsl.pojo.dto.line.order.XLOrderStatusConstant;
import hsl.pojo.dto.lineSalesPlan.LineSalesPlanConstant;
import hsl.pojo.exception.LSPException;
import hsl.pojo.exception.LineException;
import hsl.pojo.qo.line.HslLineOrderQO;
import hsl.pojo.qo.lineSalesPlan.order.LSPOrderQO;
import hsl.spi.inter.line.HslLineOrderService;
import hsl.spi.inter.lineSalesPlan.LineSalesPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import plfx.api.client.base.slfx.SlfxApiClient;
import plfx.xl.pojo.command.line.XLUpdateOrderSalePriceMessageApiCommand;
import plfx.xl.pojo.command.line.XLUpdateOrderStatusMessageApiCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @类功能说明：线路订单spi服务类
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhuxy
 * @创建时间：2015年2月26日
 */
@Service
public class HslLineOrderServiceImpl extends BaseSpiServiceImpl<LineOrderDTO, HslLineOrderQO, LineOrderLocalService> implements HslLineOrderService{
	
	@Autowired
	private LineOrderLocalService lineOrderLocalService;
	@Autowired
	private LSPOrderLocalService lspOrderLocalService;
	@Autowired
	private LineSalesPlanLocalService lineSalesPlanLocalService;
	/**
	 * 商旅分销客户端
	 */
	@Autowired
	private SlfxApiClient slfxApiClient;
	
	@Override
	public LineOrderDTO createLineOrder(HslCreateLineOrderCommand command) throws LineException {
		return lineOrderLocalService.createLineOrder(command);
	}

	@Override
	public Boolean cancelLineOrder(CancelLineOrderCommand command) {
		
		HgLogger.getInstance().info("renfeng", "HslLineOrderServiceImpl->cancelLineOrder直销后台取消线路订单："+JSON.toJSONString(command));
		
		if(command==null||StringUtils.isEmpty(command.getLineOrderID())||command.getTravelerIDs()==null){
			return false;
		}
		
		HgLogger.getInstance().info("renfeng", "HslLineOrderServiceImpl->cancelLineOrder :开始修改游客订单状态和支付状态。。。。。。。。。");
		try{
			
			HslLineOrderQO qo=new HslLineOrderQO();
			qo.setDealerOrderNo(command.getLineOrderID());
			LineOrder lineOrder=this.lineOrderLocalService.queryUnique(qo);
			//根据分销传过来的订单状态，重新设置本地订单状态
			String[] travelerIDs =command.getTravelerIDs();
			if(travelerIDs!=null){
				List<LineOrderTravelerDTO> travelerList=new ArrayList<LineOrderTravelerDTO>();
				
				//修改订单游客状态为"取消订单"
			
				int payStatus=Integer.parseInt(XLOrderStatusConstant.SHOP_WAIT_REFUND);
				for(String travelID:travelerIDs){
					
					for(LineOrderTraveler travle:lineOrder.getTravelers()){
						if(travle.getId().equals(travelID)){
							travle.getLineOrderStatus().changeOrderStatus(Integer.parseInt(XLOrderStatusConstant.SHOP_ORDER_CANCEL),travle.getLineOrderStatus().getPayStatus());
							travelerList.add(BeanMapperUtils.getMapper().map(travle, LineOrderTravelerDTO.class));
							
							//支付状态这里暂传游客原来状态。在localservice里分别更改游客状态时处理（支付状态若为 "待支付订金"，支付状态不变；否则，支付状态修改为"等待退款"）
							payStatus=travle.getLineOrderStatus().getPayStatus();
							
						}
					}
					
				}
								
				UpdateLineOrderStatusCommand updateCmd=new UpdateLineOrderStatusCommand();
				updateCmd.setDealerOrderCode(command.getLineOrderID());
				updateCmd.setTravelerList(travelerList);
				updateCmd.setOrderStatus(Integer.parseInt(XLOrderStatusConstant.SHOP_ORDER_CANCEL));
				updateCmd.setPayStatus(payStatus);
				
				HgLogger.getInstance().info("renfeng", "HslLineOrderServiceImpl->cancelLineOrder 修改游客订单状态和支付状态："+JSON.toJSONString(updateCmd));
				return this.updateLineOrderStatus(updateCmd);
				
				
			}else{
				return false;
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	protected LineOrderLocalService getService() {
		return this.lineOrderLocalService;
	}

	@Override
	protected Class<LineOrderDTO> getDTOClass() {
		return LineOrderDTO.class;
	}

	@Override
	public boolean updateLineOrderStatus (
			UpdateLineOrderStatusCommand command) throws Exception{
		return this.lineOrderLocalService.updateOrderStatus(command);
	}

	@Override
	public boolean updateLineOrderStatus(XLUpdateOrderStatusMessageApiCommand command)
			throws LineException {
		if(command==null||StringUtils.isEmpty(command.getLineOrderID())){
			return false;
		}
		HgLogger.getInstance().info("renfeng", "HslLineOrderServiceImpl->updateOrderStatus线路订单状态更新："+JSON.toJSONString(command));
		HslLineOrderQO qo=new HslLineOrderQO();
		qo.setDealerOrderNo(command.getLineOrderID());
		LineOrder lineOrder=this.lineOrderLocalService.queryUnique(qo);
		if(lineOrder!=null){
			//根据分销传过来的订单状态，重新设置本地订单状态
			Set< plfx.xl.pojo.dto.order.LineOrderTravelerDTO> travelers =command.getTravelers();
			if(travelers==null||travelers.size()<1){
				HgLogger.getInstance().info("renfeng", "HslLineOrderServiceImpl->updateOrderStatus线路订单状态更新：command 中游客信息为空"+JSON.toJSONString(travelers));
			}else{
				for( plfx.xl.pojo.dto.order.LineOrderTravelerDTO travelDto:travelers){
					if(lineOrder!=null&&lineOrder.getTravelers().size()>0){
						for(LineOrderTraveler travle:lineOrder.getTravelers()){
							if(travle.getId().equals(travelDto.getId())){
								//修改订单状态
								travle.getLineOrderStatus().changeOrderStatus(travelDto.getXlOrderStatus().getStatus(),travelDto.getXlOrderStatus().getPayStatus());

							}
						}
					}
				}
			}
			this.lineOrderLocalService.update(lineOrder);
			HgLogger.getInstance().info("renfeng", "HslLineOrderServiceImpl->updateOrderStatus线路订单状态更新：直销订单状态更新完成" + JSON.toJSONString(command));
		}else{
			/*****-------查询活动订单，是否是通知的活动订单--Start-----*****/
			LSPOrderQO lspOrderQO=new LSPOrderQO();
			lspOrderQO.setDealerOrderNo(command.getLineOrderID());
			LSPOrder lspOrder=lspOrderLocalService.queryUnique(lspOrderQO);
			if(lspOrder!=null){
				Set< plfx.xl.pojo.dto.order.LineOrderTravelerDTO> travelers =command.getTravelers();
				if(travelers==null||travelers.size()<1){
					HgLogger.getInstance().info("renfeng", "HslLineOrderServiceImpl->updateOrderStatus线路订单状态更新：command 中游客信息为空"+JSON.toJSONString(travelers));
				}else{
					Integer status=0;
					for( plfx.xl.pojo.dto.order.LineOrderTravelerDTO travelDto:travelers){
						if(lspOrder!=null&&lspOrder.getTravelers().size()>0){
							for(LSPOrderTraveler travler:lspOrder.getTravelers()){
								if(travler.getId().equals(travelDto.getId())){
									//修改订单状态
									travler.getLspOrderStatus().changeOrderStatus(travelDto.getXlOrderStatus().getStatus(),travelDto.getXlOrderStatus().getPayStatus());
									lspOrder.getOrderStatus().setOrderStatus(travelDto.getXlOrderStatus().getStatus());
									lspOrder.getOrderStatus().setPayStatus(travelDto.getXlOrderStatus().getPayStatus());
									status=travelDto.getXlOrderStatus().getStatus();
								}
							}
						}
					}
					/**当状态是取消状态以及订单类型是 秒杀活动时请求分销取消订单*/
					if(LSPOrderBaseInfo.LSP_ORDER_TYPE_SECKILL.equals(lspOrder.getOrderBaseInfo().getOrderType())
							&& LineSalesPlanConstant.LSP_ORDER_STATUS_CANCEL.equals(status)){
						/**
						 * 取消订单后，更新关联的销售方案的销售数量以及相应的活动状态,只修改秒杀订单
						 */
						HgLogger.getInstance().info("chenxy","updateLSPOrderStatus-->修改秒杀活动"+ JSON.toJSONString(lspOrder.getOrderBaseInfo().getDealerOrderNo()));
						Integer num=travelers.size();
						Integer snum=lspOrder.getLineSalesPlan().getLineSalesPlanSalesInfo().getSoldNum()-num;
						UpdateLSPSalesNumCommand updateLSPSalesNumCommand=new UpdateLSPSalesNumCommand();
						updateLSPSalesNumCommand.setPlanId(lspOrder.getLineSalesPlan().getId());
						updateLSPSalesNumCommand.setSalesNum(snum);
						try {
							lineSalesPlanLocalService.updateLSPSalesNum(updateLSPSalesNumCommand);
						} catch (LSPException e) {
							e.printStackTrace();
						}
					}
					lspOrderLocalService.update(lspOrder);
				}
			}
			/*****-------查询活动订单，是否是通知的活动订单--End  -----*****/
		}
		return true;
	}

	@Override
	public boolean updateLineOrderSalePrice(XLUpdateOrderSalePriceMessageApiCommand command) {
		if(command==null||StringUtils.isEmpty(command.getLineOrderID())){
			return false;
		}
		HgLogger.getInstance().info("yuqz", "HslLineOrderServiceImpl->updateLineOrderSalePrice线路订单金额更新："+JSON.toJSONString(command));
		HslLineOrderQO qo=new HslLineOrderQO();
		qo.setDealerOrderNo(command.getLineOrderID());
		LineOrder lineOrder=this.lineOrderLocalService.queryUnique(qo);
		
		//根据分销传过来的订单状态，重新设置本地订单状态
		Set< plfx.xl.pojo.dto.order.LineOrderTravelerDTO> travelers =command.getTravelers();
		if(travelers==null||travelers.size()<1){
			HgLogger.getInstance().info("yuqz", "HslLineOrderServiceImpl->updateLineOrderSalePrice线路订单金额更新：command 中游客信息为空"+JSON.toJSONString(travelers));
		}else{
			for(plfx.xl.pojo.dto.order.LineOrderTravelerDTO travelDto:travelers){
				if(lineOrder!=null&&lineOrder.getTravelers().size()>0){
					for(LineOrderTraveler travle:lineOrder.getTravelers()){
						if(travle.getId().equals(travelDto.getId())){
							//修改游玩人订单金额
							travle.setSingleSalePrice(travelDto.getSingleSalePrice());
							travle.setRemark(travelDto.getRemark());
						}
					}
				}
			}
		}
		lineOrder.getBaseInfo().setSalePrice(command.getSalePrice());
		this.lineOrderLocalService.update(lineOrder);
		
		HgLogger.getInstance().info("yuqz", "HslLineOrderServiceImpl->updateLineOrderSalePrice线路订单金额更新：直销订单金额更新完成"+JSON.toJSONString(command));
		return true;
	}

	@Override
	public void updateLineOrderPayInfo(UpdateLineOrderPayInfoCommand command) {
		lineOrderLocalService.updateLineOrderPayInfo(command);
	}
	@Override
	public void updateLineOrderPayCash(UpdateLineOrderPayInfoCommand command){
		lineOrderLocalService.updateLineOrderPayCash(command);
	}
}

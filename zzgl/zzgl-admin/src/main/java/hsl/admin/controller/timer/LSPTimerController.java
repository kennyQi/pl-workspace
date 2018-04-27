package hsl.admin.controller.timer;
import hg.log.util.HgLogger;
import hsl.app.service.local.lineSalesPlan.order.LSPOrderLocalService;
import hsl.domain.model.lineSalesPlan.order.LSPOrderBaseInfo;
import hsl.pojo.command.lineSalesPlan.UpdateLineSalesPlanStatusCommand;
import hsl.pojo.command.lineSalesPlan.order.UpdateLSPOrderStatusCommand;
import hsl.pojo.dto.lineSalesPlan.LineSalesPlanConstant;
import hsl.pojo.dto.lineSalesPlan.LineSalesPlanDTO;
import hsl.pojo.dto.lineSalesPlan.order.LSPOrderDTO;
import hsl.pojo.qo.lineSalesPlan.LineSalesPlanQO;
import hsl.pojo.qo.lineSalesPlan.order.LSPOrderQO;
import hsl.spi.inter.lineSalesPlan.LineSalesPlanService;
import hsl.spi.inter.lineSalesPlan.order.LSPOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
/**
* @类功能说明：线路销售方案定时器
* @类修改者：
* @公司名称： 浙江票量云科技有限公司
* @部门： 技术部
* @作者： chenxy
* @创建时间：  2015-12-03 15:26:21
* @版本： V1.0
*/
@Controller
@RequestMapping("/lspTimer")
public class LSPTimerController {
	@Autowired
	private LSPOrderService lspOrderService;
	@Autowired
	private LineSalesPlanService lineSalesPlanService;
	@Autowired
	private LSPOrderLocalService lspOrderLocalService;
	/**
	 * 检查方案订单
	 * @return String
	 */
	@RequestMapping("/checkLSPOrder")
	@ResponseBody
	public String checkLSPOrder(){
		try{
			/**--------------检查订单是否过期.则取消订单--Start-------------*/
			LSPOrderQO lspOrderQO=new LSPOrderQO();
			lspOrderQO.setFetchTraveler(true);
			lspOrderQO.setOrderStatusArray(new Integer[]{LineSalesPlanConstant.LSP_ORDER_STATUS_SUCCESS_NOT_RESERVE, LineSalesPlanConstant.LSP_ORDER_STATUS_SUCCESS_GROUP_ING});
			lspOrderQO.setPayStatus(LineSalesPlanConstant.LSP_PAY_STATUS_NOPAY);
			List<LSPOrderDTO> lspOrderDTOs=lspOrderService.queryList(lspOrderQO);
			for(LSPOrderDTO lspOrderDTO:lspOrderDTOs){
				HgLogger.getInstance().info("chenxy","测试定时检查订单是否超时"+lspOrderDTOs.size());
				Date createTime= lspOrderDTO.getOrderBaseInfo().getCreateDate();
				Date now=new Date();
				long minute = (now.getTime() - createTime.getTime()) / (1000 * 60);
				if(lspOrderDTO.getOrderStatus().getPayStatus().equals(LineSalesPlanConstant.LSP_PAY_STATUS_NOPAY)&&minute>20){
					//修改订单状态
					UpdateLSPOrderStatusCommand updateLSPOrderStatusCommand=new UpdateLSPOrderStatusCommand();
					updateLSPOrderStatusCommand.setOrderId(lspOrderDTO.getId());
					updateLSPOrderStatusCommand.setStatus(LineSalesPlanConstant.LSP_ORDER_STATUS_CANCEL);
					lspOrderService.updateLSPOrderStatus(updateLSPOrderStatusCommand);
				}
			}
			/**--------------检查订单是否过期.则取消订单--End-------------*/
		}catch (Exception e){
			HgLogger.getInstance().error("chenxy", "LSPTimer-->checkLSPOrder-->异常信息" + HgLogger.getStackTrace(e));
			e.printStackTrace();
			return "error";
		}
		return "success";
	}

	/**
	 * 检查销售方案的状态
	 * @return String
	 */
	@RequestMapping("/checkLSPStatus")
	@ResponseBody
	public String checkLineSalesPlan(){
		/**
		 * 查询未开始的活动是否到开始时间以及查询正在进行的活动是否到过期时间。
		 */
		try{
			LineSalesPlanQO lineSalesPlanQO=new LineSalesPlanQO();
			lineSalesPlanQO.setStatusArray(new Integer[]{LineSalesPlanConstant.LSP_STATUS_NOBEGIN, LineSalesPlanConstant.LSP_STATUS_SALESING});
			List<LineSalesPlanDTO> lineSalesPlanDTOs=lineSalesPlanService.queryList(lineSalesPlanQO);
			HgLogger.getInstance().info("chenxy","LSPTimer-->checkLSPStatus-->检查复合未开始和进行中的活动数量"+lineSalesPlanDTOs.size());
			for (LineSalesPlanDTO lineSalesPlanDTO:lineSalesPlanDTOs){
				if(lineSalesPlanDTO.getLineSalesPlanStatus().getStatus().equals(LineSalesPlanConstant.LSP_STATUS_NOBEGIN)){
					Date nowDate=new Date();
					if(nowDate.after(lineSalesPlanDTO.getLineSalesPlanSalesInfo().getBeginDate())){
						UpdateLineSalesPlanStatusCommand updateLineSalesPlanStatusCommand=new UpdateLineSalesPlanStatusCommand();
						updateLineSalesPlanStatusCommand.setPlanId(lineSalesPlanDTO.getId());
						updateLineSalesPlanStatusCommand.setStatus(LineSalesPlanConstant.LSP_STATUS_SALESING);
						lineSalesPlanService.updateLineSalesPlanStatus(updateLineSalesPlanStatusCommand);
					}
				}else if(lineSalesPlanDTO.getLineSalesPlanStatus().getStatus().equals(LineSalesPlanConstant.LSP_STATUS_SALESING)){
					Date nowDate=new Date();
					if(nowDate.after(lineSalesPlanDTO.getLineSalesPlanSalesInfo().getEndDate())){
						/**---判断是否到过期时间，是否是团购活动*/
						if(lineSalesPlanDTO.getBaseInfo().getPlanType().equals(LSPOrderBaseInfo.LSP_ORDER_TYPE_GROUPBUY)){
							Integer pnum=lineSalesPlanDTO.getLineSalesPlanSalesInfo().getProvideNum();
							Integer snum=lineSalesPlanDTO.getLineSalesPlanSalesInfo().getSoldNum();
							if(pnum-snum>0){
								UpdateLineSalesPlanStatusCommand updateLineSalesPlanStatusCommand=new UpdateLineSalesPlanStatusCommand();
								updateLineSalesPlanStatusCommand.setPlanId(lineSalesPlanDTO.getId());
								updateLineSalesPlanStatusCommand.setStatus(LineSalesPlanConstant.LSP_STATUS_END_GROUP_FAIL);
								lineSalesPlanService.updateLineSalesPlanStatus(updateLineSalesPlanStatusCommand);
								/**如果团购活动到期，则修改关联订单为下单成功组团失败--->等待退款*/
								lspOrderLocalService.modifyLSPOrderForGroupFail(lineSalesPlanDTO.getId());
							}else{
								UpdateLineSalesPlanStatusCommand updateLineSalesPlanStatusCommand=new UpdateLineSalesPlanStatusCommand();
								updateLineSalesPlanStatusCommand.setPlanId(lineSalesPlanDTO.getId());
								updateLineSalesPlanStatusCommand.setStatus(LineSalesPlanConstant.LSP_STATUS_END_GROUP_SUCC);
								lineSalesPlanService.updateLineSalesPlanStatus(updateLineSalesPlanStatusCommand);
							}
						}else{
							UpdateLineSalesPlanStatusCommand updateLineSalesPlanStatusCommand=new UpdateLineSalesPlanStatusCommand();
							updateLineSalesPlanStatusCommand.setPlanId(lineSalesPlanDTO.getId());
							updateLineSalesPlanStatusCommand.setStatus(LineSalesPlanConstant.LSP_STATUS_END);
							lineSalesPlanService.updateLineSalesPlanStatus(updateLineSalesPlanStatusCommand);
						}
					}
				}
			}
		}catch (Exception e){
			HgLogger.getInstance().info("chenxy","LSPTimer-->checkLSPStatus-->异常信息"+HgLogger.getStackTrace(e));
			e.printStackTrace();
			return "error";
		}
		return "success";
	}
}

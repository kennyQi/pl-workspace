package slfx.yg.open.inter;

import java.util.HashMap;
import java.util.List;

import slfx.jp.command.client.ABEDeletePnrCommand;
import slfx.jp.command.client.ABEOrderFlightCommand;
import slfx.jp.command.client.YGApplyRefundCommand;
import slfx.jp.command.client.YGAskOrderTicketCommand;
import slfx.jp.command.client.YGOrderCommand;
import slfx.jp.pojo.dto.flight.FlightStopInfoDTO;
import slfx.jp.pojo.dto.flight.SlfxFlightPolicyDTO;
import slfx.jp.pojo.dto.supplier.abe.ABEDeletePnrDTO;
import slfx.jp.pojo.dto.supplier.abe.ABEOrderFlightDTO;
import slfx.jp.pojo.dto.supplier.abe.ABEPatFlightDTO;
import slfx.jp.pojo.dto.supplier.abe.ABEXmlRtPnrDTO;
import slfx.jp.pojo.dto.supplier.yg.QueryWebFlightsDTO;
import slfx.jp.pojo.dto.supplier.yg.YGApplyRefundDTO;
import slfx.jp.pojo.dto.supplier.yg.YGAskOrderTicketDTO;
import slfx.jp.pojo.dto.supplier.yg.YGFlightOrderDTO;
import slfx.jp.pojo.dto.supplier.yg.YGQueryOrderDTO;
import slfx.jp.pojo.dto.supplier.yg.YGQueryOrderStatusDTO;
import slfx.jp.pojo.dto.supplier.yg.YGRefundActionTypesDTO;
import slfx.jp.qo.client.PatFlightQO;
import slfx.jp.qo.client.PolicyByPnrQo;
import slfx.jp.qo.client.QueryWebFlightsQO;

/**
 * 
 * @类功能说明：易购航班查询SERVICE
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午5:17:29
 * @版本：V1.0
 *
 */
public interface YGFlightService {
	
	/**
	 * 查询航班--运价云
	 * 数据格式JSON
	 * @param queryWebFlights
	 */
	public QueryWebFlightsDTO queryFlights(QueryWebFlightsQO qo);
	
	/**
	 * @方法功能说明：查询航班--运价云  数据格式JSON   V2格式返回结果
	 *   H节点解析有差异
	 * @修改者名字：yangkang
	 * @修改时间：2014-9-2下午2:10:13
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:QueryWebFlightsDTO
	 * @throws
	 */
	public QueryWebFlightsDTO queryFlightsV2(QueryWebFlightsQO qo);

	/**
	 * 查询比价
	 * @param qo  比价 qo
	 * @return 返回符合的政策列表
	 */
	public List<SlfxFlightPolicyDTO> queryPolicyByPNR(PolicyByPnrQo qo);
	
	
	/**
	 * 查询航班经停信息--运价云
	 * 数据格式JSON
	 * @param flightNo 航班号   格式：CA8904
	 * @param DateStr 航班日期 格式：2014-04-11
	 */
	public List<FlightStopInfoDTO> queryFlightStop(String flightNo, String DateStr);
	
	
	/**
	 * PAT报价（验价接口）--webABE 单个航班舱位验价
	 * 此方法一次校验全部乘客类型报价 即： ADT成人   CHD儿童   INF婴儿
	 * 数据格式XML
	 */
	public HashMap<String, ABEPatFlightDTO> patByFlights(PatFlightQO qo);
	
	/**
	 * ABE下单接口
	 * @param command
	 * @return
	 */
	public ABEOrderFlightDTO abeOrderFlight(ABEOrderFlightCommand command);
	
	/**
	 * 采购云出票接口
	 * @param command
	 * @return
	 */
	public YGAskOrderTicketDTO askOrderTicket(YGAskOrderTicketCommand command);
	
	/**
	 * 订单状态查询接口
	 * @param orderNo
	 * @return
	 */
	public YGQueryOrderStatusDTO queryOrderStatus(final String orderNo);
	
	
	/**
	 * 采购云获取退废票种类接口
	 * @param platCode 平台代码
	 */
	public YGRefundActionTypesDTO getRefundActionType(String platCode);
	
	/**
	 * 易购下单----采购云
	 * @param yGOrderCommand
	 * @return YGFlightOrderDTO
	 */
	public YGFlightOrderDTO orderByPnr(YGOrderCommand yGOrderCommand);
	
	
	/**
	 * 易购退废票接口
	 * @param command
	 * @return
	 */
	public YGApplyRefundDTO applyRefund(YGApplyRefundCommand command);
	
	
	/**
	 * @方法功能说明：删除订座记录接口
	 * @修改者名字：yangkang
	 * @修改时间：2014-8-20上午10:28:27
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:ABEDeletePnrDTO
	 * @throws
	 */
	public ABEDeletePnrDTO deletePnr(ABEDeletePnrCommand command);
	
	
	/**
	 * @方法功能说明：根据PNR获取PNRTEXT
	 * @修改者名字：yangkang
	 * @修改时间：2014-8-22下午1:53:40
	 * @参数：@param pnr
	 * @参数：@return
	 * @return:String
	 */
	public ABEXmlRtPnrDTO xmlRtPnr(String pnr);
	
	
	/**
	 * @方法功能说明： QueryOrder_1_0订单查询接口
	 * @修改者名字：yangkang
	 * @修改时间：2014-8-26下午2:51:20
	 * @修改内容：
	 * @参数：@param orderNo 订单号
	 * @参数：@return
	 * @return:YGQueryOrderDTO
	 * @throws
	 */
	public YGQueryOrderDTO queryOrder(String orderNo);
}

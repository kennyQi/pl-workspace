package hg.dzpw.app.service.local;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.common.util.UUIDGenerator;
import hg.dzpw.app.common.SystemConfig;
import hg.dzpw.app.common.util.ExportExcelUtils;
import hg.dzpw.app.dao.FinanceStatisticsDao;
import hg.dzpw.app.pojo.qo.GroupTicketQo;
import hg.dzpw.app.pojo.qo.SingleTicketQo;
import hg.dzpw.domain.model.ticket.GroupTicketStatus;
import hg.dzpw.domain.model.ticket.SingleTicket;
import hg.dzpw.domain.model.ticket.SingleTicketStatus;
import hg.dzpw.pojo.qo.FinanceDetailQo;
import hg.dzpw.pojo.vo.FinanceVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 经销商端财务明细服务
 * @author CaiHuan
 *
 * 日期:2015-12-24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FinanceLocalService {
	
	@Autowired
	private FinanceStatisticsDao financeStatisticsDao ;
	@Autowired
	private SingleTicketLocalService singleTicketLocalService;

	/**
	 * 经销商端财务明细
	 * @author CaiHuan
	 * @param qo
	 * @param selectAll
	 * @return
	 */
	public Pagination queryFinanceDetail(FinanceDetailQo qo, boolean selectAll)
	{
		return financeStatisticsDao.queryFinanceDetail(qo,selectAll);
	}
	
	@SuppressWarnings("unchecked")
	public File exportFinanceToExcel(FinanceDetailQo qo) throws Exception
	{
		//订单状态
		Map<Integer,String> map = new HashMap<Integer, String>();
		map.put(GroupTicketStatus.GROUP_TICKET_CURRENT_PAY_WAIT,"等待支付");
		map.put(GroupTicketStatus.GROUP_TICKET_CURRENT_PAY_SUCC,"支付成功");
		map.put(GroupTicketStatus.GROUP_TICKET_CURRENT_OUT_SUCC,"出票成功");
		map.put(GroupTicketStatus.GROUP_TICKET_CURRENT_DEAL_SUCC,"交易成功");
		map.put(GroupTicketStatus.GROUP_TICKET_CURRENT_DEAL_CLOSE,"交易关闭");
		
		Pagination pagination = financeStatisticsDao.queryFinanceDetail(qo, true);
		
		
		SingleTicketQo singleTicketQo = new SingleTicketQo();
		GroupTicketQo gqo = new GroupTicketQo();
		singleTicketQo.setGroupTicketQo(gqo);
		singleTicketQo.setStatus(7);
		for(FinanceVo v:(List<FinanceVo>)pagination.getList())
		{
			Double total = 0d;
			//设置手续费
			//设置退款金额
			gqo.setId(v.getGroupTicketId());
			List<SingleTicket> singleTicketList = singleTicketLocalService.queryList(singleTicketQo);
			for(SingleTicket s:singleTicketList)
			{
				total = total+(s.getSettlementInfo().getDealerPrice()==null?0:s.getSettlementInfo().getDealerPrice());
			}
			v.setRefundAmmount(total);
		}
		
		List<FinanceVo> list = (List<FinanceVo>) pagination.getList();
		String out = SystemConfig.tempFilePath + UUIDGenerator.getUUID() + ".xls";
		String[] title = new String[] { "订单编号", "票务编号", "产品编码", "产品名称", "所属单位","订票人","下单时间",
		"出票时间","结算价(元)","手续费(元)","支付流水号","退款账号","退款批次号","退款金额(元)","退款时间","订单状态"};
		String[][] body = new String[list.size()][title.length];
		for (int i = 0; i < list.size(); i++) {
			FinanceVo vo = list.get(i);
			body[i][0] = vo.getOrderId();
			body[i][1] = vo.getTicketNo();
			body[i][2] = vo.getPolicyNo();
			body[i][3] = vo.getPolicyName();
			body[i][4] = vo.getScenicSpotNameStr();
			body[i][5] = vo.getLinkMan();
			body[i][6] = DateUtil.formatDateTime(vo.getCreateDate(),"yyyy-MM-dd");
			body[i][7] = DateUtil.formatDateTime(vo.getTicketDate(),"yyyy-MM-dd");
			body[i][8] = String.valueOf(vo.getDealerAmount()==null?0:vo.getDealerAmount());
			body[i][9] = String.valueOf(vo.getDealerSettlementFee()==null?0:vo.getDealerSettlementFee());
			body[i][10] = vo.getPayTradeNo();
			body[i][11] = vo.getDealerAccountNumber();
			body[i][12] = vo.getRefundBatchNo();
			body[i][13] = String.valueOf(vo.getRefundAmmount()==null?0:vo.getRefundAmmount());
			body[i][14] = DateUtil.formatDateTime(vo.getRefundTime(),"yyyy-MM-dd HH:mm:ss");
			body[i][15] = map.get(vo.getStatus());
		}
		StringBuilder sb = new StringBuilder();
		sb.append("出票时间: ").append(DateUtil.formatDateTime(qo.getDateBegin(), "yyyy-MM-dd"));
		sb.append(" 至 ").append(DateUtil.formatDateTime(qo.getDateEnd(), "yyyy-MM-dd"));
		if(list.size()>0)
		{
			sb.append("  经销商结算: ").append("￥").append(list.get(0).getTotalPrice()!=null?list.get(0).getTotalPrice():0);
			sb.append("   手续费: ").append("￥").append(list.get(0).getTotalDealerFee()!=null?list.get(0).getTotalDealerFee():0);
			sb.append("   退款金额: ").append("￥").append(list.get(0).getTotalAmount()!=null?list.get(0).getTotalAmount():0);
		}
		else
		{
			sb.append(" 经销商结算: ￥0    手续费: ￥0    退款金额: ￥0");
		}
		return ExportExcelUtils.createMyExcel("财务明细导出", title, body, out,sb.toString());
	}
	
	/**
	 * 运营端财务明细
	 * @author CaiHuan
	 * @param qo
	 * @param selectAll
	 * @return
	 */
	public Pagination queryAdminFinanceDetail(FinanceDetailQo qo, boolean selectAll)
	{
		return financeStatisticsDao.queryAdminFinanceDetail(qo,selectAll);
	}

	/**
	 * 运营端导出财务明细报表
	 * @author CaiHuan
	 * @param qo
	 * @return
	 * @throws Exception 
	 */
	public File exportAdminFinanceToExcel(FinanceDetailQo qo) throws Exception {
		//订单状态
		Map<Integer,String> map = new HashMap<Integer, String>();
		map.put(GroupTicketStatus.GROUP_TICKET_CURRENT_PAY_WAIT,"等待支付");
		map.put(GroupTicketStatus.GROUP_TICKET_CURRENT_PAY_SUCC,"支付成功");
		map.put(GroupTicketStatus.GROUP_TICKET_CURRENT_OUT_SUCC,"出票成功");
		map.put(GroupTicketStatus.GROUP_TICKET_CURRENT_DEAL_SUCC,"交易成功");
		map.put(GroupTicketStatus.GROUP_TICKET_CURRENT_DEAL_CLOSE,"交易关闭");
		
		//景区状态
		Map<Integer,String> statusMap1 = new HashMap<Integer, String>();
		statusMap1.put(SingleTicketStatus.SINGLE_TICKET_CURRENT_TOBE_ACTIVE, "待激活");
		statusMap1.put(SingleTicketStatus.SINGLE_TICKET_CURRENT_UNUSE, "待游玩");
		statusMap1.put(SingleTicketStatus.SINGLE_TICKET_CURRENT_USED, "已游玩");
		statusMap1.put(SingleTicketStatus.SINGLE_TICKET_USE_CURRENT_INVALID, "已失效(未游玩)");
		statusMap1.put(SingleTicketStatus.SINGLE_TICKET_USE_CURRENT_INVALID_II, "已失效(未支付)");
		statusMap1.put(SingleTicketStatus.SINGLE_TICKET_CURRENT_WAIT_REFUND, "待退款");
		statusMap1.put(SingleTicketStatus.SINGLE_TICKET_CURRENT_NO_REFUND, "无须退款");
		statusMap1.put(SingleTicketStatus.SINGLE_TICKET_CURRENT_REFUNDED, "已退款");
		statusMap1.put(SingleTicketStatus.SINGLE_TICKET_CURRENT_SETTLED, "已结算");
		
		Pagination pagination = financeStatisticsDao.queryAdminFinanceDetail(qo, true);
		
		@SuppressWarnings("unchecked")
		List<FinanceVo> list = (List<FinanceVo>) pagination.getList();
		String out = SystemConfig.tempFilePath + UUIDGenerator.getUUID() + ".xls";
		String[] title = new String[] { "订单编号", "票务编号", "产品编码", "产品名称", "所属单位","订票人","游玩人","下单时间",
		"出票时间","经销商","经销商结算价(元)","经销商结算方式","支付流水号","景区结算价(元)","景区手续费(元)","结算时间",
		"退款账号","退款批次号","退款金额(元)","退款时间","订单状态","景区状态"};
		String[][] body = new String[list.size()][title.length];
		for (int i = 0; i < list.size(); i++) {
			FinanceVo vo = list.get(i);
			body[i][0] = vo.getOrderId();
			body[i][1] = vo.getTicketNo();
			body[i][2] = vo.getPolicyNo();
			body[i][3] = vo.getPolicyName();
			body[i][4] = vo.getScenicSpotNameStr();
			body[i][5] = vo.getLinkMan();
			body[i][6] = vo.getTouristName();
			body[i][7] = DateUtil.formatDateTime(vo.getCreateDate(),"yyyy-MM-dd HH:m:ss");
			body[i][8] = DateUtil.formatDateTime(vo.getTicketDate(),"yyyy-MM-dd HH:m:ss");
			body[i][9] = vo.getDealerName();
			body[i][10] = String.valueOf(vo.getDealerAmount()==null?0:vo.getDealerAmount());
			if (vo.getDealerAccountType()==1)
				body[i][11] = "汇金宝";
			
			if (vo.getDealerAccountType()==2)
				body[i][11] = "支付宝";
			else
				body[i][11] = "--";
			body[i][12] = vo.getPayTradeNo();
			body[i][13] = String.valueOf(vo.getSettlementPrice()!=null&&(vo.getScenicStatus()==2||vo.getScenicStatus()==8)?vo.getSettlementPrice():"");
			body[i][14] = String.valueOf(vo.getSettlementFee()==null?0:vo.getSettlementFee());
			body[i][15] = vo.getScenicStatus()==8?DateUtil.formatDateTime(vo.getSettleDate(),"yyyy-MM-dd HH:m:ss"):"--";
			body[i][16] = vo.getDealerAccountNumber();
			body[i][17] = vo.getRefundBatchNo();
			body[i][18] = String.valueOf(vo.getDealerAmount()!=null&&(vo.getScenicStatus()==5||vo.getScenicStatus()==7)?vo.getDealerAmount():0);
			body[i][19] = "--";
			body[i][20] = map.get(vo.getStatus());
			body[i][21] = statusMap1.get(vo.getScenicStatus());
		}
		StringBuilder sb = new StringBuilder();
		sb.append("出票时间: ").append(DateUtil.formatDateTime(qo.getDateBegin(), "yyyy-MM-dd"));
		sb.append(" 至 ").append(DateUtil.formatDateTime(qo.getDateEnd(), "yyyy-MM-dd"));
		if(list.size()>0)
		{
			sb.append("  经销商结算: ").append("￥").append(list.get(0).getTotalDealerAmount()!=null?list.get(0).getTotalDealerAmount():"");
			sb.append("   景区结算: ").append("￥").append(list.get(0).getTotalScenicAmount()!=null?list.get(0).getTotalScenicAmount():0);
			sb.append("   经销商结算手续费: ").append("￥").append(list.get(0).getTotalDealerFee()!=null?list.get(0).getTotalDealerFee():0);
			sb.append("   景区结算手续费: ").append("￥").append(list.get(0).getTotalScenicFee()!=null?list.get(0).getTotalScenicFee():0);
			sb.append("   退款金额: ").append("￥").append(list.get(0).getRefundAmmount()!=null?list.get(0).getRefundAmmount():0);
			sb.append("   平台收益: ").append("￥").append(list.get(0).getPlatformIncome()!=null?list.get(0).getPlatformIncome():0);
		}
		else
		{
			sb.append(" 经销商结算: ￥0    景区结算: ￥0        经销商结算手续费: ￥0    景区结算手续费: ￥0    退款金额: ￥0    平台收益: ￥0");
		}
		return ExportExcelUtils.createMyExcel("财务明细导出", title, body, out,sb.toString());
	}
}

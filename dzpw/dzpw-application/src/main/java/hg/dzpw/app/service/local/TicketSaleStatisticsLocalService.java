package hg.dzpw.app.service.local;

import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.common.util.UUIDGenerator;
import hg.dzpw.app.common.SystemConfig;
import hg.dzpw.app.common.util.ExportExcelUtils;
import hg.dzpw.app.dao.TicketSaleStatisticsDao;
import hg.dzpw.pojo.common.DZPWConstants;
import hg.dzpw.pojo.qo.DealerSaleStatisticsQo;
import hg.dzpw.pojo.qo.GroupTicketSaleStatisticsQo;
import hg.dzpw.pojo.qo.TicketOrderTouristDetailQo;
import hg.dzpw.pojo.vo.DealerSaleStatisticsVo;
import hg.dzpw.pojo.vo.GroupTicketSaleStatisticsVo;
import hg.dzpw.pojo.vo.TicketOrderTouristDetailVo;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @类功能说明：销售统计服务
 * @类修改者：
 * @修改日期：2014-11-28上午9:55:05
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-28上午9:55:05
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TicketSaleStatisticsLocalService {
	
	@Autowired
	private TicketSaleStatisticsDao ticketSaleStatisticsDao;
	
	/**
	 * @方法功能说明：经销商销售统计查询
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-21下午2:45:04
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return @see DealerSaleStatisticsVo
	 * @return:Pagination
	 * @throws
	 */
	public Pagination queryDealerSaleStatistics(DealerSaleStatisticsQo qo) {
		return ticketSaleStatisticsDao.queryDealerSaleStatistics(qo, false);
	}

	/**
	 * @方法功能说明：联票销售统计查询
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-21下午2:45:33
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return @see GroupTicketSaleStatisticsVo
	 * @return:Pagination
	 * @throws
	 */
	public Pagination queryGroupTicketSaleStatistics(GroupTicketSaleStatisticsQo qo) {
		return ticketSaleStatisticsDao.queryGroupTicketSaleStatistics(qo, false);
	}

	/**
	 * @方法功能说明：门票订单里的用户查询
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-21下午2:45:43
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return @see TicketOrderTouristDetailVo
	 * @return:Pagination
	 * @throws
	 */
	public Pagination queryTicketOrderTouristDetail(TicketOrderTouristDetailQo qo) {
		return ticketSaleStatisticsDao.queryTicketOrderTouristDetail(qo, false);
	}

	/**
	 * @throws Exception 
	 * @方法功能说明：经销商销售统计查询导出（EXCEL2003）
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-21下午2:46:02
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:File
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public File exportDealerSaleStatisticsToExcel(DealerSaleStatisticsQo qo) throws Exception {
		Pagination pagination = ticketSaleStatisticsDao.queryDealerSaleStatistics(qo, true);
		List<DealerSaleStatisticsVo> list = (List<DealerSaleStatisticsVo>) pagination.getList();
		String out = SystemConfig.tempFilePath + UUIDGenerator.getUUID() + ".xls";

		String[] title = new String[] { "排名", "经销商名称", "销售开始日期", "销售截止日期", "销量", "日均销量", "销售额（￥）" };
		String[][] body = new String[list.size()][title.length];
		
		for (int i = 0; i < list.size(); i++) {
			DealerSaleStatisticsVo vo = list.get(i);
			body[i][0] = String.valueOf(vo.getRank());
			body[i][1] = vo.getDealerName();
			body[i][2] = DateUtil.formatDateTime(qo.getOrderDateBegin(), "yyyy-MM-dd");
			body[i][3] = DateUtil.formatDateTime(qo.getOrderDateEnd(), "yyyy-MM-dd");
			body[i][4] = String.valueOf(vo.getSaleTicketTotalCount());
			body[i][5] = String.format("%.3f", vo.getSaleTicketDayCount());
			body[i][6] = String.format("%.2f", vo.getSaleTotalAmount());
		}
		return ExportExcelUtils.createExcelFile(String.format("经销商销售统计查询导出（排名方式：%s）",
					qo.getQueryType() == DealerSaleStatisticsQo.QUERY_TYPE_ORDER_TIKECT_COUNT ? "按销量" : "按销售额"), title, body, out);

	}

	/**
	 * @throws Exception 
	 * @方法功能说明：联票销售统计查询导出（EXCEL2003）
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-21下午2:46:17
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:File
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public File exportGroupTicketSaleStatisticsToExcel(GroupTicketSaleStatisticsQo qo) throws Exception {
		Pagination pagination = ticketSaleStatisticsDao.queryGroupTicketSaleStatistics(qo, true);
		List<GroupTicketSaleStatisticsVo> list = (List<GroupTicketSaleStatisticsVo>) pagination.getList();
		String out = SystemConfig.tempFilePath + UUIDGenerator.getUUID() + ".xls";
		
		String[] title = new String[] {  "产品名称","所属景区",  "经销商", "销售日期", "订单编号", "票务编号", "销售价格" };
		String[][] body = new String[list.size()][title.length];
		
		for (int i = 0; i < list.size(); i++) {
			GroupTicketSaleStatisticsVo vo = list.get(i);
			body[i][0] = vo.getTicketPolicyName();
			body[i][1] = vo.getScenicSpotName();
			body[i][2] = vo.getDealerName();
			body[i][3] = DateUtil.formatDateTime(vo.getCreateOrderDate(),"yyyy-MM-dd");
			body[i][4] = vo.getOrderId();
			body[i][5] = vo.getTicketNo();
			body[i][6] = "￥"+vo.getSettlementAmount();
//			body[i][3] = DateUtil.formatDateTime(qo.getOrderDateBegin(), "yyyy-MM-dd");
//			body[i][4] = DateUtil.formatDateTime(qo.getOrderDateEnd(), "yyyy-MM-dd");
//			body[i][5] = String.format("%d", vo.getSaleTicketTotalCount());
//			body[i][6] = String.format("%.3f", vo.getSaleTicketDayCount());
//			body[i][7] = String.format("%.2f", vo.getSaleTotalAmount());
		}
		StringBuilder sb = new StringBuilder();
		sb.append("起止时间: ").append(DateUtil.formatDateTime(qo.getOrderDateBegin(), "yyyy-MM-dd"));
		sb.append(" 至 ").append(DateUtil.formatDateTime(qo.getOrderDateEnd(), "yyyy-MM-dd")).append(" ");
		if(list.size()>0)
		{
			sb.append(" 门票总计:  ").append(list.get(0).getSaleTicketTotalCount()).append("张 ");
			sb.append(" 日均: ").append(String.format("%.3f", list.get(0).getSaleTicketDayCount())).append("张 ");
			sb.append(" 销售总额: ￥").append(String.format("%.2f",list.get(0).getSaleTotalAmount()));
		}
		else
		{
			sb.append("门票总计：  0张   日均：0张  销售总额：0");
		}
		
		return ExportExcelUtils.createMyExcel("销售统计", title, body, out,sb.toString());
	}

	/**
	 * @throws Exception 
	 * @方法功能说明：门票订单里的用户查询导出（EXCEL2003）
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-21下午2:46:19
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:File
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public File exportTicketOrderTouristDetailToExcel(TicketOrderTouristDetailQo qo) throws Exception {
		Pagination pagination = ticketSaleStatisticsDao.queryTicketOrderTouristDetail(qo, true);
		List<TicketOrderTouristDetailVo> list = (List<TicketOrderTouristDetailVo>) pagination.getList();
		String out = SystemConfig.tempFilePath + UUIDGenerator.getUUID() + ".xls";
		
		String[] title = new String[] { "编号", "订单编号", "票号", "游玩人", "证件类型", "证件号码", "游玩状态", "下单时间" };
		String[][] body = new String[list.size()][title.length];
		
		for (int i = 0; i < list.size(); i++) {
			TicketOrderTouristDetailVo vo = list.get(i);
			body[i][0] = String.valueOf(i + 1);
			body[i][1] = vo.getTicketOrderId();
			body[i][2] = vo.getTicketNo();
			body[i][3] = vo.getName();
			body[i][4] = DZPWConstants.CER_TYPE_NAME.get(vo.getCerType());
			body[i][5] = vo.getCerNo();
			try {
				body[i][6] = DZPWConstants.GROUP_TICKET_STATUS_NAME.get(Integer.valueOf(vo.getStatus()));
			} catch (Exception e) {
				e.printStackTrace();
				body[i][6] = "未知状态";
			}
			body[i][7] = DateUtil.formatDateTime(vo.getOrderDate(), "yyyy-MM-dd HH:mm:ss");
		}
		return ExportExcelUtils.createExcelFile("销售明细导出", title, body, out);
	}
	
}

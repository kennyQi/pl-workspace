package hg.dzpw.app.service.local;

import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.common.util.UUIDGenerator;
import hg.dzpw.app.common.SystemConfig;
import hg.dzpw.app.common.util.ExportExcelUtils;
import hg.dzpw.app.dao.TicketUseStatisticsDao;
import hg.dzpw.pojo.common.DZPWConstants;
import hg.dzpw.pojo.qo.GroupTicketUseStatisticsQo;
import hg.dzpw.pojo.qo.ScenicSpotUseStatisticsQo;
import hg.dzpw.pojo.qo.TicketUsedTouristDetailQo;
import hg.dzpw.pojo.vo.GroupTicketUseStatisticsVo;
import hg.dzpw.pojo.vo.ScenicSpotUseStatisticsVo;
import hg.dzpw.pojo.vo.TicketUsedTouristDetailVo;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @类功能说明：入园统计服务
 * @类修改者：
 * @修改日期：2014-11-20下午4:57:24
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-20下午4:57:24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TicketUseStatisticsLocalService {
	
	@Autowired
	private TicketUseStatisticsDao ticketUseStatisticsDao;
	
	/**
	 * @方法功能说明：联票入园统计查询
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-20下午4:59:31
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return @see GroupTicketUseStatisticsVo
	 * @return:Pagination
	 * @throws
	 */
	public Pagination queryGroupTicketUseStatistics(GroupTicketUseStatisticsQo qo) {
		return ticketUseStatisticsDao.queryGroupTicketUseStatistics(qo, false);
	}
	
	/**
	 * @方法功能说明：景区入园统计查询
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-20下午4:59:54
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return @see ScenicSpotUseStatisticsVo
	 * @return:Pagination
	 * @throws
	 */
	public Pagination queryScenicSpotUseStatistics(ScenicSpotUseStatisticsQo qo) {
		return ticketUseStatisticsDao.queryScenicSpotUseStatistics(qo, false);
	}
	
	/**
	 * @方法功能说明：入园用户明细查询
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-20下午5:04:30
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return @see TicketUsedTouristDetailVo
	 * @return:Pagination
	 * @throws
	 */
	public Pagination queryTicketUsedTouristDetail(TicketUsedTouristDetailQo qo) {
		return ticketUseStatisticsDao.queryTicketUsedTouristDetail(qo, false);
	}

	/**
	 * @throws Exception 
	 * @方法功能说明：联票入园统计导出（EXCEL2003）
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-20下午4:59:31
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:File
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public File exportGroupTicketUseStatisticsToExcel(GroupTicketUseStatisticsQo qo) throws Exception {
		Pagination pagination = ticketUseStatisticsDao.queryGroupTicketUseStatistics(qo, true);
		List<GroupTicketUseStatisticsVo> list = (List<GroupTicketUseStatisticsVo>) pagination.getList();
		String out = SystemConfig.tempFilePath + UUIDGenerator.getUUID() + ".xls";
		String[] title = new String[] { "排序", "所属联票", "销售开始日期", "销售截止日期", "入园人次", "日均入园人次" };
		String[][] body = new String[list.size()][title.length];
		for (int i = 0; i < list.size(); i++) {
			GroupTicketUseStatisticsVo vo = list.get(i);
			body[i][0] = String.valueOf(vo.getRank());
			body[i][1] = vo.getTicketPolicyName();
			body[i][2] = DateUtil.formatDateTime(qo.getOrderDateBegin(), "yyyy-MM-dd");
			body[i][3] = DateUtil.formatDateTime(qo.getOrderDateEnd(), "yyyy-MM-dd");
			body[i][4] = String.valueOf(vo.getUseCount());
			body[i][5] = String.format("%.1f", vo.getDayCount());
		}
		return ExportExcelUtils.createExcelFile("联票入园统计导出", title, body, out);
	}
	
	/**
	 * @throws Exception 
	 * @方法功能说明：景区入园统计导出（EXCEL2003）
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-20下午4:59:54
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:File
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public File exportScenicSpotUseStatisticsToExcel(ScenicSpotUseStatisticsQo qo) throws Exception {
		Pagination pagination = ticketUseStatisticsDao.queryScenicSpotUseStatistics(qo, true);
		List<ScenicSpotUseStatisticsVo> list = (List<ScenicSpotUseStatisticsVo>) pagination.getList();
		String out = SystemConfig.tempFilePath + UUIDGenerator.getUUID() + ".xls";
		String[] title = new String[] { "入园景区", "产品名称", "票务编号","票务类型", "游客信息", "订单编号", "入园日期" };
		String[][] body = new String[list.size()][title.length];
		for (int i = 0; i < list.size(); i++) {
			ScenicSpotUseStatisticsVo vo = list.get(i);
			body[i][0] = vo.getScenicSpotName();
			body[i][1] = vo.getPolicyName();
			body[i][2] = vo.getTicketNo();
			body[i][3] = vo.getTicketPolicyType()==1? "单票" : "联票";
			body[i][4] = vo.getTouristName()+"  "+vo.getTourisIdNo();
			body[i][5] = vo.getOrderId();
			body[i][6] = DateUtil.formatDateTime(vo.getEnterDate(), "yyyy-MM-dd");
		}
		StringBuilder sb = new StringBuilder();
		sb.append("起止时间: ").append(DateUtil.formatDateTime(qo.getOrderDateBegin(), "yyyy-MM-dd"));
		sb.append(" 至 ").append(DateUtil.formatDateTime(qo.getOrderDateEnd(), "yyyy-MM-dd"));
		if(list.size()>0)
		{
			sb.append(" 入园次数总计: ").append(list.get(0).getUseCount());
			sb.append("   单票张数: ").append(list.get(0).getSigleTicketNum());
			sb.append("   联票张数: ").append(list.get(0).getGroupTicketNum());
		}
		else
		{
			sb.append(" 入园次数总计: 0    单票张数: 0    联票张数: 0");
		}
		return ExportExcelUtils.createMyExcel("景区入园统计导出", title, body, out,sb.toString());
	}
	
	/**
	 * @throws Exception 
	 * @方法功能说明：入园用户明细导出（EXCEL2003）
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-20下午5:04:30
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:File
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public File exportTicketUsedTouristDetailToExcel(TicketUsedTouristDetailQo qo) throws Exception {
		Pagination pagination = ticketUseStatisticsDao.queryTicketUsedTouristDetail(qo, true);
		List<TicketUsedTouristDetailVo> list = (List<TicketUsedTouristDetailVo>) pagination.getList();
		String out = SystemConfig.tempFilePath + UUIDGenerator.getUUID() + ".xls";
		String[] title = new String[] { "编号", "证件类型", "证件号码", "用户姓名", "票务名称", "入园景区", "入园时间" };
		String[][] body = new String[list.size()][title.length];
		for (int i = 0; i < list.size(); i++) {
			TicketUsedTouristDetailVo vo = list.get(i);
			body[i][0] = String.valueOf(i + 1);
			body[i][1] = DZPWConstants.CER_TYPE_NAME.get(vo.getCerType());
			body[i][2] = vo.getCerNo();
			body[i][3] = vo.getName();
			body[i][4] = vo.getTicketPolicyName();
			body[i][5] = vo.getScenicSpotName();
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < vo.getUseDates().size(); j++) {
				if (j != 0) sb.append(", ");
				sb.append(DateUtil.formatDateTime(vo.getUseDates().get(j), "yyyy-MM-dd HH:mm:ss"));
			}

			body[i][6] = sb.toString();
		}
		return ExportExcelUtils.createExcelFile("入园用户明细导出", title, body, out);
	}
}
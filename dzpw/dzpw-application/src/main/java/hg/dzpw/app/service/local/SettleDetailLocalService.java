package hg.dzpw.app.service.local;

import java.io.File;
import java.util.List;

import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.common.util.UUIDGenerator;
import hg.dzpw.app.common.SystemConfig;
import hg.dzpw.app.common.util.ExportExcelUtils;
import hg.dzpw.app.dao.SettleDetailStatisticsDao;
import hg.dzpw.pojo.qo.SettleDetailQo;
import hg.dzpw.pojo.vo.SettleDetailVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 财务明细服务
 * @author CaiHuan
 *
 * 日期:2015-12-24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SettleDetailLocalService {
	
	@Autowired
	private SettleDetailStatisticsDao settleDetailStatisticsDao;

	public Pagination querySettleDetail(SettleDetailQo qo, boolean selectAll)
	{
		return settleDetailStatisticsDao.querySettleDetail(qo,selectAll);
	}
	
	@SuppressWarnings("unchecked")
	public File exportSettleDetailToExcel(SettleDetailQo qo) throws Exception
	{
		Pagination pagination = settleDetailStatisticsDao.querySettleDetail(qo, true);
		List<SettleDetailVo> list = (List<SettleDetailVo>) pagination.getList();
		String out = SystemConfig.tempFilePath + UUIDGenerator.getUUID() + ".xls";
		String[] title = new String[] { "产品名称", "票务编号", "票务类型", "游客信息", "订单编号","出票时间" ,"景区结算价","手续费","景区状态","结算日期" };
		String[][] body = new String[list.size()][title.length];
		for (int i = 0; i < list.size(); i++) {
			SettleDetailVo vo = list.get(i);
			body[i][0] = vo.getPolicyName();
			body[i][1] = vo.getTicketNo();
			body[i][2] = vo.getTicketPolicyType()==1?"单票":"联票";
			body[i][3] = vo.getTouristName()+"    "+vo.getTourisIdNo();
			body[i][4] = vo.getOrderId();
			body[i][5] = DateUtil.formatDateTime(vo.getPayDate(),"yyyy-MM-dd HH:mm:ss");
			body[i][6] = String.valueOf(vo.getScenicSpotAmount()==null?0:vo.getScenicSpotAmount());
			body[i][7] = String.valueOf(vo.getAmountFee()==null?0:vo.getAmountFee());
			//景区状态
			String status = "";
			if(vo.getScenicSpotStatus()==2)
			{
				status = "已游玩";
			}
			else if(vo.getScenicSpotStatus()==8)
			{
				status = "已结算";
			}
			body[i][8] = status;
			body[i][9] = vo.getScenicSpotStatus()==8?DateUtil.formatDateTime(vo.getSettlementDate(),"yyyy-MM-dd"):"--";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("出票时间: ").append(DateUtil.formatDateTime(qo.getDateBegin(), "yyyy-MM-dd"));
		sb.append(" 至 ").append(DateUtil.formatDateTime(qo.getDateEnd(), "yyyy-MM-dd"));
		if(list.size()>0)
		{
			sb.append(" 总计票价: ").append("￥").append(list.get(0).getTotalPrice()!=null?list.get(0).getTotalPrice():0);
			sb.append("   手续费: ").append("￥").append(list.get(0).getTotalFee()!=null?list.get(0).getTotalFee():0);
			sb.append("   实际结算: ").append("￥").append(list.get(0).getTotalAmount()!=null?list.get(0).getTotalAmount():0);
		}
		else
		{
			sb.append(" 总计票价: ￥0    手续费: ￥0    实际结算: ￥0");
		}
		return ExportExcelUtils.createMyExcel("财务明细导出", title, body, out,sb.toString());
	}
}

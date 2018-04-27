package hg.dzpw.app.service.local;

import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.common.util.UUIDGenerator;
import hg.dzpw.app.common.SystemConfig;
import hg.dzpw.app.common.util.ExportExcelUtils;
import hg.dzpw.app.dao.FinanceManagementDao;
import hg.dzpw.pojo.qo.ReconciliationCollectOrderQo;
import hg.dzpw.pojo.qo.ReconciliationOrderQo;
import hg.dzpw.pojo.vo.ReconciliationCollectOrderVo;
import hg.dzpw.pojo.vo.ReconciliationOrderVo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @类功能说明：财务管理服务
 * @类修改者：
 * @修改日期：2014-11-14下午4:17:01
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-14下午4:17:01
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FinanceManagementLocalService {
	
	@Autowired
	private FinanceManagementDao financeManagementDao;
	
	/**
	 * @方法功能说明：汇总对账单查询
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-14下午4:21:31
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return @see ReconciliationCollectOrderVo
	 * @return:Pagination
	 * @throws
	 */
	public Pagination queryReconciliationCollectOrder(ReconciliationCollectOrderQo qo) {
		return financeManagementDao.queryReconciliationCollectOrder(qo, false);
	}
	
	/**
	 * @方法功能说明：支付对账单查询
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-14下午4:22:23
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return @see ReconciliationOrderVo
	 * @return:Pagination
	 * @throws
	 */
	public Pagination queryReconciliationOrder(ReconciliationOrderQo qo) {
		return financeManagementDao.queryReconciliationOrder(qo, false);
	}
	
	/**
	 * @throws Exception 
	 * @方法功能说明：导出汇总对账单查询(Excel2003格式)
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-14下午4:21:31
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return @see ReconciliationCollectOrderVo
	 * @return:Pagination
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public File exportReconciliationCollectOrderToExcel(ReconciliationCollectOrderQo qo) throws Exception {
		Pagination pagination = financeManagementDao.queryReconciliationCollectOrder(qo, true);
		List<ReconciliationCollectOrderVo> list = (List<ReconciliationCollectOrderVo>) pagination.getList();
		String out = SystemConfig.tempFilePath + UUIDGenerator.getUUID() + ".xls";
		String[] title = new String[] { "编号", "景区名称", "应收金额（￥）", "实付金额（￥）", "实际结算金额（￥）", "门票总数（张）" };
		String[][] body = new String[list.size()][title.length];
		for (int i = 0; i < list.size(); i++) {
			ReconciliationCollectOrderVo vo = list.get(i);
			body[i][0] = String.valueOf(i + 1);
			body[i][1] = vo.getScenicSpotName();
			body[i][2] = String.format("%.2f", vo.getPrice().floatValue());
			body[i][3] = String.format("%.2f", vo.getPaidAmount().floatValue());
			body[i][4] = String.format("%.2f", vo.getSettlementAmount().floatValue());
			body[i][5] = String.valueOf(vo.getGroupTicketTotalNumber());
		}
		StringBuilder header = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		header.append("财务对账导出（")
				.append(qo.getOrderDateBegin() == null ? "" : sdf.format(qo.getOrderDateBegin()))
				.append("至")
				.append(qo.getOrderDateEnd() == null ? "" : sdf.format(qo.getOrderDateEnd()))
				.append("）");
		return ExportExcelUtils.createExcelFile(header.toString(), title, body, out);
	}
	
	/**
	 * @throws Exception 
	 * @方法功能说明：导出支付对账单查询(Excel2003格式)
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-14下午4:22:23
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return @see ReconciliationOrderVo
	 * @return:Pagination
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public File exportReconciliationOrderToExcel(ReconciliationOrderQo qo) throws Exception {
		Pagination pagination = financeManagementDao.queryReconciliationOrder(qo, true);
		List<ReconciliationOrderVo> list = (List<ReconciliationOrderVo>) pagination.getList();
		String out = SystemConfig.tempFilePath + UUIDGenerator.getUUID() + ".xls";
		String[] title = new String[] { "编号", "票号", "票务名称", "下单时间", "付款时间", "应付金额（￥）", "实付金额（￥）", "景区名称", "实际结算金额（￥）" };
		String[][] body = new String[list.size()][title.length];
		for (int i = 0; i < list.size(); i++) {
			ReconciliationOrderVo vo = list.get(i);
			body[i][0] = String.valueOf(i + 1);
			body[i][1] = vo.getTicketNo();
			body[i][2] = vo.getPolicyName();
			body[i][3] = DateUtil.formatDateTime(vo.getOrderDate(), "yyyy-MM-dd HH:mm:ss");
			body[i][4] = DateUtil.formatDateTime(vo.getPayDate(), "yyyy-MM-dd HH:mm:ss");
			body[i][5] = String.format("%.2f", vo.getPrice().floatValue());
			body[i][6] = String.format("%.2f", vo.getPaidAmount().floatValue());
			body[i][7] = vo.getScenicSpotName();
			body[i][8] = String.format("%.2f", vo.getSettlementAmount().floatValue());
		}
		return ExportExcelUtils.createExcelFile("支付对账单导出", title, body, out);
	}
}

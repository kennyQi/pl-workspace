package hg.dzpw.app.service.local;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hg.common.component.BaseServiceImpl;
import hg.dzpw.app.dao.AliPayTransferRecordDao;
import hg.dzpw.app.pojo.qo.AliPayTransferRecordQo;
import hg.dzpw.domain.model.pay.AliPayTransferRecord;
import hg.dzpw.pojo.api.alipay.CaeChargeParameter;
import hg.dzpw.pojo.api.alipay.CaeChargeResponse;

/**
 * @类功能说明：支付宝 代扣、退款服务
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2016-3-16下午1:59:44
 * @版本：V1.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AliPayTransferRecordLocalService extends BaseServiceImpl<AliPayTransferRecord, AliPayTransferRecordQo, AliPayTransferRecordDao>{

	@Autowired
	private AliPayTransferRecordDao dao;
	
	
	@Override
	protected AliPayTransferRecordDao getDao() {
		return dao;
	}
	
	
	public AliPayTransferRecord recordRequest(CaeChargeParameter cae){
		AliPayTransferRecord record = new AliPayTransferRecord();
			
		record.createByCaeChargeParameter(cae, AliPayTransferRecord.TYPE_DEALER_TO_DZPW);
		getDao().save(record);
		
		return record;
	}
	
	
	public void recordResponse(CaeChargeResponse resp, String recordId){
		
		AliPayTransferRecord record = getDao().get(recordId);
		if (record == null || resp == null)
			return;
		
		record.setCaeChargeResponse(resp);
		getDao().update(record);
		
		return;
	}


	/**
	 * 
	 * @描述： 推送完成后更新推送结果
	 * @param isSuccess 是否已推送成功
	 * @param ticketId 记录关联的Id
	 * @author: guotx 
	 * @version: 2016-4-11 下午5:25:22
	 * @param ticketNo 
	 */
	public void updateRecordPublicStatus(String ticketId, boolean isSuccess) {
		String hql="update AliPayTransferRecord record set record.isNotifySuccess=%s where record.groupTicketId='%s' and record.type=%d";
		hql=String.format(hql, isSuccess,ticketId,AliPayTransferRecord.TYPE_DZPW_TO_DEALER);
//		AliPayTransferRecordQo transferRecordQo=new AliPayTransferRecordQo();
//		transferRecordQo.setGroupTicketId(ticketId);
//		transferRecordQo.setType(AliPayTransferRecord.TYPE_DZPW_TO_DEALER);
//		AliPayTransferRecord record = getDao().queryUnique(transferRecordQo);
//		record.setIsNotifySuccess(isSuccess);
//		getDao().update(record);
		getDao().executeUpdate(hql);
	}
	
}

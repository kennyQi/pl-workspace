package pay.record.app.service.spi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pay.record.app.service.base.BasePayRecordSpiServiceImpl;
import pay.record.app.service.local.pay.AirPayRecordLocalService;
import pay.record.pojo.dto.AirPayRecordDTO;
import pay.record.pojo.qo.pay.AirPayRecordQO;
import pay.record.spi.inter.pay.AirPayRecordService;

@Service("airPayRecordService")
public class AirPayRecordServiceImpl extends BasePayRecordSpiServiceImpl<AirPayRecordDTO, AirPayRecordQO, AirPayRecordLocalService>  implements AirPayRecordService{
	@Autowired
	AirPayRecordLocalService payRecordLocalService;
	
	@Override
	protected AirPayRecordLocalService getService() {
		return payRecordLocalService;
	}

	@Override
	protected Class<AirPayRecordDTO> getDTOClass() {
		return AirPayRecordDTO.class;
	}

	
//	@Override
//	public Pagination queryJPPaymentOrderList(Pagination pagination) {
//	
//		return queryPagination(pagination);
//	}
//
//	@Override
//	public List<AirPayRecordDTO> queryAirPaymentRecordList(AirPayRecordQO payRecordQO) {
//		
//		return queryList(payRecordQO);
//	}


}
